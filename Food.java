import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Food entity class that handles food generation and rendering
 */
public class Food {
    private int x;
    private int y;
    private int unitSize;
    private int panelWidth;
    private int panelHeight;
    private Random random;
    
    // Modern food colors
    private static final Color FOOD_COLOR = new Color(231, 76, 60);
    private static final Color FOOD_HIGHLIGHT = new Color(255, 107, 91);
    private static final Color GLOW_COLOR = new Color(231, 76, 60, 100);
    
    public Food(int unitSize, int panelWidth, int panelHeight) {
        this.unitSize = unitSize;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.random = new Random();
        
        // Generate initial food position
        generateRandomPosition();
    }
    
    /**
     * Generates a new random position for the food
     */
    private void generateRandomPosition() {
        x = random.nextInt(panelWidth / unitSize) * unitSize;
        y = random.nextInt(panelHeight / unitSize) * unitSize;
    }
    
    /**
     * Generates new food position avoiding snake body
     */
    public void generateNewFood(List<Point> snakeBodyParts) {
        boolean validPosition = false;
        int attempts = 0;
        
        while (!validPosition && attempts < 100) {
            generateRandomPosition();
            validPosition = true;
            
            // Check if position conflicts with snake body
            for (Point part : snakeBodyParts) {
                if (part.x == x && part.y == y) {
                    validPosition = false;
                    break;
                }
            }
            attempts++;
        }
        
        // If we couldn't find a valid position after 100 attempts, use any position
        if (!validPosition) {
            generateRandomPosition();
        }
    }
    
    /**
     * Draws the food with modern styling and animations
     */
    public void draw(Graphics2D g2d, float gameTime) {
        // Calculate pulsing animation
        float pulse = (float) (Math.sin(gameTime * 4) * 0.1f + 0.9f);
        int size = (int) (unitSize * pulse);
        int offset = (unitSize - size) / 2;
        
        // Draw glow effect
        int glowSize = (int) (unitSize * 1.5f);
        int glowOffset = (unitSize - glowSize) / 2;
        
        // Create radial gradient for glow
        RadialGradientPaint glowGradient = new RadialGradientPaint(
            x + unitSize / 2, y + unitSize / 2, glowSize / 2,
            new float[]{0.0f, 1.0f},
            new Color[]{GLOW_COLOR, new Color(231, 76, 60, 0)}
        );
        g2d.setPaint(glowGradient);
        g2d.fillOval(x + glowOffset, y + glowOffset, glowSize, glowSize);
        
        // Draw main food body
        GradientPaint foodGradient = new GradientPaint(
            x + offset, y + offset, FOOD_HIGHLIGHT,
            x + offset + size, y + offset + size, FOOD_COLOR
        );
        g2d.setPaint(foodGradient);
        g2d.fillOval(x + offset + 1, y + offset + 1, size - 2, size - 2);
        
        // Draw highlight
        g2d.setColor(new Color(255, 255, 255, 150));
        int highlightSize = size / 3;
        g2d.fillOval(x + offset + size / 4, y + offset + size / 4, highlightSize, highlightSize);
        
        // Add sparkle effect
        drawSparkles(g2d, gameTime);
    }
    
    /**
     * Draws sparkle particles around the food
     */
    private void drawSparkles(Graphics2D g2d, float gameTime) {
        g2d.setColor(new Color(255, 255, 255, 200));
        
        for (int i = 0; i < 4; i++) {
            float angle = (float) ((gameTime * 2 + i * Math.PI / 2) % (2 * Math.PI));
            float sparkleX = x + unitSize / 2 + (float) Math.cos(angle) * unitSize;
            float sparkleY = y + unitSize / 2 + (float) Math.sin(angle) * unitSize;
            
            float sparkleIntensity = (float) (Math.sin(gameTime * 6 + i) * 0.5f + 0.5f);
            g2d.setColor(new Color(255, 255, 255, (int) (sparkleIntensity * 150)));
            g2d.fillOval((int) sparkleX - 1, (int) sparkleY - 1, 2, 2);
        }
    }
    
    /**
     * Gets the food's X position
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the food's Y position
     */
    public int getY() {
        return y;
    }
}
