//Prüft, ob ein Node ein exit ist
export function isBlockExit(node) {
  return (
    node?.type === "BlockExit" ||
    node?.id?.startsWith("BlockExit") ||
    node?.data?.label?.trim() === ""
  );
}

//Prüft ob :, ; oder nichts der Sequenzoperator ist
export function getSequenceOperator(edge) {
  return edge.sequenceOperator ?? edge.data?.sequenceOperator ?? null;
}

// Prüft rekursiv, ob searchedId im Teilbaum ab startId enthalten ist
export function containsNode(startId, searchedId, graph) {
  if (startId === searchedId) {
    return true;
  }

  let node = graph.nodes.find((node) => node.id === startId);

  if (!node) {
    return false;
  }

  for (let childId of node.data.children || []) {
    if (containsNode(childId, searchedId, graph)) {
      return true;
    }
  }

  if (node.data.next) {
    if (containsNode(node.data.next, searchedId, graph)) {
      return true;
    }
  }

  return false;
}

// Bestimmt alle Ausgangs-Nodes eines Teilbaums
export function getExits(nodeId, graph) {
  let node = graph.nodes.find((node) => node.id === nodeId);

  if (!node) {
    return [];
  }

  if (node.data.next) {
    return getExits(node.data.next, graph);
  }

  if (
    node.data.label.startsWith("Repeat(") ||
    node.data.label.startsWith("RepeatS(")
  ) {
    return [nodeId];
  }

  let children = node.data.children || [];

  if (children.length === 0) {
    return [nodeId];
  }

  let exits = [];

  for (let childId of children) {
    let childExits = getExits(childId, graph);

    for (let exitId of childExits) {
      exits.push(exitId);
    }
  }

  if (node.data.label.startsWith("Condition[") && children.length === 1) {
    exits.push(nodeId);
  }

  if (node.data.label.startsWith("Maybe")) {
    exits.push(nodeId);
  }

  return [...new Set(exits)];
}

// Erzeugt die Rückkanten von Repeat-Konstrukten zu ihrem Startknoten (und löscht alte)
export function getRepeatLoopEdges(graph) {
  let loopEdges = [];

  for (let node of graph.nodes) {
    if (
      !node.data.label.startsWith("Repeat(") &&
      !node.data.label.startsWith("RepeatS(")
    ) {
      continue;
    }

    let childId = node.data.children?.[0];
    if (!childId) continue;
    let exits = getExits(childId, graph);

    for (let exitId of exits) {
      loopEdges.push({
        id: "repeat-loop-" + exitId + "-to-" + node.id,
        source: exitId,
        target: node.id,
      });
    }
  }
  return loopEdges;
}

//Für jeden Knoten mit Sequenz: Hat er ein Kind mit nodeId?  Dann kann er keine Sequenzen mehr erstellen
export function hasSequencedAncestor(nodeId, graph) {
  for (let node of graph.nodes) {
    if (!node.data.next) {
      continue;
    }

    for (let childId of node.data.children || []) {
      if (containsNode(childId, nodeId, graph)) {
        return true;
      }
    }
  }

  return false;
}
