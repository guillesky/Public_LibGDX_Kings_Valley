package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;
import util.Constantes;

public class Mummy extends Character
{

    public Mummy(float x, float y, int p0, 
	    int speedFall, int speedWalk, int speedWalkStairs, int speedJump,Pyramid pyramid)
    {
	super(Constantes.It_mummy, x, y, p0, 0, Config.getInstance().getCharacterWidth(), Config.getInstance().getCharacterHeight(), speedFall, speedWalk, speedWalkStairs, speedJump,pyramid);
	
    }

   
}
