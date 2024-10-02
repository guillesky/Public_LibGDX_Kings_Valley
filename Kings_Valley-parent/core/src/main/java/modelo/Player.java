package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Constantes;

public class Player extends Character
{

    public Player(LevelItem door, TiledMapTileLayer layer)
    {
	super(Constantes.PLAYER, door.getX(), door.getY(), 0, 0, 6, 18, layer);

    }

}
