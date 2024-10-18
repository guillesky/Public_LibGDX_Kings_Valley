package modelo;

import java.util.ArrayList;

import util.Config;
import util.Constantes;

public class Player extends Character
{

    public Player(LevelItem door, Pyramid pyramid)
    {
	super(Constantes.PLAYER, door.getX(), door.getY(), 0, 0, Config.getInstance().getCharacterWidth(),
		Config.getInstance().getCharacterHeight(),  Config.getInstance().getCharacterSpeedFall(),
		Config.getInstance().getPlayerSpeedWalk(), Config.getInstance().getPlayerSpeedWalkStairs(),
		Config.getInstance().getPlayerSpeedJump(),pyramid);

    }
    
    public void checkItemPickUp() 
    {
    	ArrayList<LevelItem> jewels=this.pyramid.getJewels();
    	
    }

}
