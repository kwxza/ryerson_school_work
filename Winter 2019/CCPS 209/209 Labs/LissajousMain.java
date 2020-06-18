import java.awt.GridLayout;
import javax.swing.JFrame;


public class LissajousMain {


    public static void main(String[] args) {
        JFrame lissajousWindow = new JFrame("Lissajous Curves");
        
        lissajousWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lissajousWindow.setLayout(new GridLayout());
        
        lissajousWindow.add(new Lissajous(1000));
        
        lissajousWindow.pack();
        lissajousWindow.setVisible(true);
    }
    
}