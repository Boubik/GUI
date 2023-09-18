/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchgraf;

/**
 * Represents a connection between two nodes in a graph.
 * @author Boubik
 */
public class Connection {
    
    // Private instance variables to store the nodes and rank of the connection.
    private Node a;
    private Node b;
    private int rank;
    
    /**
     * Constructs a Connection object with the specified nodes and rank.
     * 
     * @param a The first node in the connection.
     * @param b The second node in the connection.
     * @param rank The rank associated with the connection.
     */
    public Connection(Node a, Node b, int rank){
        setA(a);
        setB(b);
        setRank(rank);
    }
    
    /**
     * Sets the first node of the connection.
     * 
     * @param a The first node to set.
     */
    public void setA(Node a){
        this.a = a;
    }
    
    /**
     * Sets the second node of the connection.
     * 
     * @param b The second node to set.
     */
    public void setB(Node b){
        this.b = b;
    }
    
    /**
     * Sets the rank of the connection.
     * 
     * @param rank The rank to set for the connection.
     */
    public void setRank(int rank){
        this.rank = rank;
    }
    
    /**
     * Retrieves the first node of the connection.
     * 
     * @return The first node of the connection.
     */
    public Node getA(){
        return a;
    }
    
    /**
     * Retrieves the second node of the connection.
     * 
     * @return The second node of the connection.
     */
    public Node getB(){
        return b;
    }
    
    /**
     * Retrieves the x-coordinate of the first node.
     * 
     * @return The x-coordinate of the first node.
     */
    public int getAX(){
        return a.getX();
    }
    
    /**
     * Retrieves the y-coordinate of the first node.
     * 
     * @return The y-coordinate of the first node.
     */
    public int getAY(){
        return a.getY();
    }
    
    /**
     * Retrieves the x-coordinate of the second node.
     * 
     * @return The x-coordinate of the second node.
     */
    public int getBX(){
        return b.getX();
    }
    
    /**
     * Retrieves the y-coordinate of the second node.
     * 
     * @return The y-coordinate of the second node.
     */
    public int getBY(){
        return b.getY();
    }
    
    /**
     * Retrieves the name of the first node.
     * 
     * @return The name of the first node.
     */
    public String getAName(){
        return a.getName();
    }
    
    /**
     * Retrieves the name of the second node.
     * 
     * @return The name of the second node.
     */
    public String getBName(){
        return b.getName();
    }
    
    /**
     * Retrieves the rank of the connection.
     * 
     * @return The rank of the connection.
     */
    public int getRank(){
        return rank;
    }
}
