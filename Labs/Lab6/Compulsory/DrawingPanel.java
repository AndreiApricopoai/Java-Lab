package Lab6.Compulsory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {

    final MainFrame frame;

    final static int W = 800, H = 600;

    private int numVertices;
    private double edgeProbability;
    private int[] x, y;
    BufferedImage image; //the offscreen image
    Graphics2D graphics; //the tools needed to draw in the image

    public DrawingPanel(MainFrame frame) {
        super();
        this.frame = frame;
        this.createOffscreenImage();
        this.initPanel();
        this.createBoard();
    }
    private void initPanel() {

        this.setPreferredSize(new Dimension(W, H));
        this.setBorder(BorderFactory.createEtchedBorder());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed; # of clicks: " + e.getClickCount() + "; # of buttons: " + e.getButton());
                repaint();
            }
        });
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);
    }
    final void createBoard() {
        numVertices = (Integer) frame.configPanel.dotsSpinner.getValue();
        edgeProbability = (Double) frame.configPanel.linesCombo.getSelectedItem();
        createOffscreenImage();
        createVertices();
        drawLines();
        drawVertices();
        repaint();
    }
    private void createVertices() {
        int x0 = W / 2; int y0 = H / 2; //middle of the board
        int radius = H / 2 - 10; //board radius
        double alpha = 2 * Math.PI / numVertices; // the angle
        x = new int[numVertices];
        y = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            x[i] = x0 + (int) (radius * Math.cos(alpha * i));
            y[i] = y0 + (int) (radius * Math.sin(alpha * i));
        }
    }


    private void drawLines() {
        for(int i = 0 ; i < numVertices ; i++)
            for(int j = i + 1 ; j < numVertices ; j++)
                if(Math.random() < edgeProbability){  //modifiable
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.drawLine(x[i], y[i], x[j], y[j]);
                }
    }

    private void drawVertices() {
        for(int i = 0 ; i < numVertices ; i++) {
            graphics.setColor(Color.BLACK);
            graphics.fillOval(x[i] - 5, y[i] - 5, 10, 10);
        }
    }

    @Override
    public void update(Graphics g) { } //No need for update

    //Draw the offscreen image, using the original graphics
    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }
}
