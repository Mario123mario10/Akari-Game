package pl.pw.pap.akari.view.component.manager;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.attributes.BoardButtonAttributes;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.view.component.frames.GameFrame;
import pl.pw.pap.akari.view.component.frames.MenuFrame;
import pl.pw.pap.akari.view.component.frames.SettingsFrame;

import javax.swing.*;
import java.util.List;

public class FramesManager {
    private JFrame currentFrame;

    private MenuFrame menuFrame;
    private GameFrame currentGameFrame;
    private SettingsFrame settingsFrame;
    private EventHandler eventHandler;

    public FramesManager(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.menuFrame = new MenuFrame(eventHandler);
        this.currentFrame = menuFrame;

        menuFrame.setVisible(true);
    }

    public void setCurrentFrameInvisible() {
        currentFrame.setVisible(false);
    }

    //    MENU FRAME
    public void setResumeButtonVisible() {
        menuFrame.setResumeButtonVisible();
    }

    public void setMenuFrameVisible() {
        currentFrame = menuFrame;
        menuFrame.setVisible(true);
    }

    //    GAME FRAME
    public void generateGameFrame(GameSettings gameSettings, List<BoardButtonAttributes> attributes) {
        this.currentGameFrame = new GameFrame(eventHandler, gameSettings, attributes);
    }

    public void setGameFrameVisible() {
        currentFrame = currentGameFrame;
        currentGameFrame.setVisible(true);
    }

    public void refreshGameFrame(List<BoardButtonAttributes> attributes) {
        currentGameFrame.refreshGameButtons(attributes);
    }

    public void timerStop() {
        currentGameFrame.timerStop();
    }

    public void generateSettingsFrame(GameSettings gameSettings) {
        this.settingsFrame = new SettingsFrame(eventHandler, gameSettings);
    }

    public void setSettingsFrameVisible() {
        settingsFrame.setBounds(currentFrame.getBounds());
        currentFrame = settingsFrame;
        currentFrame.setVisible(true);
    }

    public void refreshSettingsFrame(GameSettings gameSettings) {
        settingsFrame.updateSettings(gameSettings);
    }
}
