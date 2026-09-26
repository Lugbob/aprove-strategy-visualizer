import { Handle, Position } from "@xyflow/react";

export default function ConditionNode({ data }) {
  return (
    <div
      style={{
        width: 130,
        height: 130,
        position: "relative",
      }}
    >
      <Handle type="target" position={Position.Top} style={{ opacity: 0 }} />
      <Handle type="source" position={Position.Left} style={{ opacity: 0 }} />
      <Handle type="source" position={Position.Right} style={{ opacity: 0 }} />
      <Handle type="source" position={Position.Bottom} style={{ opacity: 0 }} />

      <div
        style={{
          width: "100%",
          height: "100%",
          backgroundColor: "white",
          border: "2px solid #111827",
          borderLeft: "6px solid #f59e0b",
          transform: "rotate(45deg)",
          boxSizing: "border-box",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <div
          style={{
            transform: "rotate(-45deg)",
            fontSize: "26px",
            fontWeight: 500,
            textAlign: "center",
          }}
        >
          {data.label}
        </div>
      </div>
    </div>
  );
}
