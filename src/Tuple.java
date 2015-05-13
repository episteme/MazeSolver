public class Tuple {
    public final int x;
    public final int y;
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Tuple)) {
            return false;
        }
        Tuple that = (Tuple) other;
        return (this.x == that.x && this.y == that.y);
    }

    @Override
    public int hashCode() {
        // http://en.wikipedia.org/wiki/Pairing_function#Cantor_pairing_function
        return ((x + y)*(x + y + 1)/2) + y;
    }
}
