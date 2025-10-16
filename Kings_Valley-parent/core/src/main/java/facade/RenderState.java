package facade;

import com.badlogic.gdx.ApplicationListener;

import vista2D.ui.UI2D;

/**
 * Implementa el Patron State en la clase Facade para gestiona el comportamiento
 * 
 * @author Guillermo Lazzurri
 */
public abstract class RenderState implements ApplicationListener
{
	/**
	 * Interfaz de usuario del menu prinicpal
	 */
	protected UI2D ui;
	/**
	 * Interfaz grafica del juego
	 */
	protected ApplicationListener gameInterface;

	/**
	 * Constructor de clase
	 * 
	 * @param ui            Objeto que representa la Interfaz de usuario (menues)
	 * @param gameInterface representa la interfaz grafica del juego. Facade la
	 *                      implementa con un objeto de tipo TileMapGrafica2D
	 */
	public RenderState(UI2D ui, ApplicationListener gameInterface)
	{
		super();
		this.ui = ui;
		this.gameInterface = gameInterface;
	}

	/**
	 * Metodo llamado desde Facade cuando se invoca al metodo startNewGame
	 */
	public abstract void newGame();

}
