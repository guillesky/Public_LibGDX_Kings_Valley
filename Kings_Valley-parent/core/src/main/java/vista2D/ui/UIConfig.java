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
	private String classicMapFile;
	private String extendedMapFile;
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
	 * Retorna el nombre del archivo de la imagen de fondo
	 * @return el nombre del archivo de la imagen de fondo
	 */
	public String getBackgroundFile()
	{
		return backgroundFile;
	}

	/**
	 * Retorna el nombre del archivo de la musica durante el menu de inicio
	 * @return el nombre del archivo de la musica durante el menu de inicio
	 */
	public String getMusicUIName()
	{
		return musicUIName;
	}

	/**
	 * Retorna el nombre del archivo del skin de la UI
	 * @return nombre del archivo del skin de la UI
	 */
	public String getSkinFile()
	{
		return skinFile;
	}

	/**
	 * Retorna el nombre del archivo de sonido de click
	 * @return Nombre del archivo de sonido de click
	 */
	public String getSfxClickFile()
	{
		return sfxClickFile;
	}

	/**
	 * Retorna el nombre del archivo de sonido de foco sobre un componente visual
	 * @return Nombre del archivo de sonido de foco sobre un componente visual
	 */
	public String getSfxFocusFile()
	{
		return sfxFocusFile;
	}

	/**
	 * Retorna el nombre del archivo de sonido de desplazamiento de un slider
	 * @return Nombre del archivo de sonido de desplazamiento de un slider
	 */
	public String getSlideSoundFile()
	{
		return slideSoundFile;
	}

	/**
	 * Retorna el nombre del archivo de la musica de intro al juego
	 * @return Nombre del archivo de la musica de intro al juego
	 */
	public String getMusicIntroName()
	{
		return musicIntroName;
	}

	/**
	 * Retorna el nombre del archivo del cursor del mouse
	 * @return Nombre del archivo del cursor del mouse
	 */
	public String getCursorFile()
	{
		return cursorFile;
	}

	/**
	 * Retorna el nombre del archivo de la imagen de fondo del mapa base para la version clasica de 15 niveles (piramides y flechas conectoras)
	 * @return Nombre del archivo de la imagen de fondo del mapa base para la version clasica de 15 niveles (piramides y flechas conectoras)
	 */
	public String getClassicMapFile()
	{
		return classicMapFile;
	}
	
	
	
	
	/**
	 * Retorna el nombre del archivo de la imagen de fondo del mapa base para la version extendida de 60 niveles (piramides y flechas conectoras)
	 * @return Nombre del archivo de la imagen de fondo del mapa base para la version extendida de 60 niveles (piramides y flechas conectoras)
	 */
	
	public String getExtendedMapFile()
	{
	    return extendedMapFile;
	}

	/**
	 * Retorna el nombre del archivo de la imagen que resalta la piramide actual
	 * @return Nombre del archivo de la imagen que resalta la piramide actual
	 */
	public String getPyramidActualFile()
	{
		return pyramidActualFile;
	}

	/**
	 * Retorna el nombre del archivo de la imagen que indica las piramides completadas
	 * @return Nombre del archivo de la imagen que indica las piramides completadas
	 */
	public String getPyramidCompletedFile()
	{
		return pyramidCompletedFile;
	}

}
