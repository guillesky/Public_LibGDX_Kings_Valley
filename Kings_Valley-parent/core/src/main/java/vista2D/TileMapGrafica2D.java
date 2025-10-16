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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import engine.DrawableElement;
import engine.game.Game;
import engine.gameCharacters.mummys.Mummy;
import engine.gameCharacters.mummys.PlatformAnalysisResult;
import engine.level.GiratoryMechanism;
import engine.level.LevelObject;
import engine.level.PairInt;
import engine.level.Pyramid;
import engine.level.Stair;
import engine.level.TrapMechanism;
import engine.level.dagger.Dagger;
import engine.level.door.Door;
import i18n.Messages;
import mainPackage.IMyApplicationListener;
import util.Constants;
import util.GameRules;

/**
 * Se encarga de renderizar el juego utilizando tecnicas 2D, con Sprites,
 * animaciones, y paralax
 * 
 * @author Guillermo Lazzurri
 */
public class TileMapGrafica2D implements IMyApplicationListener
{
    /**
     * Define el indice en los array de animaciones, IDDLE = 0 (caracteres en modo
     * descanso)
     */
    public static final int IDDLE = 0;
    /**
     * Define el indice en los array de animaciones, FALL = 1 (caracteres cayendo)
     */
    public static final int FALL = 1;
    /**
     * Define el indice en los array de animaciones, WALK = 2 (caracteres caminando)
     */
    public static final int WALK = 2;
    /**
     * Define el indice en los array de animaciones, DEATH = 3 (caracteres muriendo)
     */
    public static final int DEATH = 3;
    /**
     * Define el indice en los array de animaciones, JUMP = 4 (caracteres saltando)
     */
    public static final int JUMP = 4;
    /**
     * Define el indice en los array de animaciones, PICKING = 5 (Player picando)
     */
    public static final int PICKING = 5;
    /**
     * Define el indice en los array de animaciones, THROW_DAGGER = 5 (Player
     * lanzando la espada)
     */
    public static final int THROW_DAGGER = 5;
    /**
     * Define el indice en los array de animaciones, APPEAR = 5 (Momia apareciendo)
     */
    public static final int APPEAR = 5;

    private OrthographicCamera camera;
    private OrthographicCamera cameraUI;
    private OrthogonalTiledMapRenderer renderer;

    private Array<MySpriteKV> arrayOfMySpritesKV = new Array<MySpriteKV>();
    private Array<AnimatedEntity2D> animatedEntities = new Array<AnimatedEntity2D>();
    private HashMap<LevelObject, AnimatedEntity2D> hashMapLevelAnimation = new HashMap<LevelObject, AnimatedEntity2D>();
    private HashMap<TrapMechanism, AnimatedTrapKV2D> hashMapTrapAnimation = new HashMap<TrapMechanism, AnimatedTrapKV2D>();
    private Array<AnimatedTrapKV2D> animatedTraps = new Array<AnimatedTrapKV2D>();
    private ArrayList<AbstractAnimatedDoor2D> animatedDoor2D = new ArrayList<AbstractAnimatedDoor2D>();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private BitmapFont font48 = new BitmapFont();
    private BitmapFont font24 = new BitmapFont();

    private int tileWidth;
    private int tileHeight;
    private float scaleFactor = 1;
    private GraphicsFileLoader graphicsFileLoader;
    private Texture pixel;
    private boolean debug = false;
    private AnimatedPickedCell animatedPickedCell;

    private AnimatedEnteringDoor2D animatedEnteringDoor2D;
    private PlayerAnimated2D playerAnimated2D;
    private float timeToEnterLevel = 2f;
    private float timeToExitLevel = 1f;
    private float timeDying = 1f;
    private float cameraOffsetY = (12f / 22f);
    private float timeToEndGame = 2f;
    private OrthographicCamera cameraBack;
    private float factor;
    private float[] paramFloat = new float[4];

    private PlatformAnalysisResultRender platformAnalysisResultRender = null;// solo para debug

    /**
     * Constructor de clase
     * 
     * @param assetManager Encargado de cargar los recursos
     * @param factor       Indica el factor de desplazamiento del paralax. Toma
     *                     valores entre 0 y 1. Valores cercanos a 0 hacen que el
     *                     fondo se mueva casi como el frente. Valores cercanos a 1
     *                     hace que el fondo quede casi fijo. (por defecto se utliza
     *                     0.5f)
     */
    public TileMapGrafica2D(AssetManager assetManager, float factor)
    {
	this.graphicsFileLoader = new GraphicsFileLoader(assetManager);

	this.graphicsFileLoader.createAnimations(timeDying);
	this.factor = factor;

    }

    /**
     * Agrega los elementos graficos a las listas correspondientes de animaciones.
     * Es llamado durante la primer lectura del nivel y cada vez que se agrega un
     * nuevo objeto renderizable (celda sinedo picada, o muro trampa)
     */
    @Override
    public void addGraphicElement(DrawableElement element)
    {

	if (element.getType() == Constants.DRAWABLE_LEVEL_ITEM)
	{
	    LevelObject item = (LevelObject) element.getDrawable();
	    AnimatedEntity2D animatedEntity2D = null;
	    int id = 0;
	    if (item.getType() == Constants.IT_JEWEL)
		id = item.getP0();
	    else
		id = item.getType();
	    animatedEntity2D = new AnimatedEntity2D(item, this.graphicsFileLoader.getCollectablesAnimations().get(id));
	    this.animatedEntities.add(animatedEntity2D);
	    this.hashMapLevelAnimation.put(item, animatedEntity2D);
	} else if (element.getType() == Constants.DRAWABLE_TRAP)
	{
	    TrapMechanism trapMech = (TrapMechanism) element.getDrawable();
	    AnimatedTrapKV2D atrapKV = new AnimatedTrapKV2D(trapMech, this.spriteBatch);
	    this.hashMapTrapAnimation.put(trapMech, atrapKV);
	    this.animatedTraps.add(atrapKV);

	} else if (element.getType() == Constants.DRAWABLE_GYRATORY)
	{

	    GiratoryMechanism gm = (GiratoryMechanism) element.getDrawable();
	    AnimatedGiratory2D a;
	    a = new AnimatedGiratory2D(gm, this.graphicsFileLoader.getNewAnimationGiratory(gm.getHeightInTiles()));

	    this.animatedEntities.add(a);
	    this.hashMapLevelAnimation.put(gm.getLevelObject(), a);
	} else if (element.getType() == Constants.DRAWABLE_PICKING_CELL)
	{
	    PairInt pairInt = (PairInt) element.getDrawable();

	    this.animatedPickedCell.getLevelObject().setX(pairInt.getX() * this.tileWidth);
	    this.animatedPickedCell.getLevelObject().setY(pairInt.getY() * this.tileHeight);
	    this.animatedEntities.add(this.animatedPickedCell);
	    this.animatedPickedCell.setInitialTime(Game.getInstance().getDelta());

	} else if (element.getType() == Constants.DRAWABLE_FLYING_DAGGER)
	{
	    Dagger dagger = (Dagger) element.getDrawable();
	    AnimatedDagger2D animatedEntity2D = new AnimatedDagger2D(dagger,
		    this.graphicsFileLoader.getCollectablesAnimations().get(dagger.getP0()),
		    this.graphicsFileLoader.getCollectablesAnimations().get(Constants.DRAWABLE_FLYING_DAGGER));
	    this.animatedEntities.add(animatedEntity2D);
	    this.hashMapLevelAnimation.put(dagger, animatedEntity2D);

	}

	else if (element.getType() == Constants.DRAWABLE_EXIT_DOOR)
	{
	    Door door = (Door) element.getDrawable();
	    this.animatedEnteringDoor2D = new AnimatedEnteringDoor2D(door, this.graphicsFileLoader.getDoorPassage(),
		    this.graphicsFileLoader.getDoorSingleLeft(), this.graphicsFileLoader.getDoorSingleRight(),
		    this.graphicsFileLoader.getAnimationDoorLever());
	} else if (element.getType() == Constants.DRAWABLE_PLATFORM_ANALYSIS_RESULT)
	{// solo para debug
	    PlatformAnalysisResult a = (PlatformAnalysisResult) element.getDrawable();
	    this.platformAnalysisResultRender = new PlatformAnalysisResultRender(a);

	}
    }

    /**
     * Llamado al eliminar un objeto renderizable de las listas de dibujado. Llamado
     * al terminar de picar una celda, al finalizar un muro trampa, al recoger un
     * coleccionable, etc.
     */
    @Override
    public void removeGraphicElement(DrawableElement element)
    {
	if (element.getType() == Constants.DRAWABLE_LEVEL_ITEM)
	{
	    LevelObject item = (LevelObject) element.getDrawable();
	    AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(item);
	    this.animatedEntities.removeValue(animatedEntity2D, true);
	    this.hashMapLevelAnimation.remove(item);
	} else if (element.getType() == Constants.DRAWABLE_TRAP)
	{
	    TrapMechanism trapMech = (TrapMechanism) element.getDrawable();
	    AnimatedTrapKV2D atrapKV = this.hashMapTrapAnimation.get(trapMech);
	    this.animatedTraps.removeValue(atrapKV, true);
	    this.hashMapTrapAnimation.remove(trapMech);
	} else if (element.getType() == Constants.DRAWABLE_PICKING_CELL)
	{
	    this.animatedEntities.removeValue(this.animatedPickedCell, true);
	} else if (element.getType() == Constants.DRAWABLE_GYRATORY)
	{
	    LevelObject giratory = (LevelObject) element.getDrawable();
	    AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(giratory);

	    this.animatedEntities.removeValue(animatedEntity2D, true);
	    this.hashMapLevelAnimation.remove(giratory);
	}
    }

    /**
     * Crea las listas de dibujado, inicializa las camaras, las fuentes, etc.
     */
    @Override
    public void create()
    {
	this.arrayOfMySpritesKV = new Array<MySpriteKV>();
	this.animatedEntities = new Array<AnimatedEntity2D>();
	this.hashMapLevelAnimation = new HashMap<LevelObject, AnimatedEntity2D>();
	this.hashMapTrapAnimation = new HashMap<TrapMechanism, AnimatedTrapKV2D>();
	this.animatedTraps = new Array<AnimatedTrapKV2D>();
	this.animatedDoor2D = new ArrayList<AbstractAnimatedDoor2D>();
	this.spriteBatch = new SpriteBatch();
	this.font48 = new BitmapFont();

	TiledMap map = Game.getInstance().getCurrentLevel().getPyramid().getMap();
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();

	this.tileWidth = (int) map.getTileSets().getTileSet(0).getProperties().get("tilewidth");
	this.tileHeight = (int) map.getTileSets().getTileSet(0).getProperties().get("tileheight");

	camera = new OrthographicCamera(pyramid.getMapHeightInUnits() * 4 / 3, pyramid.getMapHeightInUnits());

	camera.position.x = pyramid.getMapWidthInUnits() * .5f;
	// this.calculateCameraFull();
	this.animatedPickedCell = this.graphicsFileLoader.getAnimatedPickedCell();
	renderer = new OrthogonalTiledMapRenderer(map, this.scaleFactor);

	Iterator<LevelObject> levelObjects = pyramid.getLevelObjects();
	while (levelObjects.hasNext())

	{
	    LevelObject item = levelObjects.next();
	    if (item.getType() == Constants.IT_JEWEL || item.getType() == Constants.IT_PICKER)
		this.addGraphicElement(new DrawableElement(Constants.DRAWABLE_LEVEL_ITEM, item));
	    else if (item.getType() == Constants.IT_GIRATORY)
	    {
		this.addGraphicElement(
			new DrawableElement(Constants.DRAWABLE_GYRATORY, pyramid.getGiratoryMechanism(item)));
	    } else if (item.getType() == Constants.IT_WALL_TRAP && this.debug)
	    {
		this.arrayOfMySpritesKV
			.add(new MySpriteKV(map.getTileSets().getTile(item.getType()).getTextureRegion(), item));
	    } else if (item.getType() == Constants.IT_DAGGER)
		this.addGraphicElement(new DrawableElement(Constants.DRAWABLE_FLYING_DAGGER, item));

	    Iterator<Mummy> it = Game.getInstance().getCurrentLevel().getMummys().iterator();
	    while (it.hasNext())
	    {
		Animation<TextureRegion>[] animation;
		Mummy mummy = it.next();
		animation = this.graphicsFileLoader.getAnimationMummyByColor(mummy.getType());
		this.animatedEntities.add(new MummyAnimated2D(mummy, animation));
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

	this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	cameraBack = new OrthographicCamera(pyramid.getMapHeightInUnits() * 4 / 3, pyramid.getMapHeightInUnits());
	this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    /**
     * Crea la lista de sprites para visualizar los inicios y fin de escaleras, solo
     * se utiliza en modo debug
     */
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
	    this.arrayOfMySpritesKV
		    .add(new MySpriteKV(map.getTileSets().getTile(item.getP0() + 250).getTextureRegion(), item));
	    item = escaleras.get(i).getUpStair();
	    this.arrayOfMySpritesKV
		    .add(new MySpriteKV(map.getTileSets().getTile(item.getP0() + 250).getTextureRegion(), item));

	}
    }

    /**
     * Genera la UI durante el juego (Puntaje, vidas, piramide actual)
     */
    private void prepareUI()
    {
	int fontSize = (int) (Gdx.graphics.getHeight() * (1.f / 22.f));
	this.cameraUI = new OrthographicCamera();
	this.cameraUI.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
	pixmap.setColor(Color.WHITE);
	pixmap.fill();
	this.pixel = new Texture(pixmap);
	pixmap.dispose();

	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameter.size = 48;
	parameter.color = Color.WHITE;
	font48 = generator.generateFont(parameter);

	FreeTypeFontGenerator.FreeTypeFontParameter parameter24 = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameter24.size = fontSize;
	parameter24.color = Color.WHITE;

	font24 = generator.generateFont(parameter24);

	generator.dispose();
    }

    /**
     * Recalcula las camaras en caso de redimensionado de ventana
     */
    @Override
    public void resize(int width, int height)
    {
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float alto = pyramid.getMapHeightInUnits() + GameRules.getInstance().getLevelTileHeightUnits() * 2;
	float ancho = alto * width / height;

	if (height != 0)
	{
	    camera.setToOrtho(false, ancho, alto);
	    this.calculateCameraFull();
	}
	this.prepareUI();

	if (height != 0 && this.cameraBack != null)
	{

	    cameraBack.setToOrtho(false, ancho, alto);
	    this.calculateCameraBackFull();
	}
	this.calculateTextureRegionSky(pyramid.getMapWidthInTiles());

    }

    /**
     * Dibuja los diferentes planos de scroll y las listas de objetos renderizables
     */
    @Override
    public void render()
    {

	Gdx.gl.glClearColor(.0f, .0f, .0f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	camera.update();
	this.calculateCamera();
	this.calculateCameraBack();

	this.drawBackground();

	camera.update();
	this.drawLayersMap();

	this.drawItemsCharactersMechanism();

	if (Game.getInstance().isPaused())
	{
	    this.drawPauseMessage();

	}
	if (this.platformAnalysisResultRender != null)
	    this.platformAnalysisResultRender.render(camera.combined);
	this.drawUI();

    }

    /**
     * Dibuja el mensaje de juego en pausa
     */
    private void drawPauseMessage()
    {
	spriteBatch.begin();
	this.cameraUI.update();
	spriteBatch.setProjectionMatrix(cameraUI.combined);
	GlyphLayout layout = new GlyphLayout(font48, Messages.GAME_PAUSED.getValue());
	float x = (Gdx.graphics.getWidth() - layout.width) / 2;
	float y = (Gdx.graphics.getHeight() + layout.height) / 2;
	spriteBatch.setColor(0, 0, 0, 0.5f); // negro con 50% opacidad
	spriteBatch.draw(pixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	font48.draw(spriteBatch, layout, x, y);
	spriteBatch.end();
    }

    /**
     * Llamado internamente por render(). Dibuja los items del juego, los
     * mecanismos, y los caracteres (momias y player)
     */
    private void drawItemsCharactersMechanism()
    {
	this.spriteBatch.begin();
	ArrayIterator<AnimatedTrapKV2D> it3 = this.animatedTraps.iterator();
	while (it3.hasNext())
	{
	    AnimatedTrapKV2D animatedTrapKV2 = it3.next();
	    animatedTrapKV2.updateElement();

	}
	if (Game.getInstance().getState() == Game.ST_GAME_PLAYING)
	{
	    Iterator<AbstractAnimatedDoor2D> itDoors = this.animatedDoor2D.iterator();
	    while (itDoors.hasNext())
	    {
		AbstractAnimatedDoor2D animatedDoor = itDoors.next();
		animatedDoor.updateElement();
		animatedDoor.draw(spriteBatch);
	    }
	}
	ArrayIterator<MySpriteKV> it = this.arrayOfMySpritesKV.iterator();
	while (it.hasNext())
	{
	    MySpriteKV mySpriteKV = it.next();
	    mySpriteKV.updateElement();
	    mySpriteKV.draw(spriteBatch);
	}

	ArrayIterator<AnimatedEntity2D> it2 = this.animatedEntities.iterator();
	while (it2.hasNext())
	{
	    AnimatedEntity2D animatedEntity2D = it2.next();
	    animatedEntity2D.updateElement(Game.getInstance().getDelta());
	    animatedEntity2D.render(spriteBatch);
	}

	if (Game.getInstance().getState() == Game.ST_GAME_PLAYING
		|| Game.getInstance().getState() == Game.ST_GAME_DYING)
	{

	    this.playerAnimated2D.updateElement(Game.getInstance().getDelta());
	    this.playerAnimated2D.render(spriteBatch);
	} else if (Game.getInstance().getState() != Game.ST_ENDING)
	{
	    this.animatedEnteringDoor2D.updateElement();
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
	this.spriteBatch.end();
    }

    /**
     * LLamado internamente por render. Dibuja las capas de la piramide respetando
     * el paralax
     */
    private void drawLayersMap()
    {
	spriteBatch.setProjectionMatrix(camera.combined);
	this.spriteBatch.begin();
	this.cameraBack.update();
	renderer.setView(cameraBack);

	renderer.render(new int[]
	{ 0 });
	this.spriteBatch.end();
	this.spriteBatch.begin();
	renderer.setView(camera);
	renderer.render(new int[]
	{ 1, 2 });
	this.spriteBatch.end();

    }

    /**
     * LLamado internamente por render(). Dibuja el cielo de fondo
     */
    private void drawBackground()
    {
	spriteBatch.setProjectionMatrix(this.cameraUI.combined);
	this.spriteBatch.begin();
	this.spriteBatch.setColor(Color.WHITE);
	this.spriteBatch.draw(this.graphicsFileLoader.getSkyTexture(), this.paramFloat[0], this.paramFloat[1],
		this.paramFloat[2], this.paramFloat[3]);
	this.spriteBatch.end();

    }

    /**
     * Llamado internamente por render(). Calcula la camara para la capa mas
     * profunda de la piramide (no confundir con el cielo de fondo)
     */
    private void calculateCameraBack()
    {
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float playerX = Game.getInstance().getCurrentLevel().getPlayer().getX();
	float medioX = pyramid.getMapWidthInUnits() / 2;
	float dif = (medioX - playerX) * factor;
	float aux_X = playerX + dif;

	if (playerX >= (cameraBack.viewportWidth / 2)
		&& playerX + (cameraBack.viewportWidth / 2) <= pyramid.getMapWidthInUnits())
	    cameraBack.position.x = aux_X;

	cameraBack.position.y = pyramid.getMapHeightInUnits() * this.cameraOffsetY;
    }

    /**
     * Llamado internamente por render(). Dibuja la UI del juego (vidas, puntaje,
     * piramide actual)
     */
    private void drawUI()
    {
	this.spriteBatch.begin();
	this.cameraUI.update();
	spriteBatch.setProjectionMatrix(cameraUI.combined);
	int currentPyramid = Game.getInstance().getCurrentLevel().getId();
	String scoreWithZeros = String.format("%06d", Game.getInstance().getScore());

	String cartel = Messages.CURRENT_PYRAMID.getValue() + currentPyramid;
	cartel += "    " + Messages.LIVES.getValue() + Game.getInstance().getLives();
	cartel += "    " + Messages.SCORE.getValue() + scoreWithZeros;

	GlyphLayout layout = new GlyphLayout(font24, cartel);
	float x = (Gdx.graphics.getWidth() - layout.width) / 2;
	float y = Gdx.graphics.getHeight() - layout.height;

	font24.draw(spriteBatch, layout, x, y);

	if (Game.getInstance().getState() == Game.ST_GAME_ENTERING || Game.getInstance().getState() == Game.ST_ENDING)
	{
	    font24.setColor(1, 1, 0, 1);
	    String textToShow;

	    if (Game.getInstance().getState() == Game.ST_GAME_ENTERING)
	    {
		if (Game.getInstance().isGoingBack())
		    textToShow = Messages.GOING_BACK.getValue();
		else
		    textToShow = Messages.ENTERING.getValue();
		textToShow += currentPyramid;
	    } else
	    {
		textToShow = Messages.GAME_OVER.getValue();
	    }
	    layout.setText(font24, textToShow);
	    x = (Gdx.graphics.getWidth() - layout.width) / 2;
	    y = (Gdx.graphics.getHeight() - layout.height) / 2;

	    float rx = (Gdx.graphics.getWidth() - layout.width * 1.2f) / 2;
	    float ry = (Gdx.graphics.getHeight() - layout.height * 1.2f) / 2;
	    ry -= layout.height * 1.2f;
	    spriteBatch.setColor(0, 0, 0, 0.5f); // negro con 50% opacidad
	    spriteBatch.draw(pixel, rx, ry, layout.width * 1.2f, layout.height * 1.2f);

	    font24.draw(spriteBatch, layout, x, y);
	    font24.setColor(1, 1, 1, 1);
	}
	this.spriteBatch.end();
    }

    /**
     * Metodo sobreescrito vacio (no hace nada)
     */
    @Override
    public void pause()
    {

    }

    /**
     * Metodo sobreescrito vacio (no hace nada)
     */
    @Override
    public void resume()
    {

    }

    /**
     * Libera recursos creados
     */
    @Override
    public void dispose()
    {
	this.renderer.dispose();

	this.spriteBatch.dispose();

	this.font48.dispose();
	this.font24.dispose();
	this.pixel.dispose();
	this.graphicsFileLoader.dispose();
	if (this.platformAnalysisResultRender != null)
	    this.platformAnalysisResultRender.dispose();

    }

    /**
     * Llamado internamente por render(). Calcula la camara de la capa frontal de la
     * piramide.
     * 
     * @return true si hubo cambios, false en caso contrario
     */
    private boolean calculateCamera()
    {
	boolean wasChange = false;
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float aux_X = Game.getInstance().getCurrentLevel().getPlayer().getX();

	if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInUnits())
	{
	    camera.position.x = Game.getInstance().getCurrentLevel().getPlayer().getX();
	    wasChange = true;
	}

	camera.position.y = pyramid.getMapHeightInUnits() * this.cameraOffsetY;
	return wasChange;
    }

    /**
     * Llamado internamente por resize(). Calcula la camara de la capa frontal de la
     * piramide realizando mas calculos que calculateCamera, ya que considera los
     * cambios ocurridos durante el redimensionado.
     * 
     */
    private void calculateCameraFull()
    {
	float posCameraX = 0;
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float aux_X = Game.getInstance().getCurrentLevel().getPlayer().getX();

	if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInUnits())
	    posCameraX = Game.getInstance().getCurrentLevel().getPlayer().getX();
	else
	{
	    if (camera.viewportWidth >= pyramid.getMapWidthInUnits())
		posCameraX = pyramid.getMapWidthInUnits() * .5f;
	    else
	    {
		if (aux_X < pyramid.getMapWidthInUnits() / 2)
		    posCameraX = camera.viewportWidth / 2;
		else
		    posCameraX = pyramid.getMapWidthInUnits() - camera.viewportWidth / 2;
	    }
	}

	camera.position.x = posCameraX;
	camera.position.y = pyramid.getMapHeightInUnits() * this.cameraOffsetY;
    }

    /**
     * Llama a this.create()
     */
    @Override
    public void inicialize()
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

    @Override
    public float getTimeToEndGame()
    {

	return this.timeToEndGame;
    }

    /**
     * Llamado internamente por resize. Calcula cual es la region de cielo de fondo
     * mas grande que puede utilizarse de acuerdo al radio de aspecto de la
     * pantalla.
     * 
     * @param mapWidthInTiles Cantidad de tiles de ancho que tiene la piramide
     */
    private void calculateTextureRegionSky(int mapWidthInTiles)
    {

	float cameraWidth = this.cameraUI.viewportWidth;

	float cameraTileHeight = this.cameraUI.viewportHeight / 24;
	float cameraTileWidth = GameRules.getInstance().getLevelTileWidthUnits()
		/ GameRules.getInstance().getLevelTileHeightUnits() * cameraTileHeight;

	this.paramFloat[1] = cameraTileHeight;
	this.paramFloat[3] = cameraTileHeight * 21;
	int mapWidthInPixels = (int) (cameraTileWidth * mapWidthInTiles);

	if (mapWidthInPixels < cameraWidth)
	{
	    this.paramFloat[0] = (cameraWidth - mapWidthInPixels) / 2;
	    this.paramFloat[2] = mapWidthInPixels;
	} else
	{
	    this.paramFloat[0] = 0;
	    this.paramFloat[2] = cameraWidth;
	}
    }

    /**
     * Llamado internamente por resize(). Calcula la camara de la capa de fondo de
     * la piramide realizando mas calculos que calculateCameraBack, ya que considera
     * los cambios ocurridos durante el redimensionado.
     * 
     */
    private void calculateCameraBackFull()
    {
	float posCameraX = 0;
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();

	float playerX = Game.getInstance().getCurrentLevel().getPlayer().getX();
	float medioX = pyramid.getMapWidthInUnits() / 2;
	float dif = (medioX - playerX) * this.factor;
	float aux_X = playerX + dif;

	if (playerX >= (cameraBack.viewportWidth / 2)
		&& playerX + (cameraBack.viewportWidth / 2) <= pyramid.getMapWidthInUnits())
	    posCameraX = aux_X;

	else
	{
	    if (cameraBack.viewportWidth >= pyramid.getMapWidthInUnits())
		posCameraX = pyramid.getMapWidthInUnits() * .5f;
	    else
	    {
		if (playerX < pyramid.getMapWidthInUnits() / 2)
		{

		    playerX = cameraBack.viewportWidth / 2;
		    medioX = pyramid.getMapWidthInUnits() / 2;
		    dif = (medioX - playerX) * this.factor;
		    posCameraX = playerX + dif;

		} else
		{
		    playerX = pyramid.getMapWidthInUnits() - cameraBack.viewportWidth / 2;
		    medioX = pyramid.getMapWidthInUnits() / 2;
		    dif = (medioX - playerX) * this.factor;

		    posCameraX = playerX + dif;
		}
	    }
	}

	cameraBack.position.x = posCameraX;
	cameraBack.position.y = pyramid.getMapHeightInUnits() * this.cameraOffsetY;
    }

}
