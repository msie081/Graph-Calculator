package nz.ac.auckland.se281.datastructures;

/**
 * List Interface to be implemented by singly and Doubly Linked Lists.
 *
 * @param <T> The type of each vertex, that have a total ordering
 * @author Partha Roop
 */
public interface List<T> {
  public void append(T item);

  public void prepend(T item);

  public T fetch(int pos);

  public T removeHead();

  public int size();
}
