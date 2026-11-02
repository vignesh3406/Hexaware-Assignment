
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateOrders {

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

    // Find duplicate orders using HashMap
    static void findDuplicates(List<Order> orders) {

        Map<String, Order> orderMap = new HashMap<>();

        int duplicateCount = 0;

        System.out.println("--- Duplicate Orders ---");

        for (Order order : orders) {

            // Extract date and hour/minute
            String minute = order.createdAt.substring(0, 16);

            // Create unique key
            String key = order.customerId + "|"
                    + order.amount + "|"
                    + minute;

            if (orderMap.containsKey(key)) {

                Order original = orderMap.get(key);

                System.out.println(
                        "Duplicate found: "
                                + original.orderId
                                + " and "
                                + order.orderId
                );

                duplicateCount++;

            } else {

                orderMap.put(key, order);
            }
        }

        System.out.println(
                "\nTotal duplicate orders found: "
                        + duplicateCount
        );
    }

    // Main method
    public static void main(String[] args) {

        try {

            List<Order> orders = loadOrders("orders.csv");

            System.out.println(
                    "Total orders loaded: " + orders.size()
            );

            findDuplicates(orders);

        } catch (IOException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}
