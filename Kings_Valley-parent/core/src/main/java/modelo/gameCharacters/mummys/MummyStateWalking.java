package modelo.gameCharacters.mummys;

import modelo.gameCharacters.abstractGameCharacter.GameCharacter;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa del estado de la momia "Caminando"
 */
public class MummyStateWalking extends MummyState
{
	protected boolean doJump = false;
	protected int whereIsPlayer;

	/**
	 * Constructor de clase. LLama a super(mummy, GameCharacter.ST_WALKING);
	 * 
	 * @param mummy         Correspondiente al sujeto del patron state
	 * @param directionX    indica la direccion hacia donde debe caminar la momia.
	 *                      Puede tomar los valores: MummyState.NONE;
	 *                      MummyState.RIGHT o MummyState.LEFT
	 * @param whereIsPlayer indica donde esta el player con respecto a la momia.
	 *                      Puede tomar los valores: MummyState.PLAYER_IS_UP;
	 *                      MummyState.PLAYER_IS_DOWN;
	 *                      MummyState.PLAYER_IS_SOME_LEVEL
	 */
	public MummyStateWalking(Mummy mummy, int directionX, int whereIsPlayer)
	{
		super(mummy, GameCharacter.ST_WALKING);
		this.whereIsPlayer = whereIsPlayer;

		if (directionX == MummyState.NONE)
			this.setDirection();
		else
			this.mummy.getDirection().x = directionX;

		this.timeToChange = this.mummy.getTimeToDecide();

	}

	/**
	 * Dedice la direccion de la momia de acuerdo a la posicion relativa del player
	 */
	protected void setDirection()
	{
		if (mummy.getX() < this.mummy.player.getX())
			this.mummy.getDirection().x = 1;
		else
			this.mummy.getDirection().x = -1;

	}

	
	@Override
	public void update(float deltaTime)
	{

		if ((this.mummy.getState() == GameCharacter.ST_WALKING || this.mummy.getState() == GameCharacter.ST_IDDLE)
				&& !this.mummy.isInStair())
		{
			this.checkChangeStatus();
			float x = this.mummy.x;
			int desp = 1;
			if (!this.mummy.isLookRight())
			{
				x = this.mummy.x + this.mummy.width;
				desp = -1;
			}
			int type = this.typeEndPlatform(x, desp);

			this.checkEndOfPlataform(type);
		}
		if (this.mummy.getStressLevel() >= 9)
		{ // muere por estres
			this.mummy.die(true);
		}

		if (this.mummy.getStressLevel() > 0)
			this.mummy.calmStress(deltaTime / 6);

		this.mummy.move(this.mummy.getDirection(), doJump, deltaTime);

		this.doJump = false;

	}

	/**
	 *Retorna true
	 */
	@Override
	protected boolean isDanger()
	{
		return true;
	}

	/**
	 * Si pasa el tiempo correspondiente a this.timeToChange, entonces el estado cambia a new MummyStateDeciding(this.mummy) 
	 */
	protected void checkChangeStatus()
	{
		if (this.mummy.getTimeInState() >= this.timeToChange)
			this.mummy.mummyState = new MummyStateDeciding(this.mummy);
	}

	/**
	 * Llamado para que la momia rebote contra la pared
	 */
	private void bounces()
	{
		this.mummy.getDirection().x *= -1;
		this.mummy.stressing();
	}

	/**
	 *Llamado en caso de chocar contra un muro o una giratoria
	 */
	public void doInCrashToWallOrGiratory(int crashStatus, int type)
	{
		if (crashStatus == Mummy.BLOCK_BRICK)

		{

			if (this.mummy.canJump() && type == EndPlatform.END_STEP && (this.whereIsPlayer == MummyState.PLAYER_IS_UP
					|| this.whereIsPlayer == MummyState.PLAYER_IS_SOME_LEVEL || this.mummy.isLocked()))

				this.doJump = true;
			else
				this.bounces();
		} else // rebota pues choco contra giratoria
		{
			this.bounces();
		}
	}

	/**
	 *Llamado en caso de llegar al borde de una cornisa
	 */
	protected void doInBorderCliff()
	{
		if (this.whereIsPlayer == MummyState.PLAYER_IS_UP || this.whereIsPlayer == MummyState.PLAYER_IS_SOME_LEVEL)
			if (this.mummy.canJump() && this.mummy.makeDecisionForJump())
				this.doJump = true;
			else
				this.bounces();

	}

	/**
	 *Cambia el estado a new MummyStateDying(this.mummy, mustTeleport);
	 */
	@Override
	protected void die(boolean mustTeleport)
	{
		this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);

	}

}
