Ausführen:

Node.js, Java jdk17 oder neuer und Maven muss installiert und in PATH liegen.

1. npm install
2. cd parser
3. mvn exec:java "-Dexec.args=../public/exampleStrategyFiles/(fileName)"
4. npm run dev
5. cd ..
6. Lokale Seite öffnen (Link steht im terminal)


- Zum Zeigen ist wahrscheinlich "current.strategy" am besten. 

- Wenn man Schritt 3 ausführt wird als Ergebnis die geparste Json Datei gut lesbar in public/parsedGraph.json geladen, sowie die analysis.json

-Als default processor declaration file, wenn man in Schritt 3 nur einen Parameter angibt, ist std.strategy in "exampleProcDecFiles" in public. Wenn man zwei angibt, kann man diesen optional auch festlegen.


vor einem declare oder einer Strategie kann mit #@description eine Beschreibung eines prozessors angeführt werden. Wenn mehrere Zeilen Beschreibungen nötig sind, einfach in jede neue Zeile #@description