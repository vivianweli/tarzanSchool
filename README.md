# tarzanSchool
This is a group project that aims to create a fun maze game using novel interaction styles including sound detection, click and drag for path movement and keyboard inputs.\
The graphics are handdrawn and credited to me, Yizhen and Shangyu. My specific contribution includes maze layout, character backstory, implementing the click and drag for path movement, object collection, obstacle (puddle) and shortcut (wall) implementation and debugging of the entire project.
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
* tweak the decibel threshold (line 48) in VoiceInput.java according to your environment
## Screenshots of our game ##
<figure>
    <img src="https://github.com/user-attachments/assets/62753b57-14c4-4975-95dc-ff1e24a0d1f2" alt="Image 1" style="width: 75%;" />
    <figcaption>Start Menu</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/d4a62e26-7579-4ea1-8d6c-f7fb71d22c42" alt="Image 2" style="width: 75%;" />
    <figcaption>Character Backstory</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/b684243b-8356-453c-8469-6d450debbb2b" alt="Image 3" style="width: 75%;" />
    <figcaption>Help</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/ba576a6e-b674-4f6d-a88c-0bff79d65ac5" alt="Image 4" style="width: 75%;" />
    <figcaption>Gameplay</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/d6c8c5db-9592-42eb-b9eb-2d9c1c75fbcf" alt="Image 5" style="width: 75%;" />
    <figcaption>You Win Screen</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/5ed0017f-4189-4110-95be-14233bf6ef77" alt="Image 6" style="width: 75%;" />
    <figcaption>You Pass Screen</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/291be362-d6c3-46e8-a0a0-f5948c1338d4" alt="Image 7" style="width: 75%;" />
    <figcaption>You Lose Screen 1</figcaption>
  </figure>

  <figure>
    <img src="https://github.com/user-attachments/assets/b7445f8b-d5d2-423c-872e-73910731f7f8" alt="Image 8" style="width: 75%;" />
    <figcaption>You Lose Screen 2</figcaption>
  </figure>

