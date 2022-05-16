import java.util.ArrayList;

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
        this(51, 51);
    }

    public void clear()
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if(grid[i][j].state == Tile.tileType.LIGHT) { grid[i][j].setState(Tile.tileType.EMPTY); }
            }
        }
    }

    public void simulate()
    {
        for(Light light : lights)
        {
            grid = light.simulate();
        }
    }

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

    public void addLight(Light l) { lights.add(l); }
    public void clearLights() { lights = new ArrayList<Light>(); }

    public boolean drawLine(int x1, int y1, int x2, int y2)
    {
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
        }
    }

    private int convertCoordXtoArrX(int coordX) { return (int) (width / 2) + coordX; }
    private int convertCoordYtoArrY(int coordY) { return (int) (height / 2) - coordY; }
}
