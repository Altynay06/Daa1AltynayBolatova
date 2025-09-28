package dnc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVWriter {
    public static void writeResults(String filename, String header, List<String> dataLines) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(header);
            for (String line : dataLines) {
                writer.println(line);
            }
        }
    }


    public static void log(String algorithm, int n, long timeNs, Metrics metrics) {
        System.out.printf("%s, N=%d, Time=%.2fms, Comparisons=%d, Depth=%d\n",
                algorithm, n, timeNs / 1_000_000.0, metrics.comparisons, metrics.maxDepth);
    }
}