package vista2D;

import java.util.HashMap;

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

import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.mummys.MummyFactory;
import modelo.level.LevelObject;
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
	private Animation<TextureRegion>[] animationMummyOrange;
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
	private Animation<TextureRegion> animationDoorLever;
	
	
	
	
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
		//this.graphicsFileConfig=new GraphicsFileConfig();
		this.manager.load(graphicsFileConfig.getArchiPlayer(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiCollectables(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiGiratory(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiPickingCell(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyBlue(), Texture.class);
		this.manager.load(graphicsFileConfig.getArchiMummyOrange(), Texture.class);
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

		 //saveConfig(graphicsFileConfig);
	}

	private void loadPlayerAnimations()
	{

		int startIddle = this.graphicsFileConfig.getPlayerStartIddle();
		int countIddle = this.graphicsFileConfig.getPlayerCountIddle();
		int startFall = this.graphicsFileConfig.getPlayerStartFall();
		int countFall = this.graphicsFileConfig.getPlayerCountFall();

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
		int CharacterFrameWidth = this.graphicsFileConfig.getCharacterFrameWidth();
		int CharacterFrameHeight = this.graphicsFileConfig.getCharacterFrameHeight();
		float frameDuration = this.graphicsFileConfig.getFrameDuration();
		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPlayer(),
				CharacterFrameWidth, CharacterFrameHeight);

		this.animationPlayer_Nothing[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, startIddle,
				countIddle, 0);
		this.animationPlayer_Nothing[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, startFall, countFall,
				0);
		this.animationPlayer_Nothing[TileMapGrafica2D.JUMP] = this.animationPlayer_Nothing[TileMapGrafica2D.FALL];
		this.animationPlayer_Nothing[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, startWalk, countWalk,
				frameDuration);
	
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
		int collectableWidth = this.graphicsFileConfig.getCollectableTileWidth();
		int collectableHeight = this.graphicsFileConfig.getCollectableTileHeight();
		int giratoryWidth = this.graphicsFileConfig.getGiratoryWidth();
		int giratoryHeight = this.graphicsFileConfig.getGiratoryHeight();
		int collectableCount = this.graphicsFileConfig.getCollectableCount();
		float frameDuration = this.graphicsFileConfig.getFrameDuration();
		int flyingDaggerWidth = this.graphicsFileConfig.getFlyingDaggerWidth();
		int flyingDaggerHeight = this.graphicsFileConfig.getFlyingDaggerHeight();
		int flyingDaggerCount = this.graphicsFileConfig.getFlyingDaggerCount();
		int pickingCellCount = this.graphicsFileConfig.getPickingCellCount();
		float pickingCellFrameDuration = this.graphicsFileConfig.getPickingCellFrameDuration();

		Array<TextureRegion> linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiCollectables(),
				collectableWidth, collectableHeight);
		this.animations.put(Constantes.It_dagger,
				this.framesToAnimation(linearFrames, 0, collectableCount, frameDuration));
		this.animations.put(Constantes.It_picker,
				this.framesToAnimation(linearFrames, collectableCount, collectableCount, frameDuration));

		this.animations.put(Constantes.JEWEL_1,
				this.framesToAnimation(linearFrames, collectableCount * 2, collectableCount, frameDuration));
		this.animations.put(Constantes.JEWEL_2,
				this.framesToAnimation(linearFrames, collectableCount * 3, collectableCount, frameDuration));
		this.animations.put(Constantes.JEWEL_3,
				this.framesToAnimation(linearFrames, collectableCount * 4, collectableCount, frameDuration));
		this.animations.put(Constantes.JEWEL_4,
				this.framesToAnimation(linearFrames, collectableCount * 5, collectableCount, frameDuration));
		this.animations.put(Constantes.JEWEL_5,
				this.framesToAnimation(linearFrames, collectableCount * 6, collectableCount, frameDuration));
		this.animations.put(Constantes.JEWEL_6,
				this.framesToAnimation(linearFrames, collectableCount * 7, collectableCount, frameDuration));
		this.animations.put(Constantes.JEWEL_7,
				this.framesToAnimation(linearFrames, collectableCount * 8, collectableCount, frameDuration));

		this.linearFramesGiratory = this.linearFramesForFile(this.graphicsFileConfig.getArchiGiratory(), giratoryWidth,
				giratoryHeight);

		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPickingCell(), collectableWidth,
				collectableHeight);
		Animation<TextureRegion> picking_cell = this.framesToAnimation(linearFrames, 0, pickingCellCount,
				pickingCellFrameDuration);
		picking_cell.setPlayMode(PlayMode.NORMAL);

		this.animatedPickedCell = new AnimatedPickedCell(
				new LevelObject(0, 0, 0, 0, collectableWidth, collectableHeight), picking_cell);

		linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiFlyingDagger(), flyingDaggerWidth,
				flyingDaggerHeight);

		this.animations.put(Constantes.DRAWABLE_FLYING_DAGGER,
				this.framesToAnimation(linearFrames, 0, flyingDaggerCount, frameDuration));

	}

	private Animation<TextureRegion>[] loadMummyAnimations(String archiMummy)
	{
		int startIddle = this.graphicsFileConfig.getMummyStartIddle();
		int countIddle = this.graphicsFileConfig.getMummyCountIddle();
		int startFall = this.graphicsFileConfig.getMummyStartFall();
		int countFall = this.graphicsFileConfig.getMummyCountFall();

		int startWalk = this.graphicsFileConfig.getMummyStartWalk();
		int countWalk = this.graphicsFileConfig.getMummyCountWalk();
		int CharacterFrameWidth = this.graphicsFileConfig.getCharacterFrameWidth();
		int CharacterFrameHeight = this.graphicsFileConfig.getCharacterFrameHeight();

		float frameDuration = this.graphicsFileConfig.getFrameDuration();
		float mummyIddleFrameDuration = this.graphicsFileConfig.getMummyIddleFrameDuration();

		Animation<TextureRegion>[] animationMummy = new Animation[7];

		Array<TextureRegion> linearFrames = this.linearFramesForFile(archiMummy, CharacterFrameWidth,
				CharacterFrameHeight);

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
				this.graphicsFileConfig.getCharacterFrameWidth(), this.graphicsFileConfig.getCharacterFrameHeight());

		this.animationMummyAppear = this.framesToAnimation(linearFrame, 0, mummyCountAppear, frameDuration);
		linearFrame = this.linearFramesForFile(this.graphicsFileConfig.getArchiMummyDisappear(),
				this.graphicsFileConfig.getCharacterFrameWidth(), this.graphicsFileConfig.getCharacterFrameHeight());

		this.animationMummyDeath = this.framesToAnimation(linearFrame, 0, mummyCountDeath, 0.1f);
		this.animationMummyDeath.setPlayMode(PlayMode.NORMAL);
		this.animationMummyBlue = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyBlue());
		this.animationMummyOrange = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyOrange());
		this.animationMummyRed = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyRed());
		this.animationMummyWhite = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyWhite());
		this.animationMummyYellow = this.loadMummyAnimations(this.graphicsFileConfig.getArchiMummyYellow());

		this.doorSingleLeft = manager.get(this.graphicsFileConfig.getArchiDoorLeft(), Texture.class);
		this.doorSingleRight = manager.get(this.graphicsFileConfig.getArchiDoorRight(), Texture.class);
		this.doorPassage = manager.get(this.graphicsFileConfig.getArchiDoorPassage(), Texture.class);
		
		linearFrame = this.linearFramesForFile(this.graphicsFileConfig.getArchiDoorLever(),
				this.graphicsFileConfig.getDoorLeverWidth(), this.graphicsFileConfig.getDoorLeverHeight());

		
		this.animationDoorLever = this.framesToAnimation(linearFrame, 0, 2, frameDuration*5);
			
			
	
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

	public Animation<TextureRegion>[] getAnimationMummyOrange()
	{
		return animationMummyOrange;
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
		case MummyFactory.ORANGE_MUMMY:
			respuesta = this.animationMummyOrange;
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
		float frameDuration = this.graphicsFileConfig.getFrameDuration();

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

}
