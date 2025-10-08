package engine.gameCharacters.mummys;

import engine.gameCharacters.player.Player;
import engine.level.Pyramid;
import util.GameRules;

/**
 * Representa una momia amarilla
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public class MummyYellow extends Mummy
{
	/**
	 * 
	 * 
	 * Llama a super(MummyFactory.YELLOW_MUMMY, x, y,
	 * Config.getInstance().getMummyYellowParameters(), pyramid, player);
	 * 
	 * 
	 * @param x       Coordenada x
	 * @param y       Coordenada y
	 * @param pyramid piramide en la que esta ubicada la momia
	 * @param player  referencia al player que la momia persigue
	 * 
	 */

	public MummyYellow(float x, float y, Pyramid pyramid, Player player)
	{
		super(MummyFactory.YELLOW_MUMMY, x, y, GameRules.getInstance().getMummyYellowParameters(), pyramid, player);

	}

	/**
	 * Retorna una cadena de texto que representa la momia (solo para debug)
	 */
	@Override
	public String toString()
	{
		return "MummyYellow " + super.toString();
	}
}
