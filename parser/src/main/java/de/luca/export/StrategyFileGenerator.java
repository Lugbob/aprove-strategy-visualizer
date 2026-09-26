package de.luca.export;

import java.util.List;
import java.util.Map;

import de.luca.graph.Graph;
import de.luca.graph.Node;

//Rekonstruiert strategy files aus einem erstellten Java-Graphen
public class StrategyFileGenerator {

    public String generateFile(Map<String, Graph> graphs) {
        String result = "";

        for (String strategyName : graphs.keySet()) {
            result += generate(strategyName, graphs.get(strategyName));
            result += "\n\n";
        }

        return result;
    }

    public String generate(String strategyName, Graph graph) {
        String body = nodeToStrategy(graph.getRoot(), graph);
        String result = "";

        if (graph.getDescription() != null && !graph.getDescription().isBlank()) {
            for (String line : graph.getDescription().split("\\R")) {
                result += "#@description " + line + "\n";
            }
        }
        result += strategyName + " = " + body;
        return result;
    }

    private String nodeToStrategy(String nodeId, Graph graph) {
        Node node = findNode(nodeId, graph);
        String label = node.data().getLabel();

        String result;
        // Processor
        if (label.startsWith("Processor")) {
            result = label.substring("Processor: ".length()).trim();
        }

        // Reference
        else if (label.startsWith("Reference")) {
            result = label.substring("Reference: ".length()).trim();
        }

        // AnyK
        else if (label.startsWith("AnyK")) {

            String max;

            if (label.startsWith("AnyK:")) {
                max = label.substring("AnyK:".length()).trim();
            } else {
                int maxStart = label.indexOf("max ") + "max ".length();
                int maxEnd = label.indexOf(" parallel");
                max = label.substring(maxStart, maxEnd);
            }

            List<String> children = node.data().getChildren();

            result = "AnyK(" + max;

            for (String childId : children) {
                result += ", " + nodeToStrategy(childId, graph);
            }

            result += ")";
        }

        // First
        else if (label.startsWith("First")) {
            List<String> children = node.data().getChildren();

            result = "First(";

            for (int i = 0; i < children.size(); i++) {
                result += nodeToStrategy(children.get(i), graph);

                if (i < children.size() - 1) {
                    result += ", ";
                }
            }
            result += ")";
        }

        // Delay / AnyDelay
        else if (label.startsWith("AnyDelay") || label.startsWith("Delay")) {

            String type;

            if (label.startsWith("AnyDelay")) {
                type = "AnyDelay";
            } else {
                type = "Delay";
            }

            int colonPosition = label.indexOf(":");
            String time = label.substring(colonPosition + 1).trim();

            List<String> children = node.data().getChildren();

            result = type + "(" + time;

            for (String childId : children) {
                result += ", " + nodeToStrategy(childId, graph);
            }

            result += ")";
        }

        // Any
        else if (label.startsWith("Any")) {
            List<String> children = node.data().getChildren();

            result = "Any(";

            for (int i = 0; i < children.size(); i++) {
                result += nodeToStrategy(children.get(i), graph);

                if (i < children.size() - 1) {
                    result += ", ";
                }
            }

            result += ")";
        }

        // Maybe
        else if (label.startsWith("Maybe")) {
            String childId = node.data().getChildren().get(0);
            String child = nodeToStrategy(childId, graph);
            result = "Maybe(" + child + ")";
        }

        // RepeatS oder Repeat
        else if (label.startsWith("RepeatS") || label.startsWith("Repeat(")) {

            String type;

            if (label.startsWith("RepeatS")) {
                type = "RepeatS";
            } else {
                type = "Repeat";
            }

            String lower;
            String upper;

            if (!label.contains("Lower Bound")) {
                int start = label.indexOf("(") + 1;
                int comma = label.indexOf(",", start);
                int end = label.lastIndexOf(")");

                lower = label.substring(start, comma).trim();
                upper = label.substring(comma + 1, end).trim();
            } else {
                int lowerStart = label.indexOf("Lower Bound = ") + "Lower Bound = ".length();
                int lowerEnd = label.indexOf(" |");
                lower = label.substring(lowerStart, lowerEnd);

                int upperStart = label.indexOf("Upper Bound = ") + "Upper Bound = ".length();
                int upperEnd = label.indexOf(")");
                upper = label.substring(upperStart, upperEnd);
            }

            String childId = node.data().getChildren().get(0);
            String child = nodeToStrategy(childId, graph);

            result = type + "(" + lower + "," + upper + "," + child + ")";
        }

        // Timer / WallTimer
        else if (label.startsWith("Timer") || label.startsWith("WallTimer")) {

            String type;

            if (label.startsWith("WallTimer")) {
                type = "WallTimer";
            } else {
                type = "Timer";
            }

            int colonPosition = label.indexOf(":");
            String time = label.substring(colonPosition + 1).trim();

            String childId = node.data().getChildren().get(0);
            String child = nodeToStrategy(childId, graph);

            result = type + "(" + time + "," + child + ")";
        }

        // Solve / Prove / Disprove
        else if (label.equals("Solve") || label.equals("Prove") || label.equals("Disprove")) {

            String childId = node.data().getChildren().get(0);
            String child = nodeToStrategy(childId, graph);

            result = label + "(" + child + ")";
        }

        // Combine / CombineParallel / CombineSequential
        else if (label.startsWith("Combine")) {

            String type;

            if (label.startsWith("CombineParallel") || label.contains("{parallel}")) {
                type = "CombineParallel";
            } else if (label.startsWith("CombineSequential") || label.contains("{sequential}")) {
                type = "CombineSequential";
            } else {
                type = "Combine";
            }

            List<String> children = node.data().getChildren();

            result = type + "(";

            for (int i = 0; i < children.size(); i++) {
                result += nodeToStrategy(children.get(i), graph);

                if (i < children.size() - 1) {
                    result += ", ";
                }
            }
            result += ")";
        }

        // If
        else if (label.startsWith("Condition[")) {

            int start = "Condition[".length();
            int end = label.lastIndexOf("]");

            String condition = label.substring(start, end);

            List<String> children = node.data().getChildren();

            result = "If[Condition=" + condition + "]";

            if (children.size() > 0) {
                result += "(";

                result += nodeToStrategy(children.get(0), graph);

                if (children.size() > 1) {
                    result += ", " + nodeToStrategy(children.get(1), graph);
                }

                result += ")";
            }
        }

        else {
            throw new RuntimeException("Unknown node type: " + label);
        }

        if (node.data().getNext() != null) {
            result += " " + node.data().getSequenceOperator() + " " + nodeToStrategy(node.data().getNext(), graph);
        }

        return result;
    }

    private Node findNode(String nodeId, Graph graph) {
        for (Node node : graph.getNodes()) {
            if (node.id().equals(nodeId)) {
                return node;
            }
        }
        throw new RuntimeException("Node not found: " + nodeId);
    }

}