package engine.gameCharacters.mummys;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.LevelObject;
import engine.level.Pyramid;
import util.GameRules;
import util.ProbabilisticSelector;

/**
 * Clase que representa del estado de la momia "Decidiendo"
 * 
 * @author Guillermo Lazzurri
 */
public class MummyStateDeciding extends MummyState
{
	private static final int RIGHT = 1;
	/**
	 * Codigo que indica direccion izquierda
	 */
	private static final int LEFT = -1;

	/**
	 * Codigo que indica Player a la misma altura que la momia
	 */
	private static final int GO_UP_STAIR = 3;
	private static final int GO_DOWN_STAIR = 4;

	/**
	 * Constructor de clase, llama a super(mummy, Mummy.ST_IDDLE);
	 * 
	 * @param mummy Correspondiente al sujeto del patron state
	 */
	public MummyStateDeciding(Mummy mummy)
	{
		super(mummy, Mummy.ST_IDDLE);
		this.timeToChange = this.mummy.getTimeDeciding();

		this.update(0);

	}

	/**
	 * Si pasa el tiempo corresponediente a this.timeToChange entonces llama a
	 * this.changeStatus();
	 */
	@Override
	public void update(float deltaTime)
	{
		if (this.mummy.getTimeInState() >= this.timeToChange && (this.mummy.getState() == GameCharacter.ST_WALKING
				|| this.mummy.getState() == GameCharacter.ST_IDDLE))
		{
			this.changeStatus();
		}
		this.mummy.move(new Vector2(), false, deltaTime);

	}

	/**
	 * De acuerdo a la posicion del player puede pasar a los estados
	 * MummyStateSearchingStair o MummyStateWalking
	 */
	private void changeStatus()
	{
		PlatformAnalysisResult result = new PlatformAnalysisResult(mummy);
		ProbabilisticSelector selector = new ProbabilisticSelector(Game.random);
		if (this.mummy.player.y > this.mummy.y)// player esta arriba
		{

			if (result.getNearestUpStair() != null)
			{
				if (this.mummy.player.y >= result.getNearestUpStair().getUpStair().y)
					this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result.getNearestUpStair(),
							MummyState.PLAYER_IS_UP);
				else
				{
					selector.add(0.5, GO_UP_STAIR);
				}
			} else
			{
				int directionX = this.searchEndPlatform(EndPlatformOLD.END_STEP);
				if (directionX == NONE)
					directionX = this.searchEndPlatform(EndPlatformOLD.END_CLIFF);
				this.mummy.mummyState = new MummyStateWalking(this.mummy, directionX, MummyState.PLAYER_IS_UP);
			}

		} else if (this.mummy.player.y < this.mummy.y)// player esta abajo
		{

			if (result.getNearestDownStair() != null)
			{
				if (this.mummy.player.y <= result.getNearestDownStair().getDownStair().y)
					this.mummy.mummyState = new MummyStateSearchingStair(this.mummy, result.getNearestDownStair(),
							MummyState.PLAYER_IS_DOWN);
				else
				{
					selector.add(0.5, GO_DOWN_STAIR);
				}

			} else
			{
				int direccion = this.searchEndPlatform(EndPlatformOLD.END_CLIFF);
				if (direccion == NONE)
					direccion = this.searchEndPlatform(EndPlatformOLD.END_STEP);
				this.mummy.mummyState = new MummyStateWalking(this.mummy, direccion, MummyState.PLAYER_IS_DOWN);
			}

		} else
		{
			this.mummy.mummyState = new MummyStateWalking(this.mummy, MummyState.NONE, MummyState.PLAYER_IS_SOME_LEVEL);

		} // player esta al mismo nivel

	}

	/**
	 * Retorna true
	 */
	@Override
	protected boolean isDanger()
	{
		return true;
	}

	/**
	 * Llamado internamente por changeStatus. Indica hacia donde debe ir la momia
	 * dependiendo del tipo de fin de plataforma.
	 * 
	 * @param typeEnd indica el tipo de fin de plataforma, tomara los valores:
	 *                EndPlatform.END_CLIFF o EndPlatform.END_STEP
	 * @return valor numerico que tomara los valores NONE, LEFT o RIGHT
	 */
	private int searchEndPlatform(int typeEnd)
	{
		int r = NONE;
		EndPlatformOLD endToRight = this.endPlatform(true);
		EndPlatformOLD endToLeft = this.endPlatform(false);
		if (endToRight.getType() == typeEnd || endToLeft.getType() == typeEnd)
		{
			if (endToRight.getType() != typeEnd)
				r = LEFT;
			else if (endToLeft.getType() != typeEnd)
				r = RIGHT;
			else
			{

				if (this.mummy.x < this.mummy.player.x)

					r = RIGHT;
				else
					r = LEFT;
			}
		}

		return r;
	}

	/**
	 * Llamado cuando la momia muere. Cambia el estado a new
	 * MummyStateDying(this.mummy, mustTeleport); Ademas dispara el evento:
	 * Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
	 */
	@Override
	protected void die(boolean mustTeleport)
	{
		this.mummy.mummyState = new MummyStateDying(this.mummy, mustTeleport);
		Game.getInstance().eventFired(KVEventListener.MUMMY_DIE, this);
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

}
