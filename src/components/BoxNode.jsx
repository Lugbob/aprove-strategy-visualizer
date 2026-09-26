export default function BoxNode({ data }) {
  return (
    <div
      style={{
        width: "100%",
        height: "100%",
        pointerEvents: "none",
      }}
    >
      <div
        className="box-drag-handle"
        style={{
          pointerEvents: "auto",
          cursor: "move",
          padding: "8px 10px",
          fontSize: "16px",
          fontWeight: "600",
          color: "rgba(30, 41, 59, 0.45)",
        }}
      >
        {data.label}
      </div>
    </div>
  );
}
