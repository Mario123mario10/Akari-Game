package pl.pw.pap.akari.view.component.frames;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.attributes.MenuButtonAttributes;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.resources.RESOURCES;
import pl.pw.pap.akari.view.component.buttons.menu.MenuButton;

import java.awt.*;

public class MenuFrame extends AbstractAkariFrame {
    private MenuButton resumeButton;

    public MenuFrame(EventHandler eventHandler) {
        super(eventHandler, null);
    }

    @Override
    protected void customize(Object frameDataInfo) {
        addButton(generateNewGameButton());
        addButton(generateLoadGameButton());
        addButton(generateSettingsButton());
        addButton(generateQuitButton());
        addButton(generateResumeButton());
        addButton(generateTournamentButton());
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(600, 800);
    }

    @Override
    protected RESOURCES getBackgroundPath() {
        return RESOURCES.MENU_FRAME_BACKGROUND_PATH;
    }

    public void setResumeButtonVisible() {
        this.resumeButton.setVisible(true);
    }
    
    public void setResumeButtonInvisible() {
        this.resumeButton.setVisible(false);
    }

    private MenuButton generateNewGameButton() {
        MenuButtonAttributes menuButtonAttributes
                = new MenuButtonAttributes(
                new Bounds(200, 380, 200, 60),
                RESOURCES.NEW_GAME_BUTTON_BACKGROUND_PATH);
//        eventHandler.resetNameList();
        return new MenuButton(eventHandler, menuButtonAttributes,
                new CommonEvent(EVENT_TYPE.NEW_GAME_EVENT));
    }

    private MenuButton generateLoadGameButton() {
        MenuButtonAttributes menuButtonAttributes
                = new MenuButtonAttributes(
                new Bounds(200, 450, 200, 60),
                RESOURCES.LOAD_GAME_BUTTON_BACKGROUND_PATH);

        return new MenuButton(eventHandler, menuButtonAttributes,
                new CommonEvent(EVENT_TYPE.LOAD_GAME_EVENT));
    }

    private MenuButton generateSettingsButton() {

        MenuButtonAttributes menuButtonAttributes
                = new MenuButtonAttributes(
                new Bounds(200, 520, 200, 60),
                RESOURCES.SETTINGS_BUTTON_BACKGROUND_PATH);

        return new MenuButton(eventHandler, menuButtonAttributes,
                new CommonEvent(EVENT_TYPE.SETTINGS_EVENT));
    }

    private MenuButton generateQuitButton() {
        MenuButtonAttributes menuButtonAttributes
                = new MenuButtonAttributes(
                new Bounds(200, 590, 200, 60),
                RESOURCES.QUIT_BUTTON_BACKGROUND_PATH);

        return new MenuButton(eventHandler, menuButtonAttributes,
                new CommonEvent(EVENT_TYPE.QUIT_EVENT));
    }

    private MenuButton generateResumeButton() {
        MenuButtonAttributes menuButtonAttributes
                = new MenuButtonAttributes(
                new Bounds(200, 310, 200, 60),
                RESOURCES.RESUME_BUTTON_BACKGROUND_PATH);

        MenuButton resumeButton = new MenuButton(eventHandler, menuButtonAttributes,
                new CommonEvent(EVENT_TYPE.RESUME_EVENT));
        resumeButton.setVisible(false);

        this.resumeButton = resumeButton;

        return resumeButton;
    }
    
    private MenuButton generateTournamentButton() {
        MenuButtonAttributes menuButtonAttributes
                = new MenuButtonAttributes(
                new Bounds(200, 660, 200, 60),
                RESOURCES.TOURNAMENT_BUTTON_BACKGROUND_PATH);

        return new MenuButton(eventHandler, menuButtonAttributes,
                new CommonEvent(EVENT_TYPE.TOURNAMENT_EVENT));
    }
}
