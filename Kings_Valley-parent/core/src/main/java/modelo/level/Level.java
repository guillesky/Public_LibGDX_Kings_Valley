package modelo.level;

import java.util.ArrayList;
import java.util.Iterator;

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

	public Level(int id,Pyramid pyramid, ArrayList<Mummy> mummys, Player player)
	{

		this.pyramid = pyramid;
		this.mummys = mummys;
		this.player = player;
		this.id = id;
	}

	public void updateMummys(float deltaTime)
	{
		Iterator<Mummy> it = this.mummys.iterator();
		while (it.hasNext())
			it.next().update(deltaTime, player);

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
			if (this.checkPlayerSmash(trapMechanism))
				this.death();
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

	}

	private void death()
	{
		// TODO Auto-generated method stub

	}

	private boolean checkPlayerSmash(TrapMechanism trapMechanism)
	{
		int px = (int) (this.player.getX() / Config.getInstance().getLevelTileWidthUnits());
		int py = (int) (this.player.getY() / Config.getInstance().getLevelTileHeightUnits());
		return (trapMechanism.getX() == px && trapMechanism.getY() == py);

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
	

}
