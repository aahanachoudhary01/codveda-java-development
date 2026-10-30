
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHandling {

    public static void main(String[] args) {
        Path inputPath = Paths.get("input.txt");
        Path outputPath = Paths.get("output.txt");

        try {
            // Read all lines from the input file.
            List<String> lines = Files.readAllLines(
                    inputPath, StandardCharsets.UTF_8);

            int lineCount = lines.size();
            int wordCount = 0;
            int characterCount = 0;

            for (String line : lines) {
                characterCount += line.length();

                String trimmedLine = line.trim();

                if (!trimmedLine.isEmpty()) {
                    wordCount += trimmedLine.split("\\s+").length;
                }
            }

            String result = "File Analysis Report\n"
                    + "--------------------\n"
                    + "Number of lines: " + lineCount + "\n"
                    + "Number of words: " + wordCount + "\n"
                    + "Number of characters (excluding line breaks): "
                    + characterCount + "\n";

            // Write the report to the output file.
            Files.writeString(
                    outputPath, result, StandardCharsets.UTF_8);

            System.out.println("File processed successfully!");
            System.out.println(result);
            System.out.println("Report saved to: " + outputPath.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("File handling error: " + e.getMessage());
            System.out.println(
                    "Make sure input.txt exists in the current directory.");
        }
    }
}
