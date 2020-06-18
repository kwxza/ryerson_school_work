import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.Random;
import javax.swing.JLabel;

public class Lissajous extends JPanel implements ActionListener {

    //INSTANCE FIELDS
    private double a, b, delta, size;

    private JTextField aField, bField, deltaField;
    private JLabel aLabel, bLabel, deltaLabel;
    private JButton newCurve, setCurve;
    private Random rng;

    //CONSTRUCTOR
    public Lissajous(int size) {
        initComponents();

        this.setPreferredSize(new Dimension(size, size));

        this.size = size;
    }

    
    //METHODS
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Anti-aliasing for better graphics quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Box
        g2.setPaint(Color.BLACK);
        g2.fill(new Rectangle2D.Double(0, 0, size, size));

        //Get values from text fields
        a = Double.parseDouble(aField.getText());
        b = Double.parseDouble(bField.getText());
        delta = Double.parseDouble(deltaField.getText());

        //Pick random color for new curve
        Color hue = new Color(rng.nextFloat(), rng.nextFloat(), rng.nextFloat());

        //**************************************************
        //Parameters that will control creation of cubic curve segments
        //x,y used to calculate coordinates from Lissajous formula
        double x, y;

        //Tracker for creation of cubic curve coordinates
        int count = 0;

        //Control switch for cubic curve coordinate creation & constructor execution
        boolean setCoords = true;

        //Start/end points for cubic curve constructor
        double xStart = 0, yStart = 0, xEnd = 0, yEnd = 0;

        //Control points for cubic curve constuctor
        double ctrlX1 = 0, ctrlY1 = 0, ctrlX2 = 0, ctrlY2 = 0;

        for (double t = 0; t <= (a + b) * Math.PI; t += 0.0001) {
            x = size / 2 + 2 * size / 5 * Math.sin(a * t + delta);
            y = size / 2 + 2 * size / 5 * Math.cos(b * t);

            if (setCoords) {
                //If statement will set the coordinates
                //of the cubic curve's starting point 
                if (t == 0 && count == 0) {
                    xStart = x;
                    yStart = y;
                    count++;
                } else if (count == 0) {
                    xStart = xEnd;
                    yStart = yEnd;
                    count++;
                }

                //Switch statement will set the coordinates  
                //for control and end points
                switch (count) {
                    case 1:
                        ctrlX1 = x;
                        ctrlY1 = y;
                        count++;
                        break;

                    case 2:
                        ctrlX2 = x;
                        ctrlY2 = y;
                        count++;
                        break;

                    case 3:
                        xEnd = x;
                        yEnd = y;
                        setCoords = false;
                        break;
                }
            }
            
            //Drawing the curve based on 4 coordinate points
            if (!setCoords) {
                g2.setStroke(new BasicStroke(1.3f));
                g2.setPaint(hue);
                g2.draw(new CubicCurve2D.Double(xStart, yStart, ctrlX1, ctrlY1, ctrlX2, ctrlY2, xEnd, yEnd));
                count = 0;
                setCoords = true;
            }
        }
    }
    
    /*
    MouseListener for button click, specifically separate
    from the ActionListener as the desired behaviour
    for the button click is a random value selection
    before repainting.
    */
    protected class buttonClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            aField.setText(Double.toString(10 * rng.nextDouble()).substring(0,6));
            bField.setText(Double.toString(10 * rng.nextDouble()).substring(0,6));
            deltaField.setText(Double.toString(3 * rng.nextDouble()).substring(0,6));
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    protected void initComponents() {
        this.rng = new Random();

        this.add(aLabel = new JLabel("A value "));
        aLabel.setForeground(Color.WHITE);
        this.add(aField = new JTextField(Double.toString(10 * rng.nextDouble()).substring(0,6), 5));
        aField.addActionListener(this);

        this.add(bLabel = new JLabel("B value "));
        bLabel.setForeground(Color.WHITE);
        this.add(bField = new JTextField(Double.toString(10 * rng.nextDouble()).substring(0,6), 5));
        bField.addActionListener(this);

        this.add(deltaLabel = new JLabel("Delta value "));
        deltaLabel.setForeground(Color.WHITE);
        this.add(deltaField = new JTextField(Double.toString(3 * rng.nextDouble()).substring(0,6), 5));
        deltaField.addActionListener(this);
        
        this.add(setCurve = new JButton("Set curve with these values"));
        setCurve.addActionListener(this);

        this.add(newCurve = new JButton("Click for random curve"));
        newCurve.addMouseListener(new buttonClick());
    }
}
