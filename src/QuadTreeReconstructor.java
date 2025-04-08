import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class QuadTreeReconstructor {

    public static BufferedImage reconstructImage(QuadTreeNode root, int width, int height) {
        int[][][] reconstructed = new int[height][width][3];
        fillFromQuadTree(root, reconstructed);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = reconstructed[y][x][0];
                int g = reconstructed[y][x][1];
                int b = reconstructed[y][x][2];
                int rgb = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }

    private static void fillFromQuadTree(QuadTreeNode node, int[][][] image) {
        if (node == null)
            return;

        if (node.isLeaf()) {
            int[] color = node.getAvgColor();
            int x0 = node.getX();
            int y0 = node.getY();
            int w = node.getWidth();
            int h = node.getHeight();

            for (int y = y0; y < y0 + h; y++) {
                for (int x = x0; x < x0 + w; x++) {
                    image[y][x][0] = color[0];
                    image[y][x][1] = color[1];
                    image[y][x][2] = color[2];
                }
            }
        } else {
            for (QuadTreeNode child : node.getChildren()) {
                fillFromQuadTree(child, image);
            }
        }
    }

    public static void saveImage(BufferedImage image, String outputPath) throws IOException {
        String format = getFileExtension(outputPath);
        ImageIO.write(image, format, new File(outputPath));
    }

    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex != -1 && dotIndex < filename.length() - 1)
                ? filename.substring(dotIndex + 1)
                : "png"; // default
    }

}
