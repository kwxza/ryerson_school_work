import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

public class Mondrian extends JPanel {

    /*
    Cutoff size for when a rectangle is not subdivided further.
     */
    private static final int CUTOFF = 60;
    /*
    Percentage of rectangles that are the majority color.
     */
    private static final double MAIN_COLOR = 0.55;
    /*
    Array of the minority colors to be used.
     */
    private static final Color[] COLORS = new Color[5];
    /*
    RNG instance to make the random decisions with.
     */
    private Random rng = new Random();
    /*
    The Image in which the art is drawn.
     */
    private Image mondrian;

    //Constructor
    public Mondrian(int w, int h) {

        this.setPreferredSize(new Dimension(w, h));
        
        /*
        Selecting a random initial color for COLORS array,
        and then following colors in the array are brighter
        variants of COLORS[0].
        */
        COLORS[0] = new Color(rng.nextFloat(), rng.nextFloat(), rng.nextFloat());
        for (int i = 1; i < COLORS.length; i++) {
            COLORS[i] = COLORS[i - 1].brighter();
        }

        mondrian = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        subdivide(0, 0, w, h, (Graphics2D) mondrian.getGraphics());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Anti-aliasing for better graphics quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(mondrian, 0, 0, this);
    }

    private void subdivide(int tx, int ty, int w, int h, Graphics2D g2) {

        /*
        Boolean raised will determine if 3D rectangle drawn
        appears to be raised above surface or etched into it.
         */
        boolean raised = true;
        
        if (rng.nextDouble() <= MAIN_COLOR) {
            /*
            2 main color shades will dominate the
            mondrian image and will have a
            raised appearance.
            */
            g2.setPaint(COLORS[rng.nextInt(2)]);
        } else {
            /*
            Minority color boxes will have an etched appearance.
            */
            int color = rng.nextInt(COLORS.length - 2) + 2;
            g2.setPaint(COLORS[color]);
            raised = !raised;
        }

        g2.fill3DRect(tx, ty, w, h, raised);
        /*
        A 2D border rectangle will be drawn around
        each 3D rectangle, to give more visual 
        definition between distinct boxes.
        */
        g2.setPaint(Color.BLACK);
        g2.draw(new Rectangle2D.Double(tx, ty, w, h));

        /*
        After rectangle is drawn, check if given parameters
        w and h are greater than the cutoff size.
        
        If they are, subdivide the rectangle and pass 
        the subdivision parameters for each
        newly split rectangle to 2 recursive calls to 
        subdivide()
         */
        if (w > CUTOFF && h > CUTOFF) {
            /*
            splitPoint will represent both the subdivision
            point, as well as the length of one of the
            subdivided rectangles' sides.
            
            longSide will be the greater of the given 
            parameters w & h, ie - the length to
            subdivide.
            */
            int splitPoint, longSide;
            
            /*
            Boolean longWidth will determine which parameters
            to give to the recursive calls, based on which of
            w & h is the longer side from the given parameters.
            */
            boolean longWidth = true;

            /*
            Compare w and h to see which is greater
            to set the control boolean longWidth.
             */
            if (h > w) {
                longWidth = false;
            }
            /*
            Set longSide to the length of the longest side
             */
            longSide = longWidth ? w : h;
            /*
            Find a random subdivision point within the 
            longSide, with a buffer of 1/4 of longSide's
            length on either end.
             */
            splitPoint = rng.nextInt(longSide / 2) + longSide / 4;

            /*
            After finding a random subdivision point for the 
            longSide, subdivide() can be called for the two
            newly-created rectangles.
             */
            if (longWidth) {
                subdivide(tx, ty, splitPoint, h, g2);
                subdivide(tx + splitPoint, ty, w - splitPoint, h, g2);
            } else {
                subdivide(tx, ty, w, splitPoint, g2);
                subdivide(tx, ty + splitPoint, w, h - splitPoint, g2);
            }
        }
    }
}