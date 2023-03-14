import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void paintComponent(Graphics g, BufferedImage bufferedImage, int width, int height){
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, width, height, null);

    }

    public void paintComponent(Graphics g, BufferedImage bufferedImage, int width, int height, int x, int y){
        super.paintComponent(g);
        g.drawImage(bufferedImage, x, y, width, height, null);

    }


}