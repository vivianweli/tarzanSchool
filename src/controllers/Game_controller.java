package controllers;

//import javafx.animation.Animation;

import javafx.animation.KeyFrame;

import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static components.GameValue.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import components.UniqueCoordinateList;
import components.VoiceInput;

public class Game_controller {
	    @FXML
	    private ImageView bagCollectable;

	    @FXML
		public ImageView boar1;

	    @FXML
	    public ImageView boar2;

	    @FXML
	    private ImageView bookCollectable;

	    @FXML
	    private Rectangle brickWall1;

	    @FXML
	    private Rectangle brickWall2;

	    @FXML
	    private Rectangle brickWall3;

	    @FXML
	    private BorderPane gamePane;

	    @FXML
	    private HBox infoPane;

	    @FXML
	    private HBox itemPane;

	    @FXML
	    private GridPane mazeGrid;

	    @FXML
	    private Rectangle puddleWall1;

	    @FXML
	    private Rectangle puddleWall2;

	    @FXML
	    private Rectangle puddleWall3;

	    @FXML
	    private ImageView result_bag;

	    @FXML
	    private ImageView result_boar;

	    @FXML
	    private ImageView result_book;

	    @FXML
	    private ImageView result_cloth;

	    @FXML
	    private Text result_intro;

	    @FXML
	    private ImageView result_tarzan;

	    @FXML
	    private Text result_txt;

	    @FXML
	    private ImageView shirtCollectable;

	    @FXML
		public ImageView tarzan;

	    @FXML
	    private Label timerLabel;

	    @FXML
	    private HBox txtPane;
	    
	    @FXML
	    private ImageView endPoint;

    private double mouseX, mouseY;
    
    private int countdownTime = 120;  // Set the initial countdown value
    
    @FXML
    private Button PauseIcon, HelpIcon, StartIcon;

    private boolean isPaused = false;    
    private boolean isGameOver = false;
    private boolean isPass = false;
    private boolean isBag = false, isShirt = false, isBook = false;
    private boolean isReturnlo = false, isRestart = false;

    // animation frame key
    private Timeline forwardTimeline1;
    private Timeline backwardTimeline1;
    private Timeline forwardTimeline2;
    private Timeline backwardTimeline2;

    private Timeline countdownTimeline;
    
    // control the status after collision
    public boolean isCollisionPaused = false;  // Marks if the player is in a suspended state due to a collision
    private Timeline collisionPauseTimeline;  // 3-second pause timer
    
    private boolean isMoving = false;
        
    boolean move = false;
    UniqueCoordinateList squaresHighlighted = new UniqueCoordinateList();
    int maxSquares = 0;
    List<Label> highlightList = new ArrayList<>();
   
    int lastCol = 0;
    int lastRow = 1;
    
    Rectangle[] puddleList;

    Rectangle[] brickList;
    int[] knocksRequired;
    int currentKnocks[] = {0, 0, 0};
    Random random;
    
    private List<int[]> initialPuddlePositions = new ArrayList<>();
    private List<int[]> initialBrickPositions = new ArrayList<>();

    private boolean isFirstLeave = false;
    
    private VoiceInput voiceInput;

    @FXML
    public void initialize() {
		IsStart = true; //indicate that game has started
		// allows maze to receive keyboard focus
		mazeGrid.setFocusTraversable(true);	
	    // All the interaction event handlers
		mazeGrid.setOnMousePressed(this::handleClick);
	    mazeGrid.setOnMouseDragged(this::handleDrag);
	    mazeGrid.setOnMouseReleased(this::handleRelease);
	    mazeGrid.setOnKeyPressed(this::handleKeyPress);
	    // set focus on the maze
	    mazeGrid.requestFocus();
	    
	    voiceInput = new VoiceInput(this);
	    voiceInput.start();
	   
	    Image cloth = new Image(getClass().getResource("/images/shirtGrey.png").toExternalForm());
	    result_cloth.setImage(cloth); 
	    Image bag = new Image(getClass().getResource("/images/bagGrey.png").toExternalForm());
	    result_bag.setImage(bag); 
	    Image book = new Image(getClass().getResource("/images/bookGrey.png").toExternalForm());
	    result_book.setImage(book); 
	    // Load puddle image
		 Image puddleImg = new Image(getClass().getResource("/images/puddle.png").toExternalForm()); 
		 // Create an ImagePattern using the puddle Image
		 ImagePattern puddlePattern = new ImagePattern(puddleImg);
		 // Set the puddle image pattern as the fill for the puddle obstacles (rectangle)
		 puddleList = new Rectangle[]{puddleWall1, puddleWall2, puddleWall3};
		 for (Rectangle rect : puddleList) {
		 	rect.setFill(puddlePattern);
		 }
		 for (int i = 0; i < puddleList.length; i++) {
		     puddleList[i].setFill(puddlePattern);
		     int row = GridPane.getRowIndex(puddleList[i]);
		     int col = GridPane.getColumnIndex(puddleList[i]);
		     initialPuddlePositions.add(new int[]{row, col}); // Save initial positions
		 }
		 
		 // Load brick wall image
		 Image brickImg = new Image(getClass().getResource("/images/wall.png").toExternalForm()); 
		 // Create an ImagePattern using the puddle Image
		 ImagePattern brickPattern = new ImagePattern(brickImg);
		 // Store the bricks in a list
		 brickList = new Rectangle[]{brickWall1, brickWall2, brickWall3};
		//initialize the random object
		 random = new Random(); 
		     knocksRequired = new int[3];
		  // Each brick wall needs different random knocking number between 1 and 5 
		 for (int i = 0; i < 3; i++) {
		     knocksRequired[i] = random.nextInt(5) + 1; 
		 }
		// Set the puddle image pattern as the fill for the puddle obstacles (rectangle)
		 for (int i = 0; i < brickList.length; i++) {
		     brickList[i].setFill(brickPattern);
		     int row = GridPane.getRowIndex(brickList[i]);
		     int col = GridPane.getColumnIndex(brickList[i]);
		     initialBrickPositions.add(new int[]{row, col}); // Save initial positions
		 }
		
		timerLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: green;");
		
		Image Boar = new Image(getClass().getResource("/images/boar.png").toExternalForm());
		boar1.setImage(Boar);
		boar2.setImage(Boar);
		
		startBoarMovement();
		startCountdown();
		OnResult();

    }
    
    @FXML
    void OnStartEvent(ActionEvent event) {    	
    	RestartAction();
    }
    
    private void RestartAction() {
        // Reset the game state to its initial configuration
    	mazeGrid.setDisable(false);
        mazeGrid.setFocusTraversable(true);
        mazeGrid.setOnMousePressed(this::handleClick);
        mazeGrid.setOnMouseDragged(this::handleDrag);
        mazeGrid.setOnMouseReleased(this::handleRelease);
        mazeGrid.setOnKeyPressed(this::handleKeyPress);
        mazeGrid.requestFocus();

        clearAllHighlights();
        // Reset other states
        move = false;  // Reset move to ensure it's ready for new movement
        isFirstLeave = false;  // Reset initial movement state
        lastRow = 1;
        lastCol = 0; isReturnlo = false;  // Reset back to the starting state
        squaresHighlighted.clear();  // Clear highlighted squares
        highlightList.clear();  // Clear highlight list as well
       
        startBoarMovement();
        // Clear any highlights from the maze
        mazeGrid.getChildren().removeAll(highlightList);

        // Reinitialize game state like puddles, brick walls, etc.
        restoreGameObjects();

        // Reset timers and other UI elements
        resetTimersAndUI();
    }
    
    private void clearAllHighlights() {
        // Clear all highlighted nodes
        List<Node> nodesToRemove = new ArrayList<>();

        // Iterate over all nodes in the GridPane, finding the highlighted Label
        for (Node node : mazeGrid.getChildren()) {
            if (node instanceof Label) {
                nodesToRemove.add(node);  // 将高亮的 Label 添加到待移除列表
            }
        }

        // Removes all found highlighted labels from the GridPane
        mazeGrid.getChildren().removeAll(nodesToRemove);

        // Clear the highlighted tracking list
        highlightList.clear();
        squaresHighlighted.clear();
    }

    
    private void restoreGameObjects() {    	
    	Image cloth = new Image(getClass().getResource("/images/shirtGrey.png").toExternalForm());
  	    result_cloth.setImage(cloth); 
  	    Image bag = new Image(getClass().getResource("/images/bagGrey.png").toExternalForm());
  	    result_bag.setImage(bag); 
  	    Image book = new Image(getClass().getResource("/images/bookGrey.png").toExternalForm());
  	    result_book.setImage(book); 
  	    
        // Restore puddles and brick walls, and their initial positions
        mazeGrid.getChildren().removeAll(puddleWall1, puddleWall2, puddleWall3, brickWall1, brickWall2, brickWall3);
        
        // Restore puddles
        Image puddleImg = new Image(getClass().getResource("/images/puddle.png").toExternalForm());
        ImagePattern puddlePattern = new ImagePattern(puddleImg);
        puddleList = new Rectangle[]{puddleWall1, puddleWall2, puddleWall3};
        for (int i = 0; i < puddleList.length; i++) {
            puddleList[i].setFill(puddlePattern);
            int[] position = initialPuddlePositions.get(i);
            mazeGrid.add(puddleList[i], position[1], position[0]);
        }

        // Restore brick walls
        Image brickImg = new Image(getClass().getResource("/images/wall.png").toExternalForm());
        ImagePattern brickPattern = new ImagePattern(brickImg);
        brickList = new Rectangle[]{brickWall1, brickWall2, brickWall3};
        for (int i = 0; i < brickList.length; i++) {
            brickList[i].setFill(brickPattern);
            int[] position = initialBrickPositions.get(i);
            mazeGrid.add(brickList[i], position[1], position[0]);
        }

        // Reset knock requirements for walls
        random = new Random();
        for (int i = 0; i < 3; i++) {
            knocksRequired[i] = random.nextInt(5) + 1;
        }
    }

    private void resetTimersAndUI() {
        // Reset game state flags
        countdownTime = 120;
        isPaused = false;
        isGameOver = false;
        isPass = false;
        isBag = false;
        isShirt = false;
        isBook = false;
        isReturnlo = false;
        
     // Stop the old countdown and reset
        if (countdownTimeline != null) {
            countdownTimeline.stop();
            countdownTimeline = null;  // Setting to null ensures that a new timer is created
        }

        // Reset Tarzan's position
        GridPane.setColumnIndex(tarzan, 0);
        GridPane.setRowIndex(tarzan, 1);

        // Reset items
        mazeGrid.getChildren().removeAll(bookCollectable, shirtCollectable, bagCollectable);
        mazeGrid.add(bookCollectable, 16, 5);
        mazeGrid.add(shirtCollectable, 15, 15);
        mazeGrid.add(bagCollectable, 1, 19);

        // Reset UI elements like timer and result text
        timerLabel.setText("120");
        timerLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: green;");
        result_intro.setText("");
        result_txt.setText("");
        result_boar.setImage(null);
        result_tarzan.setImage(null);
        countdownTimeline = null;
        startCountdown();
    }
   
    private void startCountdown() {
        if (countdownTimeline == null) {
            countdownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    if (!isPaused && countdownTime > 0) {
                        countdownTime -= 1;
                        timerLabel.setText(String.format("%03d", countdownTime));
                    } else if (countdownTime <= 0) {
                        countdownTimeline.stop();
                        OnResult();
                    }
                })
            );
            countdownTimeline.setCycleCount(Timeline.INDEFINITE);
            countdownTimeline.play();  // Start countdown
        }
    }

    private void handleClick(MouseEvent event) {
    	int tarzanCol = GridPane.getColumnIndex(tarzan);
        int tarzanRow = GridPane.getRowIndex(tarzan);
      
    	mouseX = event.getX();
        mouseY = event.getY();
        
     // Get the grid index where the mouse is clicked on
        int[] indices = convertToIndice(mouseY, mouseX);
        int mouseRow = indices[0]; // Get row index
        int mouseCol = indices[1]; // Get column index
    
        // if mouse is clicked on tarzan, move flag is true
        if ((mouseRow == tarzanRow) && (mouseCol == tarzanCol)) {
        	move = true;    
        	isMoving = false; // Resets the move status to true only when the move actually starts
        }
        if (mouseRow >= 20 && mouseCol >= 21) {
            OnResult();  // Trigger win condition if player reaches goal
            return; // Stop further dragging
        }        
        checkCollision();

    }

    private void handleDrag(MouseEvent event) {
    	if (isCollisionPaused) {
            System.out.println("Player is paused due to collision, cannot move.");
            return;  // If the player is suspended due to a collision and cannot move
        }    	
    	int[] indices = convertToIndice(event.getY(), event.getX());
		int mouseRow = indices[0]; // Get row index
        int mouseCol = indices[1]; // Get column index
 		maxSquares = squaresHighlighted.getSize();
 		// max movement of 5 squares
        if (move && maxSquares <= 5 ) {
            //System.out.println("dragging");
			
            // if path does not contain a wall
        	if (!isWall(mouseCol, mouseRow)) {
        		// check if next coordinate is within 3x3 grid of the last
        		int rowDiff = Math.abs(lastRow - mouseRow);
        	    int colDiff = Math.abs(lastCol - mouseCol);
        	    // if the next coordinate is within 3x3 grid of the last
        		if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1) || (rowDiff == 0 && colDiff == 0)) {
        			// add the grid cell to the list of path
            		squaresHighlighted.addCoordinate(mouseRow, mouseCol);
            		isFirstLeave = true;
            		// track list size
            		maxSquares = squaresHighlighted.getSize();
            		// highlight the grid cell dragged over except the tarzan
            		isMoving = true; // Set isMoving to true after allowing movement
            		if (maxSquares > 1) {
            			highlightCell(mouseCol, mouseRow); //highlight the path selected
                    } 
            		// Update the current grid cell to be the last
                    lastRow = mouseRow;
                    lastCol = mouseCol;
                    
                    if (mouseRow >= 20 && mouseCol >= 21) {
                    	isPass = true;
                        OnResult();  // Trigger win condition if player reaches goal
                        return; // Stop further dragging
                    }                     
        		} else {
        			// if next selected grid is not within the 3x3 of the last
        			move = false;
        		}
        	} else { 
        		// if path contains a wall
        		move = false;
        	}            
        } else {
        }
        checkCollision();
    }

    private void handleRelease(MouseEvent event) {
    	// reset move flag
    	move = false;

    	if (isMoving) {  // Make sure that the release logic is triggered only after the move
            int[] indices = convertToIndice(event.getY(), event.getX());
            int mouseRow = indices[0];
            int mouseCol = indices[1];
         // If the player returns to the starting point and has already left the starting point, trigger the failure condition
            if (isFirstLeave && mouseRow == 1 && mouseCol == 0) {
                isReturnlo = true;
                clearAllHighlights();
                OnResult();
                return;
            }

             for (UniqueCoordinateList.Coordinate grid : squaresHighlighted.getCoordinates()) {
         		// remove the highlights
             	resetHighlights(grid.getX(), grid.getY());
             	
             	 // Gets row and column indexes
                int rowIndex = grid.getX();
                int colIndex = grid.getY();

                // Ensure that row and column indexes are valid (non-negative)
                if (rowIndex >= 0 && colIndex >= 0) {
                    GridPane.setRowIndex(tarzan, rowIndex);
                    GridPane.setColumnIndex(tarzan, colIndex);
                }
         		// check if item is collected on the path
         		checkItemCollected();
         		 // Check if player has reached the finish line at (20, 21)
         		 if (rowIndex >= 20 && colIndex >= 21) {
                     isPass = true;
                     OnResult();  // If the player reaches or exceeds the goal, a victory condition is triggered
                     return;
                 }
          }
             squaresHighlighted.clear();
             move = false;
         }
    	 checkCollision();
    }

    // Converts the x and y coordinates to a grid index
    private int[] convertToIndice(double y, double x) {
        int squareSize = 30;  // Assume that each cell is 30 pixels in size
        int row = Math.max(0, (int) (y / squareSize));  // Make sure not to return negative numbers
        int col = Math.max(0, (int) (x / squareSize)); 
        return new int[]{row, col};
    }

    private boolean isWall(int columnIndex, int rowIndex) {
        // Traverse all nodes of the GridPane
        for (javafx.scene.Node node : mazeGrid.getChildren()) {
            Integer colIndex = GridPane.getColumnIndex(node);
            Integer rowIndexNode = GridPane.getRowIndex(node);

            // Check whether the node is in the specified location
            if (colIndex != null && rowIndexNode != null && colIndex == columnIndex && rowIndexNode == rowIndex) {
                if (node instanceof Rectangle) {
                    return true;  // Touch the wall
                }
            }
        }
        return false; 
    }

    // Check if the character has collided with the boar
    private void checkCollision() {
        if (isCollisionPaused) {
            return;  // If the collision is paused, the collision is not checked
        }

        if (isColliding(tarzan, boar1)) {
            handleCollisionWithBoar();
        } else if (isColliding(tarzan, boar2)) {
            handleCollisionWithBoar();
        }
    }

    
    private void handleKeyPress(KeyEvent event) {
    	if (isCollisionPaused) {
            System.out.println("Player is paused due to collision, cannot move.");
            return;  // If the player is suspended due to a collision and cannot move
        }
    	
    	switch (event.getCode()) {
    	// if space is pressed, check if puddle needs to be cleared
        case SPACE:
        	clearPuddle();
            break;
        // if enter is pressed, check if wall needs to be knocked
        case ENTER:
        	clearBrick();
        default:
            break;
    	}
    }
    // knock down brick wall function
    private void clearBrick() {
    	Integer tarzanCol = GridPane.getColumnIndex(tarzan);
    	Integer tarzanRow = GridPane.getRowIndex(tarzan);
    	// if tarzan is within the 3x3 grid of each wall, knock it down
    	if (Math.abs(tarzanRow - 8) <= 1 && Math.abs(tarzanCol - 3) <= 1) {
    		knockWall(1);
    	} else if (Math.abs(tarzanRow - 19) <= 1 && Math.abs(tarzanCol - 6) <= 1) {
    		knockWall(2);
    	} else if (Math.abs(tarzanRow - 1) <= 1 && Math.abs(tarzanCol - 9) <= 1) {
    		knockWall(3);
    	}  else if (Math.abs(tarzanRow - 10) <= 1 && Math.abs(tarzanCol - 3) <= 1) {
    		knockWall(4);
    	}
	}
    // knock down wall action
    private void knockWall(int index) {
    	// track the number of knocks applied
    	currentKnocks[index - 1] ++;
    	// if knocks reach the desired number of knocks
    	if (currentKnocks[index - 1] >= knocksRequired[index - 1]) {
    		// remove the brick wall from the grid
    		mazeGrid.getChildren().remove(brickList[index - 1]);
        }
    }
    // clear puddle function
	private void clearPuddle() {
    	Integer tarzanCol = GridPane.getColumnIndex(tarzan);
    	Integer tarzanRow = GridPane.getRowIndex(tarzan);
    	// if tarzan is within the 3x3 grid of each puddle, remove the puddle from the grid
    	if (Math.abs(tarzanRow - 7) <= 1 && Math.abs(tarzanCol - 8) <= 1) {
    		mazeGrid.getChildren().remove(puddleList[0]);
    	} else if (Math.abs(tarzanRow - 11) <= 1 && Math.abs(tarzanCol - 10) <= 1) {
    		mazeGrid.getChildren().remove(puddleList[1]);
    	} else if (Math.abs(tarzanRow - 20) <= 1 && Math.abs(tarzanCol - 2) <= 1) {
    		mazeGrid.getChildren().remove(puddleList[2]);
    	}
		
	}
	// collect item function
		private void checkItemCollected() {
	    	Integer tarzanCol = GridPane.getColumnIndex(tarzan);
	    	Integer tarzanRow = GridPane.getRowIndex(tarzan);
	    	// if tarzan is on the same coordinate as the item, remove the item from the grid
	    	// change the image to colored at top left of the item status panel
	    	if (tarzanCol == 16 && tarzanRow == 5) {
	    		result_book.setImage(bookCollectable.getImage()); 
	            mazeGrid.getChildren().remove(bookCollectable);
	            isBook = true; 
	        } else if (tarzanCol == 15 && tarzanRow == 15) {
	        	result_cloth.setImage(shirtCollectable.getImage()); 
	            mazeGrid.getChildren().remove(shirtCollectable);
	            isShirt = true;
	        } else if (tarzanCol == 1 && tarzanRow == 19) {
	        	result_bag.setImage(bagCollectable.getImage()); 
	            mazeGrid.getChildren().remove(bagCollectable); 
	            isBag = true;
	        }			
		}
		// highlight cell function
		private void highlightCell(int col, int row) {
	    	// Create a label for highlighting
	    	Label hightlightCell = new Label();
	    	// Label has no text, just a background color of yellow
	    	hightlightCell.setStyle("-fx-background-color: yellow"); 
	    	hightlightCell.setPrefSize(30, 30); 
	    	// add the highlight to the grid
	        mazeGrid.add(hightlightCell, col, row); 
	        // add the label to a list to clear it later
	        highlightList.add(hightlightCell);	        
	    }
		   private void resetHighlights(int x, int y) {
		    	// remove the highlights from the grid
		    	for (Label highlightCell : highlightList) {
		    		mazeGrid.getChildren().remove(highlightCell);
		        }
		    	// clear the list containing all the highlights
		    	highlightList.clear();		       
		    }
     
    
    private void startBoarMovement() {
        double startX1 = 0;   // Use an offset relative to the current position
        double endX1 = 180;  
        double startX2 = 0;
        double endX2 = 270;   

     // Initializes the Timeline to control the movement of pig 1
        forwardTimeline1 = new Timeline(
        	new KeyFrame(Duration.seconds(0.05), e -> checkCollision()), // Detect collisions per frame
            new KeyFrame(Duration.seconds(4), new KeyValue(boar1.translateXProperty(), endX1))
        );
        backwardTimeline1 = new Timeline(
        		new KeyFrame(Duration.seconds(0.05), e -> checkCollision()), 
        		new KeyFrame(Duration.seconds(4), new KeyValue(boar1.translateXProperty(), startX1))
        );

        // Initializes the Timeline to control the movement of pig 2
        forwardTimeline2 = new Timeline(
        		new KeyFrame(Duration.seconds(0.05), e -> checkCollision()), 
        		new KeyFrame(Duration.seconds(3), new KeyValue(boar2.translateXProperty(), endX2))
        );
        backwardTimeline2 = new Timeline(
        		new KeyFrame(Duration.seconds(0.05), e -> checkCollision()), 
        		new KeyFrame(Duration.seconds(3), new KeyValue(boar2.translateXProperty(), startX2))
        );

        // Sets the end event of the animation
        forwardTimeline1.setOnFinished(e -> {
            boar1.setScaleX(-1);  // rollover image
            backwardTimeline1.play();  // Start reverse move
        });

        backwardTimeline1.setOnFinished(e -> {
            boar1.setScaleX(1);  
            forwardTimeline1.play();  
        });

        forwardTimeline2.setOnFinished(e -> {
            boar2.setScaleX(-1);  
            backwardTimeline2.play();  
        });

        backwardTimeline2.setOnFinished(e -> {
            boar2.setScaleX(1); 
            forwardTimeline2.play(); 
        });

        // Enable the forward movement
        forwardTimeline1.play();
        forwardTimeline2.play();
    }

    
    @FXML
    void OnHelpEvent(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Help_fxml.fxml"));
            Parent helpRoot = loader.load();
            Stage helpStage = new Stage();
            helpStage.setTitle("Help");
            helpStage.setScene(new Scene(helpRoot));

            helpStage.initModality(Modality.WINDOW_MODAL);
            helpStage.initOwner(((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()));
            helpStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
   
    }
    

    @FXML
    void OnPauseEvent(ActionEvent event) {
    	isPaused = !isPaused;
    	if (!isPaused) {
            forwardTimeline1.play();
            forwardTimeline2.play();
            backwardTimeline1.play();
            backwardTimeline2.play();
            countdownTimeline.play();  
            mazeGrid.setDisable(false);  // Resume player action
            PauseIcon.setText("Pause");  
            mazeGrid.requestFocus(); // so SPACE is properly detected in game

        } else {
            forwardTimeline1.pause();
            forwardTimeline2.pause();
            backwardTimeline1.pause();
            backwardTimeline2.pause();
            countdownTimeline.pause();  // Pause countdown
            mazeGrid.setDisable(true);  // Disable player action
            PauseIcon.setText("Resume");  
        }
    }

    // 处理游戏结束
    private void GameOver() {
        forwardTimeline1.stop();
        forwardTimeline2.stop();
        backwardTimeline1.stop();
        backwardTimeline2.stop();
        countdownTimeline.stop(); 
        mazeGrid.setDisable(true); 
        //isGameOver = true; 
    }
    
    private void OnResult() {
        String resultState = "";

        // A string that determines the state of the result
        if (countdownTime <= 0 && !isPass) {
            resultState = "timeout_loss";
        } else if (countdownTime > 0 && !isPass && isReturnlo) {
            resultState = "return_loss";
        } else if (countdownTime > 0 && isPass && !isReturnlo && isShirt && isBook && isBag) {
            resultState = "win";
        } else if (countdownTime > 0 && isPass && (!isShirt || !isBook || !isBag)) {
            resultState = "pass";
        } 

        switch (resultState) {
            case "timeout_loss":
                result_intro.setText("TIMEOUT BUT YOU DIDN'T PASS"); 
                result_txt.setText("YOU LOSE!");
                result_boar.setImage(new Image(getClass().getResource("/images/boar_lose.png").toExternalForm()));
                result_tarzan.setImage(new Image(getClass().getResource("/images/tarzan_lose.png").toExternalForm()));
                GameOver();
                break;

            case "return_loss":
                result_intro.setText("YOU CAME BACK TO STARTPOINT");
                result_txt.setText("YOU LOSE!");
                result_boar.setImage(new Image(getClass().getResource("/images/boar_lose.png").toExternalForm()));
                result_tarzan.setImage(new Image(getClass().getResource("/images/tarzan_lose.png").toExternalForm()));
                GameOver();
                break;

            case "win":
                result_intro.setText("TIME USED:");
                result_txt.setText("YOU WIN!");
                result_boar.setImage(new Image(getClass().getResource("/images/boar_win.png").toExternalForm()));
                result_tarzan.setImage(new Image(getClass().getResource("/images/tarzan_win.png").toExternalForm()));
                GameOver();
                break;

            case "pass":
                result_intro.setText("TIME USED:");
                result_txt.setText("YOU PASS!");
                result_boar.setImage(new Image(getClass().getResource("/images/boar_pass.png").toExternalForm()));
                result_tarzan.setImage(new Image(getClass().getResource("/images/tarzan.png").toExternalForm()));
                GameOver();
                break;

            default:
                // No result, do nothing
                return;
        }

        // Update item status
        if (!isShirt) {
            result_cloth.setImage(new Image(getClass().getResource("/images/shirtGrey.png").toExternalForm()));
        } else {
            result_cloth.setImage(new Image(getClass().getResource("/images/cloth.png").toExternalForm()));
        }

        if (!isBook) {
            result_book.setImage(new Image(getClass().getResource("/images/bookGrey.png").toExternalForm()));
        } else {
            result_book.setImage(new Image(getClass().getResource("/images/book.png").toExternalForm()));
        }

        if (!isBag) {
            result_bag.setImage(new Image(getClass().getResource("/images/bagGrey.png").toExternalForm()));
        } else {
            result_bag.setImage(new Image(getClass().getResource("/images/bag.png").toExternalForm()));
        }
    }

    private void handleCollisionWithBoar() {
        if (isCollisionPaused) return;  // If it is already in the paused state, the collision event is not triggered repeatedly

        System.out.println("Tarzan collided with a boar!");
        isCollisionPaused = true;

        //Disable the player's movement action
        mazeGrid.setDisable(true);  // Disable all player actions

        collisionPauseTimeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> {
                System.out.println("3 seconds passed, Tarzan can move again.");
                isCollisionPaused = false; 
                mazeGrid.setDisable(false); 
            })
        );
        collisionPauseTimeline.play(); 
    }

    // Determines whether two ImageViews collide
    private boolean isColliding(ImageView a, ImageView b) {
        return a.getBoundsInParent().intersects(b.getBoundsInParent());
    }
    
}