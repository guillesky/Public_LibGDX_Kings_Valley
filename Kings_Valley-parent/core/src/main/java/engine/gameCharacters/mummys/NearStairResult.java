package engine.gameCharacters.mummys;

import engine.level.Stair;

/**
 * Clase que representa el resultado de buscar la escalera mas cercana hacia una
 * determinada direccion
 * 
 * @author Guillermo Lazzurri
 */
public class NearStairResult
{
	private Stair stair;
	private int directionX;

	/**
	 * Constructor de clase
	 * 
	 * @param stair      Indica la escalera buscada
	 * @param directionX indica hacia donde debe buscarse la escalera. Puede tomar
	 *                   los valores MummyState.RIGHT; MummyState.LEFT
	 */
	public NearStairResult(Stair stair, int directionX)
	{
		super();
		this.stair = stair;
		this.directionX = directionX;
	}

	/**
	 * @return La escalera buscada
	 */
	protected Stair getStair()
	{
		return stair;
	}

	/**
	 * @return La direccion hacia donde debe buscarse la escalera
	 */
	protected int getDirectionX()
	{
		return directionX;
	}

	/**
	 * Retorna un String que representa el objeto. (Solo para debug)
	 */
	@Override
	public String toString()
	{
		return "NearStairResult [stair=" + stair + ", directionX=" + directionX + "]";
	}

}
