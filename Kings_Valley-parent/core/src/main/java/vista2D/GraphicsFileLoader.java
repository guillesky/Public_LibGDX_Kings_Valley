package vista2D;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import engine.gameCharacters.mummys.MummyFactory;
import engine.level.LevelObject;
import util.GameRules;
import util.Constants;

/**
 * @author Guillermo Lazzurri Clase encargada de cargar la configuracion de los
 *         elementos graicos del juego y generar las animaciones
 *         correspondientes
 */
@SuppressWarnings("unchecked")
public class GraphicsFileLoader
{
	private static final String CONFIG_FILE = "graphics_file_config.json";
	private static final Json json = new Json();
	private AssetManager manager;
	private Animation<TextureRegion>[] animationPlayer_Nothing = new Animation[6];
	private Animation<TextureRegion>[] animationPlayer_Dagger = new Animation[7];
	private Animation<TextureRegion>[] animationPlayer_Picker = new Animation[7];
	private Animation<TextureRegion>[] animationMummyBlue;
	private Animation<TextureRegion>[] animationMummyPink;
	private Animation<TextureRegion>[] animationMummyRed;
	private Animation<TextureRegion>[] animationMummyWhite;
	private Animation<TextureRegion>[] animationMummyYellow;

	private Array<TextureRegion> linearFramesGiratory;
	private GraphicsFileConfig graphicsFileConfig;
	private HashMap<Integer, Animation<TextureRegion>> collectablesAnimations = new HashMap<Integer, Animation<TextureRegion>>();
	private AnimatedPickedCell animatedPickedCell;
	private Texture doorSingleLeft;
	private Texture doorSingleRight;
	private Texture doorPassage;
	private Texture skyTexture;
	private Animation<TextureRegion> animationDoorLever;
	private boolean firstTime = true;
	private static Random random = new Random();

	/**
	 * Guarda los datos de un objecto de tipo GraphicsFileConfig pasado como
	 * paramentro en el archivo "graphics_file_config.json"
	 * 
	 * @param config Objeto de a guardar
	 */
	private static void saveConfig(GraphicsFileConfig config)
	{
		FileHandle file = Gdx.files.local(CONFIG_FILE);
		json.setUsePrototypes(false);
		file.writeString(json.prettyPrint(config), false);
	}

	/**
	 * @return Objeto con los parametros obtenidos a partir del archivo
	 *         "graphics_file_config.json"
	 */
	private static GraphicsFileConfig loadConfig()
	{
		FileHandle file = Gdx.files.local(CONFIG_FILE);
		if (file.exists())
		{
			return json.fromJson(GraphicsFileConfig.class, file);
		}
		return new GraphicsFileConfig(); // Valores por defecto
	}

	/**
	 * Constructor de clase
	 * 
	 * @param manager Manager encagado de la carga de los recursos
	 */
	public GraphicsFileLoader(AssetManager manager)
	{
		this.manager = manager;
		this.graphicsFileConfig = loadConfig();
		this.manager.load(graphicsFileConfig.getArchiPlayer(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiPlayerSpecial(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiCollectables(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiGiratory(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiPickingCell(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummys(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiFlyingDagger(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorLeft(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorRight(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorLever(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorPassage(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiSky(), Texture.class);
	}

	/**
	 * Crea las animaciones del player
	 * 
	 * @param timeDying Tiempo que tarda el player en morir
	 */
	private void createPlayerAnimations(float timeDying)
	{

		int startWalk = 0;
		int countWalk = this.graphicsFileConfig.getPlayerCountWalk();

		int startIddle = countWalk;
		int countIddle = this.graphicsFileConfig.getPlayerCountIddle();

		int startJump = startIddle + countIddle;
		int countJump = this.graphicsFileConfig.getPlayerCountJump();

		int startFall = startJump + countJump;
		int countFall = this.graphicsFileConfig.getPlayerCountFall();

		int startDeath = startFall + countFall;
		int countDeath = this.graphicsFileConfig.getPlayerCountDeath();

		int pickerStartWalk = startDeath + countDeath;
		int pickerCountWalk = this.graphicsFileConfig.getPlayerPickerCountWalk();

		int pickerStartIddle = pickerStartWalk + pickerCountWalk;
		int pickerCountIddle = this.graphicsFileConfig.getPlayerPickerCountIddle();

		int pickerStartFall = pickerStartIddle + pickerCountIddle;
		int pickerCountFall = this.graphicsFileConfig.getPlayerPickerCountFall();

		int pickerStartDeath = pickerStartFall + pickerCountFall;
		int pickerCountDeath = countDeath;

		int daggerStartWalk = pickerStartDeath + pickerCountDeath;
		int daggerCountWalk = this.graphicsFileConfig.getPlayerDaggerCountWalk();

		int daggerStartIddle = daggerStartWalk + daggerCountWalk;
		int daggerCountIddle = this.graphicsFileConfig.getPlayerDaggerCountIddle();

		int daggerStartFall = daggerStartIddle + daggerCountIddle;
		int daggerCountFall = this.graphicsFileConfig.getPlayerDaggerCountFall();

		int daggerStartDeath = daggerStartFall + daggerCountFall;
		int daggerCountDeath = countDeath;

		int daggerCountThrowing = this.graphicsFileConfig.getPlayerDaggerCountThrowing();

		int pickerCountPicking = this.graphicsFileConfig.getPlayerPickerCountPicking();

		int totalCount = daggerStartDeath + daggerCountDeath;

		float frameDuration = this.graphicsFileConfig.getFrameDuration();

		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPlayer(),
				totalCount);

		this.animationPlayer_Nothing[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, startIddle,
				countIddle, frameDuration);

		this.animationPlayer_Nothing[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, startFall, countFall,
				frameDuration);
		this.animationPlayer_Nothing[TileMapGrafica2D.JUMP] = this.framesToAnimation(linearFrames, startJump, countJump,
				frameDuration);
		this.animationPlayer_Nothing[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, startWalk, countWalk,
				.75f / (float) (countWalk));

		this.animationPlayer_Nothing[TileMapGrafica2D.DEATH] = this.framesToAnimation(linearFrames, startDeath,
				countDeath, timeDying / (float) countDeath);
		this.animationPlayer_Nothing[TileMapGrafica2D.DEATH].setPlayMode(PlayMode.NORMAL);

		this.animationPlayer_Picker[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, pickerStartIddle,
				pickerCountIddle, frameDuration);
		this.animationPlayer_Picker[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, pickerStartFall,
				pickerCountFall, frameDuration);

		this.animationPlayer_Picker[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, pickerStartWalk,
				pickerCountWalk, .75f / pickerCountWalk);

		this.animationPlayer_Picker[TileMapGrafica2D.DEATH] = this.framesToAnimation(linearFrames, pickerStartDeath,
				pickerCountDeath, timeDying / (float) countDeath);
		this.animationPlayer_Picker[TileMapGrafica2D.DEATH].setPlayMode(PlayMode.NORMAL);

		this.animationPlayer_Picker[TileMapGrafica2D.JUMP] = this.animationPlayer_Picker[TileMapGrafica2D.FALL];
		this.animationPlayer_Dagger[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, daggerStartIddle,
				daggerCountIddle, frameDuration);
		this.animationPlayer_Dagger[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, daggerStartFall,
				daggerCountFall, frameDuration);
		this.animationPlayer_Dagger[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, daggerStartWalk,
				daggerCountWalk, .75f / pickerCountWalk);

		this.animationPlayer_Dagger[TileMapGrafica2D.DEATH] = this.framesToAnimation(linearFrames, daggerStartDeath,
				daggerCountDeath, timeDying / (float) countDeath);
		this.animationPlayer_Dagger[TileMapGrafica2D.DEATH].setPlayMode(PlayMode.NORMAL);
		this.animationPlayer_Dagger[TileMapGrafica2D.JUMP] = this.animationPlayer_Dagger[TileMapGrafica2D.FALL];

		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPlayerSpecial(),
				daggerCountThrowing + pickerCountPicking);
		this.animationPlayer_Dagger[TileMapGrafica2D.THROW_DAGGER] = this.framesToAnimation(linearFrames,
				pickerCountPicking, daggerCountThrowing, GameRules.getInstance().getTimeToEndThrowDagger() / 8);
		this.animationPlayer_Dagger[TileMapGrafica2D.THROW_DAGGER].setPlayMode(PlayMode.NORMAL);
		float framePickingDuration = GameRules.getInstance().getTimeToEndPicking() / (float) (pickerCountPicking * 4);

		this.animationPlayer_Picker[TileMapGrafica2D.PICKING] = this.framesToAnimation(linearFrames, 0,
				pickerCountPicking, framePickingDuration);
		this.animationPlayer_Picker[TileMapGrafica2D.PICKING].setPlayMode(PlayMode.LOOP);
	}

	/**
	 * Genera una animacion a partir de un array linear de texturas
	 * 
	 * @param linearFrames  Array lineal de texturas
	 * @param init          frame inicial de la animacion
	 * @param count         cantidad de frame a incluir
	 * @param frameDuration duracion de cada frame
	 * @return La animacion generada a partir de los parametros proporcionados
	 */
	private Animation<TextureRegion> framesToAnimation(Array<TextureRegion> linearFrames, int init, int count,
			float frameDuration)
	{

		Array<TextureRegion> frames = new Array<>();
		for (int j = init; j < init + count; j++)
		{
			frames.add(linearFrames.get(j));
		}
		Animation<TextureRegion> animation = new Animation<TextureRegion>(frameDuration, frames);
		animation.setPlayMode(PlayMode.LOOP);
		return animation;

	}

	/**
	 * Retorna un array lineal de texturas a partir de un archivo
	 * 
	 * @param file            Archivo con las texturas en mosaico
	 * @param horizontalCount cantidad de tiles horizontalmente
	 * @param verticalCount   cantidad de tiles verticalmente
	 * @return El array lineal de texturas a partir de los aprametros proporcionados
	 */
	private Array<TextureRegion> linearFramesForFile(String file, int horizontalCount, int verticalCount)
	{

		Texture spriteSheet = manager.get(file, Texture.class);
		int width = spriteSheet.getWidth() / horizontalCount;
		int height = spriteSheet.getHeight() / verticalCount;
		TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet, width, height);
		Array<TextureRegion> linearFrames = new Array<>();

		// Convertimos la matriz 2D a una lista de frames 1D
		for (int i = 0; i < tmpFrames.length; i++)
		{
			for (int j = 0; j < tmpFrames[i].length; j++)
			{
				linearFrames.add(tmpFrames[i][j]);
			}
		}

		return linearFrames;
	}

	/**
	 * Crea las animaciones de las joyas, picos y espadas clavadas en el piso
	 */
	private void createColectablesAnimations()
	{

		int collectableCount = this.graphicsFileConfig.getCollectableCount();
		float maxFrameDuration = this.graphicsFileConfig.getFrameDuration();

		float minFrameDuration = maxFrameDuration * 0.7f;

		int flyingDaggerCount = this.graphicsFileConfig.getFlyingDaggerCount();
		int pickingCellCount = this.graphicsFileConfig.getPickingCellCount();
		float pickingCellFrameDuration = 1.0f / (float) pickingCellCount;

		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiCollectables(),
				collectableCount, 9);
		this.collectablesAnimations.put(Constants.IT_DAGGER, this.framesToAnimation(linearFrames, 0, collectableCount,
				this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.IT_PICKER, this.framesToAnimation(linearFrames, collectableCount,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));

		this.collectablesAnimations.put(Constants.JEWEL_1, this.framesToAnimation(linearFrames, collectableCount * 2,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.JEWEL_2, this.framesToAnimation(linearFrames, collectableCount * 3,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.JEWEL_3, this.framesToAnimation(linearFrames, collectableCount * 4,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.JEWEL_4, this.framesToAnimation(linearFrames, collectableCount * 5,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.JEWEL_5, this.framesToAnimation(linearFrames, collectableCount * 6,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.JEWEL_6, this.framesToAnimation(linearFrames, collectableCount * 7,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.collectablesAnimations.put(Constants.JEWEL_7, this.framesToAnimation(linearFrames, collectableCount * 8,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));

		this.linearFramesGiratory = this.linearFramesForFile(this.graphicsFileConfig.getArchiGiratory(),
				this.graphicsFileConfig.getGiratoryCount());
		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPickingCell(), pickingCellCount);
		Animation<TextureRegion> picking_cell = this.framesToAnimation(linearFrames, 0, pickingCellCount,
				pickingCellFrameDuration);
		picking_cell.setPlayMode(PlayMode.NORMAL);

		this.animatedPickedCell = new AnimatedPickedCell(new LevelObject(0, 0, 0, 0,
				GameRules.getInstance().getLevelTileWidthUnits(), GameRules.getInstance().getLevelTileHeightUnits()),
				picking_cell);

		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiFlyingDagger(), flyingDaggerCount);

		this.collectablesAnimations.put(Constants.DRAWABLE_FLYING_DAGGER,
				this.framesToAnimation(linearFrames, 0, flyingDaggerCount, maxFrameDuration * 0.5f));

	}

	/**
	 * Retorna un array lineal de texturas desde un archivo con las texturas en una
	 * sola linea
	 * 
	 * @param file  Archivo de texturas
	 * @param count cantidad de tiles en el archivo
	 * @return array lineal con las texturas del archivo
	 */
	private Array<TextureRegion> linearFramesForFile(String file, int count)
	{

		return linearFramesForFile(file, count, 1);
	}

	/**
	 * Crea las animaciones de las momias
	 */
	@SuppressWarnings("unused")
	private void createMummyAnimations()
	{

		int mummyCountAppear = this.graphicsFileConfig.getMummyCountAppear();
		int mummyCountWalk = this.graphicsFileConfig.getMummyCountWalk();
		int mummyCountIddle = this.graphicsFileConfig.getMummyCountIddle();
		int mummyCountFall = this.graphicsFileConfig.getMummyCountFall();
		int mummyCountJump = this.graphicsFileConfig.getMummyCountJump();
		int mummyCountDeath = this.graphicsFileConfig.getMummyCountDeath();

		int mummyStartWalk = mummyCountAppear;
		int mummyStartIddle = mummyStartWalk + mummyCountWalk;
		int mummyStartFall = mummyStartIddle + mummyCountIddle;
		int mummyStartJump = mummyStartFall + mummyCountFall;
		int mummyStartDeath = mummyStartJump + mummyCountJump;
		int mummyTotalCount = mummyStartDeath + mummyCountDeath;
		float frameAppearingDuration = GameRules.getInstance().getMummyTimeAppearing() / (float) mummyCountAppear;
		float frameDeathDuration = GameRules.getInstance().getMummyTimeDying() / (float) mummyCountDeath;

		float frameDuration = this.graphicsFileConfig.getFrameDuration();
		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiMummys(),
				mummyTotalCount, 5);

		for (int mummyType = 0; mummyType < 5; mummyType++)
		{

			float[] mummyParameters = null;
			switch (mummyType)
			{
			case MummyFactory.WHITE_MUMMY:
				mummyParameters = GameRules.getInstance().getMummyWhiteParameters();
				break;
			case MummyFactory.PINK_MUMMY:
				mummyParameters = GameRules.getInstance().getMummyPinkParameters();
				break;
			case MummyFactory.YELLOW_MUMMY:
				mummyParameters = GameRules.getInstance().getMummyYellowParameters();
				break;
			case MummyFactory.BLUE_MUMMY:
				mummyParameters = GameRules.getInstance().getMummyBlueParameters();
				break;
			case MummyFactory.RED_MUMMY:
				mummyParameters = GameRules.getInstance().getMummyRedParameters();
				break;

			}
			float speedMummyWalk = mummyParameters[GameRules.INDEX_SPEED_WALK];
			float speedPlayerWalk = GameRules.getInstance().getPlayerSpeedWalk();
			float factorFrameWalk = speedPlayerWalk / speedMummyWalk;
			float frameWalkDuration = factorFrameWalk * .75f / (float) mummyCountWalk;

			Animation<TextureRegion>[] animationMummy = new Animation[7];

			animationMummy[TileMapGrafica2D.APPEAR] = this.framesToAnimation(linearFrames, mummyType * mummyTotalCount,
					mummyCountAppear, frameAppearingDuration);
			animationMummy[TileMapGrafica2D.APPEAR].setPlayMode(PlayMode.NORMAL);

			animationMummy[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames,
					mummyType * mummyTotalCount + mummyStartWalk, mummyCountWalk, frameWalkDuration);

			animationMummy[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames,
					mummyType * mummyTotalCount + mummyStartIddle, mummyCountIddle, frameDuration);
			animationMummy[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames,
					mummyType * mummyTotalCount + mummyStartFall, mummyCountFall, frameDuration);

			animationMummy[TileMapGrafica2D.JUMP] = this.framesToAnimation(linearFrames,
					mummyType * mummyTotalCount + mummyStartJump, mummyCountJump, frameDuration);
			animationMummy[TileMapGrafica2D.DEATH] = this.framesToAnimation(linearFrames,
					mummyType * mummyTotalCount + mummyStartDeath, mummyCountDeath, frameDeathDuration);
			animationMummy[TileMapGrafica2D.DEATH].setPlayMode(PlayMode.NORMAL);

			switch (mummyType)
			{
			case MummyFactory.WHITE_MUMMY:
				this.animationMummyWhite = animationMummy;
				break;
			case MummyFactory.PINK_MUMMY:
				this.animationMummyPink = animationMummy;
				break;
			case MummyFactory.YELLOW_MUMMY:
				this.animationMummyYellow = animationMummy;
				break;
			case MummyFactory.BLUE_MUMMY:
				this.animationMummyBlue = animationMummy;
				break;
			case MummyFactory.RED_MUMMY:
				this.animationMummyRed = animationMummy;
				break;

			}
		}

	}

	/**
	 * Crea todas las animaciones
	 * 
	 * @param timeDying tiempo que tarda en morir el player
	 */
	public void createAnimations(float timeDying)
	{
		if (this.firstTime)
		{
			this.manager.finishLoading();
			this.createPlayerAnimations(timeDying);
			this.createColectablesAnimations();

			float frameDuration = this.graphicsFileConfig.getFrameDuration();
			this.createMummyAnimations();

			this.doorSingleLeft = manager.get(this.graphicsFileConfig.getArchiDoorLeft(), Texture.class);
			this.doorSingleRight = manager.get(this.graphicsFileConfig.getArchiDoorRight(), Texture.class);
			this.doorPassage = manager.get(this.graphicsFileConfig.getArchiDoorPassage(), Texture.class);
			this.skyTexture = manager.get(this.graphicsFileConfig.getArchiSky(), Texture.class);
			Array<TextureRegion> linearFrame = this.linearFramesForFile(this.graphicsFileConfig.getArchiDoorLever(),
					this.graphicsFileConfig.getDoorLeverCount());

			this.animationDoorLever = this.framesToAnimation(linearFrame, 0,
					this.graphicsFileConfig.getDoorLeverCount(), frameDuration);

		}

	}

	/**
	 * @return el array de animaciones del player sin portar objetos
	 */
	public Animation<TextureRegion>[] getAnimationPlayer_Nothing()
	{
		return animationPlayer_Nothing;
	}

	/**
	 * @return el array de animaciones del player portando una espada
	 */
	public Animation<TextureRegion>[] getAnimationPlayer_Dagger()
	{
		return animationPlayer_Dagger;
	}

	/**
	 * @return el array de animaciones del player portando un pico
	 */
	public Animation<TextureRegion>[] getAnimationPlayer_Picker()
	{
		return animationPlayer_Picker;
	}

	/**
	 * @return El array de animaciones de la momia azul
	 */
	public Animation<TextureRegion>[] getAnimationMummyBlue()
	{
		return animationMummyBlue;
	}

	/**
	 * @return El array de animaciones de la momia rosa
	 */
	public Animation<TextureRegion>[] getAnimationMummyPink()
	{
		return animationMummyPink;
	}

	/**
	 * @return El array de animaciones de la momia roja
	 */
	public Animation<TextureRegion>[] getAnimationMummyRed()
	{
		return animationMummyRed;
	}

	/**
	 * @return El array de animaciones de la momia blanca
	 */
	public Animation<TextureRegion>[] getAnimationMummyWhite()
	{
		return animationMummyWhite;
	}

	/**
	 * @return El array de animaciones de la momia amarilla
	 */
	public Animation<TextureRegion>[] getAnimationMummyYellow()
	{
		return animationMummyYellow;
	}

	/**
	 * @return HashMap con las animaciones de las joyas, picos y espadas clavadas en
	 *         el piso
	 */
	public HashMap<Integer, Animation<TextureRegion>> getCollectablesAnimations()
	{
		return collectablesAnimations;
	}

	/**
	 * @return La animacion correspondiente a la celda siendo picada
	 */
	public AnimatedPickedCell getAnimatedPickedCell()
	{
		return animatedPickedCell;
	}

	/**
	 * Retorna el array con las animaciones de la momia pasada por parametro
	 * 
	 * @param Codigo de color de la momia. puede tomar los valores:<br>
	 *               MummyFactory.WHITE_MUMMY = 0;<br>
	 *               MummyFactory.PINK_MUMMY = 1;<br>
	 *               MummyFactory.YELLOW_MUMMY = 2;<br>
	 *               MummyFactory.BLUE_MUMMY = 3;<br>
	 *               MummyFactory.RED_MUMMY = 4;<br>
	 * @return El array con las animaciones de la momia pasada por parametro
	 */
	public Animation<TextureRegion>[] getAnimationMummyByColor(int color)
	{
		Animation<TextureRegion>[] respuesta = null;
		switch (color)
		{
		case MummyFactory.BLUE_MUMMY:
			respuesta = this.animationMummyBlue;
			break;
		case MummyFactory.PINK_MUMMY:
			respuesta = this.animationMummyPink;
			break;
		case MummyFactory.RED_MUMMY:
			respuesta = this.animationMummyRed;
			break;
		case MummyFactory.WHITE_MUMMY:
			respuesta = this.animationMummyWhite;
			break;
		case MummyFactory.YELLOW_MUMMY:
			respuesta = this.animationMummyYellow;
			break;

		}
		return respuesta;
	}

	/**
	 * Retorna la animacion correspondiente a una puerta giratoria con la cantidad
	 * de tiles de alto pasado por parametro
	 * 
	 * @param heightInTiles Altura en tiles de la puerta
	 * @return Animacion correspondiente a una puerta giratoria con la cantidad de
	 *         tiles de alto pasado por parametro
	 */
	public Animation<TextureRegion> getNewAnimationGiratory(int heightInTiles)
	{
		float frameDuration = GameRules.getInstance().getTimeToEndGiratory()
				/ (float) this.graphicsFileConfig.getGiratoryCount();

		Array<TextureRegion> linearFrame = new Array<TextureRegion>();
		for (int i = 0; i < this.linearFramesGiratory.size; i++)
			linearFrame.add(this.generateVerticalTiledTextureRegion(this.linearFramesGiratory.get(i), heightInTiles));

		return this.framesToAnimation(linearFrame, 0, linearFrame.size, frameDuration);
	}

	/**
	 * Genera una textura repetida verticalmente en mosaico. Usada para las puertas
	 * giratorias.
	 * 
	 * @param tileRegion textura origen
	 * @param count      cantidad de repeteciones
	 * @return Una textura repetida verticalmente en mosaico.
	 */
	private TextureRegion generateVerticalTiledTextureRegion(TextureRegion tileRegion, int count)
	{
		Texture originalTexture = tileRegion.getTexture();
		int tileWidth = tileRegion.getRegionWidth();
		int tileHeight = tileRegion.getRegionHeight();

		TextureData textureData = originalTexture.getTextureData();
		if (!textureData.isPrepared())
			textureData.prepare();
		Pixmap originalPixmap = textureData.consumePixmap();

		Pixmap tiledPixmap = new Pixmap(tileWidth, tileHeight * count, originalPixmap.getFormat());

		for (int i = 0; i < count; i++)
		{
			tiledPixmap.drawPixmap(originalPixmap, 0, i * tileHeight, // destino
					tileRegion.getRegionX(), tileRegion.getRegionY(), // origen
					tileWidth, tileHeight);
		}

		Texture newTexture = new Texture(tiledPixmap);
		return new TextureRegion(newTexture);
	}

	/**
	 * @return Textura de la hoja izquierda de las puertas de entrada / salida
	 */
	public Texture getDoorSingleLeft()
	{
		return doorSingleLeft;
	}

	/**
	 * @return Textura de la hoja derecha de las puertas de entrada / salida
	 */

	public Texture getDoorSingleRight()
	{
		return doorSingleRight;
	}

	/**
	 * @return Textura del pasage de las puertas de entrada / salida
	 */

	public Texture getDoorPassage()
	{
		return doorPassage;
	}

	/**
	 * @return Animacion de la palanca de las puertas de entrada / salida
	 */
	public Animation<TextureRegion> getAnimationDoorLever()
	{
		return animationDoorLever;
	}

	/**
	 * @return Textura del cielo de fondo
	 */
	public Texture getSkyTexture()
	{
		return skyTexture;
	}

	/**
	 * Retorna un valor de duracion de frame al azar entre dos valores. Usado para
	 * que los coleccionables no se vean sincronizados entre ellos
	 * 
	 * @param minFrameDuration valor minimo de duracion de frame posible
	 * @param maxFrameDuration valor maximo de duracion de frame posible
	 * @return Un valor de duracion de frame al azar
	 */
	private float getRandomFrameDuration(float minFrameDuration, float maxFrameDuration)
	{
		float delta = maxFrameDuration - minFrameDuration;
		return minFrameDuration + random.nextFloat() * delta;
	}

	/**
	 * Llama a this.manager.dispose();
	 */
	public void dispose()
	{
		this.manager.dispose();
	}

}
