package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import engine.IGameControl;
import engine.game.Game;
import vista2D.ui.UI2D;

/**
 * Clase que representa el estado durante el juego
 * 
 * @author Guillermo Lazzurri
 */
public class RenderStateInGame extends RenderState
{

    /**
     * Constructor de clase, llama a super(ui, gameInterfaz);
     * 
     * @param ui            Objeto que representa la Interfaz de usuario (menues)
     * @param gameInterface representa la interfaz grafica del juego. Facade la
     *                      implementa con un objeto de tipo TileMapGrafica2D
     */
    public RenderStateInGame(UI2D ui, ApplicationListener gameInterface)
    {
	super(ui, gameInterface);

    }

    /**
     * Llama al metodo create de su atributo gameInterface
     */
    @Override
    public void create()
    {
	this.gameInterface.create();

    }

    /**
     * Llama a los metodos resize de su ui y su gameIterface
     */
    @Override
    public void resize(int width, int height)
    {
	this.gameInterface.resize(width, height);
	this.ui.resize(width, height);

    }

    /**
     * LLama al metodo pause de su gameInterface
     */
    @Override
    public void pause()
    {
	this.gameInterface.render();

    }

    /**
     * LLama al metodo resume de su gameInterface
     */
    @Override
    public void resume()
    {
	this.gameInterface.resume();

    }

    /**
     * LLama al metodo dispose de su gameInterface
     */
    @Override
    public void dispose()
    {
	this.gameInterface.dispose();

    }

    /**
     * LLama al metodo render de su gameInterface ademas realiza las acciones en
     * caso de estar pausado o mostrar o no el mapa<br>
     * Llama tambien al metodo updateGame
     */
    @Override
    public void render()
    {
	this.gameInterface.render();
	this.updateGame();
	if (Game.getInstance().isPaused())
	{
	    if (Facade.getInstance().isShowMap())
	    {
		this.ui.renderMap();
	    }
	    this.ui.getStage().act();
	    this.ui.getStage().draw();
	}
    }

    /**
     * Lee el teclado para controlar al jugador y realiza la llamada a
     * Game.getInstance().updateframe(Gdx.graphics.getDeltaTime())
     *
     */

    private void updateGame()
    {
	Game.getInstance().getControles().updateControl();

	Game.getInstance().updateframe(Gdx.graphics.getDeltaTime());

    }

    /**
     * Se sobreescribe como metodo vacio (no hace nada)
     */
    @Override
    public void newGame()
    {

    }
}
