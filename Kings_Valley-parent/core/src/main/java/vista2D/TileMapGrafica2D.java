package vista2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import io.github.some_example_name.IMyApplicationnListener;
import modelo.game.Game;
import modelo.gameCharacters.mummys.Mummy;
import modelo.gameCharacters.player.PairInt;
import modelo.level.DrawableElement;
import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.Pyramid;
import modelo.level.Stair;
import modelo.level.TrapMechanism;
import modelo.level.dagger.Dagger;
import modelo.level.door.Door;
import util.Constantes;
import util.Messages;

public class TileMapGrafica2D implements IMyApplicationnListener
{
    public static final int IDDLE = 0;
    public static final int FALL = 1;
    public static final int WALK = 2;
    public static final int DEATH = 3;
    public static final int JUMP = 4;
    public static final int PICKING = 5;
    public static final int THROW_DAGGER = 5;
    public static final int APPEAR = 5;

    private OrthographicCamera camera;
    private OrthographicCamera cameraUI;
    private OrthogonalTiledMapRenderer renderer;
    private AssetManager manager;
    private Array<MySpriteKV> instances = new Array<MySpriteKV>();
    private Array<AnimatedEntity2D> animatedEntities = new Array<AnimatedEntity2D>();
    private HashMap<LevelObject, AnimatedEntity2D> hashMapLevelAnimation = new HashMap<LevelObject, AnimatedEntity2D>();
    private HashMap<TrapMechanism, AnimatedTrapKV2> hashMapTrapAnimation = new HashMap<TrapMechanism, AnimatedTrapKV2>();
    private Array<AnimatedTrapKV2> animatedTraps = new Array<AnimatedTrapKV2>();
    private ArrayList<AbstractAnimatedDoor2D> animatedDoor2D = new ArrayList<AbstractAnimatedDoor2D>();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private BitmapFont font48 = new BitmapFont();
    private BitmapFont font24 = new BitmapFont();

    private int tileWidth;
    private int tileHeight;
    private float scaleFactor = 1;
    private GraphicsFileLoader graphicsFileLoader;
    private Texture pixel;
    private RectaglesRender rectaglesRenderDebug = null;
    private boolean debug = false;
    private AnimatedPickedCell animatedPickedCell;
    private String fileNameTileSetChanged = null;
    private Texture newTilesetTexture = null;
    private Float originalWidth = null;
    private AnimatedEnteringDoor2D animatedEnteringDoor2D;
    private PlayerAnimated2D playerAnimated2D;
    private float timeToEnterLevel=2f;
    private float timeToExitLevel=1f; 
    private float timeDying=1f;

    public TileMapGrafica2D(AssetManager manager)
    {
	this.manager = manager;
	this.graphicsFileLoader = new GraphicsFileLoader(manager);
    }

    @Override
    public void addGraphicElement(Object element)
    {
	DrawableElement dr = (DrawableElement) element;
	if (dr.getType() == Constantes.DRAWABLE_LEVEL_ITEM)
	{
	    LevelObject item = (LevelObject) dr.getDrawable();
	    AnimatedEntity2D animatedEntity2D = null;
	    int id = 0;
	    if (item.getType() == Constantes.It_jewel)
		id = item.getP0();
	    else
		id = item.getType();
	    animatedEntity2D = new AnimatedEntity2D(item, this.graphicsFileLoader.getAnimations().get(id));
	    this.animatedEntities.add(animatedEntity2D);
	    this.hashMapLevelAnimation.put(item, animatedEntity2D);
	} else if (dr.getType() == Constantes.DRAWABLE_TRAP)
	{
	    TrapMechanism trapMech = (TrapMechanism) dr.getDrawable();
	    AnimatedTrapKV2 atrapKV = new AnimatedTrapKV2(trapMech);
	    this.hashMapTrapAnimation.put(trapMech, atrapKV);
	    this.animatedTraps.add(atrapKV);

	} else if (dr.getType() == Constantes.DRAWABLE_GYRATORY)
	{

	    GiratoryMechanism gm = (GiratoryMechanism) dr.getDrawable();
	    AnimatedGiratory2D a;
	    a = new AnimatedGiratory2D(gm, this.graphicsFileLoader.getNewAnimationGiratory(gm.getHeightInTiles()));

	    this.animatedEntities.add(a);
	    this.hashMapLevelAnimation.put(gm.getLevelObject(), a);
	} else if (dr.getType() == Constantes.DRAWABLE_PICKING_CELL)
	{
	    PairInt pairInt = (PairInt) dr.getDrawable();

	    this.animatedPickedCell.getLevelObject().setX(pairInt.getX() * this.tileWidth);
	    this.animatedPickedCell.getLevelObject().setY(pairInt.getY() * this.tileHeight);
	    this.animatedEntities.add(this.animatedPickedCell);
	    this.animatedPickedCell.resetTime(Game.getInstance().getDelta());

	} else if (dr.getType() == Constantes.DRAWABLE_FLYING_DAGGER)
	{
	    Dagger dagger = (Dagger) dr.getDrawable();
	    AnimatedDagger2D animatedEntity2D = new AnimatedDagger2D(dagger,
		    this.graphicsFileLoader.getAnimations().get(dagger.getP0()),
		    this.graphicsFileLoader.getAnimations().get(Constantes.DRAWABLE_FLYING_DAGGER));
	    this.animatedEntities.add(animatedEntity2D);
	    this.hashMapLevelAnimation.put(dagger, animatedEntity2D);

	}

	else if (dr.getType() == Constantes.DRAWABLE_EXIT_DOOR)
	{
	    Door door = (Door) dr.getDrawable();
	    this.animatedEnteringDoor2D = new AnimatedEnteringDoor2D(door, this.graphicsFileLoader.getDoorPassage(),
		    this.graphicsFileLoader.getDoorSingleLeft(), this.graphicsFileLoader.getDoorSingleRight(),
		    this.graphicsFileLoader.getAnimationDoorLever());
	}
    }

    @Override
    public void removeGraphicElement(Object element)
    {
	DrawableElement dr = (DrawableElement) element;
	if (dr.getType() == Constantes.DRAWABLE_LEVEL_ITEM)
	{
	    LevelObject item = (LevelObject) dr.getDrawable();
	    AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(item);
	    this.animatedEntities.removeValue(animatedEntity2D, true);
	    this.hashMapLevelAnimation.remove(item);
	} else if (dr.getType() == Constantes.DRAWABLE_TRAP)
	{
	    TrapMechanism trapMech = (TrapMechanism) dr.getDrawable();
	    AnimatedTrapKV2 atrapKV = this.hashMapTrapAnimation.get(trapMech);
	    this.animatedTraps.removeValue(atrapKV, true);
	    this.hashMapTrapAnimation.remove(trapMech);
	} else if (dr.getType() == Constantes.DRAWABLE_PICKING_CELL)
	{
	    this.animatedEntities.removeValue(this.animatedPickedCell, true);
	} else if (dr.getType() == Constantes.DRAWABLE_GYRATORY)
	{
	    LevelObject giratory = (LevelObject) dr.getDrawable();
	    AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(giratory);

	    this.animatedEntities.removeValue(animatedEntity2D, true);
	    this.hashMapLevelAnimation.remove(giratory);
	}
    }

    @Override
    public void create()
    {
	this.instances = new Array<MySpriteKV>();
	this.animatedEntities = new Array<AnimatedEntity2D>();
	this.hashMapLevelAnimation = new HashMap<LevelObject, AnimatedEntity2D>();
	this.hashMapTrapAnimation = new HashMap<TrapMechanism, AnimatedTrapKV2>();
	this.animatedTraps = new Array<AnimatedTrapKV2>();
	this.animatedDoor2D = new ArrayList<AbstractAnimatedDoor2D>();
	this.spriteBatch = new SpriteBatch();
	this.font48 = new BitmapFont();

	TiledMap map = Game.getInstance().getCurrentLevel().getPyramid().getMap();
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();

	this.tileWidth = (int) map.getTileSets().getTileSet(0).getProperties().get("tilewidth");
	this.tileHeight = (int) map.getTileSets().getTileSet(0).getProperties().get("tileheight");

	camera = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());

	camera.position.x = pyramid.getMapWidthInPixels() * .5f;
	// this.calculateCameraFull();
	this.graphicsFileLoader.loadAnimations();
	this.animatedPickedCell = this.graphicsFileLoader.getAnimatedPickedCell();
	renderer = new OrthogonalTiledMapRenderer(map, this.scaleFactor);

	Iterator<LevelObject> levelObjects = pyramid.getLevelObjects();
	while (levelObjects.hasNext())

	{
	    LevelObject item = levelObjects.next();
	    if (item.getType() == Constantes.It_jewel || item.getType() == Constantes.It_picker)
		this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_LEVEL_ITEM, item));
	    else if (item.getType() == Constantes.It_giratory)
	    {
		this.addGraphicElement(
			new DrawableElement(Constantes.DRAWABLE_GYRATORY, pyramid.getGiratoryMechanism(item)));
	    } else if (item.getType() == Constantes.It_wall && this.debug)
	    {
		this.instances.add(new MySpriteKV(map.getTileSets().getTile(item.getType()).getTextureRegion(), item));
	    } else if (item.getType() == Constantes.It_dagger)
		this.addGraphicElement(new DrawableElement(Constantes.DRAWABLE_FLYING_DAGGER, item));

	    Iterator<Mummy> it = Game.getInstance().getCurrentLevel().getMummys().iterator();
	    while (it.hasNext())
	    {
		Animation<TextureRegion>[] animation;
		Mummy mummy = it.next();
		animation = this.graphicsFileLoader.getAnimationMummyByColor(mummy.getType());
		this.animatedEntities.add(new MummyAnimated2D(mummy, animation));
	    }
	    if (this.fileNameTileSetChanged != null)
	    {
		this.changeTileSet(fileNameTileSetChanged);
	    }
	}
	this.playerAnimated2D = new PlayerAnimated2D(Game.getInstance().getCurrentLevel().getPlayer(),
		this.graphicsFileLoader.getAnimationPlayer_Nothing(),
		this.graphicsFileLoader.getAnimationPlayer_Picker(),
		this.graphicsFileLoader.getAnimationPlayer_Dagger());
	if (this.debug)
	    this.cargaEscaleras();

	Iterator<Door> itDoor = pyramid.getDoors().iterator();
	while (itDoor.hasNext())
	{
	    Door door = itDoor.next();
	    this.animatedDoor2D.add(new AnimatedDoor2D(door, this.graphicsFileLoader.getDoorPassage(),
		    this.graphicsFileLoader.getDoorSingleLeft(), this.graphicsFileLoader.getDoorSingleRight(),
		    this.graphicsFileLoader.getAnimationDoorLever()));
	}
	this.animatedEnteringDoor2D = new AnimatedEnteringDoor2D(Game.getInstance().getCurrentLevel().getDoorIn(),
		this.graphicsFileLoader.getDoorPassage(), this.graphicsFileLoader.getDoorSingleLeft(),
		this.graphicsFileLoader.getDoorSingleRight(), this.graphicsFileLoader.getAnimationDoorLever());

	// this.rectaglesRenderDebug=new RectaglesRender(map);
	this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void cargaEscaleras()
    {
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	TiledMap map = Game.getInstance().getCurrentLevel().getPyramid().getMap();

	ArrayList<Stair> escaleras = new ArrayList<Stair>();
	escaleras.addAll(pyramid.getPositiveStairs());
	escaleras.addAll(pyramid.getNegativeStairs());
	for (int i = 0; i < escaleras.size(); i++)
	{
	    LevelObject item = escaleras.get(i).getDownStair();
	    this.instances.add(new MySpriteKV(map.getTileSets().getTile(item.getP0() + 250).getTextureRegion(), item));
	    item = escaleras.get(i).getUpStair();
	    this.instances.add(new MySpriteKV(map.getTileSets().getTile(item.getP0() + 250).getTextureRegion(), item));

	}
    }

    private void prepareUI()
    {
	int fontSize= (int) (Gdx.graphics.getHeight()*(1.f/24.f)); 
	this.cameraUI = new OrthographicCamera();
	this.cameraUI.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
	pixmap.setColor(Color.WHITE);
	pixmap.fill();
	this.pixel = new Texture(pixmap);
	pixmap.dispose();

	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameter.size = 48; // Tamaño de fuente
	parameter.color = Color.WHITE;
	font48 = generator.generateFont(parameter);
	
	
	FreeTypeFontGenerator.FreeTypeFontParameter parameter24 = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameter24.size = fontSize; // Tamaño de fuente
	parameter24.color = Color.WHITE;
	
	font24 = generator.generateFont(parameter24);
	
	generator.dispose();
    }

    public void changeTileSet(String fileName)
    {

	TiledMap map = Game.getInstance().getCurrentLevel().getPyramid().getMap();
	if (this.originalWidth == null)
	    this.originalWidth = (float) ((int) map.getTileSets().getTileSet(0).getProperties().get("tilewidth"));
	int newWidth;
	int newHeight;

	Iterator<TiledMapTileSet> it = map.getTileSets().iterator();
	while (it.hasNext())
	{
	    TiledMapTileSet ttt = it.next();
	    map.getTileSets().removeTileSet(ttt);
	}

	// Cargar el nuevo tileset desde la imagen PNG
	if (fileName != this.fileNameTileSetChanged)
	{
	    this.fileNameTileSetChanged = fileName;
	    if (this.newTilesetTexture != null)
		this.newTilesetTexture.dispose();

	    this.newTilesetTexture = new Texture(Gdx.files.internal(fileName));
	}
	newWidth = this.newTilesetTexture.getWidth() / 20;
	newHeight = this.newTilesetTexture.getHeight() / 13;
	this.scaleFactor = originalWidth / newWidth;
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
	renderer = new OrthogonalTiledMapRenderer(map, this.scaleFactor);

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
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float ancho = pyramid.getMapHeightInPixels() * width / (height + 2);
	float alto = pyramid.getMapHeightInPixels() + 20;

	if (height != 0)
	{
	    camera.setToOrtho(false, ancho, alto);
	    this.calculateCameraFull();
	}
	this.prepareUI();
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
	    animatedTrapKV2.updateElement(Game.getInstance().getDelta());
	    animatedTrapKV2.getSprite().draw(spriteBatch);
	}
	if (Game.getInstance().getState() == Game.ST_GAME_PLAYING)
	{
	    Iterator<AbstractAnimatedDoor2D> itDoors = this.animatedDoor2D.iterator();
	    while (itDoors.hasNext())
	    {
		AbstractAnimatedDoor2D animatedDoor = itDoors.next();
		animatedDoor.updateElement(null);
		animatedDoor.draw(spriteBatch);
	    }
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
	    animatedEntity2D.updateElement(Game.getInstance().getDelta());
	    animatedEntity2D.render(spriteBatch);
	}

	if (Game.getInstance().getState() == Game.ST_GAME_PLAYING)
	{

	    this.playerAnimated2D.updateElement(Game.getInstance().getDelta());
	    this.playerAnimated2D.render(spriteBatch);
	} else
	{
	    this.animatedEnteringDoor2D.updateElement(null);
	    this.animatedEnteringDoor2D.drawBack(spriteBatch);
	    this.playerAnimated2D.updateElement(Game.getInstance().getDelta());
	    if ((Game.getInstance().getState() == Game.ST_GAME_ENTERING
		    && Game.getInstance().getDelta() <= this.getTimeToExitLevel())
		    || Game.getInstance().getState() == Game.ST_GAME_EXITING)
	    {
		this.playerAnimated2D.render(spriteBatch);
		this.animatedEnteringDoor2D.drawFront(spriteBatch);
	    } else
	    {
		this.animatedEnteringDoor2D.drawFront(spriteBatch);
		this.playerAnimated2D.render(spriteBatch);

	    }

	}

	if (Game.getInstance().isPaused())
	{
	    this.cameraUI.update();
	    spriteBatch.setProjectionMatrix(cameraUI.combined);
	    GlyphLayout layout = new GlyphLayout(font48, Messages.GAME_PAUSED.getValue());
	    float x = (Gdx.graphics.getWidth() - layout.width) / 2;
	    float y = (Gdx.graphics.getHeight() + layout.height) / 2;
	    spriteBatch.setColor(0, 0, 0, 0.5f); // negro con 50% opacidad
	    spriteBatch.draw(pixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	    font48.draw(spriteBatch, layout, x, y);

	}
	this.drawFootUI();

	spriteBatch.end();
	// this.rectaglesRenderDebug.render(camera.combined);
    }

    private void drawFootUI()
    {
	this.cameraUI.update();
	spriteBatch.setProjectionMatrix(cameraUI.combined);
	
	GlyphLayout layout = new GlyphLayout(font24, Messages.CURRENT_PYRAMID.getValue()+Game.getInstance().getCurrentLevel().getId());
	float x = (Gdx.graphics.getWidth() - layout.width) / 2;
	float y = Gdx.graphics.getHeight()-layout.height;
	//spriteBatch.setColor(0, 0, 0, 0.5f); // negro con 50% opacidad
//	spriteBatch.draw(pixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	font24.draw(spriteBatch, layout, x, y);
    }

    @Override
    public void pause()
    {

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
	if (this.rectaglesRenderDebug != null)
	    this.rectaglesRenderDebug.dispose();

    }

    private void calculateCamera()
    {
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float aux_X = Game.getInstance().getCurrentLevel().getPlayer().getX();

	if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
	    camera.position.x = Game.getInstance().getCurrentLevel().getPlayer().getX();

	camera.position.y = pyramid.getMapHeightInPixels() * .55f;
    }

    private void calculateCameraFull()
    {
	float posCameraX = 0;
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float aux_X = Game.getInstance().getCurrentLevel().getPlayer().getX();

	if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
	    posCameraX = Game.getInstance().getCurrentLevel().getPlayer().getX();
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

    @Override
    public void reset()
    {
	this.create();

    }

    @Override
    public float getTimeToExitLevel()
    {
	
	return this.timeToExitLevel;
    }

    @Override
    public float getTimeToEnterLevel()
    {
	
	return this.timeToEnterLevel;
    }

    @Override
    public float getTimeDying()
    {
	
	return this.timeDying;
    }

}
