
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OrdersSorting {

    // Order class
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
            System.out.println(
                    orderId + " | " + customerId + " | " + amount
            );
        }
    }

    // Load orders from CSV
    static List<Order> loadOrders(String filePath)
            throws IOException {

        List<Order> orders = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            br.readLine(); // Skip header

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

    // Bubble Sort - O(n²)
    // Sort amount in descending order
    static void bubbleSort(List<Order> orders) {

        int n = orders.size();

        for (int i = 0; i < n - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {

                if (orders.get(j).amount <
                        orders.get(j + 1).amount) {

                    Order temp = orders.get(j);

                    orders.set(j, orders.get(j + 1));
                    orders.set(j + 1, temp);

                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    // Merge Sort - O(n log n)
    // Sort amount in descending order
    static void mergeSort(List<Order> orders) {

        if (orders.size() <= 1) {
            return;
        }

        int mid = orders.size() / 2;

        List<Order> left =
                new ArrayList<>(orders.subList(0, mid));

        List<Order> right =
                new ArrayList<>(orders.subList(mid, orders.size()));

        mergeSort(left);
        mergeSort(right);

        merge(orders, left, right);
    }

    // Merge two sorted lists
    static void merge(List<Order> orders,
                      List<Order> left,
                      List<Order> right) {

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.size() && j < right.size()) {

            if (left.get(i).amount >= right.get(j).amount) {

                orders.set(k++, left.get(i++));

            } else {

                orders.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            orders.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            orders.set(k++, right.get(j++));
        }
    }

    // Display first five orders
    static void displayOrders(List<Order> orders) {

        for (int i = 0; i < Math.min(5, orders.size()); i++) {
            orders.get(i).display();
        }
    }

    // Trace first three passes of Bubble Sort
    static void traceBubbleSort() {

        int[] numbers = {5000, 1200, 8500, 3000, 6500};

        System.out.println("\n--- Bubble Sort Trace ---");

        System.out.println("Original: " +
                Arrays.toString(numbers));

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < numbers.length - i - 1; j++) {

                if (numbers[j] < numbers[j + 1]) {

                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }

            System.out.println(
                    "Pass " + (i + 1) + ": " +
                            Arrays.toString(numbers)
            );
        }
    }

    // Benchmark sorting algorithms
    static void benchmarkSorting(List<Order> original) {

        List<Order> bubbleOrders =
                new ArrayList<>(original);

        List<Order> mergeOrders =
                new ArrayList<>(original);

        List<Order> builtInOrders =
                new ArrayList<>(original);

        // Warm-up
        List<Order> warmUp =
                new ArrayList<>(original);

        bubbleSort(warmUp);

        warmUp = new ArrayList<>(original);
        mergeSort(warmUp);

        warmUp = new ArrayList<>(original);

        warmUp.sort(
                Comparator.comparingDouble(
                        (Order o) -> o.amount
                ).reversed()
        );

        // Bubble Sort benchmark
        long start = System.nanoTime();

        bubbleSort(bubbleOrders);

        long bubbleTime = System.nanoTime() - start;

        // Merge Sort benchmark
        start = System.nanoTime();

        mergeSort(mergeOrders);

        long mergeTime = System.nanoTime() - start;

        // Built-in Sort benchmark
        start = System.nanoTime();

        builtInOrders.sort(
                Comparator.comparingDouble(
                        (Order o) -> o.amount
                ).reversed()
        );

        long builtInTime = System.nanoTime() - start;

        System.out.println("\n--- Sorting Benchmark ---");

        System.out.printf(
                "Bubble Sort: %.3f ms%n",
                bubbleTime / 1_000_000.0
        );

        System.out.printf(
                "Merge Sort: %.3f ms%n",
                mergeTime / 1_000_000.0
        );

        System.out.printf(
                "Built-in Sort: %.3f ms%n",
                builtInTime / 1_000_000.0
        );

        // Verify that all methods produced the same order
        boolean sameResult =
                bubbleOrders.size() == mergeOrders.size()
                        && mergeOrders.size() == builtInOrders.size();

        for (int i = 0; sameResult && i < bubbleOrders.size(); i++) {

            if (Double.compare(
                    bubbleOrders.get(i).amount,
                    mergeOrders.get(i).amount) != 0
                    ||
                    Double.compare(
                            mergeOrders.get(i).amount,
                            builtInOrders.get(i).amount) != 0) {

                sameResult = false;
            }
        }

        System.out.println(
                "All sorting results match: " + sameResult
        );
    }

    // Main method
    public static void main(String[] args) {

        try {

            List<Order> orders = loadOrders("Task-1/orders.csv");

            System.out.println(
                    "Total orders loaded: " + orders.size()
            );

            // Select first 10,000 orders
            List<Order> first10000 =
                    new ArrayList<>(
                            orders.subList(
                                    0, Math.min(10000, orders.size())
                            )
                    );

            System.out.println(
                    "\nOrders selected for sorting: " +
                            first10000.size()
            );

            // Bubble Sort trace
            traceBubbleSort();

            // Run sorting benchmarks
            benchmarkSorting(first10000);

            // Display top five orders using Merge Sort
            List<Order> sortedOrders =
                    new ArrayList<>(first10000);

            mergeSort(sortedOrders);

            System.out.println(
                    "\n--- Top 5 Orders by Amount ---"
            );

            displayOrders(sortedOrders);

        } catch (IOException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}
