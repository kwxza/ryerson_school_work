import java.awt.GridLayout;
import javax.swing.JFrame;

public class MondrianMain {

    public static void main(String[] args) {
        
        JFrame mondrianWindow = new JFrame("Mondrian Window");
        
        mondrianWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mondrianWindow.setLayout(new GridLayout());
        
        mondrianWindow.add(new Mondrian(1400, 900));
        
        mondrianWindow.pack();
        mondrianWindow.setVisible(true);
    }
    
}