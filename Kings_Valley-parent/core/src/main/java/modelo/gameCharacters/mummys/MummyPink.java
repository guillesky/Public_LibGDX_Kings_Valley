package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;
import util.Config;

@SuppressWarnings("serial")
public class MummyPink extends Mummy
{

    public MummyPink(float x, float y, Pyramid pyramid,Player player)
    {
	super(MummyFactory.PINK_MUMMY, x, y,Config.getInstance().getMummyPinkParameters(),	pyramid,player);

    }

    @Override
	public String toString()
	{
		return "MummyPink "+ super.toString();
	}  

}
