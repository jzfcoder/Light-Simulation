/**
 * Directional Ray draws a ray from given point
 * The ray has an angle, and a set width
 * The arrangement of light sources is near perpendicular to angle 
 */
public class DirectionalRay extends Light
{
    int angle;
    int width;
    int[][] sources;

    public DirectionalRay(Map m, int s)
    {
        this(m, s, 0, 0, 45, 3);
    }

    public DirectionalRay(Map m, int s, int x, int y, int a, int w)
    {
        super(m, s, x, y);
        angle = a;
        width = w;
        
        sources = new int[width][2];

        // (angle > 0 ? (angle <= 45 ? 45 : (angle <= 90 ? 90 : angle <= 135 ? 135 : 180)) : (angle <= 0 ? (angle > -45 ? 0 : (angle > -90 ? -45 : (angle > -135 ? -90 : (angle > -180 ? -135 : 180)))) : 90))
        int temp = (angle > 0 ?
        (angle <= 45 ? 45 :
        (angle <= 90 ? 90 : 
        (angle <= 135 ? 135 : 180))) :
        (angle <= 0 ? (angle > -45 ? 0 :
        (angle > -90 ? -45 :
        (angle > -135 ? -90 :
        (angle > -180 ? -135 : 180)))) : 90));

        for(int i = 0; i <= (int) 5 / 2; i++)
        {
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
                // pursuing line of 90 deg
                sources[(5 / 2) + i][0] = x;
                sources[(5 / 2) + i][1] = y - i;
            }
            else
            {
                sources[(5 / 2) + i][0] = x - i;
                sources[(5 / 2) + i][1] = ((int) (Math.tan(Math.toRadians(temp + 90)) + ((Math.tan(Math.toRadians(temp + 90)) < 0) ? - 0.5 : 0.5)) * ((x - i) + x)) + y;
            }
        }
    }

    public Tile[][] simulate() { return simulate(map); }

    /**
     * Overrides Light's simulate method
     * loops through each x/y pair in sources and draws a ray at point
     * @param m Map that is simulated on
     * @return returns the modified grid
     */
    public Tile[][] simulate(Map m)
    {
        Tile[][] g = grid;
        for(int[] point : sources)
        {
            g = drawRay(angle, point[0], point[1], grid);
        }

        return g;
    }

    /**
     * Draws a ray at given point and angle, on modified grid
     * Iterates through each position and negative x, calculating
     * y from x & angle
     * Continues to iterate, until wall, edge, or strength is reached
     * Returns the modified grid with the ray
     * @param angle int angle of the ray
     * @param x int coordinate x source of the ray
     * @param y int coordinate y source of the ray
     * @param grid Tile[][] grid that is modified
     * @return returns a grid with the ray added
     */
    private Tile[][] drawRay(int angle, int x, int y, Tile[][] grid)
    {
        
        int coordX = x;
        int coordY = y;
        int prevX = x;
        int prevY = y;

        // calculate each x above start pos
        Tile nextTile = grid[convertCoordYtoArrY(y)][convertCoordXtoArrX(x)];
        nextTile.state = Tile.tileType.SOURCE;
        
        // QUADRANT #1 & #4
        if(angle > 90 || angle < -90) { angle += angle > 90 ? -180 : 180; }
        if (angle <= 90 && angle >= -90)
        {
            double rayDist = 0.0;
            boolean cleared = true;

            while (nextTile.state != Tile.tileType.HORIZONTAL_WALL
            && nextTile.state != Tile.tileType.VERTICAL_WALL
            && nextTile.state != Tile.tileType.SLANT_LEFT_WALL
            && nextTile.state != Tile.tileType.SLANT_RIGHT_WALL
            && rayDist <= strength
            && convertCoordXtoArrX(coordX) < map.getWidth() - 1
            && convertCoordYtoArrY(coordY) < map.getHeight() - 1
            && convertCoordXtoArrX(coordX) > 1
            && convertCoordYtoArrY(coordY) > 1
            )
            {
                //System.out.println("Checking between (" + prevX + ", " + prevY + ") and (" + coordX + ", " + coordY +")");
                int botDist = (coordY - prevY) / 2;
                int lowerX = prevY < coordY ? prevX : coordX;
                int lowerY = prevY < coordY ? prevY : coordY;
                int upperX = prevY < coordY ? coordX : prevX;
                int upperY = prevY < coordY ? coordY : prevY;

                for(int i = 1; i <= botDist; i++)
                {
                    if(convertCoordYtoArrY(lowerY + i) <= 0
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.HORIZONTAL_WALL
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.VERTICAL_WALL
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.SLANT_LEFT_WALL
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.SLANT_RIGHT_WALL
                    )
                    {
                        cleared = false;
                    }
                }
                for(int i = 1; i <= (upperY - lowerY) - botDist; i++)
                {
                    if(convertCoordYtoArrY(upperY - i) >= map.getHeight()
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.HORIZONTAL_WALL
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.VERTICAL_WALL
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.SLANT_LEFT_WALL
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.SLANT_RIGHT_WALL
                    )
                    {
                        //System.out.println("Works");
                        cleared = false;
                    }
                }

                if(!cleared)
                {
                    nextTile.state = Tile.tileType.EMPTY;
                    break;
                }

                prevX = coordX;
                prevY = coordY;
                // increment x, calculate y
                if (angle == 90)
                {
                    coordY++;
                }
                else if (angle == -90)
                {
                    coordY--;
                }
                else if(angle == 0 || angle == 180)
                {
                    coordX++;
                }
                else
                {
                    coordX++;
                    coordY = (int) ((Math.tan(Math.toRadians(angle)) * (coordX - x)) + y + 0.5);
                }

                nextTile = grid[angle < 0 ? Math.min(convertCoordYtoArrY(coordY), map.getHeight() - 1) : Math.max(convertCoordYtoArrY(coordY), 0)][convertCoordXtoArrX(coordX)];
                nextTile.setState(nextTile.state == Tile.tileType.EMPTY ? Tile.tileType.LIGHT : Tile.tileType.NULL);
                rayDist = Math.sqrt(Math.pow(Math.abs(coordX - x), 2) + Math.pow(Math.abs(coordY - y), 2));;
            }

            // reverse direction
            nextTile = grid[convertCoordYtoArrY(y)][convertCoordXtoArrX(x)];
            coordX = x;
            coordY = y;
            prevX = coordX;
            prevY = coordY;
            cleared = true;
            rayDist = 0;

            while (nextTile.state != Tile.tileType.HORIZONTAL_WALL
            && nextTile.state != Tile.tileType.VERTICAL_WALL
            && nextTile.state != Tile.tileType.SLANT_RIGHT_WALL
            && nextTile.state != Tile.tileType.SLANT_LEFT_WALL
            && rayDist <= strength
            && convertCoordXtoArrX(coordX) < map.getWidth() - 1
            && convertCoordYtoArrY(coordY) < map.getHeight() - 1
            && convertCoordXtoArrX(coordX) > 1
            && convertCoordYtoArrY(coordY) > 1
            && cleared
            )
            {
                //System.out.println("Checking between (" + prevX + ", " + prevY + ") and (" + coordX + ", " + coordY +")");
                int botDist = (coordY - prevY) / 2;
                int lowerX = prevY < coordY ? prevX : coordX;
                int lowerY = prevY < coordY ? prevY : coordY;
                int upperX = prevY < coordY ? coordX : prevX;
                int upperY = prevY < coordY ? coordY : prevY;

                for(int i = 1; i <= botDist; i++)
                {
                    if(convertCoordYtoArrY(lowerY + i) <= 0
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.HORIZONTAL_WALL
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.VERTICAL_WALL
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.SLANT_LEFT_WALL
                    || grid[convertCoordYtoArrY(lowerY + i)][convertCoordXtoArrX(lowerX)].state == Tile.tileType.SLANT_RIGHT_WALL
                    )
                    {
                        cleared = false;
                    }
                }
                for(int i = 1; i <= (upperY - lowerY) - botDist; i++)
                {
                    if(convertCoordYtoArrY(upperY - i) >= map.getHeight()
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.HORIZONTAL_WALL
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.VERTICAL_WALL
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.SLANT_LEFT_WALL
                    || grid[convertCoordYtoArrY(upperY - i)][convertCoordXtoArrX(upperX)].state == Tile.tileType.SLANT_RIGHT_WALL
                    )
                    {
                        //System.out.println("Works");
                        cleared = false;
                    }
                }

                if(!cleared)
                {
                    nextTile.state = Tile.tileType.EMPTY;
                    break;
                }

                prevX = coordX;
                prevY = coordY;
                //System.out.println("( " + coordX + ", " + coordY + "), arr: (" +
                //convertCoordXtoArrX(coordX) + ", " + (angle < 0 ? Math.max(convertCoordYtoArrY(coordY), 0) : Math.min(convertCoordYtoArrY(coordY), map.getHeight() - 1)) + ")");
                // increment x, calculate y
                if (angle == 90)
                {
                    coordY--;
                }
                else if (angle == -90)
                {
                    coordY--;
                }
                else if(angle == 0 || angle == 180)
                {
                    coordX--;
                }
                else
                {
                    coordX--;
                    coordY = (int) ((Math.tan(Math.toRadians(angle)) * (coordX - x)) + y - 0.5);
                }

                if(convertCoordYtoArrY(coordY) > map.getHeight() - 1) { break; }

                nextTile = grid[angle < 0 ? Math.max(convertCoordYtoArrY(coordY), 0) : Math.min(convertCoordYtoArrY(coordY), map.getHeight())][Math.min(convertCoordXtoArrX(coordX), map.getWidth() - 1)];
                nextTile.setState(nextTile.state == Tile.tileType.EMPTY ? Tile.tileType.LIGHT : Tile.tileType.NULL);
                rayDist = Math.sqrt(Math.pow(Math.abs(coordX - x), 2) + Math.pow(Math.abs(coordY - y), 2));
            }
        }
        return grid;
    }

    public void setAngle(int a) { angle = a; }

    private int convertCoordXtoArrX(int coordX) { return (int) (map.getWidth() / 2) + coordX; }
    private int convertCoordYtoArrY(int coordY) { return (int) (map.getHeight() / 2) - coordY; }
}
