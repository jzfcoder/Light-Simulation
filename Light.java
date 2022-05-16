public class Light {
    int strength;

    Map map;
    Tile[][] grid;

    int xCoordPosition;
    int yCoordPosition;

    int topIndexLimit;
    int bottomIndexLimit;
    int rightIndexLimit;
    int leftIndexLimit;

    public Light(Map m, int s)
    {
        this(m, s, 0, 0);
    }

    public Light(Map m, int s, int x, int y)
    {
        map = m;
        strength = s;
        xCoordPosition = x;
        yCoordPosition = y;

        grid = map.getGrid();

        topIndexLimit = convertArrYtoCoordY(0);
        bottomIndexLimit = convertArrYtoCoordY(map.getHeight());
        leftIndexLimit = convertArrXtoCoordX(0);
        rightIndexLimit = convertArrXtoCoordX(map.getWidth());
    }

    public Tile[][] simulate()
    {
        return simulate(map);
    }

    public Tile[][] simulate(Map m) { return grid; }

    Tile[][] getGrid() { return map.getGrid(); }
    int getCoordx(){ return xCoordPosition; }
    int getCoordy(){ return yCoordPosition; }
    int convertArrXtoCoordX(int arrX) { return arrX - map.getWidth() / 2; }
    int convertArrYtoCoordY(int arrY) { return arrY - map.getHeight() / 2; }
}
