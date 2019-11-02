package client.algorithms;

import java.util.LinkedList;

public class Queue<E> implements DataStructures<E>{
    private java.util.Queue<E> queue = new LinkedList<>();

    @Override
    public E removeAccoringly() {
        return queue.remove();
    }

    @Override
    public void add(E element) {
        queue.add(element);
    }

    @Override
    public int size() {
        return queue.size();
    }
}
