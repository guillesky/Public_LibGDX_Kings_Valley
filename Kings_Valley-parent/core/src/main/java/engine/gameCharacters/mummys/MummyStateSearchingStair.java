package engine.gameCharacters.mummys;

import engine.level.LevelObject;
import engine.level.Stair;

/**
 * Clase que representa del estado de la momia "Buscando escalera"
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateSearchingStair extends MummyStateWalking
{
	private Stair stair;
	private LevelObject beginStair;

	/**
	 * Constructor de clase, llama a super(mummy, nearStairResult.getDirectionX(),
	 * whereIsPlayer);
	 * 
	 * @param mummy         Correspondiente al sujeto del patron state
	 * @param stair2        Indica el resultado de la escalera mas cercana
	 * @param whereIsPlayer Indica donde esta el player. Puede tomar los valores:
	 *                      MummyState.PLAYER_IS_UP; PLAYER_IS_SOME_LEVEL; o
	 *                      PLAYER_IS_DOWN
	 */
	public MummyStateSearchingStair(Mummy mummy,  Stair stair)
	{

		super(mummy, decideDirection(mummy, stair), -1);
		this.stair = stair;

		if (stair.getDownStair().y == this.mummy.getLastFloorCoordinate())
			this.beginStair = stair.getDownStair();
		else
			this.beginStair = stair.getUpStair();

	}

	private static int decideDirection(Mummy mummy, Stair stair)
	{
		LevelObject beginStair;
		if (stair.getDownStair().y == mummy.getLastFloorCoordinate())
			beginStair = stair.getDownStair();
		else
			beginStair = stair.getUpStair();
		
		int direccion = (int) Math.signum(beginStair.x - mummy.x);
		
		return direccion;
	}

	/**
	 * llama a super.update(deltaTime); y luego decide la direccion de la momia de
	 * acuerdo al tipo de escalera y ubicacion del player
	 */
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (!this.mummy.isInStair())
		{

			if (this.mummy.isFeetColision(this.beginStair))
			{
				this.mummy.enterStair(stair);
				if (this.beginStair==this.stair.getDownStair())
					this.mummy.getDirection().x = this.stair.directionUp();
				else
					this.mummy.getDirection().x = this.stair.directionDown();

			}

		}

	}

}
