package util;

public enum Messages
{

    GAME_PAUSED("Juego Pausado");

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
