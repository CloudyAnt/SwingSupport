package pri.swg;

import javax.swing.*;
import java.awt.*;

//ToDo In Test
public class NewTransparent extends JFrame {
    public NewTransparent() {
        setBounds(200, 200, 500, 500);
        Dragger.drag(this);
        setUndecorated(true);
        setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
        setLayout(null);

        JButton jButton = new JButton();
        jButton.setBounds(200, 200, 100, 100);
        add(jButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new NewTransparent();
    }
}
