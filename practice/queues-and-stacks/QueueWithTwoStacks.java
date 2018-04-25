package queues;

import java.util.Stack;

public class QueueWithTwoStacks<Item> {
    private Stack<Item> enqueueStack = new Stack<>();
    private Stack<Item> dequeueStack = new Stack<>();

    public void enqueue(Item item) {
        enqueueStack.push(item);
    }

    public Item dequeue() {
        if (enqueueStack.isEmpty() && dequeueStack.isEmpty()) {
            throw new IllegalStateException("blah");
        } else if (dequeueStack.isEmpty()) {
            while (!enqueueStack.isEmpty()) {
                dequeueStack.push(enqueueStack.pop());
            }
        }
        return dequeueStack.pop();
    }

    public static void main(String[] args) {
        QueueWithTwoStacks q = new QueueWithTwoStacks();
        for (int i = 0; i < 10; i++) {
            q.enqueue("item" + i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(q.dequeue());
        }
    }
}
