package modelo.gameCharacters.mummys;

import modelo.gameCharacters.player.Player;
import modelo.level.Pyramid;
import util.Config;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa una momia rosa
 */
@SuppressWarnings("serial")
public class MummyPink extends Mummy
{

	/**
	 * 
	 * 
	 * Llama a super(MummyFactory.PINK_MUMMY, x,
	 * y,Config.getInstance().getMummyPinkParameters(), pyramid,player);
	 * 
	 * 
	 * @param x       Coordenada x
	 * @param y       Coordenada y
	 * @param pyramid piramide en la que esta ubicada la momia
	 * @param player  referencia al player que la momia persigue
	 * 
	 */

	public MummyPink(float x, float y, Pyramid pyramid, Player player)
	{
		super(MummyFactory.PINK_MUMMY, x, y, Config.getInstance().getMummyPinkParameters(), pyramid, player);

	}

	/**
	 * Retorna una cadena de texto que representa la momia (solo para debug)
	 */
	@Override
	public String toString()
	{
		return "MummyPink " + super.toString();
	}

}
