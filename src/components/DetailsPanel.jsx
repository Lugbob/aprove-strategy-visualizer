export default function DetailsPanel({ selectedDetails }) {
  if (!selectedDetails) return null;
  return (
    <div
      style={{
        position: "absolute",
        top: "80px",
        left: "12px",
        zIndex: 10,
        backgroundColor: "rgba(255, 255, 255, 0.95)",
        border: "1px solid rgba(0, 0, 0, 0.2)",
        borderRadius: "6px",
        padding: "8px 10px",
        maxWidth: "360px",
        fontSize: "17px",
        whiteSpace: "pre-wrap",
        overflowWrap: "anywhere",
        wordBreak: "break-word",
      }}
    >
      {selectedDetails.description && (
        <>
          <b>Description:</b>
          <br />
          {selectedDetails.description}
          <br />
          <br />
        </>
      )}

      {selectedDetails.details && (
        <>
          <b>Details:</b>
          <br />
          {selectedDetails.details}
        </>
      )}
    </div>
  );
}
