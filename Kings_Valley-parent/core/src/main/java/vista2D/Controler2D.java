package vista2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controler.AbstractControler;
import controler.IView;

public class Controler2D extends AbstractControler
{
    private ClickListener clickListener;

    public Controler2D(IView view)
    {
	super(view);
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
		}
	    }

	};

    }

    public ClickListener getInputListener()
    {
	return clickListener;
    }

    private void doExit()
    {
	Gdx.app.exit();
    }

    private void doNewGame()
    {

    }

    private void doCredits()
    {
	// TODO Auto-generated method stub

    }
}
