package client.algorithms;

/*
 *  DataStructures class which is used for Strategy pattern in order to avoid duplicate code.
 */

public interface DataStructures<E> {

    void add(E element);
    E removeAccoringly();
    int size();
}
