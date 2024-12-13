package io.github.bird.game;
import com.badlogic.gdx.math.Vector2;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    // Serialize version for compatibility
    private static final long serialVersionUID = 1L;

    // Positions and active states of pigs
    public List<Vector2> pigPositions; // List of pig positions
    public List<Boolean> pigActiveStates; // Whether each pig is active or not

    // Positions of the birds and remaining count
    public List<Vector2> birdPositions; // List of positions for birds in play
    public int birdsRemaining; // Birds left to be used

    // Positions and types of blocks
    public List<Vector2> blockPositions; // Positions of blocks
    public List<String> blockTypes; // Types of blocks (e.g., "wood", "glass", "steel")

    // Current bird type being used (e.g., red, black, yellow)
    public int currentBirdType;

    // Slingshot position
    public Vector2 slingshotPosition;

    // Trail of the bird's movement (for visual representation)
    public List<Vector2> birdTrail;

    // Additional game state information can be added as needed
    // For example:
    public int score; // Current score
    public boolean isDragging; // Whether the bird is being dragged

    // Constructor (optional, for initialization)
    public GameState(List<Vector2> pigPositions, List<Boolean> pigActiveStates,
                     List<Vector2> birdPositions, int birdsRemaining,
                     List<Vector2> blockPositions, List<String> blockTypes,
                     int currentBirdType, Vector2 slingshotPosition,
                     List<Vector2> birdTrail, int score, boolean isDragging) {
        this.pigPositions = pigPositions;
        this.pigActiveStates = pigActiveStates;
        this.birdPositions = birdPositions;
        this.birdsRemaining = birdsRemaining;
        this.blockPositions = blockPositions;
        this.blockTypes = blockTypes;
        this.currentBirdType = currentBirdType;
        this.slingshotPosition = slingshotPosition;
        this.birdTrail = birdTrail;
        this.score = score;
        this.isDragging = isDragging;
    }

    // Default constructor for deserialization
    public GameState() {}
}
