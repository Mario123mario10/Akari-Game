package pl.pw.pap.akari.handler;

import pl.pw.pap.akari.model.component.event.BoardButtonEvent;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.service.BoardSaver;
import pl.pw.pap.akari.service.GameService;
import pl.pw.pap.akari.service.SettingsService;
import pl.pw.pap.akari.view.component.manager.FramesManager;

public class EventHandler {
    private FramesManager framesManager;

    private GameService gameService;
    private SettingsService settingsService;
    private BoardSaver boardSaver;

    public EventHandler() {
        this.gameService = new GameService();
        this.settingsService = new SettingsService();
        this.boardSaver = new BoardSaver();
    }

    public void handleEvent(CommonEvent event) {
        switch (event.getEventType()) {
            case NEW_GAME_EVENT:
                framesManager.setCurrentFrameInvisible();
                framesManager.setResumeButtonVisible();

                gameService.generateBoard(settingsService.getGameSettings());
                framesManager.generateGameFrame(settingsService.getGameSettings(),
                        gameService.getCurrentGameButtonAttributes(settingsService.getGameSettings().getFieldSize()));

                framesManager.setGameFrameVisible();
                break;
            case LOAD_GAME_EVENT:
                framesManager.setResumeButtonVisible();
                GameSettings settings = gameService.loadGame();
                if (settings != null) {
                    settingsService.setGameSettings(settings);
                    framesManager.generateGameFrame(settingsService.getGameSettings(),
                            gameService.getCurrentGameButtonAttributes(settingsService.getGameSettings().getFieldSize()));

                    framesManager.setCurrentFrameInvisible();
                    framesManager.setGameFrameVisible();
                }
                break;
            case SETTINGS_EVENT:
                framesManager.setCurrentFrameInvisible();
                framesManager.setSettingsFrameVisible();
                break;
            case QUIT_EVENT:
                System.exit(0);
            case RESUME_EVENT:
                framesManager.setCurrentFrameInvisible();
                framesManager.setGameFrameVisible();
                break;
            case SAVE_GAME_EVENT:
                boardSaver.saveToFile(settingsService.getGameSettings(), gameService.getBoard());
                break;
            case CHECK_GAME_EVENT:
                int fieldSize = settingsService.getGameSettings().getFieldSize();
                framesManager.refreshGameFrame(gameService.getCurrentGameCheckButtonAttributes(fieldSize));
                break;
            case CHANGE_CONTEXT_TO_MENU_EVENT:
                framesManager.setCurrentFrameInvisible();
                framesManager.setMenuFrameVisible();
                break;
            default:
                System.out.println("Unknown event" + event);
        }
    }

    public void handleEvent(BoardButtonEvent event) {
        gameService.putRemoveBulb(event.getX(), event.getY());
        int fieldSize = settingsService.getGameSettings().getFieldSize();
        framesManager.refreshGameFrame(gameService.getCurrentGameButtonAttributes(fieldSize));
        if (gameService.isDoneBoard()) {
            framesManager.timerStop();
        }
    }


    public void setFramesManager(FramesManager framesManager) {
        this.framesManager = framesManager;
    }

    public SettingsService getSettingsService() {
        return this.settingsService;
    }
}
