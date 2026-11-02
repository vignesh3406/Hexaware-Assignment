public class BinarySearchTree {
    static class Node {
        String key; Node left, right;
        Node(String key) { this.key = key; }
    }
    static class BST {
        Node root;
        void insert(String key) { root = insert(root, key); }
        Node insert(Node n, String key) {
            if (n == null) return new Node(key);
            int c = key.compareToIgnoreCase(n.key);
            if (c < 0) n.left = insert(n.left, key);
            else if (c > 0) n.right = insert(n.right, key);
            return n;
        }
        boolean search(String key) {
            Node n = root;
            while (n != null) {
                int c = key.compareToIgnoreCase(n.key);
                if (c == 0) return true;
                n = c < 0 ? n.left : n.right;
            }
            return false;
        }
        void inorder(Node n) { if (n != null) { inorder(n.left); System.out.print(n.key+" "); inorder(n.right); } }
        void preorder(Node n) { if (n != null) { System.out.print(n.key+" "); preorder(n.left); preorder(n.right); } }
        void postorder(Node n) { if (n != null) { postorder(n.left); postorder(n.right); System.out.print(n.key+" "); } }
    }
    public static void main(String[] args) {
        String[] categories = {"Electronics", "Fashion", "Books", "Home", "Beauty", "Sports", "Toys", "Grocery", "Furniture", "Footwear", "Mobiles", "Laptops", "Appliances", "Jewellery", "Gaming"};
        BST tree = new BST();
        for (String category : categories) tree.insert(category);
        System.out.println("Inserted categories: " + categories.length);
        System.out.print("Inorder: "); tree.inorder(tree.root); System.out.println();
        System.out.print("Preorder: "); tree.preorder(tree.root); System.out.println();
        System.out.print("Postorder: "); tree.postorder(tree.root); System.out.println();
        System.out.println("Search Mobiles: " + tree.search("Mobiles"));
        System.out.println("Search Automotive: " + tree.search("Automotive"));
    }
}
