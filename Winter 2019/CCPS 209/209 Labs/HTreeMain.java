import java.awt.GridLayout;
import javax.swing.JFrame;

public class HTreeMain {

public static void main(String[] args) {
        JFrame htreeWindow = new JFrame("H-Tree Window");
        
        htreeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        htreeWindow.setLayout(new GridLayout());
        
        htreeWindow.add(new HTree(1400, 1000));
        
        htreeWindow.pack();
        htreeWindow.setVisible(true);
    }
    
}