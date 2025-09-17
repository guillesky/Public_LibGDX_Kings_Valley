package vista2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
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

import i18n.Messages;
import mainPackage.IMyApplicationListener;
import modelo.DrawableElement;
import modelo.game.Game;
import modelo.gameCharacters.mummys.Mummy;
import modelo.level.GiratoryMechanism;
import modelo.level.LevelObject;
import modelo.level.PairInt;
import modelo.level.Pyramid;
import modelo.level.Stair;
import modelo.level.TrapMechanism;
import modelo.level.dagger.Dagger;
import modelo.level.door.Door;
import util.Config;
import util.Constantes;

public class TileMapGrafica2D implements IMyApplicationListener
{
	public static final int IDDLE = 0;
	public static final int FALL = 1;
	public static final int WALK = 2;
	public static final int DEATH = 3;
	public static final int JUMP = 4;
	public static final int PICKING = 5;
	public static final int THROW_DAGGER = 5;
	public static final int APPEAR = 5;

	protected OrthographicCamera camera;
	protected OrthographicCamera cameraUI;
	protected OrthogonalTiledMapRenderer renderer;

	protected Array<MySpriteKV> arrayOfMySpritesKV = new Array<MySpriteKV>();
	protected Array<AnimatedEntity2D> animatedEntities = new Array<AnimatedEntity2D>();
	private HashMap<LevelObject, AnimatedEntity2D> hashMapLevelAnimation = new HashMap<LevelObject, AnimatedEntity2D>();
	private HashMap<TrapMechanism, AnimatedTrapKV2> hashMapTrapAnimation = new HashMap<TrapMechanism, AnimatedTrapKV2>();
	protected Array<AnimatedTrapKV2> animatedTraps = new Array<AnimatedTrapKV2>();
	protected ArrayList<AbstractAnimatedDoor2D> animatedDoor2D = new ArrayList<AbstractAnimatedDoor2D>();
	protected SpriteBatch spriteBatch = new SpriteBatch();
	protected BitmapFont font48 = new BitmapFont();
	private BitmapFont font24 = new BitmapFont();

	private int tileWidth;
	private int tileHeight;
	private float scaleFactor = 1;
	protected GraphicsFileLoader graphicsFileLoader;
	protected Texture pixel;
	private boolean debug = false;
	private AnimatedPickedCell animatedPickedCell;
	private String fileNameTileSetChanged = null;
	private Texture newTilesetTexture = null;
	private Float originalWidth = null;
	protected AnimatedEnteringDoor2D animatedEnteringDoor2D;
	protected PlayerAnimated2D playerAnimated2D;
	private float timeToEnterLevel = 2f;
	private float timeToExitLevel = 1f;
	private float timeDying = 1f;
	protected float cameraOffsetY = (12f / 22f);
	private float timeToEndGame = 2f;
	private OrthographicCamera cameraBack;
	private float factor;
	float[] paramFloat = new float[4];

	public TileMapGrafica2D(GraphicsFileLoader graphicsFileLoader, float factor)
	{

		this.graphicsFileLoader = graphicsFileLoader;
		this.graphicsFileLoader.loadAnimations(timeDying);
		this.factor = factor;

	}

	@Override
	public void addGraphicElement(DrawableElement element)
	{
		
		if (element.getType() == Constantes.DRAWABLE_LEVEL_ITEM)
		{
			LevelObject item = (LevelObject) element.getDrawable();
			AnimatedEntity2D animatedEntity2D = null;
			int id = 0;
			if (item.getType() == Constantes.It_jewel)
				id = item.getP0();
			else
				id = item.getType();
			animatedEntity2D = new AnimatedEntity2D(item, this.graphicsFileLoader.getAnimations().get(id));
			this.animatedEntities.add(animatedEntity2D);
			this.hashMapLevelAnimation.put(item, animatedEntity2D);
		} else if (element.getType() == Constantes.DRAWABLE_TRAP)
		{
			TrapMechanism trapMech = (TrapMechanism) element.getDrawable();
			AnimatedTrapKV2 atrapKV = new AnimatedTrapKV2(trapMech);
			this.hashMapTrapAnimation.put(trapMech, atrapKV);
			this.animatedTraps.add(atrapKV);

		} else if (element.getType() == Constantes.DRAWABLE_GYRATORY)
		{

			GiratoryMechanism gm = (GiratoryMechanism) element.getDrawable();
			AnimatedGiratory2D a;
			a = new AnimatedGiratory2D(gm, this.graphicsFileLoader.getNewAnimationGiratory(gm.getHeightInTiles()));

			this.animatedEntities.add(a);
			this.hashMapLevelAnimation.put(gm.getLevelObject(), a);
		} else if (element.getType() == Constantes.DRAWABLE_PICKING_CELL)
		{
			PairInt pairInt = (PairInt) element.getDrawable();

			this.animatedPickedCell.getLevelObject().setX(pairInt.getX() * this.tileWidth);
			this.animatedPickedCell.getLevelObject().setY(pairInt.getY() * this.tileHeight);
			this.animatedEntities.add(this.animatedPickedCell);
			this.animatedPickedCell.resetTime(Game.getInstance().getDelta());

		} else if (element.getType() == Constantes.DRAWABLE_FLYING_DAGGER)
		{
			Dagger dagger = (Dagger) element.getDrawable();
			AnimatedDagger2D animatedEntity2D = new AnimatedDagger2D(dagger,
					this.graphicsFileLoader.getAnimations().get(dagger.getP0()),
					this.graphicsFileLoader.getAnimations().get(Constantes.DRAWABLE_FLYING_DAGGER));
			this.animatedEntities.add(animatedEntity2D);
			this.hashMapLevelAnimation.put(dagger, animatedEntity2D);

		}

		else if (element.getType() == Constantes.DRAWABLE_EXIT_DOOR)
		{
			Door door = (Door) element.getDrawable();
			this.animatedEnteringDoor2D = new AnimatedEnteringDoor2D(door, this.graphicsFileLoader.getDoorPassage(),
					this.graphicsFileLoader.getDoorSingleLeft(), this.graphicsFileLoader.getDoorSingleRight(),
					this.graphicsFileLoader.getAnimationDoorLever());
		}
	}

	@Override
	public void removeGraphicElement(DrawableElement element)
	{
		if (element.getType() == Constantes.DRAWABLE_LEVEL_ITEM)
		{
			LevelObject item = (LevelObject) element.getDrawable();
			AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(item);
			this.animatedEntities.removeValue(animatedEntity2D, true);
			this.hashMapLevelAnimation.remove(item);
		} else if (element.getType() == Constantes.DRAWABLE_TRAP)
		{
			TrapMechanism trapMech = (TrapMechanism) element.getDrawable();
			AnimatedTrapKV2 atrapKV = this.hashMapTrapAnimation.get(trapMech);
			this.animatedTraps.removeValue(atrapKV, true);
			this.hashMapTrapAnimation.remove(trapMech);
		} else if (element.getType() == Constantes.DRAWABLE_PICKING_CELL)
		{
			this.animatedEntities.removeValue(this.animatedPickedCell, true);
		} else if (element.getType() == Constantes.DRAWABLE_GYRATORY)
		{
			LevelObject giratory = (LevelObject) element.getDrawable();
			AnimatedEntity2D animatedEntity2D = this.hashMapLevelAnimation.get(giratory);

			this.animatedEntities.removeValue(animatedEntity2D, true);
			this.hashMapLevelAnimation.remove(giratory);
		}
	}

	@Override
	public void create()
	{
		this.arrayOfMySpritesKV = new Array<MySpriteKV>();
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
				this.arrayOfMySpritesKV.add(new MySpriteKV(map.getTileSets().getTile(item.getType()).getTextureRegion(), item));
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

		}

		if (this.fileNameTileSetChanged != null)
		{
			this.changeTileSet();
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

		cameraBack = new OrthographicCamera(pyramid.getMapHeightInPixels() * 4 / 3, pyramid.getMapHeightInPixels());
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
			this.arrayOfMySpritesKV.add(new MySpriteKV(map.getTileSets().getTile(item.getP0() + 250).getTextureRegion(), item));
			item = escaleras.get(i).getUpStair();
			this.arrayOfMySpritesKV.add(new MySpriteKV(map.getTileSets().getTile(item.getP0() + 250).getTextureRegion(), item));

		}
	}

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

	public void changeTileSet()
	{
		String fileName = this.graphicsFileLoader.getArchiNewTileset();
		System.out.println("cambie");
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
		newWidth = this.newTilesetTexture.getWidth() / 18;
		newHeight = this.newTilesetTexture.getHeight() / 8;
		this.scaleFactor = originalWidth / newWidth;
		TextureRegion[][] splitTiles = TextureRegion.split(newTilesetTexture, newWidth, newHeight);

		// Crear el nuevo TiledMapTileSet y agregar los tiles
		TiledMapTileSet newTileSet = new TiledMapTileSet();
		for (int row = 0; row < splitTiles.length; row++)
		{
			for (int col = 0; col < splitTiles[row].length; col++)
			{
				StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[row][col]);
				tile.setId(row * splitTiles[row].length + col + 1);
				newTileSet.putTile(tile.getId(), tile);
			}
		}

		// Agregar el nuevo tileset al mapa
		map.getTileSets().addTileSet(newTileSet);

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
		float alto = pyramid.getMapHeightInPixels() + Config.getInstance().getLevelTileHeightUnits() * 2;
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
		this.calculateTextureRegionSky(width, height, pyramid.getMapWidthInTiles());

		
		
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
		ArrayIterator<MySpriteKV> it = this.arrayOfMySpritesKV.iterator();
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

	    
	    
	
	
	protected void drawUI()
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

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void dispose()
	{
		this.renderer.dispose();

		this.spriteBatch.dispose();

		this.font48.dispose();
		this.font24.dispose();
		this.pixel.dispose();
		this.graphicsFileLoader.dispose();

	}

	protected boolean calculateCamera()
	{
		boolean wasChange = false;
		Pyramid pyramid = Game.getInstance().getCurrentLevel().getPyramid();
		float aux_X = Game.getInstance().getCurrentLevel().getPlayer().getX();

		if (aux_X >= (camera.viewportWidth / 2) && aux_X + (camera.viewportWidth / 2) <= pyramid.getMapWidthInPixels())
		{
			camera.position.x = Game.getInstance().getCurrentLevel().getPlayer().getX();
			wasChange = true;
		}

		camera.position.y = pyramid.getMapHeightInPixels() * this.cameraOffsetY;
		return wasChange;
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
		camera.position.y = pyramid.getMapHeightInPixels() * this.cameraOffsetY;
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

	@Override
	public float getTimeToEndGame()
	{
		// TODO Auto-generated method stub
		return this.timeToEndGame;
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
