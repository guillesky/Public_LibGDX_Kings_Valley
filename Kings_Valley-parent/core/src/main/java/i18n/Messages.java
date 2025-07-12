package i18n;

public enum Messages
{

    GAME_PAUSED("Juego Pausado"), CURRENT_PYRAMID("Piramide Actual: "), SCORE("Score: "), LIVES("Lives: "),
    LANGUAGE_NAME("English"), NEW_GAME("New Game"), OPTIONS("Options"), CREDITS("Credits"), EXIT("Exit"),
    GO_BACK("Go Back"), MUSIC_VOLUME("Music Volume"), FX_VOLUME("Sounds Volume"), MASTER_VOLUME("Master Volume"),
    DIFICULT_LEVEL("Dificult Level"), LOAD_PROGRESS("Load Progress"), LANGUAGE("Language"), EASY("Easy"),
    NORMAL("Normal"), HARD("Hard"), MAIN_MENU("Main Menu"), RETRY("Retry"), MAP("Map"), GOING_BACK("Back to Pyramid "),
    ENTERING("Entering Pyramid "), GAME_OVER("Game Over"),
    DIALOG_RETRY_TEXT("Are you sure you want to retry the level? (You will lose a life)"),
    DIALOG_RETRY_TITLE("Confirm Retry"), DIALOG_EXIT_TITLE("Confirm Exit"),
    DIALOG_EXIT_TEXT("Are you sure you want to exit the game?"), YES("Yes"), NO("No"),
    DIALOG_GO_MAIN_MENU_TITLE("Confirm Go Back to Main Menu"),
    DIALOG_GO_MAIN_MENU_TEXT("Are you sure you want to return to the main menu? (You will lose all progress)");

    private String value;

    private Messages(String value)
    {
	this.value = value;
    }

    public String getValue()
    {
	return value;
    }

    public void setValue(String valor)
    {
	this.value = valor;
    }

}
