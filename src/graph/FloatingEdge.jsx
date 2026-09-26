// This class was developed with AI assistance; final logic was reviewed manually.

import {
  BaseEdge,
  getStraightPath,
  useInternalNode,
  EdgeLabelRenderer,
} from "@xyflow/react";

const POINTS_PER_SIDE = 3;
const EPS = 0.000001; //Toleranz für gleiche Abstände

function getBox(node) {
  const position = node.internals.positionAbsolute;

  return {
    x: position.x,
    y: position.y,
    width: node.measured?.width ?? node.width ?? node.data?.width ?? 150,
    height: node.measured?.height ?? node.height ?? node.data?.height ?? 40,
  };
}

function addPoint(points, x, y, middleScore) {
  points.push({ x, y, middleScore });
}

function getConnectionPoints(box) {
  const points = [];

  for (let i = 1; i <= POINTS_PER_SIDE; i++) {
    const t = i / (POINTS_PER_SIDE + 1);
    const middleScore = Math.abs(t - 0.5);

    const x = box.x + box.width * t;
    const y = box.y + box.height * t;

    addPoint(points, x, box.y, middleScore); // oben
    addPoint(points, x, box.y + box.height, middleScore); // unten
    addPoint(points, box.x, y, middleScore); // links
    addPoint(points, box.x + box.width, y, middleScore); // rechts
  }

  return points;
}

function squaredDistance(pointA, pointB) {
  const dx = pointA.x - pointB.x;
  const dy = pointA.y - pointB.y;

  return dx * dx + dy * dy;
}

function isBetterConnection(
  currentDistance,
  bestDistance,
  sourcePoint,
  targetPoint,
  bestSource,
  bestTarget,
) {
  const isShorter = currentDistance < bestDistance - EPS;

  const isSameDistance = Math.abs(currentDistance - bestDistance) < EPS;

  const currentMiddleScore = sourcePoint.middleScore + targetPoint.middleScore;
  const bestMiddleScore = bestSource.middleScore + bestTarget.middleScore;

  const isMoreCentered = currentMiddleScore < bestMiddleScore;

  return isShorter || (isSameDistance && isMoreCentered);
}

// Wählt das kürzeste und bei Gleichstand möglichst mittige Verbindungspaar
function getBestConnection(sourceNode, targetNode) {
  const sourceBox = getBox(sourceNode);
  const targetBox = getBox(targetNode);

  const sourcePoints = getConnectionPoints(sourceBox);
  const targetPoints = getConnectionPoints(targetBox);

  let bestSource = sourcePoints[0];
  let bestTarget = targetPoints[0];
  let bestDistance = Infinity;

  for (const sourcePoint of sourcePoints) {
    for (const targetPoint of targetPoints) {
      const currentDistance = squaredDistance(sourcePoint, targetPoint);

      if (
        isBetterConnection(
          currentDistance,
          bestDistance,
          sourcePoint,
          targetPoint,
          bestSource,
          bestTarget,
        )
      ) {
        bestDistance = currentDistance;
        bestSource = sourcePoint;
        bestTarget = targetPoint;
      }
    }
  }

  return {
    sourceX: bestSource.x,
    sourceY: bestSource.y,
    targetX: bestTarget.x,
    targetY: bestTarget.y,
  };
}

export default function FloatingEdge({
  id,
  source,
  target,
  markerEnd,
  style,
  data,
  label,
}) {
  const sourceNode = useInternalNode(source);
  const targetNode = useInternalNode(target);

  if (!sourceNode || !targetNode) {
    return null;
  }

  const connection = getBestConnection(sourceNode, targetNode);

  const index = data?.targetIndex ?? 0;
  const count = data?.targetCount ?? 1;
  //Versetzt Kanten mit dem selben Ziel ein wenig
  const offset = (index - (count - 1) / 2) * 5;
  connection.targetX += offset;
  const [edgePath, labelX, labelY] = getStraightPath(connection);

  return (
    <>
      <BaseEdge id={id} path={edgePath} markerEnd={markerEnd} style={style} />

      {label && (
        <EdgeLabelRenderer>
          <div
            style={{
              position: "absolute",
              transform: `translate(-50%, -50%) translate(${labelX}px, ${labelY}px)`,
              backgroundColor: "white",
              border: "1px solid rgba(0,0,0,0.15)",
              borderRadius: "4px",
              padding: "1px 5px",
              fontSize: "22px",
              fontWeight: 500,
              pointerEvents: "none",
            }}
          >
            {label}
          </div>
        </EdgeLabelRenderer>
      )}
    </>
  );
}
