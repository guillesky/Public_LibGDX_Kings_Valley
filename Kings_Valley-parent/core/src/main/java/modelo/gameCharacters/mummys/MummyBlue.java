package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;
import util.GameRules;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa una momia azul
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
