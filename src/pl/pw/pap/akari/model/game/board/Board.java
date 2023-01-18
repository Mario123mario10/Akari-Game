package pl.pw.pap.akari.model.game.board;

import pl.pw.pap.akari.model.game.board.field.Field;

public class Board {
    private Field[][] fields;

    public Board(int x, int y) {
        this.fields = new Field[x][y];
        for (int _x = 0; _x < x; _x++) {
            for (int _y = 0; _y < y; _y++) {
                fields[_x][_y] = new Field(_x, _y);
            }
        }
    }
    
    public void clear (int x, int y) {
        this.fields = new Field[x][y];
        for (int _x = 0; _x < x; _x++) {
            for (int _y = 0; _y < y; _y++) {
                fields[_x][_y] = new Field(_x, _y);
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public int getX() {
        return fields.length;
    }

    public int getY() {
        return fields[0].length;
    }

    public boolean isInBoard(int x, int y) {
        return 0 <= x && x < getX() && 0 <= y && y < getY();
    }
}
