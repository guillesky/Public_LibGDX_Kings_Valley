package engine.level;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import engine.DrawableElement;
import engine.KVEventListener;
import engine.game.Game;
import util.GameRules;
import util.Constants;

/**
 * Representa un muro trampa
 * 
 * @author Guillermo Lazzurri
 */
public class TrapMechanism extends Mechanism
{

	private TiledMapTileLayer layer;

	private TiledMapTile tile;
	private int x;
	private int y;

	/**
	 * Constructor de clase, llama a super(timeToEnd);<br>
	 * Invoca a pyramid.addGraphicElement(new
	 * DrawableElement(Constantes.DRAWABLE_TRAP, this));<br>
	 * Dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.ACTIVATE_TRAP, this);
	 * 
	 * @param pyramid   La piramide a la cual pertenece el muro trampa
	 * @param wall      Donde comienza a bajar la pared
	 * @param timeToEnd Timpo necesario para bajar un tile.
	 */
	public TrapMechanism(Pyramid pyramid, LevelObject wall, float timeToEnd)
	{
		super(timeToEnd);

		this.layer = (TiledMapTileLayer) pyramid.getMap().getLayers().get("front");

		this.x = wall.getColPosition();
		this.y = wall.getRowPosition();
		this.tile = this.layer.getCell(x, y).getTile();
		pyramid.addGraphicElement(new DrawableElement(Constants.DRAWABLE_TRAP, this));
		Game.getInstance().eventFired(KVEventListener.ACTIVATE_TRAP, this);

	}

	/**
	 * Si paso el tiempo correspondiente a this.timeToEnd se invoca a nextCell
	 */
	@Override
	public void update(float deltaTime)
	{
		this.incTime(deltaTime);
		if (this.time >= this.timeToEnd)
		{
			this.nextCell();
		}
	}

	/**
	 * Si el muro no llego al piso pasa a la celda de abajo. Si llego al piso
	 * termina.
	 */
	private void nextCell()
	{
		this.resetTime();
		y--;
		TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();
		newCell.setTile(tile);
		this.layer.setCell(x, y, newCell);

		if (this.layer.getCell(x, y - 1) != null)
			this.active = false;
	}

	/**
	 * Retorna el tile correspondiente a la celda de inicio del muro trampa
	 * @return El tile correspondiente a la celda de inicio del muro trampa
	 */
	public TiledMapTile getTile()
	{
		return tile;
	}

	/**
	 * Retorna la coordenada x del muro trampa
	 * @return La coordenada x del muro trampa
	 */
	public float getX()
	{
		float r = x * GameRules.getInstance().getLevelObjectHeight();
		return r;
	}

	/**
	 * Retorna la coordenada y del muro trampa
	 * @return La coordenada y del muro trampa
	 */

	public float getY()
	{
		float auxY = y;
		float r = (auxY - this.time) * GameRules.getInstance().getLevelObjectHeight();
		return r;
	}

}
