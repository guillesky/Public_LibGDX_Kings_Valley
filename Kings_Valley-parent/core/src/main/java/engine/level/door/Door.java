package engine.level.door;

import engine.gameCharacters.player.Player;
import engine.level.LevelObject;
import engine.level.Mechanism;
import util.GameRules;
import util.Constants;

/**
 * Representa una puerta de entrada y salida de la piramide
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
    /**
     * codigo de estado
     */
    protected int state;
    private int idLevel;
    private int levelConnected;

    /**
     * Constructor de clase. Llama a super(timeToEnd);
     * 
     * @param lever     Indica la palanca asociada a la puerta. La puerta se
     *                  construira en base a este parametro, ya que el tileMap la
     *                  puerta se representa solo por su palanca. El pasaje de la
     *                  puerta estara dos tiles por debajo de la palanca y 3 tiles a
     *                  la derecha.
     * @param idLevel   representa el id del nivel en el cual esta la puerta
     * @param timeToEnd tiempo para terminar de abrir o cerrar
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

    @Override
    public void update(float deltaTime)
    {
	this.doorState.update(deltaTime);
    }

    /**
     * Retorna true si la puerta es visible, false en caso contrario
     * 
     * @return true si la puerta es visible, false en caso contrario
     */
    public boolean isVisible()
    {
	return this.state != Door.HIDE;
    }

    /**
     * Retorna un valor numerico indicando el estado de la puerta (no confundir con
     * el patron state)
     * 
     * @return un valor numerico indicando el estado de la puerta (no confundir con
     *         el patron state)
     */
    public int getState()
    {
	return state;
    }

    /**
     * Hace visible la puerta. Delega el metodo en su estado (patron state)
     */
    public void setVisible()

    {
	this.doorState.setVisible();
    }

    /**
     * Verifica si el player toco la paanca. Delega el metodo en su estado (patron
     * state)
     * 
     * @param player Representa el player
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
     * Verifica si el player entro al pasage. Delega el metodo en su estado (patron
     * state)
     * 
     * @param player Representa al player
     * @return true si el player entro al pasage, false en caso contrario
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
