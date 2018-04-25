import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Item[] items;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        ensureNotNull(item);
        items[size] = item;
        size++;
        if (size == items.length) {
            resize(items.length * 2);
        }
    }

    public Item dequeue() {
        ensureNotEmpty();
        int random = StdRandom.uniform(size);
        Item randomItem = items[random];
        swap(random, size - 1);
        size--;
        if (size == items.length / 4) {
            resize(items.length / 2);
        }
        return randomItem;
    }

    public Item sample() {
        ensureNotEmpty();
        return items[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private void resize(int newSize) {
        Item[] oldItems = items;
        items = (Item[]) new Object[newSize];
        System.arraycopy(oldItems, 0, items, 0, size);
    }

    private void ensureNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
    }

    private void swap(int random, int i) {
        if (i != random) {
            Item randomItem = items[random];
            items[random] = items[i];
            items[i] = randomItem;
        }
    }

    private class RandomizedIterator implements Iterator<Item> {
        private final Item[] iteratorItems = (Item[]) new Object[size()];
        private int i = 0;

        public RandomizedIterator() {
            System.arraycopy(items, 0, iteratorItems, 0, size());
            StdRandom.shuffle(iteratorItems);
        }

        @Override
        public boolean hasNext() {
            return i < iteratorItems.length;
        }

        @Override
        public Item next() {
            if (i >= iteratorItems.length) {
                throw new NoSuchElementException("Already iterate");
            }
            Item item = iteratorItems[i];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i <1000; i++) {
            rq.enqueue(i);
        }
        for (int i = 0; i <1000; i++) {
            System.out.println(rq.dequeue());
        }
    }

}