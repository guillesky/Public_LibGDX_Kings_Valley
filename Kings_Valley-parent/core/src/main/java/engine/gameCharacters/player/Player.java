package engine.gameCharacters.player;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.GiratoryMechanism;
import engine.level.LevelObject;
import engine.level.Pyramid;
import engine.level.dagger.Dagger;
import engine.level.dagger.DaggerState;
import util.GameRules;
import util.Constants;

/**
 * Representa al Player
 * 
 * @author Guillermo Lazzurri
 */
@SuppressWarnings("serial")
public class Player extends GameCharacter
{
	/**
	 * Codigo numerico para el estado de estar lanzando la espada
	 */
	public static final int ST_THROWING_DAGGER = 20;

	/**
	 * Codigo numerico para el estado de estar picando
	 */
	
	public static final int ST_PICKING = 21;

	/**
	 * El item que el player porta (pico, espada, o null)
	 */
	protected LevelObject item = null;

	/**
	 * Estado del Player (Patron State)
	 */
	private PlayerState playerState = null;

	/**
	 * Constructor de clase. Llama a new MummyStateDying(this.mummy, mustTeleport);
	 * <br>
	 * Dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.PLAYER_RESPAWN, this);
	 * 
	 * @param x       coordenada x original del Player
	 * @param y       coordenada y original del Player
	 * @param pyramid piramide a la cual pertenece el player
	 */
	public Player(float x, float y, Pyramid pyramid)
	{
		super(Constants.PLAYER, x, y, GameRules.getInstance().getPlayerSpeedWalk(),
				GameRules.getInstance().getPlayerSpeedWalkStairs(), pyramid);
		this.playerState = new PlayerStateMoving(this, GameCharacter.ST_IDDLE);
		Game.getInstance().eventFired(KVEventListener.PLAYER_RESPAWN, this);
	}

	/**
	 * Llamado para actualizar al player. Delega el metodo en
	 * this.playerState.update(v, b, deltaTime);
	 * 
	 * @param v         Indica la direccion pretendida.
	 * @param b         true si intenta realizar una accion, false en caso contrario
	 * @param deltaTime Tiempo transcurrido desde la ultima actualizacion
	 */
	public void update(Vector2 v, boolean b, float deltaTime)
	{
		this.incAnimationDelta(deltaTime);
		this.playerState.update(v, b, deltaTime);
	}

	/**
	 * Retorna el item que porta el player (espada o pico). Si no porta ningun item
	 * retorna null
	 * 
	 * @return el item que porta el player (espada o pico). Si no porta ningun item
	 *         retorna null
	 */
	public LevelObject getItem()
	{
		return item;
	}

	/**
	 * Llamado cuando el player intenta realizas una accion. Dependiendo el item que
	 * porta, puede saltar, picar o lanzar la espada.<br>
	 * Si intenta saltar y puede hacerlo, se dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.PLAYER_JUMP, this);<br>
	 * Si se intenta picar, se llama al metodo this.doPicker();<br>
	 * Si se intenta arrojar la espada, se llama al metodo this.throwDagger();<br>
	 */
	@Override
	protected void doAction()
	{
		if (this.item == null)
		{
			if (this.doJump())
				Game.getInstance().eventFired(KVEventListener.PLAYER_JUMP, this);

		} else if (this.item.getType() == Constants.IT_PICKER)
		{
			this.playerState.doPicker();
		} else if (this.item.getType() == Constants.IT_DAGGER)
		{
			this.playerState.throwDagger();
		}

	}

	/**
	 * El player podra pasar por una puerta giratoria dependiendo la posicion del
	 * mismo y la de orientaci de la puerta giratoria.
	 */
	@Override
	protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

		return (this.state == GameCharacter.ST_WALKING && this.isLookRight() && giratoryMechanism.isRight())
				|| (this.state == GameCharacter.ST_WALKING && !this.isLookRight() && !giratoryMechanism.isRight());
	}

	/**
	 * Delega el metodo a this.playerState.passGiratoryMechanism(giratoryMechanism);
	 * (patron state)
	 */

	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{
		this.playerState.passGiratoryMechanism(giratoryMechanism);
	}

	@Override
	protected void setState(int state)
	{
		super.setState(state);
	}

	/**
	 * Cambia el estado del player (patron state)
	 * 
	 * @param playerState nuevo estado del player (patron state)
	 */
	protected void setPlayerState(PlayerState playerState)
	{
		this.playerState = playerState;
	}

	@Override
	protected void resetAnimationDelta()
	{
		super.resetAnimationDelta();
	}

	/**
	 * Incrementa la coordenada x del caracter (admite valores positivos y
	 * negativos)
	 * 
	 * @param cant cantidad a incrementar
	 */
	protected void incX(float cant)
	{
		this.x += cant;
	}

	/**
	 * llama a super.move(v, b, deltaTime); Luego verifica si se recolectan items,
	 * se pasa por giratorias o se activan trampas
	 */
	@Override
	protected void move(Vector2 v, boolean b, float deltaTime)
	{
		super.move(v, b, deltaTime);
		this.pickupCollectables();

		LevelObject activator = this.checkRectangleColision(this.pyramid.getActivators());
		if (activator != null)
			this.pyramid.activateWall(activator);
		if (!this.isInStair())
			this.checkGiratory();
	}

	/**
	 * Llamado para verificar si se recolectan objetos. Podria disparar los
	 * eventos:<br>
	 * Game.getInstance().eventFired(KVEventListener.PICKUP_JEWEL, jewel);<br>
	 * Game.getInstance().eventFired(KVEventListener.PICKUP_PICKER, picker);<br>
	 * Game.getInstance().eventFired(KVEventListener.PICKUP_DAGGER, dagger);<br>
	 * 
	 * 
	 */
	private void pickupCollectables()
	{
		if (!this.isInStair())
		{
			LevelObject jewel = this.checkItemFeetColision(this.pyramid.getJewels());
			if (jewel != null)
			{
				this.pyramid.removeJewel(jewel);
				Game.getInstance().eventFired(KVEventListener.PICKUP_JEWEL, jewel);

			}
			if (this.item == null)
			{

				Dagger dagger = (Dagger) this.checkRectangleColision(this.pyramid.getStuckedDaggers());
				if (dagger != null && dagger.getState() == DaggerState.ST_STUCKED)
				{
					this.item = dagger;
					dagger.hasPickuped();
					Game.getInstance().eventFired(KVEventListener.PICKUP_DAGGER, dagger);

				}
			}
			if (this.item == null)
			{

				LevelObject picker = this.checkRectangleColision(this.pyramid.getPickers());
				if (picker != null)
				{
					this.item = picker;
					this.pyramid.removePicker(picker);
					Game.getInstance().eventFired(KVEventListener.PICKUP_PICKER, picker);

				}

			}
		}
	}

	/**
	 * Llamado internamente por this.pickupCollectables(), verifica colision entre
	 * los objetos pasados por parametro y los pies del player
	 * 
	 * @param levelObjects conjunto de objetos que deben verificarse
	 * @return primer objeto con el que se detecta colision. Si no colisiona con
	 *         ninguno, retorna null
	 */
	private LevelObject checkItemFeetColision(ArrayList<LevelObject> levelObjects)
	{

		Iterator<LevelObject> it = levelObjects.iterator();
		LevelObject respuesta = null;
		LevelObject item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !this.isFeetColision(item));

		if (this.isFeetColision(item))
		{
			respuesta = item;
		}

		return respuesta;
	}

	/**
	 * Llamado cuando el player muere. Delega el metodo en this.playerState.die();
	 */
	public void die()
	{
		this.playerState.die();
	}

}
