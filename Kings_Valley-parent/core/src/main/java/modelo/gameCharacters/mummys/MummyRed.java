package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyRed extends Mummy
{

	public MummyRed(float x, float y, Pyramid pyramid)
	{
		super(MummyFactory.RED_MUMMY, x, y, Config.getInstance().getMummyRedParameters(), pyramid);

	}

}
