package vista2D;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import modelo.LevelItem;
import mummys.Mummy;
import util.Constantes;

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

    private GraphicsFileConfig graphicsFileConfig;
    private HashMap<Integer, Animation<TextureRegion>> animations = new HashMap<Integer, Animation<TextureRegion>>();
    private AnimatedPickedCell animatedPickedCell;

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
	GraphicsFileConfig gf = loadConfig();
	this.graphicsFileConfig = gf;
	this.manager.load(gf.getArchiPlayer(), Texture.class);
	this.manager.load(gf.getArchiCollectables(), Texture.class);
	this.manager.load(gf.getArchiGiratory3(), Texture.class);
	this.manager.load(gf.getArchiPickingCell(), Texture.class);
	this.manager.load(gf.getArchiMummyBlue(), Texture.class);
	this.manager.load(gf.getArchiMummyOrange(), Texture.class);
	this.manager.load(gf.getArchiMummyRed(), Texture.class);
	this.manager.load(gf.getArchiMummyWhite(), Texture.class);
	this.manager.load(gf.getArchiMummyYellow(), Texture.class);
	this.manager.load(gf.getArchiMummyAppear(), Texture.class);
	this.manager.load(gf.getArchiMummyDisappear(), Texture.class);

	// saveConfig(gf);
    }

    private void loadPlayerAnimations()
    {

	int startIddle = this.graphicsFileConfig.getPlayerStartIddle();
	int countIddle = this.graphicsFileConfig.getPlayerCountIddle();
	int startFall = this.graphicsFileConfig.getPlayerStartFall();
	int countFall = this.graphicsFileConfig.getPlayerCountFall();

	int startWalk = this.graphicsFileConfig.getPlayerStartWalk();
	int countWalk = this.graphicsFileConfig.getPlayerCountWalk();
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
	this.animationPlayer_Nothing[TileMapGrafica2D.STAIR] = this.animationPlayer_Nothing[TileMapGrafica2D.WALK];
	this.animationPlayer_Nothing[TileMapGrafica2D.DEATH] = this.framesToAnimation(linearFrames, startDeath,
		countDeath, frameDuration);

	this.animationPlayer_Picker[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, startIddle + 6,
		countIddle, 0);
	this.animationPlayer_Picker[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, startFall + 6,
		countIddle, 0);

	this.animationPlayer_Picker[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, startWalk + 6,
		countWalk, frameDuration);
	this.animationPlayer_Picker[TileMapGrafica2D.STAIR] = this.animationPlayer_Picker[TileMapGrafica2D.WALK];
	this.animationPlayer_Picker[TileMapGrafica2D.DEATH] = this.animationPlayer_Nothing[TileMapGrafica2D.DEATH];
	this.animationPlayer_Picker[TileMapGrafica2D.PICKING] = this.framesToAnimation(linearFrames, 19, 2,
		frameDuration);
	this.animationPlayer_Picker[TileMapGrafica2D.PICKING].setPlayMode(PlayMode.LOOP);
	this.animationPlayer_Picker[TileMapGrafica2D.JUMP] = this.animationPlayer_Picker[TileMapGrafica2D.FALL];
	this.animationPlayer_Dagger[TileMapGrafica2D.IDDLE] = this.framesToAnimation(linearFrames, startIddle + 12,
		countIddle, 0);
	this.animationPlayer_Dagger[TileMapGrafica2D.FALL] = this.framesToAnimation(linearFrames, startFall + 12,
		countIddle, 0);
	this.animationPlayer_Dagger[TileMapGrafica2D.WALK] = this.framesToAnimation(linearFrames, startWalk + 12,
		countWalk, frameDuration);
	this.animationPlayer_Dagger[TileMapGrafica2D.STAIR] = this.animationPlayer_Dagger[TileMapGrafica2D.WALK];
	this.animationPlayer_Dagger[TileMapGrafica2D.DEATH] = this.animationPlayer_Nothing[TileMapGrafica2D.DEATH];
	this.animationPlayer_Dagger[TileMapGrafica2D.JUMP] = this.animationPlayer_Dagger[TileMapGrafica2D.FALL];

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
	int giratory3Height = this.graphicsFileConfig.getGiratory3Height();
	int giratory2Height = this.graphicsFileConfig.getGiratory2Height();
	int collectableCount = this.graphicsFileConfig.getCollectableCount();
	float frameDuration = this.graphicsFileConfig.getFrameDuration();

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

	linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiGiratory3(), giratoryWidth,
		giratory3Height);

	Animation<TextureRegion> giratory3_rl = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	Animation<TextureRegion> giratory3_lr = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	giratory3_lr.setPlayMode(PlayMode.REVERSED);

	this.animations.put(Constantes.DRAWABLE_GYRATORY_3_RL, giratory3_rl);
	this.animations.put(Constantes.DRAWABLE_GYRATORY_3_LR, giratory3_lr);

	linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiGiratory3(), giratoryWidth,
		giratory2Height);

	Animation<TextureRegion> giratory2_rl = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	Animation<TextureRegion> giratory2_lr = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	giratory2_lr.setPlayMode(PlayMode.REVERSED);
	this.animations.put(Constantes.DRAWABLE_GYRATORY_2_RL, giratory2_rl);
	this.animations.put(Constantes.DRAWABLE_GYRATORY_2_LR, giratory2_lr);
	linearFrames = this.linearFramesForFile(this.graphicsFileConfig.getArchiPickingCell(), collectableWidth,
		collectableHeight);
	Animation<TextureRegion> picking_cell = this.framesToAnimation(linearFrames, 0, 4, 0.25f);
	picking_cell.setPlayMode(PlayMode.NORMAL);

	this.animatedPickedCell = new AnimatedPickedCell(new LevelItem(0, 0, 0, 0, collectableWidth, collectableHeight),
		picking_cell);

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
	animationMummy[TileMapGrafica2D.STAIR] = animationMummy[TileMapGrafica2D.WALK];
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
	case Mummy.BLUE_MUMMY:
	    respuesta = this.animationMummyBlue;
	    break;
	case Mummy.ORANGE_MUMMY:
	    respuesta = this.animationMummyOrange;
	    break;
	case Mummy.RED_MUMMY:
	    respuesta = this.animationMummyRed;
	    break;
	case Mummy.WHITE_MUMMY:
	    respuesta = this.animationMummyWhite;
	    break;
	case Mummy.YELLOW_MUMMY:
	    respuesta = this.animationMummyYellow;
	    break;

	}
	return respuesta;
    }
}
