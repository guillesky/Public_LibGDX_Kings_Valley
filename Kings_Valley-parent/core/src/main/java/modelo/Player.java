package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;
import util.Constantes;

public class Player extends Character
{

    public Player(LevelItem door, TiledMapTileLayer layer)
    {
	super(Constantes.PLAYER, door.getX(), door.getY(), 0, 0, Config.getInstance().getCharacterWidth(),
		Config.getInstance().getCharacterHeight(), layer, Config.getInstance().getCharacterSpeedFall(),
		Config.getInstance().getPlayerSpeedWalk(), Config.getInstance().getPlayerSpeedWalkStairs(),
		Config.getInstance().getPlayerSpeedJump());

    }

}
