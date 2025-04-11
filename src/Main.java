import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Utility.CompressionParams p = Utility.getUserInput();
        // ====== Konfigurasi parameter ======
        String inputPath = p.inputPath; // Gambar input
        String outputPath = p.outputPath; // Gambar hasil
        int method = p.errorMethod; // Metode error: "variance", "mad", "maxdiff", atau "entropy"
        double threshold = p.threshold; // Threshold error
        int minBlockSize = p.minBlockSize; // Minimum luas blok dalam pixel

        try {
            long startTime = System.nanoTime();

            // ====== 1. Parsing gambar ======
            ImageParser parser = new ImageParser(inputPath);

            // ====== 2. Bangun quadtree ======
            QuadTreeBuilder builder = new QuadTreeBuilder(parser, method, threshold, minBlockSize);
            QuadTreeNode root = builder.build();

            // ====== 3. Rekonstruksi gambar ======
            BufferedImage reconstructed = QuadTreeReconstructor.reconstructImage(
                    root, parser.getWidth(), parser.getHeight());

            // ====== 4. Simpan hasil rekonstruksi ======
            QuadTreeReconstructor.saveImage(reconstructed, outputPath);
            System.out.println("Gambar berhasil disimpan sebagai: " + outputPath);

            long endTime = System.nanoTime();
            double execTimeMs = (endTime - startTime) / 1_000_000.0; // dalam ms

            OutputReporter.report(inputPath, outputPath, execTimeMs, root);

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca/menyimpan gambar: " + e.getMessage());
        }
    }
}
