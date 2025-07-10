import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Particle system for managing visual effects
 */
public class ParticleSystem {
    private List<Particle> particles;
    private Random random;
    
    public ParticleSystem() {
        particles = new ArrayList<>();
        random = new Random();
    }
    
    /**
     * Creates particles when food is eaten
     */
    public void createFoodParticles(int x, int y, int unitSize) {
        Color[] colors = {
            new Color(231, 76, 60),
            new Color(255, 107, 91),
            new Color(255, 195, 0),
            new Color(255, 255, 255)
        };
        
        // Create 8-12 particles
        int particleCount = 8 + random.nextInt(5);
        
        for (int i = 0; i < particleCount; i++) {
            float angle = (float) (random.nextDouble() * 2 * Math.PI);
            float speed = 50 + random.nextFloat() * 100;
            float velocityX = (float) (Math.cos(angle) * speed);
            float velocityY = (float) (Math.sin(angle) * speed);
            
            float life = 0.5f + random.nextFloat() * 0.5f;
            Color color = colors[random.nextInt(colors.length)];
            float size = 3 + random.nextFloat() * 4;
            
            particles.add(new Particle(
                x + unitSize / 2 + random.nextFloat() * unitSize / 2,
                y + unitSize / 2 + random.nextFloat() * unitSize / 2,
                velocityX, velocityY, life, color, size
            ));
        }
    }
    
    /**
     * Updates all particles
     */
    public void update(float deltaTime) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(deltaTime);
            
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
    }
    
    /**
     * Draws all particles
     */
    public void draw(Graphics2D g2d) {
        for (Particle particle : particles) {
            particle.draw(g2d);
        }
    }
    
    /**
     * Clears all particles
     */
    public void clear() {
        particles.clear();
    }
}
