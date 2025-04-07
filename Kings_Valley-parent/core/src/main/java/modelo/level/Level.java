package modelo.level;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.IGrafica;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.player.Player;
import util.Config;
import util.Constantes;

public class Level
{
    private int id;
    private IGrafica interfaz = null;

    private Pyramid pyramid;
    private ArrayList<Mummy> mummys = new ArrayList<Mummy>();
    private Player player = null;

    public Level(int id, IGrafica interfaz, Pyramid pyramid, ArrayList<Mummy> mummys, Player player)
    {

	this.id = id;
	this.interfaz = interfaz;
	this.pyramid = pyramid;
	this.mummys = mummys;
	this.player = player;

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
	Cell cell = null;
	Iterator<Dagger> it = this.pyramid.getFliyingDaggers().iterator();
	while (it.hasNext())
	{
	    Dagger dagger = it.next();
	    if (dagger.getState() == Dagger.ST_THROWING_HORIZONTAL)
	    {
		dagger.incX(Config.getInstance().getFlyingDaggerSpeed() * deltaTime);
		if (dagger.isRight())
		    cell = this.pyramid.getCell(dagger.x + dagger.width, dagger.y);
		else
		    cell = this.pyramid.getCell(dagger.x, dagger.y);
		if (cell != null)
		    dagger.crash();
		Iterator<Mummy> itMummy = this.mummys.iterator();
		Mummy mummy = null;
		do
		{
		    if (itMummy.hasNext())
			mummy = itMummy.next();

		} while (itMummy.hasNext() && !dagger.isColision(mummy));
		if (dagger.isColision(mummy))
		{dagger.crash();
		mummy.die();
		}
	    }
	}

    }
}
