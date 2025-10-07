package engine.gameCharacters.abstractGameCharacter;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.level.Stair;
import util.GameRules;
import util.Constants;

/**
 * @author Guillermo Lazzurri
 * 
 * Clase que aplica el patron state. Representa el estado el caracter.
 */
public abstract class GameCharacterState
{
	/**
	 * Corresponde al sujeto del patron state
	 */
	protected GameCharacter gameCharacter;

	/**
	 * Constructor de clase
	 * @param gameCharacter Corresponde al sujeto del patron state
	 * @param state valor entero que representa el estado
	 */
	public GameCharacterState(GameCharacter gameCharacter, int state)
	{
		this.gameCharacter = gameCharacter;
		this.gameCharacter.setState(state);
	}

	/**
	 * Metodo llamado durante los calculos de movimiento antes de realizar el escalado por valor delta.
	 * @param v Objecto de tipo Vector2 que representa la trayectoria pretendida.
	 * @param b true si se pretende realizar una accion, false en caso contrario
	 * @param deltaTime tiempo en segundos desde la ultima actualizacion
	 */
	protected abstract void moveFirstStep(Vector2 v, boolean b, float deltaTime);

	/**
	 * Metodo llamado durante los calculos de movimiento despues de realizar el escalado por valor delta.
	 * @param escalado Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	protected abstract void moveSecondStep(Vector2 escalado);

	
	/**
	 * Metodo llamado para calcular las colisiones al intentar moverse de acuerdo a la trayectoria pasada por parametro. Se realizan las correciones necesarias.
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	protected void colision(Vector2 vectMove)
	{
		int r = -1;
		if (vectMove.x <= 0)
		{
			if (this.colisionMiddleLeft(vectMove) || this.colisionDownLeftForLanding(vectMove))
				r = Constants.LEFT;
			else if (this.colisionMiddleRight(vectMove) || this.colisionDownRightForLanding(vectMove))
				r = Constants.RIGHT;
		} else
		{
			if (this.colisionMiddleRight(vectMove) || this.colisionDownRightForLanding(vectMove))
				r = Constants.RIGHT;
			else if (this.colisionMiddleLeft(vectMove) || this.colisionDownLeftForLanding(vectMove))
				r = Constants.LEFT;

		}

		
		this.corrigeDirecciones(r, vectMove);
		
		
	}

	/**
	 * Calcula las colisiones al intentar moverse en la trayectoria pretendida durante una caminata
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	protected void colisionForWalk(Vector2 vectMove)
	{
		int r = -1;
		if (this.colisionMiddleRight(vectMove) || this.colisionDownRightForLanding(vectMove)
				|| this.colisionUpRight(vectMove))
			r = Constants.RIGHT;
		else if (this.colisionMiddleLeft(vectMove) || this.colisionDownLeftForLanding(vectMove)
				|| this.colisionUpLeft(vectMove))
			r = Constants.LEFT;
		this.corrigeDirecciones(r, vectMove);
	}

	/**
	 * Indica si existira colision en el medio del caracter a la derecha. Usado por los metodos colision y colisionForWalk
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return true si existira colision, false en caso contrario.
	 */
	private boolean colisionMiddleRight(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x + this.gameCharacter.getWidth(),
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight() * .55f);
	}


	
	/**
	 * Indica si existira colision en el medio del caracter a la izquierda. Usado por los metodos colision y colisionForWalk
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return true si existira colision, false en caso contrario.
	 */

	private boolean colisionMiddleLeft(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x,
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight() * .55f);
	}

	/**
	 * Indica si existira colision en el en la esquina superior derecha del caracter a la derecha. Usado por el metodo colisionForWalk
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return true si existira colision, false en caso contrario.
	 */

	private boolean colisionUpRight(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x + this.gameCharacter.getWidth(),
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight());
	}

	/**
	 * Indica si existira colision en el en la esquina superior izquierda del caracter a la derecha. Usado por el metodo colisionForWalk
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return true si existira colision, false en caso contrario.
	 */

	private boolean colisionUpLeft(Vector2 vectMove)
	{
		return isCellBlocked(this.gameCharacter.x + vectMove.x,
				this.gameCharacter.y + vectMove.y + this.gameCharacter.getHeight());
	}
	/**
	 * Indica si existira colision en el en la esquina inferior izquierda del caracter a la derecha. Usado por los metodos colision, colisionForWalk, y checkLanding
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return true si existira colision, false en caso contrario.
	 */
	private boolean colisionDownLeftForLanding(Vector2 vectMove)
	{
		return colisionDown(this.gameCharacter.x, vectMove);
	}

	/**
	 * Indica si existira colision en el en la esquina inferior derecha del caracter a la derecha. Usado por los metodos colision, colisionForWalk, y checkLanding
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return true si existira colision, false en caso contrario.
	 */

	private boolean colisionDownRightForLanding(Vector2 vectMove)
	{
		return colisionDown(this.gameCharacter.x + this.gameCharacter.width, vectMove);
	}

	
	/**
	 * Indica si existira colision en el en la esquina inferior derecha del caracter a la derecha. Usado por los metodos colisionDownRightForLanding, y colisionDownLeftForLanding
	 * @param x indica un desplazamiento para calcular una u otra esquina
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return
	 */
	private boolean colisionDown(float x, Vector2 vectMove)
	{
		boolean respuesta = false;
		if (isCellBlocked(x, this.gameCharacter.y + vectMove.y))
		{
			float tileY = (int) ((this.gameCharacter.y + vectMove.y) / GameRules.getInstance().getLevelTileHeightUnits());
			tileY = (tileY + 1) * GameRules.getInstance().getLevelTileHeightUnits();
			respuesta = (this.gameCharacter.y > tileY && this.gameCharacter.y + vectMove.y <= tileY);
		}
		return respuesta;
	}

	/**
	 * Corrige la trayectoria pretendida en caso de colision por derecha
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	private void correctRight(Vector2 vectMove)
	{
		float epsilon = 0.002f * GameRules.getInstance().getLevelTileWidthUnits();
		float aux = (int) ((this.gameCharacter.x + this.gameCharacter.getWidth() + vectMove.x)
				/ GameRules.getInstance().getLevelTileWidthUnits());
		vectMove.x = (aux) * GameRules.getInstance().getLevelTileWidthUnits()
				- (this.gameCharacter.getWidth() + epsilon + this.gameCharacter.x);
		this.gameCharacter.motionVector.x = 0;

	}
	
	/**
	 * Corrige la trayectoria pretendida en caso de colision por izquierda
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	private void correctLeft(Vector2 vectMove)
	{
		float epsilon = 0.002f * GameRules.getInstance().getLevelTileWidthUnits();
		float aux = (int) ((this.gameCharacter.x + vectMove.x) / GameRules.getInstance().getLevelTileWidthUnits());
		vectMove.x = (aux + 1) * GameRules.getInstance().getLevelTileWidthUnits() + epsilon - this.gameCharacter.x;
		this.gameCharacter.motionVector.x = 0;

	}

	/**
	 * Corrige la trayectoria pretendida en caso de colision por abajo
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	private void correctDown(Vector2 vectMove)
	{
		float aux = (int) ((this.gameCharacter.y + vectMove.y) / GameRules.getInstance().getLevelTileHeightUnits());
		vectMove.y = (aux + 1) * GameRules.getInstance().getLevelTileHeightUnits() - this.gameCharacter.y;
		if (vectMove.x == 0)
			this.gameCharacter.gameCharacterState = new GameCharacterStateIddle(this.gameCharacter);
		else
		{
			this.gameCharacter.lookRight = vectMove.x >= 0;
			this.gameCharacter.gameCharacterState = new GameCharacterStateWalking(this.gameCharacter);
		}
		this.gameCharacter.motionVector.y = 0;
		Game.getInstance().eventFired(KVEventListener.CHARACTER_END_JUMP, this.gameCharacter);



	}

	/**
	 * Metodo que decide si la colision fue lateral o por debajo, en caso de que la colision solo se detecta en un vertice inferior del caracter. Es invocado por el metodo checkLanding. Utiliza un calculo de funcion lineal. 
	 * @param x desplazamiento en x necesario para decidir el vertice a evaluar
	 * @param y desplazamiento en y necesario para decidir el vertice a evaluar 
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @return un valor indicando el tipo de tipo de colision. Puede ser: Constantes.DOWN; Constantes.RIGHT; Constantes.LEFT  
	 */
	private int buscarColisionPorVertice(float x, float y, Vector2 vectMove)
	{

		x += vectMove.x;
		y += vectMove.y;
		int r;
		if (this.gameCharacter.motionVector.x == 0)
		{
			r = Constants.DOWN;
		}

		else
		{

			float m = this.gameCharacter.motionVector.y / this.gameCharacter.motionVector.x;
			float b = y - x * m;
			float valorX;
			int tileX = (int) (x / GameRules.getInstance().getLevelTileWidthUnits());
			int tileY = (int) (y / GameRules.getInstance().getLevelTileHeightUnits());

			int respuestaLateral = 0;
			if (this.gameCharacter.motionVector.x > 0)
			{
				valorX = tileX * GameRules.getInstance().getLevelTileWidthUnits();
				respuestaLateral = Constants.RIGHT;
			} else
			{
				valorX = (tileX + 1) * GameRules.getInstance().getLevelTileWidthUnits();
				respuestaLateral = Constants.LEFT;
			}

			if (this.gameCharacter.motionVector.y >= 0)
				r = respuestaLateral;
			else
			{
				float yBuscado = m * valorX + b;
				float abajo = tileY * GameRules.getInstance().getLevelTileHeightUnits();
				float arriba = (tileY + 1) * GameRules.getInstance().getLevelTileHeightUnits();
				if (abajo <= yBuscado && yBuscado <= arriba)
					r = respuestaLateral;
				else
					r = Constants.DOWN;
			}
		}
		return r;

	}

	/**
	 * Corrige la trayectoria pretendida en caso de colision. Es llamado por los metodos colision, colisionForWalk, y checkLanding 
	 * @param direction indica la direccion desde la que se produjo la colision. Puede tomar los valores: Constantes.DOWN; Constantes.RIGHT; Constantes.LEFT 
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
	private void corrigeDirecciones(int direction, Vector2 vectMove)
	{
		switch (direction)
		{

		case Constants.LEFT:
			this.correctLeft(vectMove);
			break;
		case Constants.DOWN:
			this.correctDown(vectMove);

			break;
		case Constants.RIGHT:
			this.correctRight(vectMove);
			break;

		}

	}

	/**
	 * Indica si una celda de la piramide esta bloqueada, es decir, sea diferente de null
	 * @param x valor float de la coordenada x a evaluar
	 * @param y valor float de la coordenada y a evaluar
	 * @return retorna true si la celda esta bloqueada, false en caso contrario
	 */
	protected boolean isCellBlocked(float x, float y)
	{
		return this.gameCharacter.pyramid.getCell(x, y) != null;
	}

	/**
	 * Verifica si el caracter ha aterrizado. 
	 * @param vectMove Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 */
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
					r = Constants.DOWN;
				else
					r = this.buscarColisionPorVertice(this.gameCharacter.x, this.gameCharacter.y, vectMove);
			}
		} else if (this.colisionDownRightForLanding(vectMove))
		{
			if (vectMove.x <= 0)
				r = Constants.DOWN;
			else
				r = this.buscarColisionPorVertice(this.gameCharacter.x + this.gameCharacter.width, this.gameCharacter.y,
						vectMove);
		}
		this.corrigeDirecciones(r, vectMove);
		if (r != -1)
			this.checkLanding(vectMove);
	}

	/**
	 * Indica si hay piso bajo los pies del caracter
	 * @return true si hay piso bajo el caracter, false en caso contrario.
	 */
	protected boolean isFloorDown()
	{
		float epsilon = 0.000001f * GameRules.getInstance().getLevelTileHeightUnits();
		return ((isCellBlocked(this.gameCharacter.x + this.gameCharacter.getWidth(),
				this.gameCharacter.y - epsilon * this.gameCharacter.height)
				&& isCellBlocked(this.gameCharacter.x + this.gameCharacter.getWidth(),
						this.gameCharacter.y - this.gameCharacter.height * 0.25f))
				|| (isCellBlocked(this.gameCharacter.x, this.gameCharacter.y - 0.00001f * this.gameCharacter.height)
						&& isCellBlocked(this.gameCharacter.x,
								this.gameCharacter.y - this.gameCharacter.height * 0.25f)));
	}

	/**
	 * Retorna la escalera sobre la que esta parado el caracter. Podria ser null
	 * @return objeto de tipo Stair sobre la que esta el caracter. Si no esta en ninguna escalera retorna null
	 */
	protected Stair getStair()
	{
		return null;
	}

	/**
	 * Metodo invocado para mover el caracter.
	 * @param v Objeto de tipo Vector2 que representa la trayectoria pretendida.
	 * @param b Indica si se pretenda realizar una accion (puede ser saltar, arrojar espada o picar)
	 * @param deltaTime indica el deltaTime desde la ultima actualizacion
	 */
	public void move(Vector2 v, boolean b, float deltaTime)
	{
		this.moveFirstStep(v, b, deltaTime);
		Vector2 escalado = this.gameCharacter.motionVector.cpy().scl(deltaTime);
		this.moveSecondStep(escalado);
		this.gameCharacter.x += escalado.x;
		this.gameCharacter.y += escalado.y;
		this.checkOutLevel();
	}

	/**
	 * Corrige posibles salidas del caracter fuera de la piramide
	 */
	private void checkOutLevel()
	{
		float epsilon = .1f * GameRules.getInstance().getLevelTileWidthUnits();

		if (this.gameCharacter.x < GameRules.getInstance().getLevelTileWidthUnits())
		{
			this.gameCharacter.x = GameRules.getInstance().getLevelTileWidthUnits();
		} else

		{
			if (this.gameCharacter.x + epsilon + this.gameCharacter.width > (this.gameCharacter.pyramid.getMapWidthInTiles() - 1)
					* GameRules.getInstance().getLevelTileWidthUnits())

				this.gameCharacter.x = (this.gameCharacter.pyramid.getMapWidthInTiles() - 1) * GameRules.getInstance().getLevelTileWidthUnits()
						- (this.gameCharacter.width + epsilon);
		}

	}

	/**
	 * Metodo llamado al entrar en una escalera
	 * @param stair Objeto de tipo Stair que indica la escalera a la qu se esta entrando
	 */
	protected abstract void enterStair(Stair stair);

	/**
	 * Metodo llamado al intentar un salto
	 * @return true si el salto pudo realizarse, fale en caso contrario
	 */
	protected abstract boolean doJump();

}