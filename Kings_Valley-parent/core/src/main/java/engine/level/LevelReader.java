package engine.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import engine.IGraphic;
import engine.game.Game;
import engine.gameCharacters.mummys.Mummy;
import engine.gameCharacters.mummys.MummyFactory;
import engine.gameCharacters.player.Player;
import engine.level.dagger.Dagger;
import engine.level.door.Door;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import util.GameRules;
import util.Constants;

/**
 * @author Guillermo Lazzurri Contiene un metodo publico para obtener un nivel
 *         completo a partir de un archivo de tipo TMX del programa Tiled
 */
public class LevelReader
{

	private ArrayList<Door> doors = null;

	private ArrayList<LevelObject> jewels = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> pickers = new ArrayList<LevelObject>();
	private ArrayList<Dagger> stuckedDaggers = new ArrayList<Dagger>();
	private ArrayList<LevelObject> walls = new ArrayList<LevelObject>();
	private ArrayList<LevelObject> activators = new ArrayList<LevelObject>();
	private ArrayList<Cell> unpickableCells = new ArrayList<Cell>();
	private HashMap<LevelObject, LevelObject> hashTraps = new HashMap<LevelObject, LevelObject>();
	private HashMap<LevelObject, GiratoryMechanism> hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
	private Pyramid pyramid;
	private ArrayList<Mummy> mummys = new ArrayList<Mummy>();
	private MummyFactory mummyFactory = new MummyFactory();
	private ArrayList<MummyData> mummyDatas = new ArrayList<MummyData>();
	private ArrayList<Stair> positiveStairs;
	private ArrayList<Stair> negativeStairs;
	private TmxMapLoader mapLoader = new TmxMapLoader();

	/**
	 * Devuelve un nivel completo a partir de un archivo de tipo TMX del programa
	 * Tiled de acuerdo a los parametros. <br>
	 * <b>PRE: </b>El archivo contiene objetos consistes con las reglas del juego.
	 * (por ejemplo cada trampa tiene un solo un activador), las puertas de salida
	 * de este nivel deben ser consistentes con las puertas en los niveles a los que
	 * conecta.
	 * 
	 * @param id            Numero identificatorio del nivel
	 * @param mapFile       Nombre del archivo a leer
	 * @param dificultLevel nivel de dificultad requerido.<br>
	 *                      Modificara el tipo de momia.<br>
	 *                      Si es cero las momias seran las que indique el archivo
	 *                      sin ninguna modificacion<br>
	 *                      Si es un numero positivo incrementara la dificultad de
	 *                      la momia (if (dificultLevel &gt; 4) dificultLevel=4; )<br>
	 *                      Si es un numero negativo se decremnetara la dificultad
	 *                      de la momia (if (dificultLevel &lt;-4) dificultLevel=-4;
	 *                      )<br>
	 * @param isCompleted   true si el nivel ya fue completado anteriomente (en caso
	 *                      de haber vuelto de otro nivel), false en caso contrario.
	 *                      Si el nivel ya fue completado las puertas giratorias y
	 *                      las gemas no aparecen y las puertas de salida estan
	 *                      siempre visibles.
	 * @param doorFrom      Indica la puerta de origen, es decir, la puerta por la
	 *                      que el player salio del nivel que termino. En caso de no
	 *                      venir de un nivel anterior o de morir, este paremtro
	 *                      podria ser null.
	 * @param fromDeath     true si el player murio y esta reviviendo, false en caso
	 *                      contrario. Si el player esta reviviendo, lo hara en la
	 *                      puerta por la cual entro al nivel.
	 * @param interfaz      Objeto que implementa la interfaz IGraphic y que se
	 *                      pasara por parametro a la piramide.
	 * @return Objeto de tipo Level coherente con los parametros.
	 */
	public Level getLevel(int id, String mapFile, int dificultLevel, boolean isCompleted, Door doorFrom,
			boolean fromDeath, IGraphic interfaz)
	{
		TiledMap map = this.mapLoader.load(mapFile);
		this.resetAll();
		this.readLevelObjects(map, id);
		this.readStairs(map);
		if (isCompleted)
		{
			this.jewels.clear();
			this.hashGiratoryMechanisms.clear();
		}
		this.pyramid = new Pyramid(map, doors, jewels, positiveStairs, negativeStairs, pickers, stuckedDaggers,
				 unpickableCells, hashTraps,
				hashGiratoryMechanisms, interfaz);

		if (isCompleted)
			this.pyramid.prepareToExit();
		Door door;
		if (fromDeath)
			door = Game.getInstance().getCurrentLevel().getDoorIn();
		else
			door = this.getDoorIn(id, doorFrom);
		float y = door.getPassage().y;
		float x = door.getPassage().x;

		Player player = new Player(x, y, this.pyramid);
		this.generateMummys(player, dificultLevel);

		Level level = new Level(id, pyramid, mummys, door, player);
		return level;

	}

	

	/**
	 * Llamado internamente por getLevel<br>
	 * Retorna la puerta en la que debe aparecer el player de acuerdo al id del
	 * nivel y a la puerta de origen del player. <br>
	 * <b>PRE: </b>EL nivel contiene una puerta consistente con los parametros
	 * indicados.
	 * 
	 * @param id       indica cual es el id del nivel que se quiere crear.
	 * @param doorFrom Indica la puerta de origen del player. Este parametro podria
	 *                 ser null.
	 * @return Retorna la puerta en la que debe aparecer el player.<br>
	 *         if (doorFrom == null || doorFrom.getLevelConnected() == Door.TO_NEXT
	 *         || doorFrom.getLevelConnected() == Door.UNIQUE) door =
	 *         this.searchDoor(Door.TO_PREVIUS);<br>
	 * 
	 *         else if (doorFrom.getLevelConnected() == Door.TO_PREVIUS) door =
	 *         this.searchDoor(Door.TO_NEXT);<br>
	 *         else door = this.searchDoor(doorFrom.getIdLevel());
	 * 
	 */
	private Door getDoorIn(int id, Door doorFrom)
	{
		Door door = null;
		if (this.doors.size() == 1)
			door = this.doors.get(0);
		else
		{
			if (doorFrom == null || doorFrom.getLevelConnected() == Door.TO_NEXT
					|| doorFrom.getLevelConnected() == Door.UNIQUE)
				door = this.searchDoor(Door.TO_PREVIUS);

			else if (doorFrom.getLevelConnected() == Door.TO_PREVIUS)
				door = this.searchDoor(Door.TO_NEXT);
			else
				door = this.searchDoor(doorFrom.getIdLevel());
		}
		return door;
	}

	/**
	 * Llamado internamente por getDoorIn<br>
	 * Retorna la puerta que conecta al nivel anterior, o al nivel siguiente, o a un
	 * nivel de atajo (version extendida de Kings Valley) <br>
	 * <b>PRE: </b>EL nivel contiene una puerta consistente con los parametros
	 * indicados.
	 * 
	 * @param value Puede tomar los valores Door.TO_PREVIUS, Door.TO_NEXT, o bien un
	 *              valor entero que debe ser consistente
	 * @return Retorna la puerta que coincide con los valores requeridos.
	 */
	private Door searchDoor(int value)
	{

		Iterator<Door> it = this.doors.iterator();
		Door respuesta = null;
		Door door;
		do
		{
			door = it.next();
		} while (it.hasNext() && door.getLevelConnected() != value);

		if (door.getLevelConnected() == value)
		{
			respuesta = door;
		}

		return respuesta;
	}

	/**
	 * Llamado internamente por getLevel<br>
	 * Crea las listas de escaleras a partir del mapa pasado por parametro
	 * 
	 * @param map Objeto de tipo map leido dede el archivo
	 */
	private void readStairs(TiledMap map)
	{

		int mapWidthInTiles = map.getProperties().get("width", Integer.class);
		int mapHeightInTiles = map.getProperties().get("height", Integer.class);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("front");
		Cell cell;
		for (int i = 0; i < mapHeightInTiles; i++)
			for (int j = 0; j < mapWidthInTiles; j++)
			{
				cell = layer.getCell(j, i);
				if (cell != null)
				{
					int value = cell.getTile().getId();
					if (Constants.tilesPositiveStairs.contains(value))
					{
						this.generateStair(map, j, i, true);

					} else if (Constants.tilesNegativeStairs.contains(value))
					{
						this.generateStair(map, j, i, false);

					}
				}
			}

	}

	/**
	 * Llamado internamente por readStairs<br>
	 * Genera la escalera a parir de su parte superior hasta que encuentra una celda
	 * que no es escalera en la capa de escaleras. Luego la agrega a la coleccion de
	 * escaleras correspondiente
	 * 
	 * @param map        Mapa a evaluar
	 * @param j          Indica la columna (coordenada matricial) de la cabecera de
	 *                   la escalera
	 * @param i          Indica la fila (coordenada matricial) de la cabecera de la
	 *                   escalera
	 * @param isPositive true si la escalera tiene pendiente positiva (sube hacia la
	 *                   derecha), false si tiene pendiente negativa (sube hacia la
	 *                   izquierda)
	 */
	private void generateStair(TiledMap map, int j, int i, boolean isPositive)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("stairs");

		float xUpStair;
		float yUpStair;
		float xDownStair;
		float yDownStair;
		int down;
		int up;
		int sign;
		float incX = 0;

		i++;
		if (isPositive)
		{
			up = Constants.STAIR_DL;
			down = Constants.STAIR_UR;
			sign = -1;
		} else
		{
			up = Constants.STAIR_DR;
			down = Constants.STAIR_UL;
			sign = 1;
			incX = GameRules.getInstance().getLevelTileWidthUnits() - GameRules.getInstance().getStairWidth();
		}

		xUpStair = j * GameRules.getInstance().getLevelTileWidthUnits() + incX;
		yUpStair = i * GameRules.getInstance().getLevelTileHeightUnits();

		do
		{
			i--;
			j += sign;
		} while (layer.getCell(j + sign, i - 1) != null);

		xDownStair = j * GameRules.getInstance().getLevelTileWidthUnits() + incX;
		yDownStair = i * GameRules.getInstance().getLevelTileHeightUnits();

		LevelObject upStair = new LevelObject(Constants.IT_STAIR, xUpStair, yUpStair, up,
				GameRules.getInstance().getStairWidth(), GameRules.getInstance().getStairHeight());
		LevelObject downStair = new LevelObject(Constants.IT_STAIR, xDownStair, yDownStair, down,
				GameRules.getInstance().getStairWidth(), GameRules.getInstance().getStairHeight());
		Stair stair = new Stair(downStair, upStair);

		if (isPositive)
			this.positiveStairs.add(stair);
		else
			this.negativeStairs.add(stair);
	}

	/**
	 * Llamado internamente por getLevel<br>
	 * Crea nuevamente todas las listas de objetos.
	 * 
	 */
	private void resetAll()
	{

		this.doors = new ArrayList<Door>();
		this.jewels = new ArrayList<LevelObject>();
		this.pickers = new ArrayList<LevelObject>();
		this.stuckedDaggers = new ArrayList<Dagger>();
		this.walls = new ArrayList<LevelObject>();
		this.activators = new ArrayList<LevelObject>();
		this.unpickableCells = new ArrayList<Cell>();
		this.hashTraps = new HashMap<LevelObject, LevelObject>();
		this.hashGiratoryMechanisms = new HashMap<LevelObject, GiratoryMechanism>();
		this.pyramid = null;
		this.mummys = new ArrayList<Mummy>();
		this.positiveStairs = new ArrayList<Stair>();
		this.negativeStairs = new ArrayList<Stair>();
		this.mummyDatas = new ArrayList<MummyData>();
	}

	/**
	 * Llamado internamente por getLevel<br>
	 * Lee todos los objetos del nivel (Dagas, picos, gemas, momias, giratorias,
	 * trampas, palancas de puertas)
	 * 
	 * @param map Mapa origen de los datos
	 * @param id  numero identificatorio de nivel (necesario para crear las puertas
	 *            de salida)
	 */
	private void readLevelObjects(TiledMap map, int id)
	{
		MapLayers mapLayers = map.getLayers();
		MapLayer layerObject = mapLayers.get("items");
		for (int i = 0; i < layerObject.getObjects().getCount(); i++)
		{
			MapObject objeto = layerObject.getObjects().get(i);
			MapProperties mp = objeto.getProperties();
			String stype = (String) mp.get("type");
			float fx = (float) mp.get("x");
			float fy = (float) mp.get("y");
			String sp0 = (String) mp.get("p0");

			int type = Constants.stringToInteger.get(stype);

			int p0 = Integer.parseInt(sp0);

			float width = GameRules.getInstance().getLevelObjectWidth();
			float height = GameRules.getInstance().getLevelObjectHeight();
			if (type == Constants.IT_STAIR)
			{
				width = GameRules.getInstance().getStairWidth();
				height = GameRules.getInstance().getStairHeight();
			} else if (type == Constants.IT_GIRATORY)
			{
				width = GameRules.getInstance().getGiratoryWidth();
				int contador = 2;
				Cell cell = this.getCell(map, fx, fy + GameRules.getInstance().getLevelTileHeightUnits() * contador);
				while (cell == null)
				{
					contador++;
					cell = this.getCell(map, fx, fy + GameRules.getInstance().getLevelTileHeightUnits() * contador);

				}
				height = GameRules.getInstance().getLevelTileHeightUnits() * contador;
				this.unpickableCells.add(cell);

			}

			LevelObject levelObject;
			switch (type)
			{
			case Constants.IT_MUMMY:
				this.mummyDatas.add(new MummyData(fx, fy, p0));

				break;
			case Constants.IT_DOOR_LEVER:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);
				Door door = new Door(levelObject, id, GameRules.getInstance().getTimeToOpenCloseDoor());
				this.doors.add(door);

				break;
			case Constants.IT_JEWEL:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);
				this.jewels.add(levelObject);
				break;
			case Constants.IT_PICKER:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);
				this.pickers.add(levelObject);
				break;

			case Constants.IT_DAGGER:

				this.stuckedDaggers.add(new Dagger(fx, fy, p0, width, height));
				break;

			case Constants.IT_GIRATORY:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);

				GiratoryMechanism giratoryMechanism = new GiratoryMechanism(levelObject,
						GameRules.getInstance().getTimeToEndGiratory());
				this.hashGiratoryMechanisms.put(levelObject, giratoryMechanism);
				this.unpickableCells.add(this.getCell(map, levelObject.getX(),
						levelObject.getY() - GameRules.getInstance().getLevelTileHeightUnits()));

				break;
			case Constants.IT_WALL_TRAP:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);

				this.walls.add(levelObject);
				this.unpickableCells.add(this.getCell(map, levelObject.getX(), levelObject.getY()));
				break;

			case Constants.IT_TRAP_ACTIVATOR:
				levelObject = new LevelObject(type, fx, fy, p0, width, height);

				this.activators.add(levelObject);
				break;

			}

		}
		this.createTraps();
	}

	/**
	 * Llamado internamente por readLevelObjects<br>
	 * Arma las paredes trampa de acuerdo a los objetos de tipo Constantes.It_wall y
	 * Constantes.It_activator contenidos en el mapa
	 */
	private void createTraps()
	{
		Iterator<LevelObject> itActivators = this.activators.iterator();
		while (itActivators.hasNext())
		{
			LevelObject activator = itActivators.next();
			Iterator<LevelObject> itWalls = this.walls.iterator();
			LevelObject wall = null;
			if (itWalls.hasNext())
				do
				{
					wall = itWalls.next();
				} while (itWalls.hasNext() && wall.getP0() != activator.getP0());

			if (wall.getP0() == activator.getP0())
			{
				this.hashTraps.put(activator, wall);
			}
		}

	}

	/**
	 * Llamado internamente por el metodo readLevelObjects. Retorna la celda (objeto
	 * Cell) del mapa pasado por parametro en las coordenadas requeridas por
	 * parametro
	 * 
	 * @param map Mapa del que se quiere obtener la celda
	 * @param x   coordenada x
	 * @param y   coordenada y
	 * @return la celda del mapa en las coordenadas indicadas
	 */
	private TiledMapTileLayer.Cell getCell(TiledMap map, float x, float y)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell((int) (x / GameRules.getInstance().getLevelTileWidthUnits()),
				(int) (y / GameRules.getInstance().getLevelTileHeightUnits()));
		return cell;
	}

	/**
	 * Llamado internamente por getLevel<br>
	 * Genera todas las momias de acuerdo al nivel de dificultad pasado por
	 * parametro.
	 * 
	 * @param player        El Player al cual deberan perseguir las momias
	 * @param dificultLevel Nivel de dificultad que modifica el tipo de momia por
	 *                      defecto
	 */
	private void generateMummys(Player player, int dificultLevel)
	{
		Iterator<MummyData> it = this.mummyDatas.iterator();
		while (it.hasNext())
		{
			MummyData mummyData = it.next();
			int mummyType = dificultLevel + mummyData.getType();
			if (mummyType > MummyFactory.RED_MUMMY)
				mummyType = MummyFactory.RED_MUMMY;
			if (mummyType < MummyFactory.WHITE_MUMMY)
				mummyType = MummyFactory.WHITE_MUMMY;

			Mummy mummy = this.mummyFactory.getMummy(mummyData.getX(), mummyData.getY(), mummyType, this.pyramid,
					player);
			this.mummys.add(mummy);
		}
	}

}
