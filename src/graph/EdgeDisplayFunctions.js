import { MarkerType } from "@xyflow/react";
import {
  isBlockExit,
  getSequenceOperator,
  containsNode,
  getRepeatLoopEdges,
} from "./GraphFunctions.js";

//Kantenlogik im Visualizer:
export function buildVisibleEdges(currentGraph, showBlockExits, editMode) {
  //Lookup maps für Kanten und Knoten:
  const nodeById = new Map(currentGraph.nodes.map((node) => [node.id, node]));

  const edgesBySource = new Map();

  for (const edge of currentGraph.edges) {
    if (!edgesBySource.has(edge.source)) {
      edgesBySource.set(edge.source, []);
    }
    edgesBySource.get(edge.source).push(edge);
  }

  function getVisibleTargets(nodeId, inheritedOperator = null) {
    const node = nodeById.get(nodeId);

    if (!isBlockExit(node)) {
      return [{ target: nodeId, operator: inheritedOperator }];
    }

    const outgoingEdges = edgesBySource.get(nodeId) ?? [];

    if (outgoingEdges.length === 0) {
      return [];
    }

    return outgoingEdges.flatMap((edge) => {
      const operator = inheritedOperator ?? getSequenceOperator(edge);

      return getVisibleTargets(edge.target, operator);
    });
  }

  let drawableEdges = [];

  //Macht Exit-Nodes sichtbar
  if (showBlockExits) {
    drawableEdges = [...currentGraph.edges];
  } else {
    for (const edge of currentGraph.edges) {
      const sourceNode = nodeById.get(edge.source);
      if (isBlockExit(sourceNode)) {
        continue;
      }

      const visibleTargets = getVisibleTargets(
        edge.target,
        getSequenceOperator(edge),
      );

      for (const visibleTarget of visibleTargets) {
        drawableEdges.push({
          ...edge,
          id: edge.id + "-to-" + visibleTarget.target,
          source: edge.source,
          target: visibleTarget.target,
          sequenceOperator: visibleTarget.operator,
          data: {
            ...edge.data,
            sequenceOperator: visibleTarget.operator,
          },
        });
      }
    }
  }
  //Repeat loop edges werden mit editierbaren ausgetauscht:
  if (editMode) {
    let repeatLoopEdges = getRepeatLoopEdges(currentGraph);

    drawableEdges = drawableEdges.filter((edge) => {
      const repeat = currentGraph.nodes.find(
        (node) =>
          node.id === edge.target &&
          (node.data.label.startsWith("Repeat(") ||
            node.data.label.startsWith("RepeatS(")),
      );

      if (!repeat) return true;

      const childId = repeat.data.children?.[0];

      return !childId || !containsNode(childId, edge.source, currentGraph);
    });

    drawableEdges.push(...repeatLoopEdges);
  }

  const targetCounts = new Map();

  for (const edge of drawableEdges) {
    const target = edge.target;
    const oldCount = targetCounts.get(target) ?? 0;
    targetCounts.set(target, oldCount + 1);
  }

  const targetSeen = new Map();

  //Kanten nach Typ einfärben:
  const visibleEdges = drawableEdges.map((edge) => {
    const targetIndex = targetSeen.get(edge.target) ?? 0;
    targetSeen.set(edge.target, targetIndex + 1);
    const sourceNode = nodeById.get(edge.source);
    const targetNode = nodeById.get(edge.target);
    const isExitEdge = isBlockExit(sourceNode) || isBlockExit(targetNode);

    const operator = getSequenceOperator(edge);

    let edgeColor = "#9ca3af";
    let strokeWidth = 1.8;
    let dashedLinePattern;

    if (operator === ":") {
      edgeColor = "#60a5fa";
      strokeWidth = 2.2;
    }

    if (operator === ";") {
      edgeColor = "#86efac";
      strokeWidth = 2.2;
      dashedLinePattern = "6 4";
    }

    if (isExitEdge) {
      edgeColor = "#ef4444";
      strokeWidth = 2.5;
      dashedLinePattern = undefined;
    }

    return {
      ...edge,
      type: "floating",
      label: edge.label,
      data: {
        ...edge.data,
        sequenceOperator: operator,
        targetIndex,
        targetCount: targetCounts.get(edge.target),
      },
      style: {
        stroke: edgeColor,
        strokeWidth,
        dashedLinePattern,
      },
      markerEnd: {
        type: MarkerType.ArrowClosed,
        width: isExitEdge ? 35 : 30,
        height: isExitEdge ? 35 : 30,
        color: edgeColor,
      },
    };
  });
  return visibleEdges;
}
