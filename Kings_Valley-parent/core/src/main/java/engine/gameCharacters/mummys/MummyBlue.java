package engine.gameCharacters.mummys;

import engine.gameCharacters.player.Player;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa una momia azul
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public class MummyBlue extends Mummy
{

	/**
	 * 
	 * Llama a super(MummyFactory.BLUE_MUMMY, x, y,
	 * Config.getInstance().getMummyBlueParameters(), pyramid,player);
	 * 
	 * @param x       Coordenada x
	 * @param y       Coordenada y
	 * @param pyramid piramide en la que esta ubicada la momia
	 * @param player  referencia al player que la momia persigue
	 * 
	 */
	public MummyBlue(float x, float y, Pyramid pyramid, Player player)
	{
		super(MummyFactory.BLUE_MUMMY, x, y, GameRules.getInstance().getMummyBlueParameters(), pyramid, player);

	}

	/**
	 * Retorna una cadena de texto que representa la momia (solo para debug)
	 */
	@Override
	public String toString()
	{
		return "MummyBlue " + super.toString();
	}

}
