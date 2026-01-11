// Program: LogicPuzzle
// Written by: Kyle De Castro
// Program Description: This program uses JavaFX to make a 4x4 logic puzzle with clues and has a submit and reset button
// File name: LogicPuzzle.java
// File description: This file runs a GUI
// Other files in this project: Node.java
// Challenges: Not sure if this even counts as a logic puzzle as the best I can implement is a 4x4 grid and not
// the standard one you see online. I do not know how to make the grid extend in a similar fashion and I wanted
// to add another category which was age to make the clues more complicated
// Time Spent: 6+ hours
//
//               Revision History
// Date:                   By:                  Action:
// ---------------------------------------------------
// 05/09/2024              KDC                  created

//new stuff
//trying to merge

//hello

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LogicPuzzle extends Application {

    private final String[] structures = {"Array", "Binary Tree", "Graphs", "Linked List", "Queue"};
    private final String[] names = {"Alex", "Brad", "Charles", "Derrick", "Elton"};
    private final int[][] cellState; //0 for empty, 1 for X, 2 for O
    private final int[][] puzzleSolvedState = {//solution of puzzle
            {2, 1, 1, 1, 1},
            {1, 1, 2, 1, 1},
            {1, 1, 1, 2, 1},
            {1, 2, 1, 1, 1},
            {1, 1, 1, 1, 2}
    };


    private GridPane gridPane;

    public LogicPuzzle() {
        cellState = new int[names.length][structures.length];
    }

    @Override
    public void start(Stage primaryStage) {
        String[][] puzzle = generatePuzzle();

        //gridpane for names and structures
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //rows
        for (int i = 0; i < structures.length; i++) {
            Label structureLabel = new Label(structures[i]);
            structureLabel.setStyle("-fx-font-weight: bold;");
            gridPane.add(structureLabel, i + 1, 0);
        }

        //columns
        for (int i = 0; i < names.length; i++) {
            Label nameLabel = new Label(names[i]);
            nameLabel.setStyle("-fx-font-weight: bold;");
            gridPane.add(nameLabel, 0, i + 1);
        }

        //puzzle grid
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                Label label = new Label();
                label.setStyle("-fx-border-color: black; -fx-padding: 5px;");
                label.setMinSize(50, 50);
                final int row = i;
                final int col = j;
                label.setOnMouseClicked(event -> {//for setting adjacent cells X when user puts O
                    switch (cellState[row][col]) {
                        case 0 -> {
                            //empty cell
                            label.setText("X");
                            cellState[row][col] = 1;
                        }
                        case 1 -> {
                            //x cell
                            label.setText("O");
                            markAdjacentCells(row, col, 1); //adjacent cells remain X
                            cellState[row][col] = 2;
                        }
                        default -> {
                            //o cell
                            label.setText("");
                            markAdjacentCells(row, col, 0); //clear adjacent cells
                            cellState[row][col] = 0;
                        }
                    }
                });
                gridPane.add(label, j + 1, i + 1);
            }
        }

        //VBox to hold the grid and clues
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        Label instructionsLabel = new Label("""
                Clue 1: Alex likes to make stuff starting with the first letter of his name
                Clue 2: Brad likes interconnected things
                Clue 3: Charles can either go one way or two way
                Clue 4: Derrick is a nature lover
                Clue 5: Elton is in line
                """);

        //buttons below
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> checkPuzzle());

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetPuzzle());

        vbox.getChildren().addAll(instructionsLabel, gridPane, submitButton, resetButton);

        Scene scene = new Scene(vbox, 500, 600);

        primaryStage.setTitle("Data Structure Logic Puzzle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String[][] generatePuzzle() {
        // Create a 2D array to represent the puzzle
        String[][] puzzle = new String[names.length][structures.length];

        //assign a structure to each name sequentially
        int structureIndex = 0;
        for (int i = 0; i < names.length; i++) {
            //assign the current structure to the current name
            puzzle[i][structureIndex] = structures[structureIndex];

            //move to the next structure index
            structureIndex = (structureIndex + 1) % structures.length;
        }

        return puzzle;
    }

//using node class with same grid to clean up grid
private void resetPuzzle() {
    for (Node node : gridPane.getChildren()) {
        if (node instanceof Label label) {
            if (GridPane.getRowIndex(label) != null && GridPane.getColumnIndex(label) != null &&
                    GridPane.getRowIndex(label) != 0 && GridPane.getColumnIndex(label) != 0) {
                //reset cell state to 0 only for non-header labels
                label.setText("");
            }
        }
    }
    //reset cellState to all zeros
    for (int[] cellState1 : cellState) {
        for (int j = 0; j < cellState1.length; j++) {
            cellState1[j] = 0;
        }
    }
}


 //mark adjacent cells with a specific state
private void markAdjacentCells(int row, int col, int state) {
    for (int i = Math.max(0, row - 1); i <= Math.min(gridPane.getRowCount() - 1, row + 1); i++) {
        for (int j = Math.max(0, col - 1); j <= Math.min(gridPane.getColumnCount() - 1, col + 1); j++) {
            int currentRow = i;
            int currentCol = j;
            if (currentRow != row || currentCol != col) {
                Label adjacentLabel = (Label) gridPane.getChildren().stream()
                        .filter(n -> GridPane.getRowIndex(n) == currentRow + 1 && GridPane.getColumnIndex(n) == currentCol + 1)
                        .findFirst().orElse(null);
                if (adjacentLabel != null) {
                    if (state == 0) {
                        adjacentLabel.setText("");
                    } else if (state == 1 && cellState[currentRow][currentCol] != 2) {
                        adjacentLabel.setText("X");
                    }
                }
            }
        }
    }
}

//using node to check answer based on node game state
private void checkPuzzle() {
    //for loop to set adjacent cells on O to be X on submit as it does not count as solved even if player submits correct answer
    for (int i = 0; i < cellState.length; i++) {
        for (int j = 0; j < cellState[i].length; j++) {
            int currentRow = i;
            int currentCol = j;
            if (cellState[currentRow][currentCol] != 2) {
                Label label = (Label) gridPane.getChildren().stream()
                        .filter(n -> GridPane.getRowIndex(n) == currentRow + 1 && GridPane.getColumnIndex(n) == currentCol + 1)
                        .findFirst().orElse(null);
                if (label != null) {
                    label.setText("X");
                    cellState[currentRow][currentCol] = 1;
                }
            }
        }
    }

    //compare cellState with puzzleSolvedState
    boolean solved = true;
    for (int i = 0; i < cellState.length; i++) {
        for (int j = 0; j < cellState[i].length; j++) {
            int currentRow = i;
            int currentCol = j;
            if (cellState[currentRow][currentCol] != puzzleSolvedState[currentRow][currentCol]) {
                solved = false;
                break;
            }
        }
    }
    if (solved) {
        displayNotification("Puzzle Solved", "Congratulations! You solved the puzzle.");
    } else {
        displayNotification("Puzzle Not Solved", "Sorry, the puzzle is not solved yet.");
    }
}



    private void displayNotification(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
