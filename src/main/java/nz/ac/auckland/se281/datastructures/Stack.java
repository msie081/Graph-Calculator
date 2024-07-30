package nz.ac.auckland.se281.datastructures;

/**
 * A Stack is a data structure that follows the Last In First Out (LIFO) principle.
 *
 * @param <T> The type of each vertex, that have a total ordering
 */
public class Stack<T> extends LinkedList<T> {

  public void push(T element) {
    // Adds the element to the top of the stack, the front of the list
    prepend(element);
  }

  public T pop() {
    // Removes and returns the element at the top of the stack, the front of the list
    return removeHead();
  }
}
