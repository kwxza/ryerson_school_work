import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

public class HTree extends JPanel {

    /*
    Once line segment length is shorter 
    than cutoff, stop subdividing.
     */
    private static final double CUTOFF = 20;
    /*
    Radius of the little dot drawn
    on each intersection.
     */
    private static final double R = 8;
    /*
    The image inside which the
    H-Tree fractal is rendered.
     */
    private Image htree;
    /*
    Four possible direction vectors
    that a line segment can have.
     */
    private static final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    /*
    Four diagonal vectors that a
    line segment can have.
     */
    private static final int[][] DIAGS = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
    /*
    A random number generator for
    choosing the end piece colours.
     */
    private static final Random rng = new Random();

    //Constructor
    public HTree(int w, int h) {

        this.setPreferredSize(new Dimension(w, h));

        htree = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        /*
        Acquiring Graphics2d object for recursive calls to render().
        */
        Graphics2D htreeGraphic = (Graphics2D) htree.getGraphics();
        
        /*
        Anti-aliasing for better graphics quality.
        */
        htreeGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        /*
        Paint initial background of image 
        before htree graphic is drawn
        */
        htreeGraphic.setPaint(Color.WHITE);
        htreeGraphic.fillRect(0, 0, w, h);
        
        /*
        Initial segment length calculation.
        */
        double len = w <= h ? w / 3 : h / 3;

        render(w / 2, h / 2, 3, len, htreeGraphic);
        render(w / 2, h / 2, 1, len, htreeGraphic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(htree, 0, 0, this);
    }

    private static void render(double x, double y, int i, double len, Graphics2D g2) {
        Color discColor = Color.BLACK;
        g2.setPaint(discColor);
        g2.setStroke(new BasicStroke(1.2f));
        /*
        Base case if line segment length
        is less than the CUTOFF point.
         */
        if (len < CUTOFF) {
            /*
            Selecting fill color of end disc of line segment
            with ~50% probability of each color.
            */
            discColor = rng.nextDouble() * 2 >= 1 ? Color.LIGHT_GRAY : Color.CYAN;
            
            g2.setPaint(discColor);
            g2.fill(new Ellipse2D.Double(x - R, y - R, 2 * R, 2 * R));
            
            /*
            Changing disc color back to black
            for end disc outline.
            */
            discColor = Color.BLACK;
            
            g2.setPaint(discColor);
            g2.draw(new Ellipse2D.Double(x - R, y - R, 2 * R, 2 * R));
        } else {
            
            /*
            Finding end-coordinates of new line segment,
            using vector directions from DIRS array.
            */
            double nx = len * DIRS[i][0] + x;
            double ny = len * DIRS[i][1] + y;
            
            /*
            Generating random rotation of line vector 
            to create hand-drawn effect.
            */
            double randomRotation = -0.005 + rng.nextDouble() * 0.01;
            
            /*
            Matrix multiplication of line end-coordinates
            to achieve rotation effect.
            */
            nx = nx * Math.cos(randomRotation) - ny * Math.sin(randomRotation);
            ny= nx * Math.sin(randomRotation) + ny * Math.cos(randomRotation);
            
            /*
            Draw line segment and intersection disc.
            */
            g2.draw(new Line2D.Double(x, y, nx, ny));
            g2.fill(new Ellipse2D.Double(x - R, y - R, 2 * R, 2 * R));
            
            /*
            Handling possible IndexOutOfBounds 
            exceptions for recursive calls, ie -
            when i == 0 or when i == 3.
            */
            if (i == 0) {
                render(nx, ny, 3, len / Math.sqrt(2), g2);
                render(nx, ny, i + 1, len / Math.sqrt(2), g2);
            } else if (i == 3) {
                render(nx, ny, i - 1, len / Math.sqrt(2), g2);
                render(nx, ny, 0, len / Math.sqrt(2), g2);
            } else {
                render(nx, ny, i - 1, len / Math.sqrt(2), g2);
                render(nx, ny, i + 1, len / Math.sqrt(2), g2);
            }
        }
    }
}