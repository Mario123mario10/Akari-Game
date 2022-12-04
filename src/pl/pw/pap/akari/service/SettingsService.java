package pl.pw.pap.akari.service;

import pl.pw.pap.akari.model.game.settings.GameSettings;

public class SettingsService {
    private GameSettings gameSettings;

    public SettingsService() {
        this.gameSettings = new GameSettings();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }
}
