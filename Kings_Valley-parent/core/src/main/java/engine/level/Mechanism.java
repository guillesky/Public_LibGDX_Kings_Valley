package engine.level;

/**
 * Representa un mecanismo del nivel basado en tiempo.
 *
 * <p>
 * Un Mechanism es un componente del mundo del juego cuya activacion y
 * resolucion dependen del paso del tiempo. Estos mecanismos modelan
 * comportamientos temporales del escenario, como activadores, puertas, eventos
 * o efectos con duracion limitada.
 * </p>
 *
 * <p>
 * Cada mecanismo mantiene un contador interno de tiempo desde su activacion y
 * define un ciclo de vida que finaliza cuando se alcanza el tiempo configurado.
 * </p>
 *
 * <p>
 * Esta clase sirve como base para mecanismos concretos que implementan
 * comportamiento especifico en el entorno del juego.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public abstract class Mechanism
{
	/**
	 * Tiempo transcurrido desde el inicio de activacion
	 */
	protected float time = 0;
	/**
	 * Tiempo para finalizar el ciclo del mecanismo
	 */
	protected float timeToEnd;
	/**
	 * true si esta activo el mecanismo, false en caso contrario
	 */
	protected boolean active = true;

	/**
	 * Llamado para actualizar el estado
	 * 
	 * @param deltaTime indica el tiempo tranucurrido dede la ultima llamada
	 */
	public abstract void update(float deltaTime);

	/**
	 * Crea un nuevo mecanismo con duracion definida.
	 *
	 * @param timeToEnd tiempo necesario para completar el ciclo del mecanismo
	 */
	public Mechanism(float timeToEnd)
	{

		this.timeToEnd = timeToEnd;
	}

	/**
	 * Indica si el mecanismo se encuentra activo.
	 *
	 * @return true si el mecanismo sigue en ejecucion, false en caso contrario
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * Retorna el tiempo transcurrido desde la activacion del mecanismo.
	 *
	 * @return tiempo acumulado en segundos
	 */
	public float getTime()
	{
		return time;
	}

	/**
	 * Incrementa el tiempo interno del mecanismo.
	 *
	 * @param delta tiempo a agregar al contador interno
	 */
	protected void incTime(float delta)
	{
		this.time += delta;
	}

	/**
	 * Reinicia el contador de tiempo del mecanismo.
	 */
	protected void resetTime()
	{
		this.time = 0;
	}

	/**
	 * Retorna el tiempo total requerido para completar el ciclo del mecanismo.
	 *
	 * @return duracion total del mecanismo
	 */
	public float getTimeToEnd()
	{
		return timeToEnd;
	}

}
