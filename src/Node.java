/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchgraf;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node with connections in a graph.
 * @author Boubik
 */
public class Node {
    
    private int x;
    private int y;
    private String name;
    private List<Connection> connections = new ArrayList<>();
    
    /**
     * Default constructor for Node.
     */
    public Node(){
    }
    
    /**
     * Constructor for Node with coordinates.
     *
     * @param x The x-coordinate of the node.
     * @param y The y-coordinate of the node.
     */
    public Node(int x, int y){
        setX(x);
        setY(y);
    }
    
    /**
     * Sets the name of the node.
     *
     * @param name The name to set for the node.
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Retrieves the name of the node.
     *
     * @return The name of the node.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Sets the x-coordinate of the node.
     *
     * @param x The x-coordinate to set for the node.
     */
    public void setX(int x){
        this.x = x;
    }
    
    /**
     * Sets the y-coordinate of the node.
     *
     * @param y The y-coordinate to set for the node.
     */
    public void setY(int y){
        this.y = y;
    }
    
    /**
     * Retrieves the x-coordinate of the node.
     *
     * @return The x-coordinate of the node.
     */
    public int getX(){
        return x;
    }
    
    /**
     * Retrieves the y-coordinate of the node.
     *
     * @return The y-coordinate of the node.
     */
    public int getY(){
        return y;
    }
    
    /**
     * Adds a connection to the list of connections for this node.
     *
     * @param connection The connection to add.
     */
    public void addConnection(Connection connection){
        this.connections.add(connection);
    }
    
    /**
     * Retrieves the list of connections associated with this node.
     *
     * @return The list of connections.
     */
    public List<Connection> GetConnection(){
        return this.connections;
    }
}
