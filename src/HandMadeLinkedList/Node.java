package HandMadeLinkedList;

import Task.Task;

public class Node<E extends Task> {
    E data;
    Node<E> prev;
    Node<E> next;

    public Node(Node<E> prev, E data, Node<E> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public E getData(){ return data;}
}
