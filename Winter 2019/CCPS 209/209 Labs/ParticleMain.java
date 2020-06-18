import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JFrame;

public class ParticleMain {

    public static void main(String[] args) {
        
        //Creating the JFrame that will hold the ParticleField component
        JFrame particleWindow = new JFrame("Particle Window");

        particleWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        particleWindow.setLayout(new GridLayout());
        
        //An instance of ParticleField with 2000 Particle components
        ParticleField newField = new ParticleField(2000, 800, 800);

        particleWindow.add(newField);

        particleWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                newField.terminate();
                particleWindow.dispose();
            }
        });

        particleWindow.pack();
        particleWindow.setVisible(true);
    }

}