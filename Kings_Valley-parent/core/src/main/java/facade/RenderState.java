package facade;

import com.badlogic.gdx.ApplicationListener;

public abstract class RenderState implements ApplicationListener
{
protected ApplicationListener appListener;

    public RenderState(ApplicationListener appListener)
{
    
    this.appListener = appListener;
}

    @Override
    public void create()
    {
	this.appListener.create();
	
    }

    @Override
    public void resize(int width, int height)
    {
	this.appListener.resize( width, height);
	
    }

    @Override
    public void render()
    {
	this.appListener.render();
	
    }

    @Override
    public void pause()
    {
	this.appListener.render();
	
    }

    @Override
    public void resume()
    {
	this.appListener.resume();
	
    }

    @Override
    public void dispose()
    {
	this.appListener.dispose();
	
    }
    
    public abstract void newGame();
    

    
   

}
