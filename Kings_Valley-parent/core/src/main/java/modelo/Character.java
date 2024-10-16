package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import util.Config;
import util.Constantes;

public abstract class Character extends LevelItem
{

	public static final int ST_INIT = 0; // Inicializando
	public static final int ST_WALK = 1; // Andando
	public static final int ST_ONSTAIRS = 2; // En una escalera
	public static final int ST_FALLING = 3; // Cayendo
	public static final int ST_SUMP_LEFT = 4; // Saltando izquierda
	public static final int ST_JUMP_TOP = 5; // Saltando arriba
	public static final int ST_JUMP_RIGHT = 6; // Saltando derecha
	public static final int ST_DYING = 7; // Muriendo

	protected int state = Character.ST_INIT;
	protected Vector2 inputVector = new Vector2();
	protected Vector2 motionVector = new Vector2();
	protected int speedFall;
	protected int speedWalk;
	protected int speedWalkStairs;
	protected int speedJump;
	protected int stairInit = Constantes.It_none;
	protected TiledMapTileLayer layer;
	protected float speedY = 0;
	protected float feetWidth;
	protected float feetHeight;

	public Character(int type, float x, float y, int p0, int p1, float width, float height, TiledMapTileLayer layer,
			int speedFall, int speedWalk, int speedWalkStairs, int speedJump)
	{
		super(type, x, y, p0, p1, width, height);
		this.layer = layer;
		this.speedFall = speedFall;

		this.speedWalk = speedWalk;
		this.speedWalkStairs = speedWalkStairs;
		this.speedJump = speedJump;
		this.feetHeight = Config.getInstance().getCharacterFeetHeight();
		this.feetWidth = Config.getInstance().getCharacterFeetWidth();
	}

	public void move(Vector2 v, boolean b, float deltaTime)
	{
		deltaTime *= Config.getInstance().getSpeedGame();

		if (!this.isFloorDown()) // aplico gravedad
		{

			if (this.state == Character.ST_WALK)
			{

				this.state = Character.ST_FALLING;
				this.motionVector.x = 0;
			}
			this.motionVector.y += this.speedFall * deltaTime;
			if (this.motionVector.y < this.speedFall)
				this.motionVector.y = speedFall;

		}

		else
		{
			this.motionVector.x = v.x * this.speedWalk;

			this.state = Character.ST_WALK;
			this.motionVector.y = 0;
			if (b)
				this.doJump();

		}

		Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
		this.x += escalado.x;
		this.y += escalado.y;
		this.colision3();

	}

	private void doJump()
	{
		this.motionVector.y = this.speedJump;
		this.state = Character.ST_JUMP_TOP;
	}

	public int getState()
	{
		return state;
	}

	public boolean isFloorDown()
	{
		return ((isCellBlocked(this.x + this.getWidth(), this.y - 0.00001f)
				&& !isCellBlocked(this.x + this.getWidth(), this.y + 0.5f))
				|| (isCellBlocked(this.x, this.y - 0.00001f) && !isCellBlocked(this.x, this.y + 0.5f)));
	}

	public boolean isFloorDown2()
	{
		return (isCellBlocked(this.x + this.getWidth(), this.y - 0.00001f) || isCellBlocked(this.x, this.y - 0.00001f));
	}

	private boolean colisionUpRight()
	{
		return isCellSolid(this.x + this.getWidth(), this.y + this.getHeight());
	}

	private boolean colisionUpLeft()
	{
		return isCellSolid(this.x, this.y + this.getHeight());
	}

	private boolean colisionMiddleRight()
	{
		return isCellSolid(this.x + this.getWidth(), this.y + this.getHeight() / 2);
	}

	private boolean colisionMiddleLeft()
	{
		return isCellSolid(this.x, this.y + this.getHeight() / 2);
	}

	private boolean colisionDownLeft()
	{
		return isCellSolid(this.x, this.y);
	}

	private boolean colisionDownRight()
	{
		return isCellSolid(this.x + this.getWidth(), this.y);
	}

	private void correctRight()
	{

		float aux = (int) ((this.x + this.getWidth()) / Config.getInstance().getLevelTileWidthUnits());
		this.x = (aux) * Config.getInstance().getLevelTileWidthUnits() - this.getWidth() - 0.2f;
		if (this.motionVector.y < 30)
			this.motionVector.x = 0;
	}

	private void correctLeft()
	{

		float aux = (int) (this.x / Config.getInstance().getLevelTileWidthUnits());
		this.x = (aux + 1) * Config.getInstance().getLevelTileWidthUnits() + 0.2f;
		if (this.motionVector.y < 30)
			this.motionVector.x = 0;
	}

	private void correctUp()
	{
		this.motionVector.y = 0;
		int aux = (int) ((this.y + this.getHeight()) / Config.getInstance().getLevelTileHeightUnits());
		this.y = aux * Config.getInstance().getLevelTileHeightUnits() - this.getHeight() - 0.1f;
	}

	private void correctDown()
	{

		float aux = (int) (this.y / Config.getInstance().getLevelTileHeightUnits());
		this.y = (aux + 1) * Config.getInstance().getLevelTileHeightUnits();
		this.state = Character.ST_WALK;
		this.motionVector.y = 0;

	}

	private TiledMapTileLayer.Cell getCell(float x, float y)
	{
		TiledMapTileLayer.Cell cell = this.layer.getCell((int) (x / Config.getInstance().getLevelTileWidthUnits()),
				(int) (y / Config.getInstance().getLevelTileHeightUnits()));
		return cell;
	}

	private boolean isCellBlocked(float x, float y)
	{
		return this.getCell(x, y) != null;
	}

	private boolean isCellSolid(float x, float y)
	{
		TiledMapTileLayer.Cell cell = this.getCell(x, y);
		return cell != null && cell.getTile().getId() < 100;
	}

	private void colision3()
	{
		if (this.colisionMiddleLeft())
		{

			this.correctLeft();

		} else if (this.colisionMiddleRight())
		{
			this.correctRight();
		}

		else if (this.colisionDownLeft())
		{
			if (this.colisionDownRight())
			{
				this.correctDown();
			} else if (this.colisionUpLeft())
			{
				this.correctLeft();
			} else
			{
				this.buscarColisionPorVertice(this.x, this.y);
			}
		} else if (this.colisionUpRight())
		{
			if (this.colisionDownRight())
			{
				this.correctRight();
			} else if (this.colisionUpLeft())
			{
				this.correctUp();
			} else
			{
				this.buscarColisionPorVertice(this.x + this.width, this.y + this.height);

			}

		} else if (this.colisionDownRight())
		{
			this.buscarColisionPorVertice(this.x + this.width, this.y);

		} else if (this.colisionUpLeft())
		{
			this.buscarColisionPorVertice(this.x, this.y + this.height);
		}
	}

	private void buscarColisionPorVertice(float x, float y)
	{
		int r = 0;
		if (this.motionVector.x == 0)
		{
			if (this.motionVector.y > 0)
				r = Constantes.UP;
			else
				r = Constantes.DOWN;
		}

		else
		{

			float m = this.motionVector.y / this.motionVector.x;
			float b = y - x * m;
			float valorX;
			int tileX = (int) (x / Config.getInstance().getLevelTileWidthUnits());
			int tileY = (int) (y / Config.getInstance().getLevelTileHeightUnits());

			int respuestaLateral = 0;
			if (this.motionVector.x > 0)
			{
				valorX = tileX * Config.getInstance().getLevelTileWidthUnits();
				respuestaLateral = Constantes.RIGHT;
			} else
			{
				valorX = (tileX + 1) * Config.getInstance().getLevelTileWidthUnits();
				respuestaLateral = Constantes.LEFT;
			}

			float yBuscado = m * valorX + b;
			float abajo = tileY * Config.getInstance().getLevelTileHeightUnits();
			float arriba = (tileY + 1) * Config.getInstance().getLevelTileHeightUnits();
			if (abajo <= yBuscado && yBuscado <= arriba)
				r = respuestaLateral;
			else
			{
				if (this.motionVector.y > 0 && y != this.y)
					r = Constantes.UP;
				else
					r = Constantes.DOWN;
			}

		}

		switch (r)
		{
		case Constantes.UP:
			this.correctUp();
			break;
		case Constantes.DOWN:
			this.correctDown();
			break;
		case Constantes.LEFT:
			this.correctLeft();
			break;
		case Constantes.RIGHT:
			this.correctRight();
			break;

		}

	}

	public boolean isFeetColision(Rectangle another)
	{
		float mitad = this.x + this.width / 2;
		mitad -= this.feetWidth / 2;
		Rectangle feet = new Rectangle(mitad, y, this.feetWidth, this.feetHeight);
		return LevelItem.rectangleColision(feet, another);
	}
}
