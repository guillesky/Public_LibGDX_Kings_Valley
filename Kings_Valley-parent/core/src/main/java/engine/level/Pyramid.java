package engine.level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import engine.DrawableElement;
import engine.IGraphic;
import engine.KVEventListener;
import engine.game.Game;
import engine.level.dagger.Dagger;
import engine.level.door.Door;
import util.GameRules;
import util.Constants;

/**
 * @author Guillermo Lazzurri Clase que representa una piramide. Tiene
 *         informacion sobre las celdas, escaleras, joyas, picos, espadas,
 *         puertas giratorias, puertas de salida y las trampas. No incluye
 *         informacion sobre el player o las o las momias
 */
public class Pyramid implements IGraphic
{
	private TiledMap map;

	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private int mapWidthInUnits;
	private int mapHeightInUnits;
	private ArrayList<Door> doors;
	private ArrayList<LevelObject> jewels = new ArrayList<LevelObject>();
	private ArrayList<Stair> positiveStairs;
	private ArrayList<Stair> negativeStairs;
	private ArrayList<Stair> allStairs;
	private ArrayList<LevelObject> pickers = new ArrayList<LevelObject>();

	private ArrayList<LevelObject> activators = new ArrayList<LevelObject>();
	private ArrayList<TrapMechanism> trapMechanisms = new ArrayList<TrapMechanism>();
	private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
	private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();

	private HashMap<LevelObject, LevelObject> hashTraps = new HashMap<LevelObject, LevelObject>();
	private HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
	private IGraphic interfaz = null;

	/**
	 * Crea una piramide de acuerdo a los datos suministrdos por parametro. Este
	 * constructor es llamado por la clase LevelReader.
	 * 
	 * @param map                    Mapa que representa la piramide
	 * @param doors                  Coleccion de puertas de entrada
	 * @param jewels                 Coleccion de joyas
	 * @param positiveStairs         Coleccion de escaleras con pendiente positiva
	 * @param negativeStairs         Coleccion de escaleras con pendiente negativa
	 * @param pickers                Coleccion de picos
	 * @param stuckedDaggers         Coleccion de espadas clavadas en el piso
	 * @param unpickableCells        Coleccion de celdas que no pueden ser picadas
	 * @param hashTraps              HashMap de trampas, el objeto clase es su
	 *                               activador de tipo LevelObject
	 * @param hashGiratoryMechanisms HashMap de mecanismos giratorios, el objeto
	 *                               clase es la puerta giratoria de tipo
	 *                               LevelObject
	 * @param interfaz               Interfaz grafica asociada.
	 */
	public Pyramid(TiledMap map, ArrayList<Door> doors, ArrayList<LevelObject> jewels, ArrayList<Stair> positiveStairs,
			ArrayList<Stair> negativeStairs, ArrayList<LevelObject> pickers, ArrayList<Dagger> stuckedDaggers,
			ArrayList<Cell> unpickableCells, HashMap<LevelObject, LevelObject> hashTraps,
			HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms, IGraphic interfaz)
	{

		this.map = map;
		this.interfaz = interfaz;
		MapProperties properties = map.getProperties();
		int tileWidth = properties.get("tilewidth", Integer.class);
		int tileHeight = properties.get("tileheight", Integer.class);

		mapWidthInTiles = properties.get("width", Integer.class);
		mapHeightInTiles = properties.get("height", Integer.class);
		this.mapHeightInUnits = mapHeightInTiles * tileHeight;
		this.mapWidthInUnits = mapWidthInTiles * tileWidth;
		this.doors = doors;
		this.jewels = jewels;
		this.positiveStairs = positiveStairs;
		this.negativeStairs = negativeStairs;
		this.pickers = pickers;
		this.stuckedDaggers = stuckedDaggers;
		this.trapMechanisms = new ArrayList<TrapMechanism>();
		this.unpickableCells = unpickableCells;
		this.hashTraps = hashTraps;
		this.hashGiratoryMechanisms = hashGiratoryMechanisms;
		this.allStairs = new ArrayList<Stair>();
		allStairs.addAll(this.positiveStairs);
		allStairs.addAll(this.negativeStairs);
		this.activators.addAll(this.hashTraps.keySet());

	}

	/**
	 * @return El mapa de la piramide
	 */
	public TiledMap getMap()
	{
		return map;
	}

	/**
	 * @return Ancho del mapa en tiles
	 */
	public int getMapWidthInTiles()
	{
		return mapWidthInTiles;
	}

	/**
	 * @return Alto del mapa en tiles
	 */
	public int getMapHeightInTiles()
	{
		return mapHeightInTiles;
	}

	/**
	 * @return Todos los objetos del mapa. Picos, Joyas, Dagas y Giratorias
	 */
	public Iterator<LevelObject> getLevelObjects()
	{
		ArrayList<LevelObject> levelObjects = new ArrayList<LevelObject>();
		levelObjects.addAll(this.pickers);
		levelObjects.addAll(this.jewels);

		levelObjects.addAll(this.stuckedDaggers);

		levelObjects.addAll(this.hashGiratoryMechanisms.keySet());

		return levelObjects.iterator();
	}

	/**
	 * @return Ancho del mapa en unidades
	 */
	public int getMapWidthInUnits()
	{
		return mapWidthInUnits;
	}

	/**
	 * @return Alto del mapa en unidades
	 */
	public int getMapHeightInUnits()
	{
		return mapHeightInUnits;
	}

	/**
	 * @return Las joyas de la piramide
	 */
	public ArrayList<LevelObject> getJewels()
	{
		return jewels;
	}

	/**
	 * @return Los picos de la piramide
	 */
	public ArrayList<LevelObject> getPickers()
	{
		return pickers;
	}

	/**
	 * @return Las puertas giratorias
	 */
	public Collection<LevelObject> getGiratories()
	{
		return this.hashGiratoryMechanisms.keySet();
	}

	/**
	 * @return Los activadores de los muros trampa
	 */
	public ArrayList<LevelObject> getActivators()
	{
		return activators;
	}

	/**
	 * Activa un muro trampa de acuerdo al activador pasado por parametro
	 * 
	 * @param activator Asociado al muro trampa que se debe activar
	 */
	public void activateWall(LevelObject activator)
	{
		this.activators.remove(activator);
		LevelObject wall = this.hashTraps.get(activator);
		TrapMechanism trap = new TrapMechanism(this, wall, GameRules.getInstance().getTimeToEndTrapMechanism());
		this.trapMechanisms.add(trap);
	}

	/**
	 * Retorna el mecanismo giratorio asociado a la puerta giratoria pasada por
	 * parametro
	 * 
	 * @param giratory La puerta giratoria asociada al mecanismo requerido
	 * @return Mecanismo giratorio asociado a la puerta giratoria pasada por
	 *         parametro
	 */
	public GiratoryMechanism getGiratoryMechanism(LevelObject giratory)
	{
		return this.hashGiratoryMechanisms.get(giratory);
	}

	/**
	 * Retorna la celda correspondiente a las coordenadas cartesianas pasadas por
	 * parametro
	 * 
	 * @param x Coordenada x
	 * @param y Coordenada y
	 * @return Celda correspondiente a las coordenadas cartesianas pasadas por
	 *         parametro
	 */
	public TiledMapTileLayer.Cell getCell(float x, float y)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell((int) (x / GameRules.getInstance().getLevelTileWidthUnits()),
				(int) (y / GameRules.getInstance().getLevelTileHeightUnits()));
		return cell;
	}

	/**
	 * Retorna la celda correspondiente a las coordenadas matriciales pasadas por
	 * parametro
	 * 
	 * @param col Coordenada matricial (columna)
	 * @param row Coordenada matricial (fila)
	 * @return Celda correspondiente a las coordenadas matriciales pasadas por
	 *         parametro
	 */
	public TiledMapTileLayer.Cell getCellInTiledCoord(int col, int row)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell(col, row);
		return cell;
	}

	/**
	 * /** Retorna la celda correspondiente a las coordenadas cartesianas pasadas
	 * por parametros considerando un desplazamiento matricial
	 * 
	 * @param x         Coordenada x
	 * @param y         Coordenada y
	 * @param colOffset desplazamiento en columnas
	 * @param rowOffset desplazamiento en filas
	 * @return Celda correspondiente a las coordenadas y desplazamiento pasados por
	 *         parametro
	 */
	public TiledMapTileLayer.Cell getCell(float x, float y, int colOffset, int rowOffset)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) this.getMap().getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell(
				(int) (x / GameRules.getInstance().getLevelTileWidthUnits()) + colOffset,
				(int) (y / GameRules.getInstance().getLevelTileHeightUnits()) + rowOffset);
		return cell;
	}

	/**
	 * Indica si la celda pasada por parametro es picable (se puede picar)
	 * 
	 * @param celda Celda a evaluar
	 * @return true si es picable, false en caso contrario.
	 */
	public boolean isPickable(Cell celda)
	{
		boolean isBeginStair = (celda != null && (Constants.tilesPositiveStairs.contains(celda.getTile().getId())
				|| Constants.tilesNegativeStairs.contains(celda.getTile().getId())
				|| Constants.tilesPreviusToStairs.contains(celda.getTile().getId())));

		Iterator<LevelObject> itJewels = this.jewels.iterator();
		Cell cellWithItem = null;
		while (itJewels.hasNext() && cellWithItem != celda)
		{
			LevelObject item = (LevelObject) itJewels.next();
			cellWithItem = this.getCell(item.getX(), item.getY() - GameRules.getInstance().getLevelTileHeightUnits());
		}

		Iterator<Dagger> itDaggers = this.stuckedDaggers.iterator();

		while (itDaggers.hasNext() && cellWithItem != celda)
		{
			LevelObject item = (LevelObject) itDaggers.next();
			cellWithItem = this.getCell(item.getX(), item.getY() - GameRules.getInstance().getLevelTileHeightUnits());
		}

		return !this.unpickableCells.contains(celda) && !isBeginStair && cellWithItem != celda;
	}

	/**
	 * @return Los muros trampas activos de la piramide
	 */
	protected ArrayList<TrapMechanism> getTrapMechanisms()
	{
		return trapMechanisms;
	}

	/**
	 * @return Los mecanismos giratorios de la piramide
	 */
	protected Collection<GiratoryMechanism> getGiratoryMechanisms()
	{
		return this.hashGiratoryMechanisms.values();
	}

	/**
	 * @return Las dagas clavadas en el piso de la piramide
	 */
	public ArrayList<Dagger> getStuckedDaggers()
	{
		return stuckedDaggers;
	}

	/**
	 * Elimina el pico pasado por parametro
	 * 
	 * @param picker Pico que debera ser eliminado. Se invoca a
	 *               this.removeGraphicElement(new
	 *               DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, picker));
	 * 
	 */
	public void removePicker(LevelObject picker)
	{
		this.pickers.remove(picker);
		this.removeGraphicElement(new DrawableElement(Constants.DRAWABLE_LEVEL_ITEM, picker));

	}

	/**
	 * Elimina la joya pasada por parametro, se invoca a
	 * this.removeGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM,
	 * joya));
	 * 
	 * @param joya
	 */
	public void removeJewel(LevelObject joya)
	{
		this.jewels.remove(joya);
		this.removeGraphicElement(new DrawableElement(Constants.DRAWABLE_LEVEL_ITEM, joya));

	}

	/**
	 * Invoca a this.interfaz.addGraphicElement(element);
	 */
	@Override
	public void addGraphicElement(DrawableElement element)
	{
		this.interfaz.addGraphicElement(element);
	}

	/**
	 * Incova a this.interfaz.removeGraphicElement(element);
	 * 
	 */
	@Override
	public void removeGraphicElement(DrawableElement element)
	{
		this.interfaz.removeGraphicElement(element);

	}

	/**
	 * Elimina los mecanismos y las puertas giratorias. Llamado al recolectar todas
	 * las joyas.
	 */
	private void removeGiratories()
	{
		Iterator<LevelObject> it = this.hashGiratoryMechanisms.keySet().iterator();
		while (it.hasNext())
			this.removeGraphicElement(new DrawableElement(Constants.DRAWABLE_GYRATORY, it.next()));
		this.hashGiratoryMechanisms.clear();
	}

	/**
	 * @return Las escaleras con pendiente positiva
	 */
	public ArrayList<Stair> getPositiveStairs()
	{
		return positiveStairs;
	}

	/**
	 * @return Las escaleras con pendiente negativa
	 */

	public ArrayList<Stair> getNegativeStairs()
	{
		return negativeStairs;
	}

	

	/**
	 * @return Las puertas de entrada y salida de la piramide
	 */
	public ArrayList<Door> getDoors()
	{
		return doors;
	}

	/**
	 * Invoca a this.interfaz.reset();
	 */
	@Override
	public void inicialize()
	{
		this.interfaz.inicialize();
	}

	/**
	 * Llamado al recolectar todas las joyas, elimina las puertas giratorias y
	 * vuelve visibles las puertas de entrada y salida. Dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.PICKUP_ALL_JEWEL, this);
	 */
	public void prepareToExit()
	{
		this.removeGiratories();
		Iterator<Door> it = this.doors.iterator();
		while (it.hasNext())
		{
			Door door = it.next();
			door.setVisible();
		}
		Game.getInstance().eventFired(KVEventListener.PICKUP_ALL_JEWEL, this);
	}

	/**
	 * Llama a this.map.dispose();
	 */
	public void dispose()
	{
		this.map.dispose();
	}

	/**
	 * Invoca a return this.interfaz.getTimeToExitLevel();
	 */
	@Override
	public float getTimeToExitLevel()
	{
		return this.interfaz.getTimeToExitLevel();
	}

	/**
	 * Invoca a return this.interfaz.getTimeToEnterLevel();
	 */
	@Override
	public float getTimeToEnterLevel()
	{

		return this.interfaz.getTimeToEnterLevel();
	}

	/**
	 * Invoca a return this.interfaz.getTimeDying();
	 */
	@Override
	public float getTimeDying()
	{
		return this.interfaz.getTimeDying();
	}

	public ArrayList<Stair> getAllStairs()
	{
		return allStairs;
	}

	/**
	 * Invoca a return this.interfaz.getTimeToEndGame();
	 */
	@Override
	public float getTimeToEndGame()
	{

		return this.interfaz.getTimeToEndGame();
	}

}
