package modelo.game;

import com.badlogic.gdx.Input;

import modelo.control.Controls;
import modelo.gameCharacters.player.Player;
import modelo.level.Level;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa el estado de estar muriendo
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
		currentLevel.updateMechanism(deltaTime);
		currentLevel.updateMummys(deltaTime);
		currentLevel.updateFlyingDagger(deltaTime);

		if (this.game.getDelta() >= this.game.getInterfaz().getTimeDying())
		{
			this.game.lives--;
			if (this.game.lives >= 0)
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
