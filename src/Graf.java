/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchgraf;

import java.util.ArrayList;
import java.util.List;

/**
 * The Graf class represents a graph with nodes and connections.
 *
 * @author Boubik
 */
public class Graf {

    // Class variables
    private Node start;
    private Node end;
    private List<Node> nodes;

    /**
     * Constructs a Graf instance with a start node, an end node, and a list of
     * nodes.
     *
     * @param start The starting node.
     * @param end The ending node.
     * @param list The list of nodes in the graph.
     */
    public Graf(Node start, Node end, List<Node> list) {
        setStart(start);
        setEnd(end);
        setList(list);
    }

    /**
     * Sets the list of nodes for the graph.
     *
     * @param list The list of nodes to set.
     */
    public void setList(List<Node> list) {
        this.nodes = new ArrayList<>(list);
    }

    /**
     * Sets the start node of the graph.
     *
     * @param start The start node to set.
     */
    public void setStart(Node start) {
        this.start = start;
    }

    /**
     * Sets the end node of the graph.
     *
     * @param end The end node to set.
     */
    public void setEnd(Node end) {
        this.end = end;
    }

    /**
     * Retrieves the start node of the graph.
     *
     * @return The start node.
     */
    public Node getStart() {
        return start;
    }

    /**
     * Retrieves the end node of the graph.
     *
     * @return The end node.
     */
    public Node getEnd() {
        return end;
    }

    /**
     * Retrieves the x-coordinate of the start node.
     *
     * @return The x-coordinate of the start node.
     */
    public int getStartX() {
        return start.getX();
    }

    /**
     * Retrieves the y-coordinate of the start node.
     *
     * @return The y-coordinate of the start node.
     */
    public int getStartY() {
        return start.getY();
    }

    /**
     * Retrieves the x-coordinate of the end node.
     *
     * @return The x-coordinate of the end node.
     */
    public int getEndX() {
        return end.getX();
    }

    /**
     * Retrieves the y-coordinate of the end node.
     *
     * @return The y-coordinate of the end node.
     */
    public int getEndY() {
        return end.getY();
    }
    
    /**
     * This will print out side table of Dijkstra's algorithm
     * @param table of strigns from side table
     */
    public void printSideTable(List<String> table){
        System.out.println("\nSide table");
        for (String item : table) {
            System.out.println(item);
        }
    }

    /**
     * Calculates and returns the shortest path using Dijkstra's algorithm.
     *
     * @return A string representation of the shortest path.
     */
    public String getShortestPath() {
        // Initialization
        List<Node> unvisited = new ArrayList<>(nodes);
        List<Node> visited = new ArrayList<>();
        List<String> table = new ArrayList<>();
        Node currentNode = new Node();
        Node subCurrentNode = new Node();
        List<Connection> currentConnections = new ArrayList<>();

        // Initializing table values
        nodes.forEach((item) -> {
            if (start == item) {
                table.add(item.getX() + "," + item.getY() + ";0" + ";");
            } else {
                table.add(item.getX() + "," + item.getY() + ";9999999" + ";");
            }
        });
        
        // Print side table init
        printSideTable(table);

        // Dijkstra's algorithm
        int k = 0;
        while (unvisited.size() != 1) {
            int startRank = 0;
            int lovest = -1;
            int i = 0;
            while (i < table.size()) {
                String[] values = table.get(i).split(";");
                subCurrentNode = getNode(values[0]);
                if ((lovest == -1 || Integer.parseInt(values[1]) < lovest) && unvisited.contains(subCurrentNode)) {
                    lovest = Integer.parseInt(values[1]);
                    currentNode = subCurrentNode;
                    currentConnections = currentNode.GetConnection();
                    startRank = Integer.parseInt(values[1]);
                }
                i++;
            }

            Node a = new Node();
            Node b = new Node();
            for (Connection item : currentConnections) {
                int rank = startRank + item.getRank();
                if (currentNode == item.getA()) {
                    a = item.getA();
                    b = item.getB();
                } else {
                    a = item.getB();
                    b = item.getA();
                }

                i = 0;
                while (i < table.size()) {
                    String[] values = table.get(i).split(";");
                    subCurrentNode = getNode(values[0]);
                    if (b == subCurrentNode && (rank < Integer.parseInt(values[1]) || Integer.parseInt(values[1]) == -1)) {
                        table.set(i, values[0] + ";" + rank + ";" + getNodePositionInTable(table, currentNode));
                        break;
                    }
                    i++;
                }
            }
            visited.add(a);
            unvisited.remove(a);
            k++;
        }

        // Showing table values
        printSideTable(table);

        // Constructing the shortest path
        String path = end.getName() + "[" + end.getX() + "," + end.getY() + "]";
        String zacatek = "";
        Node curr = end;
        int i = 0;
        while (curr != start) {
            for (String item : table) {
                String[] snode = item.split(";");
                Node susCurr = getNode(snode[0]);
                if (susCurr == end) {
                    zacatek = "Délka: " + snode[1] + "\nCesta: ";
                }
                if (curr == susCurr) {
                    String[] next = table.get(Integer.parseInt(snode[2])).split(";");
                    curr = getNode(next[0]);
                    path = curr.getName() + "[" + curr.getX() + "," + curr.getY() + "]" + " -> " + path;
                    break;
                }
            }
            i++;
        }

        return zacatek + path;
    }

    /**
     * Retrieves a Node based on its coordinates.
     *
     * @param value The coordinates of the Node in the format "x,y".
     * @return The Node with the specified coordinates, or null if not found.
     */
    public Node getNode(String value) {
        String[] snode = value.split(",");

        for (Node item : nodes) {
            if (item.getX() == Integer.parseInt(snode[0]) && item.getY() == Integer.parseInt(snode[1])) {
                return item;
            }
        }

        return null;
    }

    /**
     * Retrieves the position of a Node in the table.
     *
     * @param table The list of strings representing the table.
     * @param currNode The Node whose position is being sought.
     * @return The index of the Node in the table, or -1 if not found.
     */
    public int getNodePositionInTable(List<String> table, Node currNode) {
        for (String item : table) {
            String[] values = item.split(";");
            String[] snode = values[0].split(",");
            if (currNode.getX() == Integer.parseInt(snode[0]) && currNode.getY() == Integer.parseInt(snode[1])) {
                return table.indexOf(item);
            }
        }
        return -1;
    }

}
