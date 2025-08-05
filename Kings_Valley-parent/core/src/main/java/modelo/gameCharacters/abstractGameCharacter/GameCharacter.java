package modelo.gameCharacters.abstractGameCharacter;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase abstracta que representa un caracter del juego (puede ser
 *         player o momia) y se encarga de manejar la fisica de colisiones y
 *         movimientos generales.<br>
 *         Aplica el patron State
 */
@SuppressWarnings("serial")
public abstract class GameCharacter extends LevelObject
{

	public static final int ST_IDDLE = 0; // Inicializando
	public static final int ST_WALKING = 11; // Andando
	public static final int ST_ONSTAIRS = 2; // En una escalera pendiente positiva

	public static final int ST_JUMPING = 5; // Saltando

	public static final int ST_FALLING = 7; // Cayendo

	public static final int ST_DYING = 8; // Muriendo

	protected int state = GameCharacter.ST_IDDLE;

	protected Vector2 motionVector = new Vector2();
	protected float speedFall;
	protected float speedWalk;
	protected float speedWalkStairs;
	protected float speedJump;
	protected float speedY = 0;
	protected Pyramid pyramid;
	protected boolean lookRight = true;
	protected float animationDelta = 0;
	private Rectangle feet;
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
		super(type, x, y, 0, Config.getInstance().getCharacterWidth(), Config.getInstance().getCharacterHeight());
		float feetWidth;
		float feetHeight;

		this.speedFall = Config.getInstance().getCharacterSpeedFall();
		this.speedWalk = speedWalk;
		this.speedWalkStairs = speedWalkStairs;
		this.speedJump = Config.getInstance().getCharacterSpeedJump();

		feetHeight = Config.getInstance().getCharacterFeetHeight();
		feetWidth = Config.getInstance().getCharacterFeetWidth();
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
	 * Intenta saltar si el techo no lo bloquea. Si puede saltar el estado cambia a
	 * GameCharacterStateJumping
	 * 
	 * @return true si pudo saltar, false en caso contrario
	 */
	protected boolean doJump()
	{
		boolean r = false;
		if (this.isFreeUp())
		{
			this.gameCharacterState = new GameCharacterStateJumping(this);
			r = true;
		}
		return r;
	}

	/**
	 * @return un codigo de estado (no confundir con el patron state)<br>
	 *         ST_IDDLE = 0; <br>
	 *         ST_WALKING = 11;<br>
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
		this.feet.x = this.x + (this.width - Config.getInstance().getCharacterFeetHeight()) / 2;
		this.feet.y = this.y;
		return LevelObject.rectangleColision(this.feet, another);
	}

	/**
	 * @return tiempo en segundos transcurrido desde que se inicio la animacion
	 *         actual
	 */
	public float getAnimationDelta()
	{
		return animationDelta;
	}

	/**
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
	public LevelObject checkRectangleColision(ArrayList levelObjects)
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
	 * @return
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

	public boolean isLocked()
	{

		return this.isLockedRight() && this.isLockedLeft();
	}

	protected boolean isLockedLeft()
	{
		return this.pyramid.getCell(this.x - Config.getInstance().getLevelTileWidthUnits(),
				this.y + Config.getInstance().getLevelTileHeightUnits()) != null
				|| this.pyramid.getCell(this.x - Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

	}

	protected boolean isLockedRight()
	{
		return this.pyramid.getCell(this.x + Config.getInstance().getLevelTileWidthUnits(),
				this.y + Config.getInstance().getLevelTileHeightUnits()) != null
				|| this.pyramid.getCell(this.x + Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

	}

	protected boolean isFreeUp()
	{
		return this.pyramid.getCell(this.x, this.y, 0, 2) == null
				&& this.pyramid.getCell(this.x + this.width, this.y, 0, 2) == null;

	}

	protected abstract boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism);

	protected abstract void passGiratoryMechanism(GiratoryMechanism giratoryMechanism);

	protected void blockGiratory(GiratoryMechanism gm)
	{
		LevelObject g = gm.getLevelObject();
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

	protected void setState(int state)
	{
		this.state = state;
	}

	protected void resetAnimationDelta()
	{
		this.animationDelta = 0;
	}

	protected void incAnimationDelta(float delta)
	{
		this.animationDelta += delta;
	}

	public Pyramid getPyramid()
	{
		return pyramid;
	}

	public boolean isInStair()
	{
		return this.gameCharacterState.getStair() != null;
	}

	public Stair getStair()
	{
		return this.gameCharacterState.getStair();
	}

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

	protected void enterStair(Stair stair)
	{
		this.gameCharacterState = new GameCharacterStateOnStair(this, stair);
	}

}
