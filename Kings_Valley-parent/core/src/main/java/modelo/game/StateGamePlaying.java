package modelo.game;

import com.badlogic.gdx.Input;

import modelo.control.Controls;
import modelo.gameCharacters.player.Player;
import modelo.level.Level;

public class StateGamePlaying extends StateGame
{

    public StateGamePlaying()
    {
	super();
    }

    @Override
    public void updateframe(float deltaTime)
    {
	this.game.incDelta(deltaTime);
	Level currentLevel=this.game.getCurrentLevel();
	Controls controles=this.game.getControles();
	    Player player = currentLevel.getPlayer();
	    player.update(controles.getNuevoRumbo(), controles.getShot(Input.Keys.SPACE), deltaTime);
	    if (controles.getShot(Input.Keys.N))
		this.game.finishLevel();
	    currentLevel.updateMechanism(deltaTime);
	    currentLevel.updateMummys(deltaTime);
	    currentLevel.updateFlyingDagger(deltaTime);
    }

}
