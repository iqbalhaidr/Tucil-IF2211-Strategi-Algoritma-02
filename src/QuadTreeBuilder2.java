public class QuadTreeBuilder2 {
    private int[][][] pixels;
    private int width, height;
    private double threshold;
    private int minBlockSize;
    private int method;

    public QuadTreeBuilder2(ImageParser parser, int method, double threshold, int minBlockSize) {
        this.pixels = parser.getPixels();
        this.width = parser.getWidth();
        this.height = parser.getHeight();
        this.threshold = threshold;
        this.minBlockSize = minBlockSize;
        this.method = method;
    }

    public QuadTreeNode build() {
        return buildRecursive(0, 0, width, height);
    }

    private QuadTreeNode buildRecursive(int x, int y, int w, int h) {
        int[] avg = ImageUtils.calculateAverageRGB(pixels, x, y, w, h);
        double error;

        switch (method) {
            case 1:
                error = ImageUtils.calculateVariance(pixels, x, y, w, h, avg);
                break;
            case 2:
                error = ImageUtils.calculateMAD(pixels, x, y, w, h, avg);
                break;
            case 3:
                error = ImageUtils.calculateMaxPixelDifference(pixels, x, y, w, h);
                break;
            case 4:
                error = ImageUtils.calculateEntropy(pixels, x, y, w, h);
                break;
            default:
                throw new IllegalArgumentException("Metode error tidak dikenali: " + method);
        }

        int blockArea = w * h;

        boolean canSplit = (blockArea >= 4 * minBlockSize) && (w > 1 || h > 1);
        boolean shouldSplit = error >= threshold && canSplit;

        QuadTreeNode node = new QuadTreeNode(x, y, w, h, avg);

        if (shouldSplit) {
            int w1 = w / 2, w2 = w - w1;
            int h1 = h / 2, h2 = h - h1;

            QuadTreeNode[] children = new QuadTreeNode[4];
            children[0] = buildRecursive(x, y, w1, h1); // Top-left
            children[1] = buildRecursive(x + w1, y, w2, h1); // Top-right
            children[2] = buildRecursive(x, y + h1, w1, h2); // Bottom-left
            children[3] = buildRecursive(x + w1, y + h1, w2, h2); // Bottom-right

            node.setChildren(children);
        }

        return node;
    }
}
