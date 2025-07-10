import java.awt.*;

/**
 * Individual particle class for visual effects
 */
public class Particle {
    private float x, y;
    private float velocityX, velocityY;
    private float life;
    private float maxLife;
    private Color color;
    private float size;
    
    public Particle(float x, float y, float velocityX, float velocityY, float life, Color color, float size) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.life = life;
        this.maxLife = life;
        this.color = color;
        this.size = size;
    }
    
    /**
     * Updates the particle's position and life
     */
    public void update(float deltaTime) {
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        life -= deltaTime;
        
        // Add gravity
        velocityY += 100 * deltaTime;
        
        // Add air resistance
        velocityX *= 0.98f;
        velocityY *= 0.98f;
    }
    
    /**
     * Draws the particle with fade effect
     */
    public void draw(Graphics2D g2d) {
        float alpha = life / maxLife;
        if (alpha > 0) {
            int alphaValue = (int) (alpha * 255);
            Color particleColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alphaValue);
            g2d.setColor(particleColor);
            
            float currentSize = size * alpha;
            g2d.fillOval((int) (x - currentSize / 2), (int) (y - currentSize / 2), 
                        (int) currentSize, (int) currentSize);
        }
    }
    
    /**
     * Checks if the particle is still alive
     */
    public boolean isAlive() {
        return life > 0;
    }
}
