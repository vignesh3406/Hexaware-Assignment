
public class BinarySearchTree {

    // Node class
    static class Node {
        String data;
        Node left, right;

        Node(String data) {
            this.data = data;
            left = right = null;
        }
    }

    // BST class
    static class BST {
        Node root;

        // Insert category
        Node insert(Node root, String data) {

            if (root == null) {
                return new Node(data);
            }

            if (data.compareTo(root.data) < 0) {
                root.left = insert(root.left, data);
            } else if (data.compareTo(root.data) > 0) {
                root.right = insert(root.right, data);
            }

            return root;
        }

        // Search category
        boolean search(Node root, String key) {

            if (root == null) {
                return false;
            }

            if (root.data.equals(key)) {
                return true;
            }

            if (key.compareTo(root.data) < 0) {
                return search(root.left, key);
            }

            return search(root.right, key);
        }

        // Inorder: Left, Root, Right
        void inorder(Node root) {
            if (root != null) {
                inorder(root.left);
                System.out.print(root.data + " ");
                inorder(root.right);
            }
        }

        // Preorder: Root, Left, Right
        void preorder(Node root) {
            if (root!= null) {
                System.out.print(root.data + " ");
                preorder(root.left);
                preorder(root.right);
            }
        }

        // Postorder: Left, Right, Root
        void postorder(Node root) {
            if (root != null) {
                postorder(root.left);
                postorder(root.right);
                System.out.print(root.data + " ");
            }
        }
    }

    public static void main(String[] args) {

        BST bst = new BST();

        String[] categories = {
                "Electronics", "Fashion", "Books",
                "Home", "Beauty", "Sports",
                "Toys", "Grocery", "Furniture",
                "Footwear", "Mobiles", "Laptops",
                "Appliances", "Jewellery", "Gaming"
        };

        // Insert 15 categories
        for (String category : categories) {
            bst.root = bst.insert(bst.root, category);
        }

        System.out.println("Product Categories Inserted Successfully!");

        // Search
        String key = "Mobiles";

        if (bst.search(bst.root, key)) {
            System.out.println(key + " category found.");
        } else {
            System.out.println(key + " category not found.");
        }

        // Traversals
        System.out.print("\nInorder Traversal: ");
        bst.inorder(bst.root);

        System.out.print("\nPreorder Traversal: ");
        bst.preorder(bst.root);

        System.out.print("\nPostorder Traversal: ");
        bst.postorder(bst.root);

        System.out.println();
    }
}
