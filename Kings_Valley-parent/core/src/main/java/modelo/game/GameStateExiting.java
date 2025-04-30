package modelo.game;

import modelo.level.DrawableElement;
import modelo.level.door.Door;
import util.Constantes;

public class GameStateExiting extends GameState
{
	private Door door;

	public GameStateExiting(Door door)
	{
		super(Game.ST_GAME_EXITING);
		this.door = door;
		this.game.getInterfaz().addGraphicElement(new DrawableElement(Constantes.DRAWABLE_EXIT_DOOR, door));

	}

	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);

		if (this.game.getDelta() >= this.game.getTimeToTransicion() / 2)
		{
			this.game.goToLevel(door);
			
		}
	}

}
