import { isBlockExit } from "./GraphFunctions.js";

export function createPlaceholder(id, x, y) {
  return {
    id,
    position: { x, y },
    data: {
      label: "Select strategy term",
      width: 0,
      height: 0,
      description: "",
      children: [],
      next: null,
      sequenceOperator: null,
      box: false,
    },
  };
}

export function createBox(rootId, label, x, y) {
  return {
    id: "Box-" + Date.now() + "-" + rootId,
    position: { x: x - 25, y: y - 25 },
    data: {
      label,
      box: true,
      rootId,
      width: 250,
      height: 150,
      children: [],
      next: null,
    },
  };
}
// Sammelt alle Nachfolger eines Nodes
export function addBoxDescendants(
  rootId,
  nodes,
  result,
  followRootNext = false,
) {
  const visited = new Set();

  function collect(nodeId, followNext = true) {
    if (visited.has(nodeId)) return;
    visited.add(nodeId);

    const node = nodes.find((n) => n.id === nodeId);
    if (!node) return;

    for (const childId of node.data.children || []) {
      result.add(childId);
      collect(childId, true);
    }

    if (followNext && node.data.next) {
      result.add(node.data.next);
      collect(node.data.next, true);
    }
  }

  collect(rootId, followRootNext);
}

// Ergänzt bei bereits geladenen Boxen die rootID (fehlt sonst)
export function addMissingBoxRootIds(graph) {
  const nodes = graph.nodes.map((node) => {
    if (!node.data?.box || node.data.rootId) return node;

    const left = node.position.x;
    const top = node.position.y;
    const right = left + node.data.width;
    const bottom = top + node.data.height;

    const root = graph.nodes
      .filter((n) => !n.data?.box && !isBlockExit(n))
      .filter(
        (n) =>
          n.position.x >= left &&
          n.position.x <= right &&
          n.position.y >= top &&
          n.position.y <= bottom,
      )
      .sort((a, b) => a.position.y - b.position.y)[0];

    return {
      ...node,
      data: { ...node.data, rootId: root?.id },
    };
  });

  return { ...graph, nodes };
}
