package de.ophosting.gcodeprogess;

import de.ophosting.gcodeprogess.cli.CliOptions;
import de.ophosting.gcodeprogess.cli.CommandLineParameterHandler;
import de.ophosting.gcodeprogess.gcodeprocessing.GCodeProcessor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

/**
 * @author Jan Ophey
 */
public class Main {

    public static void main(final String[] args) throws ParseException, IOException {
        try {
            final CommandLineParameterHandler parameterHandler = new CommandLineParameterHandler();
            final CommandLine commandLine = parameterHandler.parseCommandLine(args);
            final String inputFileName = commandLine.getOptionValue(CliOptions.INPUT);
            final String outputFileName = commandLine.getOptionValue(CliOptions.OUTPUT);
            final GCodeProcessor gCodeProcessor = new GCodeProcessor();
            gCodeProcessor.processGcode(inputFileName, outputFileName);
        } catch (final RuntimeException ex) {
            System.exit(1);
        }
    }

}
