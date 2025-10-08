package vista2D.ui;

/**
 * Clase que representa la configuracion de la UI (ventanas de menues e
 * interaccion con el usuario. No la interfaz grafica del juego en si)
 * 
 * @author Guillermo Lazzurri
 */
public class UIConfig
{
	private String backgroundFile;
	private String mapFile;
	private String pyramidActualFile;
	private String pyramidCompletedFile;

	private String cursorFile;
	private String musicUIName;
	private String musicIntroName;
	private String skinFile;
	private String sfxClickFile;
	private String sfxFocusFile;
	private String slideSoundFile;

	/**
	 * @return el nombre del archivo de la imagen de fondo
	 */
	public String getBackgroundFile()
	{
		return backgroundFile;
	}

	/**
	 * @return el nombre del archivo de la musica durante el menu de inicio
	 */
	public String getMusicUIName()
	{
		return musicUIName;
	}

	/**
	 * @return nombre del archivo del skin de la UI
	 */
	public String getSkinFile()
	{
		return skinFile;
	}

	/**
	 * @return Nombre del archivo de sonido de click
	 */
	public String getSfxClickFile()
	{
		return sfxClickFile;
	}

	/**
	 * @return Nombre del archivo de sonido de foco sobre un componente visual
	 */
	public String getSfxFocusFile()
	{
		return sfxFocusFile;
	}

	/**
	 * @return Nombre del archivo de sonido de desplazamiento de un slider
	 */
	public String getSlideSoundFile()
	{
		return slideSoundFile;
	}

	/**
	 * @return Nombre del archivo de la musica de intro al juego
	 */
	public String getMusicIntroName()
	{
		return musicIntroName;
	}

	/**
	 * @return Nombre del archivo del cursor del mouse
	 */
	public String getCursorFile()
	{
		return cursorFile;
	}

	/**
	 * @return Nombre del archivo de la imagen de fondo del mapa (mapa vacio)
	 */
	public String getMapFile()
	{
		return mapFile;
	}

	/**
	 * @return Nombre del archivo de la imagen que resalta la piramide actual
	 */
	public String getPyramidActualFile()
	{
		return pyramidActualFile;
	}

	/**
	 * @return Nombre del archivo de la imagen que indica las piramides completadas
	 */
	public String getPyramidCompletedFile()
	{
		return pyramidCompletedFile;
	}

}
