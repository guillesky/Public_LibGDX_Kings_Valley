package engine.gameCharacters.mummys;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.gameCharacters.player.Player;
import engine.level.GiratoryMechanism;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase abstracta que representa un momia
 */
@SuppressWarnings("serial")
public abstract class Mummy extends GameCharacter
{

	/**
	 * En el limbo
	 */
	public static final int ST_LIMBUS = 101;

	/**
	 * Apareciendo
	 */
	public static final int ST_APPEARING = 102;
	/**
	 * Teletransportandose
	 */
	public static final int ST_TELEPORTING = 104;
	protected static final int BLOCK_FREE = 0;
	protected static final int BLOCK_BRICK = 1;
	protected static final int BLOCK_GIRATORY = 2;

	protected static final Random random = new Random();

	protected float decisionFactorForFall;
	protected float decisionFactorForJump;
	private float timeToDecide;
	private float timeDeciding;
	private float timeInState = 0;

	private Vector2 direction = new Vector2();
	protected MummyState mummyState;
	private float stressLevel = 0;
	protected Player player;

	/**
	 * Constructor de clase
	 * 
	 * @param type       Indica el tipo de momia (solo deberia utilizarse para
	 *                   elegir modelos de visualizacion)
	 * @param x          Coordenada x
	 * @param y          Coordenada y
	 * @param parameters array de float que indica los parametros de la momia
	 * @param pyramid    piramide en la que esta ubicada la momia
	 * @param player     referencia al player que la momia persigue
	 */
	public Mummy(int type, float x, float y, float[] parameters, Pyramid pyramid, Player player)
	{
		super(type, x, y, parameters[GameRules.INDEX_SPEED_WALK], parameters[GameRules.INDEX_SPEED_STAIR], pyramid);
		this.decisionFactorForFall = parameters[GameRules.INDEX_DECICION_FACTOR_FALL];
		this.decisionFactorForJump = parameters[GameRules.INDEX_DECICION_FACTOR_JUMP];
		this.timeToDecide = parameters[GameRules.INDEX_TIME_TO_DECIDE];
		this.timeDeciding = parameters[GameRules.INDEX_TIME_DECIDING];

		this.mummyState = new MummyStateLimbus(this, 1);
		this.player = player;
	}

	/**
	 * Llama a this.doJump(); (las momias solo pueden saltar) y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.MUMMY_JUMP, this);
	 * 
	 */
	@Override
	protected void doAction()
	{
		this.doJump();
		Game.getInstance().eventFired(KVEventListener.MUMMY_JUMP, this);

	}

	/**
	 * Indica si la momia esta al borde de una cornisa
	 * 
	 * @return true si esta al borde de una cornisa, false en caso contrario
	 */
	protected boolean isInBorderCliff()
	{
		boolean condicion = false;

		float probableX;
		if (this.isLookRight())
			probableX = x + width * .5f;
		else
			probableX = x;
		condicion = this.pyramid.getCell(probableX, this.y - GameRules.getInstance().getLevelTileHeightUnits()) == null
				&& this.pyramid.getCell(probableX, this.y) == null
				&& this.pyramid.getCell(probableX, this.y + GameRules.getInstance().getLevelTileHeightUnits()) == null;
		return condicion;

	}

	/**
	 * 
	 * @return tiempo que tarda la momia en tomar la proxima decision
	 */
	protected float getTimeToDecide()
	{
		return this.timeToDecide;
	}

	/**
	 * @return tiempo que tarda la momia en dudar, para tomar la decision
	 */
	protected float getTimeDeciding()
	{
		return this.timeDeciding;
	}

	/**
	 * @return true si la momia decide saltar, false en caso contrario
	 */
	protected boolean makeDecisionForJump()
	{
		return Mummy.random.nextDouble() <= this.decisionFactorForJump;
	}

	/**
	 * Siempre retorna false (las momias no pueden utilizar giratorias)
	 */
	@Override
	protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{
		return false;
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

	}

	/**
	 * Llamado para actualizar la momia
	 * 
	 * @param deltaTime tiempo desde la ultima llamada
	 */
	public void update(float deltaTime)
	{
		this.incAnimationDelta(deltaTime);
		this.incTimeInState(deltaTime);
		this.mummyState.update(deltaTime);

	}

	/**
	 * llama a super.move(v, b, deltaTime);
	 * 
	 */
	@Override
	protected void move(Vector2 v, boolean b, float deltaTime)
	{
		super.move(v, b, deltaTime);

	}

	/**
	 * @return la direccion hacia la que se dirige la momia
	 */
	public Vector2 getDirection()
	{
		return direction;
	}

	/**
	 * Setea el atribotuo state (no confundir con el patron State)
	 */
	protected void setState(int state)
	{
		this.state = state;

	}

	/**
	 * Incrementa el nivel de stress de la momia (si es muy alto la momia muere)
	 */
	protected void stressing()
	{
		this.stressLevel++;
	}

	/**
	 * Calma el nivel de stress de la momia
	 * 
	 * @param cant cantidad de stress que debe restar
	 */
	protected void calmStress(float cant)
	{
		this.stressLevel -= cant;
	}

	/**
	 * @return nivel de stress de la momia
	 */
	protected float getStressLevel()
	{
		return stressLevel;
	}

	/**
	 * pone el nivel de stress en cero
	 */
	protected void resetStress()
	{
		this.stressLevel = 0;

	}

	/**
	 * Calcula la distancia al cuadrado del player a las coordenadas x e y pasadas
	 * por parametro, usado para calcular posibles destinos de teletransporte
	 * 
	 * @param paramX coordenada x de destino
	 * @param paramY coordenada y de destino
	 * @return distancia al cuadrado
	 */
	private float distanceQuadToPlayer(float paramX, float paramY)
	{
		float deltaX = paramX - player.getX();
		float deltaY = paramY - player.getY();

		return deltaX * deltaX + deltaY * deltaY;

	}

	/**
	 * llama a super.resetAnimationDelta(); (su unico objetivo es hacer visible el
	 * metodo dentro del paquete)
	 */
	@Override
	protected void resetAnimationDelta()
	{
		super.resetAnimationDelta();
	}

	/**
	 * Delega en el metodo this.mummyState.die(mustTeleport); (patron state)
	 * 
	 * @param mustTeleport true si la momia debe teletransportarse al renacer, false
	 *                     en caso contrario.
	 */
	public void die(boolean mustTeleport)
	{

		this.mummyState.die(mustTeleport);
	}

	/**
	 * Delega en el metodo this.mummyState.isDanger(); (patron state)
	 * 
	 * @return true si la momia es peligrosa, false en caso contrario (esta en el
	 *         limbo, apareciendo o muriendo)
	 * 
	 */
	public boolean isDanger()
	{
		return this.mummyState.isDanger();
	}

	/**
	 * Llamado para calcular un lugar de teletransportacion
	 */
	public void teleport()
	{
		float[] coords;
		do
		{
			coords = this.getRandomCellInFloor();

		} while (this.distanceQuadToPlayer(coords[0], coords[1]) < GameRules.getInstance()
				.getMinMummySpawnDistanceToPlayer());
		this.x = coords[0];
		this.y = coords[1];

	}

	/**
	 * @return retorna al azar una celda candidata para teletransporte
	 */
	private float[] getRandomCellInFloor()
	{
		int i;
		int j;
		do
		{
			i = random.nextInt(this.pyramid.getMapHeightInTiles() - 2) + 1;
			j = random.nextInt(this.pyramid.getMapWidthInTiles() - 2) + 1;

		} while (this.pyramid.getCellInTiledCoord(j, i) != null || this.pyramid.getCellInTiledCoord(j, i + 1) != null);

		while (this.pyramid.getCellInTiledCoord(j, i - 1) == null)
			i--;
		float[] r =
		{ j * GameRules.getInstance().getLevelTileWidthUnits(), i * GameRules.getInstance().getLevelTileHeightUnits() };
		return r;
	}

	/**
	 * Llama super.checkStairsFeetColision(positiveStairs, isUpping); (su unico
	 * objetivo es hacer visible el metodo dentro del paquete)
	 */
	@Override
	protected Stair checkStairsFeetColision(boolean positiveStairs, boolean isUpping)
	{

		return super.checkStairsFeetColision(positiveStairs, isUpping);
	}

	/**
	 * Llama super.enterStair(stair); (su unico objetivo es hacer visible el metodo
	 * dentro del paquete)
	 */
	@Override
	protected void enterStair(Stair stair)
	{

		super.enterStair(stair);
	}

	/**
	 * @return true si la momia puede saltar sin chocarse arriba, false en caso
	 *         contrario
	 */
	protected boolean canJump()
	{
		float posX;
		int offset;
		if (this.lookRight)
		{
			posX = this.x;
			offset = 1;
		} else
		{
			posX = this.x + this.width;
			offset = -1;
		}
		return this.pyramid.getCell(posX, this.y, offset, 2) == null;

	}

	/**
	 * @return El tiempo que permacio en el estado actual
	 */
	protected float getTimeInState()
	{
		return timeInState;
	}

	/**
	 * Pone en cero el tiempo en el estado actual
	 */
	protected void resetTimeInState()
	{
		this.timeInState = 0;
	}

	/**
	 * Incrementa el tiempo en el estaco actual
	 * 
	 * @param delta tiempo de incremento
	 */
	protected void incTimeInState(float delta)
	{
		this.timeInState += delta;
	}

}
