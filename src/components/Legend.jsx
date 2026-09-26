export default function Legend() {
  return (
    <div
      style={{
        //Legende
        position: "absolute",
        left: "20px",
        bottom: "20px",
        zIndex: 10,
        backgroundColor: "rgba(255, 255, 255, 0.9)",
        border: "1px solid rgba(0, 0, 0, 0.25)",
        borderRadius: "6px",
        padding: "12px 18px",
        fontSize: "14px",
        lineHeight: "1.7",
        display: "flex",
        alignItems: "center",
        gap: "26px",
      }}
    >
      <div
        style={{
          display: "flex",
          alignItems: "center",
          gap: "14px",
        }}
      >
        <span style={{ fontWeight: "700" }}>Node Types</span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "10px",
              height: "10px",
              backgroundColor: "#f59e0b",
              marginRight: "6px",
            }}
          />
          Strategy Terms
        </span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "10px",
              height: "10px",
              backgroundColor: "#3b82f6",
              marginRight: "6px",
            }}
          />
          Processor
        </span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "10px",
              height: "10px",
              backgroundColor: "#c026d3",
              marginRight: "6px",
            }}
          />
          Reference
        </span>
      </div>

      <div
        style={{
          width: "1px",
          height: "28px",
          backgroundColor: "rgba(0,0,0,0.2)",
        }}
      />

      <div
        style={{
          display: "flex",
          alignItems: "center",
          gap: "14px",
        }}
      >
        <span style={{ fontWeight: "700" }}>Edge Types</span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "26px",
              borderTop: "2px solid #94a3b8",
              marginRight: "6px",
              verticalAlign: "middle",
            }}
          />
          Normal edge
        </span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "26px",
              borderTop: "2px solid #60a5fa",
              marginRight: "6px",
              verticalAlign: "middle",
            }}
          />
          Sequence ":"
        </span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "26px",
              borderTop: "2px dashed #86efac",
              marginRight: "6px",
              verticalAlign: "middle",
            }}
          />
          Sequence ";"
        </span>

        <span>
          <span
            style={{
              display: "inline-block",
              width: "26px",
              borderTop: "2px solid #f87171",
              marginRight: "6px",
              verticalAlign: "middle",
            }}
          />
          Exit edge
        </span>
      </div>
    </div>
  );
}
