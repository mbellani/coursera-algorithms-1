package queues;

public class ArrayBasedQueue<Item> {

    private Object[] items;
    private int head = 0;
    private int tail = 0;

    public ArrayBasedQueue(int initialSize) {
        this.items = new Object[initialSize];
    }

    public void enqueue(Item item) {
        items[tail++] = item;
        if (tail == items.length - 1) {
            resize(items.length * 2);
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        Item item = (Item) items[head++];
        if (((tail - head) / (float) items.length) <= 1 / (float) 4) {
            shrink();
        }
        return item;

    }

    private void shrink() {
        Object[] oldItems = items;
        items = new Object[items.length / 2];
        int availableItems = tail - head;
        System.arraycopy(oldItems, head, items, 0, availableItems);
        head = 0;
        tail = availableItems;
    }


    private void resize(int size) {
        Object[] oldItems = items;
        items = new Object[size];
        System.arraycopy(oldItems, 0, items, 0, oldItems.length);
    }


    public boolean isEmpty() {
        return tail - head == 0;
    }

    public static void main(String[] args) {
        ArrayBasedQueue<String> q = new ArrayBasedQueue<>(10);
        for (int i = 0; i < 15; i++) {
            q.enqueue("item" + i);
        }

        for (int i = 0; i < 15; i++) {
            System.out.println(q.dequeue());
        }

    }
}
