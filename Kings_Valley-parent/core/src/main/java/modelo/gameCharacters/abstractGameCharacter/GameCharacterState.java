package modelo.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import modelo.level.Stair;
import util.Config;
import util.Constantes;

public abstract class GameCharacterState
{
	protected GameCharacter gameCharacter;

	public GameCharacterState(GameCharacter gameCharacter, int state)
	{
		this.gameCharacter = gameCharacter;
		this.gameCharacter.setState(state);
	}

	protected abstract void moveFirstStep(Vector2 v, boolean b, float deltaTime);

	protected abstract void moveSecondStep(Vector2 escalado);

	protected void colision(Vector2 vectMove)
	{
		int r = -1;
		if (vectMove.x <= 0)
		{
			if (this.colisionMiddleLeft(vectMove) || this.colisionDownLeftForLanding(vectMove))
				r = Constantes.LEFT;
			else if (this.colisionMiddleRight(vectMove) || this.colisionDownRightForLanding(vectMove))
				r = Constantes.RIGHT;
		} else
		{
			if (this.colisionMiddleRight(vectMove) || this.colisionDownRightForLanding(vectMove))
				r = Constantes.RIGHT;
			else if (this.colisionMiddleLeft(vectMove) || this.colisionDownLeftForLanding(vectMove))
				r = Constantes.LEFT;

		}

		/*
		 * if (this.colisionUpRight(vectMove)) { if (this.colisionUpLeft(vectMove)) { r
		 * = Constantes.UP; } else if (this.colisionMiddleRight(vectMove)) { r =
		 * Constantes.RIGHT; } else { r =
		 * this.buscarColisionPorVertice(this.gameCharacter.x +
		 * this.gameCharacter.width, this.gameCharacter.y + this.gameCharacter.height,
		 * vectMove); if (r == Constantes.DOWN) { r = Constantes.RIGHT;
		 * System.out.println("ABERRACION"); } }
		 * 
		 * } else if (this.colisionUpLeft(vectMove)) { if
		 * (this.colisionMiddleLeft(vectMove)) { r = Constantes.LEFT; } else { r =
		 * this.buscarColisionPorVertice(this.gameCharacter.x, this.gameCharacter.y +
		 * this.gameCharacter.height, vectMove); if (r == Constantes.DOWN) { r =
		 * Constantes.LEFT;
		 * 
		 * System.out.println("ABERRACION");
		 * 
		 * } } } else if (this.isCellSolid(this.gameCharacter.x + vectMove.x,
		 * this.gameCharacter.y + vectMove.y) && this
		 * .buscarColisionPorVertice(this.gameCharacter.x, this.gameCharacter.y,
		 * vectMove) == Constantes.LEFT) r = Constantes.LEFT;
		 * 
		 * else if (this.isCellSolid(this.gameCharacter.x + vectMove.x +
		 * this.gameCharacter.width, this.gameCharacter.y + vectMove.y) &&
		 * this.buscarColisionPorVertice(this.gameCharacter.x +
		 * this.gameCharacter.width, this.gameCharacter.y, vectMove) ==
		 * Constantes.RIGHT) r = Constantes.RIGHT;
		 * 
		 * else if (this.colisionMiddleLeft(vectMove)) {
		 * 
		 * r = Constantes.LEFT;
		 * 
		 * } else if (this.colisionMiddleRight(vectMove)) {
		 * 
		 * r = Constantes.RIGHT;
		 * 
		 * }
		 * 
		 * this.corrigeDirecciones(r, vectMove);
		 */
		// return r != -1;
		this.corrigeDirecciones(r, vectMove);
	}

	protected void colisionForWalk(Vector2 vectMove)
	{
		int r = -1;
		if (this.colisionMiddleRight(vectMove) || this.colisionDownRightForLanding(vectMove)
				|| this.colisionUpRight(vectMove))
			r = Constantes.RIGHT;
		else if (this.colisionMiddleLeft(vectMove) || this.colisionDownLeftForLanding(vectMove)
				|| this.colisionUpLeft(vectMove))
			r = Constantes.LEFT;
		this.corrigeDirecciones(r, vectMove);
	}

	private boolean colisionMiddleRight(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x + this.gameCharacter.getWidth(),
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight()*.55f);
	}

	private boolean colisionMiddleLeft(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x,
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight() *.55f);
	}

	private boolean colisionUpRight(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x + this.gameCharacter.getWidth(),
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight());
	}

	private boolean colisionUpLeft(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x,
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight());
	}

	private boolean colisionDownLeftForLanding(Vector2 vectMove)
	{
		return colisionDown(this.gameCharacter.x, vectMove);
	}

	private boolean colisionDownRightForLanding(Vector2 vectMove)
	{
		return colisionDown(this.gameCharacter.x + this.gameCharacter.width, vectMove);
	}

	private boolean colisionDown(float x, Vector2 vectMove)
	{
		boolean respuesta = false;
		if (isCellBlocked(x, this.gameCharacter.y + vectMove.y))
		{
			float tileY = (int) ((this.gameCharacter.y + vectMove.y) / Config.getInstance().getLevelTileHeightUnits());
			tileY = (tileY + 1) * Config.getInstance().getLevelTileHeightUnits();
			respuesta = (this.gameCharacter.y > tileY && this.gameCharacter.y + vectMove.y <= tileY);
		}
		return respuesta;
	}

	private void correctRight(Vector2 vectMove)
	{
		float epsilon = 0.000001f * Config.getInstance().getLevelTileWidthUnits();
		float aux = (int) ((this.gameCharacter.x + this.gameCharacter.getWidth() + vectMove.x)
				/ Config.getInstance().getLevelTileWidthUnits());
		vectMove.x = (aux) * Config.getInstance().getLevelTileWidthUnits()
				- (this.gameCharacter.getWidth() + epsilon + this.gameCharacter.x);
		this.gameCharacter.motionVector.x = 0;

	}

	private void correctLeft(Vector2 vectMove)
	{
		float epsilon = 0.002f * Config.getInstance().getLevelTileWidthUnits();
		float aux = (int) ((this.gameCharacter.x + vectMove.x) / Config.getInstance().getLevelTileWidthUnits());
		vectMove.x = (aux + 1) * Config.getInstance().getLevelTileWidthUnits() + epsilon - this.gameCharacter.x;
		this.gameCharacter.motionVector.x = 0;

	}

	private void correctDown(Vector2 vectMove)
	{
		float aux = (int) ((this.gameCharacter.y + vectMove.y) / Config.getInstance().getLevelTileHeightUnits());
		vectMove.y = (aux + 1) * Config.getInstance().getLevelTileHeightUnits() - this.gameCharacter.y;
		if (vectMove.x == 0)
			this.gameCharacter.gameCharacterState = new GameCharacterStateIddle(this.gameCharacter);
		else
		{
			this.gameCharacter.lookRight = vectMove.x >= 0;
			this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);
		}
		this.gameCharacter.motionVector.y = 0;

	}

	private int buscarColisionPorVertice(float x, float y, Vector2 vectMove)
	{

		x += vectMove.x;
		y += vectMove.y;
		int r ;
		if (this.gameCharacter.motionVector.x == 0)
		{
			r = Constantes.DOWN;
		}

		else
		{

			float m = this.gameCharacter.motionVector.y / this.gameCharacter.motionVector.x;
			float b = y - x * m;
			float valorX;
			int tileX = (int) (x / Config.getInstance().getLevelTileWidthUnits());
			int tileY = (int) (y / Config.getInstance().getLevelTileHeightUnits());

			int respuestaLateral = 0;
			if (this.gameCharacter.motionVector.x > 0)
			{
				valorX = tileX * Config.getInstance().getLevelTileWidthUnits();
				respuestaLateral = Constantes.RIGHT;
			} else
			{
				valorX = (tileX + 1) * Config.getInstance().getLevelTileWidthUnits();
				respuestaLateral = Constantes.LEFT;
			}

			if (this.gameCharacter.motionVector.y >= 0)
				r = respuestaLateral;
			else
			{
				float yBuscado = m * valorX + b;
				float abajo = tileY * Config.getInstance().getLevelTileHeightUnits();
				float arriba = (tileY + 1) * Config.getInstance().getLevelTileHeightUnits();
				if (abajo <= yBuscado && yBuscado <= arriba)
					r = respuestaLateral;
				else
					r = Constantes.DOWN;
			}
		}
		return r;

	}

	private void corrigeDirecciones(int direction, Vector2 vectMove)
	{
		switch (direction)
		{

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

	protected boolean isCellBlocked(float x, float y)
	{
		return this.gameCharacter.pyramid.getCell(x, y) != null;
	}

	protected void checkLanding(Vector2 vectMove)
	{
		int r = -1;

		if (this.colisionDownLeftForLanding(vectMove))
		{
			if (this.colisionDownRightForLanding(vectMove)) // ambas esquinas bajas colisionan
				this.correctDown(vectMove);
			else // solo colisiona esquina baja izq
			{
				if (vectMove.x >= 0)
					r = Constantes.DOWN;
				else
					r = this.buscarColisionPorVertice(this.gameCharacter.x, this.gameCharacter.y, vectMove);
			}
		} else if (this.colisionDownRightForLanding(vectMove))
		{
			if (vectMove.x <= 0)
				r = Constantes.DOWN;
			else
				r = this.buscarColisionPorVertice(this.gameCharacter.x + this.gameCharacter.width, this.gameCharacter.y,
						vectMove);
		}
		this.corrigeDirecciones(r, vectMove);
		if (r != -1)
			this.checkLanding(vectMove);
	}

	protected boolean isFloorDown()
	{
		float epsilon = 0.000001f * Config.getInstance().getLevelTileHeightUnits();
		return ((isCellBlocked(this.gameCharacter.x + this.gameCharacter.getWidth(),
				this.gameCharacter.y - epsilon * this.gameCharacter.height)
				&& isCellBlocked(this.gameCharacter.x + this.gameCharacter.getWidth(),
						this.gameCharacter.y - this.gameCharacter.height * 0.25f))
				|| (isCellBlocked(this.gameCharacter.x, this.gameCharacter.y - 0.00001f * this.gameCharacter.height)
						&& isCellBlocked(this.gameCharacter.x,
								this.gameCharacter.y - this.gameCharacter.height * 0.25f)));
	}

	protected Stair getStair()
	{
		return null;
	}

}