package facade;

import com.badlogic.gdx.ApplicationListener;

import vista2D.ui.UI2D;

public abstract class RenderState implements ApplicationListener
{
protected UI2D ui;
protected ApplicationListener gameInterfaz;

    

    

    public RenderState(UI2D ui, ApplicationListener gameInterfaz)
{
    super();
    this.ui = ui;
    this.gameInterfaz = gameInterfaz;
}

    
    public abstract void newGame();
    

    
   

}
