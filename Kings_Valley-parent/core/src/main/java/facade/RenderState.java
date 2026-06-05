package facade;

import com.badlogic.gdx.ApplicationListener;

import vista2D.ui.UI2D;

/**
 * Representa un estado de renderizado dentro de la Facade (patron State).
 *
 * <p>
 * Cada implementacion concreta define como se comporta la aplicacion en
 * terminos de renderizado y actualizacion en un contexto determinado,
 * por ejemplo: menu principal, juego en ejecucion, pantalla de carga, etc.
 * </p>
 *
 * <p>
 * Este estado encapsula la logica de presentacion y delega el control
 * entre la interfaz de usuario (UI2D) y la interfaz grafica del juego.
 * </p>
 *
 * <p>
 * La Facade mantiene una referencia a una instancia de RenderState y
 * delega en ella los metodos del ciclo de vida de ApplicationListener.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public abstract class RenderState implements ApplicationListener
{
	 /**
     * Interfaz de usuario correspondiente a los menus del juego
     * (HUD, menu principal, opciones, etc.).
     */
	protected UI2D ui;
	  /**
     * Interfaz grafica del juego (render del mundo, entidades y mapa).
     
     */
	protected ApplicationListener gameInterface;

	/**
     * Construye un estado de renderizado.
     *
     * @param ui interfaz de usuario utilizada para menus y pantallas
     * @param gameInterface interfaz grafica del juego (render del gameplay)
     */
	public RenderState(UI2D ui, ApplicationListener gameInterface)
	{
		super();
		this.ui = ui;
		this.gameInterface = gameInterface;
	}

	/**
     * Se invoca desde Facade cuando se inicia una nueva partida.
     *
     * <p>
     * Permite al estado realizar transiciones internas, inicializar
     * recursos o cambiar el modo de renderizado.
     * </p>
     */
	public abstract void newGame();

}
