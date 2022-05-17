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
        //frame.setVisible(true);
        JTextArea area = new JTextArea();
        area.setText(x + ", " + list.toString());
        frame.add(area);

        Scanner s = new Scanner(System.in);
        int angle = 0;
        int[][] sources = new int[5][2];
        while(angle != 1000)
        {
            angle = Integer.parseInt(s.nextLine());
            sources = new int[5][2];

            // (angle > 0 ? (angle <= 45 ? 45 : (angle <= 90 ? 90 : angle <= 135 ? 135 : 180)) : (angle <= 0 ? (angle > -45 ? 0 : (angle > -90 ? -45 : (angle > -135 ? -90 : (angle > -180 ? -135 : 180)))) : 90))

            for(int i = 0; i <= (int) 5 / 2; i++)
            {
                sources[i][0] = x + i;
                sources[i][1] = ((int) (Math.tan(Math.toRadians(((angle > 0 ?
                (angle <= 45 ? 45 :
                (angle <= 90 ? 90 : 
                (angle <= 135 ? 135 : 180))) :

                (angle <= 0 ? (angle > -45 ? 0 :
                (angle > -90 ? -45 :
                (angle > -135 ? -90 :
                (angle > -180 ? -135 : 180)))) :
                90))) + 90)) + 0.5)) * (x - i);
            }
            for(int i = 1; i < 5 / 2; i++)
            {
                sources[(5 / 2) + i][0] = x - i;
                sources[(5 / 2) + i][1] = (int) Math.tan(Math.toRadians(((angle > 0 ?
                (angle <= 45 ? 45 :
                (angle <= 90 ? 90 :
                angle <= 135 ? 135 : 180)) :
                
                (angle <= 0 ? (angle > -45 ? 0 :
                (angle > -90 ? -45 :
                (angle > -135 ? -90 :
                (angle > -180 ? -135 : 180)))) :
                90))) + 90)) * (x + i);
            }
            for(int[] pairs : sources)
            {
                System.out.println(pairs[0] + ", " + pairs[1]);
            }
        }
        s.close();
        System.exit(1);
    }
}
