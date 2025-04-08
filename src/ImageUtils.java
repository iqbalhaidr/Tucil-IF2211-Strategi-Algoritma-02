import java.io.IOException;

public class ImageUtils {

    /**
     * Menghitung warna rata-rata (R, G, B) dari blok gambar berukuran width x
     * height,
     * dengan titik kiri atas di (x, y).
     *
     * @param pixels Matriks piksel 3D [y][x][channel]
     * @param x      Koordinat kiri atas blok (sumbu X)
     * @param y      Koordinat kiri atas blok (sumbu Y)
     * @param width  Lebar blok
     * @param height Tinggi blok
     * @return Array [avgR, avgG, avgB]
     */
    public static int[] calculateAverageRGB(int[][][] pixels, int x, int y, int width, int height) {
        long sumR = 0, sumG = 0, sumB = 0;
        int totalPixel = width * height;

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                sumR += pixels[i][j][0];
                sumG += pixels[i][j][1];
                sumB += pixels[i][j][2];
            }
        }

        int avgR = (int) (sumR / totalPixel);
        int avgG = (int) (sumG / totalPixel);
        int avgB = (int) (sumB / totalPixel);

        return new int[] { avgR, avgG, avgB };
    }

    public static double calculateVariance(int[][][] pixels, int x, int y, int width, int height, int[] avgColor) {
        double sumSqR = 0, sumSqG = 0, sumSqB = 0;
        int totalPixel = width * height;

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                int r = pixels[i][j][0];
                int g = pixels[i][j][1];
                int b = pixels[i][j][2];

                sumSqR += Math.pow(r - avgColor[0], 2);
                sumSqG += Math.pow(g - avgColor[1], 2);
                sumSqB += Math.pow(b - avgColor[2], 2);
            }
        }

        double varR = sumSqR / totalPixel;
        double varG = sumSqG / totalPixel;
        double varB = sumSqB / totalPixel;

        return (varR + varG + varB) / 3.0; // sesuai petunjuk tugas
    }

    public static double calculateMAD(int[][][] pixels, int x, int y, int width, int height, int[] avgColor) {
        double sumAbsR = 0, sumAbsG = 0, sumAbsB = 0;
        int totalPixel = width * height;

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                int r = pixels[i][j][0];
                int g = pixels[i][j][1];
                int b = pixels[i][j][2];

                sumAbsR += Math.abs(r - avgColor[0]);
                sumAbsG += Math.abs(g - avgColor[1]);
                sumAbsB += Math.abs(b - avgColor[2]);
            }
        }

        double madR = sumAbsR / totalPixel;
        double madG = sumAbsG / totalPixel;
        double madB = sumAbsB / totalPixel;

        return (madR + madG + madB) / 3.0;
    }

    public static double calculateMaxPixelDifference(int[][][] pixels, int x, int y, int width, int height) {
        int minR = 255, minG = 255, minB = 255;
        int maxR = 0, maxG = 0, maxB = 0;

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                int r = pixels[i][j][0];
                int g = pixels[i][j][1];
                int b = pixels[i][j][2];

                minR = Math.min(minR, r);
                minG = Math.min(minG, g);
                minB = Math.min(minB, b);

                maxR = Math.max(maxR, r);
                maxG = Math.max(maxG, g);
                maxB = Math.max(maxB, b);
            }
        }

        int diffR = maxR - minR;
        int diffG = maxG - minG;
        int diffB = maxB - minB;

        return (diffR + diffG + diffB) / 3.0;
    }

    public static double calculateEntropy(int[][][] pixels, int x, int y, int width, int height) {
        int totalPixel = width * height;

        // Histogram untuk masing-masing channel
        int[] histR = new int[256];
        int[] histG = new int[256];
        int[] histB = new int[256];

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                histR[pixels[i][j][0]]++;
                histG[pixels[i][j][1]]++;
                histB[pixels[i][j][2]]++;
            }
        }

        double entropyR = 0.0, entropyG = 0.0, entropyB = 0.0;

        for (int i = 0; i < 256; i++) {
            if (histR[i] > 0) {
                double p = histR[i] / (double) totalPixel;
                entropyR -= p * (Math.log(p) / Math.log(2));
            }
            if (histG[i] > 0) {
                double p = histG[i] / (double) totalPixel;
                entropyG -= p * (Math.log(p) / Math.log(2));
            }
            if (histB[i] > 0) {
                double p = histB[i] / (double) totalPixel;
                entropyB -= p * (Math.log(p) / Math.log(2));
            }
        }

        return (entropyR + entropyG + entropyB) / 3.0;
    }

    // Test calculateAverageRGB
    public static void main(String[] args) {
        try {
            ImageParser parser = new ImageParser(
                    "C:/Users/IQBAL-LAPTOP/IF/Strago/Tucil2_13523111/test/parserTestJPG.jpg");
            int[] avg = calculateAverageRGB(parser.getPixels(), 100, 120, 200, 200);
            System.out.println("Avg R: " + avg[0] + ", G: " + avg[1] + ", B: " + avg[2]);
        } catch (IOException e) {
            System.err.println("Gagal menghitung avg: " + e.getMessage());
        }
    }
}
