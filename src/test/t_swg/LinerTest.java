package test.t_swg;

import pri.swg.Liner;

import javax.swing.*;
import java.awt.*;

public class LinerTest extends JFrame {
    private LinerTest() {
        setUndecorated(true);
        setBounds(200, 200, 500, 500);
        setLayout(null);

        Liner liner = new Liner(500, 10, Liner.Horizontal);
        liner.setLocation(0, 245);
        liner.draw(Color.red, Color.green, Color.blue);

        add(liner);
        setVisible(true);
    }
    public static void main(String[] args) {
        new LinerTest();
    }
}
