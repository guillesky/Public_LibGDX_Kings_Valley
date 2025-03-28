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

	public static final int ST_IDDLE = 0; // Inicializando
	public static final int ST_WALK = 11; // Andando
	public static final int ST_ONSTAIRS_POSITIVE = 2; // En una escalera pendiente positiva
	public static final int ST_ONSTAIRS_NEGATIVE = 3; // En una escalera pendiente negativa

	public static final int ST_JUMP_LEFT = 4; // Saltando izquierda
	public static final int ST_JUMP_TOP = 5; // Saltando arriba
	public static final int ST_JUMP_RIGHT = 6; // Saltando derecha
	public static final int ST_FALLING = 7; // Cayendo

	public static final int ST_DYING = 8; // Muriendo

	protected int state = GameCharacter.ST_IDDLE;

	protected Vector2 motionVector = new Vector2();
	protected float speedFall;
	protected float speedWalk;
	protected float speedWalkStairs;
	protected float speedJump;
	protected int stairInit = Constantes.It_none;
	protected float speedY = 0;
	protected float feetWidth;
	protected float feetHeight;
	protected Pyramid pyramid;
	private boolean lookRight = true;
	protected float animationDelta = 0;

	public GameCharacter(int type, float x, float y, float speedWalk, float speedWalkStairs, Pyramid pyramid)
	{
		super(type, x, y, 0, Config.getInstance().getCharacterWidth(), Config.getInstance().getCharacterHeight());
		this.speedFall = Config.getInstance().getCharacterSpeedFall();
		this.speedWalk = speedWalk;
		this.speedWalkStairs = speedWalkStairs;
		this.speedJump = Config.getInstance().getCharacterSpeedJump();
		this.feetHeight = Config.getInstance().getCharacterFeetHeight();
		this.feetWidth = Config.getInstance().getCharacterFeetWidth();
		this.pyramid = pyramid;
	}

	public void move(Vector2 v, boolean b, float deltaTime)
	{

		deltaTime *= Config.getInstance().getSpeedGame();
		if (this.state != GameCharacter.ST_ONSTAIRS_POSITIVE && this.state != GameCharacter.ST_ONSTAIRS_NEGATIVE)
		// Si no estoy en escalera
		{
			if (!this.isFloorDown()) // aplico gravedad
			{

				if (this.state == GameCharacter.ST_WALK || this.state == GameCharacter.ST_IDDLE)
				{

					this.state = GameCharacter.ST_FALLING;
					this.motionVector.x = 0;

					this.animationDelta = 0;
				} else
				{
					this.animationDelta += deltaTime;
				}
				this.motionVector.y += this.speedFall * deltaTime;

				if (this.motionVector.y < this.speedFall)
					this.motionVector.y = speedFall;

			}

			else // estoy caminando
			{

				if (v.x == 0)
				{
					if (this.state != ST_IDDLE)
					{
						this.animationDelta = 0;
						this.state = ST_IDDLE;
					} else
						this.animationDelta += deltaTime;

				} else
				{
					this.lookRight = v.x > 0;
					if (this.state != ST_WALK)
					{
						this.animationDelta = 0;
						this.state = ST_WALK;

					} else
					{
						this.animationDelta += deltaTime;
					}

				}
				this.motionVector.x = v.x * this.speedWalk;

				// this.motionVector.y = 0;
				if (b)
					this.doAction();
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
			{
				this.checkExitStair(v);
				this.animationDelta += deltaTime;
				this.lookRight = v.x > 0;
			}
		}

		Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
		if (this.state != GameCharacter.ST_ONSTAIRS_POSITIVE && this.state != GameCharacter.ST_ONSTAIRS_NEGATIVE) // Si
			// no
			// estoy
			// en
			this.colision5(escalado);

		this.x += escalado.x;
		this.y += escalado.y;
		this.checkOutLevel();
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
			this.lookRight = v.x > 0;
			this.animationDelta = 0;
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

	protected abstract void doAction();

	protected void doJump()
	{
		this.motionVector.y = this.speedJump;
		this.state = GameCharacter.ST_JUMP_TOP;
		this.animationDelta = 0;
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

	private boolean colisionUpRight(Vector2 vectMove)
	{
		return isCellSolid(this.x + this.getWidth() + vectMove.x, this.y + this.getHeight() + vectMove.y);
	}

	private boolean colisionUpLeft(Vector2 vectMove)
	{
		return isCellSolid(this.x + vectMove.x, this.y + this.getHeight() + vectMove.y);
	}

	private boolean colisionMiddleRight(Vector2 vectMove)
	{
		return isCellSolid(this.x + vectMove.x + this.getWidth(), this.y + vectMove.y + this.getHeight() / 2);
	}

	private boolean colisionMiddleLeft(Vector2 vectMove)
	{
		return isCellSolid(this.x + vectMove.x, this.y + vectMove.y + this.getHeight() / 2);
	}

	private boolean colisionDownLeftForLanding(Vector2 vectMove)
	{
		return colisionDown(this.x, vectMove);
	}

	private boolean colisionDownRightForLanding(Vector2 vectMove)
	{
		return colisionDown(this.x + this.width, vectMove);
	}

	private boolean colisionDown(float x, Vector2 vectMove)
	{
		boolean respuesta = false;
		if (isCellBlocked(x, this.y + vectMove.y))
		{
			float tileY = (int) ((y + vectMove.y) / Config.getInstance().getLevelTileHeightUnits());
			tileY = (tileY + 1) * Config.getInstance().getLevelTileHeightUnits();
			respuesta = (this.y > tileY && this.y + vectMove.y <= tileY);
		}
		return respuesta;
	}

	private void correctRight(Vector2 vectMove)
	{
		float aux = (int) ((this.x + this.getWidth() + vectMove.x) / Config.getInstance().getLevelTileWidthUnits());
		vectMove.x = (aux) * Config.getInstance().getLevelTileWidthUnits() - (this.getWidth() + 0.02f + this.x);
		if (this.motionVector.y < 30)
			this.motionVector.x = 0;

	}

	private void correctLeft(Vector2 vectMove)
	{
		float aux = (int) ((this.x + vectMove.x) / Config.getInstance().getLevelTileWidthUnits());
		vectMove.x = (aux + 1) * Config.getInstance().getLevelTileWidthUnits() + 0.2f - this.x;
		if (this.motionVector.y < 30)
			this.motionVector.x = 0;

	}

	private void correctUp(Vector2 vectMove)
	{
		this.motionVector.y = 0;
		int aux = (int) ((this.y + this.getHeight() + vectMove.y) / Config.getInstance().getLevelTileHeightUnits());
		vectMove.y = aux * Config.getInstance().getLevelTileHeightUnits() - (this.y + this.getHeight() + 0.1f);

	}

	private void correctDown(Vector2 vectMove)
	{

		float aux = (int) ((this.y + vectMove.y) / Config.getInstance().getLevelTileHeightUnits());
		vectMove.y = (aux + 1) * Config.getInstance().getLevelTileHeightUnits() - this.y;
		if (vectMove.x == 0)
			this.state = GameCharacter.ST_IDDLE;
		else
		{
			this.state = GameCharacter.ST_WALK;
			this.lookRight = vectMove.x >= 0;
		}
		this.motionVector.y = 0;

	}

	private boolean isCellBlocked(float x, float y)
	{
		return this.pyramid.getCell(x, y) != null;
	}

	private boolean isCellSolid(float x, float y)
	{
		TiledMapTileLayer.Cell cell = this.pyramid.getCell(x, y);
		return cell != null && cell.getTile().getId() < 220;
	}

	private boolean colision3(Vector2 vectMove)
	{
		if (this.state != GameCharacter.ST_WALK && this.state != GameCharacter.ST_IDDLE)
			this.checkLanding(vectMove);

		int r = -1;

		if (this.colisionUpRight(vectMove))
		{
			if (this.colisionUpLeft(vectMove))
			{
				this.correctUp(vectMove);
			} else
			{
				r = this.buscarColisionPorVertice(this.x + this.width, this.y + this.height, vectMove);
				if (r == Constantes.DOWN)
				{
					r = Constantes.RIGHT;
					System.out.println("ABERRACION");
				}
			}

		} else if (this.colisionUpLeft(vectMove))
		{
			r = this.buscarColisionPorVertice(this.x, this.y + this.height, vectMove);
			if (r == Constantes.DOWN)
			{
				r = Constantes.LEFT;
				// TODO
				System.out.println("ABERRACION");
				// TODO
			}
		} else if (this.isCellSolid(this.x + vectMove.x, this.y + vectMove.y)
				&& this.buscarColisionPorVertice(this.x, this.y, vectMove) == Constantes.LEFT)
			r = Constantes.LEFT;

		else if (this.isCellSolid(this.x + vectMove.x + this.width, this.y + vectMove.y)
				&& this.buscarColisionPorVertice(this.x + this.width, this.y, vectMove) == Constantes.RIGHT)
			r = Constantes.RIGHT;

		else if (this.colisionMiddleLeft(vectMove))
		{
			this.correctLeft(vectMove);
			r = -1;

		} else if (this.colisionMiddleRight(vectMove))
		{
			this.correctRight(vectMove);
			r = -1;

		}

		this.corrigeDirecciones(r, vectMove);

		/*
		 * if (this.isFloorDown()) this.correctDown();
		 */
		return r != -1;
	}

	private boolean colision4(Vector2 vectMove)
	{
		if (this.state != GameCharacter.ST_WALK && this.state != GameCharacter.ST_IDDLE)
			this.checkLanding(vectMove);

		int r = -1;

		if (this.colisionUpRight(vectMove))
		{
			if (this.colisionUpLeft(vectMove))
			{
				r = Constantes.UP;
			} else
			{
				r = this.buscarColisionPorVertice(this.x + this.width, this.y + this.height, vectMove);
				if (r == Constantes.DOWN)
				{
					r = Constantes.RIGHT;
					System.out.println("ABERRACION");
				}
			}

		} else if (this.colisionUpLeft(vectMove))
		{
			r = this.buscarColisionPorVertice(this.x, this.y + this.height, vectMove);
			if (r == Constantes.DOWN)
			{
				r = Constantes.LEFT;
				// TODO
				System.out.println("ABERRACION");
				// TODO
			}
		} else if (this.isCellSolid(this.x + vectMove.x, this.y + vectMove.y)
				&& this.buscarColisionPorVertice(this.x, this.y, vectMove) == Constantes.LEFT)
			r = Constantes.LEFT;

		else if (this.isCellSolid(this.x + vectMove.x + this.width, this.y + vectMove.y)
				&& this.buscarColisionPorVertice(this.x + this.width, this.y, vectMove) == Constantes.RIGHT)
			r = Constantes.RIGHT;

		else if (this.colisionMiddleLeft(vectMove))
		{

			r = Constantes.LEFT;

		} else if (this.colisionMiddleRight(vectMove))
		{

			r = Constantes.RIGHT;

		}

		this.corrigeDirecciones(r, vectMove);

		/*
		 * if (this.isFloorDown()) this.correctDown();
		 */
		return r != -1;
	}

	private boolean colision5(Vector2 vectMove)
	{
		if (this.state != GameCharacter.ST_WALK && this.state != GameCharacter.ST_IDDLE)
			this.checkLanding(vectMove);

		int r = -1;

		if (this.colisionUpRight(vectMove))
		{
			if (this.colisionUpLeft(vectMove))
			{
				r = Constantes.UP;
			} else if (this.colisionMiddleRight(vectMove))
			{
				r = Constantes.RIGHT;
			} else
			{
				r = this.buscarColisionPorVertice(this.x + this.width, this.y + this.height, vectMove);
				if (r == Constantes.DOWN)
				{
					r = Constantes.RIGHT;
					System.out.println("ABERRACION");
				}
			}

		} else if (this.colisionUpLeft(vectMove))
		{
			if (this.colisionMiddleLeft(vectMove))
			{
				r = Constantes.LEFT;
			} else
			{
				r = this.buscarColisionPorVertice(this.x, this.y + this.height, vectMove);
				if (r == Constantes.DOWN)
				{
					r = Constantes.LEFT;
					// TODO
					System.out.println("ABERRACION");
					// TODO
				}
			}
		} else if (this.isCellSolid(this.x + vectMove.x, this.y + vectMove.y)
				&& this.buscarColisionPorVertice(this.x, this.y, vectMove) == Constantes.LEFT)
			r = Constantes.LEFT;

		else if (this.isCellSolid(this.x + vectMove.x + this.width, this.y + vectMove.y)
				&& this.buscarColisionPorVertice(this.x + this.width, this.y, vectMove) == Constantes.RIGHT)
			r = Constantes.RIGHT;

		else if (this.colisionMiddleLeft(vectMove))
		{

			r = Constantes.LEFT;

		} else if (this.colisionMiddleRight(vectMove))
		{

			r = Constantes.RIGHT;

		}

		this.corrigeDirecciones(r, vectMove);

		/*
		 * if (this.isFloorDown()) this.correctDown();
		 */
		return r != -1;
	}

	private void corrigeDirecciones(int direction, Vector2 vectMove)
	{
		switch (direction)
		{
		case Constantes.UP:
			this.correctUp(vectMove);
			break;

		case Constantes.LEFT:
			this.correctLeft(vectMove);
			break;
		case Constantes.DOWN:
			this.correctDown(vectMove);

			break;
		case Constantes.RIGHT:
			this.correctRight(vectMove);
			break;

		}

	}

	protected void checkLanding(Vector2 vectMove)
	{
		int r = -1;
		if (this.colisionDownLeftForLanding(vectMove))
		{
			if (this.colisionDownRightForLanding(vectMove))
				this.correctDown(vectMove);
			else
			{
				r = this.buscarColisionPorVertice(this.x, this.y, vectMove);
			}
		} else if (this.colisionDownRightForLanding(vectMove))
		{
			r = this.buscarColisionPorVertice(this.x + this.width, this.y, vectMove);
		}
		this.corrigeDirecciones(r, vectMove);

	}

	private int buscarColisionPorVertice(float x, float y, Vector2 vectMove)
	{
		x += vectMove.x;
		y += vectMove.y;
		int r = -1;
		if (this.motionVector.x == 0)
		{
			if (this.motionVector.y > 0)
				r = Constantes.UP;
			else if (this.motionVector.y < 0)
			{
				r = Constantes.DOWN;
			}
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
		return r;

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

	public float getAnimationDelta()
	{
		return animationDelta;
	}

	public boolean isLookRight()
	{
		return lookRight;
	}

	private void checkOutLevel()
	{

		if (this.x < Config.getInstance().getLevelTileWidthUnits())
		{
			this.x = Config.getInstance().getLevelTileWidthUnits();
		} else

		{
			if (this.x > (this.pyramid.getMapWidthInTiles() - 1) * Config.getInstance().getLevelTileWidthUnits()
					- this.width)

				this.x = (this.pyramid.getMapWidthInTiles() - 1) * Config.getInstance().getLevelTileWidthUnits()
						- this.width;
		}

	}

	public LevelItem checkRectangleColision(ArrayList levelItems)
	{

		Iterator<LevelItem> it = levelItems.iterator();
		LevelItem respuesta = null;
		LevelItem item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !this.isColision(item));

		if (this.isColision(item))
		{
			respuesta = item;
		}

		return respuesta;
	}

	protected void checkGiratory(Vector2 v)
	{
		LevelItem giratory = this.checkRectangleColision(this.pyramid.getGiratorys());
		if (giratory != null)
		{
			GiratoryMechanism gm = this.pyramid.getGiratoryMechanism(giratory);

			if (this.canPassGiratoryMechanism(gm))
			{
				this.passGiratoryMechanism(gm);
			} else
				this.blockGiratory(gm);
		}
	}

	protected boolean isLocked()
	{

		return this.isLockedRight() && this.isLockedLeft();
	}

	protected boolean isLockedLeft()
	{
		return this.pyramid.getCell(this.x - Config.getInstance().getLevelTileWidthUnits(),
				this.y + Config.getInstance().getLevelTileHeightUnits()) != null
				|| this.pyramid.getCell(this.x - Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

	}

	protected boolean isLockedRight()
	{
		return this.pyramid.getCell(this.x + Config.getInstance().getLevelTileWidthUnits(),
				this.y + Config.getInstance().getLevelTileHeightUnits()) != null
				|| this.pyramid.getCell(this.x + Config.getInstance().getLevelTileWidthUnits(), this.y) != null;

	}

	protected abstract boolean canPassGiratoryMechanism(GiratoryMechanism giratoryMechanism);

	protected abstract void passGiratoryMechanism(GiratoryMechanism giratoryMechanism);

	protected void blockGiratory(GiratoryMechanism gm)
	{
		LevelItem g = gm.getLevelItem();
		float lado = g.x - this.x;

		this.motionVector.x = 0;
		if (lado < 0)
		{
			this.x = g.x + g.width;
		} else

		{
			this.x = g.x - this.width;
		}

	}

}
