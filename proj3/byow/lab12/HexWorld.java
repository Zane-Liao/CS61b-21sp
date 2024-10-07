package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private int s;

    public HexWorld() {

    }

    public void addHexagon(int s) {

    }

    private void upperDrawHexagon(int s) {
        for (int i = 0; i < s - 1; i++) {
            System.out.println(' ');
        }
        for () {

        }
    }

    private void middleDrawHexagon(int s) {
        for (int i = 0; i < s; i++) {
            TETile wall = Tileset.WALL;
        }
    }

    private void downDrawHexagon(int s) {

    }

    private void rowHexagon(int s) {

        if ((s & 1) == 1) {

        }
        else {

        }
    }
}
