package facade;

import com.badlogic.gdx.ApplicationListener;

import vista2D.ui.UI2D;

public class RenderStateInUI extends RenderState
{

   

   

    public RenderStateInUI(UI2D ui, ApplicationListener gameInterfaz)
    {
	super(ui, gameInterfaz);
	
    }

    
    @Override
    public void create()
    {
	this.ui.create();
	
    }

    @Override
    public void resize(int width, int height)
    {
	this.ui.resize( width, height);
	
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
    public void render()
    {
	this.ui.render();
    }

    
    
    @Override
    public void newGame()
    {
	Facade.getInstance().setRenderState(new RenderStateStartingGame(this.ui,this.gameInterfaz,Facade.getInstance().getMusicIntro()));
	
    }

    
   

}
