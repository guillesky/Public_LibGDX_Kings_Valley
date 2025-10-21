package engine.gameCharacters.player;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.level.GiratoryMechanism;
import engine.level.PairInt;
import engine.level.dagger.Dagger;
import util.GameRules;

/**
 * 
 * Representa el estado "moviendose" (tambien esta en este estado si el player
 * esta en modo descanso)
 * 
 * @author Guillermo Lazzurri
 */
public class PlayerStateMoving extends PlayerState
{

	/**
	 * Constructor de clase, llama a super(player, state);
	 * 
	 * @param player Corresponde al sujeto del patron state
	 * @param state  Indica el tipo de estado. En este caso puede tomar los valores
	 *               GameCharacter.ST_IDDLE; o GameCharacter.ST_WALKING;
	 */
	public PlayerStateMoving(Player player, int state)
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
		Game.getInstance().eventFired(KVEventListener.ENTER_GIRATORY, giratoryMechanism);

	}

	/**
	 * Llamado al intentar picar, podria llamar al metodo privado picking donde se
	 * realizan los cambios de estado
	 */
	@Override
	protected void doPicker()
	{
		float px, py;

		if (this.player.isLocked())
		{
			if (this.player.isLookRight())
			{
				px = this.player.x + GameRules.getInstance().getLevelTileWidthUnits();
			} else
			{
				px = this.player.x - GameRules.getInstance().getLevelTileWidthUnits();
			}
			py = this.player.y + GameRules.getInstance().getLevelTileHeightUnits();
			if (this.player.getPyramid().getCell(px, py) != null)
			{
				int tileY = (int) (py / GameRules.getInstance().getLevelTileHeightUnits());
				int tileX = (int) (px / GameRules.getInstance().getLevelTileWidthUnits());
				if (this.picking(tileX, tileY, 2, true, new ArrayList<PairInt>()))
					this.player.item = null;

			} else
			{
				int tileY = (int) (this.player.y / GameRules.getInstance().getLevelTileHeightUnits());
				int tileX = (int) (px / GameRules.getInstance().getLevelTileWidthUnits());
				if (this.picking(tileX, tileY, 1, true, new ArrayList<PairInt>()))
					this.player.item = null;
			}
		} else

		{

			if (this.player.isLookRight())
			{
				px = this.player.x + GameRules.getInstance().getLevelTileWidthUnits();
				py = this.player.y - GameRules.getInstance().getLevelTileHeightUnits();

			} else
			{
				px = this.player.x + this.player.width - GameRules.getInstance().getLevelTileWidthUnits();
				py = this.player.y - GameRules.getInstance().getLevelTileHeightUnits();

			}

			int tileY = (int) (py / GameRules.getInstance().getLevelTileHeightUnits());
			int tileX = (int) (px / GameRules.getInstance().getLevelTileWidthUnits());

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
	 * Llamado al intentar lanzar la espada. Si se lanza, se realiza el cambio de
	 * estado mediante this.player.setPlayerState(new
	 * PlayerStateThrowingDagger(this.player));
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
			dagger.x = this.player.x + direccion * GameRules.getInstance().getLevelTileHeightUnits() * 0.2f;
			dagger.y = this.player.y + GameRules.getInstance().getLevelTileHeightUnits();
			
			dagger.throwHorizontal(this.player.isLookRight());
			this.player.setPlayerState(new PlayerStateThrowingDagger(this.player));
		} else if (this.player.getPyramid().getCell(this.player.x, this.player.y, direccion, 2) == null
				&& this.player.getPyramid().getCell(this.player.x, this.player.y, 0, 2) == null)
		{
			dagger.x = this.player.x;
			dagger.y = this.player.y + GameRules.getInstance().getLevelTileHeightUnits();
			dagger.throwVertical(this.player.isLookRight());

			this.player.setPlayerState(new PlayerStateThrowingDagger(this.player));

		}

	}

}
