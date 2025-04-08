package modelo.level.dagger;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public class DaggerStateStucked extends DaggerState
{

	public DaggerStateStucked(Dagger dagger)
	{
		super(dagger);
		this.dagger.setState(Dagger.ST_STUCKED);

	}

	@Override
	public void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys)
	{
	}
}
