import View.Application;

import javax.swing.*;
import java.awt.*;

public final class LL1Application {

    public static void main(String... arg) {
        JFrame frame = new JFrame("LL1Application");
        frame.setResizable(true);
        frame.setContentPane(new Application().getPanel());
        frame.setMaximumSize(new Dimension(1370, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
