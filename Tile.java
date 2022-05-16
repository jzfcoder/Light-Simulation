public class Tile {
    tileType state;

    public Tile()
    {
        state = tileType.EMPTY;
    }
    
    public String toString()
    {
        switch(state)
        {
            case EMPTY:
            return " ";

            case SOURCE:
            return "+";

            case LIGHT:
            return ".";

            case HORIZONTAL_WALL:
            return "_";

            case VERTICAL_WALL:
            return "|";

            case SLANT_RIGHT_WALL:
            return "/";

            case SLANT_LEFT_WALL:
            return "\\";

            case CENTER:
            return "X";

            case NULL:
            return "what the hell happened here";

            default:
            return " ";
        }
    }

    public void setState(tileType s)
    {
        if(s != tileType.NULL) { state = s; }
    }

    public enum tileType {
        EMPTY,
        HORIZONTAL_WALL,
        VERTICAL_WALL,
        SLANT_RIGHT_WALL,
        SLANT_LEFT_WALL,
        LIGHT,
        SOURCE,
        CENTER,
        NULL
    }
}
