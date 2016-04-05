package de.ophosting.gcodeprogess.gcodeprocessing;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jan Ophey
 */
public class GCodeProcessor {

    static final int PROCESSING_THRESHOLD = 10000;

    private static final String CRLF = "\r\n";

    private static final NumberFormat numberFormatter = new DecimalFormat("0.0");

    public void processGcode(final String inputFileName, final String outputFileName) throws IOException {
        System.out.println("Analyzing file '" + inputFileName + "'");
        final AtomicInteger commandCount = new AtomicInteger(0);
        final List<String> lines = Files.readAllLines(Paths.get(inputFileName));
        lines.parallelStream()
                .filter(this::containsCommand)
                .forEach(line -> commandCount.addAndGet(1));

        System.out.println("Processing " + lines.size() + " lines with a total of " + commandCount.get() + " commands");
        final double stepsPerPermille = calculateStepsPerPermille(commandCount.get());
        int currentPermille = 0;
        commandCount.set(0);

        try (BufferedWriter output = Files.newBufferedWriter(Paths.get(outputFileName))) {
            for (final String line : lines) {
                if (containsCommand(line)) {
                    commandCount.addAndGet(1);
                }
                if (stepsPerPermille * currentPermille <= commandCount.get()) {
                    final String currentProgress = formatProgress(currentPermille);
                    System.out.println("Processed " + currentProgress + "%");
                    output.write("M117 Pro: " + currentProgress + " %%" + CRLF);
                    currentPermille++;
                }
                output.write(line + CRLF);
            }
        }

        System.out.println("Processed file written to '" + outputFileName + "'");
    }

    double calculateStepsPerPermille(final int commandCount) {
        if (commandCount <= PROCESSING_THRESHOLD) {
            // prevent insertion of M117-Commands by setting step-width = PROCESSING_THRESHOLD
            //this is to prevent blocking the printer-firmware with too many display-updates
            return PROCESSING_THRESHOLD;
        }
        // 1000 Steps -> resolution = 0.1%
        return commandCount / 1000.0;
    }

    String formatProgress(final int currentProgressPermille) {
        if (currentProgressPermille < 0) {
            throw new IllegalArgumentException("currentProgressPermille must not be negative");
        }
        if (currentProgressPermille > 1000) {
            throw new IllegalArgumentException("currentProgressPermille must not be larger than 1000");
        }
        return String.format("%5s", numberFormatter.format(0.1 * currentProgressPermille));
    }

    boolean containsCommand(final String line) {
        return line.startsWith("G") || line.startsWith("M");
    }
}
