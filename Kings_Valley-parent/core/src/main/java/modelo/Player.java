package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import util.Config;
import util.Constantes;

public class Player extends GameCharacter
{

	public Player(LevelItem door, Pyramid pyramid)
	{
		super(Constantes.PLAYER, door.getX(), door.getY(), 0,  Config.getInstance().getCharacterWidth(),
				Config.getInstance().getCharacterHeight(), Config.getInstance().getCharacterSpeedFall(),
				Config.getInstance().getPlayerSpeedWalk(), Config.getInstance().getPlayerSpeedWalkStairs(),
				Config.getInstance().getPlayerSpeedJump(), pyramid);

	}

	@Override
	public void move(Vector2 v, boolean b, float deltaTime)
	{
		super.move(v, b, deltaTime);
		LevelItem joya=this.checkItemFeetColision(this.pyramid.getJewels());
		if(joya!=null)
			this.pyramid.removeJewel(joya);
	}

	

}
