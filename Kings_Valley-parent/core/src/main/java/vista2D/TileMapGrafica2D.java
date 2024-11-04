package vista2D;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import io.github.some_example_name.IMyApplicationnListener;
import modelo.Juego;
import modelo.LevelItem;
import modelo.Pyramid;
import util.Constantes;

public class TileMapGrafica2D implements IMyApplicationnListener
{

    private final String archiPlayer = "pics/vick.png";
    private final String archiColectables = "pics/colectables.png";

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private AssetManager manager;
    private SpriteBatch spriteBatch = new SpriteBatch();;
    private Array<MySpriteKV> instances = new Array<MySpriteKV>();
    private Array<AnimatedEntity2D> animatedEntities = new Array<AnimatedEntity2D>();
    private GameCharacterAnimated2D playerAnimated2D;

    private Animation<TextureRegion> animationPlayerWalk = null;
    private Animation<TextureRegion> animationPlayerStair = null;
    private Animation<TextureRegion> animationPlayerIddle = null;
    private Animation<TextureRegion> animationPlayerJump = null;
    private Animation<TextureRegion> animationPlayerFall = null;
    private Animation<TextureRegion> animationPlayerDeath = null;
    private Animation<TextureRegion> animationPlayerKnife = null;
    private Animation<TextureRegion> animationPlayerPicker = null;

    public TileMapGrafica2D(AssetManager manager)
    {
	this.manager = manager;
	this.manager.load(this.archiPlayer, Texture.class);
	this.manager.load(this.archiColectables, Texture.class);

    }

    private void loadAnimations()
    {
	int frameWidth = 16;
	int frameHeight = 20;
	float frameDuration = 0.1f;

	int colectableWidth = 10;
	int colectableHeight = 10;

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

	Texture spriteSheet = manager.get(this.archiPlayer, Texture.class);
	Texture colectablesSheet = manager.get(this.archiColectables, Texture.class);

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

	this.animationPlayerIddle = this.framesToAnimation(linearFrames, startIddle, countIddle, 0);
	this.animationPlayerFall = this.animationPlayerIddle;
	this.animationPlayerJump = this.animationPlayerIddle;
	this.animationPlayerWalk = this.framesToAnimation(linearFrames, startWalk, countWalk, frameDuration);
	this.animationPlayerStair = this.animationPlayerWalk;
	this.animationPlayerDeath = this.framesToAnimation(linearFrames, startDeath, countDeath, frameDuration);

	tmpFrames = TextureRegion.split(colectablesSheet, colectableWidth, colectableHeight);
	linearFrames.clear();
	for (int i = 0; i < tmpFrames.length; i++)
	{
	    for (int j = 0; j < tmpFrames[i].length; j++)
	    {
		linearFrames.add(tmpFrames[i][j]);
	    }
	}

	this.animationPlayerKnife = this.framesToAnimation(linearFrames, startKnife, countKnife, frameDuration);
	this.animationPlayerPicker = this.framesToAnimation(linearFrames, starPicker, countPicker, frameDuration);

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
	// TODO Auto-generated method stub

    }

    @Override
    public void removeGraphicElement(Object element)
    {
	ArrayIterator<MySpriteKV> it = this.instances.iterator();
	while (it.hasNext() && it.next().getLevelItem() != element)
	{

	}
	it.remove();

    }

    @Override
    public void create()
    {
	TiledMap map = Juego.getInstance().getCurrentPyramid().getMap();
	Pyramid pyramid = Juego.getInstance().getCurrentPyramid();
	camera = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
	camera.position.x = pyramid.getMapWidthInPixels() * .5f;
	this.calculateCamera();
	
	this.loadAnimations();
	renderer = new OrthogonalTiledMapRenderer(map);

	TiledMapTileSet tileSet = pyramid.getMap().getTileSets().getTileSet(0);

	Iterator<LevelItem> levelItems = Juego.getInstance().getCurrentPyramid().getLevelItems();
	while (levelItems.hasNext())

	{
	    LevelItem item = levelItems.next();
	    if (item.getType() == Constantes.It_jewel)
	    {
		MySpriteKV sprite = new MySpriteKV(tileSet.getTile(item.getP0()).getTextureRegion(), item);
		this.instances.add(sprite);

	    } else if (item.getType() == Constantes.It_dagger)
	    {
		this.animatedEntities.add(new AnimatedEntity2D(item, this.animationPlayerKnife));
	    } else if (item.getType() == Constantes.It_picker)
		this.animatedEntities.add(new AnimatedEntity2D(item, this.animationPlayerPicker));
	}
	this.playerAnimated2D = new GameCharacterAnimated2D(pyramid.getPlayer(), animationPlayerIddle,
		animationPlayerWalk, animationPlayerStair, animationPlayerJump, animationPlayerFall,
		animationPlayerDeath);
	this.animatedEntities.add(this.playerAnimated2D);

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
	    this.calculateCamera();
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
	ArrayIterator<MySpriteKV> it = this.instances.iterator();
	while (it.hasNext())
	{
	    MySpriteKV mskv = it.next();
	    mskv.updateElement(null);
	    mskv.draw(spriteBatch);
	}

	ArrayIterator<AnimatedEntity2D> it2 = this.animatedEntities.iterator();
	while (it2.hasNext())
	{
	    AnimatedEntity2D ae2d = it2.next();
	    ae2d.update(Juego.getInstance().getDelta());
	    ae2d.render(spriteBatch);
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
	float aux_X=Juego.getInstance().getCurrentPyramid().getPlayer().getX();
	
	
	if(aux_X>=(camera.viewportWidth/2) && aux_X+(camera.viewportWidth/2)<=pyramid.getMapWidthInPixels())
	    camera.position.x = Juego.getInstance().getCurrentPyramid().getPlayer().getX();
	
	
	camera.position.y = pyramid.getMapHeightInPixels() * .55f;
    }
}
