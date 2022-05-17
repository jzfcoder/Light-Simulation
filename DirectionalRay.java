public class DirectionalRay extends Light
{
    int angle;
    int width;
    public DirectionalRay(Map m, int s)
    {
        super(m, s);
        angle = 45;
        width = 3;
    }

    public DirectionalRay(Map m, int s, int x, int y, int a, int w)
    {
        super(m, s, x, y);
        angle = a;
        width = w;
    }

    public Tile[][] simulate() { return simulate(map); }

    public Tile[][] simulate(Map m)
    {
        Tile[][] g = grid;
        for(int i = 0; i <= width / 2; i++)
        {
            g = drawRay(angle, super.getCoordx(), super.getCoordy() + i, grid);
        }
        for(int i = 1; i <= width / 2; i++)
        {
            g = drawRay(angle, super.getCoordx(), super.getCoordy() - i, grid);
        }

        return g;
    }

    private Tile[][] drawRay(int angle, int x, int y, Tile[][] grid)
    {
        // takes in angle in degrees, and start point (coordinate plane)
        // slope = rise / run
        // how to find slope from angle?
        // tan(angle) = opp / adjacent = rise / run
        // y = (rounded) tan(angle) * x
        
        int coordX = x;
        int coordY = y;
        int prevX = x;
        int prevY = y;

        // calculate each x above start pos
        Tile nextTile = grid[convertCoordYtoArrY(y)][convertCoordXtoArrX(x)];
        nextTile.state = Tile.tileType.SOURCE;
        
        // QUADRANT #1 & #4
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

    private int convertCoordXtoArrX(int coordX) { return (int) (map.getWidth() / 2) + coordX; }
    private int convertCoordYtoArrY(int coordY) { return (int) (map.getHeight() / 2) - coordY; }
}
