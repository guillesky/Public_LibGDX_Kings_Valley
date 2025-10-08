package engine.gameCharacters.mummys;

import engine.gameCharacters.player.Player;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa una momia roja
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public class MummyRed extends Mummy
{
	/**
	 * 
	 * 
	 * Llama a super(MummyFactory.RED_MUMMY, x, y,
	 * Config.getInstance().getMummyRedParameters(), pyramid,player);
	 * 
	 * 
	 * @param x       Coordenada x
	 * @param y       Coordenada y
	 * @param pyramid piramide en la que esta ubicada la momia
	 * @param player  referencia al player que la momia persigue
	 * 
	 */

	public MummyRed(float x, float y, Pyramid pyramid, Player player)
	{
		super(MummyFactory.RED_MUMMY, x, y, GameRules.getInstance().getMummyRedParameters(), pyramid, player);

	}

	/**
	 * Retorna una cadena de texto que representa la momia (solo para debug)
	 */
	@Override
	public String toString()
	{
		return "MummyRed " + super.toString();
	}
}
