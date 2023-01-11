package pl.pw.pap.akari.model.resources;

public enum RESOURCES {
    MENU_FRAME_BACKGROUND_PATH("src/resources/background.jpg"),
    NEW_GAME_BUTTON_BACKGROUND_PATH("src/resources/new_game.jpg"),
    LOAD_GAME_BUTTON_BACKGROUND_PATH("src/resources/load_game.jpg"),
    RESUME_BUTTON_BACKGROUND_PATH("src/resources/resume.jpg"),
    SETTINGS_BUTTON_BACKGROUND_PATH("src/resources/settings.jpg"),
    QUIT_BUTTON_BACKGROUND_PATH("src/resources/quit.jpg"),
    BULB_ICON_PATH("src/resources/bulb.jpg"),
    ERR_BULB_ICON_PATH("src/resources/bulb_err.jpg"),
	TOURNAMENT_BUTTON_BACKGROUND_PATH("src/resources/new_game.jpg");
	
    private String value;

    RESOURCES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
