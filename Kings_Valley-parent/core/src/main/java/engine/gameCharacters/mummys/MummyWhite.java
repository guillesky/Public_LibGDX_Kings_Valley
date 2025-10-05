package engine.gameCharacters.mummys;

import engine.gameCharacters.player.Player;
import engine.level.Pyramid;
import util.GameRules;

@SuppressWarnings("serial")
public class MummyWhite extends Mummy
{
	/**
	 * 
	 * 
	 * Llama a super(MummyFactory.WHITE_MUMMY, x, y,
	 * Config.getInstance().getMummyWhiteParameters(), pyramid, player);
	 * 
	 * 
	 * @param x       Coordenada x
	 * @param y       Coordenada y
	 * @param pyramid piramide en la que esta ubicada la momia
	 * @param player  referencia al player que la momia persigue
	 * 
	 */

	public MummyWhite(float x, float y, Pyramid pyramid, Player player)
	{
		super(MummyFactory.WHITE_MUMMY, x, y, GameRules.getInstance().getMummyWhiteParameters(), pyramid, player);

	}

	/**
	 * Retorna una cadena de texto que representa la momia (solo para debug)
	 */
	@Override
	public String toString()
	{
		return "MummyWhite " + super.toString();
	}

}
