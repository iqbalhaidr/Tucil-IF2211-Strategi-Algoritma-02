public class QuadTreeBuilder2 {
    private int[][][] pixels;
    private int width, height;
    private int threshold;
    private int minBlockSize;

    public QuadTreeBuilder2(ImageParser parser, int threshold, int minBlockSize) {
        this.pixels = parser.getPixels();
        this.width = parser.getWidth();
        this.height = parser.getHeight();
        this.threshold = threshold;
        this.minBlockSize = minBlockSize;
    }

    public QuadTreeNode build() {
        return buildRecursive(0, 0, width, height);
    }

    private QuadTreeNode buildRecursive(int x, int y, int w, int h) {
        int[] avg = ImageUtils.calculateAverageRGB(pixels, x, y, w, h);
        double error = ImageUtils.calculateMAD(pixels, x, y, w, h, avg); // Atur Error Method
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
