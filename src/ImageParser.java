import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageParser {
    private int[][][] pixels; // [y][x][channel] → channel: 0=R, 1=G, 2=B
    private int width;
    private int height;

    /**
     * Konstruktor: membaca file gambar dan menyimpan pixel RGB-nya.
     */
    public ImageParser(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        width = image.getWidth();
        height = image.getHeight();
        pixels = new int[height][width][3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                pixels[y][x][0] = (rgb >> 16) & 0xFF; // Red
                pixels[y][x][1] = (rgb >> 8) & 0xFF; // Green
                pixels[y][x][2] = rgb & 0xFF; // Blue
            }
        }
    }

    public int[][][] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Mendapatkan warna RGB pada posisi tertentu.
     */
    public int[] getPixel(int x, int y) {
        return pixels[y][x]; // mengembalikan array {R, G, B}
    }

    // Test
    public static void main(String[] args) {
        try {
            String fileJPG = "parserTestJPG.jpg";
            String fileJPEG = "parserTestJPEG.jpeg";
            String filePNG = "parserTestPNG.png";
            String absPath = "C:/Users/IQBAL-LAPTOP/IF/Strago/Tucil2_13523111/test/";

            ImageParser parserJPG = new ImageParser(absPath + fileJPG);
            int widthJPG = parserJPG.getWidth();
            int heightJPG = parserJPG.getHeight();
            int[] pixelJPG = parserJPG.getPixel(0, 0);

            System.out.println("file: " + fileJPG);
            System.out.println("Ukuran gambar: " + widthJPG + " x " + heightJPG);
            System.out.println("Pixel (0,0): R=" + pixelJPG[0] + ", G=" + pixelJPG[1] + ", B=" + pixelJPG[2]);

            ImageParser parserJPEG = new ImageParser(absPath + fileJPEG);
            int widthJPEG = parserJPEG.getWidth();
            int heightJPEG = parserJPEG.getHeight();
            int[] pixelJPEG = parserJPEG.getPixel(0, 0);

            System.out.println("file: " + fileJPEG);
            System.out.println("Ukuran gambar: " + widthJPEG + " x " + heightJPEG);
            System.out.println("Pixel (0,0): R=" + pixelJPEG[0] + ", G=" + pixelJPEG[1] + ", B=" + pixelJPEG[2]);

            ImageParser parserPNG = new ImageParser(absPath + filePNG);
            int widthPNG = parserPNG.getWidth();
            int heightPNG = parserPNG.getHeight();
            int[] pixelPNG = parserPNG.getPixel(1000, 1000);

            System.out.println("file: " + filePNG);
            System.out.println("Ukuran gambar: " + widthPNG + " x " + heightPNG);
            System.out.println("Pixel (1000,1000): R=" + pixelPNG[0] + ", G=" + pixelPNG[1] + ", B=" + pixelPNG[2]);

        } catch (IOException e) {
            System.err.println("Gagal membaca gambar: " + e.getMessage());
        }
    }
}
