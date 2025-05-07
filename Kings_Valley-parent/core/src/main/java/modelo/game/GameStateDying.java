package modelo.game;

import com.badlogic.gdx.Input;

import modelo.control.Controls;
import modelo.gameCharacters.player.Player;
import modelo.level.Level;

public class GameStateDying extends GameState
{

	public GameStateDying()
	{
		super(Game.ST_GAME_DYING);
	}

	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		Level currentLevel = this.game.getCurrentLevel();
		Controls controles = this.game.getControles();
		Player player = currentLevel.getPlayer();
		player.update(controles.getNuevoRumbo(), controles.getShot(Input.Keys.SPACE), deltaTime);
		currentLevel.updateMechanism(deltaTime);
		currentLevel.updateMummys(deltaTime);
		currentLevel.updateFlyingDagger(deltaTime);

		if (this.game.getDelta() >= this.game.getInterfaz().getTimeDying())
		{
			this.game.lives--;
			this.game.start();
		}
	}

}
