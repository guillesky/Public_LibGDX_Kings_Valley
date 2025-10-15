package engine.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

import engine.level.LevelObject;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;

/**
 * Clase abstracta que representa el estado de la momia (patron state)
 * 
 * @author Guillermo Lazzurri
 */
public abstract class MummyState

{

	/**
	 * Codigo que indica que no hay decision de direccion
	 */
	protected static final int NONE = 0;
	/**
	 * Codigo que indica direccion derecha
	 */

	protected static final int RIGHT = 1;
	/**
	 * Codigo que indica direccion izquierda
	 */
	protected static final int LEFT = -1;

	/**
	 * Codigo que indica Player a la misma altura que la momia
	 */

	protected static final int PLAYER_IS_SOME_LEVEL = 0;
	/**
	 * Codigo que indica Player a esta arriba de la momia
	 */

	protected static final int PLAYER_IS_UP = 1;

	/**
	 * Codigo que indica Player a esta abajo de la momia
	 */
	protected static final int PLAYER_IS_DOWN = -1;
	private static final int BLOCK_FREE = 0;
	protected static final int BLOCK_BRICK = 1;
	private static final int BLOCK_GIRATORY = 2;
	/**
	 * Inica cuanto tiempo debe permanecer en este estado antes de cambiar
	 */
	protected float timeToChange;
	/**
	 * Corresponde al sujeto del patron state
	 */
	protected Mummy mummy;
	private Rectangle mummyRectangleWithOffset;

	/**
	 * Contructor de clase
	 * 
	 * @param mummy Momia a la que pertences el estado (patron state)
	 * @param state tipo de estado
	 */
	public MummyState(Mummy mummy, int state)
	{
		this.mummy = mummy;
		this.mummyRectangleWithOffset = new Rectangle(this.mummy.x, this.mummy.y, this.mummy.width, this.mummy.height);
		mummy.setState(state);
		mummy.resetAnimationDelta();
		mummy.resetTimeInState();

	}

	/**
	 * Metodo llamado al actualizar la momia
	 * 
	 * @param deltaTime tiempo transcurrido desde la ultima actualizacion
	 */
	public abstract void update(float deltaTime);

	/**
	 * Retorna true si la momia es peligrosa, false en caso contrario
	 * @return true si la momia es peligrosa, false en caso contrario
	 */
	protected abstract boolean isDanger();

	/**
	 * Evento que podria pasar a modo muerto.
	 * 
	 * @param mustTeleport true si la momia debe teletransportarse al reaparecer,
	 *                     false en caso contrarioF
	 */
	protected abstract void die(boolean mustTeleport);

	/**
	 * Metodo que retorna la escalera mas cercana a la momia hacia las direcciones
	 * indicadas. Si no hay una escalera en la direccion pretendida retorna null
	 * 
	 * @param toUp    true si se pretende subir, false si se pretende bajar
	 * @param toRight true si busco hacia la derecha, false si busco a la izquierda
	 * @return La escalera mas cercana a la direccion pretendida. Si no hay escalera
	 *         en la plataforma en la direccion requerido retorna null
	 */
	private Stair nearStair(boolean toUp, boolean toRight)
	{
		Stair r = null;
		Pyramid pyramid = mummy.getPyramid();
		int count = this.endPlatform(toRight).getCount();
		ArrayList<Stair> candidatesStairs = new ArrayList<Stair>();
		LevelObject footStair;

		Iterator<Stair> it = pyramid.getAllStairs().iterator();
		float tileWidth = GameRules.getInstance().getLevelTileWidthUnits();
		float posX = mummy.x + mummy.width * 0.5f;
		while (it.hasNext())
		{
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();
			if (footStair.y == mummy.y && (toRight && footStair.x >= posX || !toRight && footStair.x <= posX))
				candidatesStairs.add(stair);
		}

		if (!candidatesStairs.isEmpty())
		{
			it = candidatesStairs.iterator();
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();

			Stair minStair = stair;

			float aux = posX - (footStair.x + footStair.width * 0.5f);
			if (aux < 0)
				aux *= -1;
			int min = (int) (aux / (float) tileWidth);
			while (it.hasNext())
			{
				stair = it.next();
				if (toUp)
					footStair = stair.getDownStair();
				else
					footStair = stair.getUpStair();

				aux = posX - (footStair.x + footStair.width * 0.5f);
				if (aux < 0)
					aux *= -1;
				int dist = (int) (aux / (float) tileWidth);
				if (dist < min)
				{
					min = dist;

					minStair = stair;
				}
			}
			if (min <= count)
				r = minStair;
		}

		return r;
	}

	/**
	 * Retorna el resultado de buscar una escalera cercana que suba o baje (podria
	 * retornar true)
	 * 
	 * @param toUp true si se busca una escalera que suba, false si busco una
	 *             escalera que baje
	 * @return El resultado de la escalera buscada, si no hay una escalera con las
	 *         caracteristicas pedidas, retorna null
	 */
	protected NearStairResult getNearStair(boolean toUp)
	{
		Stair stair = null;
		Stair toRight = this.nearStair(toUp, true);
		Stair toLeft = this.nearStair(toUp, false);

		if (toRight == null)
			stair = toLeft;
		else
		{
			if (toLeft == null)
				stair = toRight;
			else
			{

				LevelObject footStairRight;
				LevelObject footStairLeft;

				if (toUp)
				{
					footStairRight = toRight.getDownStair();
					footStairLeft = toLeft.getDownStair();
				} else
				{
					footStairRight = toRight.getUpStair();
					footStairLeft = toLeft.getUpStair();
				}

				if (footStairRight.x - mummy.x < mummy.x - footStairLeft.x)
					stair = toRight;
				else
					stair = toLeft;

			}
		}
		NearStairResult r = null;
		if (stair != null)
		{
			if (stair == toRight)
				r = new NearStairResult(stair, RIGHT);
			else
				r = new NearStairResult(stair, LEFT);
		}
		return r;
	}

	/**
	 * Crea y retorna un objeto de tipo EndPlatform indicando el tipo de final de
	 * plataforma buscado
	 * 
	 * @param toRight true si se busca el final de plataforma or derecha, false si
	 *                se lo busca por izquierda
	 * @return Objeto de tipo EndPlatform que indica el tipo de final de plataforma
	 */
	protected EndPlatformOLD endPlatform(boolean toRight)
	{
		int inc;
		int acum = 0;
		int type;
		int count;
		float x;
		Pyramid pyramid = mummy.getPyramid();
		x = mummy.x;
		if (toRight)
		{
			inc = 1;
			x += mummy.width;
		} else

		{
			inc = -1;

		}

		while (pyramid.getCell(x, mummy.y, acum, 0) == null && pyramid.getCell(x, mummy.y, acum, 1) == null
				&& pyramid.getCell(x, mummy.y, acum, -1) != null && this.isColDesplaInMap(acum))
		{
			acum += inc;

		}
		type = this.typeEndPlatform(x, acum);
		if (acum < 0)
			acum *= -1;
		count = acum;
		EndPlatformOLD r = new EndPlatformOLD(type, count);
		this.correctGiratoryEndPlatform(r, toRight);

		return r;

	}

	/**
	 * Corrige los atributos del objeto endPlatform pasado como parametro en caso de
	 * encontrar una puerta giratoria. En ese caso se considera que hay un bloqueo
	 * insalvable para la momia. Este metodo es invocado por this.endPlatform
	 * 
	 * @param endPlatform objeto de tipo endPlatform que debe ser evaluado. Su
	 *                    estado podria cambiar
	 * @param toRight     true si el endPlatform esta a la derecha, false si esta a
	 *                    la izquierda.
	 */
	private void correctGiratoryEndPlatform(EndPlatformOLD endPlatform, boolean toRight)
	{
		Iterator<LevelObject> it = mummy.getPyramid().getGiratories().iterator();
		boolean condicion = false;
		float posX = mummy.x;

		if (toRight)
			posX += mummy.width;

		while (it.hasNext() && !condicion)
		{
			LevelObject giratoria = it.next();
			if (giratoria.y == mummy.y && (toRight && giratoria.x >= posX || !toRight && giratoria.x <= posX))
			{
				float aux = posX - (giratoria.x + giratoria.width * 0.5f);
				if (aux < 0)
					aux *= -1;
				int dist = (int) (aux / (float) GameRules.getInstance().getLevelTileWidthUnits());

				if (dist < endPlatform.getCount())
				{
					endPlatform.setCount(dist);
					endPlatform.setType(EndPlatformOLD.END_BLOCK);
					condicion = true;
				}
			}
		}

	}

	/**
	 * Metodo llamado internadmente por this.endPlatform. Evita que durante los
	 * calculos se busque fuera de la piramide
	 * 
	 * @param col cantidad de desplazamiento
	 * @return true si se esta dentro del mapa, false en caso contrario.
	 */
	private boolean isColDesplaInMap(int col)
	{

		return mummy.getColPosition() + col > 1
				&& mummy.getColPosition() + col < mummy.getPyramid().getMapWidthInTiles() - 1;
	}

	/**
	 * Indica el tipo de fin de plataforma que existe de acuerdo a la posicionX y al
	 * desplazamiento en x pasado por parametro
	 * 
	 * @param positionX coordenada X pretendida
	 * @param iDeltaX   Desplazamiento en x
	 * @return valor entero que representa el tipo de final de plataforma. Puede
	 *         tomar los valores: EndPlatform.END_CLIFF; EndPlatform.END_STEP;
	 *         EndPlatform.END_BLOCK
	 */
	protected int typeEndPlatform(float positionX, int iDeltaX)
	{
		int type = -1;
		Pyramid pyramid = mummy.getPyramid();
		if (pyramid.getCell(positionX, mummy.y, iDeltaX, 0) == null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, 1) == null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, -1) == null)
			type = EndPlatformOLD.END_CLIFF;
		else if ((pyramid.getCell(positionX, mummy.y, iDeltaX, 1) != null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, 2) == null
				&& pyramid.getCell(positionX, mummy.y, iDeltaX, 3) == null)
				|| (pyramid.getCell(positionX, mummy.y, iDeltaX, 0) != null
						&& pyramid.getCell(positionX, mummy.y, iDeltaX, 1) == null
						&& pyramid.getCell(positionX, mummy.y, iDeltaX, 2) == null))
			type = EndPlatformOLD.END_STEP;
		else
			type = EndPlatformOLD.END_BLOCK;
		return type;
	}

	/**
	 * Chequea si llega al final de la plataforma y actua en consecuencia. Puede
	 * llamar a this.doInCrashToWallOrGiratory, o a this.doInBorderCliff()
	 * 
	 * @param type indica el tipo de final de plataforma al que se esta acercando la
	 *             momia.
	 */
	public void checkEndOfPlataform(int type)
	{
		int crashStatus = this.crashWallOrGiratory();
		if (crashStatus != BLOCK_FREE) // si choca contra un ladrillo o una giratoria
		{
			this.doInCrashToWallOrGiratory(crashStatus, type);
		} else
		{

			if (type == EndPlatformOLD.END_CLIFF) // Si esta al borde del acantilado
			{
				this.doInBorderCliff();
			}

		}
	}

	/**
	 * Indica que debe hacer la momia en caso de llegar al borde de una cornisa
	 */
	protected abstract void doInBorderCliff();

	/**
	 * Indica que debe hacer la momia si choca contra una pared o una giratoria
	 * 
	 * @param crashStatus valor entero que determina si el choque es contra un muro
	 *                    o una giratoria, los valores respectivamente son
	 *                    BLOCK_BRICK y BLOCK_GIRATORY
	 * @param type        indica el tipo de final de plataforma al que se esta
	 *                    acercando la momia.
	 */
	public abstract void doInCrashToWallOrGiratory(int crashStatus, int type);

	/**
	 * Llamado internamente por checkEndOfPlatform. Indica si la momia choca contra
	 * un muro o contra una giratoria
	 * 
	 * @return indica si la momia no esta bloqueada, si choca contra un muro o
	 *         contra una giratoria. Puede tomar respectivamente los valores
	 *         BLOCK_FREE; BLOCK_BRICK; BLOCK_GIRATORY
	 */
	private int crashWallOrGiratory()
	{
		boolean condicion = false;
		Pyramid pyramid = this.mummy.getPyramid();
		int respuesta = BLOCK_FREE;
		float epsilon = GameRules.getInstance().getLevelTileWidthUnits() * 0.1f;
		if (mummy.isLookRight())
		{
			condicion = mummy.getColPosition() >= pyramid.getMapWidthInTiles() - 2
					|| pyramid.getCell(mummy.x + GameRules.getInstance().getLevelTileWidthUnits(),
							mummy.y + GameRules.getInstance().getLevelTileHeightUnits()) != null
					||

					pyramid.getCell(mummy.x + GameRules.getInstance().getLevelTileWidthUnits(), mummy.y) != null;

		} else
		{

			condicion = mummy.getColPosition() <= 1 || pyramid.getCell(mummy.x - epsilon,
					mummy.y + GameRules.getInstance().getLevelTileHeightUnits()) != null ||

					pyramid.getCell(mummy.x - epsilon, mummy.y) != null;

		}
		if (condicion)
			respuesta = BLOCK_BRICK;
		else if (this.checkGiratory())
			respuesta = BLOCK_GIRATORY;

		return respuesta;
	}

	/**
	 * Llamado internamente por this.crashWallOrGiratory. Indica si la momia choco
	 * contra una giratoria
	 * 
	 * @return true si choco contra una giratoria, false en caso contrario.
	 */
	private boolean checkGiratory()
	{
		float epsilon = GameRules.getInstance().getLevelTileWidthUnits() * 0.1f;
		if (this.mummy.isLookRight())
			this.mummyRectangleWithOffset.x = this.mummy.x + epsilon;
		else
			this.mummyRectangleWithOffset.x = this.mummy.x - epsilon;
		this.mummyRectangleWithOffset.y = this.mummy.y;

		return this.checkRectangleColision(this.mummy.getPyramid().getGiratories());
	}

	/**
	 * Llamado internamente por checkGiratory. Verifica si la momia choca contra
	 * alguna giratoria
	 * 
	 * @param levelObjects Coleccion de objetos de tipo levelObjects. Representara
	 *                     la totalidad de giratorias en la piramide
	 * @return true si hay colision, false en caso contrario.
	 */
	private boolean checkRectangleColision(Collection<LevelObject> levelObjects)
	{

		Iterator<LevelObject> it = levelObjects.iterator();
		LevelObject item = null;
		if (it.hasNext())
			do
			{
				item = it.next();
			} while (it.hasNext() && !LevelObject.rectangleColision(mummyRectangleWithOffset, item));

		return (LevelObject.rectangleColision(mummyRectangleWithOffset, item));

	}

}
