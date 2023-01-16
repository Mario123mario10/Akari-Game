package pl.pw.pap.akari.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.pw.pap.akari.model.component.event.BoardButtonEvent;
import pl.pw.pap.akari.model.component.event.CommonEvent;
import pl.pw.pap.akari.model.component.event.SettingsUpdateEvent;
import pl.pw.pap.akari.model.component.event.TournamentUpdateEvent;
import pl.pw.pap.akari.model.game.settings.GameSettings;
import pl.pw.pap.akari.service.BoardSaver;
import pl.pw.pap.akari.service.GameService;
import pl.pw.pap.akari.service.SettingsService;
import pl.pw.pap.akari.service.TournamentService;
import pl.pw.pap.akari.view.component.manager.FramesManager;

public class EventHandler {
	private FramesManager framesManager;

	private GameService gameService;
	private SettingsService settingsService;
	private BoardSaver boardSaver;
	private TournamentService tournamentService;
	private List<String> nameList;

	public EventHandler() {
		this.gameService = new GameService();
		this.settingsService = new SettingsService();
		this.boardSaver = new BoardSaver();
		this.nameList = new ArrayList<String> ();

	}

	public void handleEvent(CommonEvent event) {
		switch (event.getEventType()) {
		case NEW_GAME_EVENT:
			nameList = Arrays.asList(new String[] {});
			this.tournamentService = new TournamentService(nameList);
			
			framesManager.setCurrentFrameInvisible();
			framesManager.setResumeButtonVisible();

			gameService.generateBoard(settingsService.getGameSettings());
			framesManager.generateGameFrame(settingsService.getGameSettings(),
			gameService.getCurrentGameButtonAttributes(settingsService.getGameSettings().getFieldSize()));

			framesManager.setGameFrameVisible();
			break;
			
		case TOURNAMENT_GAME_EVENT:
			
			
			this.tournamentService = new TournamentService(nameList);
			
			framesManager.setCurrentFrameInvisible();
			framesManager.setResumeButtonVisible();

			gameService.generateBoard(settingsService.getGameSettings());
			framesManager.generateGameFrame(settingsService.getGameSettings(),
			gameService.getCurrentGameButtonAttributes(settingsService.getGameSettings().getFieldSize()));

			framesManager.setGameFrameVisible();
			break;
		case NEXT_EVENT:
			if (tournamentService.isLastPlayer())
			{
				tournamentService.incrementIndex(framesManager.getTime());
				framesManager.setCurrentFrameInvisible();
				framesManager.generateLeaderboardFrame(tournamentService.getTournamentInfo());
				framesManager.setLeaderboardFrameVisible();
				framesManager.setResumeButtonInvisible();
				break;
			}
			
			
			tournamentService.incrementIndex(framesManager.getTime());
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
			framesManager.generateSettingsFrame(settingsService.getGameSettings());
			framesManager.setSettingsFrameVisible();
			break;
		case TOURNAMENT_EVENT:
			framesManager.setCurrentFrameInvisible();
			framesManager.generateTournamentFrame();
			framesManager.setTournamentFrameVisible();
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
			framesManager.setNextButtonEnabled();
		}
	}

	public void handleEvent(SettingsUpdateEvent event) {
		var gameSettings = settingsService.getGameSettings();
		gameSettings.setX(event.getWidth());
		gameSettings.setY(event.getHeight());
		gameSettings.setDifficultyLevel(event.getDifficultyLevel());
		settingsService.setGameSettings(gameSettings);
		framesManager.refreshSettingsFrame(settingsService.getGameSettings());
	}
	
	public void handleEvent(TournamentUpdateEvent event) {
		this.nameList = Arrays.asList(event.getList());
		
		
	}

	public void setFramesManager(FramesManager framesManager) {
		this.framesManager = framesManager;
	}
	
	public TournamentService getTournamentService()
	{
		return tournamentService;
	}

	public void resetNameList() {
		nameList.clear();
		
	}
}
