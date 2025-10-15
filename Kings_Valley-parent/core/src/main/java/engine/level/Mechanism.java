package engine.level;

/**
 * Representa un mecanismo en el nivel, es decir, un objeto que realiza alguna
 * accion que requiere tiempo para tarminar
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
	 * Constructor de clase
	 * 
	 * @param timeToEnd indica el tiempo necesario para terminar
	 */
	public Mechanism(float timeToEnd)
	{

		this.timeToEnd = timeToEnd;
	}

	/**
	 * Retorna true si esta activo, false en caso contrario
	 * @return true si esta activo, false en caso contrario
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * Retorna el tiempo que lleva activo
	 * @return el tiempo que lleva activo
	 */
	public float getTime()
	{
		return time;
	}

	/**
	 * Incrementa el tiempo que esta activo el mecanismo
	 * 
	 * @param delta tiempo de incremento
	 */
	protected void incTime(float delta)
	{
		this.time += delta;
	}

	/**
	 * Pone en cero el tiempo
	 */
	protected void resetTime()
	{
		this.time = 0;
	}

	/**
	 * Retorna el tiempo que tarda en terminar el ciclo
	 * @return El tiempo que tarda en terminar el ciclo
	 */
	public float getTimeToEnd()
	{
		return timeToEnd;
	}

}
