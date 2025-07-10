import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Main game panel that handles rendering, game logic, and input
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 16; // ~60 FPS
    
    // Modern color scheme
    private static final Color BACKGROUND_COLOR = new Color(23, 32, 42);
    private static final Color GRID_COLOR = new Color(52, 73, 94, 100);
    private static final Color UI_COLOR = new Color(236, 240, 241);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    
    private Snake snake;
    private Food food;
    private ParticleSystem particleSystem;
    private SoundManager soundManager;
    private GameState gameState;
    private Timer timer;
    
    private int score;
    private boolean running;
    private Font gameFont;
    private Font scoreFont;
    
    // Animation variables
    private float gameTime;
    private long lastUpdateTime;
    
    public GamePanel() {
        initializePanel();
        initializeGame();
        startGame();
    }
    
    /**
     * Sets up the panel properties and fonts
     */
    private void initializePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        addKeyListener(new GameKeyAdapter());
        
        // Initialize fonts
        try {
            gameFont = new Font("Arial", Font.BOLD, 20);
            scoreFont = new Font("Arial", Font.BOLD, 16);
        } catch (Exception e) {
            gameFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
            scoreFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        }
    }
    
    /**
     * Initializes game objects and systems
     */
    private void initializeGame() {
        snake = new Snake(UNIT_SIZE);
        food = new Food(UNIT_SIZE, PANEL_WIDTH, PANEL_HEIGHT);
        particleSystem = new ParticleSystem();
        soundManager = new SoundManager();
        gameState = GameState.PLAYING;
        
        score = 0;
        running = false;
        gameTime = 0;
        lastUpdateTime = System.currentTimeMillis();
        
        timer = new Timer(DELAY, this);
    }
    
    /**
     * Starts the game loop
     */
    public void startGame() {
        running = true;
        timer.start();
    }
    
    /**
     * Main game update method called by timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && gameState == GameState.PLAYING) {
            updateGame();
        }
        repaint();
    }
    
    /**
     * Updates all game logic
     */
    private void updateGame() {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
        lastUpdateTime = currentTime;
        gameTime += deltaTime;
        
        // Update snake
        snake.update();
        
        // Update particle system
        particleSystem.update(deltaTime);
        
        // Check food collision
        if (snake.checkFoodCollision(food.getX(), food.getY())) {
            score++;
            snake.grow();
            
            // Create particle effect
            particleSystem.createFoodParticles(food.getX(), food.getY(), UNIT_SIZE);
            
            // Play sound effect
            soundManager.playEatSound();
            
            // Generate new food
            food.generateNewFood(snake.getBodyParts());
        }
        
        // Check collisions
        if (snake.checkWallCollision(PANEL_WIDTH, PANEL_HEIGHT) || snake.checkSelfCollision()) {
            gameState = GameState.GAME_OVER;
            soundManager.playGameOverSound();
            running = false;
        }
    }
    
    /**
     * Custom paint method with modern graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if (gameState == GameState.PLAYING) {
            drawGame(g2d);
        } else if (gameState == GameState.GAME_OVER) {
            drawGameOver(g2d);
        }
        
        drawUI(g2d);
    }
    
    /**
     * Draws the main game elements
     */
    private void drawGame(Graphics2D g2d) {
        // Draw subtle grid
        drawGrid(g2d);
        
        // Draw food with glow effect
        food.draw(g2d, gameTime);
        
        // Draw snake with modern styling
        snake.draw(g2d, gameTime);
        
        // Draw particle effects
        particleSystem.draw(g2d);
    }
    
    /**
     * Draws a subtle grid background
     */
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(GRID_COLOR);
        g2d.setStroke(new BasicStroke(1));
        
        // Draw vertical lines
        for (int i = 0; i < PANEL_WIDTH / UNIT_SIZE; i++) {
            g2d.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, PANEL_HEIGHT);
        }
        
        // Draw horizontal lines
        for (int i = 0; i < PANEL_HEIGHT / UNIT_SIZE; i++) {
            g2d.drawLine(0, i * UNIT_SIZE, PANEL_WIDTH, i * UNIT_SIZE);
        }
    }
    
    /**
     * Draws the game over screen
     */
    private void drawGameOver(Graphics2D g2d) {
        // Draw semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        
        // Draw game over text
        g2d.setColor(UI_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics metrics = g2d.getFontMetrics();
        String gameOverText = "GAME OVER";
        int x = (PANEL_WIDTH - metrics.stringWidth(gameOverText)) / 2;
        int y = PANEL_HEIGHT / 2 - 50;
        g2d.drawString(gameOverText, x, y);
        
        // Draw final score
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        metrics = g2d.getFontMetrics();
        String finalScoreText = "Final Score: " + score;
        x = (PANEL_WIDTH - metrics.stringWidth(finalScoreText)) / 2;
        y = PANEL_HEIGHT / 2 + 10;
        g2d.drawString(finalScoreText, x, y);
        
        // Draw restart instruction
        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        metrics = g2d.getFontMetrics();
        String restartText = "Press SPACE to restart";
        x = (PANEL_WIDTH - metrics.stringWidth(restartText)) / 2;
        y = PANEL_HEIGHT / 2 + 50;
        g2d.drawString(restartText, x, y);
    }
    
    /**
     * Draws the UI elements (score, etc.)
     */
    private void drawUI(Graphics2D g2d) {
        g2d.setColor(UI_COLOR);
        g2d.setFont(scoreFont);
        FontMetrics metrics = g2d.getFontMetrics();
        
        // Draw score
        String scoreText = "Score: " + score;
        g2d.drawString(scoreText, 10, metrics.getHeight() + 5);
        
        // Draw FPS (for debugging)
        if (running) {
            String fpsText = "FPS: " + Math.round(1000.0 / DELAY);
            g2d.drawString(fpsText, PANEL_WIDTH - metrics.stringWidth(fpsText) - 10, metrics.getHeight() + 5);
        }
    }
    
    /**
     * Restarts the game
     */
    private void restartGame() {
        snake = new Snake(UNIT_SIZE);
        food = new Food(UNIT_SIZE, PANEL_WIDTH, PANEL_HEIGHT);
        particleSystem = new ParticleSystem();
        
        score = 0;
        running = true;
        gameState = GameState.PLAYING;
        gameTime = 0;
        lastUpdateTime = System.currentTimeMillis();
        
        timer.start();
    }
    
    /**
     * Handles keyboard input
     */
    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            
            if (gameState == GameState.PLAYING) {
                // Handle movement
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        snake.setDirection('L');
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        snake.setDirection('R');
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        snake.setDirection('U');
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        snake.setDirection('D');
                        break;
                }
            } else if (gameState == GameState.GAME_OVER) {
                // Handle restart
                if (keyCode == KeyEvent.VK_SPACE) {
                    restartGame();
                }
            }
        }
    }
}
