export function validProcessorParameters(parameters) {
  const text = parameters.trim();

  if (text === "") return true;

  if (!text.startsWith("[") || !text.endsWith("]")) {
    return false;
  }

  let depth = 0;

  for (let i = 0; i < text.length; i++) {
    const char = text[i];

    if (char === "[") depth++;
    if (char === "]") depth--;

    if (depth < 0) return false;

    // Äußerer Parameterblock darf erst am Ende geschlossen werden
    if (depth === 0 && i < text.length - 1) {
      return false;
    }
  }

  return depth === 0;
}

export function formatProcessorParameters(parameters) {
  let result = "";
  let depth = 0;

  for (let char of parameters.replace(/\s/g, "")) {
    if (char === "[") {
      depth++;
      result += "[\n" + "  ".repeat(depth);
    } else if (char === ",") {
      result += ",\n" + "  ".repeat(depth);
    } else if (char === "]") {
      depth--;
      result += "\n" + "  ".repeat(depth) + "]";
    } else {
      result += char;
    }
  }

  return result;
}
