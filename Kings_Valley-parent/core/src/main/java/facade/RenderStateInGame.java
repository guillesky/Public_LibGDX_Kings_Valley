package facade;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import modelo.control.Controls;
import modelo.game.Game;

public class RenderStateInGame extends RenderState
{

    public RenderStateInGame(ApplicationListener appListener)
    {
	super(appListener);
	
    }
    
    
    
  
    @Override
    public void render()
    {
	
	super.render();
	this.updateGame();
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
	controles.processKey(Input.Keys.S);
	Game.getInstance().updateframe(Gdx.graphics.getDeltaTime());

    }




    @Override
    public void newGame()
    {
	
    }
}
