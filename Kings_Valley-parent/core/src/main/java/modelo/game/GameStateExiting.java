package modelo.game;

import modelo.DrawableElement;
import modelo.KVEventListener;
import modelo.level.door.Door;
import util.Constantes;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa el estado Saliendo del nivel (util para
 *         animaciones, musica de triunfo, etc.)
 */
public class GameStateExiting extends GameState
{
	private Door door;

	/**
	 * Llama a super(Game.ST_GAME_EXITING);<br>
	 * setea el atributo door.<br>
	 * Agrega a la interfaz el objeto drawable que representa la puerta de salida
	 * (this.game.getInterfaz().addGraphicElement(new
	 * DrawableElement(Constantes.DRAWABLE_EXIT_DOOR, door));)<br>
	 * Dispara el evento this.game.eventFired(KVEventListener.EXITING_LEVEL,
	 * null);<br>
	 * 
	 * @param door objeto de tipo Door, que representa la puerta por la cual esta
	 *             saliendo el player del nivel
	 */
	public GameStateExiting(Door door)
	{
		super(Game.ST_GAME_EXITING);
		this.door = door;
		this.game.getInterfaz().addGraphicElement(new DrawableElement(Constantes.DRAWABLE_EXIT_DOOR, door));
		this.game.eventFired(KVEventListener.EXITING_LEVEL, null);
	}

	/**
	 * LLama a super.updateframe(deltaTime); <br>
	 * Si el tiempo transcurrido supera el estipulado para salir del
	 * nivel(this.game.getInterfaz().getTimeToExitLevel()) se llama a
	 * this.game.goToLevel(door);
	 * 
	 */
	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeToExitLevel())
		{
			this.game.goToLevel(door);

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
