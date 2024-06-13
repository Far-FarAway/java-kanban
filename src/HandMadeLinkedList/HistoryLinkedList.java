package HandMadeLinkedList;

import Task.Task;
public class HistoryLinkedList<T extends Task> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public void addFirst(T data) {
        Node<T> oldHead = head;
        Node<T> newNode = new Node<T>(null, data, oldHead);
        head = newNode;
        if (oldHead == null) {
            tail = newNode;
        } else {
            oldHead.next = newNode;
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> oldTail = tail;
        Node<T> newNode = new Node<T>(oldTail, data, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    public Node<T> getFirst() {
        return head;
    }

    public Node<T> getLast() {
        return tail;
    }

    public void delete(Node<T> node){
        node.data = null;
        Node<T> prev = node.prev;
        node.prev.next = node.next;
        node.next.prev = prev;
    }
}