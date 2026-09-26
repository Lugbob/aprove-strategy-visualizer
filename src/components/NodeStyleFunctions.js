export function getBoxColor(label) {
  if (label.includes("Repeat") || label.includes("Iterate")) {
    return {
      backgroundColor: "rgba(34, 197, 94, 0.08)",
      border: "1px solid rgba(34, 197, 94, 0.35)",
    };
  }

  if (label.includes("First")) {
    return {
      backgroundColor: "rgba(59, 130, 246, 0.08)",
      border: "1px solid rgba(59, 130, 246, 0.35)",
    };
  }

  if (label.includes("Solve")) {
    return {
      backgroundColor: "rgba(100, 116, 139, 0.07)",
      border: "1px solid rgba(100, 116, 139, 0.28)",
    };
  }

  if (label.includes("Maybe")) {
    return {
      backgroundColor: "rgba(168, 85, 247, 0.07)",
      border: "1px solid rgba(168, 85, 247, 0.30)",
    };
  }

  if (label.includes("Timer")) {
    return {
      backgroundColor: "rgba(245, 158, 11, 0.08)",
      border: "1px solid rgba(245, 158, 11, 0.35)",
    };
  }

  if (label.includes("If")) {
    return {
      backgroundColor: "rgba(249, 115, 22, 0.08)",
      border: "1px solid rgba(249, 115, 22, 0.35)",
    };
  }

  return {
    backgroundColor: "rgba(148, 163, 184, 0.06)",
    border: "1px solid rgba(148, 163, 184, 0.25)",
  };
}

export function getNodeTypeStyle(label) {
  if (
    label.startsWith("Repeat(") ||
    label.startsWith("RepeatS(") ||
    label.startsWith("Iterate") ||
    label.startsWith("IterateS") ||
    label.startsWith("If") ||
    label.startsWith("IfElse") ||
    label.startsWith("First") ||
    label.startsWith("Maybe") ||
    label.startsWith("Solve") ||
    label.startsWith("Timer") ||
    label.startsWith("WallTimer") ||
    label.startsWith("Delay") ||
    label.startsWith("AnyDelay") ||
    label.startsWith("Any") ||
    label.startsWith("AnyK") ||
    label.startsWith("Combine") ||
    label.startsWith("CombineSequential") ||
    label.startsWith("CombineParallel") ||
    label.startsWith("Condition") ||
    label.startsWith("Prove") ||
    label.startsWith("Disprove")
  ) {
    return {
      border: "1px solid rgba(245, 158, 11, 0.65)",
      borderTop: "5px solid #f59e0b",
      backgroundColor: "white",
      borderRadius: "6px",
    };
  }

  if (label.startsWith("Processor:")) {
    return {
      border: "1px solid #3b82f6",
      borderLeft: "6px solid #3b82f6",
      backgroundColor: "white",
      borderRadius: "6px",
    };
  }

  if (label.startsWith("Reference:")) {
    return {
      border: "3px dashed #c026d3",
      backgroundColor: "white",
      borderRadius: "14px",
    };
  }
  return {};
}
