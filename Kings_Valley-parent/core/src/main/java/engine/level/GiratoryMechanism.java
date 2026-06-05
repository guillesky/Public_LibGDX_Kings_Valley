package engine.level;

import util.GameRules;

/**
 * Representa un mecanismo de puerta giratoria dentro del nivel.
 *
 * <p>
 * Una puerta giratoria es un elemento del escenario que controla el flujo de
 * paso entre secciones del nivel, alternando su estado de accesibilidad despues
 * de un intervalo de tiempo definido.
 * </p>
 *
 * <p>
 * Este mecanismo afecta directamente la navegacion del jugador y de otros
 * personajes, determinando desde que lado puede ser atravesado.
 * </p>
 *
 * <p>
 * Su comportamiento es temporal: una vez activada, la puerta permanece en
 * ejecucion durante un tiempo determinado y luego invierte su estado de
 * orientacion.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public class GiratoryMechanism extends Mechanism
{
	LevelObject levelObject;
	private boolean right;
	private int heightInTiles;

	/**
	 * Crea una nueva puerta giratoria asociada a un objeto del nivel.
	 *
	 * @param levelObject objeto del nivel que representa la posicion y forma de la
	 *                    puerta
	 * @param timeToEnd   tiempo que tarda en completar un ciclo de giro
	 */
	public GiratoryMechanism(LevelObject levelObject, float timeToEnd)
	{
		super(timeToEnd);
		this.levelObject = levelObject;
		this.heightInTiles = (int) (levelObject.getHeight() / GameRules.getInstance().getLevelTileHeightUnits());
		this.right = (levelObject.getP0() == 0);
		this.active = false;

	}

	/**
	 * Actualiza el estado temporal de la puerta giratoria.
	 *
	 * <p>
	 * Incrementa el contador interno y verifica si se completo el ciclo de
	 * activacion. En ese caso, la puerta invierte su orientacion y vuelve a estado
	 * inactivo.
	 * </p>
	 */
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
	 * Finaliza el ciclo de activacion de la puerta giratoria.
	 *
	 * <p>
	 * Invierte la direccion de acceso y reinicia el contador interno.
	 * </p>
	 */
	private void end()
	{
		this.resetTime();
		this.right = !this.right;
		this.active = false;
	}

	/**
	 * Activa la puerta giratoria para iniciar su ciclo de funcionamiento.
	 *
	 * <p>
	 * Este metodo evita activaciones multiples simultaneas.
	 * </p>
	 */
	public void activate()
	{
		this.active = true;
	}

	/**
	 * Retorna el levelObect asociado a la puerta giratoria
	 * 
	 * @return el levelObect asociado a la puerta giratoria
	 */
	public LevelObject getLevelObject()
	{
		return levelObject;
	}

	/**
	 * Indica desde que lado puede ser atravesada la puerta giratoria en su estado
	 * actual.
	 *
	 * @return true si se puede atravesar desde la derecha, false desde la izquierda
	 */
	public boolean isRight()
	{
		return right;
	}

	/**
	 * Retorna la altura de la puerta giratoria en cantidad de tiles del nivel.
	 *
	 * @return altura en tiles
	 */
	public int getHeightInTiles()
	{
		return heightInTiles;
	}

}
