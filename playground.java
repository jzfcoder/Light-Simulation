import java.util.*;
import java.awt.*;
import javax.swing.*;

/** Class used for experimenting w/ functions and libraries */
public class playground {
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
    }
}
