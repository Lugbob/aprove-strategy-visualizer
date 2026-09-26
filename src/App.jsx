import { useEffect, useState } from "react";
import { ReactFlow, applyNodeChanges } from "@xyflow/react";
import "@xyflow/react/dist/style.css";
import FloatingEdge from "./graph/FloatingEdge.jsx";
import ConditionNode from "./components/ConditionNode.jsx";
import Legend from "./components/Legend.jsx";
import DetailsPanel from "./components/DetailsPanel.jsx";
import BoxNode from "./components/BoxNode.jsx";
import {
  validProcessorParameters,
  formatProcessorParameters,
} from "./utils/ProcessorFunctions.js";
import {
  isBlockExit,
  containsNode,
  getExits,
  hasSequencedAncestor,
} from "./graph/GraphFunctions.js";
import {
  createPlaceholder,
  createBox,
  addBoxDescendants,
  addMissingBoxRootIds,
} from "./graph/EditGraphFunctions.js";
import TermMenu from "./components/TermMenu.jsx";
import { buildVisibleEdges } from "./graph/EdgeDisplayFunctions.js";
import {
  getNodeTypeStyle,
  getBoxColor,
} from "./components/NodeStyleFunctions.js";

const X_GAP = 420;
const Y_GAP = 250;

const edgeTypes = {
  floating: FloatingEdge,
};

const nodeTypes = {
  condition: ConditionNode,
  box: BoxNode,
};

export default function App() {
  const [allGraphs, setAllGraphs] = useState(null);
  const [selectedGraphName, setSelectedGraphName] = useState("main");
  const [selectedDetails, setSelectedDetails] = useState(null);
  const [history, setHistory] = useState(["main"]); //main ist immer der erste Eintrag beim neu laden
  const [showBlockExits, setShowBlockExits] = useState(false);
  const [hoveredLabel, setHoveredLabel] = useState(null);
  const [hoverPosition, setHoverPosition] = useState({ x: 0, y: 0 });
  const [editMode, setEditMode] = useState(false);
  const [editableGraphs, setEditableGraphs] = useState(null);
  const [selectedEditNode, setSelectedEditNode] = useState(null);
  const [showTermMenu, setShowTermMenu] = useState(false);
  const [declaredProcessors, setDeclaredProcessors] = useState([]);
  const [newReferenceName, setNewReferenceName] = useState("");
  const [showStrategyMenu, setShowStrategyMenu] = useState(false);
  const [newStrategyName, setNewStrategyName] = useState("");
  const [timerValue, setTimerValue] = useState("");
  const [editNodes, setEditNodes] = useState([]);
  const [childCount, setChildCount] = useState("");
  const [anyKLimit, setAnyKLimit] = useState("");
  const [repeatMin, setRepeatMin] = useState("");
  const [repeatMax, setRepeatMax] = useState("");
  const [ifCondition, setIfCondition] = useState("");
  const [ifHasElse, setIfHasElse] = useState(false);
  const [activeSubmenu, setActiveSubmenu] = useState(null);
  const [processorDefaults, setProcessorDefaults] = useState({});
  const [selectedProcessor, setSelectedProcessor] = useState(null);
  const [processorParameters, setProcessorParameters] = useState("");
  const [showDescriptionEditor, setShowDescriptionEditor] = useState(false);

  //Generierte JSON wird geladen
  useEffect(() => {
    fetch("/parsedGraph.json")
      .then((response) => response.json())
      .then((data) => setAllGraphs(data))
      .catch((error) =>
        console.error("Graph konnte nicht geladen werden: ", error),
      );
  }, []);

  useEffect(() => {
    fetch("/analysis.json")
      .then((response) => response.json())
      .then((data) => {
        setDeclaredProcessors(data.declaredProcessors);
        setProcessorDefaults(data.declaredProcessorsdefaults);
      })
      .catch((error) =>
        console.error("Analysis konnte nicht geladen werden: ", error),
      );
  }, []);

  //Synchronisiert editNodes mit dem aktuellen Graphen
  useEffect(() => {
    if (editMode && editableGraphs && editableGraphs[selectedGraphName]) {
      setEditNodes(editableGraphs[selectedGraphName].nodes);
    }
  }, [editMode, selectedGraphName, editableGraphs]);

  const activeGraphs = editMode && editableGraphs ? editableGraphs : allGraphs;
  const graphNames = activeGraphs ? Object.keys(activeGraphs) : [];
  const currentGraph = activeGraphs?.[selectedGraphName] ?? {
    root: null,
    nodes: [],
    edges: [],
  };

  let selectedNodeForEdit = null;
  let canAddTerm = false;
  let canAddSequence = false;

  if (selectedEditNode) {
    selectedNodeForEdit = currentGraph.nodes.find(
      (node) => node.id === selectedEditNode,
    );
  }

  if (selectedNodeForEdit) {
    //Damit addTerm nur bei Placeholdern geht:
    if (selectedNodeForEdit.data.label === "Select strategy term") {
      canAddTerm = true;
    } else if (
      !selectedNodeForEdit.data.next &&
      !hasSequencedAncestor(selectedNodeForEdit.id, currentGraph)
    ) {
      canAddSequence = true;
    }
  }
  const visibleEdges = buildVisibleEdges(
    currentGraph,
    showBlockExits,
    editMode,
  );
  function closeAllSubmenus() {
    setActiveSubmenu(null);
  }

  // Bereitet die Nodes für die Darstellung in React Flow auf
  let nodesForDisplay = currentGraph.nodes;

  if (editMode) {
    nodesForDisplay = updateEditBoxes(editNodes);
  }

  const displayNodes = nodesForDisplay
    .filter((node) => {
      if (editMode && isBlockExit(node)) {
        return false;
      }

      return showBlockExits || !isBlockExit(node);
    })
    .map((node) => {
      if (isBlockExit(node)) {
        return {
          ...node,
          draggable: false,
          data: {
            ...node.data,
            label: "Exit",
          },
          style: {
            ...node.style,
            minWidth: "70px",

            fontSize: "16px",
            fontWeight: "600",
            textAlign: "center",
            backgroundColor: "rgba(239, 68, 68, 0.12)",
            border: "2px solid #ef4444",
            color: "#991b1b",
          },
        };
      }

      if (!node.data || !node.data.label) {
        return {
          ...node,
          zIndex: 1,
        };
      }

      if (node.data?.box) {
        const boxColor = getBoxColor(node.data.label);
        return {
          ...node,
          type: "box",
          dragHandle: ".box-drag-handle",
          zIndex: -Math.round(node.data.width * node.data.height),
          draggable: editMode,
          selectable: false,
          connectable: false,
          data: {
            ...node.data,
            label: node.data.label,
          },
          style: {
            ...node.style,
            width: node.data.width,
            height: node.data.height,
            ...boxColor,
            borderRadius: "10px",
            padding: "0",
            boxSizing: "border-box",
            pointerEvents: "none",
          },
        };
      }

      let label = node.data.label;

      if (label.startsWith("Condition[")) {
        return {
          ...node,
          zIndex: 1,
          type: "condition",
          data: {
            ...node.data,
            label: "Condition",
            fullLabel: label,
            isExpandable: true,
            details: label,
          },
          style: {
            ...node.style,
            width: 130,
            height: 130,
            backgroundColor: "transparent",
            border: "none",
          },
        };
      }
      let nodeStyle = {
        ...node.style,
        minWidth: "200px",
        maxWidth: "400px",
        fontSize: "25px",
        lineHeight: "1.2",
        padding: "6px 10px",
        whiteSpace: "pre-wrap",
        overflowWrap: "break-word",
        textAlign: "center",
        ...getNodeTypeStyle(label),
      };

      if (
        label.startsWith("Processor:") ||
        label.startsWith("Condition[") ||
        label.startsWith("Reference:") ||
        label.startsWith("Repeat(") ||
        label.startsWith("RepeatS(") ||
        label.startsWith("Timer:") ||
        label.startsWith("CombineParallel") ||
        label.startsWith("CombineSequential") ||
        label.startsWith("WallTimer:") ||
        label.startsWith("Delay:") ||
        label.startsWith("AnyDelay:") ||
        label.startsWith("Iterate(") ||
        label.startsWith("IterateS(")
      ) {
        let shortLabel = label;
        let bracketPos = label.indexOf("[");
        let fromBracket = "No Parameters";

        let graphTarget = null;

        if (label.startsWith("Processor:")) {
          let withoutType = label.substring("Processor:".length).trim();
          let processorBracketPos = withoutType.indexOf("[");

          if (processorBracketPos !== -1) {
            shortLabel = withoutType.substring(0, processorBracketPos).trim();
            fromBracket =
              "Type: Processor\n" + withoutType.substring(processorBracketPos);
          } else {
            shortLabel = withoutType;
            fromBracket = "Type: Processor\nNo Parameters";
          }
        } else if (label.startsWith("Reference:")) {
          shortLabel = label.substring(label.indexOf(":") + 1).trim();
          graphTarget = shortLabel;
          fromBracket =
            "Type: Reference\nName: " +
            shortLabel +
            "\n" +
            "Double-click to open target graph.";
        } else if (label.startsWith("Repeat(")) {
          shortLabel = "Repeat\n{loop}";
          fromBracket = label;
        } else if (label.startsWith("RepeatS(")) {
          shortLabel = "RepeatS\n{sequential}";
          fromBracket = label;
        } else if (label.startsWith("Iterate(")) {
          shortLabel = "Iterate\n{parallel}";
          fromBracket = label;
        } else if (label.startsWith("IterateS(")) {
          shortLabel = "IterateS\n{sequential}";
          fromBracket = label;
        } else if (label.startsWith("WallTimer:")) {
          shortLabel = "WallTimer";
          fromBracket = label.substring(label.indexOf(":") + 1).trim();
        } else if (label.startsWith("Timer:")) {
          shortLabel = "Timer";
          fromBracket = label.substring(label.indexOf(":") + 1).trim();
        } else if (label.startsWith("Delay:")) {
          shortLabel = "Delay";
          fromBracket = label.substring(label.indexOf(":") + 1).trim();
        } else if (label.startsWith("AnyDelay:")) {
          shortLabel = "AnyDelay";
          fromBracket = label.substring(label.indexOf(":") + 1).trim();
        } else if (bracketPos !== -1) {
          shortLabel = label.substring(0, bracketPos).trim();
          fromBracket = label.substring(bracketPos);
        } else if (label.startsWith("CombineParallel")) {
          shortLabel = "Combine\n{parallel}";
          fromBracket = label;
        } else if (label.startsWith("CombineSequential")) {
          shortLabel = "Combine\n{sequential}";
          fromBracket = label;
        }
        return {
          ...node,
          zIndex: 1,
          data: {
            ...node.data,
            label: shortLabel,
            fullLabel: label,
            isExpandable: true,
            details: fromBracket,
            graphTarget: graphTarget,

            description:
              editMode && graphTarget
                ? activeGraphs[graphTarget]?.description ||
                  node.data.description
                : node.data.description,
          },
          style: nodeStyle,
        };
      }

      return {
        ...node,
        zIndex: 1,
        style: nodeStyle,
      };
    });

  function changeGraph(event) {
    let newGraphName = event.target.value;
    setSelectedGraphName(newGraphName);
    setSelectedDetails(null);
    addToHistory(newGraphName);
    setShowDescriptionEditor(false);
    setShowTermMenu(false);
    setSelectedEditNode(null);
  }

  function nodeHovered(event, node) {
    if (!node.data || node.data.box || isBlockExit(node)) {
      return;
    }
    setHoveredLabel(node.data.fullLabel ?? node.data.label);
    setHoverPosition({ x: event.clientX, y: event.clientY });
  }

  function nodeUnhovered() {
    setHoveredLabel(null);
  }

  function nodeClicked(event, node) {
    if (!node.data || node.data.box || isBlockExit(node)) {
      return;
    }
    if (event.detail === 2) {
      nodeDoubleClicked(event, node);
      return;
    }

    if (node.data.description || node.data.details) {
      setSelectedDetails(node.data);
    }

    if (editMode) {
      setShowTermMenu(false);
      setSelectedEditNode(node.id);
      return;
    }
  }

  function nodeDoubleClicked(event, node) {
    if (!node.data || node.data.box || isBlockExit(node)) {
      return;
    }

    if (node.data.graphTarget && activeGraphs[node.data.graphTarget]) {
      setSelectedGraphName(node.data.graphTarget);
      setShowDescriptionEditor(false);
      setSelectedDetails(null);
      addToHistory(node.data.graphTarget);
      return;
    }

    let label = node.data.label;
    let temp = label;
    let pos = label.indexOf(":");

    if (pos !== -1) {
      temp = label.substring(pos + 1).trim();
    }

    if (activeGraphs && activeGraphs[temp]) {
      setSelectedGraphName(temp);
      setShowDescriptionEditor(false);
      setSelectedDetails(null);
      addToHistory(temp);
    }
  }

  function hasPlaceholders() {
    if (!editableGraphs) return true;

    return Object.values(editableGraphs).some((graph) =>
      graph.nodes.some((node) => node.data?.label === "Select strategy term"),
    );
  }

  // Bestimmt alle Nodes, die zu einer Box gehören
  function getBoxMemberIds(box, nodes) {
    if (box.data.rootId) {
      const result = new Set([box.data.rootId]);

      addBoxDescendants(box.data.rootId, nodes, result);

      return [...result];
    }

    const originalGraph = allGraphs?.[selectedGraphName];
    if (!originalGraph) return [];

    const originalBox = originalGraph.nodes.find((node) => node.id === box.id);
    if (!originalBox) return [];

    const left = originalBox.position.x;
    const top = originalBox.position.y;
    const right = left + originalBox.data.width;
    const bottom = top + originalBox.data.height;

    const originalMemberIds = originalGraph.nodes
      .filter((node) => !node.data?.box && !isBlockExit(node))
      .filter(
        (node) =>
          node.position.x >= left &&
          node.position.x <= right &&
          node.position.y >= top &&
          node.position.y <= bottom,
      )
      .map((node) => node.id);

    const result = new Set(originalMemberIds);

    const boxRootId = originalMemberIds
      .map((id) => originalGraph.nodes.find((n) => n.id === id))
      .filter(Boolean)
      .sort((a, b) => a.position.y - b.position.y)[0]?.id;

    if (boxRootId) {
      addBoxDescendants(boxRootId, nodes, result);
    }

    return [...result];
  }

  // Passt Position und Größe der Boxen an ihre enthaltenen Nodes an
  function updateEditBoxes(nodes) {
    return nodes.map((node) => {
      if (!node.data?.box) return node;

      const memberIds = getBoxMemberIds(node, nodes);
      const members = memberIds
        .map((id) => nodes.find((n) => n.id === id))
        .filter(Boolean);
      if (members.length === 0) return node;

      const padding = 25;
      const minX = Math.min(...members.map((n) => n.position.x));
      const minY = Math.min(...members.map((n) => n.position.y));
      const maxX = Math.max(
        ...members.map((n) => n.position.x + (n.measured?.width ?? 210)),
      );
      const maxY = Math.max(
        ...members.map((n) => n.position.y + (n.measured?.height ?? 125)),
      );
      return {
        ...node,
        position: {
          x: minX - padding,
          y: minY - padding,
        },
        data: {
          ...node.data,
          width: maxX - minX + 2 * padding,
          height: maxY - minY + 2 * padding,
        },
      };
    });
  }

  function addToHistory(graphName) {
    setHistory((oldHistory) => {
      let newHistory = [];
      newHistory.push(graphName);

      for (let oldName of oldHistory) {
        if (oldName !== graphName) {
          newHistory.push(oldName);
        }
      }

      if (newHistory.length > 10) {
        newHistory.pop();
      }

      return newHistory;
    });
  }

  function findUsedIn() {
    let usedIn = [];

    if (!activeGraphs) {
      return usedIn;
    }
    for (let graphName of Object.keys(activeGraphs)) {
      let graph = activeGraphs[graphName];

      for (let node of graph.nodes) {
        if (!node.data || !node.data.label) {
          continue;
        }

        const label = node.data.label;

        if (!label.startsWith("Reference:")) {
          continue;
        }

        const referencedStrategy = label.substring("Reference:".length).trim();

        if (referencedStrategy === selectedGraphName) {
          usedIn.push(graphName);
          break;
        }
      }
    }
    return usedIn;
  }

  let usedInList = findUsedIn();

  const editButtonStyle = {
    position: "absolute",
    bottom: "20px",
    zIndex: 20,
    padding: "10px 16px",
    cursor: "pointer",
  };

  return (
    <div style={{ width: "100vw", height: "100vh", position: "relative" }}>
      {!editMode ? (
        <>
          <button
            style={{ ...editButtonStyle, right: "20px" }}
            className="edit-strategy-button"
            onClick={startEditing}
          >
            {" "}
            Edit Strategy File
          </button>

          <button
            onClick={createNewStrategyFile}
            style={{ ...editButtonStyle, right: "200px" }}
          >
            New Strategy File
          </button>
        </>
      ) : (
        <button
          style={{ ...editButtonStyle, right: "20px" }}
          className="edit-strategy-button"
          onClick={cancelEditing}
        >
          Cancel Editing Strategy File
        </button>
      )}

      <div
        style={{
          position: "absolute",
          top: "12px",
          left: "12px",
          right: "12px",
          zIndex: 10,
          fontSize: "20px",
          backgroundColor: "rgba(255, 255, 255, 0.95)",
          border: "1px solid rgba(0, 0, 0, 0.25)",
          borderRadius: "8px",
          padding: "6px 10px",
          display: "flex",
          alignItems: "center",
          gap: "18px",
          boxShadow: "0 2px 8px rgba(0, 0, 0, 0.08)",
        }}
      >
        <label style={{ display: "flex", alignItems: "center", gap: "6px" }}>
          <span>
            <b>Equation:</b>
          </span>
          <select value={selectedGraphName} onChange={changeGraph}>
            {graphNames.map((name) => (
              <option key={name} value={name}>
                {name}
              </option>
            ))}
          </select>
        </label>

        {!editMode ? (
          <label style={{ display: "flex", alignItems: "center", gap: "6px" }}>
            <span>
              <b>History:</b>
            </span>
            <select value={selectedGraphName} onChange={changeGraph}>
              {history.map((name) => (
                <option key={name} value={name}>
                  {name}
                </option>
              ))}
            </select>
          </label>
        ) : (
          <>
            <select
              defaultValue=""
              disabled={!canAddTerm && !canAddSequence}
              onChange={(event) => {
                const action = event.target.value;

                if (action === "term") addTerm();
                if (action === ":") addSequence(":");
                if (action === ";") addSequence(";");

                event.target.value = "";
              }}
            >
              <option value="" disabled hidden>
                Add
              </option>

              <option value="term" disabled={!canAddTerm}>
                Add Term
              </option>

              <option value=":" disabled={!canAddSequence}>
                Add Sequence :
              </option>

              <option value=";" disabled={!canAddSequence}>
                Add Sequence ;
              </option>
            </select>
            <select
              defaultValue=""
              disabled={
                (!selectedEditNode ||
                  selectedNodeForEdit?.data.label === "Select strategy term") &&
                !selectedNodeForEdit?.data.next
              }
              onChange={(event) => {
                const action = event.target.value;

                if (action === "subtree") deleteSubtree();
                if (action === "sequence") deleteSequence();

                event.target.value = "";
              }}
            >
              <option value="" disabled hidden>
                Delete
              </option>

              <option
                value="subtree"
                disabled={
                  !selectedEditNode ||
                  selectedNodeForEdit?.data.label === "Select strategy term"
                }
              >
                Delete Subtree
              </option>

              <option
                value="sequence"
                disabled={!selectedNodeForEdit?.data.next}
              >
                Delete Sequence
              </option>
            </select>
          </>
        )}

        {!editMode ? (
          <label style={{ display: "flex", alignItems: "center", gap: "6px" }}>
            <input
              type="checkbox"
              checked={showBlockExits}
              onChange={(event) => setShowBlockExits(event.target.checked)}
            />
            <span>
              <b>Show exits</b> (Might increase readability)
            </span>
          </label>
        ) : (
          <button onClick={addStrategy}>New Strategy</button>
        )}

        {!editMode ? (
          <label
            style={{
              display: "flex",
              alignItems: "center",
              gap: "6px",
              marginLeft: "auto",
            }}
          >
            <span>
              <b>Used in: ({usedInList.length})</b>
            </span>
            <select value="" onChange={changeGraph}>
              <option value="" disabled></option>
              {usedInList.map((name) => (
                <option key={name} value={name}>
                  {name}
                </option>
              ))}
            </select>
          </label>
        ) : (
          <>
            <button
              onClick={() => setShowDescriptionEditor(!showDescriptionEditor)}
            >
              {" "}
              Edit Reference Description
            </button>

            {showDescriptionEditor && (
              <textarea
                value={currentGraph.description ?? ""}
                onChange={(e) =>
                  updateCurrentGraph({ description: e.target.value })
                }
                placeholder="Strategy description"
              />
            )}
            <button
              onClick={exportFile}
              disabled={hasPlaceholders()}
              style={{ marginLeft: "auto" }}
            >
              Export File
            </button>

            <button
              onClick={() => {
                const link = document.createElement("a");
                link.href = "/analysis.json";
                link.download = "analysis.json";
                link.click();
              }}
            >
              Download Analysis
            </button>
          </>
        )}
      </div>

      {showTermMenu && ( //PopUp Menü für Prozessoren
        <TermMenu
          declaredProcessors={declaredProcessors}
          processorDefaults={processorDefaults}
          selectedProcessor={selectedProcessor}
          setSelectedProcessor={setSelectedProcessor}
          processorParameters={processorParameters}
          setProcessorParameters={setProcessorParameters}
          activeSubmenu={activeSubmenu}
          setActiveSubmenu={setActiveSubmenu}
          timerValue={timerValue}
          setTimerValue={setTimerValue}
          childCount={childCount}
          setChildCount={setChildCount}
          anyKLimit={anyKLimit}
          setAnyKLimit={setAnyKLimit}
          ifCondition={ifCondition}
          setIfCondition={setIfCondition}
          ifHasElse={ifHasElse}
          setIfHasElse={setIfHasElse}
          newReferenceName={newReferenceName}
          setNewReferenceName={setNewReferenceName}
          repeatMin={repeatMin}
          setRepeatMin={setRepeatMin}
          repeatMax={repeatMax}
          setRepeatMax={setRepeatMax}
          editableGraphs={editableGraphs}
          actions={{
            addProcessor,
            addReference,
            addNewReference,
            addTimer,
            addWallTimer,
            addDelay,
            addAnyDelay,
            addAny,
            addFirst,
            addCombine,
            addCombineParallel,
            addCombineSequential,
            addAnyK,
            addMaybe,
            addSolve,
            addProve,
            addDisprove,
            addRepeat,
            addRepeatS,
            addIf,
            closeAllSubmenus,
            formatProcessorParameters,
            validProcessorParameters,
          }}
        />
      )}

      {showStrategyMenu && (
        <div
          style={{
            position: "absolute",
            top: "70px",
            left: "300px",
            zIndex: 20,
            backgroundColor: "white",
            border: "1px solid #ccc",
            padding: "10px",
          }}
        >
          <b>New Reference</b>

          <input
            value={newStrategyName}
            onChange={(event) => {
              let temp = event.target.value.replace(/[0-9]/g, "_");
              setNewStrategyName(temp.charAt(0).toLowerCase() + temp.slice(1));
            }}
            placeholder="Strategy name"
          />

          <button onClick={createStrategy}>Create</button>

          <button onClick={() => setShowStrategyMenu(false)}>Cancel</button>
        </div>
      )}

      {hoveredLabel && (
        <div
          style={{
            position: "absolute",
            left: hoverPosition.x + 12,
            top: hoverPosition.y + 12,
            zIndex: 20,
            backgroundColor: "white",
            border: "1px solid rgba(0,0,0,0.2)",
            borderRadius: "6px",
            padding: "6px 8px",
            fontSize: "25px",
            maxWidth: "360px",
            whiteSpace: "pre-wrap",
          }}
        >
          {hoveredLabel}
        </div>
      )}

      <DetailsPanel selectedDetails={selectedDetails} />

      <Legend />

      <ReactFlow
        key={selectedGraphName}
        nodes={displayNodes}
        edges={visibleEdges}
        edgeTypes={edgeTypes}
        onPaneClick={() => setSelectedDetails(null)}
        nodeTypes={nodeTypes}
        proOptions={{ hideAttribution: true }}
        onNodeClick={nodeClicked}
        onNodeMouseEnter={nodeHovered}
        onNodeMouseLeave={nodeUnhovered}
        minZoom={0.2}
        maxZoom={2}
        nodesDraggable={editMode}
        onNodesChange={handleEditNodesChange}
        onNodeDragStop={handleNodeDragStop}
        fitView
      />
    </div>
  );

  function createNewStrategyFile() {
    setEditableGraphs({
      main: createEmptyGraph(),
    });

    setSelectedGraphName("main");
    setSelectedEditNode(null);
    setSelectedDetails(null);
    setShowDescriptionEditor(false);
    setShowBlockExits(false);
    setHistory(["main"]);
    setEditMode(true);
  }

  function startEditing() {
    const graphs = structuredClone(allGraphs);

    for (const name of Object.keys(graphs)) {
      graphs[name] = addMissingBoxRootIds(graphs[name]);
    }

    setEditableGraphs(graphs);
    setEditMode(true);
    setShowBlockExits(false);
  }

  // Exportiert die bearbeiteten Graphen als Strategy-Datei
  //This method "exportFile" was developed with AI assistance; final logic was reviewed manually
  async function exportFile() {
    const cleanGraphs = Object.fromEntries(
      Object.entries(editableGraphs).map(([name, graph]) => [
        name,
        {
          description: graph.description ?? "",
          root: graph.root,
          nodes: graph.nodes.map((node) => ({
            id: node.id,
            position: node.position,
            data: { ...node.data, rootId: undefined },
          })),

          edges: graph.edges.map((edge) => ({
            id: edge.id,
            source: edge.source,
            target: edge.target,
            sequenceOperator:
              edge.sequenceOperator ?? edge.data?.sequenceOperator ?? null,
            label: edge.label ?? null,
          })),
        },
      ]),
    );

    let response = await fetch("http://localhost:8080/export", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(cleanGraphs),
    });

    if (!response.ok) {
      throw new Error("Export failed: " + response.status);
    }

    let strategyText = await response.text();
    let blob = new Blob([strategyText], { type: "text/plain" });
    let url = URL.createObjectURL(blob);
    let link = document.createElement("a");

    link.href = url;
    link.download = "edited.strategy";
    link.click();

    URL.revokeObjectURL(url);
  }

  function addTerm() {
    if (!selectedEditNode) {
      return;
    }
    closeAllSubmenus();

    setShowTermMenu(true);

    setTimerValue("");
    setChildCount("");
    setAnyKLimit("");
    setRepeatMin("");
    setRepeatMax("");
    setIfCondition("");
    setIfHasElse(false);
  }

  function createEmptyGraph() {
    const placeholder = createPlaceholder("Placeholder-0", 0, 0);
    return {
      description: "",
      root: "Placeholder-0",
      nodes: [placeholder],
      edges: [],
    };
  }

  function deleteSequence() {
    if (!selectedEditNode) return;

    const graph = editableGraphs[selectedGraphName];
    const selectedNode = graph.nodes.find(
      (node) => node.id === selectedEditNode,
    );

    if (!selectedNode?.data.next) return;

    const nextId = selectedNode.data.next;
    const deletedIds = new Set([nextId]);

    // Gesamte Sequenz ab nextId löschen
    addBoxDescendants(nextId, graph.nodes, deletedIds, true);

    // Boxen bestimmen, die komplett in der gelöschten Sequenz liegen
    const boxIds = new Set(
      graph.nodes
        .filter((node) => node.data?.box)
        .filter((box) => {
          const members = getBoxMemberIds(box, graph.nodes);

          return (
            members.length > 0 && members.every((id) => deletedIds.has(id))
          );
        })
        .map((box) => box.id),
    );

    const updatedNodes = graph.nodes
      .filter((node) => !deletedIds.has(node.id) && !boxIds.has(node.id))
      .map((node) =>
        node.id === selectedEditNode
          ? {
              ...node,
              data: {
                ...node.data,
                next: null,
                sequenceOperator: null,
              },
            }
          : node,
      );

    const updatedEdges = graph.edges.filter(
      (edge) => !deletedIds.has(edge.source) && !deletedIds.has(edge.target),
    );

    updateCurrentGraph({
      nodes: updatedNodes,
      edges: updatedEdges,
    });
  }

  function updateCurrentGraph(changes) {
    setEditableGraphs((graphs) => ({
      ...graphs,
      [selectedGraphName]: {
        ...graphs[selectedGraphName],
        ...changes,
      },
    }));
  }

  function addProcessor(processorName, parameters) {
    if (!validProcessorParameters(parameters)) {
      return;
    }

    let label = "Processor: " + processorName;

    if (parameters.trim()) {
      label += "\n" + parameters.trim();
    }
    let graph = editableGraphs[selectedGraphName];

    let updatedNodes = graph.nodes.map((node) => {
      if (node.id !== selectedEditNode) {
        return node;
      }

      return {
        ...node,
        data: {
          ...node.data,
          label: label,
          description:
            "Processor call. Succeeds if the processor transforms a basic obligation into a new obligation.",
          children: [],
          box: false,
        },
      };
    });

    updateCurrentGraph({ nodes: updatedNodes });

    setSelectedEditNode(null);
    setShowTermMenu(false);
    setSelectedProcessor(null);
    setProcessorParameters("");
  }

  function addStrategy() {
    setShowStrategyMenu(true);
  }

  function createStrategy() {
    if (!newStrategyName) {
      return;
    }

    if (editableGraphs[newStrategyName]) {
      return;
    }

    const newGraph = createEmptyGraph();

    setEditableGraphs({
      ...editableGraphs,
      [newStrategyName]: newGraph,
    });

    setSelectedGraphName(newStrategyName);
    setShowDescriptionEditor(false);
    setNewStrategyName("");
    setShowStrategyMenu(false);
  }

  function addReference(strategyName) {
    let graph = editableGraphs[selectedGraphName];

    let updatedNodes = createReferenceNodes(graph, strategyName);

    updateCurrentGraph({ nodes: updatedNodes });

    setSelectedEditNode(null);
    setShowTermMenu(false);
  }

  function createReferenceNodes(graph, strategyName) {
    return graph.nodes.map((node) => {
      if (node.id !== selectedEditNode) {
        return node;
      }

      return {
        ...node,
        data: {
          ...node.data,
          label: "Reference: " + strategyName,
          description:
            "Variable which refers to another defined strategy term.",
          children: [],
          box: false,
        },
      };
    });
  }

  function addNewReference() {
    if (!newReferenceName || !selectedEditNode) {
      return;
    }

    if (editableGraphs[newReferenceName]) {
      return;
    }

    let graph = editableGraphs[selectedGraphName];
    let updatedNodes = createReferenceNodes(graph, newReferenceName);
    let newGraph = createEmptyGraph();

    setEditableGraphs({
      ...editableGraphs,

      [selectedGraphName]: {
        ...graph,
        nodes: updatedNodes,
      },

      [newReferenceName]: newGraph,
    });

    setNewReferenceName("");
    setSelectedEditNode(null);
    setShowTermMenu(false);
    closeAllSubmenus();
  }

  function cancelEditing() {
    setEditableGraphs(null);
    setShowDescriptionEditor(false);
    setEditMode(false);
    setSelectedEditNode(null);
    setShowTermMenu(false);
    setShowStrategyMenu(false);

    if (!allGraphs[selectedGraphName]) {
      setSelectedGraphName("main");
    }
  }

  // Ersetzt den ausgewählten Teilbaum durch einen neuen Placeholder
  function deleteSubtree() {
    if (!selectedEditNode) return;

    const graph = editableGraphs[selectedGraphName];

    const selectedNode = graph.nodes.find(
      (node) => node.id === selectedEditNode,
    );

    if (!selectedNode) return;

    const descendantIds = new Set();

    addBoxDescendants(selectedEditNode, graph.nodes, descendantIds, false);
    const placeholder = createPlaceholder(
      selectedNode.id,
      selectedNode.position.x,
      selectedNode.position.y,
    );

    const subtreeIds = new Set([selectedEditNode, ...descendantIds]);

    const externalEdges = graph.edges.filter(
      (edge) => subtreeIds.has(edge.source) && !subtreeIds.has(edge.target),
    );

    placeholder.data.next = selectedNode.data.next;
    placeholder.data.sequenceOperator = selectedNode.data.sequenceOperator;
    const boxIds = new Set(
      graph.nodes
        .filter((node) => node.data?.box)
        .filter((box) => {
          const members = getBoxMemberIds(box, graph.nodes);

          return (
            members.length > 0 &&
            members.every(
              (id) => id === selectedEditNode || descendantIds.has(id),
            )
          );
        })
        .map((box) => box.id),
    );
    const updatedNodes = graph.nodes
      .filter((node) => !descendantIds.has(node.id) && !boxIds.has(node.id))
      .map((node) => (node.id === selectedEditNode ? placeholder : node));
    let updatedEdges = graph.edges.filter(
      (edge) =>
        edge.source !== selectedEditNode &&
        !descendantIds.has(edge.source) &&
        !descendantIds.has(edge.target),
    );

    for (const edge of externalEdges) {
      if (
        !updatedEdges.some(
          (existing) =>
            existing.source === selectedEditNode &&
            existing.target === edge.target,
        )
      ) {
        updatedEdges.push({
          ...edge,
          id: selectedEditNode + "-to-" + edge.target,
          source: selectedEditNode,
        });
      }
    }

    updateCurrentGraph({
      nodes: updatedNodes,
      edges: updatedEdges,
    });

    addTerm();
  }

  function handleNodeDragStop() {
    updateCurrentGraph({
      nodes: editNodes,
    });
  }

  //Verarbeitet verschobene Nodes und verschiebt Box-Inhalte mit
  function handleEditNodesChange(changes) {
    if (!editMode) return;

    setEditNodes((nodes) => {
      const updatedNodes = applyNodeChanges(changes, nodes);

      const boxChange = changes.find(
        (change) =>
          change.type === "position" &&
          change.position &&
          nodes.find((node) => node.id === change.id)?.data?.box,
      );

      if (!boxChange) return updatedNodes;

      const box = nodes.find((node) => node.id === boxChange.id);
      const dx = boxChange.position.x - box.position.x;
      const dy = boxChange.position.y - box.position.y;
      const members = new Set(getBoxMemberIds(box, nodes));

      return updatedNodes.map((node) =>
        members.has(node.id)
          ? {
              ...node,
              position: {
                x: node.position.x + dx,
                y: node.position.y + dy,
              },
            }
          : node,
      );
    });
  }
  //Ersetzt einen Placeholder mit einem Konstrukt mit einem Kind
  function addSingleChildConstruct(label, description, parameter = "") {
    if (!editableGraphs || !selectedEditNode) {
      return;
    }

    let graph = editableGraphs[selectedGraphName];

    if (hasSequencedAncestor(selectedEditNode, graph)) {
      return;
    }

    let selectedNode = graph.nodes.find((node) => node.id === selectedEditNode);

    if (!selectedNode) {
      return;
    }

    let nodeLabel = label;

    if (parameter !== "") {
      nodeLabel = label + ": " + parameter;
    }

    let childId = "Placeholder-" + Date.now();

    let childNode = createPlaceholder(
      childId,
      selectedNode.position.x + X_GAP,
      selectedNode.position.y + Y_GAP,
    );

    let updatedNodes = graph.nodes.map((node) => {
      if (node.id !== selectedEditNode) {
        return node;
      }

      return {
        ...node,
        data: {
          ...node.data,
          label: nodeLabel,
          description: description,
          children: [childId],
        },
      };
    });

    updatedNodes.push(childNode);

    updatedNodes.push(
      createBox(
        selectedEditNode,
        nodeLabel,
        selectedNode.position.x,
        selectedNode.position.y,
      ),
    );

    let newEdge = {
      id: selectedEditNode + "-to-" + childId,
      source: selectedEditNode,
      target: childId,
    };

    updateCurrentGraph({
      nodes: updatedNodes,
      edges: [...graph.edges, newEdge],
    });
    setSelectedEditNode(null);
    setShowTermMenu(false);
  }
  //Ersetzt einen Placeholder mit einem Konstrukt mit mehreren Kindern
  function addMultiChildConstruct(
    label,
    description,
    childCount,
    parameter = "",
  ) {
    if (!editableGraphs || !selectedEditNode) {
      return;
    }
    if (childCount < 2 || childCount > 20) {
      return;
    }

    let graph = editableGraphs[selectedGraphName];

    if (hasSequencedAncestor(selectedEditNode, graph)) {
      return;
    }

    let selectedNode = graph.nodes.find((node) => node.id === selectedEditNode);

    if (!selectedNode || selectedNode.data.label !== "Select strategy term") {
      return;
    }

    let nodeLabel = label;

    if (parameter !== "") {
      nodeLabel = label + ": " + parameter;
    }

    let time = Date.now();
    let childIds = [];
    let newChildren = [];
    let newEdges = [];

    for (let i = 0; i < childCount; i++) {
      let childId = "Placeholder-" + (time + i);

      childIds.push(childId);

      newChildren.push(
        createPlaceholder(
          childId,
          selectedNode.position.x + (i - (childCount - 1) / 2) * X_GAP,
          selectedNode.position.y + Y_GAP,
        ),
      );

      let edge = {
        id: selectedEditNode + "-to-" + childId,
        source: selectedEditNode,
        target: childId,
      };

      if (label === "First" || label === "CombineSequential") {
        edge.label = String(i + 1);
      }

      newEdges.push(edge);
    }

    let updatedNodes = graph.nodes.map((node) => {
      if (node.id !== selectedEditNode) {
        return node;
      }

      return {
        ...node,
        data: {
          ...node.data,
          label: nodeLabel,
          description: description,
          children: childIds,
        },
      };
    });

    updatedNodes.push(...newChildren);
    updatedNodes.push(
      createBox(
        selectedEditNode,
        nodeLabel,
        selectedNode.position.x,
        selectedNode.position.y,
      ),
    );

    updateCurrentGraph({
      nodes: updatedNodes,
      edges: [...graph.edges, ...newEdges],
    });
    setSelectedEditNode(null);
    setShowTermMenu(false);
  }

  function addMaybe() {
    addSingleChildConstruct(
      "Maybe",
      "Applies the strategy at most once and succeeds even if it fails.",
    );
  }

  function addTimer() {
    if (timerValue === "") {
      return;
    }
    let time = Number(timerValue);
    if (time <= 0 || time >= 10000) {
      //Falls Wert unpassend
      time = 200;
    }

    addSingleChildConstruct(
      "Timer",
      "Executes the strategy in the box with a time limit of " +
        time +
        "ms. Fails if the timeout is reached.",
      time,
    );
    setTimerValue("");
  }

  function addWallTimer() {
    if (timerValue === "") {
      return;
    }

    let time = Number(timerValue);
    if (time <= 0 || time >= 10000) {
      //Falls Wert unpassend
      time = 200;
    }

    addSingleChildConstruct(
      "WallTimer",
      "Executes the strategy in the box with a wall-clock time limit of " +
        time +
        "ms. Fails if the timeout is reached.",
      time,
    );
    setTimerValue("");
  }

  function addSolve() {
    addSingleChildConstruct(
      "Solve",
      "Evaluates strategy S and succeeds if the resulting truth value is known.",
    );
  }

  function addProve() {
    addSingleChildConstruct(
      "Prove",
      "Attempts to prove the current obligation using the strategy in the box.",
    );
  }

  function addDisprove() {
    addSingleChildConstruct(
      "Disprove",
      "Attempts to Disprove the current obligation using the strategy in the box.",
    );
  }

  function addFirst(count) {
    addMultiChildConstruct(
      "First",
      "Evaluates strategies in the given order. Fails if none succeeds.",
      count,
    );
  }

  function addAny(count) {
    addMultiChildConstruct(
      "Any",
      "Evaluates strategies in arbitrary order, possibly in parallel. Fails if none succeeds.",
      count,
    );
  }
  function addAnyK(count, maximum) {
    addMultiChildConstruct(
      "AnyK",
      "Evaluates multiple strategies in arbitrary order, with at most " +
        maximum +
        " strategies in parallel. Succeeds if one strategy succeeds.",
      count,
      maximum,
    );
  }

  function addCombine(count) {
    addMultiChildConstruct(
      "Combine",
      "Combines multiple strategies into one strategy.",
      count,
    );
  }

  function addCombineParallel(count) {
    addMultiChildConstruct(
      "CombineParallel",
      "Combines multiple strategies and executes them in parallel.",
      count,
    );
  }

  function addCombineSequential(count) {
    addMultiChildConstruct(
      "CombineSequential",
      "Combines multiple strategies and executes them sequentially.",
      count,
    );
  }

  function addDelay() {
    if (timerValue === "") {
      return;
    }

    let time = Number(timerValue);
    if (time <= 0 || time >= 10000) {
      //Falls Wert unpassend
      time = 200;
    }
    addSingleChildConstruct(
      "Delay",
      "Executes the strategy in the box after a delay of " + time + " ms.",
      time,
    );
    setTimerValue("");
  }

  function addAnyDelay(count) {
    if (timerValue === "") {
      return;
    }

    let time = Number(timerValue);
    if (time <= 0 || time >= 10000) {
      //Falls Wert unpassend
      time = 200;
    }
    addMultiChildConstruct(
      "AnyDelay",
      "Starts the first strategy immediately and the remaining strategies after " +
        time +
        " ms. Succeeds as soon as one strategy succeeds.",
      count,
      time,
    );
    setTimerValue("");
  }

  function addRepeat(type, min, max) {
    let label = type + "(" + min + "," + max + ")";

    addSingleChildConstruct(
      label,
      "Repeats the strategy in the box as often as possible, at least " +
        min +
        " and at most " +
        max +
        " times. The order in which results are processed is not specified.",
    );
  }

  function addRepeatS(type, min, max) {
    let label = type + "(" + min + "," + max + ")";

    addSingleChildConstruct(
      label,
      "Repeats the strategy in the box as often as possible, at least " +
        min +
        " and at most " +
        max +
        " times in sequential order.",
    );
  }

  function addIf(condition, hasElse) {
    let graph = editableGraphs[selectedGraphName];
    if (hasSequencedAncestor(selectedEditNode, graph)) {
      return;
    }

    let selectedNode = graph.nodes.find((node) => node.id === selectedEditNode);

    if (!selectedNode || selectedNode.data.label !== "Select strategy term") {
      return;
    }

    let time = Date.now();
    let trueId = "Placeholder-" + time;
    let falseId = "Placeholder-" + (time + 1);

    let trueNode = createPlaceholder(
      trueId,
      selectedNode.position.x + (hasElse ? -X_GAP : 0),
      selectedNode.position.y + Y_GAP,
    );
    let newNodes = [trueNode];
    let newEdges = [
      {
        id: selectedEditNode + "-true-" + trueId,
        source: selectedEditNode,
        target: trueId,
        label: "true",
      },
    ];

    if (hasElse) {
      let falseNode = createPlaceholder(
        falseId,
        selectedNode.position.x + X_GAP,
        selectedNode.position.y + Y_GAP,
      );

      newNodes.push(falseNode);
      newEdges.push({
        id: selectedEditNode + "-false-" + falseId,
        source: selectedEditNode,
        target: falseId,
        label: "false",
      });
    }
    let updatedNodes = graph.nodes.map((node) => {
      if (node.id !== selectedEditNode) {
        return node;
      }

      return {
        ...node,
        data: {
          ...node.data,
          label: "Condition" + condition.trim(),
          description:
            "Evaluates a Condition and continues with the corresponding branch.",
          children: hasElse ? [trueId, falseId] : [trueId],
        },
      };
    });

    updatedNodes.push(...newNodes);
    updatedNodes.push(
      createBox(
        selectedEditNode,
        "If",
        selectedNode.position.x,
        selectedNode.position.y,
      ),
    );

    updateCurrentGraph({
      nodes: updatedNodes,
      edges: [...graph.edges, ...newEdges],
    });
    setSelectedEditNode(null);
    setShowTermMenu(false);
    closeAllSubmenus();
  }

  function addSequence(operator) {
    if (!editableGraphs || !selectedEditNode) {
      return;
    }

    let graph = editableGraphs[selectedGraphName];
    let selectedNode = graph.nodes.find((node) => node.id === selectedEditNode);

    if (hasSequencedAncestor(selectedEditNode, graph)) {
      return;
    }

    if (!selectedNode || selectedNode.data.next) {
      return;
    }

    let exits = getExits(selectedEditNode, graph);
    let nextId = "Placeholder-" + Date.now();
    let maxY = Math.max(
      ...graph.nodes
        .filter(
          (node) =>
            !node.data?.box && containsNode(selectedEditNode, node.id, graph),
        )
        .map((node) => node.position.y),
    );

    let nextNode = createPlaceholder(
      nextId,
      selectedNode.position.x,
      maxY + Y_GAP,
    );
    let updatedNodes = graph.nodes.map((node) => {
      if (node.id !== selectedEditNode) {
        return node;
      }

      return {
        ...node,
        data: {
          ...node.data,
          next: nextId,
          sequenceOperator: operator,
        },
      };
    });

    updatedNodes.push(nextNode);

    let updatedEdges = [...graph.edges];

    for (let exitId of exits) {
      let exitNode = graph.nodes.find((node) => node.id === exitId);

      let isFalseExit =
        exitNode?.data.label.startsWith("Condition[") &&
        exitNode.data.children?.length === 1;

      updatedEdges.push({
        id: exitId + "-to-" + nextId,
        source: exitId,
        target: nextId,
        label: isFalseExit ? "false" : undefined,
        sequenceOperator: operator,
        data: {
          sequenceOperator: operator,
        },
      });
    }

    updateCurrentGraph({ nodes: updatedNodes, edges: updatedEdges });
  }
}
