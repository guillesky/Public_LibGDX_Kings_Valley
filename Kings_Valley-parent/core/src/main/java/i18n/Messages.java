package i18n;

public enum Messages
{

    GAME_PAUSED("Juego Pausado"), CURRENT_PYRAMID("Piramide Actual: "), SCORE("Score: "), LIVES("Lives: "),
    LANGUAGE_NAME("English"), NEW_GAME("New Game"), OPTIONS("Options"), CREDITS("Credits"), EXIT("Exit"),
    GO_BACK("Go Back"), MUSIC_VOLUME("Music Volume"), SOUNDS_VOLUME("Sounds Volume"), MASTER_VOLUME("Master Volume"),
    DIFICULT_LEVEL("Dificult Level"), LOAD_PROGRESS("Load Progress");

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
