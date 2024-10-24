package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import util.Config;
import util.Constantes;

public class Player extends GameCharacter
{

	public Player(LevelItem door, Pyramid pyramid)
	{
		super(Constantes.PLAYER, door.getX(), door.getY(), 0, 0, Config.getInstance().getCharacterWidth(),
				Config.getInstance().getCharacterHeight(), Config.getInstance().getCharacterSpeedFall(),
				Config.getInstance().getPlayerSpeedWalk(), Config.getInstance().getPlayerSpeedWalkStairs(),
				Config.getInstance().getPlayerSpeedJump(), pyramid);

	}

	

}
