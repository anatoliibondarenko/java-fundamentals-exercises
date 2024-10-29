package com.bobocode.cs;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> generic type parameter
 * @author Taras Boychuk
 * @author Serhii Hryhus
 */
public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;


    private static class Node<T> {
        private T value;
        private Node<T> next;

        private static <T> Node<T> valueOf(T element) {
            return new Node<>(element);
        }

        private Node(T value) {
            this.value = value;
        }
    }

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements they were passed as method parameters
     */
    @SafeVarargs
    public static <T> LinkedList<T> of(T... elements) {
        LinkedList<T> linkedList = new LinkedList<>();
        Stream.of(elements).forEach(linkedList::add);
        return linkedList;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        add(size, element);
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        if (index != 0 && index != size) {
            checkIndex(index);
        }

        Node<T> newNode = Node.valueOf(element);

        if (index != 0 && index != size) {
            Node<T> prevNode = moveToIndex(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        } else if (size == 0) {
            newNode.next = head;
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Changes the value of a list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   a position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        checkIndex(index);
        Node<T> node = moveToIndex(index);
        node.value = element;
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> nodeByIndex = moveToIndex(index);
        return nodeByIndex.value;
    }


    private Node<T> moveToIndex(int index) {
        Node<T> nodeByIndex = head;
        for (int i = 0; i < index; i++) {
            nodeByIndex = nodeByIndex.next;
        }

        return nodeByIndex;
    }

    /**
     * Returns the first element of the list. Operation is performed in constant time O(1)
     *
     * @return the first element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getFirst() {
        if (head != null) {
            return head.value;
        } else {
            throw new NoSuchElementException();
        }

    }

    /**
     * Returns the last element of the list. Operation is performed in constant time O(1)
     *
     * @return the last element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getLast() {
        if (tail != null) {
            return tail.value;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return deleted element
     */
    @Override
    public T remove(int index) {

        checkIndex(index);
        Node<T> deletedNode;
        if (index != 0 ) {
            Node<T> prevDeletedNode = moveToIndex(index - 1);
            deletedNode = prevDeletedNode.next;
            prevDeletedNode.next = deletedNode.next;
            deletedNode.next = null;
            if (index == size - 1) {
                tail = prevDeletedNode;
            }
        } else {
            deletedNode = head;
            head = head.next;
            deletedNode.next = null;
        }

        size--;
        return deletedNode.value;
    }


    /**
     * Checks if a specific exists in the list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == element) {
                return true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

}
