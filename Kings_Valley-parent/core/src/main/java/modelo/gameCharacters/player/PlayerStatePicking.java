package modelo.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.level.DrawableElement;
import modelo.level.Pyramid;
import util.Config;
import util.Constantes;

public class PlayerStatePicking extends PlayerState
{
	private ArrayList<PairInt> coordToPick = new ArrayList<PairInt>();

	public PlayerStatePicking(Player player, ArrayList<PairInt> coordToPick)
	{
		super(player, Player.ST_PICKING);
		this.coordToPick = coordToPick;
	}

	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{
		Pyramid pyramid = this.player.getPyramid();
		PairInt pairInt = coordToPick.get(0);
		if (this.player.getTimePicking() == 0)// Recien comienza a picar
			this.player.getPyramid().addGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pairInt));

		this.player.incTimePicking(deltaTime);

		if (this.player.getTimePicking() >= Config.getInstance().getTimeToEndPicking()) // termino de picar una celda
		{

			TiledMapTileLayer layer = (TiledMapTileLayer) pyramid.getMap().getLayers().get("front");
			layer.setCell(pairInt.getX(), pairInt.getY(), null);
			coordToPick.remove(0);
			this.player.getPyramid()
					.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pairInt));
			if (coordToPick.isEmpty())
			{
				int state;
				if (v.x == 0)
					state = GameCharacter.ST_IDDLE;
				else
					state = GameCharacter.ST_WALKING;
				this.player.setPlayerState(new PlayerStateWalking(this.player, state));
				pyramid.endPicking(pairInt);
			}
			this.player.resetTimePicking();
		}

	}

	@Override
	protected void die()
	{
	    this.player.setPlayerState(new PlayerStateDying(this.player));
	    
	}

}
