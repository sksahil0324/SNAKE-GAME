import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Snake entity class that handles snake movement, growth, and collision detection
 */
public class Snake {
    private List<Point> bodyParts;
    private char direction;
    private int unitSize;
    private int bodyPartsCount;
    
    // Modern snake colors
    private static final Color HEAD_COLOR = new Color(46, 204, 113);
    private static final Color BODY_COLOR = new Color(39, 174, 96);
    private static final Color BODY_ACCENT = new Color(35, 155, 86);
    
    public Snake(int unitSize) {
        this.unitSize = unitSize;
        this.bodyParts = new ArrayList<>();
        this.direction = 'R'; // Start moving right
        this.bodyPartsCount = 3;
        
        // Initialize snake body
        for (int i = 0; i < this.bodyPartsCount; i++) {
            bodyParts.add(new Point(unitSize * (2 - i), 0));
        }
    }
    
    /**
     * Updates snake position based on current direction
     */
    public void update() {
        // Add new head
        Point newHead = new Point(bodyParts.get(0));
        
        switch (direction) {
            case 'U':
                newHead.y -= unitSize;
                break;
            case 'D':
                newHead.y += unitSize;
                break;
            case 'L':
                newHead.x -= unitSize;
                break;
            case 'R':
                newHead.x += unitSize;
                break;
        }
        
        bodyParts.add(0, newHead);
        
        // Remove tail if not growing
        if (bodyParts.size() > this.bodyPartsCount) {
            bodyParts.remove(bodyParts.size() - 1);
        }
    }
    
    /**
     * Draws the snake with modern styling and smooth animations
     */
    public void draw(Graphics2D g2d, float gameTime) {
        for (int i = 0; i < bodyParts.size(); i++) {
            Point part = bodyParts.get(i);
            
            // Calculate size with slight variation for organic look
            int size = unitSize - 2;
            if (i == 0) {
                // Draw head with special styling
                drawHead(g2d, part.x, part.y, size, gameTime);
            } else {
                // Draw body segments
                drawBodySegment(g2d, part.x, part.y, size, i, gameTime);
            }
        }
    }
    
    /**
     * Draws the snake head with eyes and modern styling
     */
    private void drawHead(Graphics2D g2d, int x, int y, int size, float gameTime) {
        // Add subtle breathing animation
        float breathe = (float) (Math.sin(gameTime * 3) * 0.1f + 1.0f);
        int animatedSize = (int) (size * breathe);
        int offset = (size - animatedSize) / 2;
        
        // Draw head with gradient effect
        GradientPaint headGradient = new GradientPaint(
            x + offset, y + offset, HEAD_COLOR.brighter(),
            x + offset + animatedSize, y + offset + animatedSize, HEAD_COLOR
        );
        g2d.setPaint(headGradient);
        g2d.fillRoundRect(x + offset + 1, y + offset + 1, animatedSize, animatedSize, 6, 6);
        
        // Draw eyes
        g2d.setColor(Color.WHITE);
        int eyeSize = 4;
        int eyeOffset = animatedSize / 4;
        
        switch (direction) {
            case 'U':
                g2d.fillOval(x + offset + eyeOffset, y + offset + eyeOffset, eyeSize, eyeSize);
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize, y + offset + eyeOffset, eyeSize, eyeSize);
                break;
            case 'D':
                g2d.fillOval(x + offset + eyeOffset, y + offset + animatedSize - eyeOffset - eyeSize, eyeSize, eyeSize);
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize, y + offset + animatedSize - eyeOffset - eyeSize, eyeSize, eyeSize);
                break;
            case 'L':
                g2d.fillOval(x + offset + eyeOffset, y + offset + eyeOffset, eyeSize, eyeSize);
                g2d.fillOval(x + offset + eyeOffset, y + offset + animatedSize - eyeOffset - eyeSize, eyeSize, eyeSize);
                break;
            case 'R':
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize, y + offset + eyeOffset, eyeSize, eyeSize);
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize, y + offset + animatedSize - eyeOffset - eyeSize, eyeSize, eyeSize);
                break;
        }
        
        // Draw pupils
        g2d.setColor(Color.BLACK);
        int pupilSize = 2;
        switch (direction) {
            case 'U':
                g2d.fillOval(x + offset + eyeOffset + 1, y + offset + eyeOffset + 1, pupilSize, pupilSize);
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize + 1, y + offset + eyeOffset + 1, pupilSize, pupilSize);
                break;
            case 'D':
                g2d.fillOval(x + offset + eyeOffset + 1, y + offset + animatedSize - eyeOffset - eyeSize + 1, pupilSize, pupilSize);
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize + 1, y + offset + animatedSize - eyeOffset - eyeSize + 1, pupilSize, pupilSize);
                break;
            case 'L':
                g2d.fillOval(x + offset + eyeOffset + 1, y + offset + eyeOffset + 1, pupilSize, pupilSize);
                g2d.fillOval(x + offset + eyeOffset + 1, y + offset + animatedSize - eyeOffset - eyeSize + 1, pupilSize, pupilSize);
                break;
            case 'R':
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize + 1, y + offset + eyeOffset + 1, pupilSize, pupilSize);
                g2d.fillOval(x + offset + animatedSize - eyeOffset - eyeSize + 1, y + offset + animatedSize - eyeOffset - eyeSize + 1, pupilSize, pupilSize);
                break;
        }
    }
    
    /**
     * Draws individual body segments with gradient and scale effects
     */
    private void drawBodySegment(Graphics2D g2d, int x, int y, int size, int index, float gameTime) {
        // Scale segments slightly smaller toward the tail
        float scale = 1.0f - (index * 0.02f);
        int scaledSize = (int) (size * scale);
        int offset = (size - scaledSize) / 2;
        
        // Alternate colors for striped effect
        Color segmentColor = (index % 2 == 0) ? BODY_COLOR : BODY_ACCENT;
        
        // Add subtle wave animation
        float wave = (float) Math.sin(gameTime * 2 + index * 0.3f) * 0.5f;
        offset += (int) wave;
        
        // Draw segment with gradient
        GradientPaint segmentGradient = new GradientPaint(
            x + offset, y + offset, segmentColor.brighter(),
            x + offset + scaledSize, y + offset + scaledSize, segmentColor
        );
        g2d.setPaint(segmentGradient);
        g2d.fillRoundRect(x + offset + 1, y + offset + 1, scaledSize, scaledSize, 4, 4);
        
        // Add highlight
        g2d.setColor(new Color(255, 255, 255, 30));
        g2d.fillRoundRect(x + offset + 2, y + offset + 2, scaledSize / 2, scaledSize / 2, 2, 2);
    }
    
    /**
     * Sets the snake's direction, preventing 180-degree turns
     */
    public void setDirection(char newDirection) {
        // Prevent snake from going backwards
        if ((direction == 'U' && newDirection == 'D') ||
            (direction == 'D' && newDirection == 'U') ||
            (direction == 'L' && newDirection == 'R') ||
            (direction == 'R' && newDirection == 'L')) {
            return;
        }
        this.direction = newDirection;
    }
    
    /**
     * Checks collision with food
     */
    public boolean checkFoodCollision(int foodX, int foodY) {
        Point head = bodyParts.get(0);
        return head.x == foodX && head.y == foodY;
    }
    
    /**
     * Checks collision with walls
     */
    public boolean checkWallCollision(int panelWidth, int panelHeight) {
        Point head = bodyParts.get(0);
        return head.x < 0 || head.x >= panelWidth || head.y < 0 || head.y >= panelHeight;
    }
    
    /**
     * Checks collision with snake's own body
     */
    public boolean checkSelfCollision() {
        Point head = bodyParts.get(0);
        for (int i = 1; i < bodyParts.size(); i++) {
            if (head.equals(bodyParts.get(i))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Grows the snake by one segment
     */
    public void grow() {
        this.bodyPartsCount++;
    }
    
    /**
     * Returns the snake's body parts for food generation
     */
    public List<Point> getBodyParts() {
        return bodyParts;
    }
}
