import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main class for the modernized Snake game
 * Initializes the game window and starts the application
 */
public class SnakeGame extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final String GAME_TITLE = "Modern Snake Game";
    
    private GamePanel gamePanel;
    
    public SnakeGame() {
        initializeWindow();
        initializeGame();
    }
    
    /**
     * Sets up the main game window with modern styling
     */
    private void initializeWindow() {
        setTitle(GAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        
        // Set modern look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        
        // Set window icon (using a simple colored rectangle as fallback)
        setIconImage(createGameIcon());
    }
    
    /**
     * Creates a simple game icon
     */
    private Image createGameIcon() {
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw a modern snake-like icon
        g2d.setColor(new Color(46, 204, 113));
        g2d.fillRoundRect(4, 4, 24, 24, 8, 8);
        g2d.setColor(new Color(39, 174, 96));
        g2d.fillRoundRect(8, 8, 16, 16, 6, 6);
        
        g2d.dispose();
        return icon;
    }
    
    /**
     * Initializes the game panel and adds it to the window
     */
    private void initializeGame() {
        gamePanel = new GamePanel();
        add(gamePanel);
        
        // Focus on the game panel for key events
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SnakeGame game = new SnakeGame();
                game.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Failed to start the game: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
