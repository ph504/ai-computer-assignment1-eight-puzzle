package client.algorithms;

public class Stack<E> implements DataStructures<E>{
    java.util.Stack<E> stack = new java.util.Stack<>();
    @Override
    public E removeAccoringly() {
        return  stack.pop();
    }

    @Override
    public void add(E element) {
        stack.push(element);
    }

    @Override
    public int size(){
        return stack.size();
    }
}
