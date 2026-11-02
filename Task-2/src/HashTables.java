
import java.util.HashMap;

public class HashTables {

    // Node for separate chaining
    static class Node {
        String sku;
        String productName;
        Node next;

        Node(String sku, String productName) {
            this.sku = sku;
            this.productName = productName;
            this.next = null;
        }
    }

    // Custom Hash Table
    static class HashTable {

        Node[] table;
        int size;

        HashTable(int size) {
            this.size = size;
            table = new Node[size];
        }

        // Hash function
        int hash(String sku) {
            return Math.floorMod(sku.hashCode(), size);
        }

        // Insert product
        void insert(String sku, String productName) {

            int index = hash(sku);

            Node newNode = new Node(sku, productName);

            if (table[index] == null) {
                table[index] = newNode;
            } else {
                newNode.next = table[index];
                table[index] = newNode;

                System.out.println("Collision occurred for " + sku);
            }
        }

        // Search product
        String search(String sku) {

            int index = hash(sku);

            Node current = table[index];

            while (current != null) {

                if (current.sku.equals(sku)) {
                    return current.productName;
                }

                current = current.next;
            }

            return null;
        }
    }

    public static void main(String[] args) {

        HashTable customTable = new HashTable(10);

        System.out.println("Custom Hash Table");

        customTable.insert("SKU101", "Laptop");
        customTable.insert("SKU102", "Mobile");
        customTable.insert("SKU103", "Keyboard");

        System.out.println("Search SKU102: "
                + customTable.search("SKU102"));

        // Built-in HashMap
        HashMap<String, String> map = new HashMap<>();

        map.put("SKU101", "Laptop");
        map.put("SKU102", "Mobile");
        map.put("SKU103", "Keyboard");

        System.out.println("\nBuilt-in HashMap");

        System.out.println("Search SKU102: "
                + map.get("SKU102"));

        // Benchmark 100,000 SKUs
        HashMap<String, String> benchmarkMap = new HashMap<>();
        HashTable benchmarkTable = new HashTable(200003);

        int total = 100000;

        for (int i = 0; i < total; i++) {

            String sku = "SKU" + i;

            benchmarkMap.put(sku, "Product" + i);
            benchmarkTable.insert(sku, "Product" + i);
        }

        long start = System.nanoTime();

        for (int i = 0; i < total; i++) {
            benchmarkTable.search("SKU" + i);
        }

        long customTime = System.nanoTime() - start;

        start = System.nanoTime();

        for (int i = 0; i < total; i++) {
            benchmarkMap.get("SKU" + i);
        }

        long mapTime = System.nanoTime() - start;

        System.out.println("\nPerformance Comparison");

        System.out.println("Custom Hash Table: "
                + customTime / 1_000_000.0 + " ms");

        System.out.println("Java HashMap: "
                + mapTime / 1_000_000.0 + " ms");
    }
}
