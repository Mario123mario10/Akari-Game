package pl.pw.pap.akari.view.component.frames;

import pl.pw.pap.akari.handler.EventHandler;
import pl.pw.pap.akari.model.component.attributes.BoardButtonAttributes;
import pl.pw.pap.akari.model.component.data.Bounds;
import pl.pw.pap.akari.model.component.event.BoardButtonEvent;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.EVENT_TYPE;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.model.game.tournament.TournamentInfo;
import pl.pw.pap.akari.model.resources.RESOURCES;
import pl.pw.pap.akari.service.TournamentService;
import pl.pw.pap.akari.view.component.buttons.game.NavigateButton;
import pl.pw.pap.akari.view.component.buttons.game.BoardButton;
import pl.pw.pap.akari.view.component.buttons.game.TimerButton;
import pl.pw.pap.akari.view.component.buttons.game.WallButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameFrame extends AbstractAkariFrame {
    private JButton buttons[][];
    private GameSettings cachedSettings;
    private TimerButton timerButton;
    private NavigateButton nextButton;
    private boolean isTournament;
    private TournamentService tournamentService;

    public GameFrame(EventHandler eventHandler, GameSettings settings, List<BoardButtonAttributes> attributesList, TournamentService tournamentService) {
        super(eventHandler, settings);

        this.isTournament = tournamentService != null && tournamentService.getPlayers().size() > 0;
        this.tournamentService = tournamentService;
        prepareFrame(attributesList);
    }


    @Override
    protected void customize(Object frameDataInfo) {
        GameSettings settings = (GameSettings) frameDataInfo;
        this.cachedSettings = settings;

        int x = settings.getX();
        int y = settings.getY();

        buttons = new JButton[x][y];
        for (JButton[] button : buttons) {
            button = new JButton[y];
        }
    }

    private void prepareFrame(List<BoardButtonAttributes> attributesList) {
        addBoardButtons(attributesList);
        addNavigateBar();
    }

    public void timerStop() {
        this.timerButton.stop();
    }

    public void setNextButtonEnabled() {
        this.nextButton.setEnabled(true);
    }

    public int getTime() {
        return timerButton.getTime();
    }

    public void refreshGameButtons(List<BoardButtonAttributes> attributesList) {
        for (BoardButtonAttributes attributes : attributesList) {
            int x = attributes.getBounds().getX() / attributes.getBounds().getWidth();
            int y = attributes.getBounds().getY() / attributes.getBounds().getHeight();

            JButton buttonBoard = buttons[x][y];
            if (buttonBoard instanceof BoardButton) {
                BoardButton button = (BoardButton) buttonBoard;

                if (!button.getAttributes().equals(attributes)) {
                    button.updateButton(attributes);
                }
            }
            if (buttonBoard instanceof WallButton) {
                WallButton button = (WallButton) buttonBoard;

                if (!button.getAttributes().equals(attributes)) {
                    button.updateButton(attributes);
                }
            }
        }
    }

    @Override
    protected Dimension getDimension() {
        GameSettings gameSettings = currentGameSettings();

        return new Dimension(gameSettings.getX() * gameSettings.getFieldSize(),
                (gameSettings.getY() + 2) * gameSettings.getFieldSize());
    }

    @Override
    protected RESOURCES getBackgroundPath() {
        return null;
    }

    private void addNavigateBar() {

        addButton(generateBackButton());
        if (timerButton == null) {
            TimerButton timerButton = generateTimerButton();

            this.timerButton = timerButton;
            addButton(timerButton);
        }
        addButton(generateNextButton());
        addButton(generateSaveButton());
    }

    private void addBoardButtons(List<BoardButtonAttributes> attributesList) {
        for (BoardButtonAttributes attributes : attributesList) {
            JButton button = generateButton(attributes);

            int x = attributes.getBounds().getX() / attributes.getBounds().getWidth();
            int y = attributes.getBounds().getY() / attributes.getBounds().getHeight();

            this.buttons[x][y] = button;
            addButton(button);
        }
    }

    private JButton generateButton(BoardButtonAttributes attributes) {
        if (Color.BLUE.equals(attributes.getBackgroundColor())) {
            return new WallButton(eventHandler, attributes);
        } else {
            int x = attributes.getBounds().getX() / attributes.getBounds().getWidth();
            int y = attributes.getBounds().getY() / attributes.getBounds().getHeight();

            return new BoardButton(eventHandler, attributes,
                    new BoardButtonEvent(x, y,
                            EVENT_TYPE.BOARD_BUTTON_EVENT));
        }
    }

    private NavigateButton generateBackButton() {
        GameSettings gameSettings = currentGameSettings();

        return new NavigateButton(eventHandler, new Bounds(0,
                (gameSettings.getY()) * gameSettings.getFieldSize(),
                gameSettings.getX() * gameSettings.getFieldSize() / 3,
                50),
                isTournament ? null : new CommonEvent(EVENT_TYPE.CHANGE_CONTEXT_TO_MENU_EVENT),
                isTournament ? tournamentService.getCurrentPlayerName().getName() : "BACK"
        );
    }

    private NavigateButton generateNextButton() {
        GameSettings gameSettings = currentGameSettings();

        CommonEvent newGameEvent = new CommonEvent(EVENT_TYPE.NEW_GAME_EVENT);
        CommonEvent nextEvent = new CommonEvent(EVENT_TYPE.NEXT_EVENT);

        CommonEvent buttonEvent = isTournament ? nextEvent : newGameEvent;

        NavigateButton nextButton = new NavigateButton(eventHandler,
                new Bounds(gameSettings.getX() * gameSettings.getFieldSize() * 2 / 3,
                        (gameSettings.getY()) * gameSettings.getFieldSize(),
                        gameSettings.getX() * gameSettings.getFieldSize() / 3, 50),
                buttonEvent,
                "NEXT");
        this.nextButton = nextButton;
        if (isTournament) {
            nextButton.setEnabled(false);
        }
        return nextButton;
    }

    private TimerButton generateTimerButton() {
        GameSettings gameSettings = currentGameSettings();

        return new TimerButton(eventHandler,
                new Bounds(gameSettings.getX() * gameSettings.getFieldSize() / 3,
                        (gameSettings.getY()) * gameSettings.getFieldSize(),
                        gameSettings.getX() * gameSettings.getFieldSize() / 3, 50),
                new CommonEvent(EVENT_TYPE.CHECK_GAME_EVENT));
    }

    private NavigateButton generateSaveButton() {
        GameSettings gameSettings = currentGameSettings();

        return new NavigateButton(eventHandler,
                new Bounds(0,
                        (gameSettings.getY()) * gameSettings.getFieldSize() + 50,
                        gameSettings.getX() * gameSettings.getFieldSize(), 50),
                new CommonEvent(EVENT_TYPE.SAVE_GAME_EVENT),
                "SAVE");
    }

    private GameSettings currentGameSettings() {
        return cachedSettings;
    }
}
