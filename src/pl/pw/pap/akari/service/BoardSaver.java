package pl.pw.pap.akari.service;

import pl.pw.pap.akari.model.game.board.Board;
import pl.pw.pap.akari.model.game.board.field.Field;
import pl.pw.pap.akari.model.game.settings.GameSettings;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BoardSaver {
    private DateFormat fileNameFormat = new SimpleDateFormat("ddMMyy-hhmmss");

    public void saveToFile(GameSettings gameSettings, Board board) {
        Date date = new Date();
        try {
            PrintWriter writer = new PrintWriter("maps/" + fileNameFormat.format(date) + ".akari", StandardCharsets.UTF_8);
            for (String serializedObjects : getSerializedMapObjects(gameSettings, board)) {
                writer.println(serializedObjects);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("BoardSaver.saveToFile" + e.toString());
        }
    }

    private List<String> getSerializedMapObjects(GameSettings gameSettings, Board board) {
        List<String> serializedObjects = new LinkedList<>();

        serializedObjects.add(gameSettings.serialize());
        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                serializedObjects.add(field.serialize());
            }
        }

        return serializedObjects;
    }
}
