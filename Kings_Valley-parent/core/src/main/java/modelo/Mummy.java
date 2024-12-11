package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import util.Config;
import util.Constantes;

public class Mummy extends GameCharacter
{

    public Mummy(float x, float y, int p0, 
	    float speedFall, float speedWalk, float speedWalkStairs, float speedJump,Pyramid pyramid)
    {
	super(Constantes.It_mummy, x, y, p0,  Config.getInstance().getCharacterWidth(), Config.getInstance().getCharacterHeight(), speedFall, speedWalk, speedWalkStairs, speedJump,pyramid);
	
    }

    @Override
    protected void doAction()
    {
	this.doJump();
	
    }

   
}
