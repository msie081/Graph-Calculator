package nz.ac.auckland.se281.datastructures;

/**
 * Node class for use in linked lists.
 *
 * @param <T> The type of each vertex, that have a total ordering
 */
public class Node<T> {
  private T val;
  private Node<T> next;

  // constructor

  public Node() {}

  /**
   * Constructor for Node.
   *
   * @param v The value of the node
   */
  public Node(T v) {
    val = v;
    this.next = null;
    ;
  }

  public Node(T v, Node<T> next) {
    val = v;
    this.next = next;
  }

  // getters and setters

  public void setNext(Node<T> n) {
    next = n;
  }

  public Node<T> getNext() {
    return next;
  }

  public T getValue() {
    return val;
  }
}
