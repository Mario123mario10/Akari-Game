package pl.pw.pap.akari.model.game.settings;


public class GameSettings {
    private final int fieldSize = 64;

    private int x;
    private int y;
    private DIFFICULTY_LEVEL difficultyLevel;

    public GameSettings() {
        this.x = 10;
        this.y = 10;
        this.difficultyLevel = DIFFICULTY_LEVEL.NORMAL;
    }

    public GameSettings(int x, int y, DIFFICULTY_LEVEL difficultyLevel) {
        this.x = x;
        this.y = y;
        this.difficultyLevel = difficultyLevel;
    }

    public String serialize() {
        return String.valueOf(x + ";" + y + ";" + difficultyLevel);
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DIFFICULTY_LEVEL getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DIFFICULTY_LEVEL difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
