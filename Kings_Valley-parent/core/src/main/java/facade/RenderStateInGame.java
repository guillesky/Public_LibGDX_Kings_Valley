package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import engine.control.Controls;
import engine.game.Game;
import vista2D.ui.UI2D;

/**
 * @author Guillermo Lazzurri Clase que representa el estado durante el juego
 */
public class RenderStateInGame extends RenderState
{

	public RenderStateInGame(UI2D ui, ApplicationListener gameInterfaz)
	{
		super(ui, gameInterfaz);

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
	 * Lee el teclado para controlar al jugador y realiza la llamada a Game.getInstance().updateframe(Gdx.graphics.getDeltaTime())
	 *
	 */
	
	private void updateGame()
	{
		Controls controles = Game.getInstance().getControles();

		float x = 0, y = 0;

		Vector2 aux;
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			y += 1;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			y -= 1;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			x += 1;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			x -= 1;
		aux = new Vector2(x, y);

		controles.setNuevoRumbo(aux);
		controles.processKey(Input.Keys.SPACE);
		controles.processKey(Input.Keys.F);
		controles.processKey(Input.Keys.N);
		controles.processKey(Input.Keys.O);

		controles.processKey(Input.Keys.P);
		controles.processKey(Input.Keys.ESCAPE);
		controles.processKey(Input.Keys.S);
		Game.getInstance().updateframe(Gdx.graphics.getDeltaTime());

	}

	/**
	 *Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void newGame()
	{

	}
}
