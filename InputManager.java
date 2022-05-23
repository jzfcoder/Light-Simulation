import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.ArrayList;

import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Class that handles all user input
 * Includes methods to determine usability of user input
 * reduces complexity and improves redability of the main class
 */
public class InputManager {
    Scanner s;
    String input;
    boolean inputCompleted = false;;
    Map map;

    private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    final int SCREEN_WIDTH = (int) d.getWidth();
    final int SCREEN_HEIGHT = (int) d.getHeight();

    public InputManager()
    {
        s = new Scanner(System.in);
        map = new Map();
    }
    
    /**
     * Prompts user for map dimensions, and constructs map accordingly
     */
    public void getMapInput()
    {
        // get user input for map dimensions
        inputCompleted = false;
        while(!inputCompleted)
        {
            System.out.println("Enter Map Dimensions (width & height separated by a space): ");
            input = s.nextLine();
            if(input.equals("demo"))
            {
                runDemo();
                s.close();
                System.exit(0);
            }
            if(input.length() >= 3 && countSpaces(input) == 1)
            {
                inputCompleted = true;
                break;
            }
            System.out.println("Invalid Input!");
        }

        // attempt to parse
        try {
            int width = Integer.parseInt(input.substring(0, input.indexOf(" ")));
            int height = Integer.parseInt(input.substring(input.indexOf(" ") + 1));

            map = new Map(width % 2 == 0 ? width + 1: width, height % 2 == 0 ? height + 1: height);
            System.out.println("Your map was created with dimensions: " + width + " by " + height);
        } catch (NumberFormatException e) {
            System.out.println("Something went wrong with your input");
            s.close();
            System.exit(1);
        }
    }

    /**
     * Prompts user for two points to add a in between on the map
     * Loops until user decides to finish
    */
    public void getWallInput()
    {
        // get user input for walls
        inputCompleted = false;
        for(int i = 0; !inputCompleted; i++)
        {
            System.out.println("Would you like to add" + (i == 0 ? " a " : " another ") + "wall to your Map? Y/N: ");
            input = s.nextLine();
            if(input.length() == 1 && (input.toLowerCase().equals("y") || input.toLowerCase().equals("n")))
            {
                if(input.toLowerCase().equals("n")) { inputCompleted = true; break; }
                System.out.println("List first x/y position and second x/y position (space separated): ");
                input = s.nextLine();
                int x1 = 10;
                int y1 = 10;
                int x2 = -10;
                int y2 = 10;
                if(countSpaces(input) == 3 && isNumbersAndSpaces(input))
                {
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(input.split(" ")));
                    x1 = Integer.parseInt(list.get(0));
                    y1 = Integer.parseInt(list.get(1));
                    x2 = Integer.parseInt(list.get(2));
                    y2 = Integer.parseInt(list.get(3));
                    
                    if( map.drawLine(x1, y1, x2, y2)) { System.out.println("Added!"); }
                    else { System.out.println("Unable to add wall; invalid points (Points must form a vertical, horizontal, or slope = 1 line)"); }
                }
                else { System.out.println("Invalid Input!"); }
            }
        }
    }

    /**
     * Asks user if they want to run project in real time, runs the point or ray demo respectively
     * Otherwise, returns false
    */
    public boolean runRealTime()
    {
        inputCompleted = false;
        while(!inputCompleted)
        {
            System.out.println("Would you like to run a realtime point/ray simulation or print? point/ray/print:");
            input = s.nextLine();
            if(input.toLowerCase().equals("point"))
            {
                System.out.println("List strength and light complexity (space separated): ");
                input = s.nextLine();
                if(countSpaces(input) == 1 && isNumbersAndSpaces(input))
                {
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(input.split(" ")));
                    runPointDemo(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
                    return true;
                }
                else { System.out.println("Invalid Input!"); }
            }
            else if(input.toLowerCase().equals("ray"))
            {
                System.out.println("List strength and width (space separated): ");
                input = s.nextLine();
                if(countSpaces(input) == 1 && isNumbersAndSpaces(input))
                {
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(input.split(" ")));
                    runRayDemo(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
                    return true;
                }
                else { System.out.println("Invalid Input!"); }
            }
            else if(input.toLowerCase().equals("print"))
            {
                inputCompleted = true;
                return false;
            }
            else { System.out.println("Invalid Input!"); }
        }
        return false;
    }
    
    /**
     * If the user doesn't want to run realtime, they are prompted too add lights
     * The function loops until the user decides to finish
     * Once finished, the function prints out the output
    */
    public void getLightsAndSimulate()
    {
        inputCompleted = false;
        for(int i = 0; !inputCompleted; i++)
        {
            System.out.println("Would you like to add" + (i == 0 ? " a " : " another ") + "Light to your Map? Y/N: ");
            input = s.nextLine();
            if(input.length() == 1 && (input.toLowerCase().equals("y") || input.toLowerCase().equals("n")))
            {
                if(input.toLowerCase().equals("n")) { inputCompleted = true; break; }

                System.out.println("What kind of light? Point/Ray: ");
                input = s.nextLine();
                if((input.toLowerCase().equals("point") || input.toLowerCase().equals("ray")))
                {
                    if(input.toLowerCase().equals("point"))
                    {
                        System.out.println("List strength, x/y position, and light complexity (space separated): ");
                        input = s.nextLine();
                        int st = 10;
                        int x = 0;
                        int y = 0;
                        int l = 36;
                        if(countSpaces(input) == 3 && isNumbersAndSpaces(input))
                        {
                            ArrayList<String> list = new ArrayList<String>(Arrays.asList(input.split(" ")));
                            st = Integer.parseInt(list.get(0));
                            x = Integer.parseInt(list.get(1));
                            y = Integer.parseInt(list.get(2));
                            l = Integer.parseInt(list.get(3));

                            PointLight light = new PointLight(map, st, x, y, l);
                            map.addLight(light);
                            System.out.println("Added!");
                        }
                        else { System.out.println("Invalid Input!"); }
                    }
                    else
                    {
                        System.out.println("List strength, x/y position, angle, and width (space separated): ");
                        input = s.nextLine();
                        int st = 10;
                        int x = 0;
                        int y = 0;
                        int a = 36;
                        int w = 45;
                        if(countSpaces(input) == 4 && isNumbersAndSpaces(input))
                        {
                            ArrayList<String> list = new ArrayList<String>(Arrays.asList(input.split(" ")));
                            st = Integer.parseInt(list.get(0));
                            x = Integer.parseInt(list.get(1));
                            y = Integer.parseInt(list.get(2));
                            a = Integer.parseInt(list.get(3));
                            w = Integer.parseInt(list.get(4));

                            DirectionalRay light = new DirectionalRay(map, st, x, y, a, w);
                            map.addLight(light);
                            System.out.println("Added!");
                        }
                        else { System.out.println("Invalid Input!"); }
                    }
                }
                else { System.out.println("Invalid Input!"); }
            }
            else { System.out.println("Invalid Input!"); }
        }

        map.simulate();

        System.out.println(map.toString());
    }

    /**
     * returns the simulated map as a file, if user requests
     */
    public void returnAsFile()
    {
        inputCompleted = false;
        while(!inputCompleted)
        {
            System.out.println("Would you like to save this to a file? Y/N");
            input = s.nextLine();
            if(input.length() == 1 && (input.toLowerCase().equals("y")))
            {
                System.out.println("What do you want to name your file? ");
                input = s.nextLine();
                File file = new File(input + ".txt");
                try {
                    FileWriter myWriter = new FileWriter(file.getName());
                    myWriter.write(map.toString());
                    myWriter.close();
                    System.out.println("Saved!");
                    inputCompleted = true;
                    break;
                } catch (IOException e)
                {
                    System.out.println("An error occurred while attempting to write to the file...");
                    System.out.println(e.toString());
                }
                
            }
            else if(input.toLowerCase().equals("n"))
            {
                break;
            }
            else { System.out.println("Invalid Input!"); }
        }
    }

    /**
     * creates a JFrame and pointlight object
     * Tracks mouse x/y position and translates screenspace relative position
     * into arrayspace relative position for the source of the pointlight
     * @param s int to determine strength of light
     * @param l int to determine light complexity of light
     */
    public void runPointDemo(int s, int l)
    {
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        StringBuilder sb = new StringBuilder("<html>");
        boolean clicked = false;
        int x = 0;
        int y = 0;
        PointLight curLight = new PointLight(map, s, convertArrXtoCoordX(x), convertArrYtoCoordY(y), l);

        frame.setSize(map.getWidth() * 8, map.getHeight() * 16);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(label);

        frame.setVisible(true);

        while(!clicked)
        {
            try { TimeUnit.SECONDS.sleep(1/2); } catch (InterruptedException e) { e.printStackTrace(); }
            if(x != (((int) MouseInfo.getPointerInfo().getLocation().getX()) * map.getWidth()) / SCREEN_WIDTH
            || y != (((int) MouseInfo.getPointerInfo().getLocation().getY()) * map.getWidth()) / SCREEN_HEIGHT)
            {
                x = (((int) MouseInfo.getPointerInfo().getLocation().getX()) * map.getWidth()) / SCREEN_WIDTH;
                y = (((int) MouseInfo.getPointerInfo().getLocation().getY()) * map.getWidth()) / SCREEN_HEIGHT;

                sb = new StringBuilder("<html>");
                map.clear();
                map.clearLights();
                
                curLight.setCoordX(convertArrXtoCoordX(x));
                curLight.setCoordY(convertArrYtoCoordY(y));
                map.addLight(curLight);
                
                map.simulate();

                sb.append("<table class=\"content-table\"border=0>");
                for(Tile[] t : map.getGrid())
                {
                    sb.append("<tr>");
                    for(Tile tile : t)
                    {
                        sb.append("<td align=center>").append(tile.toString()).append("</td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");
                label.setText(sb.toString());
            }
        }
    }

    /**
     * creates a JFrame and directional ray object at 0, 0
     * Tracks mouse x/y position and determines angle relative to screenspace 0, 0
     * Sets the directional ray's angle to angle determined
     * @param s int strength of ray
     * @param w int width of ray
     */
    public void runRayDemo(int s, int w)
    {
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        StringBuilder sb = new StringBuilder("<html>");
        boolean clicked = false;
        double x = 0;
        double y = 0;

        map.drawLine(10, 10, -10, 10);

        frame.setSize(map.getWidth() * 8, map.getHeight() * 16);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(new GridBagLayout());

        frame.add(label);

        frame.setVisible(true);

        while(!clicked)
        {
            try { TimeUnit.SECONDS.sleep(1/2); } catch (InterruptedException e) { e.printStackTrace(); }
            if(x != (((int) MouseInfo.getPointerInfo().getLocation().getX()) * map.getWidth()) / SCREEN_WIDTH
            || y != (((int) MouseInfo.getPointerInfo().getLocation().getY()) * map.getWidth()) / SCREEN_HEIGHT)
            {
                x = convertScreenXtoCoordX(MouseInfo.getPointerInfo().getLocation().getX());
                y = convertScreenYtoCoordY(MouseInfo.getPointerInfo().getLocation().getY());

                // p1: (0, 0)
                // p2: (10, 0)
                // p3: (x, y)

                int angle = (int) Math.toDegrees(Math.atan2(y - 0.0, x - 0.0) - Math.atan2(10 - 0, 0));
                angle += 90;

                sb = new StringBuilder("<html>");
                map.clear();
                map.clearLights();
                
                map.addLight(new DirectionalRay(map, s, 0, 0, angle, w));
                
                map.simulate();

                sb.append("<table border=0>");
                for(Tile[] t : map.getGrid())
                {
                    sb.append("<tr>");
                    for(Tile tile : t)
                    {
                        sb.append("<td align=center>").append(tile.toString()).append("</td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");
                label.setText(sb.toString());
            }
        }
        try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * runs simple point light realtime demo
     */
    private void runDemo()
    {
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        StringBuilder sb = new StringBuilder("<html>");
        boolean clicked = false;
        int x = 0;
        int y = 0;
        PointLight curLight = new PointLight(map, 25, convertArrXtoCoordX(x), convertArrYtoCoordY(y), 36);
        map.drawLine(10, 10, -10, 10);
        map.drawLine(10, 0, 15, -5);

        frame.setSize(map.getWidth() * 8, map.getHeight() * 16);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(label);

        frame.setVisible(true);

        while(!clicked)
        {
            try { TimeUnit.SECONDS.sleep(1/2); } catch (InterruptedException e) { e.printStackTrace(); }
            if(x != (((int) MouseInfo.getPointerInfo().getLocation().getX()) * map.getWidth()) / SCREEN_WIDTH
            || y != (((int) MouseInfo.getPointerInfo().getLocation().getY()) * map.getWidth()) / SCREEN_HEIGHT)
            {
                x = (((int) MouseInfo.getPointerInfo().getLocation().getX()) * map.getWidth()) / SCREEN_WIDTH;
                y = (((int) MouseInfo.getPointerInfo().getLocation().getY()) * map.getWidth()) / SCREEN_HEIGHT;

                sb = new StringBuilder("<html>");
                map.clear();
                map.clearLights();
                
                curLight.setCoordX(convertArrXtoCoordX(x));
                curLight.setCoordY(convertArrYtoCoordY(y));
                map.addLight(curLight);
                
                map.simulate();

                sb.append("<table border=0>");
                for(Tile[] t : map.getGrid())
                {
                    sb.append("<tr>");
                    for(Tile tile : t)
                    {
                        sb.append("<td align=center>").append(tile.toString()).append("</td>");
                    }
                    sb.append("</tr>");
                }
                sb.append("</table>");
                label.setText(sb.toString());
            }
        }
    }

    public void clean() { s.close(); }

    /**
     * counts num of spaces in given string
     * used to verify input
     * @param str input string
     * @return returns num of spaces in string
     */
    private int countSpaces(String str)
    {
        int count = 0;
        for(int i = 0; i < str.length(); i++)
        {
            if(str.substring(i, i + 1).equals(" ")) { count++; }
        }
        return count;
    }

    /**
     * ensures each character is valid for number-based input
     * used to verify input
     * @param str input string
     * @return returns true or false depending on if input is valid
     */
    private boolean isNumbersAndSpaces(String str)
    {
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == ' ' || Character.isDigit(str.charAt(i)) || str.charAt(i) == '-') { }
            else { return false; }
        }
        return true;
    }

    private int convertArrXtoCoordX(int arrX) { return (int) (arrX - (map.getWidth() / 2)); }
    private int convertArrYtoCoordY(int arrY) { return - (int) (arrY - (map.getHeight() / 2)); }
    private double convertScreenXtoCoordX(double sX) { return (sX - (SCREEN_WIDTH / 2)); }
    private double convertScreenYtoCoordY(double sY) { return - (sY - (SCREEN_HEIGHT / 2)); }
}
