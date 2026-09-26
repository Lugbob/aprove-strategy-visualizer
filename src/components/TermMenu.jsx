//Menu zum Editieren:
export default function TermMenu({
  declaredProcessors,
  processorDefaults,
  selectedProcessor,
  setSelectedProcessor,
  processorParameters,
  setProcessorParameters,
  activeSubmenu,
  setActiveSubmenu,
  timerValue,
  setTimerValue,
  childCount,
  setChildCount,
  anyKLimit,
  setAnyKLimit,
  ifCondition,
  setIfCondition,
  ifHasElse,
  setIfHasElse,
  newReferenceName,
  setNewReferenceName,
  repeatMin,
  setRepeatMin,
  repeatMax,
  setRepeatMax,
  editableGraphs,
  actions,
}) {
  const {
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
  } = actions;

  return (
    <div
      style={{
        position: "absolute",
        top: "95px",
        left: "12px",
        zIndex: 20,
        backgroundColor: "white",
        border: "1px solid #ccc",
        padding: "10px",
      }}
    >
      <b>Select Term</b>
      <select
        defaultValue=""
        onChange={(event) => {
          const processorName = event.target.value;
          setSelectedProcessor(processorName);
          setProcessorParameters(
            formatProcessorParameters(processorDefaults[processorName] ?? ""),
          );
        }}
      >
        <option value="" disabled>
          Select Processor
        </option>
        {declaredProcessors.map((name) => (
          <option key={name} value={name}>
            {name}
          </option>
        ))}
      </select>
      {selectedProcessor && (
        <>
          <textarea
            value={processorParameters}
            onChange={(e) => setProcessorParameters(e.target.value)}
            onBlur={() =>
              setProcessorParameters(
                formatProcessorParameters(processorParameters),
              )
            }
            placeholder="Optional: enter processor parameters"
          />

          <button
            onClick={() => addProcessor(selectedProcessor, processorParameters)}
            disabled={!validProcessorParameters(processorParameters)}
          >
            {" "}
            Add Processor
          </button>
        </>
      )}
      <select
        defaultValue=""
        onChange={(event) => {
          let value = event.target.value;

          if (value === "newReference") {
            setActiveSubmenu("newReference");
            return;
          }

          addReference(value);
        }}
      >
        <option value="" disabled>
          Select Reference
        </option>

        {Object.keys(editableGraphs ?? {}).map((name) => (
          <option key={name} value={name}>
            {" "}
            {name}
          </option>
        ))}
        <option value="newReference">New Reference...</option>
      </select>
      {["Timer", "WallTimer", "Delay", "AnyDelay"].includes(activeSubmenu) && (
        <>
          <input
            type="number"
            value={timerValue}
            onChange={(event) => setTimerValue(event.target.value)}
            placeholder="Timer value"
          />

          {activeSubmenu === "AnyDelay" && (
            <input
              type="number"
              min="2"
              value={childCount}
              onChange={(event) => setChildCount(Number(event.target.value))}
              placeholder="Number of children"
              style={{ width: "170px" }}
            />
          )}

          <button
            disabled={
              timerValue === "" ||
              (activeSubmenu === "AnyDelay" &&
                (childCount < 2 || childCount > 20))
            }
            onClick={() => {
              if (activeSubmenu === "Timer") addTimer();
              if (activeSubmenu === "WallTimer") addWallTimer();
              if (activeSubmenu === "Delay") addDelay();
              if (activeSubmenu === "AnyDelay") addAnyDelay(childCount);

              closeAllSubmenus();
            }}
          >
            Add
          </button>
        </>
      )}
      {[
        "Any",
        "First",
        "Combine",
        "CombineParallel",
        "CombineSequential",
        "AnyK",
      ].includes(activeSubmenu) && (
        <>
          <input
            type="number"
            min="2"
            value={childCount}
            onChange={(event) => setChildCount(event.target.value)}
            placeholder="Number of children"
            style={{ width: "170px" }}
          />

          {activeSubmenu === "AnyK" && (
            <input
              type="number"
              min="1"
              max={childCount}
              value={anyKLimit}
              onChange={(event) => setAnyKLimit(event.target.value)}
              placeholder="Maximum parallel strategies"
              style={{ width: "200px" }}
            />
          )}

          <button
            disabled={
              childCount < 2 ||
              childCount > 20 ||
              (activeSubmenu === "AnyK" &&
                (anyKLimit < 1 || anyKLimit > childCount))
            }
            onClick={() => {
              if (activeSubmenu === "Any") addAny(childCount);
              if (activeSubmenu === "First") addFirst(childCount);
              if (activeSubmenu === "Combine") addCombine(childCount);
              if (activeSubmenu === "AnyK") addAnyK(childCount, anyKLimit);
              if (activeSubmenu === "CombineSequential")
                addCombineSequential(childCount);
              if (activeSubmenu === "CombineParallel")
                addCombineParallel(childCount);

              closeAllSubmenus();
            }}
          >
            Add
          </button>
        </>
      )}
      {activeSubmenu === "If" && (
        <>
          <input
            value={ifCondition}
            onChange={(event) => setIfCondition(event.target.value)}
            placeholder="Condition [...]"
          />

          <label>
            <input
              type="checkbox"
              checked={ifHasElse}
              onChange={(event) => setIfHasElse(event.target.checked)}
            />{" "}
            Else branch
          </label>

          <button
            disabled={!validCondition(ifCondition)}
            onClick={() => addIf(ifCondition, ifHasElse)}
          >
            Add
          </button>
        </>
      )}
      {activeSubmenu === "newReference" && (
        <>
          <input
            value={newReferenceName}
            onChange={(event) => {
              let temp = event.target.value.replace(/[0-9]/g, "_");
              setNewReferenceName(temp.charAt(0).toLowerCase() + temp.slice(1));
            }}
            placeholder="New Reference name"
          />

          <button onClick={addNewReference}>Create</button>
        </>
      )}
      {["Repeat", "RepeatS"].includes(activeSubmenu) && (
        <>
          <input
            value={repeatMin}
            onChange={(event) => {
              let value = event.target.value;

              if (/^\d*$/.test(value)) {
                setRepeatMin(value);
              }
            }}
            placeholder="Minimum repetitions"
          />

          <input
            value={repeatMax}
            onChange={(event) => {
              let value = event.target.value;

              if (value === "*" || /^\d*$/.test(value)) {
                setRepeatMax(value);
              }
            }}
            placeholder="Maximum repetitions or *"
          />

          <button
            disabled={
              repeatMin === "" ||
              repeatMax === "" ||
              (repeatMax !== "*" && Number(repeatMax) < Number(repeatMin))
            }
            onClick={() => {
              if (activeSubmenu === "Repeat") {
                addRepeat("Repeat", repeatMin, repeatMax);
              }

              if (activeSubmenu === "RepeatS") {
                addRepeatS("RepeatS", repeatMin, repeatMax);
              }

              closeAllSubmenus();
            }}
          >
            {" "}
            Add
          </button>
        </>
      )}

      <select // Öffnet das passende Untermenü
        defaultValue=""
        onChange={(event) => {
          if (
            [
              "Any",
              "First",
              "Combine",
              "CombineParallel",
              "CombineSequential",
              "AnyK",
            ].includes(event.target.value)
          ) {
            setActiveSubmenu(event.target.value);
            return;
          }
          if (event.target.value === "Maybe") {
            addMaybe();
          }
          if (event.target.value === "Prove") {
            addProve();
          }
          if (event.target.value === "Solve") {
            addSolve();
          }
          if (event.target.value === "Disprove") {
            addDisprove();
          }
          if (
            ["Timer", "WallTimer", "Delay", "AnyDelay"].includes(
              event.target.value,
            )
          ) {
            setActiveSubmenu(event.target.value);
            return;
          }
          if (["Repeat", "RepeatS"].includes(event.target.value)) {
            setActiveSubmenu(event.target.value);
            return;
          }
          if (event.target.value === "If") {
            setActiveSubmenu("If");
          }
          event.target.value = "";
        }}
      >
        <option value="" disabled>
          Select Strategy Construct
        </option>
        <option value="Any">Any</option>
        <option value="AnyK">AnyK</option>
        <option value="First">First</option>
        <option value="Combine">Combine</option>
        <option value="CombineParallel">CombineParallel</option>
        <option value="CombineSequential">CombineSequential</option>
        <option value="If">If</option>
        <option value="Repeat">Repeat</option>
        <option value="RepeatS">RepeatS</option>
        <option value="Delay">Delay</option>
        <option value="AnyDelay">AnyDelay</option>
        <option value="Timer">Timer</option>
        <option value="WallTimer">WallTimer</option>
        <option value="Maybe">Maybe</option>
        <option value="Solve">Solve</option>
        <option value="Prove">Prove</option>
        <option value="Disprove">Disprove</option>
      </select>
    </div>
  );
}

function validCondition(condition) {
  const text = condition.trim();

  if (!text.startsWith("[") || !text.endsWith("]")) {
    return false;
  }

  const content = text.slice(1, -1);

  return (
    content.trim() !== "" && !content.includes("[") && !content.includes("]")
  );
}
