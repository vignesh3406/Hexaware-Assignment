
import java.util.Arrays;

public class RefundCalculator {

    // Greedy Algorithm
    static void greedy(int amount, int[] denominations) {

        int count = 0;

        System.out.println("--- Greedy Algorithm ---");

        for (int coin : denominations) {

            int numberOfCoins = amount / coin;

            if (numberOfCoins > 0) {

                System.out.println(
                        coin + " x " + numberOfCoins
                );

                count += numberOfCoins;

                amount = amount % coin;
            }
        }

        if (amount != 0) {
            System.out.println(
                    "Exact refund cannot be formed."
            );
        } else {
            System.out.println(
                    "Minimum notes/coins (Greedy): " + count
            );
        }
    }

    // Dynamic Programming
    static void dynamicProgramming(
            int amount, int[] denominations) {

        int[] dp = new int[amount + 1];

        // Initialize DP array
        Arrays.fill(dp, amount + 1);

        dp[0] = 0;

        // Calculate minimum coins required
        for (int i = 1; i <= amount; i++) {

            for (int coin : denominations) {

                if (coin <= i) {

                    dp[i] = Math.min(
                            dp[i],
                            dp[i - coin] + 1
                    );
                }
            }
        }

        System.out.println(
                "\n--- Dynamic Programming ---"
        );

        if (dp[amount] > amount) {

            System.out.println(
                    "Exact refund cannot be formed."
            );

        } else {

            System.out.println(
                    "Minimum notes/coins (DP): "
                            + dp[amount]
            );

            // Display selected denominations
            System.out.print("Selected denominations: ");

            int remaining = amount;

            while (remaining > 0) {

                for (int coin : denominations) {

                    if (coin <= remaining
                            && dp[remaining]
                            == dp[remaining - coin] + 1) {

                        System.out.print(coin + " ");

                        remaining -= coin;

                        break;
                    }
                }
            }

            System.out.println();
        }
    }

    // Main method
    public static void main(String[] args) {

        // Example 1: Greedy works
        int[] normalDenominations = {
                50, 20, 10, 5, 2, 1
        };

        int refundAmount = 93;

        System.out.println(
                "Refund Amount: " + refundAmount
        );

        greedy(refundAmount, normalDenominations);

        // Example 2: Greedy fails
        int[] specialDenominations = {
                4, 3, 1
        };

        int specialAmount = 6;

        System.out.println(
                "\nRefund Amount: " + specialAmount
        );

        greedy(specialAmount, specialDenominations);

        // Dynamic Programming gives optimal answer
        dynamicProgramming(
                specialAmount,
                specialDenominations
        );
    }
}
