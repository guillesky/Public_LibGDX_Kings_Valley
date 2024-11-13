package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import util.Config;
import util.Constantes;

public class Player extends GameCharacter
{
	public static final int ST_GIRATORY_RIGHT = 20;
	public static final int ST_GIRATORY_LEFT = 21;

	public Player(LevelItem door, Pyramid pyramid)
	{
		super(Constantes.PLAYER, door.getX(), door.getY(), 0, Config.getInstance().getCharacterWidth(),
				Config.getInstance().getCharacterHeight(), Config.getInstance().getCharacterSpeedFall(),
				Config.getInstance().getPlayerSpeedWalk(), Config.getInstance().getPlayerSpeedWalkStairs(),
				Config.getInstance().getPlayerSpeedJump(), pyramid);

	}

	@Override
	public void move(Vector2 v, boolean b, float deltaTime)
	{

		LevelItem giratory = this.checkRectangleColision(this.pyramid.getGiratorys());
		if (giratory != null)
		{
			this.pyramid.activateGiratory(giratory);
			GiratoryDoor gd=(GiratoryDoor) giratory;
			
		}

		if (this.state == Player.ST_GIRATORY_LEFT || this.state == Player.ST_GIRATORY_RIGHT)
		{// TODO}

		} else
			super.move(v, b, deltaTime);

		LevelItem joya = this.checkItemFeetColision(this.pyramid.getJewels());
		if (joya != null)
			this.pyramid.removeJewel(joya);

		LevelItem activator = this.checkRectangleColision(this.pyramid.getActivators());
		if (giratory != activator)
			this.pyramid.activateWall(activator);

	}

	public LevelItem checkRectangleColision(ArrayList levelItems)
	{

		Iterator<LevelItem> it = levelItems.iterator();
		LevelItem respuesta = null;
		LevelItem item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !this.isColision(item));

		if (this.isColision(item))
		{
			respuesta = item;
		}

		return respuesta;
	}

}
