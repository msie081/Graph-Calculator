package nz.ac.auckland.se281.datastructures;

/**
 * The Linked List Class Has only one head pointer to the start node Nodes are indexed starting from
 * 0. List goes from 0 to size-1.
 *
 * @param <T> The type of each vertex, that have a total ordering
 * @author Melissa Sieu
 */
public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  public LinkedList() {
    head = null;
    tail = null;
  }

  // Key methods of the List interface

  /**
   * This method adds a node with specified data as the first node of the list.
   *
   * @param data The data to add to the list as the first node
   */
  public void prepend(T data) {
    // Note -- works even if list is empty
    Node<T> n = new Node<T>(data);
    // If there is no head, set the head and tail to the new node
    if (head == null) {
      head = n;
      tail = head;
    } else {
      // If there is a head, set the next node of the new node to the head and set the head to the
      // new node
      n.setNext(head);
      head = n;
    }
    size++;
  }

  /**
   * This method adds a node with specified data as the last node of the list.
   *
   * @param data The data to add to the list as the last node
   */
  public void append(T data) {
    // Note -- works even if list is empty
    Node<T> n = new Node<T>(data);
    // If there is no head, set the head and tail to the new node
    if (head == null) {
      head = n;
      tail = head;
    } else {
      // If there is a head, set the next node of the tail to the new node and set the tail to the
      // new node
      tail.setNext(n);
      tail = n;
    }
    size++;
  }

  /**
   * This method will fetch the value of a node at a given position.
   *
   * @param pos The position of the node to fetch
   * @return The value of the node at the given position
   */
  public T fetch(int pos) {
    Node<T> nodeAtPosition;
    // If there is no head, return null
    if (head == null) {
      return null;
    }
    // If the position is 0, return the value of the head
    nodeAtPosition = head;
    // Loops through the nodes until the position is reached
    for (int i = 1; i < pos; i++) {
      nodeAtPosition = nodeAtPosition.getNext();
    }
    // Return the value of the node at the position
    return nodeAtPosition.getValue();
  }

  /** This method removes a node at a given position. */
  public T removeHead() {
    // If there is no head, return null
    if (head == null) {
      return null;
    }
    T value = head.getValue();
    if (head.getNext() != null) {
      head = head.getNext();
    } else {
      head = null;
    }
    size--;
    return value;
  }

  /**
   * This method will find the size of a list.
   *
   * @return the size of the list
   */
  public int size() {
    return size;
  }

  /**
   * This method will check if the list is empty.
   *
   * @return true if the list is empty, false otherwise
   */
  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    }
    return false;
  }
}
