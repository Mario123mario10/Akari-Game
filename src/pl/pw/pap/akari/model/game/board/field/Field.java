package pl.pw.pap.akari.model.game.board.field;

import pl.pw.pap.akari.model.game.board.field.type.FIELD_TYPE;

public class Field {
    private int x, y;
    private FIELD_TYPE filedType;
    /*
       When filedType is WALL, fieldData refers to the number of bulbs that surround the wall. 
       Otherwise, fieldData is a counter of how many bulbs illuminate this field.
     */
    private int fieldData;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.filedType = FIELD_TYPE.EMPTY;
        fieldData = 0;
    }

    public FIELD_TYPE getFieldType() {
        return filedType;
    }

    public int getData() {
        return fieldData;
    }

    public void setFieldData(int data) {
        this.fieldData = data;
    }

    public void setFiledType(FIELD_TYPE filedType) {
        this.filedType = filedType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String serialize() {
        return filedType.toString() + ";" + x + ";" + y + ";" + fieldData;
    }
}
