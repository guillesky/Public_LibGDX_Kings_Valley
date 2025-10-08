package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;

import engine.game.Game;
import vista2D.ui.UI2D;

/**
 * Clase que representa el estado de estar entrando al juego.
 * 
 * @author Guillermo Lazzurri
 */
public class RenderStateStartingGame extends RenderState
{

	private Music introMusic;

	/**
	 * Constructor que llamara al constructor encadenao de la super clase
	 * 
	 * @param ui           Objeto que representa la Interfaz de usuario (menues)
	 * @param gameInterfaz representa la interfaz grafica del juego. Facade la
	 *                     implementa con un objeto de tipo TileMapGrafica2D
	 * @param introMusic   representa la musica de introduccion a reproducir antes
	 *                     de iniciar el juego.
	 */
	public RenderStateStartingGame(UI2D ui, ApplicationListener gameInterfaz, Music introMusic)
	{
		super(ui, gameInterfaz);
		this.introMusic = introMusic;

	}

	/**
	 * LLama al metodo render del atributo ui Si a la musica de intro le falta para
	 * terminar lo mismo que tarda la interfaz grafica en entrar al nivel, se
	 * dispara el juego (llama al metodo Facade.getInstance().fireGame()) y luego
	 * cambia el renderState del Facade a RenderStateInGame
	 */
	@Override
	public void render()
	{

		this.ui.render();

		if (7 - this.introMusic.getPosition() <= Game.getInstance().getInterfaz().getTimeToEnterLevel())
		{
			Facade.getInstance().fireGame();
			Facade.getInstance().setRenderState(new RenderStateInGame(this.ui, this.gameInterface));
		}
	}

	/**
	 * LLama al metodo create del atributo ui
	 */
	@Override
	public void create()
	{
		this.ui.create();

	}

	/**
	 * LLama al metodo resize del atributo ui
	 */

	@Override
	public void resize(int width, int height)
	{
		this.ui.resize(width, height);

	}

	/**
	 * LLama al metodo pause del atributo ui
	 */

	@Override
	public void pause()
	{
		this.ui.pause();

	}

	/**
	 * LLama al metodo resume del atributo ui
	 */

	@Override
	public void resume()
	{
		this.ui.resume();

	}

	/**
	 * LLama al metodo dispose del atributo ui
	 */

	@Override
	public void dispose()
	{
		this.ui.dispose();

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void newGame()
	{

	}

}
