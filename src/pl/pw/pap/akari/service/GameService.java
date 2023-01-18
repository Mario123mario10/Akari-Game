package pl.pw.pap.akari.service;

import pl.pw.pap.akari.model.component.attributes.BoardButtonAttributes;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.game.board.Board;
import pl.pw.pap.akari.model.game.board.field.Field;
import pl.pw.pap.akari.model.game.board.field.type.FIELD_TYPE;
import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.model.resources.RESOURCES;
import pl.pw.pap.akari.model.utills.DIRECTION;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {
    private Random random = new Random();
    private Board board;

    public GameService() {
    }

    public List<BoardButtonAttributes> getCurrentGameButtonAttributes(int fieldSize) {
        List<BoardButtonAttributes> attributes = new ArrayList<>();

        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                attributes.add(convertToAttributes(field, fieldSize));
            }
        }

        return attributes;
    }

    public List<BoardButtonAttributes> getCurrentGameCheckButtonAttributes(int fieldSize) {
        List<BoardButtonAttributes> attributes = new ArrayList<>();

        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                attributes.add(convertToCheckAttributes(field, fieldSize));
            }
        }

        return attributes;
    }

    public void putRemoveBulb(int x, int y) {
        Field field = board.getFields()[x][y];

        switch (field.getFieldType()) {
            case EMPTY:
                putBulb(x, y);
                break;
            case BULB:
                removeBulb(x, y);
        }
    }

    public boolean isDoneBoard() {
        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                if (!checkFiledCorrect(field)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generateBoard(GameSettings gameSettings) {
        this.board = new Board(gameSettings.getX(), gameSettings.getY());
        int walls = getWallsCountByLevel(gameSettings.getX() * gameSettings.getY(),
                gameSettings.getDifficultyLevel());
        generateRandomWalls(walls);
        fillOthersFields();
        wallInfoUpdate();
        clearMapAfterGeneration();
    }
    
    public void clearBoard()
    {
    	clearMapAfterGeneration();
    }

    public GameSettings loadGame() {
        JFileChooser fileChooser = new JFileChooser("/maps");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".akari", "akari");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BoardLoader boardLoader = new BoardLoader(selectedFile);
            this.board = boardLoader.getBoard();
            return boardLoader.getSettings();
        }
        return null;
    }

    public Board getBoard() {
        return this.board;
    }

    private BoardButtonAttributes convertToCheckAttributes(Field field, int fieldSize) {
        if (checkFiledCorrect(field)) {
            return convertToAttributes(field, fieldSize);
        }
        switch (field.getFieldType()) {
            case WALL:
                return new BoardButtonAttributes(
                        new Bounds(field.getX() * fieldSize,
                                field.getY() * fieldSize,
                                fieldSize,
                                fieldSize),
                        null,
                        Color.RED,
                        field.getData()
                );
            case EMPTY:
                return new BoardButtonAttributes(
                        new Bounds(field.getX() * fieldSize,
                                field.getY() * fieldSize,
                                fieldSize,
                                fieldSize),
                        null,
                        Color.RED,
                        null
                );
            case BULB:
                return new BoardButtonAttributes(
                        new Bounds(field.getX() * fieldSize,
                                field.getY() * fieldSize,
                                fieldSize, fieldSize),
                        RESOURCES.ERR_BULB_ICON_PATH, null, null
                );
            default:
                return null;
        }
    }

    private boolean checkFiledCorrect(Field field) {
        if (isBulbField(field.getX(), field.getY())) {
            return isUnlitField(field.getX(), field.getY());
        }
        if (isEmptyField(field)) {
            return !isUnlitField(field.getX(), field.getY());
        } else {
            return isWallInfoCorrect(field);
        }
    }

    private boolean isWallInfoCorrect(Field field) {
        int bulbsAround = field.getData();
        return bulbsAround == countBulbsAround(field);
    }

    private boolean isEmptyField(Field field) {
        return field.getFieldType() == FIELD_TYPE.EMPTY;
    }

    private BoardButtonAttributes convertToAttributes(Field field, int fieldSize) {
        switch (field.getFieldType()) {
            case WALL:
                return new BoardButtonAttributes(
                        new Bounds(field.getX() * fieldSize,
                                field.getY() * fieldSize,
                                fieldSize,
                                fieldSize), null,
                        Color.BLUE,
                        field.getData()
                );
            case EMPTY:
                Color color = Color.YELLOW;
                if (field.getData() == 0) {
                    color = Color.WHITE;
                }
                return new BoardButtonAttributes(
                        new Bounds(field.getX() * fieldSize,
                                field.getY() * fieldSize,
                                fieldSize,
                                fieldSize),
                        null,
                        color, null
                );
            case BULB:
                return new BoardButtonAttributes(
                        new Bounds(field.getX() * fieldSize,
                                field.getY() * fieldSize,
                                fieldSize,
                                fieldSize),
                        RESOURCES.BULB_ICON_PATH, null, null
                );
            default:
                return null;
        }
    }


    private void generateRandomWalls(int wallsCount) {
        int errorsCount = 0;

        for (int i = 0; i < wallsCount; i++) {
            int random_x = random.nextInt(board.getX());
            int random_y = random.nextInt(board.getY());

            if (!generateWall(random_x, random_y)) {
                errorsCount++;
                i--;
            } else {
                errorsCount = 0;
            }

            if (errorsCount == 40) {
                break;
            }
        }
    }

    private int getWallsCountByLevel(int fieldsCount, DIFFICULTY_LEVEL difficulty_level) {
        switch (difficulty_level) {
            case EASY:
                return fieldsCount / 10;
            case NORMAL:
                return fieldsCount / 4;
            case HARD:
                return fieldsCount / 6;
            default:
                return 0;
        }
    }

    private boolean generateWall(int x, int y) {
        if (!validate(x, y, FIELD_TYPE.EMPTY)) {
            return false;
        }
        if (isUnlitField(x, y)) {
            Field field = board.getFields()[x][y];

            field.setFiledType(FIELD_TYPE.WALL);
            int bulbsAround = generateRandomBulbsAround(x, y);

            field.setFieldData(bulbsAround);
            return true;
        }
        return false;
    }

    private int generateRandomBulbsAround(int x, int y) {
        int bulbs = 0;
        for (int i = 0; i < 3; i++) {
            DIRECTION direction = DIRECTION.GetRandomDirection();
            switch (direction) {
                case UP:
                    if (putBulb(x, y - 1))
                        bulbs++;
                    break;
                case DOWN:
                    if (putBulb(x, y + 1))
                        bulbs++;
                    break;
                case LEFT:
                    if (putBulb(x - 1, y))
                        bulbs++;
                    break;
                case RIGHT:
                    if (putBulb(x + 1, y))
                        bulbs++;
                    break;
            }
        }
        return bulbs;
    }

    private int countBulbsAround(Field field) {
        return countBulbsAround(field.getX(), field.getY());
    }

    private int countBulbsAround(int x, int y) {
        int bulbs = 0;

        bulbs = isBulbField(x, y - 1) ? bulbs + 1 : bulbs;
        bulbs = isBulbField(x, y + 1) ? bulbs + 1 : bulbs;
        bulbs = isBulbField(x - 1, y) ? bulbs + 1 : bulbs;
        bulbs = isBulbField(x + 1, y) ? bulbs + 1 : bulbs;

        return bulbs;
    }

    private boolean isBulbField(int x, int y) {
        return validate(x, y, FIELD_TYPE.BULB);
    }

    private boolean putBulb(Field field) {
        return putBulb(field.getX(), field.getY());
    }

    private boolean putBulb(int x, int y) {
        if (!validate(x, y, FIELD_TYPE.EMPTY)) {
            return false;
        }
        if (isUnlitField(x, y)) {
            Field field = board.getFields()[x][y];

            field.setFiledType(FIELD_TYPE.BULB);
            lightUpFrom(x, y);
            return true;
        }
        return false;
    }

    private void removeBulb(int x, int y) {
        if (!validate(x, y, FIELD_TYPE.BULB)) {
            return;
        }
        Field field = board.getFields()[x][y];

        field.setFiledType(FIELD_TYPE.EMPTY);
        lightOffFrom(x, y);
    }

    private void lightUpFrom(int x, int y) {
        illuminatedCounterUpdateVerticallyFrom(x, y, true);
        illuminatedCounterUpdateHorizontallyFrom(x, y, true);
    }

    private void lightOffFrom(int x, int y) {
        illuminatedCounterUpdateVerticallyFrom(x, y, false);
        illuminatedCounterUpdateHorizontallyFrom(x, y, false);
    }

    private void illuminatedCounterUpdateVerticallyFrom(int x, int y, boolean increase) {
        Field[][] fields = board.getFields();

        for (int _y = y + 1; _y < board.getY(); _y++) {
            if (!illuminatedCounterUpdate(x, _y, increase)) {
                break;
            }
        }
        for (int _y = y - 1; _y >= 0; _y--) {
            if (!illuminatedCounterUpdate(x, _y, increase)) {
                break;
            }
        }
    }

    private void illuminatedCounterUpdateHorizontallyFrom(int x, int y, boolean increase) {
        Field[][] fields = board.getFields();

        for (int _x = x + 1; _x < board.getX(); _x++) {
            if (!illuminatedCounterUpdate(_x, y, increase)) {
                break;
            }
        }
        for (int _x = x - 1; _x >= 0; _x--) {
            if (!illuminatedCounterUpdate(_x, y, increase)) {
                break;
            }
        }
    }


    private boolean illuminatedCounterUpdate(int x, int y, boolean increase) {
        if (validate(x, y, FIELD_TYPE.WALL)) {
            return false;
        }
        Field field = board.getFields()[x][y];

        int newFieldData = increase ? field.getData() + 1 : field.getData() - 1;
        field.setFieldData(newFieldData);

        return true;
    }

    private boolean validate(int x, int y, FIELD_TYPE requiredType) {
        return board.isInBoard(x, y) && board.getFields()[x][y].getFieldType() == requiredType;
    }

    private boolean isUnlitField(Field field) {
        return isUnlitField(field.getX(), field.getY());
    }

    private boolean isUnlitField(int x, int y) {
        Field field = board.getFields()[x][y];
        return field.getData() == 0;
    }

    private void fillOthersFields() {
        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                if (isEmptyField(field) && isUnlitField(field)) {
                    putBulb(field);
                }
            }
        }
    }

    private void wallInfoUpdate() {
        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                if (field.getFieldType() == FIELD_TYPE.WALL) {
                    field.setFieldData(countBulbsAround(field));
                }
            }
        }
    }

    private void clearMapAfterGeneration() {
        for (Field[] fields : board.getFields()) {
            for (Field field : fields) {
                if (field.getFieldType() != FIELD_TYPE.WALL) {
                    field.setFieldData(0);
                    field.setFiledType(FIELD_TYPE.EMPTY);
                }
            }
        }
    }
}
