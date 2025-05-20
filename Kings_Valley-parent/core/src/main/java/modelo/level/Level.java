package modelo.level;

import java.util.ArrayList;
import java.util.Iterator;

import modelo.game.Game;
import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.player.Player;
import modelo.level.dagger.Dagger;
import modelo.level.door.Door;
import util.Config;
import util.Constantes;

public class Level
{

	private Pyramid pyramid;
	private ArrayList<Mummy> mummys = new ArrayList<Mummy>();
	private Player player = null;
	private int id;
	private Door doorIn;

	public Level(int id, Pyramid pyramid, ArrayList<Mummy> mummys, Door door, Player player)
	{

		this.pyramid = pyramid;
		this.mummys = mummys;
		this.doorIn = door;
		this.id = id;
		this.player = player;

	}

	public void updateMummys(float deltaTime)
	{
		Iterator<Mummy> it = this.mummys.iterator();
		while (it.hasNext())
			it.next().update(deltaTime);

	}

	public ArrayList<Mummy> getMummys()
	{
		return mummys;
	}

	public void updateMechanism(float deltaTime)
	{
		ArrayList<TrapMechanism> trapMechanisms = this.pyramid.getTrapMechanisms();
		ArrayList<GiratoryMechanism> giratoryMechanisms = this.pyramid.getGiratoryMechanisms();

		for (TrapMechanism trapMechanism : trapMechanisms)
		{
			trapMechanism.update(deltaTime);
			if (this.checkCharacterSmash(trapMechanism, this.player))
				this.death();
			for (Mummy mummy : mummys)
			{
				if (this.checkCharacterSmash(trapMechanism, mummy))
					mummy.die(true);
						
			}

		}

		Iterator<TrapMechanism> it = trapMechanisms.iterator();
		while (it.hasNext())
		{
			Mechanism mechanism = it.next();
			if (!mechanism.isActive())
			{
				it.remove();
				this.pyramid.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_TRAP, mechanism));
			}

		}

		for (GiratoryMechanism gMechanism : giratoryMechanisms)
		{
			if (gMechanism.isActive())
				gMechanism.update(deltaTime);
		}

		for (Door door : this.pyramid.getDoors())
		{

			door.update(deltaTime);
		}

	}

	private void death()
	{
		Game.getInstance().dying();
	}

	private boolean checkCharacterSmash(TrapMechanism trapMechanism, GameCharacter gameCharacter)
	{
		boolean condicionX = (gameCharacter.x + gameCharacter.width > trapMechanism.getX()
				&& gameCharacter.x < trapMechanism.getX() + Config.getInstance().getLevelTileWidthUnits());
		boolean condicionY = gameCharacter.y + gameCharacter.height > trapMechanism.getY()
				&& gameCharacter.y < trapMechanism.getY();
		return gameCharacter.getState() != GameCharacter.ST_JUMPING && condicionX && condicionY;

	}

	public Player getPlayer()
	{
		return player;
	}

	public Pyramid getPyramid()
	{
		return pyramid;
	}

	public void updateFlyingDagger(float deltaTime)
	{

		Iterator<Dagger> it = this.pyramid.getStuckedDaggers().iterator();
		while (it.hasNext())
		{
			Dagger dagger = it.next();
			dagger.updateDagger(deltaTime, pyramid, mummys);
		}

	}

	public void prepareToExit()
	{
		this.pyramid.prepareToExit();

	}

	public boolean isReadyToExit()
	{
		return this.pyramid.getJewels().isEmpty();
	}

	public void checkLevers()
	{
		Iterator<Door> it = this.pyramid.getDoors().iterator();
		while (it.hasNext())
		{
			Door door = it.next();
			door.checkPushLever(player);
		}

	}

	public int getId()
	{
		return id;
	}

	public Door checkPassages()
	{

		Iterator<Door> it = this.pyramid.getDoors().iterator();
		Door respuesta = null;
		Door door;
		do
		{
			door = it.next();
		} while (it.hasNext() && !door.checkEnterPassage(player));

		if (door.checkEnterPassage(player))
		{
			respuesta = door;
		}

		return respuesta;

	}

	public Door getDoorIn()
	{
		return doorIn;
	}

	public boolean checkPlayerDie()
	{

		Iterator<Mummy> it = this.mummys.iterator();

		Mummy mummy = null;
		if (it.hasNext())
			do
			{
				mummy = it.next();
			} while (it.hasNext() && !(this.playerCoincideMummy(mummy) && this.player.isColision(mummy)));

		return (this.playerCoincideMummy(mummy) && this.player.isColision(mummy));
	}

	private boolean playerCoincideMummy(Mummy mummy)
	{
		boolean bothInStair = (this.player.isInStair() && mummy.isInStair());
		boolean bothNotInStair = (!this.player.isInStair() && !mummy.isInStair());

		return (mummy.isDanger() && (bothInStair || bothNotInStair));

	}

	public void dispose()
	{
		this.pyramid.dispose();
	}

	
	
}
