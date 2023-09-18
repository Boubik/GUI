/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchgraf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;

/**
 *
 * @author Boubik
 */
public class SearchGraf extends Application {

    private static final double arrowLength = 10;
    private static final double arrowWidth = 3;
    private Node start = null; // will be first line
    private Node end = null; // will be second line
    private List<String> lines = new ArrayList<>();
    private List<Node> nodes = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();
    private final String filepath = "grafs/Graf.Boubik";
    int point = 4; // Size of points

    @Override
    public void start(Stage stage) {

        // Read a file and set start and end Node
        try {
            File file = new File(filepath);           // Create a file instance
            FileReader fr = new FileReader(file);     // Initialize a FileReader to read the file
            BufferedReader br = new BufferedReader(fr); // Create a buffered character input stream
            StringBuffer sb = new StringBuffer();      // Initialize a string buffer

            String line;
            String[] curXY;
            Node a;

            // Read lines from the file and process each line
            while ((line = br.readLine()) != null) {
                lines.add(line);                        // Store the line
                String[] values = line.split(";");       // Split line into values
                curXY = values[0].split(",");            // Split XY coordinates and name

                // Create a new Node with coordinates and name
                a = new Node(Integer.parseInt(curXY[0]), Integer.parseInt(curXY[1]));
                a.setName(curXY[2]);
                nodes.add(a);                            // Add the Node to the list

                // Assign start and end Nodes based on conditions
                if (start == null) {
                    start = a;
                } else {
                    if (end == null) {
                        end = a;
                    }
                }

                sb.append(line);                          // Append line to string buffer
                sb.append("\n");                          // Add a line feed   
            }
            fr.close();    // Close the stream and release resources
            System.out.println("Contents of File: ");
            System.out.println(sb.toString());   // Display the file contents  
        } catch (IOException e) {
            e.printStackTrace();                // Print exception details
        }

        // Process data from the file and categorize it into lists
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            String[] values = line.split(";");
            String[] xyname = values[0].split(",");
            Node a = getNodeByName(xyname[2]);
            Node b;
            Connection c;
            int rank;

            int k = 1;
            while (k < values.length) {
                String[] namerank = values[k].split(",");
                b = getNodeByName(namerank[0]);
                rank = Integer.parseInt(namerank[1]);
                c = new Connection(a, b, rank);            // Create a Connection between Nodes
                a.addConnection(c);                        // Add the Connection to Node's connections
                k++;
            }
            i++;
        }

        // Display information about all nodes
        System.out.println("Nodes");
        nodes.forEach((item) -> {
            System.out.println("X: " + item.getX() + " Y: " + item.getY());

        });

        // Initialize necessary objects for graph visualization
        Path path = new Path();
        Group root = new Group(path);
        Graf graf = new Graf(start, end, nodes);          // Create a graph with start, end, and nodes
        String str_path = graf.getShortestPath();          // Get the shortest path as a string
        List<String> conns = new ArrayList<>();

        // Extract connection information using regular expression
        String regex = " [a-zA-Z]+\\[";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str_path);
        while (matcher.find()) {
            conns.add(matcher.group().substring(1, matcher.group().length() - 1));
        }
        // Reverse the list of connections for proper order
        Collections.reverse(conns);

        nodes.forEach((Node item) -> {
            // Creating a circle representing the node at the given coordinates
            Circle circle = new Circle(item.getX(), item.getY(), point);

            // Creating a text label for the node
            Text a = new Text();
            a.setText(item.getName());
            a.setX(item.getX() + 3);
            a.setY(item.getY() - 3);

            // Setting color for nodes that are the start or end nodes
            if (start == item || end == item) {
                circle.setFill(javafx.scene.paint.Color.RED);
                a.setFill(javafx.scene.paint.Color.RED);
            }

            // Adding the circle and text label to the root container
            root.getChildren().add(circle);
            root.getChildren().add(a);

            // Getting connections of the current node
            connections = item.GetConnection();
            List<Connection> visitedCon = new ArrayList<>();

            // Iterating through connections of the current node
            connections.forEach((Connection itemc) -> {
                if (visitedCon.contains(itemc)) {
                    return; //continue but java
                }

                // Calculating half of midpoint coordinates for connection text label
                double d = Math.sqrt((itemc.getAX() - itemc.getBX()) * (itemc.getAX() - itemc.getBX()) + (itemc.getAY() - itemc.getBY()) * (itemc.getAY() - itemc.getBY())) / 4;
                Double D = Math.sqrt((itemc.getBX() - itemc.getAX()) * (itemc.getBX() - itemc.getAX()) + (itemc.getBY() - itemc.getAY()) * (itemc.getBY() - itemc.getAY()));
                int x3 = (int) ((itemc.getAX() + (d / D) * (itemc.getBX() - itemc.getAX())));
                int y3 = (int) ((itemc.getAY() + (d / D) * (itemc.getBY() - itemc.getAY())) - 5);

                // Creating and positioning the connection text label
                Text text = new Text();
                text.setText(itemc.getRank() + "");
                text.setX(x3);
                text.setY(y3);
                root.getChildren().add(text);

                // Creating lines to represent connections between nodes
                x3 = (int) ((itemc.getAX() + ((d * 2) / D) * (itemc.getBX() - itemc.getAX())));
                y3 = (int) ((itemc.getAY() + ((d * 2) / D) * (itemc.getBY() - itemc.getAY())));
                Line line = new Line(itemc.getAX(), itemc.getAY(), x3, y3);

                // Creating arrows to represent connections
                Line arrow1 = new Line();
                Line arrow2 = new Line();
                double ex = itemc.getAX();
                double ey = itemc.getAY();
                double sx = itemc.getBX();
                double sy = itemc.getBY();
                arrow1.setEndX(ex);
                arrow1.setEndY(ey);
                arrow2.setEndX(ex);
                arrow2.setEndY(ey);

                // Configuring arrowhead positions for connections
                // (calculations depend on arrowLength and arrowWidth variables)
                if (ex == sx && ey == sy) {
                    // arrow parts of length 0
                    arrow1.setStartX(ex);
                    arrow1.setStartY(ey);
                    arrow2.setStartX(ex);
                    arrow2.setStartY(ey);
                } else {
                    double factor = arrowLength / Math.hypot(sx - ex, sy - ey);
                    double factorO = arrowWidth / Math.hypot(sx - ex, sy - ey);

                    // part in direction of main line
                    double dx = (sx - ex) * factor;
                    double dy = (sy - ey) * factor;

                    // part ortogonal to main line
                    double ox = (sx - ex) * factorO;
                    double oy = (sy - ey) * factorO;
                    arrow1.setStartX(ex + dx - oy);
                    arrow1.setStartY(ey + dy + ox);
                    arrow2.setStartX(ex + dx + oy);
                    arrow2.setStartY(ey + dy - ox);
                }

                // Adjusting color for specific connections based on conditions
                if (conns.contains(itemc.getAName()) && conns.contains(itemc.getBName())) {
                    try {
                        int i1 = conns.indexOf(itemc.getBName());
                        String now = itemc.getAName();
                        String next = itemc.getBName();
                        String now2 = conns.get(--i1);
                        String next2 = conns.get(i1);

                        // color them only if there are in corect way
                        if (next2.equals(next) || now2.equals(now)) {
                            arrow1.setStroke(Color.RED);
                            arrow2.setStroke(Color.RED);
                            line.setStroke(Color.RED);
                        }
                    } catch (IndexOutOfBoundsException e) {
                    }
                }

                // Adding lines and arrowheads to the root container
                visitedCon.add(itemc);
                root.getChildren().add(line);
                root.getChildren().add(arrow1);
                root.getChildren().add(arrow2);
            });
        });

        // Creating a scene object
        Scene scene = new Scene(root, 600, 300);

        // Setting title to the Stage
        stage.setTitle("Graf");

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();

        System.out.println("\n\n" + str_path);
    }

    /**
     * The entry point of the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String args[]) {
        launch(args);  // Launches the JavaFX application
    }

    /**
     * Retrieves a Node object based on its name.
     *
     * @param name The name of the Node to be retrieved.
     * @return The Node object with the specified name, or null if not found.
     */
    public Node getNodeByName(String name) {
        int i = 0;
        while (i < nodes.size()) {
            Node node = nodes.get(i);
            if (node.getName().equals(name)) {
                return nodes.get(i);  // Returns the Node object with the matching name
            }
            i++;
        }

        return null;  // Returns null if no Node with the specified name is found
    }
}
