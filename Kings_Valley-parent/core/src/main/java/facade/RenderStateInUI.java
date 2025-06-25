package facade;

import com.badlogic.gdx.ApplicationListener;

import vista2D.ui.UI2D;

public class RenderStateInUI extends RenderState
{

    public RenderStateInUI(ApplicationListener appListener)
    {
	super(appListener);
	
    }

    @Override
    public void newGame()
    {
	Facade.getInstance().setRenderState(new RenderStateStartingGame(Facade.getInstance().getUi()));
	
    }
   

}
