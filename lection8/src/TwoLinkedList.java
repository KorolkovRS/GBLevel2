public class TwoLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public void add(String data) {
        Node newNode = new Node(data);
        if (size == 0) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, String data) {
        if (index == size) {
            add(data);
            return;
        }

        Node newNode = new Node(data);
        Node prev;
        Node next;

        if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            next = getNode(index);
            prev = next.prev;

            next.prev = newNode;
            prev.next = newNode;

            newNode.next = next;
            newNode.prev = prev;
        }
        size++;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size %d", index, size));
        }
        Node current = null;

        if (index <= size / 2) {
            //System.out.println("Dir");
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            //System.out.println("Rev");
            current = tail;
            for (int i = size; i > index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    public String get(int index) {
        return getNode(index).data;
    }

    public void remove(int index) {
        if (index < 0 || index >= size || size == 0) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size %d", index, size));
        }

        if (index == 0) {
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node current = getNode(index);
            current.next.prev = current.prev;
            current.prev.next = current.next;
        }
        size--;
    }

    public int getSize() {
        return size;
    }

    public Iterator iterator() {
        return new Iterator(head);
    }

    @Override
    public String toString() {
        return "Node = " + head;
    }

    private class Node {
        private String data;
        private Node prev;
        private Node next;

        public Node(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public Node getPrev() {
            return prev;
        }

        public Node getNext() {
            return next;
        }

        @Override
        public String toString() {
            if (prev != null) {
                return String.format("[Prev: %s, Data: %s, Next: %s]", prev.data, data, next);
            }
            return String.format("[Prev: %s, Data: %s, Next: %s]", null, data, next);
        }
    }

    public static class Iterator {
        private Node head;
        private Node current;

        private Iterator() {
        }

        private Iterator(Node current) {
            this.head = current;
        }

        public boolean hasNext() {
            if (current == null) {
                return head != null;
            }
            return current.getNext() != null;
        }

        public String next() {
            if (current == null) {
                current = head;
            } else {
                current = current.getNext();
            }
            return current.getData();
        }
    }
}
