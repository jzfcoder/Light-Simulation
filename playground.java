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
        x = 0;
        int y = 0;
        int[][] sources = new int[5][2];
        while(angle != 1000)
        {
            angle = Integer.parseInt(s.nextLine());
            sources = new int[5][2];
            int temp = (angle > 0 ?
            (angle <= 45 ? 45 :
            (angle <= 90 ? 90 : 
            (angle <= 135 ? 135 : 180))) :

            (angle <= 0 ? (angle > -45 ? 0 :
            (angle > -90 ? -45 :
            (angle > -135 ? -90 :
            (angle > -180 ? -135 : 180)))) :
            90));

            for(int i = 0; i <= (int) 5 / 2; i++)
            {
                System.out.println(temp);
                if(temp == 0 || temp == 180)
                {
                    // pursuing line of 90 deg
                    sources[i][0] = x;
                    sources[i][1] = y + i;
                }
                else
                {
                    sources[i][0] = x + i;
                    sources[i][1] = ((int) (Math.tan(Math.toRadians(temp + 90)) + ((Math.tan(Math.toRadians(temp + 90)) < 0) ? - 0.5 : 0.5)) * ((x + i) - x)) + y;
                }
            }
            for(int i = 1; i <= 5 / 2; i++)
            {
                if(temp == 0 || temp == 180)
                {
                    sources[(5 / 2) + i][0] = x;
                    sources[(5 / 2) + i][1] = y - i;
                }
                else
                {
                    sources[(5 / 2) + i][0] = x - i;
                    sources[(5 / 2) + i][1] = ((int) (Math.tan(Math.toRadians(temp + 90)) + ((Math.tan(Math.toRadians(temp + 90)) < 0) ? - 0.5 : 0.5)) * ((x - i) + x)) + y;
                }
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
