package vista2D;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array.ArrayIterator;

import i18n.Messages;
import modelo.game.Game;
import modelo.level.Pyramid;
import util.Config;

public class TileMapGrafica2D_PARALAX extends TileMapGrafica2D
{
    private OrthographicCamera cameraBack;
    private float factor;
    float[] paramFloat = new float[4];

    public TileMapGrafica2D_PARALAX(GraphicsFileLoader graphicsFileLoader, float factor)
    {
	super(graphicsFileLoader);
	this.factor = factor;
    }

    @Override
    public void create()
    {
	super.create();

	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	cameraBack = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
	this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

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
	this.drawUI();

	
    }

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

    private void drawItemsCharactersMechanism()
    {
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

	if (Game.getInstance().getState() == Game.ST_GAME_PLAYING
		|| Game.getInstance().getState() == Game.ST_GAME_DYING)
	{

	    this.playerAnimated2D.updateElement(Game.getInstance().getDelta());
	    this.playerAnimated2D.render(spriteBatch);
	} else if(Game.getInstance().getState() != Game.ST_ENDING)
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
	this.spriteBatch.end();
    }

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

    private void drawBackground()
    {
	spriteBatch.setProjectionMatrix(this.cameraUI.combined);
	this.spriteBatch.begin();
	this.spriteBatch.setColor(Color.WHITE);
	this.spriteBatch.draw(this.graphicsFileLoader.getSkyTexture(), this.paramFloat[0], this.paramFloat[1],
		this.paramFloat[2], this.paramFloat[3]);
	this.spriteBatch.end();

    }

    private void calculateCameraBack()
    {
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
	float playerX = Game.getInstance().getCurrentLevel().getPlayer().getX();
	float medioX = pyramid.getMapWidthInPixels() / 2;
	float dif = (medioX - playerX) * factor;
	float aux_X = playerX + dif;

	if (playerX >= (cameraBack.viewportWidth / 2)
		&& playerX + (cameraBack.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
	    cameraBack.position.x = aux_X;

	cameraBack.position.y = pyramid.getMapHeightInPixels() * this.cameraOffsetY;
    }

    @Override
    public void resize(int width, int height)
    {

	super.resize(width, height);
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();

	if (height != 0 && this.cameraBack != null)
	{

	    float alto = pyramid.getMapHeightInPixels() + Config.getInstance().getLevelTileHeightUnits() * 2;
	    float ancho = alto * width / height;
	    cameraBack.setToOrtho(false, ancho, alto);
	    this.calculateCameraBackFull();
	}
	this.calculateTextureRegionSky(width, height, pyramid.getMapWidthInTiles());

    }

    private void calculateTextureRegionSky(int width, int height, int mapWidthInTiles)
    {

	float cameraWidth = this.cameraUI.viewportWidth;

	float cameraTileHeight = this.cameraUI.viewportHeight / 24;
	float cameraTileWidth = Config.getInstance().getLevelTileWidthUnits()
		/ Config.getInstance().getLevelTileHeightUnits() * cameraTileHeight;

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

    private void calculateCameraBackFull()
    {
	float posCameraX = 0;
	Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();

	float playerX = Game.getInstance().getCurrentLevel().getPlayer().getX();
	float medioX = pyramid.getMapWidthInPixels() / 2;
	float dif = (medioX - playerX) * this.factor;
	float aux_X = playerX + dif;

	if (playerX >= (cameraBack.viewportWidth / 2)
		&& playerX + (cameraBack.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
	    posCameraX = aux_X;

	else
	{
	    if (cameraBack.viewportWidth >= pyramid.getMapWidthInPixels())
		posCameraX = pyramid.getMapWidthInPixels() * .5f;
	    else
	    {
		if (playerX < pyramid.getMapWidthInPixels() / 2)
		{

		    playerX = cameraBack.viewportWidth / 2;
		    medioX = pyramid.getMapWidthInPixels() / 2;
		    dif = (medioX - playerX) * this.factor;
		    posCameraX = playerX + dif;

		} else
		{
		    playerX = pyramid.getMapWidthInPixels() - cameraBack.viewportWidth / 2;
		    medioX = pyramid.getMapWidthInPixels() / 2;
		    dif = (medioX - playerX) * this.factor;

		    posCameraX = playerX + dif;
		}
	    }
	}

	cameraBack.position.x = posCameraX;
	cameraBack.position.y = pyramid.getMapHeightInPixels() * this.cameraOffsetY;
    }

}
