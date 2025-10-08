package facade;

import com.badlogic.gdx.ApplicationListener;

import vista2D.ui.UI2D;

/**
 * Representa el estado durante la UI (no esta jugando)
 * 
 * @author Guillermo Lazzurri
 */
public class RenderStateInUI extends RenderState
{

	public RenderStateInUI(UI2D ui, ApplicationListener gameInterfaz)
	{
		super(ui, gameInterfaz);

	}

	/**
	 * LLama al metodo create de su atributo ui
	 */
	@Override
	public void create()
	{
		this.ui.create();

	}

	/**
	 * LLama al metodo resize de su atributo ui
	 */

	@Override
	public void resize(int width, int height)
	{
		this.ui.resize(width, height);

	}

	/**
	 * LLama al metodo pause de su atributo ui
	 */

	@Override
	public void pause()
	{
		this.ui.render();

	}

	/**
	 * LLama al metodo resume de su atributo ui
	 */

	@Override
	public void resume()
	{
		this.ui.resume();

	}

	/**
	 * LLama al metodo dispose de su atributo ui
	 */

	@Override
	public void dispose()
	{
		this.ui.dispose();

	}

	/**
	 * LLama al metodo render de su atributo ui
	 */

	@Override
	public void render()
	{
		this.ui.render();
	}

	/**
	 * Cambia el estado de facade a RenderStateStartingGame
	 */
	@Override
	public void newGame()
	{
		Facade.getInstance().setRenderState(
				new RenderStateStartingGame(this.ui, this.gameInterface, Facade.getInstance().getMusicIntro()));

	}

}
