package asg1;

import java.util.*;

// Class to represent a graph and perform Uniform Cost Search
public class WeightedGraph {
    static class Edge {
        int source, destination, weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "< " + source + " " + destination + " " + weight + " >";
        }

        public boolean isNul() {
            return this.destination == -1;
        }
    }

    // Class to represent a graph node
    static class Node {
        int vertex, cost, heuristic;

        public Node(int vertex) {
            this.vertex = vertex;
            this.cost = 0;
        }

        public Node(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        public Node(int vertex, int cost, int heuristic) {
            this.vertex = vertex;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }

    static class NodeComparatorBEFS implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.heuristic) - (n2.heuristic);
        }
    }

    static class NodeComparatorUCS implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.cost) - (n2.cost);
        }
    }

    static class NodeComparatorA implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return (n1.cost + n1.heuristic) - (n2.cost + n2.heuristic);
        }
    }

    int edgeCount;
    List<List<Edge>> adjList;

    public WeightedGraph(int e) {
        this.edgeCount = e;
        adjList = new ArrayList<>(edgeCount + 2);
        for (int i = 0; i < edgeCount + 2; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjList.get(source).add(edge);
    }

    List<List<Edge>> getAdjList() {
        return adjList;
    }

    public static void main(String[] args) {
        Edge e = new Edge(5, 6, 1);
        System.err.println(e);
    }
}
