package modelo;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import util.Constantes;

public class Character extends LevelItem
{

	protected static final int st_init = 0; // Inicializando
	protected static final int st_walk = 1; // Andando
	protected static final int st_onstairs = 2; // En una escalera
	protected static final int st_falling = 3; // Cayendo
	protected static final int st_jump_left = 4; // Saltando izquierda
	protected static final int st_jump_top = 5; // Saltando arriba
	protected static final int st_jump_right = 6; // Saltando derecha
	protected static final int st_dying = 7; // Muriendo

	protected int state = Character.st_init;
	protected Vector2 inputVector = new Vector2();
	protected Vector2 motionVector = new Vector2();
	protected int speedFall = -600;
	protected int speedWalk = 60;
	protected int speedWalkStairs = 60;
	protected int speedJump = 160;
	protected int stairInit = Constantes.It_none;
	protected TiledMapTileLayer layer;
	protected float speedY = 0;

	public Character(int type, float x, float y, int p0, int p1, float width, float height, TiledMapTileLayer layer)
	{
		super(type, x, y, p0, p1, width, height);
		this.layer = layer;
		// TODO Auto-generated constructor stub
	}

	public void move(Vector2 v, boolean b, float deltaTime)
	{
		this.motionVector.x = v.x * this.speedWalk;

		if (!this.isFloorDown())
		{
			this.state = Character.st_falling;
			this.motionVector.y += this.speedFall * deltaTime;
			if (this.motionVector.y < this.speedFall)
				this.motionVector.y = speedFall;
		}

		else
		{
			this.state = Character.st_walk;
			this.motionVector.y = 0;
			if (b)
			{
				this.motionVector.y = this.speedJump;
				this.state = Character.st_jump_top;
			}
		}

		Vector2 escalado = this.motionVector.cpy().scl(deltaTime);
		this.x += escalado.x;
		this.y += escalado.y;
		this.colision1();

	}

	private void colision1()
	{
		if (this.state != Character.st_walk)
		{
			if (this.colisionDownLeft() || this.colisionDownRight())
				this.correctDown();
			
			if (this.colisionDownLeft() && this.colisionUpLeft())
				this.correctLeft();
			
			if (this.colisionDownRight() && this.colisionUpRight())
				this.correctRight();

		}


			else
		 {
				if (this.motionVector.x > 0 && (this.colisionDownRight() || this.colisionUpRight()))
					this.correctRight();
				if (this.motionVector.x < 0 && (this.colisionDownLeft() || this.colisionUpLeft()))
					this.correctLeft();
			}
				/*
		 * if (this.motionVector.x > 0) { if (this.colisionDownRight()) { if
		 * (this.colisionUpRight()) this.correctRight(); else if
		 * (this.colisionDownLeft()) this.correctDown(); else {
		 * System.out.println("DE PUNTA 1"); }
		 * 
		 * }
		 * 
		 * } else if (this.motionVector.x < 0) { if (this.colisionDownLeft()) { if
		 * (this.colisionUpLeft()) this.correctLeft(); else if
		 * (this.colisionDownRight()) this.correctDown(); else {
		 * System.out.println("DE PUNTA 2"); } } } if (this.motionVector.y > 0) { if
		 * (this.colisionUpRight()) { if (this.colisionUpLeft()) this.correctUp(); else
		 * if (this.colisionDownRight()) this.correctRight(); else {
		 * System.out.println("DE PUNTA 3"); } } } else if (this.motionVector.y < 0) {
		 * if (this.colisionUpRight()) { if (this.colisionUpLeft()) this.correctUp();
		 * else if (this.colisionDownRight()) this.correctRight(); else {
		 * System.out.println("DE PUNTA 3"); } } }
		 */
	}

	private void colision()
	{
		if (this.motionVector.y > 0) // si mueve hacia arriba calculo colisiones arriba
		{
			if (isCellBlocked(this.x + this.getWidth() / 2, this.y + this.getHeight()) // Esquina superior derecha
					|| isCellBlocked(this.x - this.getWidth() / 2, this.y + this.getHeight())) // Esquina superior
																								// izq.
			{
				this.motionVector.y = 0;
				int aux = (int) ((this.y + this.getHeight() / 2) / this.layer.getTileHeight());
				this.y = aux * this.layer.getTileHeight() - this.getHeight() / 2;
			}
		}
		if (this.motionVector.x > 0) // si mueve a la derecha calculo colisiones por derecha
		{
			if (isCellBlocked(this.x + this.getWidth() / 2, this.y) // Esquina inferior derecha
					|| isCellBlocked(this.x + this.getWidth() / 2, this.y + this.getHeight())) // Esquina superior
																								// derecha
			{
				this.motionVector.x = 0;
				float aux = (int) ((this.x + 0.1 + this.getWidth() / 2) / this.layer.getTileWidth());
				System.out.println(" DERECHA actual: " + this.x + " " + this.y + "  Iria a: "
						+ (aux * this.layer.getTileWidth() - this.getWidth() / 2));
				this.x = aux * this.layer.getTileWidth() - this.getWidth() / 2;
			}
		} else if (this.motionVector.x < 0) // si mueve a la izquierda calculo colisiones por izquierda
			if (isCellBlocked(this.x - this.getWidth() / 2, this.y) // Esquina inferior izq.
					|| isCellBlocked(this.x - this.getWidth() / 2, this.y + this.getHeight())) // Esquina superior
																								// izq.
			{
				this.motionVector.x = 0;
				float aux = (int) (this.x / this.layer.getTileWidth());
				this.x = aux * this.layer.getTileWidth() + this.getWidth() / 2;
			}

		if (this.motionVector.y < 0) // si mueve hacia abajo calculo colisiones abajo
			if (isCellBlocked(this.x - this.getWidth() / 2, this.y) // Esquina inferior izq.
					|| isCellBlocked(this.x + this.getWidth() / 2, this.y)) // Esquina inferior derecha. // izq.
			{
				this.motionVector.y = 0;
				float aux = (int) (this.y / this.layer.getTileHeight());
				this.y = (aux + 1) * this.layer.getTileHeight();
			}

	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public boolean isFloorDown()
	{
		return (this.isCellBlocked(this.getX() + this.getWidth() / 2, (this.getY() - 1))
				|| this.isCellBlocked(this.getX() - this.getWidth() / 2, (this.getY() - 1)));
	}

	private boolean colisionUpRight()
	{
		return isCellBlocked(this.x + this.getWidth() / 2, this.y + this.getHeight());
	}

	private boolean colisionUpLeft()
	{
		return isCellBlocked(this.x - this.getWidth() / 2, this.y + this.getHeight());
	}

	private boolean colisionDownLeft()
	{
		return isCellBlocked(this.x - this.getWidth() / 2, this.y);
	}

	private boolean colisionDownRight()
	{
		return isCellBlocked(this.x + this.getWidth() / 2, this.y);
	}

	private void correctRight()
	{
		this.motionVector.x = 0;
		float aux = (int) ((this.x + this.getWidth() / 2) / this.layer.getTileWidth());
		this.x = (aux) * this.layer.getTileWidth() - this.getWidth() / 2 - 0.1f;
	}

	private void correctLeft()
	{
		this.motionVector.x = 0;
		float aux = (int) (this.x / this.layer.getTileWidth());
		this.x = aux * this.layer.getTileWidth() + this.getWidth() / 2;
	}

	private void correctUp()
	{
		this.motionVector.y = 0;
		int aux = (int) ((this.y + this.getHeight()) / this.layer.getTileHeight());
		this.y = aux * this.layer.getTileHeight() - this.getHeight();
	}

	private void correctDown()
	{
		this.motionVector.y = 0;
		float aux = (int) (this.y / this.layer.getTileHeight());
		this.y = (aux + 1) * this.layer.getTileHeight();
	}

	private boolean isCellBlocked(float x, float y)
	{
		// Convertimos la posición del mundo a la celda del mapa de tiles
		TiledMapTileLayer.Cell cell = this.layer.getCell((int) (x / this.layer.getTileWidth()),
				(int) (y / this.layer.getTileHeight()));
		return cell != null; // Si la celda no es nula, entonces es un bloque sólido
	}

}
