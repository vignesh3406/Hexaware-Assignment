
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class StackAndQueue {

    // Own Stack implementation
    static class MyStack<T> {

        private ArrayList<T> items = new ArrayList<>();

        // Add item
        void push(T item) {
            items.add(item);
        }

        // Remove last item
        T pop() {
            if (isEmpty()) {
                return null;
            }

            return items.remove(items.size() - 1);
        }

        // View last item
        T peek() {
            if (isEmpty()) {
                return null;
            }

            return items.get(items.size() - 1);
        }

        boolean isEmpty() {
            return items.isEmpty();
        }
    }

    // Own Queue implementation
    static class MyQueue<T> {

        private ArrayList<T> items = new ArrayList<>();

        // Add item at the end
        void enqueue(T item) {
            items.add(item);
        }

        // Remove first item
        T dequeue() {
            if (isEmpty()) {
                return null;
            }

            return items.remove(0);
        }

        // View first item
        T peek() {
            if (isEmpty()) {
                return null;
            }

            return items.get(0);
        }

        boolean isEmpty() {
            return items.isEmpty();
        }
    }

    public static void main(String[] args) {

        // PART 1: Own Stack
        System.out.println("--- Own Stack: Undo Order Edit ---");

        MyStack<String> undoStack = new MyStack<>();

        undoStack.push("Order amount changed to 500");
        undoStack.push("Order address changed");
        undoStack.push("Order status changed");

        System.out.println("Undo operation: " + undoStack.pop());
        System.out.println("Undo operation: " + undoStack.pop());

        // PART 2: Built-in Stack alternative
        System.out.println("\n--- Built-in Stack Alternative ---");

        ArrayDeque<String> builtInStack = new ArrayDeque<>();

        builtInStack.push("Order amount changed to 500");
        builtInStack.push("Order address changed");
        builtInStack.push("Order status changed");

        System.out.println("Undo operation: " + builtInStack.pop());
        System.out.println("Undo operation: " + builtInStack.pop());

        // PART 3: Own Queue
        System.out.println("\n--- Own Queue: Dispatch Line ---");

        MyQueue<String> dispatchQueue = new MyQueue<>();

        dispatchQueue.enqueue("ORD001");
        dispatchQueue.enqueue("ORD002");
        dispatchQueue.enqueue("ORD003");

        System.out.println("Dispatch order: " + dispatchQueue.dequeue());
        System.out.println("Dispatch order: " + dispatchQueue.dequeue());

        // PART 4: Built-in Queue alternative
        System.out.println("\n--- Built-in Queue Alternative ---");

        Queue<String> builtInQueue = new ArrayDeque<>();

        builtInQueue.offer("ORD001");
        builtInQueue.offer("ORD002");
        builtInQueue.offer("ORD003");

        System.out.println("Dispatch order: " + builtInQueue.poll());
        System.out.println("Dispatch order: " + builtInQueue.poll());
    }
}
