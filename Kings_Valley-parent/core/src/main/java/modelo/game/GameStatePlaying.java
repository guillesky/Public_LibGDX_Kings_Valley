package modelo.game;

import com.badlogic.gdx.Input;

import modelo.control.Controls;
import modelo.gameCharacters.player.Player;
import modelo.level.Level;
import modelo.level.door.Door;

public class GameStatePlaying extends GameState
{
	private boolean readyToExit = false;

	public GameStatePlaying()
	{
		super();
	}

	@Override
	public void updateframe(float deltaTime)
	{
		this.game.incDelta(deltaTime);
		Level currentLevel = this.game.getCurrentLevel();
		Controls controles = this.game.getControles();
		Player player = currentLevel.getPlayer();
		player.update(controles.getNuevoRumbo(), controles.getShot(Input.Keys.SPACE), deltaTime);

		if (currentLevel.isReadyToExit() && !this.readyToExit)
		{
			currentLevel.prepareToExit();
			this.readyToExit = true;
		}

		currentLevel.updateMechanism(deltaTime);
		currentLevel.updateMummys(deltaTime);
		currentLevel.updateFlyingDagger(deltaTime);
		if (this.readyToExit)
		{
			currentLevel.checkLevers();
			Door door=currentLevel.checkPassages();
			if(door!=null)
				this.goToLevel(door);
		}
		// CHEATS FOR DEBUG
		if (controles.getShot(Input.Keys.F))
		{
			currentLevel.prepareToExit();
			this.readyToExit = true;
		}

		if (controles.getShot(Input.Keys.N))
			this.game.nextLevel();

		if (controles.getShot(Input.Keys.O))
			this.game.priorLevel();

	}

	private void goToLevel(Door door)
	{
		// TODO Auto-generated method stub
		
	}

}
