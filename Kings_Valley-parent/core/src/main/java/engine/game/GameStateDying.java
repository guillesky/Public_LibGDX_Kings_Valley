package engine.game;

import com.badlogic.gdx.Input;

import engine.control.Controls;
import engine.gameCharacters.player.Player;
import engine.level.Level;

/**
 * Clase que representa el estado de estar muriendo
 * 
 * @author Guillermo Lazzurri
 */
public class GameStateDying extends GameState
{

	/**
	 * Contructor de clase Llama a super(Game.ST_GAME_DYING);
	 */
	public GameStateDying()
	{
		super(Game.ST_GAME_DYING);
	}

	/**
	 * Llama a super.updateframe(deltaTime);<br>
	 * Si transcurrio el tiempo estipulado por la interfaz para representar la
	 * muerte del player (this.game.getInterfaz().getTimeDying()) verifica si debe
	 * revivir (en caso de tener vidas disponibles) o terminar el juego
	 */
	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		Level currentLevel = this.game.getCurrentLevel();
		Controls controles = this.game.getControles();
		Player player = currentLevel.getPlayer();
		player.update(controles.getNuevoRumbo(), controles.getShot(Input.Keys.SPACE), deltaTime);

		currentLevel.update(deltaTime);

		if (this.game.getDelta() >= this.game.getInterfaz().getTimeDying())
		{
			this.game.loseLive();
			if (this.game.getLives() >= 0)
				this.game.start(null, true);
			else
				this.game.endGame();
		}
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void startNewGame()
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void dying()
	{
	}

}
