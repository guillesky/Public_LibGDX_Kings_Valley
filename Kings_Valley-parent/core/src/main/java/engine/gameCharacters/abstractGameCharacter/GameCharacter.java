package engine.gameCharacters.abstractGameCharacter;

import java.util.Collection;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import engine.level.GiratoryMechanism;
import engine.level.LevelObject;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;

/**
 * Clase abstracta que representa un caracter del juego (puede ser player o
 * momia) y se encarga de manejar la fisica de colisiones y movimientos
 * generales.<br>
 * Aplica el patron State
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public abstract class GameCharacter extends LevelObject
{

    /**
     * Quieto en el piso
     */
    public static final int ST_IDDLE = 0;
    /**
     * Caminando
     */
    public static final int ST_WALKING = 1;
    /**
     * En escalera
     */
    public static final int ST_ONSTAIRS = 2;

    /**
     * Saltando
     */
    public static final int ST_JUMPING = 5;

    /**
     * Cayendo
     */
    public static final int ST_FALLING = 7;

    /**
     * Muriendo
     */
    public static final int ST_DYING = 8;

    /**
     * Codigo de estado
     */
    protected int state = GameCharacter.ST_IDDLE;

    /**
     * Indica la direccion pretendida
     */
    protected Vector2 motionVector = new Vector2();

    /**
     * Velocidad de caida
     */
    private float speedFall;
    /**
     * Velocidad de caminata
     */
    private float speedWalk;
    /**
     * Velocidad en las escaleras
     */
    private float speedWalkStairs;
    /**
     * Velocidad de impulso vertical del salto
     */
    private float speedJump;
    /**
     * La piramide en la que esta el caracter
     */
    protected Pyramid pyramid;
    /**
     * true si mira a la derecha, false en caso contrario
     */
    protected boolean lookRight = true;
    /**
     * Tiempo que esta en la misma animacion
     */
    protected float animationDelta = 0;
    /**
     * Rectangulo que representa los pies del caracter. Para determinadas colisiones
     */
    private Rectangle feet;
    /**
     * Estado del caracter (patron state)
     */
    protected GameCharacterState gameCharacterState;

    /**
     * Llama a super(type, x, y, 0, Config.getInstance().getCharacterWidth(),
     * Config.getInstance().getCharacterHeight());
     * 
     * @param type            entero que indica el tipo de objeto (util para
     *                        renderizar)
     * @param x               (posicion x en el mapa)
     * @param y               (posicion y en el mapa)
     * @param speedWalk       velocidad de caminata
     * @param speedWalkStairs velocidad de subir o bajar escaleras
     * @param pyramid         objeto de tipo Pyramid a la cual pertenece
     */
    public GameCharacter(int type, float x, float y, float speedWalk, float speedWalkStairs, Pyramid pyramid)
    {
	super(type, x, y, 0, GameRules.getInstance().getCharacterWidth(), GameRules.getInstance().getCharacterHeight());
	float feetWidth;
	float feetHeight;

	this.speedFall = GameRules.getInstance().getCharacterSpeedFall();
	this.speedWalk = speedWalk;
	this.speedWalkStairs = speedWalkStairs;
	this.speedJump = GameRules.getInstance().getCharacterVerticalSpeedJump();

	feetHeight = GameRules.getInstance().getCharacterFeetHeight();
	feetWidth = GameRules.getInstance().getCharacterFeetWidth();
	this.pyramid = pyramid;

	float mitad = this.x + this.width / 2;
	mitad -= feetWidth / 2;
	this.feet = new Rectangle(mitad, y, feetWidth, feetHeight);
	this.gameCharacterState = new GameCharacterStateIddle(this);

    }

    /**
     * Delega en su atributo de estado: this.gameCharacterState.move(v, b,
     * deltaTime);
     * 
     * @param v         vector de direccion pretendida
     * @param b         realzar accion (true o false) puede ser saltar, lanzar daga,
     *                  o intentar picar
     * @param deltaTime tiempo transcurrido desde el ultimo frame en segundos
     */
    protected void move(Vector2 v, boolean b, float deltaTime)
    {
	this.gameCharacterState.move(v, b, deltaTime);

    }

    /**
     * Metodo llamado en caso de solicitar una accion
     */
    protected abstract void doAction();

    /**
     * Delega el metodo a this.gameCharacterState.doJump() Intenta saltar si el
     * techo no lo bloquea. Si puede saltar el estado cambia a
     * GameCharacterStateJumping
     * 
     * @return true si pudo saltar, false en caso contrario
     */
    protected boolean doJump()
    {

	return this.gameCharacterState.doJump();
    }

    /**
     * Retorna un codigo de estado (no confundir con el patron state)<br>
     * ST_IDDLE = 0; <br>
     * ST_WALKING = 1;<br>
     * ST_ONSTAIRS = 2; <br>
     * ST_JUMPING = 5; <br>
     * ST_FALLING = 7;<br>
     * ST_DYING = 8; <br>
     * 
     * @return un codigo de estado (no confundir con el patron state)<br>
     *         ST_IDDLE = 0; <br>
     *         ST_WALKING = 1;<br>
     *         ST_ONSTAIRS = 2; <br>
     *         ST_JUMPING = 5; <br>
     *         ST_FALLING = 7;<br>
     *         ST_DYING = 8; <br>
     */
    public int getState()
    {
	return state;
    }

    /**
     * Indica si hay colision entre sus pies con otro objeto de tipo Rectangle (si
     * lo esta pisando)
     * 
     * @param another Rectangulo con el cual calular colision
     * @return true si hay colision, false en caso contrario
     */
    public boolean isFeetColision(Rectangle another)
    {
	this.feet.x = this.x + (this.width - GameRules.getInstance().getCharacterFeetHeight()) / 2;
	this.feet.y = this.y;
	return LevelObject.rectangleColision(this.feet, another);
    }

    /**
     * Retorna el tiempo en segundos transcurrido desde que se inicio la animacion
     * actual
     * 
     * @return tiempo en segundos transcurrido desde que se inicio la animacion
     *         actual
     */
    public float getAnimationDelta()
    {
	return animationDelta;
    }

    /**
     * Retorna true si el caracter mira a la derecha, false si mira a la izquierda
     * 
     * @return true si el caracter mira a la derecha, false si mira a la izquierda
     */
    public boolean isLookRight()
    {
	return lookRight;
    }

    /**
     * Devuelve el objeto de tipo de LevelObjet con el cual el caracter colisiona
     * desde una lista pasada por parametro. Si no colisiona con ninguno, retorna
     * null
     * 
     * @param levelObjects ArrayList con los objetos a verificar
     * @return El objeto de la lista con el cual el caracter colisiona, null si no
     *         hay colision.
     */
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    public LevelObject checkRectangleColision(Collection levelObjects)
    {

	Iterator<LevelObject> it = levelObjects.iterator();
	LevelObject respuesta = null;
	LevelObject item = null;
	if (it.hasNext())
	    do
	    {
		item = it.next();
	    } while (it.hasNext() && !this.isColision(item));

	if (this.isColision(item))
	{
	    respuesta = item;
	}

	return respuesta;
    }

    /**
     * Verifica que el caracter colisione con una puerta giratoria. Si puede pasar
     * lo hace y en caso contrario es bloqueado.
     * 
     * @return devuelve true si el caracter colisiona con una puerta giratoria (ya
     *         sea que pueda pasar o no). Retorna false en caso contrario
     */
    protected boolean checkGiratory()
    {
	boolean r = false;
	LevelObject giratory = this.checkRectangleColision(this.pyramid.getGiratories());
	if (giratory != null)
	{
	    GiratoryMechanism gm = this.pyramid.getGiratoryMechanism(giratory);

	    if (this.canPassGiratoryMechanism(gm))
	    {
		this.passGiratoryMechanism(gm);

	    } else
	    {
		this.blockGiratory(gm);
		r = true;
	    }
	}
	return r;
    }

    /**
     * Retorna true si esta bloqueado por izquierda y por derecha (eso significa que
     * no puede caminar hacia ninguno de ambos lados). Retorna false en caso
     * contrario
     * 
     * @return true si esta bloqueado por izquierda y por derecha (eso significa que
     *         no puede caminar hacia ninguno de ambos lados). Retorna false en caso
     *         contrario
     */
    public boolean isLocked()
    {

	return this.isLockedRight() && this.isLockedLeft();
    }

    /**
     * Retorna true si esta bloqueado por izquierda (eso significa que no puede
     * caminar hacia la izquierda). Retorna false en caso contrario
     * 
     * @return true si esta bloqueado por izquierda (eso significa que no puede
     *         caminar hacia la izquierda). Retorna false en caso contrario
     */
    protected boolean isLockedLeft()
    {
	return this.pyramid.getCell(this.x - GameRules.getInstance().getLevelTileWidthUnits(),
		this.y + GameRules.getInstance().getLevelTileHeightUnits()) != null
		|| this.pyramid.getCell(this.x - GameRules.getInstance().getLevelTileWidthUnits(), this.y) != null;

    }

    /**
     * Retorna true si esta bloqueado por derecha (eso significa que no puede
     * caminar hacia la derecha). Retorna false en caso contrario
     * 
     * @return true si esta bloqueado por derecha (eso significa que no puede
     *         caminar hacia la derecha). Retorna false en caso contrario
     */

    protected boolean isLockedRight()
    {
	return this.pyramid.getCell(this.x + GameRules.getInstance().getLevelTileWidthUnits(),
		this.y + GameRules.getInstance().getLevelTileHeightUnits()) != null
		|| this.pyramid.getCell(this.x + GameRules.getInstance().getLevelTileWidthUnits(), this.y) != null;

    }

    /**
     * Retorna true si esta libre arriba y podria saltar (esto es que no tenga un
     * tile justo encima de la cabeza del caracter). Retorna false en caso
     * contrario.
     * 
     * @return true si esta libre arriba y podria saltar (esto es que no tenga un
     *         tile justo encima de la cabeza del caracter). Retorna false en caso
     *         contrario.
     */
    protected boolean isFreeUp()
    {
	return this.pyramid.getCell(this.x, this.y, 0, 2) == null
		&& this.pyramid.getCell(this.x + this.width, this.y, 0, 2) == null;

    }

    /**
     * Verifica si el caracter puede pasar a traves de una puerta giratoria pasada
     * por parametro
     * 
     * @param giratoryMechanism La puerta giratoria que debe ser evaluada.
     * @return Retorna true si el caracter puede pasar a traves de la puerta
     *         giratoria pasada por parametro. Retorna false en caso contrario.
     */
    protected abstract boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism);

    /**
     * Raliza las acciones necesarias al pasar por la puerta giratoria pasada como
     * parametro.
     * 
     * @param giratoryMechanism Puerta giratoria por la que esta pasando el
     *                          caracter.
     */
    protected abstract void passGiratoryMechanism(GiratoryMechanism giratoryMechanism);

    /**
     * Bloquea el paso del caracter por la puerta giratoria pasada por paramentro.
     * 
     * @param giratoryMechanism La puerta giratoria que bloque al caracter
     */
    protected void blockGiratory(GiratoryMechanism giratoryMechanism)
    {
	LevelObject g = giratoryMechanism.getLevelObject();
	float lado = g.x - this.x;

	this.motionVector.x = 0;
	if (lado < 0)
	{
	    this.x = g.x + g.width;
	} else

	{
	    this.x = g.x - this.width;
	}

    }

    /**
     * setea un valor entero que representa el estado del caracter (no confundir con
     * un objeto de tipo GameCharacterState). Estan contemplados los tipos:<br>
     * ST_IDDLE = 0; <br>
     * ST_WALKING = 11; <br>
     * ST_ONSTAIRS = 2; <br>
     * ST_JUMPING = 5; <br>
     * ST_FALLING = 7; <br>
     * ST_DYING = 8; <br>
     * 
     * @param state Valor entero que representa el estado del caracter (no confundir
     *              con un objeto de tipo GameCharacterState). Estan contemplados
     *              los tipos:<br>
     *              ST_IDDLE = 0; <br>
     *              ST_WALKING = 11; <br>
     *              ST_ONSTAIRS = 2; <br>
     *              ST_JUMPING = 5; <br>
     *              ST_FALLING = 7; <br>
     *              ST_DYING = 8; <br>
     */
    protected void setState(int state)
    {
	this.state = state;
    }

    /**
     * this.animationDelta = 0;
     */
    protected void resetAnimationDelta()
    {
	this.animationDelta = 0;
    }

    /**
     * this.animationDelta += delta;
     * 
     * @param delta El valor en segundos que debe incrementarse el atributo
     *              this.animationDelta
     */
    protected void incAnimationDelta(float delta)
    {
	this.animationDelta += delta;
    }

    /**
     * Retorna la piramide a la cual pertence el caracter
     * 
     * @return La piramide a la cual pertence el caracter
     */
    public Pyramid getPyramid()
    {
	return pyramid;
    }

    /**
     * Retorna true si el caracter esta ascendiendo o descendiendo de una escalera.
     * Retorna false en caso contrario.
     * 
     * @return true si el caracter esta ascendiendo o descendiendo de una escalera.
     *         Retorna false en caso contrario.
     */
    public boolean isInStair()
    {
	return this.gameCharacterState.getStair() != null;
    }

    /**
     * Retorna la escalera en la cual el caracter esta ascendiendo o descendiendo.
     * Si no esta en una escalera retorna null
     * 
     * @return La escalera en la cual el caracter esta ascendiendo o descendiendo.
     *         Si no esta en una escalera retorna null
     */
    public Stair getStair()
    {
	return this.gameCharacterState.getStair();
    }

    /**
     * Retorna la escalera (positiva o negativa segun parametro) a traves de la cual
     * es caracter pretende subir o bajar (segun parametro)
     * 
     * @param positiveStairs true si se pretende acceder a una escalera positiva,
     *                       false en caso contrario.
     * @param isUpping       true si se intenta subir, false en caso contrario.
     * @return La escalera la escalera (positiva o negativa segun parametro) a
     *         traves de la cual es caracter pretende subir o bajar (segun
     *         parametro). Si no hay una escalera accesible segun las condicione,
     *         retorna null
     */
    protected Stair checkStairsFeetColision(boolean positiveStairs, boolean isUpping)
    {

	Iterator<Stair> it;
	if (positiveStairs)
	    it = this.getPyramid().getPositiveStairs().iterator();
	else
	    it = this.getPyramid().getNegativeStairs().iterator();
	Stair respuesta = null;
	Stair stair = null;
	LevelObject item = null;
	if (it.hasNext())
	    do
	    {
		stair = it.next();
		if (isUpping)
		    item = stair.getDownStair();
		else
		    item = stair.getUpStair();
	    } while (it.hasNext() && !this.isFeetColision(item));

	if (this.isFeetColision(item))
	{
	    respuesta = stair;
	}

	return respuesta;
    }

    /**
     * Delega el metodo a this.gameCharacterState.enterStair(stair); Llamado cuando
     * el caracter entra a una escalera pasada por parametro
     * 
     * @param stair Escalera a la cual entro el caracter
     */
    protected void enterStair(Stair stair)
    {
	this.gameCharacterState.enterStair(stair);
    }

    /**
     * Retorna la velocidad de caminata
     * 
     * @return La velocidad de caminata
     */
    public float getSpeedWalk()
    {
	return speedWalk;
    }

    /**
     * Retorna la velocidad de caminata en escaleras
     * 
     * @return La velocidad de caminata en escaleras
     */
    public float getSpeedWalkStairs()
    {
	return speedWalkStairs;
    }

    /**
     * Retorna la velocidad maxima de caida
     * 
     * @return La velocidad maxima de caida
     */
    public float getSpeedFall()
    {
	return speedFall;
    }

    /**
     * Retorna la velocidad de de empuje vertical en salto
     * 
     * @return La velocidad de de empuje vertical en salto
     */
    public float getSpeedJump()
    {
	return speedJump;
    }

}
