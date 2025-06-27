package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import modelo.game.Game;

public class RenderStateStartingGame extends RenderState
{

    private Music introMusic;

    public RenderStateStartingGame(ApplicationListener appListener)
    {
	super(appListener);
	this.introMusic = Facade.getInstance().getMusicIntro();

    }

    @Override
    public void render()
    {

	super.render();

	if (7 - this.introMusic.getPosition() <= Game.getInstance().getInterfaz().getTimeToEnterLevel())
	{
	    Facade.getInstance().setRenderState(new RenderStateInGame(Facade.getInstance().getGameAppListener()));
	    Gdx.input.setCursorCatched(true);
	}
    }

    @Override
    public void newGame()
    {

    }

}
