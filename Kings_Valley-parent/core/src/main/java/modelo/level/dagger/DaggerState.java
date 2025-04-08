package modelo.level.dagger;

import java.util.ArrayList;

import modelo.gameCharacters.mummys.Mummy;
import modelo.level.Pyramid;
import util.Config;

public abstract class DaggerState
{
	protected Dagger dagger;

	public DaggerState(Dagger dagger)
	{
		this.dagger = dagger;
		this.dagger.resetDelta();
	}

	public abstract void updateDagger(float deltaTime, Pyramid pyramid, ArrayList<Mummy> mummys);

	private float roundCoord(float value, float factor)
	{
		float r;
		float i = (int) ((value+factor/2) / factor);
			r = i * factor;
		return r;
	}

	protected void roundX()
	{
		
			dagger.x = this.roundCoord(dagger.x, Config.getInstance().getLevelTileWidthUnits());

	}

	protected void roundY()
	{
		
		dagger.y = this.roundCoord(dagger.y, Config.getInstance().getLevelTileHeightUnits());
		
	}
}
