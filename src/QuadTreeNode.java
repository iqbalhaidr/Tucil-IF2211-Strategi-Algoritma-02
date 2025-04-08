public class QuadTreeNode {
    private int x, y; // Koordinat kiri-atas area
    private int width, height; // Ukuran area
    private int[] avgColor; // Warna rata-rata area [R, G, B]
    private QuadTreeNode[] children; // Anak-anak: [0]=LU, [1]=RU, [2]=LD, [3]=RD

    // Konstruktor untuk simpul daun (leaf) atau simpul internal (yang punya anak)
    public QuadTreeNode(int x, int y, int width, int height, int[] avgColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.avgColor = avgColor;
        this.children = null; // Belum dibagi
    }

    // Setter anak-anak ketika dibagi
    public void setChildren(QuadTreeNode[] children) {
        this.children = children;
    }

    // Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getAvgColor() {
        return avgColor;
    }

    public QuadTreeNode[] getChildren() {
        return children;
    }

    // Cek apakah node ini adalah leaf (tidak dibagi lagi)
    public boolean isLeaf() {
        return children == null;
    }
}
