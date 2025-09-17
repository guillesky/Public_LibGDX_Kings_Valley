package modelo.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.abstractGameCharacter.GameCharacter;
import modelo.level.GiratoryMechanism;
import modelo.level.PairInt;
import modelo.level.dagger.Dagger;
import util.Config;

/**
 * @author Guillermo Lazzurri
 * Representa el estado "caminando" (tambien esta en este estado si el player esta en modo descanso)
 */
public class PlayerStateWalking extends PlayerState
{

	/**
	 * Constructor de clase, llama a super(player, state);
	 * @param player Corresponde al sujeto del patron state
	 * @param state Indica el tipo de estado. En este caso puede tomar los valores GameCharacter.ST_IDDLE; o GameCharacter.ST_WALKING;
	 */
	public PlayerStateWalking(Player player, int state)
	{
		super(player, state);
	}

	@Override
	public void update(Vector2 v, boolean b, float deltaTime)
	{
		this.player.move(v, b, deltaTime);

	}
	/**
	 * Se realiza el cambio de estado mediante this.player.setPlayerState(new
	 * PlayerStateDying(this.player));
	 * 
	 */
	@Override
	protected void die()
	{
		this.player.setPlayerState(new PlayerStateDying(this.player));
	}

	/**
	 * Llamado al pasar exitosamente por la giratoria. Dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.ENTER_GIRATORY, this);
	 * 
	 */

	@Override
	protected void passGiratoryMechanism(GiratoryMechanism giratoryMechanism)
	{
		this.player.setPlayerState(new PlayerStatePassingGiratory(this.player, giratoryMechanism));
		giratoryMechanism.activate();
		Game.getInstance().eventFired(KVEventListener.ENTER_GIRATORY, this);

	}

	/**
	 * Llamado al intentar picar, podria llamar al metodo privado picking donde se realizan los cambios de estado 
	 */
	@Override
	protected void doPicker()
	{
		float px, py;

		if (this.player.isLocked())
		{
			if (this.player.isLookRight())
			{
				px = this.player.x + Config.getInstance().getLevelTileWidthUnits();
			} else
			{
				px = this.player.x - Config.getInstance().getLevelTileWidthUnits();
			}
			py = this.player.y + Config.getInstance().getLevelTileHeightUnits();
			if (this.player.getPyramid().getCell(px, py) != null)
			{
				int tileY = (int) (py / Config.getInstance().getLevelTileHeightUnits());
				int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());
				if (this.picking(tileX, tileY, 2, true, new ArrayList<PairInt>()))
					this.player.item = null;

			} else
			{
				int tileY = (int) (this.player.y / Config.getInstance().getLevelTileHeightUnits());
				int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());
				if (this.picking(tileX, tileY, 1, true, new ArrayList<PairInt>()))
					this.player.item = null;
			}
		} else

		{

			if (this.player.isLookRight())
			{
				px = this.player.x + Config.getInstance().getLevelTileWidthUnits();
				py = this.player.y - Config.getInstance().getLevelTileHeightUnits();

			} else
			{
				px = this.player.x + this.player.width - Config.getInstance().getLevelTileWidthUnits();
				py = this.player.y - Config.getInstance().getLevelTileHeightUnits();

			}

			int tileY = (int) (py / Config.getInstance().getLevelTileHeightUnits());
			int tileX = (int) (px / Config.getInstance().getLevelTileWidthUnits());

			if (this.picking(tileX, tileY, 2, false, new ArrayList<PairInt>()))
				this.player.item = null;
		}
	}

	/**
	 * Llamado para picar una celda concreta. Realiza el cambio de estado llamando a
	 * this.player.setPlayerState(new PlayerStatePicking(this.player, coordToPick));
	 * 
	 * @param x           coordenada matricial x del player
	 * @param y           coordenada matricial y del player
	 * @param cont        Indica la cantidad de celdas que faltan picar (en caso de
	 *                    necesitar picar dos celdas)
	 * @param locked      true si el player esta encerrado, false en caso contrario
	 * @param coordToPick array que indica las coordenadas matriciales con las
	 *                    celdas a picar
	 * @return true si pudo picar, false en caso contrario
	 */
	private boolean picking(int x, int y, int cont, boolean locked, ArrayList<PairInt> coordToPick)
	{
		boolean respuesta = false;

		TiledMapTileLayer layer = (TiledMapTileLayer) this.player.getPyramid().getMap().getLayers().get("front");
		Cell cell = layer.getCell(x, y);
		Cell celdaArriba = layer.getCell(x, y + 1);

		if (x != 0 && x != this.player.getPyramid().getMapWidthInTiles() - 1 && y != 0
				&& this.player.getPyramid().isPickable(cell) && cell != null && (celdaArriba == null || locked))
		{

			PairInt pairInt = new PairInt(x, y);
			coordToPick.add(pairInt);
			// this.state = Player.ST_PICKING;
			this.player.setPlayerState(new PlayerStatePicking(this.player, coordToPick));

			respuesta = true;
			if (cont == 2)
				this.picking(x, y - 1, 1, true, coordToPick);
		}
		return respuesta;
	}

	/**
	 * Llamado al intentar lanzar la espada. Si se lanza, se dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.THROW_DAGGER, dagger);
	 * 
	 */
	protected void throwDagger()
	{
		Dagger dagger = (Dagger) this.player.item;
		int direccion;
		if (this.player.isLookRight())
			direccion = 1;
		else
			direccion = -1;
		if (this.player.getPyramid().getCell(this.player.x, this.player.y, direccion, 1) == null)
		{
			dagger.x = this.player.x + direccion * Config.getInstance().getLevelTileHeightUnits() * 0.1f;
			dagger.y = this.player.y + Config.getInstance().getLevelTileHeightUnits();
			// this.pyramid.addFlyingDagger(dagger);

			dagger.throwHorizontal(this.player.isLookRight());
			Game.getInstance().eventFired(KVEventListener.THROW_DAGGER, dagger);
			this.player.item = null;
			this.player.setPlayerState(new PlayerStateThrowingDagger(this.player));
		} else if (this.player.getPyramid().getCell(this.player.x, this.player.y, direccion, 2) == null
				&& this.player.getPyramid().getCell(this.player.x, this.player.y, 0, 2) == null)
		{
			dagger.x = this.player.x;
			dagger.y = this.player.y + Config.getInstance().getLevelTileHeightUnits();
			dagger.throwVertical(this.player.isLookRight());
			Game.getInstance().eventFired(KVEventListener.THROW_DAGGER, dagger);
			this.player.setPlayerState(new PlayerStateThrowingDagger(this.player));
			this.player.item = null;

		}

	}

}
