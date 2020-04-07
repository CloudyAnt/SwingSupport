package pri.swg;

import javax.swing.*;
import java.awt.*;

public class LinerTest extends JFrame {
    private LinerTest() {
        setUndecorated(true);
        setBounds(200, 200, 500, 500);
        setLayout(null);

        Liner liner = new Liner(500, 500, Liner.Horizontal);
        liner.setLocation(0, 0);
        liner.draw(Color.red, Color.green, Color.blue);

        Dragger.drag(this, liner);
        add(liner);
        setVisible(true);
    }
    public static void main(String[] args) {
        new LinerTest();
    }
}
