package pri.swg;

import javax.swing.*;
import java.awt.*;

public class NewTransparent extends JFrame {
    public NewTransparent() {
        setBounds(200, 200, 500, 500);
        Dragger.drag(this);
        setUndecorated(true);
        setBackground(new Color(1.0f,1.0f,1.0f,0.5f));

        setVisible(true);
    }

    public static void main(String[] args) {
        new NewTransparent();
    }
}
