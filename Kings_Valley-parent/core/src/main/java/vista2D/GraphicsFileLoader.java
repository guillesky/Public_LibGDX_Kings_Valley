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

import modelo.gameCharacters.mummys.MummyFactory;
import modelo.level.LevelObject;
import util.Config;
import util.Constantes;

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
	private Animation<TextureRegion> animationMummyAppear;
	private Animation<TextureRegion> animationMummyDeath;
	private Array<TextureRegion> linearFramesGiratory;
	private GraphicsFileConfig graphicsFileConfig;
	private HashMap<Integer, Animation<TextureRegion>> animations = new HashMap<Integer, Animation<TextureRegion>>();
	private AnimatedPickedCell animatedPickedCell;
	private Texture doorSingleLeft;
	private Texture doorSingleRight;
	private Texture doorPassage;
	private Texture skyTexture;
	private Animation<TextureRegion> animationDoorLever;
	private static Random random = new Random();

	public static void saveConfig(GraphicsFileConfig config)
	{
		FileHandle file = Gdx.files.local(CONFIG_FILE);
		json.setUsePrototypes(false);
		file.writeString(json.prettyPrint(config), false);
	}

	public static GraphicsFileConfig loadConfig()
	{
		FileHandle file = Gdx.files.local(CONFIG_FILE);
		if (file.exists())
		{
			return json.fromJson(GraphicsFileConfig.class, file);
		}
		return new GraphicsFileConfig(); // Valores por defecto
	}

	public GraphicsFileLoader(AssetManager manager)
	{
		this.manager = manager;
		this.graphicsFileConfig = loadConfig();
		// this.graphicsFileConfig = new GraphicsFileConfig();
		this.manager.load(graphicsFileConfig.getArchiPlayer(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiCollectables(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiGiratory(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiPickingCell(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyBlue(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyPink(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyRed(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyWhite(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyYellow(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyAppear(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyDisappear(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiFlyingDagger(), Texture.class);

		this.manager.load(graphicsFileConfig.getArchiDoorLeft(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorRight(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorLever(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiDoorPassage(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiNewTileset(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiSky(), Texture.class);
		// saveConfig(graphicsFileConfig);
	}

	public String getArchiNewTileset()
	{
		return this.graphicsFileConfig.getArchiNewTileset();
	}

	private void loadPlayerAnimations()
	{

		int startIddle = this.graphicsFileConfig.getPlayerStartIddle();
		int countIddle = this.graphicsFileConfig.getPlayerCountIddle();
		int startFall = this.graphicsFileConfig.getPlayerStartFall();
		int countFall = this.graphicsFileConfig.getPlayerCountFall();
		int startJump = this.graphicsFileConfig.getPlayerStartJump();
		int countJump = this.graphicsFileConfig.getPlayerCountJump();

		int startWalk = this.graphicsFileConfig.getPlayerStartWalk();
		int countWalk = this.graphicsFileConfig.getPlayerCountWalk();

		int pickerStartIddle = this.graphicsFileConfig.getPlayerPickerStartIddle();
		int pickerCountIddle = this.graphicsFileConfig.getPlayerPickerCountIddle();
		int pickerStartFall = this.graphicsFileConfig.getPlayerPickerStartFall();
		int pickerCountFall = this.graphicsFileConfig.getPlayerPickerCountFall();

		int pickerStartWalk = this.graphicsFileConfig.getPlayerPickerStartWalk();
		int pickerCountWalk = this.graphicsFileConfig.getPlayerPickerCountWalk();
		int pickerStartPicking = this.graphicsFileConfig.getPlayerPickerStartPicking();
		int pickerCountPicking = this.graphicsFileConfig.getPlayerPickerCountPicking();

		int daggerStartIddle = this.graphicsFileConfig.getPlayerDaggerStartIddle();
		int daggerCountIddle = this.graphicsFileConfig.getPlayerDaggerCountIddle();
		int daggerStartFall = this.graphicsFileConfig.getPlayerDaggerStartFall();
		int daggerCountFall = this.graphicsFileConfig.getPlayerDaggerCountFall();

		int daggerStartWalk = this.graphicsFileConfig.getPlayerDaggerStartWalk();
		int daggerCountWalk = this.graphicsFileConfig.getPlayerDaggerCountWalk();
		int daggerStartThrowing = this.graphicsFileConfig.getPlayerDaggerStartThrowing();
		int daggerCountThrowing = this.graphicsFileConfig.getPlayerDaggerCountThrowing();

		int startDeath = this.graphicsFileConfig.getPlayerStartDeath();
		int countDeath = this.graphicsFileConfig.getPlayerCountDeath();
		float frameDuration = this.graphicsFileConfig.getFrameDuration();

		int totalCount = 53;/*
							 * countIddle + countFall + countWalk + pickerCountIddle + pickerCountFall +
							 * pickerCountWalk + pickerCountPicking + daggerCountIddle + daggerCountFall +
							 * daggerCountWalk + daggerCountThrowing + countDeath;
							 */

		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPlayer(),
				totalCount);

		this.animationPlayer_Nothing[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, startIddle,
				countIddle, frameDuration);
		this.animationPlayer_Nothing[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, startFall, countFall,
				frameDuration);
		this.animationPlayer_Nothing[TileMapGrafica2D.JUMP] = this.framesToAnimation(linearFrames, startJump, countJump,
				frameDuration);
		this.animationPlayer_Nothing[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, startWalk, countWalk,
				.75f/countWalk);

		this.animationPlayer_Nothing[TileMapGrafica2D.DEATH] = this.framesToAnimation(linearFrames, startDeath,
				countDeath, frameDuration);

		this.animationPlayer_Picker[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, pickerStartIddle,
				pickerCountIddle, 0);
		this.animationPlayer_Picker[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, pickerStartFall,
				pickerCountFall, 0);

		this.animationPlayer_Picker[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, pickerStartWalk,
				pickerCountWalk, frameDuration);

		this.animationPlayer_Picker[TileMapGrafica2D.DEATH] = this.animationPlayer_Nothing[TileMapGrafica2D.DEATH];
		this.animationPlayer_Picker[TileMapGrafica2D.PICKING] = this.framesToAnimation(linearFrames, pickerStartPicking,
				pickerCountPicking, frameDuration);
		this.animationPlayer_Picker[TileMapGrafica2D.PICKING].setPlayMode(PlayMode.LOOP);
		this.animationPlayer_Picker[TileMapGrafica2D.JUMP] = this.animationPlayer_Picker[TileMapGrafica2D.FALL];
		this.animationPlayer_Dagger[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, daggerStartIddle,
				daggerCountIddle, 0);
		this.animationPlayer_Dagger[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, daggerStartFall,
				daggerCountFall, 0);
		this.animationPlayer_Dagger[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, daggerStartWalk,
				daggerCountWalk, frameDuration);

		this.animationPlayer_Dagger[TileMapGrafica2D.DEATH] = this.animationPlayer_Nothing[TileMapGrafica2D.DEATH];
		this.animationPlayer_Dagger[TileMapGrafica2D.JUMP] = this.animationPlayer_Dagger[TileMapGrafica2D.FALL];
		this.animationPlayer_Dagger[TileMapGrafica2D.THROW_DAGGER] = this.framesToAnimation(linearFrames,
				daggerStartThrowing, daggerCountThrowing, frameDuration);

	}

	private Animation<TextureRegion> framesToAnimation(Array<TextureRegion> linearFrames, int init, int count,
			float frameDuration)
	{

		Array<TextureRegion> frames = new Array<>();
		for (int j = init; j < init + count; j++)
		{
			frames.add(linearFrames.get(j));
		}

		return new Animation<TextureRegion>(frameDuration, frames);

	}

	private Array<TextureRegion> linearFramesForFile(String file, int width, int height)
	{

		Texture spriteSheet = manager.get(file, Texture.class);

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

	private void loadColectablesAnimations()
	{

		int collectableCount = this.graphicsFileConfig.getCollectableCount();
		float maxFrameDuration = this.graphicsFileConfig.getFrameDuration();

		float minFrameDuration = maxFrameDuration * 0.7f;

		int flyingDaggerCount = this.graphicsFileConfig.getFlyingDaggerCount();
		int pickingCellCount = this.graphicsFileConfig.getPickingCellCount();
		float pickingCellFrameDuration = 1.0f / (float) pickingCellCount;

		Texture spriteSheet = manager.get(this.graphicsFileConfig.getArchiCollectables(), Texture.class);
		int collectableWidth = spriteSheet.getWidth() / collectableCount;
		int collectableHeight = spriteSheet.getHeight() / 9;

		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiCollectables(),
				collectableWidth, collectableHeight);
		this.animations.put(Constantes.It_dagger, this.framesToAnimation(linearFrames, 0, collectableCount,
				this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.It_picker, this.framesToAnimation(linearFrames, collectableCount,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));

		this.animations.put(Constantes.JEWEL_1, this.framesToAnimation(linearFrames, collectableCount * 2,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.JEWEL_2, this.framesToAnimation(linearFrames, collectableCount * 3,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.JEWEL_3, this.framesToAnimation(linearFrames, collectableCount * 4,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.JEWEL_4, this.framesToAnimation(linearFrames, collectableCount * 5,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.JEWEL_5, this.framesToAnimation(linearFrames, collectableCount * 6,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.JEWEL_6, this.framesToAnimation(linearFrames, collectableCount * 7,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));
		this.animations.put(Constantes.JEWEL_7, this.framesToAnimation(linearFrames, collectableCount * 8,
				collectableCount, this.getRandomFrameDuration(minFrameDuration, maxFrameDuration)));

		this.linearFramesGiratory = this.linearFramesForFile(this.graphicsFileConfig.getArchiGiratory(),
				this.graphicsFileConfig.getGiratoryCount());
		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPickingCell(), pickingCellCount);
		Animation<TextureRegion> picking_cell = this.framesToAnimation(linearFrames, 0, pickingCellCount,
				pickingCellFrameDuration);
		picking_cell.setPlayMode(PlayMode.NORMAL);

		this.animatedPickedCell = new AnimatedPickedCell(new LevelObject(0, 0, 0, 0,
				Config.getInstance().getLevelTileWidthUnits(), Config.getInstance().getLevelTileHeightUnits()),
				picking_cell);

		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiFlyingDagger(), flyingDaggerCount);

		this.animations.put(Constantes.DRAWABLE_FLYING_DAGGER,
				this.framesToAnimation(linearFrames, 0, flyingDaggerCount, maxFrameDuration * 0.5f));

	}

	private Array<TextureRegion> linearFramesForFile(String file, int count)
	{
		Texture spriteSheet = manager.get(file, Texture.class);
		int width = spriteSheet.getWidth() / count;

		return linearFramesForFile(file, width, spriteSheet.getHeight());
	}

	private Animation<TextureRegion>[] loadMummyAnimations(String archiMummy)
	{
		int startIddle = this.graphicsFileConfig.getMummyStartIddle();
		int countIddle = this.graphicsFileConfig.getMummyCountIddle();
		int startFall = this.graphicsFileConfig.getMummyStartFall();
		int countFall = this.graphicsFileConfig.getMummyCountFall();

		int startWalk = this.graphicsFileConfig.getMummyStartWalk();
		int countWalk = this.graphicsFileConfig.getMummyCountWalk();

		float frameDuration = this.graphicsFileConfig.getFrameDuration();
		float mummyIddleFrameDuration = this.graphicsFileConfig.getMummyIddleFrameDuration();

		Animation<TextureRegion>[] animationMummy = new Animation[7];
		int countMummyFrames = 11;
		Array<TextureRegion> linearFrames = this.linearFramesForFile(archiMummy, countMummyFrames);

		animationMummy[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, startIddle, countIddle,
				mummyIddleFrameDuration);
		animationMummy[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, startFall, countFall,
				frameDuration);
		animationMummy[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, startWalk, countWalk,
				frameDuration);

		animationMummy[TileMapGrafica2D.JUMP] = animationMummy[TileMapGrafica2D.FALL];
		animationMummy[TileMapGrafica2D.DEATH] = this.animationMummyDeath;
		animationMummy[TileMapGrafica2D.APPEAR] = this.animationMummyAppear;

		return animationMummy;
	}

	public void loadAnimations()
	{

		this.loadPlayerAnimations();
		this.loadColectablesAnimations();
		int mummyCountAppear = this.graphicsFileConfig.getMummyCountAppear();
		int mummyCountDeath = this.graphicsFileConfig.getMummyCountDeath();

		float frameDuration = this.graphicsFileConfig.getFrameDuration();
		Array<TextureRegion> linearFrame = this.linearFramesForFile(this.graphicsFileConfig.getArchiMummyAppear(),
				mummyCountAppear);

		this.animationMummyAppear = this.framesToAnimation(linearFrame, 0, mummyCountAppear, frameDuration);
		linearFrame = this.linearFramesForFile(this.graphicsFileConfig.getArchiMummyDisappear(), mummyCountDeath);

		this.animationMummyDeath = this.framesToAnimation(linearFrame, 0, mummyCountDeath, 0.1f);
		this.animationMummyDeath.setPlayMode(PlayMode.NORMAL);
		this.animationMummyBlue = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyBlue());
		this.animationMummyPink = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyPink());
		this.animationMummyRed = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyRed());
		this.animationMummyWhite = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyWhite());
		this.animationMummyYellow = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyYellow());

		this.doorSingleLeft = manager.get(this.graphicsFileConfig.getArchiDoorLeft(), Texture.class);
		this.doorSingleRight = manager.get(this.graphicsFileConfig.getArchiDoorRight(), Texture.class);
		this.doorPassage = manager.get(this.graphicsFileConfig.getArchiDoorPassage(), Texture.class);
		this.skyTexture = manager.get(this.graphicsFileConfig.getArchiSky(), Texture.class);
		linearFrame = this.linearFramesForFile(this.graphicsFileConfig.getArchiDoorLever(),
				this.graphicsFileConfig.getDoorLeverCount());

		this.animationDoorLever = this.framesToAnimation(linearFrame, 0, this.graphicsFileConfig.getDoorLeverCount(),
				frameDuration);

	}

	public Animation<TextureRegion>[] getAnimationPlayer_Nothing()
	{
		return animationPlayer_Nothing;
	}

	public Animation<TextureRegion>[] getAnimationPlayer_Dagger()
	{
		return animationPlayer_Dagger;
	}

	public Animation<TextureRegion>[] getAnimationPlayer_Picker()
	{
		return animationPlayer_Picker;
	}

	public Animation<TextureRegion>[] getAnimationMummyBlue()
	{
		return animationMummyBlue;
	}

	public Animation<TextureRegion>[] getAnimationMummyPink()
	{
		return animationMummyPink;
	}

	public Animation<TextureRegion>[] getAnimationMummyRed()
	{
		return animationMummyRed;
	}

	public Animation<TextureRegion>[] getAnimationMummyWhite()
	{
		return animationMummyWhite;
	}

	public Animation<TextureRegion>[] getAnimationMummyYellow()
	{
		return animationMummyYellow;
	}

	public HashMap<Integer, Animation<TextureRegion>> getAnimations()
	{
		return animations;
	}

	public AnimatedPickedCell getAnimatedPickedCell()
	{
		return animatedPickedCell;
	}

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

	public Animation<TextureRegion> getNewAnimationGiratory(int heightInTiles)
	{
		float frameDuration = Config.getInstance().getTimeToEndGiratory()
				/ (float) this.graphicsFileConfig.getGiratoryCount();

		Array<TextureRegion> linearFrame = new Array<TextureRegion>();
		for (int i = 0; i < this.linearFramesGiratory.size; i++)
			linearFrame.add(this.generateVerticalTiledTextureRegion(this.linearFramesGiratory.get(i), heightInTiles));

		return this.framesToAnimation(linearFrame, 0, linearFrame.size, frameDuration);
	}

	private TextureRegion generateVerticalTiledTextureRegion(TextureRegion tileRegion, int count)
	{
		Texture originalTexture = tileRegion.getTexture();
		int tileWidth = tileRegion.getRegionWidth();
		int tileHeight = tileRegion.getRegionHeight();

		// Obtener el Pixmap original de la textura entera
		TextureData textureData = originalTexture.getTextureData();
		if (!textureData.isPrepared())
			textureData.prepare();
		Pixmap originalPixmap = textureData.consumePixmap();

		// Crear un Pixmap nuevo del triple de ancho
		Pixmap tiledPixmap = new Pixmap(tileWidth, tileHeight * count, originalPixmap.getFormat());

		// Dibujar tres copias del tile en el nuevo Pixmap
		for (int i = 0; i < count; i++)
		{
			tiledPixmap.drawPixmap(originalPixmap, 0, i * tileHeight, // destino
					tileRegion.getRegionX(), tileRegion.getRegionY(), // origen
					tileWidth, tileHeight // tamaño del recorte
			);
		}

		// Crear una nueva textura y TextureRegion
		Texture newTexture = new Texture(tiledPixmap);
		return new TextureRegion(newTexture);
	}

	public static String getConfigFile()
	{
		return CONFIG_FILE;
	}

	public static Json getJson()
	{
		return json;
	}

	public Texture getDoorSingleLeft()
	{
		return doorSingleLeft;
	}

	public Texture getDoorSingleRight()
	{
		return doorSingleRight;
	}

	public Texture getDoorPassage()
	{
		return doorPassage;
	}

	public Animation<TextureRegion> getAnimationDoorLever()
	{
		return animationDoorLever;
	}

	public Texture getSkyTexture()
	{
		return skyTexture;
	}

	private float getRandomFrameDuration(float minFrameDuration, float maxFrameDuration)
	{
		float delta = maxFrameDuration - minFrameDuration;
		return minFrameDuration + random.nextFloat(delta);
	}

}
