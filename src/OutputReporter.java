import java.io.File;

public class OutputReporter {
    public static void report(String inputPath, String outputPath, double execTimeMs, QuadTreeNode root) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        long originalSize = inputFile.length();
        long compressedSize = outputFile.length();
        double compressionRate = 100.0 * (1.0 - ((double) compressedSize / originalSize));
        int depth = TreeAnalyzer.getDepth(root);
        int nodeCount = TreeAnalyzer.countNodes(root);

        System.out.println("\n=== HASIL KOMPRESI ===");
        System.out.printf("1. Waktu Eksekusi     : %.2f ms%n", execTimeMs);
        System.out.printf("2. Ukuran Sebelum     : %d bytes%n", originalSize);
        System.out.printf("3. Ukuran Setelah     : %d bytes%n", compressedSize);
        System.out.printf("4. Persentase Kompresi: %.2f%%%n", compressionRate);
        System.out.printf("5. Kedalaman Pohon    : %d%n", depth);
        System.out.printf("6. Jumlah Simpul      : %d%n", nodeCount);
        System.out.println("7. Gambar disimpan di : " + outputPath);
    }
}
