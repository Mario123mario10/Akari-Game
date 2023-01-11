package pl.pw.pap.akari.model.component.event;

import pl.pw.pap.akari.model.game.settings.DIFFICULTY_LEVEL;

public class SettingsUpdateEvent {
    private final int width;
    private final int height;
    private final DIFFICULTY_LEVEL difficultyLevel;
    private final String [] nameList;

    public SettingsUpdateEvent(int width, int height, DIFFICULTY_LEVEL difficultyLevel, String [] nameList) {
        this.width = width;
        this.height = height;
        this.difficultyLevel = difficultyLevel;
        this.nameList = nameList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DIFFICULTY_LEVEL getDifficultyLevel() {
        return difficultyLevel;
    }
    
    public String [] getList()
    {
    	return nameList;
    }
}