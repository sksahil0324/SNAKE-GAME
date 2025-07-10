# Modern Snake Game

A modernized version of the classic Snake game built with Java Swing, featuring smooth animations, particle effects, and contemporary visual design.

## Features

### Visual Enhancements
- **Modern Dark Theme**: Sleek dark background with subtle grid overlay
- **Smooth Animations**: 60 FPS gameplay with fluid snake movement
- **Particle Effects**: Explosive particle effects when eating food
- **Gradient Graphics**: Modern gradient styling for snake and food
- **Glow Effects**: Animated glow effects around food items
- **Animated Snake**: Snake head with eyes that follow direction, breathing animation
- **Pulsating Food**: Food items pulse and sparkle with dynamic effects

### Game Features
- **Enhanced Controls**: Arrow keys or WASD for movement
- **Collision Detection**: Precise wall and self-collision detection
- **Score System**: Real-time score tracking
- **Game Over Screen**: Modern game over display with restart option
- **Sound Effects**: Procedurally generated sound effects for eating and game over

### Technical Features
- **Object-Oriented Design**: Clean, modular code structure
- **Particle System**: Advanced particle system for visual effects
- **Sound Manager**: Built-in sound generation system
- **Smooth Rendering**: Anti-aliased graphics with modern styling
- **Responsive Input**: Prevents invalid moves (180-degree turns)

## How to Play

1. **Start the Game**: Run the application and the game starts automatically
2. **Control the Snake**: 
   - Use arrow keys (↑ ↓ ← →) or WASD to move
   - Snake cannot reverse direction directly
3. **Eat Food**: Guide the snake to the red pulsating food
4. **Avoid Collisions**: Don't hit walls or the snake's own body
5. **Game Over**: Press SPACE to restart when game ends

## Installation and Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any modern operating system (Windows, macOS, Linux)

### Running the Game
1. Compile all Java files:
   ```bash
   javac *.java
   ```

2. Run the game:
   ```bash
   java SnakeGame
   ```

## File Structure

```
├── SnakeGame.java      # Main game class and window setup
├── GamePanel.java      # Game loop, rendering, and input handling
├── Snake.java          # Snake entity with movement and collision logic
├── Food.java           # Food entity with generation and animation
├── Particle.java       # Individual particle for visual effects
├── ParticleSystem.java # Manages all particle effects
├── SoundManager.java   # Handles sound generation and playback
├── GameState.java      # Game state enumeration
└── README.md          # This file
```

## Technical Details

### Architecture
- **MVC Pattern**: Clear separation between game logic, rendering, and input
- **Entity System**: Modular entities for Snake, Food, and Particles
- **State Management**: Proper game state handling (Playing, Game Over)
- **Resource Management**: Efficient memory usage with automatic cleanup

### Graphics Features
- **Anti-aliasing**: Smooth edges and curves
- **Gradient Painting**: Modern gradient effects
- **Transparency**: Alpha blending for glow and particle effects
- **Dynamic Animations**: Time-based animations for smooth movement

### Performance
- **60 FPS**: Optimized for smooth 60 frames per second
- **Efficient Rendering**: Minimal overdraw and optimized painting
- **Memory Management**: Automatic particle cleanup and resource management

## Customization

### Visual Customization
- **Colors**: Modify color constants in each class
- **Animations**: Adjust timing and intensity in draw methods
- **Effects**: Customize particle count and behavior in ParticleSystem

### Gameplay Customization
- **Speed**: Adjust `DELAY` constant in GamePanel
- **Grid Size**: Modify `UNIT_SIZE` for different grid sizes
- **Window Size**: Change `WINDOW_WIDTH` and `WINDOW_HEIGHT`

### Sound Customization
- **Volume**: Adjust volume parameters in SoundManager
- **Frequencies**: Modify tone frequencies for different sounds
- **Duration**: Change sound duration for different effects

## Controls

| Key | Action |
|-----|---------|
| ↑ or W | Move Up |
| ↓ or S | Move Down |
| ← or A | Move Left |
| → or D | Move Right |
| Space | Restart (when game over) |

## Known Issues

- Audio system may not be available on some systems (game continues without sound)
- Performance may vary on older hardware

## Future Enhancements

- High score persistence
- Multiple difficulty levels
- Power-ups and special food items
- Background music
- Menu system with options
- Multiplayer support

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to fork this project and submit pull requests for improvements!

---

*Enjoy the modern Snake experience!* 🐍