package vista2D;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import io.github.some_example_name.IMyApplicationnListener;
import modelo.DrawableElement;
import modelo.GiratoryMechanism;
import modelo.Juego;
import modelo.LevelItem;
import modelo.Pyramid;
import modelo.TrapMechanism;
import util.Constantes;

public class TileMapGrafica2D implements IMyApplicationnListener
{
    public static final int IDDLE = 0;
    public static final int FALL = 1;
    public static final int JUMP = 2;
    public static final int WALK = 3;
    public static final int STAIR = 4;
    public static final int DEATH = 5;

    private final String archiPlayer = "pics/vick.png";
    private final String archiColectables = "pics/colectables.png";
    private final String archiGiratory3 = "pics/giratory3.png";

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private AssetManager manager;
    private SpriteBatch spriteBatch = new SpriteBatch();;
    private Array<MySpriteKV> instances = new Array<MySpriteKV>();
    private Array<AnimatedEntity2D> animatedEntities = new Array<AnimatedEntity2D>();
    private HashMap<LevelItem, AnimatedEntity2D> hashMapLevelAnimation = new HashMap<LevelItem, AnimatedEntity2D>();
    private HashMap<TrapMechanism, AnimatedTrapKV2> hashMapTrapAnimation = new HashMap<TrapMechanism, AnimatedTrapKV2>();

    private Array<AnimatedTrapKV2> animatedTraps = new Array<AnimatedTrapKV2>();

    private Animation<TextureRegion>[] animationPlayer_Nothing =  new Animation[6];
    private Animation<TextureRegion>[] animationPlayer_Dagger =  new Animation[6];
    private Animation<TextureRegion>[] animationPlayer_Picker =  new Animation[6];
    
    private float scaleFactor = 1;

    private HashMap<Integer, Animation<TextureRegion>> animations = new HashMap<Integer, Animation<TextureRegion>>();

    public TileMapGrafica2D(AssetManager manager)
    {
	this.manager = manager;
	this.manager.load(this.archiPlayer, Texture.class);
	this.manager.load(this.archiColectables, Texture.class);
	this.manager.load(this.archiGiratory3, Texture.class);

    }

    private void loadAnimations()
    {
	int frameWidth = 16;
	int frameHeight = 20;
	float frameDuration = 0.1f;

	int colectableWidth = 10;
	int colectableHeight = 10;
	int giratoryWidth = 20;
	int giratory3Height = 30;
	int giratory2Height = 20;
	int startIddle = 0;
	int countIddle = 1;
	int startWalk = 0;
	int countWalk = 6;
	int startDeath = 21;
	int countDeath = 4;
	int startKnife = 0;
	int countKnife = 7;
	int starPicker = 7;
	int countPicker = 7;
	int countJewel = 7;

	Texture spriteSheet = manager.get(this.archiPlayer, Texture.class);
	Texture colectablesSheet = manager.get(this.archiColectables, Texture.class);
	Texture giratory3Sheet = manager.get(this.archiGiratory3, Texture.class);

	TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet, frameWidth, frameHeight);
	Array<TextureRegion> linearFrames = new Array<>();

	// Convertimos la matriz 2D a una lista de frames 1D
	for (int i = 0; i < tmpFrames.length; i++)
	{
	    for (int j = 0; j < tmpFrames[i].length; j++)
	    {
		linearFrames.add(tmpFrames[i][j]);
	    }
	}

	this.animationPlayer_Nothing[IDDLE] = this.framesToAnimation(linearFrames, startIddle, countIddle, 0);
	this.animationPlayer_Nothing[FALL] = this.animationPlayer_Nothing[IDDLE];
	this.animationPlayer_Nothing[JUMP] = this.animationPlayer_Nothing[IDDLE];
	this.animationPlayer_Nothing[WALK] = this.framesToAnimation(linearFrames, startWalk, countWalk, frameDuration);
	this.animationPlayer_Nothing[STAIR] = this.animationPlayer_Nothing[WALK];
	this.animationPlayer_Nothing[DEATH] = this.framesToAnimation(linearFrames, startDeath, countDeath, frameDuration);


	this.animationPlayer_Picker[IDDLE] = this.framesToAnimation(linearFrames, startIddle+6, countIddle, 0);
	this.animationPlayer_Picker[FALL] = this.animationPlayer_Picker[IDDLE];
	this.animationPlayer_Picker[JUMP] = this.animationPlayer_Picker[IDDLE];
	this.animationPlayer_Picker[WALK] = this.framesToAnimation(linearFrames, startWalk+6, countWalk, frameDuration);
	this.animationPlayer_Picker[STAIR] = this.animationPlayer_Picker[WALK];
	this.animationPlayer_Picker[DEATH] = this.animationPlayer_Nothing[DEATH];

	this.animationPlayer_Dagger[IDDLE] = this.framesToAnimation(linearFrames, startIddle+12, countIddle, 0);
	this.animationPlayer_Dagger[FALL] = this.animationPlayer_Dagger[IDDLE];
	this.animationPlayer_Dagger[JUMP] = this.animationPlayer_Dagger[IDDLE];
	this.animationPlayer_Dagger[WALK] = this.framesToAnimation(linearFrames, startWalk+12, countWalk, frameDuration);
	this.animationPlayer_Dagger[STAIR] = this.animationPlayer_Dagger[WALK];
	this.animationPlayer_Dagger[DEATH] = this.animationPlayer_Nothing[DEATH];
	
	
	
	
	

	tmpFrames = TextureRegion.split(colectablesSheet, colectableWidth, colectableHeight);
	linearFrames.clear();
	for (int i = 0; i < tmpFrames.length; i++)
	{
	    for (int j = 0; j < tmpFrames[i].length; j++)
	    {
		linearFrames.add(tmpFrames[i][j]);
	    }
	}

	this.animations.put(Constantes.It_dagger,
		this.framesToAnimation(linearFrames, startKnife, countKnife, frameDuration));
	this.animations.put(Constantes.It_picker,
		this.framesToAnimation(linearFrames, starPicker, countPicker, frameDuration));

	this.animations.put(Constantes.JEWEL_1, this.framesToAnimation(linearFrames, 14, countJewel, frameDuration));
	this.animations.put(Constantes.JEWEL_2, this.framesToAnimation(linearFrames, 21, countJewel, frameDuration));
	this.animations.put(Constantes.JEWEL_3, this.framesToAnimation(linearFrames, 28, countJewel, frameDuration));
	this.animations.put(Constantes.JEWEL_4, this.framesToAnimation(linearFrames, 35, countJewel, frameDuration));
	this.animations.put(Constantes.JEWEL_5, this.framesToAnimation(linearFrames, 42, countJewel, frameDuration));
	this.animations.put(Constantes.JEWEL_6, this.framesToAnimation(linearFrames, 49, countJewel, frameDuration));
	this.animations.put(Constantes.JEWEL_7, this.framesToAnimation(linearFrames, 56, countJewel, frameDuration));

	tmpFrames = TextureRegion.split(giratory3Sheet, giratoryWidth, giratory3Height);
	linearFrames.clear();
	for (int i = 0; i < tmpFrames.length; i++)
	{
	    for (int j = 0; j < tmpFrames[i].length; j++)
	    {
		linearFrames.add(tmpFrames[i][j]);
	    }
	}
	Animation<TextureRegion> giratory3_rl = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	Animation<TextureRegion> giratory3_lr = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	giratory3_lr.setPlayMode(PlayMode.REVERSED);

	this.animations.put(Constantes.DRAWABLE_GYRATORY_3_RL, giratory3_rl);
	this.animations.put(Constantes.DRAWABLE_GYRATORY_3_LR, giratory3_lr);

	tmpFrames = TextureRegion.split(giratory3Sheet, giratoryWidth, giratory2Height);
	linearFrames.clear();
	for (int i = 0; i < tmpFrames.length; i++)
	{
	    for (int j = 0; j < tmpFrames[i].length; j++)
	    {
		linearFrames.add(tmpFrames[i][j]);
	    }
	}

	Animation<TextureRegion> giratory2_rl = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	Animation<TextureRegion> giratory2_lr = this.framesToAnimation(linearFrames, 0, 10, frameDuration);
	giratory2_lr.setPlayMode(PlayMode.REVERSED);
	this.animations.put(Constantes.DRAWABLE_GYRATORY_2_RL, giratory2_rl);
	this.animations.put(Constantes.DRAWABLE_GYRATORY_2_LR, giratory2_lr);

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

    @Override
    public void addGraphicElement(Object element)
    {
	DrawableElement dr = (DrawableElement) element;
	if (dr.getType() == Constantes.DRAWABLE_LEVEL_ITEM)
	{
	    LevelItem item = (LevelItem) dr.getDrawable();
	    AnimatedEntity2D animatedEntity2D = null;
	    int id = 0;
	    if (item.getType() == Constantes.It_jewel)
		id = item.getP0();
	    else
		id = item.getType();
	    animatedEntity2D = new AnimatedEntity2D(item, this.animations.get(id));
	    this.animatedEntities.add(animatedEntity2D);
	    this.hashMapLevelAnimation.put(item, animatedEntity2D);
	} else if (dr.getType() == Constantes.DRAWABLE_TRAP)
	{
	    TrapMechanism trapMech = (TrapMechanism) dr.getDrawable();
	    AnimatedTrapKV2 atrapKV = new AnimatedTrapKV2(trapMech);
	    this.hashMapTrapAnimation.put(trapMech, atrapKV);
	    this.animatedTraps.add(atrapKV);

	} else if (dr.getType() == Constantes.DRAWABLE_GYRATORY_3_RL)
	{

	    GiratoryMechanism gm = (GiratoryMechanism) dr.getDrawable();
	    AnimatedGiratory2D a;
	    if (gm.isTriplex())
		a = new AnimatedGiratory2D(gm, this.animations.get(Constantes.DRAWABLE_GYRATORY_3_RL),
			this.animations.get(Constantes.DRAWABLE_GYRATORY_3_LR));
	    else
		a = new AnimatedGiratory2D(gm, this.animations.get(Constantes.DRAWABLE_GYRATORY_2_RL),
			this.animations.get(Constantes.DRAWABLE_GYRATORY_2_LR));

	    this.animatedEntities.add(a);
	}
    }

    @Override
    public void removeGraphicElement(Object element)
    {
	DrawableElement dr = (DrawableElement) element;
	if (dr.getType() == Constantes.DRAWABLE_LEVEL_ITEM)
	{
	    LevelItem item = (LevelItem) dr.getDrawable();
	    AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(item);
	    this.animatedEntities.removeValue(animatedEntity2D, true);
	    this.hashMapLevelAnimation.remove(item);
	} else if (dr.getType() == Constantes.DRAWABLE_TRAP)
	{
	    TrapMechanism trapMech = (TrapMechanism) dr.getDrawable();

	    AnimatedTrapKV2 atrapKV = this.hashMapTrapAnimation.get(trapMech);
	    this.animatedTraps.removeValue(atrapKV, true);
	    this.hashMapTrapAnimation.remove(trapMech);

	}
    }

    @Override
    public void create()
    {

	TiledMap map = Juego.getInstance().getCurrentPyramid().getMap();
	Pyramid pyramid = Juego.getInstance().getCurrentPyramid();

	// this.changeTileSet("pics/tiles2x.png",20,20);
	camera = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
	camera.position.x = pyramid.getMapWidthInPixels() * .5f;
	this.calculateCameraFull();

	this.loadAnimations();
	renderer = new OrthogonalTiledMapRenderer(map, this.scaleFactor);
	Iterator<LevelItem> levelItems = pyramid.getLevelItems();
	while (levelItems.hasNext())

	{
	    LevelItem item = levelItems.next();
	    if (item.getType() == Constantes.It_jewel || item.getType() == Constantes.It_dagger
		    || item.getType() == Constantes.It_picker)
		this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, item));
	    else if (item.getType() == Constantes.It_giratory)
	    {
		this.addGraphicElement(
			new DrawableElement(Constantes.DRAWABLE_GYRATORY_3_RL, pyramid.getGiratoryMechanism(item)));
	    } else if (item.getType() == Constantes.It_wall)
	    {
		this.instances.add(new MySpriteKV(map.getTileSets().getTile(item.getType()).getTextureRegion(), item));
	    }

	}

	this.animatedEntities
		.add(new GameCharacterAnimated2D(pyramid.getPlayer(), this.animationPlayer_Nothing));

    }

    private void changeTileSet(String fileName, int newWidth, int newHeight)
    {

	TiledMap map = Juego.getInstance().getCurrentPyramid().getMap();
	float originalWidth = (int) map.getTileSets().getTileSet(0).getProperties().get("tilewidth");
	this.scaleFactor = originalWidth / newWidth;

	Iterator<TiledMapTileSet> it = map.getTileSets().iterator();
	while (it.hasNext())
	{
	    TiledMapTileSet ttt = it.next();
	    map.getTileSets().removeTileSet(ttt);
	}

	// Cargar el nuevo tileset desde la imagen PNG

	Texture newTilesetTexture = new Texture(Gdx.files.internal(fileName));
	TextureRegion[][] splitTiles = TextureRegion.split(newTilesetTexture, newWidth, newHeight);

	// Crear el nuevo TiledMapTileSet y agregar los tiles
	TiledMapTileSet newTileSet = new TiledMapTileSet();
	for (int row = 0; row < splitTiles.length; row++)
	{
	    for (int col = 0; col < splitTiles[row].length; col++)
	    {
		StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[row][col]);
		tile.setId(row * splitTiles[row].length + col + 1); // Asignar un ID único a cada tile
		newTileSet.putTile(tile.getId(), tile);
	    }
	}

	// Agregar el nuevo tileset al mapa
	map.getTileSets().addTileSet(newTileSet);

	// Crear una nueva capa con el tamaño de tile especificado

	TiledMapTileLayer originalLayer = (TiledMapTileLayer) map.getLayers().get("back");
	this.replaceLayer(map, originalLayer, newTileSet, newWidth, newHeight);
	originalLayer = (TiledMapTileLayer) map.getLayers().get("stairs");
	this.replaceLayer(map, originalLayer, newTileSet, newWidth, newHeight);
	originalLayer = (TiledMapTileLayer) map.getLayers().get("front");
	this.replaceLayer(map, originalLayer, newTileSet, newWidth, newHeight);

    }

    private void replaceLayer(TiledMap map, TiledMapTileLayer originalLayer, TiledMapTileSet newTileSet, int newWidth,
	    int newHeight)
    {
	int width = originalLayer.getWidth();
	int height = originalLayer.getHeight();
	TiledMapTileLayer newLayer = new TiledMapTileLayer(width, height, newWidth, newHeight);
	newLayer.setName(originalLayer.getName());
	// Configurar las celdas de la nueva capa usando el nuevo tileset
	for (int x = 0; x < width; x++)
	{
	    for (int y = 0; y < height; y++)
	    {
		TiledMapTileLayer.Cell cell = originalLayer.getCell(x, y);
		if (cell != null)
		{
		    // Obtener el ID del tile actual y buscar el tile en el nuevo tileset
		    int originalTileId = cell.getTile().getId();
		    TiledMapTile newTile = newTileSet.getTile(originalTileId);

		    // Crear una nueva celda y asignar el nuevo tile
		    TiledMapTileLayer.Cell newCell = new TiledMapTileLayer.Cell();
		    if (newTile != null)
		    {
			newCell.setTile(newTile);
		    }
		    newLayer.setCell(x, y, newCell);
		}
	    }
	}

	// Reemplazar la capa original con la nueva capa en el mapa
	map.getLayers().remove(originalLayer);
	map.getLayers().add(newLayer);
    }

    @Override
    public void resize(int width, int height)
    {
	Pyramid pyramid = Juego.getInstance().getCurrentPyramid();
	float ancho = pyramid.getMapHeightInPixels() * width / (height + 2);
	float alto = pyramid.getMapHeightInPixels() + 20;

	if (height != 0)
	{
	    camera.setToOrtho(false, ancho, alto);
	    this.calculateCameraFull();
	}
    }

    @Override
    public void render()
    {
	Gdx.gl.glClearColor(.0f, .0f, .0f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	camera.update();
	this.calculateCamera();
	spriteBatch.setProjectionMatrix(camera.combined);

	renderer.setView(camera);
	renderer.render();

	this.spriteBatch.begin();

	ArrayIterator<AnimatedTrapKV2> it3 = this.animatedTraps.iterator();
	while (it3.hasNext())
	{
	    AnimatedTrapKV2 animatedTrapKV2 = it3.next();
	    animatedTrapKV2.updateElement(Juego.getInstance().getDelta());
	    animatedTrapKV2.getSprite().draw(spriteBatch);
	}

	ArrayIterator<MySpriteKV> it = this.instances.iterator();
	while (it.hasNext())
	{
	    MySpriteKV mySpriteKV = it.next();
	    mySpriteKV.updateElement(null);
	    mySpriteKV.draw(spriteBatch);
	}

	ArrayIterator<AnimatedEntity2D> it2 = this.animatedEntities.iterator();
	while (it2.hasNext())
	{
	    AnimatedEntity2D animatedEntity2D = it2.next();
	    animatedEntity2D.updateElement(Juego.getInstance().getDelta());
	    animatedEntity2D.render(spriteBatch);
	}
	spriteBatch.end();
    }

    @Override
    public void pause()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void dispose()
    {
	this.renderer.dispose();
	this.manager.dispose();
	this.spriteBatch.dispose();

    }

    private void calculateCamera()
    {
	Pyramid pyramid = Juego.getInstance().getCurrentPyramid();
	float aux_X = Juego.getInstance().getCurrentPyramid().getPlayer().getX();

	if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
	    camera.position.x = Juego.getInstance().getCurrentPyramid().getPlayer().getX();

	camera.position.y = pyramid.getMapHeightInPixels() * .55f;
    }

    private void calculateCameraFull()
    {
	float posCameraX = 0;
	Pyramid pyramid = Juego.getInstance().getCurrentPyramid();
	float aux_X = Juego.getInstance().getCurrentPyramid().getPlayer().getX();

	if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
	    posCameraX = Juego.getInstance().getCurrentPyramid().getPlayer().getX();
	else
	{
	    if (camera.viewportWidth >= pyramid.getMapWidthInPixels())
		posCameraX = pyramid.getMapWidthInPixels() * .5f;
	    else
	    {
		if (aux_X < pyramid.getMapWidthInPixels() / 2)
		    posCameraX = camera.viewportWidth / 2;
		else
		    posCameraX = pyramid.getMapWidthInPixels() - camera.viewportWidth / 2;
	    }
	}

	camera.position.x = posCameraX;
	camera.position.y = pyramid.getMapHeightInPixels() * .55f;
    }

}
