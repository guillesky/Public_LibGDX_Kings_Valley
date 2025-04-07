package modelo.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import modelo.Juego;
import modelo.level.DrawableElement;
import modelo.level.Pyramid;
import util.Constantes;

public class PlayerStatePicking extends PlayerState
{

    public PlayerStatePicking(Player player)
    {
	super(player, Player.ST_PICKING);
    }

    @Override
    public void update(Vector2 v, boolean b, float deltaTime)
    {
	Pyramid pyramid = this.player.getPyramid();
	ArrayList<PairInt> coordToPick = this.player.getCoordToPick();
	PairInt pi = coordToPick.get(0);
	if (this.player.getTimePicking() == 0)// Recien comienza a picar
	    this.player.getPyramid().addGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pi));

	this.player.incTimePicking(deltaTime);

	if (this.player.getTimePicking() >= 1.f) // termino de picar una celda
	{

	    TiledMapTileLayer layer = (TiledMapTileLayer) pyramid.getMap().getLayers().get("front");
	    layer.setCell(pi.getX(), pi.getY(), null);
	    coordToPick.remove(0);
	    this.player.getPyramid().removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_PICKING_CELL, pi));
	    if (coordToPick.isEmpty())
		this.player.setPlayerState(new PlayerStateWalking(this.player));
	    this.player.resetTimePicking();
	}

    }

}
