package vista2D.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controler.AbstractControler;
import controler.IView;
import facade.Facade;
import modelo.KVEventListener;

public class Controler2D extends AbstractControler implements KVEventListener
{
    private ClickListener clickListener;
    private ChangeListener changeListener;
private UI2D ui;
    public Controler2D(IView view)
    {
	super(view);
	this.ui=(UI2D) view;
	this.clickListener = new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		Actor actor = event.getTarget();

		// Si el actor clickeado no tiene nombre, buscá el padre que sí lo tenga
		while (actor != null && actor.getUserObject() == null)
		{
		    actor = actor.getParent();
		}
		int action = (int) actor.getUserObject();
		switch (action)
		{
		case AbstractControler.NEW_GAME:
		{
		    doNewGame();
		    break;

		}
		case AbstractControler.EXIT:
		{
		    doExit();
		    break;

		}

		case AbstractControler.CREDITS:
		{
		    doCredits();
		    break;

		}
		case AbstractControler.RETRY:
		    Facade.getInstance().retry();
		    break;
		    
		case AbstractControler.MAIN_MENU:
		    Facade.getInstance().mainMenu();
		    break;
		}
	    }

	};

	this.changeListener = new ChangeListener()
	{
	    @Override
	    public void changed(ChangeEvent event, Actor actor)
	    {
		while (actor != null && actor.getUserObject() == null)
		{
		    actor = actor.getParent();
		}
		int action = (int) actor.getUserObject();
		Slider sl = (Slider) actor;
		switch (action)
		{
		case AbstractControler.DIFICULT_LEVEL:
		    changeDificultLevel((int) sl.getValue());
		    break;
		case AbstractControler.MASTER_VOLUME:
		    changeMasterVolume(sl.getValue());
		    break;
		case AbstractControler.MUSIC_VOLUME:
		    changeMusicVolume(sl.getValue());
		    break;
		case AbstractControler.FX_VOLUME:
		    changeSoundsVolume(sl.getValue());
		    break;
		    
		

		}
	    }

	};

    }

    protected void changeSoundsVolume(float value)
    {
	Facade.getInstance().setSoundsVolume(value / 100f);
	this.view.updateSoundsVolume();

    }

    protected void changeMusicVolume(float value)
    {
	Facade.getInstance().setMusicVolume(value / 100f);
	this.view.updateMusicVolume();

    }

    protected void changeMasterVolume(float value)
    {
	Facade.getInstance().setMasterVolume(value / 100f);
	this.view.updateMasterVolume();

    }

    public ClickListener getInputListener()
    {
	return clickListener;
    }

    private void doExit()
    {
	Gdx.app.exit();
    }

    public ChangeListener getChangeListener()
    {
	return changeListener;
    }

    private void doNewGame()
    {
	Facade.getInstance().startNewGame(this.view.getDificultLevel());
    }

    private void doCredits()
    {
	this.view.updateCredits(null);
    }

    private void changeDificultLevel(int value)
    {
	// TODO Auto-generated method stub

    }

    public void changeLanguage(String languageName)
    {
	Facade.getInstance().changeLanguage(languageName);
	this.view.updateLanguage();
    }

    @Override
    public void eventFired(int eventCode, Object param)
    {
	switch(eventCode) 
	{
	case KVEventListener.PAUSED_IS_CHANGED:
	{
	    boolean isPaused=(boolean) param;
	    if(isPaused) 
	    {this.ui.doUiInGame();;
		
	    }
	    else this.ui.doEnterGame();
	}
	
	}
	
    }
}
