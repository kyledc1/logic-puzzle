// Program: Node
// Written by: Kyle De Castro
// Program Description: This node class creates grids for the reset button
// File name: Node.java
// File description: This file will be used by LogicPuzzle 
// Other files in this project: LogicPuzzle.java
// Challenges: Thinking of how to reset the puzzle and finally thought of making this class
// Time Spent: 30+ minutes
//
//               Revision History
// Date:                   By:                  Action:
// ---------------------------------------------------
// 05/09/2024              KDC                  created
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Node extends StackPane {
    private final Rectangle border;

    public Node(String text) {
        border = new Rectangle(50, 50);
        border.setStroke(Color.BLACK);
        border.setFill(Color.WHITE);
        getChildren().addAll(border);
    }
}
