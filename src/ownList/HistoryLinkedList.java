package ownList;

import task.Task;

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
            oldHead.prev = newNode;
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

    public int getSize() {
        return size;
    }

    public void delete(Node<T> node) {
        node.data = null;
        if (node.prev == null && node.next != null) {
            node.next.prev = null;
        } else if (node.next == null && node.prev != null) {
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}
