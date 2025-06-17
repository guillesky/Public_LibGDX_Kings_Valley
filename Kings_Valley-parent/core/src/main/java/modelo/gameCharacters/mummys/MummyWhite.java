package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyWhite extends Mummy
{

	public MummyWhite(float x, float y, Pyramid pyramid,Player player)
	{
		super(MummyFactory.WHITE_MUMMY, x, y, Config.getInstance().getMummyWhiteParameters(), pyramid, player);

	}

	
	
	@Override
	public String toString()
	{
		return "MummyWhite "+ super.toString();
	}
	

}
