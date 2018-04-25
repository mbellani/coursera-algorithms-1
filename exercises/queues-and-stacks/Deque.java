import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    public Deque() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        ensureNotNull(item);
        Node oldFirst = first;
        first = new Node(item);
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        size++;
    }

    public void addLast(Item item) {
        ensureNotNull(item);
        Node oldLast = last;
        last = new Node(item);
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
        }
        size++;
    }

    public Item removeFirst() {
        ensureNotEmpty();
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return item;
    }

    public Item removeLast() {
        ensureNotEmpty();
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void ensureNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null item");
        }
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("iterator already consumed or is empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        deque.addFirst("first");
        deque.addLast("last");
        System.out.println(deque.size);
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());

        deque.addFirst("first");
        deque.addLast("last");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

        deque.addFirst("first");
        deque.addFirst("beforefirst");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

        deque.addFirst("last");
        deque.addFirst("first");
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());

        deque.addFirst("first");
        deque.addLast("last");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());

        deque.addFirst("one");
        deque.addLast("two");
        deque.addLast("three");
        System.out.println(deque.size());
        for (String item : deque) {
            System.out.print(item);
        }
        for (String item : deque) {
            System.out.print(item);
        }
    }
}
