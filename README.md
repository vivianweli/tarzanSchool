# tarzanSchool
a fun maze game with keyboard input, drag and drop and sound detection interaction styles

## Game Features
### 1. Main Menu
- **Start**: Starts the maze game
- **Character**: Introduction about the back story
- **Help**: Instructions about game controls
- **Quit**: Close game

### 2. Gameplay Features
- **Movement**: Tarzan moves by click, drag, release motion (NEW interaction)
  - Path is highlighted during dragging
  - Maximum of 5 squares per movement
- **Objects**: A shirt, a bag, and a book are collected along the way if Tarzan’s path crosses
  - Object status lights up in color if object is collected (otherwise, it’s grey)
- **Obstacle**: Puddle
  - Player needs to press SPACE while beside the puddle to clear it; otherwise, the path is blocked
- **Obstacle**: Boar that is roaming (NEW interaction)
  - When the player is close to a boar, by SCREAMING (or making a large noise over a certain decibel), the boar will be inactive (flickering) and Tarzan can safely pass
  - If the boar is active and collides with Tarzan, he freezes for 3 seconds and cannot move
- **Shortcut**: Brick wall
  - Player can press ENTER for an unknown amount of times (within 5) to knock down the brick wall, creating a shortcut
- **Countdown Timer**
  - 2-minute limit (120 seconds) to finish a game

### 3. Gameplay Interface Functions
- **Pause**: Player can pause the game or choose to continue (resume)
- **Help**: Player can find the tips related to the game
- **Restart**: Whether in the game or at the end of the game, the player can restart through this button

### 4. Win/Pass/Lose Conditions
- **Win**: If Tarzan gets to the exit within the time limit and collects all three objects
- **Pass**: If Tarzan gets to the exit within the time limit but did not collect all three objects
- **Lose**: If Tarzan does not get to the exit within the time limit
- **Lose**: If Tarzan travels back to the first tile after he has started traveling


## Compilation & Execution Instruction (Terminal) ##
** Instructions to run on the Terminal (on Mac, Eclipse does not give microphone access) **
1. Set the environment variable PATH_TO_FX. Add a line in your /Users/your_user_name/.zshrc file:
-     export PATH_TO_FX=/Users/your_user_name/eclipse-workspace/javafx-sdk-18.0.2/lib

2. Navigate to the /src of the project
3. Compilation
-     javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml components/*.java application/*.java controllers/*.java
4. Run
-     java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml application.main

## Screenshots of our game ##
  
