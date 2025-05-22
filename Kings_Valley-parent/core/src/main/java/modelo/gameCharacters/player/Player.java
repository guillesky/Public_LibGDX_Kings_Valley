package modelo.gameCharacters.player;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.gameCharacters.mummys.Mummy;
import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import modelo.level.dagger.Dagger;
import modelo.level.dagger.DaggerState;
import util.Config;
import util.Constantes;

@SuppressWarnings("serial")
public class Player extends GameCharacter
{
	public static final int ST_THROWING_DAGGER = 20;

	public static final int ST_PICKING = 21;

	private LevelObject item = null;
	private float timePicking = 0;
	private PlayerState playerState = null;

	public Player(float x, float y, Pyramid pyramid)
	{
		super(Constantes.PLAYER, x, y, Config.getInstance().getPlayerSpeedWalk(),
				Config.getInstance().getPlayerSpeedWalkStairs(), pyramid);
		this.playerState = new PlayerStateWalking(this, GameCharacter.ST_IDDLE);
	}

	public void update(Vector2 v, boolean b, float deltaTime)
	{
		this.incAnimationDelta(deltaTime);
		this.playerState.update(v, b, deltaTime);
	}

	public LevelObject getItem()
	{
		return item;
	}

	@Override
	protected void doAction()
	{
		if (this.item == null)
			this.doJump();
		else if (this.item.getType() == Constantes.It_picker)
			this.doPicker();
		else if (this.item.getType() == Constantes.It_dagger)
			this.throwDagger();

	}

	private void throwDagger()
	{
		Dagger dagger = (Dagger) this.item;
		int direccion;
		if (this.isLookRight())
			direccion = 1;
		else
			direccion = -1;
		if (this.pyramid.getCell(x, y, direccion, 1) == null)
		{
			dagger.x = this.x + direccion * Config.getInstance().getLevelTileHeightUnits() * 0.1f;
			dagger.y = this.y + Config.getInstance().getLevelTileHeightUnits();
			// this.pyramid.addFlyingDagger(dagger);

			dagger.throwHorizontal(isLookRight());
			this.item = null;
			this.playerState = new PlayerStateThrowingDagger(this);
		} else if (this.pyramid.getCell(x, y, direccion, 2) == null && this.pyramid.getCell(x, y, 0, 2) == null)
		{
			dagger.x = this.x;
			dagger.y = this.y + Config.getInstance().getLevelTileHeightUnits();
			dagger.throwVertical(isLookRight());
			this.playerState = new PlayerStateThrowingDagger(this);
			this.item = null;

		}

	}

	private void doPicker()
	{

		float px, py;

		if (this.isLocked())
		{
			if (this.isLookRight())
			{
				px = this.x + Config.getInstance().getLevelTileWidthUnits();
			} else
			{
				px = this.x - Config.getInstance().getLevelTileWidthUnits();
			}
			py = this.y + Config.getInstance().getLevelTileHeightUnits();
			if (this.pyramid.getCell(px, py) != null)
			{
				int tileY = (int) (py / Config.getInstance().getLevelTileHeightUnits());
				int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());
				if (this.picking(tileX, tileY, 2, true, new ArrayList<PairInt>()))
					this.item = null;

			} else
			{
				int tileY = (int) (this.y / Config.getInstance().getLevelTileHeightUnits());
				int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());
				if (this.picking(tileX, tileY, 1, true, new ArrayList<PairInt>()))
					this.item = null;
			}
		} else

		{

			if (this.isLookRight())
			{
				px = this.x + Config.getInstance().getLevelTileWidthUnits();
				py = this.y - Config.getInstance().getLevelTileHeightUnits();

			} else
			{
				px = this.x + this.width - Config.getInstance().getLevelTileWidthUnits();
				py = this.y - Config.getInstance().getLevelTileHeightUnits();

			}

			int tileY = (int) (py / Config.getInstance().getLevelTileHeightUnits());
			int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());

			if (this.picking(tileX, tileY, 2, false, new ArrayList<PairInt>()))
				this.item = null;
		}
	}

	private boolean picking(int x, int y, int cont, boolean locked, ArrayList<PairInt> coordToPick)
	{
		boolean respuesta = false;

		TiledMapTileLayer layer = (TiledMapTileLayer) this.pyramid.getMap().getLayers().get("front");
		Cell cell = layer.getCell(x, y);
		Cell celdaArriba = layer.getCell(x, y + 1);

		if (x != 0 && x != this.pyramid.getMapWidthInTiles() - 1 && y != 0 && this.pyramid.isPickable(cell)
				&& cell != null && (celdaArriba == null || locked))
		{

			PairInt pairInt = new PairInt(x, y);
			coordToPick.add(pairInt);
			// this.state = Player.ST_PICKING;
			this.setPlayerState(new PlayerStatePicking(this, coordToPick));
			respuesta = true;
			if (cont == 2)
				this.picking(x, y - 1, 1, true, coordToPick);
		}
		return respuesta;
	}

	@Override
	protected boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{

		return (this.state == GameCharacter.ST_WALKING && this.isLookRight() && giratoryMechanism.isRight())
				|| (this.state == GameCharacter.ST_WALKING && !this.isLookRight() && !giratoryMechanism.isRight());
	}

	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{
		this.setPlayerState(new PlayerStatePassingGiratory(this, giratoryMechanism));
		giratoryMechanism.activate();

	}

	@Override
	protected void setState(int state)
	{
		super.setState(state);
	}

	protected float getTimePicking()
	{
		return timePicking;
	}

	protected void incTimePicking(float delta)
	{
		this.timePicking += delta;
	}

	protected void resetTimePicking()
	{
		this.timePicking = 0;
	}

	protected void setPlayerState(PlayerState playerState)
	{
		this.playerState = playerState;
	}

	@Override
	protected void resetAnimationDelta()
	{
		super.resetAnimationDelta();
	}

	protected float getSpeedWalk()
	{
		return speedWalk;
	}

	protected float getSpeedWalkStairs()
	{
		return speedWalkStairs;
	}

	protected float getSpeedJump()
	{
		return speedJump;
	}

	protected void incX(float cant)
	{
		this.x += cant;
	}

	@Override
	protected void move(Vector2 v, boolean b, float deltaTime)
	{
		super.move(v, b, deltaTime);
		this.pickupCollectables();

		LevelObject activator = this.checkRectangleColision(this.pyramid.getActivators());
		if (activator != null)
			this.pyramid.activateWall(activator);
		if (!this.isInStair())
			this.checkGiratory(v);
	}

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
				LevelObject picker = this.checkRectangleColision(this.pyramid.getPickers());
				if (picker != null)
				{
					this.item = picker;
					this.pyramid.removePicker(picker);
					Game.getInstance().eventFired(KVEventListener.PICKUP_PICKER, picker);

				}

				Dagger dagger = (Dagger) this.checkRectangleColision(this.pyramid.getStuckedDaggers());
				if (dagger != null && dagger.getState() == DaggerState.ST_STUCKED)
				{
					this.item = dagger;
					dagger.hasPickuped();
					Game.getInstance().eventFired(KVEventListener.PICKUP_DAGGER, dagger);

				}
			}
		}
	}

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

	@Override
	protected Pyramid getPyramid()
	{

		return super.getPyramid();
	}

	public void die()
	{
		this.playerState = new PlayerStateDying(this);
	}

	private int[] endPlatform(boolean toRight)
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
		return r;

	}

	private boolean isColDesplaInMap(int col)
	{

		return this.getColPosition() + col > 1 && this.getColPosition() + col < this.pyramid.getMapWidthInTiles() - 1;
	}

	private int nearStair(boolean toUp, boolean toRight)
	{
		int r = -1;

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

			double aux = this.x - footStair.x;
			if (aux < 0)
				aux = -1;
			int min = (int) (aux / tileWidth);
			while (itf.hasNext())
			{
				footStair = itf.next();
				aux = this.x + this.width * 0.5 - (footStair.x + footStair.width * 0.5);
				if (aux < 0)
					aux = -1;
				int dist = (int) (aux / tileWidth);
				if (dist < min)
					min = dist;
			}
			if (min <= count)
				r = min;
		}

		return r;
	}

	@Override
	public String toString()
	{
		int[] der = this.endPlatform(true);
		int[] izq = this.endPlatform(false);
		return " x=" + x + ", y=" + y + " DERECHA " + der[0] + "  " + der[1] + " IZQUIERDA " + izq[0] + "  " + izq[1]
				+ "\nEscalera IZQ arriba: " + this.nearStair(true, false) + " Escalera DER arriba: "
				+ this.nearStair(true, true) + "\nEscalera IZQ abajo: " + this.nearStair(false, false)
				+ " Escalera DER abajo: " + this.nearStair(false, true);
	}

}
