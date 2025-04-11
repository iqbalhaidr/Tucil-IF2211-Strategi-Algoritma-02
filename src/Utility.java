import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Utility {

    public static class CompressionParams {
        public String inputPath;
        public int errorMethod;
        public double threshold;
        public int minBlockSize;
        public String outputPath;
    }

    public static CompressionParams getUserInput() {
        Scanner scanner = new Scanner(System.in);
        CompressionParams params = new CompressionParams();

        while (true) {
            System.out.print("Input Path: ");
            params.inputPath = scanner.nextLine();
            File inputFile = new File(params.inputPath);
            if (!inputFile.exists() || !inputFile.isFile()) {
                System.out.println("File tidak ditemukan atau bukan file.");
                continue;
            }
            String[] allowedExts = { ".jpg", ".jpeg", ".png" };
            boolean validExt = Arrays.stream(allowedExts)
                    .anyMatch(params.inputPath.toLowerCase()::endsWith);
            if (!validExt) {
                System.out.println("Format file tidak didukung.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Error Method (1=Variance, 2=MAD, 3=MaxDiff, 4=Entropy): ");
            try {
                params.errorMethod = Integer.parseInt(scanner.nextLine());
                if (params.errorMethod < 1 || params.errorMethod > 4) {
                    System.out.println("Pilihan harus antara 1 sampai 4.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
            }
        }

        while (true) {
            System.out.print("Threshold (Desimal): ");
            try {
                params.threshold = Double.parseDouble(scanner.nextLine());
                if (params.threshold < 0) {
                    System.out.println("Threshold tidak boleh negatif.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
            }
        }

        while (true) {
            System.out.print("Minimum Block Size (Bulat): ");
            try {
                params.minBlockSize = Integer.parseInt(scanner.nextLine());
                if (params.minBlockSize <= 0) {
                    System.out.println("Minimum block size harus > 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
            }
        }

        while (true) {
            System.out.print("Output Path: ");
            params.outputPath = scanner.nextLine();
            File outputFile = new File(params.outputPath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                System.out.println("Folder tujuan tidak ditemukan.");
                continue;
            }

            int outputDot = params.outputPath.lastIndexOf(".");
            if (outputDot == -1) {
                System.out.println("Path output harus memiliki ekstensi file.");
                continue;
            }

            String inputExt = params.inputPath.substring(params.inputPath.lastIndexOf(".")).toLowerCase();
            String outputExt = params.outputPath.substring(params.outputPath.lastIndexOf(".")).toLowerCase();
            if (!inputExt.equals(outputExt)) {
                System.out.println("Ekstensi file output harus sama dengan input (" + inputExt + ").");
                continue;
            }
            break;
        }

        return params;
    }

    // Testing
    public static void main(String[] args) {
        CompressionParams p = getUserInput();
        System.out.println("inputPath: " + p.inputPath);
        System.out.println("Error Method: " + p.errorMethod);
        System.out.println("Threshold: " + p.threshold);
        System.out.println("MinSize: " + p.minBlockSize);
        System.out.println("outputPath: " + p.outputPath);
    }
}
