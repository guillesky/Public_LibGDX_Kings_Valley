package engine.level.door;

import engine.gameCharacters.player.Player;
import engine.level.LevelObject;
import engine.level.Mechanism;
import util.GameRules;
import util.Constants;

/**
 * Representa una puerta de entrada o salida del nivel.
 *
 * <p>
 * La puerta forma parte del sistema de mecanismos del juego y extiende
 * Mechanism, incorporando comportamiento temporal (apertura, cierre y
 * transiciones de estado).
 * </p>
 *
 * <p>
 * Su comportamiento se modela mediante el patron State a traves de DoorState,
 * lo que permite encapsular la logica de interaccion con el jugador, la palanca
 * (lever) y el pasaje (passage).
 * </p>
 *
 * <p>
 * La puerta no se actualiza directamente desde el mundo, sino que delega su
 * comportamiento activo en su estado interno.
 * </p>
 *
 * @author Guillermo Lazzurri
 */
public class Door extends Mechanism
{
	/**
	 * Codigo de puerta oculta
	 */
	public static final int HIDE = 0;
	/**
	 * Codigo de puerta cerrada
	 */
	public static final int CLOSED = 1;
	/**
	 * Codigo de puerta abierta
	 */
	public static final int OPEN = 2;
	/**
	 * Codigo de puerta cerrandose
	 */
	public static final int CLOSING = 3;
	/**
	 * Codigo de puerta abriendose
	 */
	public static final int OPENING = 4;

	/**
	 * Codigo de puerta que conecta al nivel previo
	 */
	public static final int TO_PREVIUS = 0;
	/**
	 * Codigo de puerta que conecta al nivel siguiente
	 */
	public static final int TO_NEXT = -1;
	/**
	 * Codigo de puerta unica en el nivel
	 */
	public static final int UNIQUE = -2;

	private LevelObject lever;
	private LevelObject passage;
	/**
	 * estado de la puerta (patron state)
	 */
	protected DoorState doorState;
	private int idLevel;
	private int levelConnected;

	/**
	 * 
	 * Construye una puerta a partir de su palanca asociada.
	 *
	 * <p>
	 * La geometria del pasaje se deriva automaticamente desde la posicion del
	 * lever, siguiendo reglas fijas del layout del nivel. (dos tiles por debajo de
	 * la palanca y 3 tiles a la derecha.)
	 * </p>
	 *
	 * <p>
	 * La puerta inicia en estado oculto y no esta activa hasta ser interactuada por
	 * el jugador.
	 * </p>
	 *
	 * @param lever     objeto del nivel que actua como palanca de activacion
	 * @param idLevel   identificador del nivel al que pertenece la puerta
	 * @param timeToEnd tiempo necesario para completar transiciones de
	 *                  apertura/cierre
	 */

	public Door(LevelObject lever, int idLevel, float timeToEnd)
	{
		super(timeToEnd);
		this.lever = lever;
		float width = GameRules.getInstance().getLevelTileWidthUnits();
		float eight = GameRules.getInstance().getLevelTileHeightUnits();
		float x = this.lever.x + width * 3;
		float y = this.lever.y - eight * 2;
		this.doorState = new DoorStateHide(this);
		this.passage = new LevelObject(Constants.IT_DOOR_PASSAGE, x, y, this.lever.getP0(), width * 0.2f, eight * 2);
		this.active = false;
		this.levelConnected = lever.getP0() * -1;
		this.idLevel = idLevel;

	}

	/**
	 * Retorna el objeto que representa la palanca de la puerta
	 * 
	 * @return Objeto que representa la palanca de la puerta
	 */
	public LevelObject getLever()
	{
		return lever;
	}

	/**
	 * Retorna el objeto que representa el pasaje de la puerta
	 * 
	 * @return Objeto que representa el pasaje de la puerta
	 */
	public LevelObject getPassage()
	{
		return passage;
	}

	/**
	 * Actualiza el estado interno de la puerta.
	 *
	 * <p>
	 * La logica de comportamiento se delega completamente al estado actual (
	 * DoorState).
	 * </p>
	 *
	 * @param deltaTime tiempo transcurrido desde la ultima actualizacion
	 */
	@Override
	public void update(float deltaTime)
	{
		this.doorState.update(deltaTime);
	}

	/**
	 * Indica si la puerta es visible en el mundo.
	 *
	 * <p>
	 * Una puerta oculta no se renderiza ni interactua con el jugador.
	 * </p>
	 *
	 * @return true si la puerta esta visible, false en caso contrario
	 */
	public boolean isVisible()
	{
		return this.getRenderMode() != Door.HIDE;
	}

	/**
	 * Retorna un codigo numerico que indica como sera la renderizacion de acuerdo
	 * al estado de la puerta.<br>
	 * Lo delega a su estado mediante:<br>
	 * return this.doorState.getRenderMode();<br>
	 * Puede tomar los valores:<br>
	 * HIDE = 0;<br>
	 * CLOSED = 1<br>
	 * OPEN = 2<br>
	 * CLOSING = 3<br>
	 * OPENING = 4<br>
	 * 
	 * @return un codigo numerico que indica como sera la renderizacion de acuerdo
	 *         al estado de la puerta.
	 */
	public int getRenderMode()
	{
		return this.doorState.getRenderMode();
	}

	/**
	 * Hace visible la puerta. Delega el metodo en su estado (patron state)
	 */
	public void setVisible()

	{
		this.doorState.setVisible();
	}

	/**
	 * Evalua si el jugador interactuo con la palanca de la puerta.
	 *
	 * <p>Este metodo no aplica logica directa, sino que delega la resolucion
	 * al estado actual de la puerta.</p>
	 *
	 * @param player jugador que puede activar la palanca
	 */
	public void checkPushLever(Player player)
	{
		this.doorState.checkPushLever(player);
	}

	@Override
	protected void incTime(float delta)
	{
		super.incTime(delta);
	}

	@Override
	protected void resetTime()
	{
		super.resetTime();
	}

	/**
	 * Verifica si el jugador ingreso al pasaje de la puerta.
	 *
	 * <p>La resolucion del evento depende del estado interno de la puerta.</p>
	 *
	 * @param player jugador a evaluar
	 * @return true si el jugador ingreso al pasaje, false en caso contrario
	 */
	public boolean checkEnterPassage(Player player)
	{
		return this.doorState.checkEnterPassage(player);
	}

	/**
	 * Retorna un valor numerico indicando el nivel al cual se conecta la puerta.
	 * Puede tomar los valores: <br>
	 * Door.UNIQUE = -2; En caso que sea la unica puerta del nivel. Conectara con el
	 * nivel siguiente. No hay nivel previo (usado para el primer nivbel del juego)
	 * <br>
	 * Door.TO_NEXT=-1; En caso que conecte con el nivel siguiente.<br>
	 * Door.TO_PREVIUS=0; En caso que conecte con el nivel anterior.<br>
	 * Un valor entero positivo; En caso de niveles con puertas y atajos. El nivel
	 * receptor debera tener una puerta al presente nivel para guardar consistencia
	 * 
	 * @return un valor numerico indicando el nivel al cual se conecta la puerta.
	 *         Puede tomar los valores: <br>
	 *         Door.UNIQUE = -2; En caso que sea la unica puerta del nivel.
	 *         Conectara con el nivel siguiente. No hay nivel previo (usado para el
	 *         primer nivbel del juego) <br>
	 *         Door.TO_NEXT=-1; En caso que conecte con el nivel siguiente.<br>
	 *         Door.TO_PREVIUS=0; En caso que conecte con el nivel anterior.<br>
	 *         Un valor entero positivo; En caso de niveles con puertas y atajos. El
	 *         nivel receptor debera tener una puerta al presente nivel para guardar
	 *         consistencia
	 * 
	 * 
	 */
	public int getLevelConnected()
	{
		return levelConnected;
	}

	/**
	 * Retorna el id del nivel actual de la puerta
	 * 
	 * @return el id del nivel actual de la puerta
	 */
	public int getIdLevel()
	{
		return idLevel;
	}

}
