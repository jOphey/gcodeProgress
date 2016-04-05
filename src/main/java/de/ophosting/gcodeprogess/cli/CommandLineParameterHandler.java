package de.ophosting.gcodeprogess.cli;

import org.apache.commons.cli.*;

/**
 * @author Jan Ophey
 */
public class CommandLineParameterHandler {

    public CommandLine parseCommandLine(final String[] args) throws ParseException, IllegalArgumentException {
        final Options options = defineOptions();
        final CommandLineParser commandLineParser = new DefaultParser();
        final CommandLine commandLine = commandLineParser.parse(options, args);
        if (!commandLine.hasOption(CliOptions.INPUT) || !commandLine.hasOption(CliOptions.OUTPUT)) {
            new HelpFormatter().printHelp("java -jar gcodeProgress.jar", options, true);
            throw new IllegalArgumentException("Wrong parameters");
        }
        return commandLine;
    }

    private Options defineOptions() {
        final Options options = new Options();

        options.addOption(Option.builder()
                .longOpt("input")
                .argName("file")
                .hasArg(true)
                .desc("Filename to process")
                .optionalArg(false)
                .build());
        options.addOption(Option.builder()
                .longOpt("output")
                .argName("file")
                .hasArg(true)
                .desc("file to write output to")
                .optionalArg(false)
                .build()
        );
        return options;
    }

}
