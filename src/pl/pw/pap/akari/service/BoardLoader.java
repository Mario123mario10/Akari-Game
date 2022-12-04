package pl.pw.pap.akari.service;

import pl.pw.pap.akari.model.game.board.Board;
import pl.pw.pap.akari.model.game.board.field.Field;
import pl.pw.pap.akari.model.game.board.field.type.FIELD_TYPE;
import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;
import pl.pw.pap.akari.model.game.settings.GameSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BoardLoader {
    private Board board;
    private GameSettings settings;

    public BoardLoader(File file) {
        load(file);
    }

    public void load(File file) {
        List<String> serializedObjects = getSerializedObject(file);
        this.settings = deserializeGameSettings(serializedObjects.remove(0));
        this.board = deserializeBoard(serializedObjects, settings.getX(), settings.getY());
    }

    private List<String> getSerializedObject(File file) {
        List<String> serializedObjects = new LinkedList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String serializedObject;
            while ((serializedObject = bufferReader.readLine()) != null) {
                if (!serializedObject.isEmpty())
                    serializedObjects.add(serializedObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serializedObjects;
    }

    public Board getBoard() {
        return board;
    }

    public GameSettings getSettings() {
        return settings;
    }

    private GameSettings deserializeGameSettings(String serialized) {
        GameSettings gameSettings = new GameSettings();

        String[] values = serialized.split(";");
        gameSettings.setX(Integer.parseInt(values[0]));
        gameSettings.setY(Integer.parseInt(values[1]));
        gameSettings.setDifficultyLevel(DIFFICULTY_LEVEL.valueOf(values[2]));

        return gameSettings;
    }

    private Board deserializeBoard(List<String> serializedObjects, int x, int y) {
        Board board = new Board(x, y);

        for (String objecy : serializedObjects) {
            Field field = deserializeObject(objecy);
            board.getFields()[field.getX()][field.getY()] = field;
        }

        return board;
    }

    private Field deserializeObject(String serialized) {
        String[] values = serialized.split(";");
        Field field = new Field(Integer.parseInt(values[1]), Integer.parseInt(values[2]));

        FIELD_TYPE type = FIELD_TYPE.valueOf(values[0]);

        field.setFiledType(type);
        field.setFieldData(Integer.parseInt(values[3]));
        return field;
    }

}
