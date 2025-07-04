package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import modelo.control.Controls;
import modelo.game.Game;
import vista2D.ui.UI2D;

public class RenderStateInGame extends RenderState
{

	public RenderStateInGame(UI2D ui, ApplicationListener gameInterfaz)
	{
		super(ui, gameInterfaz);

	}

	@Override
	public void create()
	{
		this.gameInterfaz.create();

	}

	@Override
	public void resize(int width, int height)
	{
		this.gameInterfaz.resize(width, height);
		this.ui.resize(width, height);

	}

	@Override
	public void pause()
	{
		this.gameInterfaz.render();

	}

	@Override
	public void resume()
	{
		this.gameInterfaz.resume();

	}

	@Override
	public void dispose()
	{
		this.gameInterfaz.dispose();

	}

	@Override
	public void render()
	{
		this.gameInterfaz.render();
		this.updateGame();
		if (Game.getInstance().isPaused())
		{
			if (Facade.getInstance().showMap)
			{
				this.ui.renderMap();
			}
			this.ui.getStage().act();
			this.ui.getStage().draw();
		}
	}

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

	@Override
	public void newGame()
	{

	}
}
