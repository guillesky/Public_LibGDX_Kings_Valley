package modelo.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.player.Player;
import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import util.Config;

@SuppressWarnings("serial")
public abstract class Mummy extends GameCharacter
{

	public static final int ST_LIMBUS = 101;

	public static final int ST_APPEARING = 102;
	public static final int ST_TELEPORTING = 104;
	protected static final int BLOCK_FREE = 0;
	protected static final int BLOCK_BRICK = 1;
	protected static final int BLOCK_GIRATORY = 2;

	private static final int INDEX_SPEED_WALK = 0;
	private static final int INDEX_SPEED_STAIR = 1;
	private static final int INDEX_MIN_TIME_TO_DECIDE = 2;
	private static final int INDEX_MAX_TIME_TO_DECIDE = 3;
	private static final int INDEX_MIN_TIME_DECIDING = 4;
	private static final int INDEX_MAX_TIME_DECIDING = 5;
	private static final int INDEX_DECICION_FACTOR_FALL = 6;
	private static final int INDEX_DECICION_FACTOR_JUMP = 7;
	private static final int INDEX_BEST_DECICION_PROBALITY = 8;
	private static final int QUAD_DISTANCE_VISION = 9;

	public static final int END_BLOCK = 0;
	public static final int END_CLIFF = 1;
	public static final int END_STEP = 2;

	protected static final Random random = new Random();

	protected float decisionFactorForFall;
	protected float decisionFactorForJump;
	private float minTimeToDecide;
	private float maxTimeToDecide;
	private float minTimeDeciding;
	private float maxTimeDeciding;
	private float timeInState = 0;
	private float bestDecisionProbability;
	protected float rangeVision;
	protected float timeWhitoutSeePlayer = 0;
	private Vector2 direction = new Vector2();
	protected MummyState mummyState;
	protected float stressLevel = 0;
	protected Player player;

	protected float getTimeInState()
	{
		return timeInState;
	}

	protected void resetTimeInState()
	{
		this.timeInState = 0;
	}

	protected void incTimeInState(float delta)
	{
		this.timeInState += delta;
	}

	protected void setTimeInState(float timeInState)
	{
		this.timeInState = timeInState;
	}

	public Mummy(int type, float x, float y, float[] parameters, Pyramid pyramid, Player player)
	{
		super(type, x, y, parameters[INDEX_SPEED_WALK], parameters[INDEX_SPEED_STAIR], pyramid);
		this.decisionFactorForFall = parameters[INDEX_DECICION_FACTOR_FALL];
		this.decisionFactorForJump = parameters[INDEX_DECICION_FACTOR_JUMP];
		this.minTimeToDecide = parameters[INDEX_MIN_TIME_TO_DECIDE];
		this.maxTimeToDecide = parameters[INDEX_MAX_TIME_TO_DECIDE];
		this.minTimeDeciding = parameters[INDEX_MIN_TIME_DECIDING];
		this.maxTimeDeciding = parameters[INDEX_MAX_TIME_DECIDING];
		this.bestDecisionProbability = parameters[INDEX_BEST_DECICION_PROBALITY];
		this.rangeVision = parameters[QUAD_DISTANCE_VISION] * Config.getInstance().getLevelTileHeightUnits()
				* Config.getInstance().getLevelTileWidthUnits();

		this.mummyState = new MummyStateLimbus(this, 1);
		this.player = player;
	}

	@Override
	protected void doAction()
	{
		this.doJump();
	}

	protected boolean canJump()
	{

		boolean respuesta = false;

		if (this.isLookRight())
		{
			respuesta = this.getColPosition() < this.pyramid.getMapWidthInTiles() - 2
					&& this.pyramid.getCell(x + Config.getInstance().getLevelTileWidthUnits(),
							this.y + Config.getInstance().getLevelTileHeightUnits()) == null
					&&

					this.pyramid.getCell(x + Config.getInstance().getLevelTileWidthUnits(),
							this.y + Config.getInstance().getLevelTileHeightUnits() * 2) == null;

		} else
		{

			respuesta = this.getColPosition() > 1
					&& this.pyramid.getCell(x - Config.getInstance().getLevelTileWidthUnits(),
							this.y + Config.getInstance().getLevelTileHeightUnits()) == null
					&&

					this.pyramid.getCell(x - Config.getInstance().getLevelTileWidthUnits(),
							this.y + Config.getInstance().getLevelTileHeightUnits() * 2) == null;

		}
		return respuesta;
	}

	protected int CrashWallOrGiratory()
	{
		boolean condicion = false;

		int respuesta = Mummy.BLOCK_FREE;
		float epsilon = Config.getInstance().getLevelTileWidthUnits() * 0.1f;
		if (this.isLookRight())
		{
			condicion = this.getColPosition() >= this.pyramid.getMapWidthInTiles() - 2
					|| this.pyramid.getCell(x + Config.getInstance().getLevelTileWidthUnits(),
							this.y + Config.getInstance().getLevelTileHeightUnits()) != null
					||

					this.pyramid.getCell(x + Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

		} else
		{

			condicion = this.getColPosition() <= 1 || this.pyramid.getCell(x - epsilon,
					this.y + Config.getInstance().getLevelTileHeightUnits()) != null ||

					this.pyramid.getCell(x - epsilon, this.y) != null;

		}
		if (condicion)
			respuesta = Mummy.BLOCK_BRICK;
		else if (this.checkGiratory(direction))
			respuesta = Mummy.BLOCK_GIRATORY;

		return respuesta;
	}

	protected boolean isInBorderCliff()
	{
		boolean condicion = false;

		float probableX;
		if (this.isLookRight())
			probableX = x + width * .5f;
		else
			probableX = x;
		condicion = this.pyramid.getCell(probableX, this.y - Config.getInstance().getLevelTileHeightUnits()) == null
				&& this.pyramid.getCell(probableX, this.y) == null
				&& this.pyramid.getCell(probableX, this.y + Config.getInstance().getLevelTileHeightUnits()) == null;
		return condicion;

	}

	protected float getTimeToDecide()
	{
		return random.nextFloat(this.minTimeToDecide, this.maxTimeToDecide);
	}

	protected float getTimeDeciding()
	{
		return random.nextFloat(this.minTimeDeciding, this.maxTimeDeciding);
	}

	protected boolean makeDecisionForFall()
	{
		return Mummy.random.nextFloat(1.0f) <= this.decisionFactorForFall;
	}

	protected boolean makeDecisionForJump()
	{
		return Mummy.random.nextDouble(1) <= this.decisionFactorForJump;
	}

	protected boolean makeBestDecisionProbability()
	{
		return Mummy.random.nextDouble(1) <= this.bestDecisionProbability;
	}

	@Override
	protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{
		return false;
	}

	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

	}

	public void update(float deltaTime)
	{
		this.mummyState.update(deltaTime);
		this.incAnimationDelta(deltaTime);
		this.incTimeInState(deltaTime);
	}

	@Override
	protected void move(Vector2 v, boolean b, float deltaTime)
	{
		super.move(v, b, deltaTime);

	}

	protected Vector2 getDirection()
	{
		return direction;
	}

	protected void setState(int state)
	{
		this.state = state;

	}

	protected void stressing()
	{
		this.stressLevel++;
	}

	protected void calmStress(float cant)
	{
		this.stressLevel -= cant;
	}

	protected float getStressLevel()
	{
		return stressLevel;
	}

	protected void resetStress()
	{
		this.stressLevel = 0;

	}

	protected float distanceQuadToPlayer(Player player)
	{

		return this.distanceQuadToPlayer(this.x, this.y, player);
	}

	private float distanceQuadToPlayer(float paramX, float paramY, Player player)
	{
		float deltaX = paramX - player.getX();
		float deltaY = paramY - player.getY();

		return deltaX * deltaX + deltaY * deltaY;

	}

	@Override
	protected void resetAnimationDelta()
	{
		super.resetAnimationDelta();
	}

	@Override
	protected Pyramid getPyramid()
	{
		return super.getPyramid();
	}

	public void die(boolean mustTeleport)
	{
		this.mummyState = new MummyStateDying(this, mustTeleport);
		Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
	}

	public boolean isDanger()
	{
		return this.mummyState.isDanger();
	}

	public void teleport()
	{
		float[] coords;
		do
		{
			coords = this.getRandomCellInFloor();

		} while (this.distanceQuadToPlayer(coords[0], coords[1], player) < Config.getInstance()
				.getMinMummySpawnDistanceToPlayer());
		this.x = coords[0];
		this.y = coords[1];

	}

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
		{ j * Config.getInstance().getLevelTileWidthUnits(), i * Config.getInstance().getLevelTileHeightUnits() };
		return r;
	}

	@Override
	protected Stair checkStairsFeetColision(boolean positiveStairs, boolean isUpping)
	{

		return super.checkStairsFeetColision(positiveStairs, isUpping);
	}

	protected int[] endPlatform(boolean toRight)
	{
		int r[] = new int[2];
		int inc;
		int acum = 0;
		float xAux;
		xAux = x + width * .5f;
		if (toRight)
			inc = 1;
		else
			inc = -1;

		while (this.pyramid.getCell(xAux, y, acum, 0) == null && this.pyramid.getCell(xAux, y, acum, 1) == null
				&& this.pyramid.getCell(xAux, y, acum, -1) != null && this.isColDesplaInMap(acum))
		{
			acum += inc;
		}

		if (this.pyramid.getCell(xAux, y, acum, 0) == null && this.pyramid.getCell(xAux, y, acum, 1) == null
				&& this.pyramid.getCell(xAux, y, acum, -1) == null)
			r[0] = Mummy.END_CLIFF;
		else if ((this.pyramid.getCell(xAux, y, acum, 1) != null && this.pyramid.getCell(xAux, y, acum, 2) == null
				&& this.pyramid.getCell(xAux, y, acum, 3) == null)
				|| (this.pyramid.getCell(xAux, y, acum, 0) != null && this.pyramid.getCell(xAux, y, acum, 1) == null
						&& this.pyramid.getCell(xAux, y, acum, 2) == null))
			r[0] = Mummy.END_STEP;
		else
			r[0] = Mummy.END_BLOCK;

		if (acum < 0)
			acum *= -1;
		r[1] = acum;
		this.correctGiratoryEndPlatform(r, toRight);

		return r;

	}

	private void correctGiratoryEndPlatform(int[] r, boolean toRight)
	{
		Iterator<LevelObject> it = this.pyramid.getGiratories().iterator();
		boolean condicion = false;
		float posX = this.x + this.width * 0.5f;
		while (it.hasNext() && !condicion)
		{
			LevelObject giratoria = it.next();
			if (giratoria.y == this.y && (toRight && giratoria.x >= posX || !toRight && giratoria.x <= posX))
			{
				float aux = posX - (giratoria.x + giratoria.width * 0.5f);
				if (aux < 0)
					aux = -1;
				int dist = (int) (aux / Config.getInstance().getLevelTileWidthUnits());
				if(dist<r[1]) 
				{
					r[1]=dist;
					r[0]=Mummy.END_BLOCK;
					condicion=true;
				}
			}
		}

	}

	private boolean isColDesplaInMap(int col)
	{

		return this.getColPosition() + col > 1 && this.getColPosition() + col < this.pyramid.getMapWidthInTiles() - 1;
	}

	protected LevelObject nearStair(boolean toUp, boolean toRight)
	{
		LevelObject r = null;

		int count = this.endPlatform(toRight)[1];
		ArrayList<LevelObject> candidatesFootStairs = new ArrayList<LevelObject>();
		LevelObject footStair;

		Iterator<Stair> it = this.pyramid.getAllStairs().iterator();
		float tileWidth = Config.getInstance().getLevelTileWidthUnits();
		float posX = this.x + this.width * 0.5f;
		while (it.hasNext())
		{
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();
			if (footStair.y == this.y && (toRight && footStair.x >= posX || !toRight && footStair.x <= posX))
				candidatesFootStairs.add(footStair);
		}

		if (!candidatesFootStairs.isEmpty())
		{
			Iterator<LevelObject> itf = candidatesFootStairs.iterator();

			footStair = itf.next();
			LevelObject minFootStair = footStair;

			double aux = this.x - footStair.x;
			if (aux < 0)
				aux *= -1;
			int min = (int) (aux / tileWidth);
			while (itf.hasNext())
			{
				footStair = itf.next();
				aux = posX - (footStair.x + footStair.width * 0.5);
				if (aux < 0)
					aux = -1;
				int dist = (int) (aux / tileWidth);
				if (dist < min)
				{
					min = dist;
					minFootStair = footStair;
				}
			}
			System.out.println("min: " + min + "    count" + count);
			if (min <= count)
				r = minFootStair;
		}

		return r;
	}


	/*
	@Override
	public String toString()
	{
		int[] der = this.endPlatform(true);
		int[] izq = this.endPlatform(false);
		return " x=" + x + ", y=" + y + " DERECHA " + der[0] + "  " + der[1] + " IZQUIERDA " + izq[0] + "  " + izq[1]
				+ "\nEscalera IZQ arriba: " + this.nearStair(true, false) + " Escalera DER arriba: "
				+ this.nearStair(true, true) + "\nEscalera IZQ abajo: " + this.nearStair(false, false)
				+ " Escalera DER abajo: " + this.nearStair(false, true);
	}*/
}
