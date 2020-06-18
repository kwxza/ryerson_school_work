 

import java.awt.*;
import javax.swing.*;

public class HeadMain {

 
    public static void main(String[] args) {
        JFrame headWindow = new JFrame("Head Window");
        
        headWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        headWindow.setLayout(new GridLayout(2,2));
        
        headWindow.add(new Head());
        headWindow.add(new Head());
        headWindow.add(new Head());
        headWindow.add(new Head());
        
        headWindow.pack();
        headWindow.setVisible(true);
    }
    
}
