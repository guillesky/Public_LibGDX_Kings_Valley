package i18n;

public enum Messages
{

    GAME_PAUSED("Juego Pausado"),CURRENT_PYRAMID("Piramide Actual: "), SCORE("Score: "), LIVES("Lives: "),LANGUAGE_NAME("English");

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
