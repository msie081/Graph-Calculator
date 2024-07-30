package nz.ac.auckland.se281.datastructures;

/**
 * A Queue is a data structure that follows the First In First Out (FIFO) principle.
 *
 * @param <T> The type of each vertex, that have a total ordering
 */
public class Queue<T> extends LinkedList<T> {

  // Add a new element to the end of the queue
  public void enqueue(T element) {
    append(element);
  }

  // Remove and return the element at the front of the queue
  public T dequeue() {
    return removeHead();
  }
}
