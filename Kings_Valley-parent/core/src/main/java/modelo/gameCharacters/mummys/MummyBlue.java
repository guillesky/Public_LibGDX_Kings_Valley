package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyBlue extends Mummy
{

    public MummyBlue(float x, float y, Pyramid pyramid,Player player)
    {
	super(MummyFactory.BLUE_MUMMY, x, y, Config.getInstance().getMummyBlueParameters(),	pyramid,player);

    }

}
