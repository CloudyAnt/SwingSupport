package test.t_swg;

import pri.swg.Dragger;

import javax.swing.*;
import java.awt.*;

public class TransparentTest extends JFrame {
    private TransparentTest() {
        setBounds(200, 200, 500, 500);
        setLayout(null);
        Dragger.drag(this);

        setUndecorated(true);
        setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));

        setVisible(true);
    }

    public static void main(String[] args) {
        new TransparentTest();
    }
}
