import java.util.ArrayList;

/** 
 * Map class holds a 2D array of tiles
 * 
 * Manages all objects present within the grid, including
 * walls and lights
 * 
 * Is also responsible for simulating
*/
public class Map
{
    private Tile[][] grid;
    private int width; // width (ARRAY)
    private int height; // height (ARRAY)

    private ArrayList<Light> lights;

    public Map(int width, int height)
    {
        this.width = width;
        this.height = height;

        grid = new Tile[width][height];
        lights = new ArrayList<Light>();

        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                grid[i][j] = new Tile();
            }
        }
    }

    public Map()
    {
        this(101, 101);
    }

    /**
    * clears grid of all Light-based tiles
    */
    public void clear()
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if(grid[i][j].state == Tile.tileType.LIGHT
                || grid[i][j].state == Tile.tileType.CENTER
                || grid[i][j].state == Tile.tileType.SOURCE
                ) { grid[i][j].setState(Tile.tileType.EMPTY); }
            }
        }
    }

    /**
     * clears all lights in lights
     */
    public void clearLights() { lights = new ArrayList<Light>(); }

    /**
    * simulates all lights in "lights" ArrayList
    */
    public void simulate()
    {
        for(Light light : lights)
        {
            grid = light.simulate();
        }
    }

    /**
     * converts 2D grid to String by getting each tile's toString
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        //grid[convertCoordYtoArrY(0)][convertCoordXtoArrX(0)].setState(Tile.tileType.CENTER);
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                sb.append(grid[i][j].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }   

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Tile[][] getGrid() { return grid; }

    /**
     * returns a string of multiple "backspaces" in unicode; sufficient in 
     * deleting the entire toString()
     * @return String of unicode backspaces to remove object's toString
     */
    public String deleteString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < width * height + 50; i++)
        {
            sb.append("\u0008");
        }
        return sb.toString();
    }

    /**
     * Adds given light to lights array, can be Point Light or Directional Ray
     * @param l Light is the Light object added to the lights array
     */
    public void addLight(Light l) { lights.add(l); }

    /**
     * Draws line on grid; sets all tiles within given points to WALL
     * lines supported are vertical, horizontal, and slope 1 slant
     * @param x1 int coordinate x of first point
     * @param y1 int coordinate y of first point
     * @param x2 int coordinate x of second point
     * @param y2 int coordinate y of second point
     * @return true if it successfully added to array, false if not
     */
    public boolean drawLine(int x1, int y1, int x2, int y2)
    {
        try {
        if(x1 == x2)
        {
            for(int i = y1 < y2 ? y1 : y2; i <= (y1 < y2 ? y2 : y1); i++)
            {
                //System.out.println("Drawing Wall: (" + x1 + ", " + i + ") ; (" + convertCoordXtoArrX(x1) + ", " + convertCoordYtoArrY(i) + ")");
                grid[convertCoordYtoArrY(i)][convertCoordXtoArrX(x1)].setState(Tile.tileType.VERTICAL_WALL);
            }
            return true;
        }
        else if(y1 == y2)
        {
            for(int i = x1 < x2 ? x1 : x2; i <= (x1 < x2 ? x2 : x1); i++)
            {
                //System.out.println("Drawing Wall: (" + i + ", " + y1 + ") ; (" + convertCoordXtoArrX(i) + ", " + convertCoordYtoArrY(y1) + ")");
                grid[convertCoordYtoArrY(y1)][convertCoordXtoArrX(i)].setState(Tile.tileType.HORIZONTAL_WALL);
            }
            return true;
        }
        else if((y2 - y1) / (x2 - x1) == -1)
        {
            if(x1 < x2)
            {
                for(int i = 0; i <= x2 - x1; i++)
                {
                    grid[convertCoordYtoArrY(y1 - i)][convertCoordXtoArrX(x1 + i)].setState(Tile.tileType.SLANT_LEFT_WALL);
                }
                return true;
            }
            else if(x1 > x2)
            {
                for(int i = 0; i <= x1 - x2; i++)
                {
                    grid[convertCoordYtoArrY(y2 - i)][convertCoordXtoArrX(x2 + i)].setState(Tile.tileType.SLANT_LEFT_WALL);
                }
                return true;
            }
            else { return false; }
        }
        else if((y2 - y1) / (x2 - x1) == 1)
        {
            if(x1 < x2)
            {
                for(int i = 0; i <= x2 - x1; i++)
                {
                    grid[convertCoordYtoArrY(y1 + i)][convertCoordXtoArrX(x1 + i)].setState(Tile.tileType.SLANT_RIGHT_WALL);
                }
                return true;
            }
            else if(x1 > x2)
            {
                for(int i = 0; i <= x1 - x2; i++)
                {
                    grid[convertCoordYtoArrY(y2 + i)][convertCoordXtoArrX(x2 + i)].setState(Tile.tileType.SLANT_RIGHT_WALL);
                }
                return true;
            }
            else { return false; }
        }
        else
        {
            return false;
        }} catch (IndexOutOfBoundsException e) { return false; }
    }

    private int convertCoordXtoArrX(int coordX) { return (int) (width / 2) + coordX; }
    private int convertCoordYtoArrY(int coordY) { return (int) (height / 2) - coordY; }
}
