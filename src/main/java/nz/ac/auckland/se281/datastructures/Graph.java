package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {

  // Comparator class for the list
  private class ListComparator<T> implements Comparator<T> {
    @Override
    // Compare the two vertices of type T by casting as a string and comparing the integers
    public int compare(T vertex1, T vertex2) {
      if (vertex1 instanceof String && vertex2 instanceof String) {
        String s1 = (String) vertex1;
        String s2 = (String) vertex2;
        return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
      }

      // Default comparison if types don't match
      return vertex1.toString().compareTo(vertex2.toString());
    }
  }

  private Set<T> verticies;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * This method finds the set of verticies that are roots in the graph.
   *
   * @return The set of verticies that are roots in the graph.
   */
  public Set<T> getRoots() {
    Set<T> roots = new HashSet<T>();
    if (isEquivalence()) {
      // Find the equivalence class of the vertex
      Set<T> equivalenceClass;
      for (T vertex : verticies) {
        equivalenceClass = getEquivalenceClass(vertex);

        // If the equivalence class contains more than one node, the node with the minimum value is
        // returned.
        if (equivalenceClass.size() >= 1) {
          // Find the minimum value
          T minimum = vertex;
          for (T vertex2 : equivalenceClass) {
            if (vertex2.compareTo(minimum) < 0) {
              minimum = vertex2;
            }
          }
          // Add the minimum value to the set
          roots.add(minimum);
        }
      }
    } else {
      for (T vertex : verticies) {
        // Assume it is not a root
        boolean root = false;
        for (Edge<T> edge : edges) {
          // If the in degree is not equal to the vertice and the out degree is equal to the vertice
          // then it is a root
          if ((edge.getSource() == vertex)) {
            root = true;
          }
          if (edge.getDestination() == vertex) {
            root = false;
            // Break the loop once a case is found where the in degree is equal to the vertex
            break;
          }
        }
        // If it is a root then add it to the set
        if (root == true) {
          roots.add(vertex);
        }
      }
    }
    return roots;
  }

  /**
   * Returns the set of verticies that are reachable from the given vertex.
   *
   * @return boolean, true if it is reflexive
   */
  public boolean isReflexive() {
    // For the set vertice, if in the set of edges the in and out degree is to the same vertice then
    // this means that it is reflexive.
    for (T vertex : verticies) {
      // Assume it is not reflexive
      boolean reflexive = false;
      for (Edge<T> edge : edges) {
        // If the source and destination are the same then it is reflexive
        if ((edge.getSource() == vertex) && (edge.getDestination() == vertex)) {
          reflexive = true;
        }
      }
      // If it is not reflexive then return false
      if (!reflexive) {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks if the graph is symmetric.
   *
   * @return a boolean, true if the graph is symmetric
   */
  public boolean isSymmetric() {
    // For the set of edges, if the source points to the destination and the destination points to
    // the source, it is symmetric.
    for (Edge<T> edge : edges) {
      // Assume it is not symmetric
      boolean symmetric = false;
      for (Edge<T> edge2 : edges) {
        // If the source points to the destination and the destination points to the source, it is
        // symmetric.
        if ((edge.getSource() == edge2.getDestination())
            && (edge.getDestination() == edge2.getSource())) {
          symmetric = true;
        }
      }
      // If it is not symmetric then return false
      if (!symmetric) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the graph is transitive.
   *
   * @return a boolean, true if the graph is transitive
   */
  public boolean isTransitive() {
    // For the set of edges, if If A->B and B->C then A->C, use isTransitive to find if this is
    // true.
    for (Edge<T> edge : edges) {
      for (Edge<T> edge2 : edges) {
        if (edge.getDestination() == edge2.getSource()) {
          // Find if an edge exists that connects edgesource and edge2destination does not exist
          boolean transitive = false;
          for (Edge<T> edge3 : edges) {
            // If the edge3 source and edge source are the same and the edge3 destination and edge2
            // destination are the same then it is transitive
            if (edge3.getSource() == edge.getSource()
                && edge3.getDestination() == edge2.getDestination()) {
              transitive = true;
              break;
            }
          }
          // If it is not transitive then return false
          if (!transitive) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * This method will find if the graph is anti symmetric or not.
   *
   * @return a boolean, true if the graph is anti symmetric
   */
  public boolean isAntiSymmetric() {
    // For the set of edges, if A-->B and B-->A then A==B, use isAntiSymmetric to find if this is
    // true.
    for (Edge<T> edge : edges) {
      for (Edge<T> edge2 : edges) {
        // If the edge source and edge2 destination are the same and the edge destination and edge2
        // source are the same then do the next check
        if (edge.getDestination() == edge2.getSource()
            && (edge2.getDestination() == edge.getSource())) {
          // If the source and destination are the same then it is anti symmetric
          if (edge.getSource() != edge.getDestination()) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * This method will find if the graph is of equivalence relation.
   *
   * @return a boolean, true if the graph is an equivalence relation
   */
  public boolean isEquivalence() {
    if (isReflexive() && isSymmetric() && isTransitive()) {
      return true;
    }
    return false;
  }

  /**
   * This method will find the equivalence class of a vertex.
   *
   * @param vertex an object of type T, a vertex in the graph
   * @return a set of type T, the equivalence class of the vertex
   */
  public Set<T> getEquivalenceClass(T vertex) {
    // Find the equivalence class of the vertex
    Set<T> equivalenceClass = new HashSet<T>();
    // Check if the vertex is reflexive
    if (isEquivalence()) {
      equivalenceClass.add(vertex);
      // If it is reflexive then check the destination edges from the vertex as the source and add
      // them to the equivalence class
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex)) {
          equivalenceClass.add(edge.getDestination());
        } else if (edge.getDestination().equals(vertex)) {
          equivalenceClass.add(edge.getSource());
        }
      }
    }
    return equivalenceClass;
  }

  /**
   * Method performs an iterative breadth first search on the graph.
   *
   * @return a list of type T, the nodes that have been visited in the order of the search method
   */
  public List<T> iterativeBreadthFirstSearch() {
    // List for the nodes that have been visted
    List<T> visited = new ArrayList<T>();
    // Create a queue to keep track of the nodes
    Queue<T> queue = new Queue<T>();
    // getRoots and add these to the queue
    List<T> roots = new ArrayList<T>(getRoots());
    Collections.sort(roots, new ListComparator<>());
    for (T root : roots) {
      queue.enqueue(root);
    }
    while (!queue.isEmpty()) {
      // Get the first element in the queue
      T vertex = queue.dequeue();
      // If the vertex has not been visited then add it to the visited list
      if (!visited.contains(vertex)) {
        visited.add(vertex);
      }
      List<T> orderedList = orderedEdgeList(vertex);
      for (T destination : orderedList) {
        // If the destination has not been visited then add it to the queue
        if (!visited.contains(destination)) {
          queue.enqueue(destination);
        }
      }
    }

    return visited;
  }

  /**
   * Method performs an iterative depth first search on the graph.
   *
   * @return a list of type T, the nodes that have been visited in the order of the search method
   */
  public List<T> iterativeDepthFirstSearch() {
    // Use iterative Depth First Search method to go through the graph
    // List for the nodes that have been visted
    List<T> visited = new ArrayList<T>();
    // Create a stack to keep track of the nodes
    Stack<T> stack = new Stack<T>();
    // getRoots and add these to the stack
    List<T> roots = new ArrayList<T>(getRoots());
    Collections.sort(roots, new ListComparator<>());
    Collections.reverse(roots);
    for (T root : roots) {
      stack.push(root);
      // add
    }
    while (!stack.isEmpty()) {
      // Get the first element in the stack
      T vertex = stack.pop();
      // If the vertex has not been visited then add it to the visited list
      if (!visited.contains(vertex)) {
        visited.add(vertex);
      }
      List<T> orderedList = orderedEdgeList(vertex);
      Collections.reverse(orderedList);
      for (T destination : orderedList) {
        // If the destination has not been visited then add it to the stack
        if (!visited.contains(destination)) {
          stack.push(destination);
        }
      }
    }

    return visited;
  }

  /**
   * Method takes in a vertex and returns a list of the vertices that are connected to it after
   * sorting them.
   *
   * @param vertex an object of type T, a vertex in the graph
   * @return a list of type T, the sorted vertices that are connected to the vertex
   */
  public List<T> orderedEdgeList(T vertex) {
    List<T> destinations = new ArrayList<T>();
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        destinations.add(edge.getDestination());
      }
    }
    // Sort the list
    Collections.sort(destinations, new ListComparator<>());
    return destinations;
  }

  /**
   * Method performs a recursive breadth first search on the graph.
   *
   * @return a list of type T, the nodes that have been visited in the order of the search method
   */
  public List<T> recursiveBreadthFirstSearch() {
    // List for the nodes that have been visted
    List<T> visited = new ArrayList<T>();
    // Create a queue to keep track of the nodes
    Queue<T> queue = new Queue<T>();
    // getRoots and add these to the queue
    List<T> roots = new ArrayList<T>(getRoots());
    Collections.sort(roots, new ListComparator<>());
    for (T root : roots) {
      queue.enqueue(root);
    }
    System.out.println("QUEUE " + queue);
    // Call the recursiveBreadthFirstSearchHelper method
    recursiveBreadthFirstSearchHelper(queue, visited);
    return visited;
  }

  /**
   * Method is a helper method for the recursiveBreadthFirstSearch method.
   *
   * @param queue a queue of type T, the queue of vertices
   * @param visited a list of type T, the list of visited vertices
   */
  private void recursiveBreadthFirstSearchHelper(Queue<T> queue, List<T> visited) {
    // If the queue is empty then return
    if (queue.isEmpty()) {
      return;
    }
    // Get the first element in the queue and dequeue it
    T vertex = queue.dequeue();
    if (!visited.contains(vertex)) {
      visited.add(vertex);
    } // The desintation will be ordered with the orderedEdgeList method
    for (T destination : orderedEdgeList(vertex)) {
      // If the vertex has not been visited then add it to the visited list and add it to the queue
      if (!visited.contains(destination)) {
        queue.enqueue(destination);
      }
    }
    // Call the recursiveBreadthFirstSearchHelper method recursively
    recursiveBreadthFirstSearchHelper(queue, visited);
  }

  /**
   * Method performs a recursive depth first search on the graph.
   *
   * @return a list of type T, the nodes that have been visited in the order of the search method
   */
  public List<T> recursiveDepthFirstSearch() {
    // Use recursive Depth First Search method to go through the graph
    // List for the nodes that have been visted
    List<T> visited = new ArrayList<T>();
    // Create a stack to keep track of the nodes
    Stack<T> stack = new Stack<T>();
    List<T> roots = new ArrayList<T>(getRoots());
    Collections.sort(roots, new ListComparator<>());
    Collections.reverse(roots);
    for (T root : roots) {
      stack.push(root);
      // add
    }
    // Call the recursiveDepthFirstSearchHelper method
    recursiveDepthFirstSearchHelper(stack, visited);
    return visited;
  }

  /**
   * Method is a helper method for the recursiveDepthFirstSearch method.
   *
   * @param stack a stack of type T, the stack of vertices
   * @param visited a list of type T, the list of visited vertices
   */
  private void recursiveDepthFirstSearchHelper(Stack<T> stack, List<T> visited) {
    // If the stack is empty then return
    if (stack.isEmpty()) {
      return;
    }
    // Get the first element in the stack and pop it
    T vertex = stack.pop();
    // If the vertex has not been visited then add it to the visited list
    if (!visited.contains(vertex)) {
      visited.add(vertex);
    }
    List<T> orderedList = orderedEdgeList(vertex);
    Collections.reverse(orderedList);
    for (T destination : orderedList) {
      // If the destination has not been visited then add it to the stack
      if (!visited.contains(destination)) {
        stack.push(destination);
      }
    }
    // Call the recursiveDepthFirstSearchHelper method recursively
    recursiveDepthFirstSearchHelper(stack, visited);
  }
}
