public class TreeAnalyzer {

    /**
     * Mengembalikan kedalaman maksimum pohon quadtree.
     * Root dihitung sebagai kedalaman 1.
     */
    public static int getDepth(QuadTreeNode node) {
        if (node == null || node.getChildren() == null)
            return 1;

        int maxDepth = 0;
        for (QuadTreeNode child : node.getChildren()) {
            if (child != null) {
                int childDepth = getDepth(child);
                maxDepth = Math.max(maxDepth, childDepth);
            }
        }
        return 1 + maxDepth;
    }

    /**
     * Mengembalikan jumlah total simpul pada pohon quadtree.
     */
    public static int countNodes(QuadTreeNode node) {
        if (node == null)
            return 0;

        int count = 1; // Hitung simpul ini
        if (node.getChildren() != null) {
            for (QuadTreeNode child : node.getChildren()) {
                count += countNodes(child);
            }
        }
        return count;
    }
}
