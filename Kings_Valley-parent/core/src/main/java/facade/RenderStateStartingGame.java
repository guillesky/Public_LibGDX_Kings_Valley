package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;

public class RenderStateStartingGame extends RenderState
{

    private Music introMusic;

    public RenderStateStartingGame(ApplicationListener appListener)
    {
	super(appListener);
	this.introMusic =Facade.getInstance().getMusicIntro();

    }

    @Override
    public void render()
    {

	super.render();
	if (!this.introMusic.isPlaying())
	    Facade.getInstance().setRenderState(new RenderStateInGame(Facade.getInstance().getGameAppListener()));
    }

    @Override
    public void newGame()
    {

    }

}
