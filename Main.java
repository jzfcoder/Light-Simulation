/**
* Main class that runs programs
* Gets user input and either saves custom map + light to .txt or
* runs a realtime mouse-input based simulation
*
* Uses Map, Point Light, and Directional Ray
*
* @author Jeremy Flint
* @since 05/08/2022
*/
class Main
{
    public static void main(String[] args)
    {
        InputManager input = new InputManager();
        input.getMapInput();
        input.getWallInput();
        if(input.runRealTime())
        {
            input.clean();
        }
        else
        {
            input.getLightsAndSimulate();
            input.returnAsFile();
            input.clean();
            System.exit(0);
        }
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

        // calculate point test
        System.out.println("-=CALCULATE POINT=-");
        map.calculateLightPoint(0, 0);
        System.out.println(map.toString());
        */
    }
}