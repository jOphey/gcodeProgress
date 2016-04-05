# gcodeProgress
Simple tool to process a reprap-gcode-files

Adds M117-commands (print message on display) for every 0.1% progress done while printing the file.

## Compiling
You need a working java8 and maven-setup on your machine.
- Check out sources
- Execute 'mvn clean package' in checkout-root
- See ./target/gcodeprogress-0.2-jar-with-dependencies.jar

## Download
Use a precompiled Version from

`https://github.com/jOphey/gcodeProgress/raw/master/bin/gcodeprogress-0.2-jar-with-dependencies.jar
`
## Usage
You will need a working java8-setup on your machine

Open a shell (cmd.exe on Windows) and use command `java -version` to ensure java is working and reports a version >= 1.8

### Standalone
- Execute

`java -jar C:/path/to/gcodeprogress-0.2-jar-with-dependencies.jar --input C:/path/to/inputfile.gcode --output C:/path/to/outputfile.gcode`

Now outputfile.gcode will contain the progress-messages wich will be displayed on your printers LCD during a print.

### Repetier-Host
- Goto: "Config" -> "Printer Settings"
- Choose Tab "Advanced"
- Set "Post Slice Filter"-Parameter to:

`java -jar C:/path/to/gcodeprogress-0.2-jar-with-dependencies.jar --input #in --output #out`

Now everytime you slice some objects the script will be executed right after slicing and the messages for the printer-display will be added to the resulting gcode-file
