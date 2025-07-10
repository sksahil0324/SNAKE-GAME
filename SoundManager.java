import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Sound manager for game audio effects
 */
public class SoundManager {
    private boolean soundEnabled;
    
    public SoundManager() {
        soundEnabled = true;
        
        // Test audio system availability
        try {
            AudioSystem.getMixer(null);
        } catch (Exception e) {
            System.err.println("Audio system not available: " + e.getMessage());
            soundEnabled = false;
        }
    }
    
    /**
     * Plays a sound effect for eating food
     */
    public void playEatSound() {
        if (!soundEnabled) return;
        
        try {
            // Generate a simple beep sound programmatically
            playTone(800, 100, 0.3f);
        } catch (Exception e) {
            System.err.println("Could not play eat sound: " + e.getMessage());
        }
    }
    
    /**
     * Plays a sound effect for game over
     */
    public void playGameOverSound() {
        if (!soundEnabled) return;
        
        try {
            // Generate a descending tone for game over
            playTone(400, 300, 0.5f);
            Thread.sleep(50);
            playTone(300, 300, 0.5f);
            Thread.sleep(50);
            playTone(200, 500, 0.5f);
        } catch (Exception e) {
            System.err.println("Could not play game over sound: " + e.getMessage());
        }
    }
    
    /**
     * Generates and plays a simple tone
     */
    private void playTone(int frequency, int duration, float volume) {
        try {
            // Audio format specifications
            int sampleRate = 22050;
            int sampleSize = 16;
            int channels = 1;
            boolean signed = true;
            boolean bigEndian = false;
            
            AudioFormat format = new AudioFormat(sampleRate, sampleSize, channels, signed, bigEndian);
            
            // Generate tone data
            int numSamples = (int) (sampleRate * duration / 1000.0);
            byte[] audioData = new byte[numSamples * 2];
            
            for (int i = 0; i < numSamples; i++) {
                double time = i / (double) sampleRate;
                double amplitude = volume * Math.sin(2 * Math.PI * frequency * time);
                
                // Apply fade out to prevent clicking
                if (i > numSamples * 0.8) {
                    amplitude *= (numSamples - i) / (numSamples * 0.2);
                }
                
                short sample = (short) (amplitude * Short.MAX_VALUE);
                audioData[i * 2] = (byte) (sample & 0xff);
                audioData[i * 2 + 1] = (byte) ((sample >> 8) & 0xff);
            }
            
            // Play the sound
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            AudioInputStream ais = new AudioInputStream(bais, format, numSamples);
            
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            
            // Clean up resources after playing
            new Thread(() -> {
                try {
                    Thread.sleep(duration + 100);
                    clip.close();
                    ais.close();
                    bais.close();
                } catch (Exception e) {
                    // Ignore cleanup errors
                }
            }).start();
            
        } catch (Exception e) {
            System.err.println("Error generating tone: " + e.getMessage());
        }
    }
    
    /**
     * Enables or disables sound effects
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }
    
    /**
     * Checks if sound is enabled
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}
