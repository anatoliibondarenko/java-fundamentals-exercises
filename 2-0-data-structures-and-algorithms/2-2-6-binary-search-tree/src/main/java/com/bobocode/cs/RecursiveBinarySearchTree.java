package com.bobocode.cs;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {
    private Node<T> root;
    private int size;
    private int depth;

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        private Node(T value) {
            this.value = value;
        }

        public static <T> Node<T> valueOf(T value) {
            return new Node<>(value);
        }
    }

    @SafeVarargs
    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> binarySearchTree = new RecursiveBinarySearchTree<>();
        Stream.of(elements).forEach(binarySearchTree::insert);
        return binarySearchTree;
    }

    @Override
    public boolean insert(T element) {

        if (root != null) {
            return insert(root, element);
        } else {
            root = Node.valueOf(element);
            size++;
            return true;
        }
    }

    private boolean insert(Node<T> node, T element) {
        if (node.value.compareTo(element) > 0) {
            if (node.left == null) {
                node.left = Node.valueOf(element);
                size++;
                return true;
            } else {
                return insert(node.left, element);
            }
        } else if (node.value.compareTo(element) < 0) {
            if (node.right == null) {
                node.right = Node.valueOf(element);
                size++;
                return true;
            } else {
                return insert(node.right, element);
            }
        }
        return false;
    }

    @Override
    public boolean contains(T element) {
        if (element == null) {
            throw new NullPointerException();
        }

        Node<T> currentNode = root;
        while (currentNode != null) {
            int compare = element.compareTo(currentNode.value);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        if (root == null) {
            return 0;
        }
        seekDepth(root, 0);
        return depth;
    }

    private void seekDepth(Node<T> node, int currentDepth) {
        int leftDepth, rightDepth;
        leftDepth = rightDepth = currentDepth;

        if (node.left != null) {
            seekDepth(node.left, currentDepth + 1);
        }

        if (leftDepth > depth) {
            depth = leftDepth;
        }

        if (node.right != null) {
            seekDepth(node.right, currentDepth + 1);
        }

        if (rightDepth > depth) {
            depth = rightDepth;
        }

    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        Node<T> node = root;
        travers(node, consumer);
    }

    private void travers(Node<T> node, Consumer<T> consumer) {

        if (node.left != null) {
            travers(node.left, consumer);
        }

        consumer.accept(node.value);

        if (node.right != null) {
            travers(node.right, consumer);
        }
    }
}
