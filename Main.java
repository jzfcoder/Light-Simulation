import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Main
{
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        String in = " ";
        boolean inputCompleted = false;
        Map map = new Map();

        while(!inputCompleted)
        {
            System.out.println("Enter Map Dimensions (width & height separated by a space): ");
            in = s.nextLine();
            if(in.length() >= 3 && countSpaces(in) == 1)
            {
                inputCompleted = true;
                break;
            }
            System.out.println("Invalid Input!");
        }

        try {
            int width = Integer.parseInt(in.substring(0, in.indexOf(" ")));
            int height = Integer.parseInt(in.substring(in.indexOf(" ") + 1));

            map = new Map(width % 2 == 0 ? width + 1: width, height % 2 == 0 ? height + 1: height);
            System.out.println("Your map was created with dimensions: " + width + " by " + height);
        } catch (NumberFormatException e) {
            System.out.println("Something went wrong with your input");
            s.close();
            System.exit(1);
        }
        
        
        inputCompleted = false;
        for(int i = 0; !inputCompleted; i++)
        {
            System.out.println("Would you like to add" + (i == 0 ? " a " : " another ") + "Light to your Map? Y/N: ");
            in = s.nextLine();
            if(in.length() == 1 && (in.toLowerCase().equals("y") || in.toLowerCase().equals("n")))
            {
                if(in.toLowerCase().equals("n")) { break; }

                System.out.println("What kind of light? Point/Ray: ");
                in = s.nextLine();
                if((in.toLowerCase().equals("point") || in.toLowerCase().equals("ray")))
                {
                    if(in.toLowerCase().equals("point"))
                    {
                        System.out.println("List strength, x/y position, and light complexity (space separated): ");
                        in = s.nextLine();
                        int st = 10;
                        int x = 0;
                        int y = 0;
                        int l = 36;
                        if(countSpaces(in) == 3 && isNumbersAndSpaces(in))
                        {
                            ArrayList<String> list = new ArrayList<String>(Arrays.asList(in.split(" ")));
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
                        in = s.nextLine();
                        int st = 10;
                        int x = 0;
                        int y = 0;
                        int a = 36;
                        int w = 45;
                        if(countSpaces(in) == 4 && isNumbersAndSpaces(in))
                        {
                            ArrayList<String> list = new ArrayList<String>(Arrays.asList(in.split(" ")));
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

        for(int i = 0; !inputCompleted; i++)
        {
            System.out.println("Would you like to add" + (i == 0 ? " a " : " another ") + "wall to your Map? Y/N: ");
            in = s.nextLine();
            if(in.length() == 1 && (in.toLowerCase().equals("y") || in.toLowerCase().equals("n")))
            {
                if(in.toLowerCase().equals("n")) { break; }
                System.out.println("List first x/y position and second x/y position (space separated): ");
                in = s.nextLine();
                int x1 = 10;
                int y1 = 10;
                int x2 = -10;
                int y2 = 10;
                if(countSpaces(in) == 3 && isNumbersAndSpaces(in))
                {
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(in.split(" ")));
                    x1 = Integer.parseInt(list.get(0));
                    y1 = Integer.parseInt(list.get(1));
                    x2 = Integer.parseInt(list.get(2));
                    y2 = Integer.parseInt(list.get(3));
                    
                    if(map.drawLine(x1, y1, x2, y2)) { System.out.println("Added!"); }
                    else { System.out.println("Unable to add wall; invalid points (Points must form a vertical, horizontal, or slope = 1 line)"); }
                }
                else { System.out.println("Invalid Input!"); }
            }
        }

        map.simulate();

        System.out.println(map.toString());

        while(!inputCompleted)
        {
            System.out.println("Would you like to save this to a file? Y/N");
            in = s.nextLine();
            if(in.length() == 1 && (in.toLowerCase().equals("y")))
            {
                System.out.println("What do you want to name your file? ");
                in = s.nextLine();
                File file = new File(in + ".txt");
                try {
                    FileWriter myWriter = new FileWriter(file.getName());
                    myWriter.write(map.toString());
                    myWriter.close();
                    System.out.println("Saved!");
                    break;
                } catch (IOException e)
                {
                    System.out.println("An error occurred while attempting to write to the file...");
                    System.out.println(e.toString());
                }
                
            }
            else if(in.toLowerCase().equals("n"))
            {
                break;
            }
            else { System.out.println("Invalid Input!"); }
        }

        s.close();
        System.exit(0);
        
        /*
        0  | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |10 |11 |12 |13 |14 |15 |16 |17 |18 |19 |20 |21 |22 |23 |24 |
        1
        2
        3
        4
        5
        6
        7
        8
        9
        10
        11
        12  |-11|-10|-9 |-8 |-7 |-6 |-5 |-4 |-3 |-2 |-1 | 0 |
        13
        14
        15
        16
        17
        18
        19
        20
        21
        22
        23
        24

        Ycoord| Arr
        ______|______
           0  |  12  
         -12  |  24
          12  |  0
           1  |  11

        Xcoord| Arr
        ______|______
           0  |  12  
         -12  |  0
          12  |  24
           1  |  13
              
        */
        /*
        // convert y-coord test
        System.out.println("-=Y-COORD to ARR-COORD TEST=-");
        System.out.println("Input: 0\nExpected: 12\nOutput: " + map.convertCoordYtoArrY(0) + "\n---");
        System.out.println("Input: 1\nExpected: 11\nOutput: " + map.convertCoordYtoArrY(1) + "\n---");
        System.out.println("Input: 12\nExpected: 0\nOutput: " + map.convertCoordYtoArrY(12) + "\n---");
        System.out.println("Input: -12\nExpected: 24\nOutput: " + map.convertCoordYtoArrY(-12) + "\n---");

        // convert x-coord test
        System.out.println("-=X-COORD to ARR-COORD TEST=-");
        System.out.println("Input: 0\nExpected: 12\nOutput: " + map.convertCoordXtoArrX(0) + "\n---");
        System.out.println("Input: 1\nExpected: 13\nOutput: " + map.convertCoordXtoArrX(1) + "\n---");
        System.out.println("Input: 12\nExpected: 24\nOutput: " + map.convertCoordXtoArrX(12) + "\n---");
        System.out.println("Input: -12\nExpected: 0\nOutput: " + map.convertCoordXtoArrX(-12) + "\n---");
        
        // Single point test
        System.out.println("-=SINGLE POINT TEST=-");
        System.out.println("Input: 0, 45\nExpected: 0\nOutput: " + (int) ((Math.tan(Math.toRadians(45)) * 0) + 0.5)+ "\n---");
        System.out.println("Input: 1, 45\nExpected: 1\nOutput: " + (int) ((Math.tan(Math.toRadians(45)) * 1) + 0.5)+ "\n---");
        System.out.println("Input: 3, 33.69\nExpected: 2\nOutput: " + (int) ((Math.tan(Math.toRadians(33.69)) * 3) + 0.5) + "\n---");
        

        // draw single ray test
        System.out.println("-=SINGLE RAY TEST=-");
        System.out.println("Input: -60, 0, 0");
        map.drawRay(-60, 0, 0);
        System.out.println("Output:\n" + map.toString());

        
        // find ray degrees test
        System.out.println("-=FIND RAY DEGREES=-");
        for (int i : map.getRayDegrees())
        {
            System.out.print(i + " ");
        }
        System.out.println("");
        */


        /* calculate point test
        System.out.println("-=CALCULATE POINT=-");
        map.calculateLightPoint(0, 0);
        System.out.println(map.toString()); */
    }

    public static int countSpaces(String str)
    {
        int count = 0;
        for(int i = 0; i < str.length(); i++)
        {
            if(str.substring(i, i + 1).equals(" ")) { count++; }
        }
        return count;
    }

    public static boolean isNumbersAndSpaces(String str)
    {
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == ' ' || Character.isDigit(str.charAt(i)) || str.charAt(i) == '-') { }
            else { return false; }
        }
        return true;
    }
}