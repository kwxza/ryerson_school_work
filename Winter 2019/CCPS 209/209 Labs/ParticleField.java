import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticleField extends javax.swing.JPanel {

    private boolean running = true;
    private List<Particle> particles;
    private int width, height, numberOfParticles;

    public ParticleField(int n, int width, int height) {

        this.width = width;
        this.height = height;
        numberOfParticles = n;

        this.setPreferredSize(new Dimension(width, height));

        particles = new ArrayList<Particle>();

        Thread t = new Thread(new runField());
        t.start();
    }

    private class runField implements Runnable {

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run() {
            long time = 20L;
            int sleepCounter = 0;
            //The sleep counter variable keeps track of how many
            //times the thread has gone to sleep.
            int timeControl = 20;
            // The time control variable will control how fast the 
            //propogation of particles occurs, from the initialization
            //of the particle field.
            
            /*
            The idea here is this: Instead of instantly showing
            all the particles as soon as the ParticleField starts
            running, I want to begin by showing one by one.
            However, I want the particles that are shown to 
            continue appearing animated (which relies on the thread
            being put to sleep) and I want the propagation
            of particles to quickly speed up after the first
            couple seconds.
            
            I can show particles one by one if I only create a new
            particle and add it to the field each time the thread loops.
            However, the timing is too fast for the desired visuals
            of one-by-one propagation because the sleep time of 
            the thread is only 20 milliseconds.
            
            Starting with a long thread sleep time and then reducing it
            would have the effect of showing one-by-one propagation that
            accelerates, but would also have the side-effect of making 
            the initial particles appear unanimated (because the thread
            is sleeping long enough that its rest time is blatantly 
            apparent to the human observer).
            
            However, if I set a number of sleep-cycles that the thread should 
            go through before a new particle is created (ie - timeControl), 
            then I just have to  track the number of thread sleep-cycles and
            compare them to timeControl before creating a new particle.
            
            As the thread progress, the timeControl variable is decremented
            to accelerate the propagation of particles.
             */
            
            while (running) {
                try {
                    Thread.sleep(time);
                    if (timeControl != 0) {
                        sleepCounter++;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(ParticleField.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Building the ArrayList of particle components, staggered by the
                //timeControl variable
                if ((sleepCounter == timeControl && particles.size() < numberOfParticles)) {
                    particles.add(new Particle(width, height));
                    if (particles.size() > 10 && particles.size() < numberOfParticles - 1) {
                        particles.add(new Particle(width, height));
                    }
                    sleepCounter = 0;
                }
                
                //Decrementing the time control to speed the addition
                //of particles as the particle count rises
                if (timeControl != 0 && particles.size() % 2 == 0) {
                    if (sleepCounter == 0) {
                        timeControl--;
                    }
                }
                
                //Updating each particle's coordinates
                for (Particle particle : particles) {
                    particle.move();
                }
                
                //Repainting the ParticleField component
                repaint();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Anti-aliasing for better graphics quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Black background
        g2.setPaint(Color.BLACK);
        g2.fill(new Rectangle2D.Double(0, 0, width, height));

        for (int i = 0; i < particles.size(); i++) {
                g2.setPaint(Color.GREEN);
                g2.fill(new Rectangle2D.Double(particles.get(i).getX(), particles.get(i).getY(), 3, 3));            
            }

        //Displaying the number of particles currently on screen
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Times", Font.BOLD, 20));
        g2.drawString("Particle Count: " + particles.size(), 30, 30);
    }

    public void terminate() {
        running = false;
    }

}