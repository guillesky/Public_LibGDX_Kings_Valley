package modelo.game;

import com.badlogic.gdx.Input;

import modelo.control.Controls;
import modelo.gameCharacters.player.Player;
import modelo.level.Level;
import modelo.level.door.Door;

public class GameStatePlaying extends GameState
{
	private boolean readyToExit = false;
	private boolean godMode=true;

	public GameStatePlaying()
	{
		super(Game.ST_GAME_PLAYING);
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
		if (this.readyToExit&& player.getState()!=Player.ST_ONSTAIRS)
		{
			currentLevel.checkLevers();
			Door door=currentLevel.checkPassages();
			if(door!=null && controles.getNuevoRumbo().y>0)
				this.game.stateGame=new GameStateExiting( door);
				
		}
		
		if(!this.godMode && currentLevel.checkPlayerDie()) 
		{
			this.game.stateGame=new GameStateDying();
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

	

}
