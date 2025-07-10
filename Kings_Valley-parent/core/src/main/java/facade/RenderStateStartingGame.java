package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;

import modelo.game.Game;
import vista2D.ui.UI2D;

public class RenderStateStartingGame extends RenderState
{

    private Music introMusic;

    public RenderStateStartingGame(UI2D ui, ApplicationListener gameInterfaz, Music introMusic)
    {
	super(ui, gameInterfaz);
	this.introMusic = introMusic;

    }

    @Override
    public void render()
    {

	this.ui.render();

	if (7 - this.introMusic.getPosition() <= Game.getInstance().getInterfaz().getTimeToEnterLevel())
	{
	    Facade.getInstance().fireGame();
	    Facade.getInstance().setRenderState(new RenderStateInGame(this.ui, this.gameInterfaz));
	}
    }

    @Override
    public void create()
    {
	this.ui.create();

    }

    @Override
    public void resize(int width, int height)
    {
	this.ui.resize(width, height);

    }

    @Override
    public void pause()
    {
	this.ui.render();

    }

    @Override
    public void resume()
    {
	this.ui.resume();

    }

    @Override
    public void dispose()
    {
	this.ui.dispose();

    }

    @Override
    public void newGame()
    {

    }

}
