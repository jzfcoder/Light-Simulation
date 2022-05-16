import java.util.*;
import java.awt.*;
import javax.swing.*;

public class playground {
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        ArrayList<Integer> list = new ArrayList<Integer>();

        frame.setSize(500, 500);
        frame.setVisible(true);
        JTextArea area = new JTextArea();
        area.setText(x + ", " + list.toString());
        frame.add(area);
    }
}
