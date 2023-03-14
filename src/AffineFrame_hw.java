//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AffineFrame_hw extends JFrame {
    JPanel cotrolPanelMain = new JPanel();
    JPanel cotrolPanelShow = new JPanel();
    JPanel cotrolPanelBackColor = new JPanel();
    JPanel cotrolPanelTranslate = new JPanel();
    JPanel cotrolPanelScale = new JPanel();
    JPanel cotrolPanelRotate = new JPanel();
    JPanel cotrolPanelShear = new JPanel();
    ImagePanel imagePanel;
    JButton btnShow;
    JButton btnTranslate;
    JButton btnScale;
    BufferedImage img;
    BufferedImage newImg;
    JTextField tfDeltaX = new JTextField(5);
    JTextField tfDeltaY = new JTextField(5);
    JTextField tfAmpX = new JTextField(5);
    JTextField tfAmpY = new JTextField(5);
    JLabel lbDeltaY = new JLabel("y軸位移");
    JLabel lbDeltaX = new JLabel("x軸位移");
    JLabel lbAmpX = new JLabel("x軸倍率");
    JLabel lbAmpY = new JLabel("y軸倍率");
    final int[][][] data;
    static int height;
    static int width;
    static boolean loadImage = false;
    BufferedImage img1 = null;

    int nowPosX = 0 , nowPosY = 0;

    AffineFrame_hw() {
        this.setBounds(0, 0, 1500, 1500);
        this.getContentPane().setLayout((LayoutManager)null);
        this.tfDeltaX.setText("0");
        this.tfDeltaY.setText("0");
        this.tfAmpX.setText("1.0");
        this.tfAmpY.setText("1.0");
        this.setTitle("Affine Transform Homework");

        try {
            this.img = ImageIO.read(new File("/home/sherloxk/Downloads/homework/plate.png"));
            loadImage = true;
        } catch (IOException var2) {
            System.out.println("IO exception");
        }

        this.height = this.img.getHeight();
        this.width = this.img.getWidth();
        this.data = new int[this.height][this.width][3];
        this.btnShow = new JButton("顯示");
        this.btnTranslate = new JButton("平移");
        this.btnScale = new JButton("放大/縮小");
        this.cotrolPanelMain = new JPanel();
        this.cotrolPanelMain.setLayout(new GridLayout(1, 7));
        this.cotrolPanelShow.add(this.btnShow);
        this.cotrolPanelMain.add(this.cotrolPanelShow);
        this.cotrolPanelMain.add(this.cotrolPanelBackColor);
        this.cotrolPanelTranslate.add(this.lbDeltaX);
        this.cotrolPanelTranslate.add(this.tfDeltaX);
        this.cotrolPanelTranslate.add(this.lbDeltaY);
        this.cotrolPanelTranslate.add(this.tfDeltaY);
        this.cotrolPanelTranslate.add(this.btnTranslate);
        this.cotrolPanelMain.add(this.cotrolPanelTranslate);
        this.cotrolPanelScale.add(this.lbAmpX);
        this.cotrolPanelScale.add(this.tfAmpX);
        this.cotrolPanelScale.add(this.lbAmpY);
        this.cotrolPanelScale.add(this.tfAmpY);
        this.cotrolPanelScale.add(this.btnScale);
        this.cotrolPanelMain.add(this.cotrolPanelScale);
        this.cotrolPanelMain.add(this.cotrolPanelRotate);
        this.cotrolPanelMain.add(this.cotrolPanelShear);
        this.cotrolPanelMain.add(new JPanel());
        this.cotrolPanelMain.add(new JPanel());
        this.cotrolPanelMain.add(new JPanel());
        this.cotrolPanelMain.setBounds(0, 0, 1200, 150);
        this.getContentPane().add(this.cotrolPanelMain);
        this.imagePanel = new ImagePanel();
        this.imagePanel.setBounds(0, 180, 1500, 1500);
        this.getContentPane().add(this.imagePanel);
        this.btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                width = img.getWidth();
                height = img.getHeight();
                nowPosX = 0;
                nowPosY = 0;

                img1 = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                for (int i = 0 ; i < width; i ++){
                    for (int j = 0 ; j < height ; j ++){
                        img1.setRGB(i, j, img.getRGB(i, j));
                    }
                }

                Graphics graphics = imagePanel.getGraphics();
                imagePanel.paintComponent(graphics, img1, width, height, nowPosX, nowPosY);
            }
        });
        this.btnTranslate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (loadImage){
                    nowPosX = Integer.parseInt(tfDeltaX.getText());
                    nowPosY = Integer.parseInt(tfDeltaY.getText());
                    Graphics graphics = imagePanel.getGraphics();
                    imagePanel.paintComponent(graphics, img1, width, height, nowPosX, nowPosY);
                }
                else{
                    System.out.println("you didn't load the image. Please press \"show image\" button !");
                }
            }
        });
        this.btnScale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (loadImage){
                    double ampX = Double.parseDouble(tfAmpX.getText());
                    double ampY = Double.parseDouble(tfAmpY.getText());
                    int newWidth = (int) (ampX * img.getWidth());
                    int newHeight = (int) (ampY * img.getHeight());


                    img1 = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);


                    for(int i = 0 ; i < newWidth ; i ++){
                        for (int j = 0 ; j < newHeight ; j ++){
                            double[] target = new double[2];
                            target[0] = (i / ampX);
                            target[1] = (j / ampY);

//                            System.out.print("i, j : " + i + "," + j + " | ");
                            int color = Util.scaleColor(img, target);
                            img1.setRGB(i, j, color);
                        }
                    }
                    width = newWidth;
                    height = newHeight;

                    Graphics graphics = imagePanel.getGraphics();
                    imagePanel.paintComponent(graphics, img1, width, height, nowPosX, nowPosY);

                }
                else{
                    System.out.println("you didn't load the image. Please press \"show image\" button !");
                }
            }
        });
    }

    public static void main(String[] args) {
        AffineFrame_hw frame = new AffineFrame_hw();
        frame.setSize(1500, 1500);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
    }
}
