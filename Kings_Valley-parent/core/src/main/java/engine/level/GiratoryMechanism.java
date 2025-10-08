package engine.level;

import util.GameRules;

/**
 * Representa una puerta giratoria
 * 
 * @author Guillermo Lazzurri
 */
public class GiratoryMechanism extends Mechanism
{
	LevelObject levelObject;
	private boolean right;
	private int heightInTiles;

	/**
	 * Constructor de clase. LLama a super(timeToEnd);. Construye la puerta
	 * giratoria a partir de LevelObject generado en la clase LevelReader
	 * 
	 * @param levelObject Representa el levelObject asociado a la puerta giratoria
	 * @param timeToEnd   Tiempo para finalizar el giro
	 */
	public GiratoryMechanism(LevelObject levelObject, float timeToEnd)
	{
		super(timeToEnd);
		this.levelObject = levelObject;
		this.heightInTiles = (int) (levelObject.getHeight() / GameRules.getInstance().getLevelTileHeightUnits());
		this.right = (levelObject.getP0() == 0);
		this.active = false;

	}

	@Override
	public void update(float deltaTime)
	{
		this.incTime(deltaTime);
		if (this.time >= this.timeToEnd)
		{
			this.end();
		}
	}

	/**
	 * Llamado internamente para indicar que la puerta termino de girar
	 */
	private void end()
	{
		this.resetTime();
		this.right = !this.right;
		this.active = false;
	}

	/**
	 * Indica si la puerta esta activa, es decir, si se puede pasar por ella (para
	 * evitar una doble activacion)
	 */
	public void activate()
	{
		this.active = true;
	}

	/**
	 * @return el levelObect asociado a la puerta giratoria
	 */
	public LevelObject getLevelObject()
	{
		return levelObject;
	}

	/**
	 * @return true si la puerta es accesible por derecha, false si es accesible por
	 *         izquierda
	 */
	public boolean isRight()
	{
		return right;
	}

	/**
	 * @return cantidad de tiles de alto de la puerta giratoria
	 */
	public int getHeightInTiles()
	{
		return heightInTiles;
	}

}
