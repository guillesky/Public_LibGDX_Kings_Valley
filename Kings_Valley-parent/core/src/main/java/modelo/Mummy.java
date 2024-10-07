package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;
import util.Constantes;

public class Mummy extends Character
{

    public Mummy(float x, float y, int p0, TiledMapTileLayer layer,
	    int speedFall, int speedWalk, int speedWalkStairs, int speedJump)
    {
	super(Constantes.It_mummy, x, y, p0, 0, Config.getInstance().getCharacterWidth(), Config.getInstance().getCharacterHeight(), layer, speedFall, speedWalk, speedWalkStairs, speedJump);
	
    }

   
}
