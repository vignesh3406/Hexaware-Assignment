public class TwoPointers {
    public static void main(String[] args) {
        int[] values = {10, 20, 30, 40, 50, 60, 70, 80, 90}; // Already sorted.
        int target = 100, left = 0, right = values.length - 1;
        boolean found = false;
        System.out.println("Target gift-card value: " + target);
        System.out.println("Matching pairs:");
        while (left < right) {
            int sum = values[left] + values[right];
            if (sum == target) {
                System.out.println(values[left] + " + " + values[right] + " = " + target);
                found = true;
                left++;
                right--;
            } else if (sum < target) left++;
            else right--;
        }
        if (!found) System.out.println("No pair found.");
    }
}
