dir /s /B *.java > sources.txt
javac -d bin @sources.txt
java -cp bin editor/Main
