package engine.game;

import com.badlogic.gdx.Input;

import engine.KVEventListener;
import engine.control.Controls;
import engine.gameCharacters.player.Player;
import engine.level.Level;
import engine.level.door.Door;
import util.GameRules;

/**
 * Clase que representa el estado Jugando
 * 
 * @author Guillermo Lazzurri
 */
public class GameStatePlaying extends GameState
{
	private boolean readyToExit = false;
	

	/**
	 * Se llama a super();<br>
	 * Se dispara el evento this.game.eventFired(KVEventListener.ENTER_LEVEL, null);
	 * 
	 */
	public GameStatePlaying()
	{
		super();
		this.game.eventFired(KVEventListener.ENTER_LEVEL, this.game.getCurrentLevel());

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

		if (this.readyToExit && !player.isInStair())
		{
			currentLevel.checkLevers();
			Door door = currentLevel.checkPassages();
			if (door != null && controles.getNuevoRumbo().y > 0)
			{
				this.game.stateGame = new GameStateExiting(door);
			}
		}

		if (!GameRules.getInstance().isGodMode() && currentLevel.checkPlayerDieByMummy())
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
		 
		if (controles.getShot(Input.Keys.S))
		{
			this.game.getInterfaz().addGraphicElement(new DrawableElement(Constants.DRAWABLE_PLATFORM_ANALYSIS_RESULT,new PlatformAnalysisResult(player)));
		}*/

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void startNewGame(boolean isExtendedVersion, int episode)
	{

	}

	/**
	 * Se provcesa la muerte del player. Se cambia el estado del juego a
	 * GameStateDying
	 */
	@Override
	protected void dying()
	{
		this.game.level.getPlayer().die();
		this.game.stateGame = new GameStateDying();
	}

	@Override
	public int getRenderMode()
	{
		
		return Game.ST_GAME_PLAYING;
	}

}
