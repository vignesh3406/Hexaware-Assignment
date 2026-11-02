
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderSearch {

    // 1. Create Order class to store the data
    static class Order {
        String orderId;
        String customerId;
        double amount;
        String createdAt;

        Order(String orderId, String customerId,
              double amount, String createdAt) {

            this.orderId = orderId;
            this.customerId = customerId;
            this.amount = amount;
            this.createdAt = createdAt;
        }

        void display() {
            System.out.println("Order ID: " + orderId);
            System.out.println("Customer ID: " + customerId);
            System.out.println("Amount: " + amount);
            System.out.println("Created At: " + createdAt);
        }
    }

    // 2. Read CSV file through buffer reader
    static List<Order> loadOrders(String filePath)
            throws IOException {

        List<Order> orders = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                Order order = new Order(
                        data[0],
                        data[1],
                        Double.parseDouble(data[2]),
                        data[3]
                );

                orders.add(order);
            }
        }

        return orders;
    }

    // 3. Linear Search
    static Order linearSearch(List<Order> orders,
                              String targetId) {

        for (Order order : orders) {

            if (order.orderId.equals(targetId)) {
                return order;
            }
        }

        return null;
    }

    // 4. Binary Search
    static Order binarySearch(List<Order> orders,
                              String targetId) {

        int low = 0;
        int high = orders.size() - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            Order middleOrder = orders.get(mid);

            int comparison =
                    middleOrder.orderId.compareTo(targetId);

            if (comparison == 0) {
                return middleOrder;
            }

            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return null;
    }


    static void benchmarkSearch(List<Order> orders) {

        // Create a sorted copy for Binary Search
        List<Order> sortedOrders = new ArrayList<>(orders);

        sortedOrders.sort(
                Comparator.comparing(o -> o.orderId)
        );

        // Generate 1000 search IDs
        List<String> searchIds = new ArrayList<>();

        java.util.Random random = new java.util.Random(42);

        for (int i = 0; i < 1000; i++) {
            int index = random.nextInt(orders.size());
            searchIds.add(orders.get(index).orderId);
        }

        // Warm-up both algorithms
        for (String id : searchIds) {
            linearSearch(orders, id);
            binarySearch(sortedOrders, id);
        }

        // Benchmark Linear Search
        long linearTotal = 0;

        for (int round = 0; round < 3; round++) {

            long start = System.nanoTime();

            for (String id : searchIds) {
                linearSearch(orders, id);
            }

            long end = System.nanoTime();

            linearTotal += end - start;
        }

        // Benchmark Binary Search
        long binaryTotal = 0;

        for (int round = 0; round < 3; round++) {

            long start = System.nanoTime();

            for (String id : searchIds) {
                binarySearch(sortedOrders, id);
            }

            long end = System.nanoTime();

            binaryTotal += end - start;
        }

        // Calculate average time in milliseconds
        double linearAverage =
                linearTotal / 3.0 / 1_000_000.0;

        double binaryAverage =
                binaryTotal / 3.0 / 1_000_000.0;

        System.out.println("\n--- Search Benchmark ---");

        System.out.printf(
                "Linear Search: %.3f ms%n", linearAverage
        );

        System.out.printf(
                "Binary Search: %.3f ms%n", binaryAverage
        );

        if (binaryAverage > 0) {
            System.out.printf(
                    "Binary Search speed ratio: %.2f times%n",
                    linearAverage / binaryAverage
            );
        }
    }


    // 5. Main method
    public static void main(String[] args) {

        String filePath = "Task-1/orders.csv";

        try {

            // Load all orders
            List<Order> orders = loadOrders(filePath);

            System.out.println("Total orders loaded: "
                    + orders.size());

            // Take order ID to search or target order id
            String targetId = "ORD025000";

            // Linear Search
            System.out.println("\n--- Linear Search ---");

            Order linearResult =
                    linearSearch(orders, targetId);

            if (linearResult != null) {
                linearResult.display();
            } else {
                System.out.println("Order not found");
            }

            // Create sorted copy for Binary Search
            List<Order> sortedOrders =
                    new ArrayList<>(orders);

            // sort the value according to order id
            sortedOrders.sort(
                    Comparator.comparing(o -> o.orderId)
            );

            // Binary Search
            System.out.println("\n--- Binary Search ---");

            Order binaryResult =
                    binarySearch(sortedOrders, targetId);

            if (binaryResult != null) {
                binaryResult.display();
            } else {
                System.out.println("Order not found");
            }

            // To calculate the benchmark
            benchmarkSearch(orders);

        } catch (IOException e) {

            System.out.println("Error: " + e.getMessage());

        }
    }
}
