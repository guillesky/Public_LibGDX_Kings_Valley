package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import util.Config;
import util.Constantes;

public abstract class GameCharacter extends LevelItem
{

	public static final int ST_INIT = 0; // Inicializando
	public static final int ST_WALK = 1; // Andando
	public static final int ST_ONSTAIRS_POSITIVE = 2; // En una escalera pendiente positiva
	public static final int ST_ONSTAIRS_NEGATIVE = 3; // En una escalera pendiente negativa

	public static final int ST_SUMP_LEFT = 4; // Saltando izquierda
	public static final int ST_JUMP_TOP = 5; // Saltando arriba
	public static final int ST_JUMP_RIGHT = 6; // Saltando derecha
	public static final int ST_FALLING = 7; // Cayendo

	public static final int ST_DYING = 8; // Muriendo

	protected int state = GameCharacter.ST_INIT;
	protected Vector2 inputVector = new Vector2();
	protected Vector2 motionVector = new Vector2();
	protected int speedFall;
	protected int speedWalk;
	protected int speedWalkStairs;
	protected int speedJump;
	protected int stairInit = Constantes.It_none;
	protected float speedY = 0;
	protected float feetWidth;
	protected float feetHeight;
	protected Pyramid pyramid;
	private int cont = 0;

	public GameCharacter(int type, float x, float y, int p0, int p1, float width, float height, int speedFall,
			int speedWalk, int speedWalkStairs, int speedJump, Pyramid pyramid)
	{
		super(type, x, y, p0, p1, width, height);
		this.speedFall = speedFall;
		this.speedWalk = speedWalk;
		this.speedWalkStairs = speedWalkStairs;
		this.speedJump = speedJump;
		this.feetHeight = Config.getInstance().getCharacterFeetHeight();
		this.feetWidth = Config.getInstance().getCharacterFeetWidth();
		this.pyramid = pyramid;
	}

	public void move(Vector2 v, boolean b, float deltaTime)
	{
		this.cont++;
		deltaTime *= Config.getInstance().getSpeedGame();
		if (this.state != GameCharacter.ST_ONSTAIRS_POSITIVE && this.state != GameCharacter.ST_ONSTAIRS_NEGATIVE) // Si
																													// no
																													// estoy
		// en
		// escalera
		{
			if (!this.isFloorDown()) // aplico gravedad
			{

				if (this.state == GameCharacter.ST_WALK)
				{

					this.state = GameCharacter.ST_FALLING;
					this.motionVector.x = 0;
				}
				this.motionVector.y += this.speedFall * deltaTime;
				if (this.motionVector.y < this.speedFall)
					this.motionVector.y = speedFall;

			}

			else // estoy caminando
			{

				this.motionVector.x = v.x * this.speedWalk;

				this.motionVector.y = 0;
				if (b)
					this.doJump();
				if (v.y != 0 && v.x != 0)
					this.checkEnterStair(v);

			}
		} else // estoy en escalera
		{
			this.motionVector.x = v.x * this.speedWalkStairs;
			if (this.state == GameCharacter.ST_ONSTAIRS_POSITIVE)
			{
				this.motionVector.y = v.x * this.speedWalkStairs;

			} else
			{
				this.motionVector.y = -v.x * this.speedWalkStairs;
			}
			if (v.x != 0)
				this.checkExitStair(v);
		}
		Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
		this.x += escalado.x;
		this.y += escalado.y;

		if (this.state != GameCharacter.ST_ONSTAIRS_POSITIVE && this.state != GameCharacter.ST_ONSTAIRS_NEGATIVE) // Si
																													// no
																													// estoy
			// en

			this.colision3();

	}

	private void checkExitStair(Vector2 v)
	{
		LevelItem escalera = null;
		if (this.state == GameCharacter.ST_ONSTAIRS_POSITIVE)
		{
			if (v.x > 0)
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_dl());
			} else
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_ur());
			}
		} else
		{
			if (v.x > 0)
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_ul());
			} else
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_dr());
			}

		}
		if (escalera != null)
		{
			this.state = GameCharacter.ST_WALK;
			this.y = escalera.y;
			this.motionVector.y = 0;
		}
	}

	private void checkEnterStair(Vector2 v)
	{
		LevelItem escalera = null;
		if (v.y > 0)
		{
			if (v.x > 0)
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_ur());
				if (escalera != null)
					this.state = GameCharacter.ST_ONSTAIRS_POSITIVE;
			} else
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_ul());
				if (escalera != null)
					this.state = GameCharacter.ST_ONSTAIRS_NEGATIVE;
			}
		} else
		{
			if (v.x > 0)
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_dr());
				if (escalera != null)
					this.state = GameCharacter.ST_ONSTAIRS_NEGATIVE;
			} else
			{
				escalera = this.checkItemFeetColision(this.pyramid.getStairs_dl());
				if (escalera != null)
					this.state = GameCharacter.ST_ONSTAIRS_POSITIVE;
			}
		}

	}

	private void doJump()
	{
		this.motionVector.y = this.speedJump;
		this.state = GameCharacter.ST_JUMP_TOP;
	}

	public int getState()
	{
		return state;
	}

	public boolean isFloorDown()
	{
		return ((isCellBlocked(this.x + this.getWidth(), this.y - 0.00001f * this.height)
				&& isCellBlocked(this.x + this.getWidth(), this.y - this.height * 0.25f))
				|| (isCellBlocked(this.x, this.y - 0.00001f * this.height)
						&& isCellBlocked(this.x, this.y - this.height * 0.25f)));
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

	private boolean colisionDownLeftForLanding()
	{
		return (isCellBlocked(this.x, this.y) && isCellBlocked(this.x, this.y - this.height * .25f));
	}

	private boolean colisionDownRightForLanding()
	{
		return (isCellBlocked(this.x + this.getWidth(), this.y)
				&& isCellBlocked(this.x + this.getWidth(), this.y - this.height * .25f));
	}

	private void correctRight()
	{

		float oldX = this.x;
		float aux = (int) ((this.x + this.getWidth()) / Config.getInstance().getLevelTileWidthUnits());
		this.x = (aux) * Config.getInstance().getLevelTileWidthUnits() - this.getWidth() - 0.2f;
		System.out.println(
				"dolx: " + oldX + "X:      " + this.x + " cont: " + this.cont + " Motion: " + this.motionVector);
		if (this.motionVector.y < 30)
			this.motionVector.x = 0;

	}

	private void correctLeft()
	{
		float oldX = this.x;

		float aux = (int) (this.x / Config.getInstance().getLevelTileWidthUnits());
		this.x = (aux + 1) * Config.getInstance().getLevelTileWidthUnits() + 0.2f;
		System.out.println(
				"dolx: " + oldX + "X:      " + this.x + " cont: " + this.cont + " Motion: " + this.motionVector);
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
		this.state = GameCharacter.ST_WALK;
		this.motionVector.y = 0;

	}

	private TiledMapTileLayer.Cell getCell(float x, float y)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) this.pyramid.getMap().getLayers().get("front");
		TiledMapTileLayer.Cell cell = layer.getCell((int) (x / Config.getInstance().getLevelTileWidthUnits()),
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

	private void colision2()
	{
		if (this.colisionMiddleLeft())
		{
			this.correctLeft();
			System.out.println("MIDDLE LEFT");
		} else if (this.colisionMiddleRight())
		{
			this.correctRight();
			System.out.println("MIDDLE ROGTH");
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
		/*
		 * if (this.isFloorDown()) this.correctDown();
		 */
	}

	private void colision3()
	{
		if (this.state != ST_WALK)
			this.checkLanding();
		
		if (this.colisionDownLeft())
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

		if (this.colisionMiddleLeft())
		{
			this.correctLeft();
			System.out.println("MIDDLE LEFT");
		} else if (this.colisionMiddleRight())
		{
			this.correctRight();
			System.out.println("MIDDLE ROGTH");
		}

		/*
		 * if (this.isFloorDown()) this.correctDown();
		 */
	}

	protected void checkLanding()
	{
		System.out.println("FLY");
		if (this.colisionDownLeftForLanding())
		{
			if (this.colisionDownRightForLanding())
				this.correctDown();
			else
				this.buscarColisionPorVertice(this.x, this.y);

		} else if (this.colisionDownRightForLanding())
			this.buscarColisionPorVertice(this.x + this.width, this.y);

	}

	private void buscarColisionPorVertice(float x, float y)
	{
		System.out.println("VERTICE");
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
				if (this.motionVector.y >= 0)
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
			System.out.println("VERTICE LEFT");
			break;
		case Constantes.RIGHT:
			this.correctRight();
			System.out.println("VERTICE RIGHT");
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

	public LevelItem checkItemFeetColision(ArrayList<LevelItem> levelItems)
	{

		Iterator<LevelItem> it = levelItems.iterator();
		LevelItem respuesta = null;
		LevelItem item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !this.isFeetColision(item));

		if (this.isFeetColision(item))
		{
			respuesta = item;
		}

		return respuesta;
	}

}
