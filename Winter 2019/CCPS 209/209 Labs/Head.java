 

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class Head extends JPanel {

    private boolean mouseInside;

    public Head() {

        this.setPreferredSize(new Dimension(500, 500));
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        class MyMouseListener extends MouseAdapter {

            @Override
            public void mouseEntered(MouseEvent E) {
                mouseInside = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent E) {
                mouseInside = false;
                repaint();
            }
        }

        this.addMouseListener(new MyMouseListener());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.BLACK);
        g2.fill(new Rectangle2D.Double(50, 50, 400, 400));

        //Head
        g2.setPaint(Color.YELLOW);
        g2.fill(new Ellipse2D.Double(100, 100, 300, 300));

        //Face
        if (mouseInside) {
            //Left Eye Open
            g2.setPaint(Color.BLACK);
            g2.fill(new Ellipse2D.Double(160, 150, 50, 80));
            //Right Eye Open
            g2.setPaint(Color.BLACK);
            g2.fill(new Ellipse2D.Double(290, 150, 50, 80));
            //Mouth Open
            g2.setPaint(Color.BLACK);
            g2.fill(new Ellipse2D.Double(160, 270, 180, 80));
        } else {
            //Left Eye Closed
            g2.setPaint(Color.BLACK);
            g2.fill(new Rectangle2D.Double(160, 210, 50, 10));
            //Right Eye Closed
            g2.setPaint(Color.BLACK);
            g2.fill(new Rectangle2D.Double(290, 210, 50, 10));
            //Mouth Closed
            g2.setPaint(Color.BLACK);
            g2.fill(new Rectangle2D.Double(160, 300, 180, 10));
        }

    }

}
