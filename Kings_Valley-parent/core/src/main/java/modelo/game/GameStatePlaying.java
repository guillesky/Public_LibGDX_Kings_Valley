package modelo.game;

import com.badlogic.gdx.Input;

import modelo.KVEventListener;
import modelo.control.Controls;
import modelo.gameCharacters.player.Player;
import modelo.level.Level;
import modelo.level.door.Door;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa el estado Jugando
 */
public class GameStatePlaying extends GameState
{
	private boolean readyToExit = false;
	private boolean godMode = true;

	/**
	 * Se llama a super(Game.ST_GAME_PLAYING);<br>
	 * Se dispara el evento this.game.eventFired(KVEventListener.ENTER_LEVEL, null);
	 * 
	 */
	public GameStatePlaying()
	{
		super(Game.ST_GAME_PLAYING);
		this.game.eventFired(KVEventListener.ENTER_LEVEL, null);

	}

	/**
	 * llama a super.updateframe(deltaTime); <br>
	 * Actaliza el estado del player, las momias, los mecanismos, las dagas
	 * lanzadas, chequea muertes, verifica salidas de nivel, etc.
	 * 
	 */
	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		Level currentLevel = this.game.getCurrentLevel();
		Controls controles = this.game.getControles();
		Player player = currentLevel.getPlayer();
		player.update(controles.getNuevoRumbo(), controles.getShot(Input.Keys.SPACE), deltaTime);

		if (currentLevel.isReadyToExit() && !this.readyToExit)
		{
			currentLevel.prepareToExit();
			this.readyToExit = true;
		}

		
		currentLevel.update(deltaTime);
		
		if (this.readyToExit && player.getState() != Player.ST_ONSTAIRS)
		{
			currentLevel.checkLevers();
			Door door = currentLevel.checkPassages();
			if (door != null && controles.getNuevoRumbo().y > 0)
			{
				this.game.stateGame = new GameStateExiting(door);
				this.game.eventFired(KVEventListener.FINISH_CURRENT_LEVEL, null);
			}
		}

		if (!this.godMode && currentLevel.checkPlayerDieByMummy())
		{
			this.game.dying();
		}
		// CHEATS FOR DEBUG
		/*
		 * if (controles.getShot(Input.Keys.F)) { currentLevel.prepareToExit();
		 * this.readyToExit = true; }
		 * 
		 * if (controles.getShot(Input.Keys.N)) this.game.nextLevel();
		 * 
		 * if (controles.getShot(Input.Keys.O)) this.game.dying();
		 * 
		 * if (controles.getShot(Input.Keys.S)) this.game.showPlayer();
		 */
	}
	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void startNewGame()
	{

	}

	/**
	 *Se provcesa la muerte del player. Se cambia el estado del juego a GameStateDying
	 */
	@Override
	protected void dying()
	{
		this.game.level.getPlayer().die();
		this.game.stateGame = new GameStateDying();
	}

}
