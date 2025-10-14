package engine.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.LevelObject;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;

/**
 * Clase que analiza la plataforma desde la perspecctiva de un caracter.
 * 
 * @author Guillermo Lazzurri
 */
public class PlatformAnalyzer

{

	/**
	 * Retorna un PlatformAnalysisResult a partir del analisis de la plataforma
	 * desde la perspectiva del GameCharacter pasado por parametro
	 * 
	 * @param gameCharacter GameCharacter desde el cual debe realizarse el analisis
	 *                      de la plataforma
	 * @return el resultado del analisis de la plataforma desde la perspectiva del
	 *         GameCharacter pasado por parametro
	 */
	public PlatformAnalysisResult getPlatFormAnalisys(GameCharacter gameCharacter)
	{
		EndPlatform endPlatFormLeft = this.endPlatform(gameCharacter, false);
		EndPlatform endPlatFormRight = this.endPlatform(gameCharacter, true);
		Stair upStair = this.getNearStair(gameCharacter, endPlatFormLeft, endPlatFormRight, true);
		Stair downStair = this.getNearStair(gameCharacter, endPlatFormLeft, endPlatFormRight, false);
		return new PlatformAnalysisResult(endPlatFormLeft, endPlatFormRight, upStair, downStair);
	}

	/**
	 * Llamado internamente por getNearStair.<br>
	 * Retorna la escalera mas cercana al GameCharacter pasado por parametro.
	 * 
	 * 
	 * @param gameCharacter    El GameCharacter a partir del cual se busca la
	 *                         escalera mas cercana
	 * @param endPlatformLeft  El final izquierdo de la plataforma
	 * @param endPlatformRight El final derecho de la plataforma
	 * @param toUp             true si busco una escalera que suba, false si busco
	 *                         una escalera que baje
	 * @param toRight          true si busco la escalera hacia la derecha, false si
	 *                         la busco haia la izquierda
	 * @return La escalera mas cercana que satisfaga los parametros enviados. Si no
	 *         hay ninguna escalera que cumpla los requisitos se devuelve null
	 */
	private Stair nearStair(GameCharacter gameCharacter, EndPlatform endPlatformLeft, EndPlatform endPlatformRight,
			boolean toUp, boolean toRight)
	{
		Stair r = null;
		Pyramid pyramid = gameCharacter.getPyramid();
		ArrayList<Stair> candidatesStairs = new ArrayList<Stair>();
		LevelObject footStair;

		Iterator<Stair> it = pyramid.getAllStairs().iterator();
		float tileWidth = GameRules.getInstance().getLevelTileWidthUnits();
		float posX = gameCharacter.x + gameCharacter.width * 0.5f;
		while (it.hasNext())
		{
			Stair stair = it.next();
			if (toUp)
				footStair = stair.getDownStair();
			else
				footStair = stair.getUpStair();
			if (footStair.y == gameCharacter.y && (toRight && footStair.x >= posX || !toRight && footStair.x <= posX))
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
			if (toUp)
				footStair = minStair.getDownStair();
			else
				footStair = minStair.getUpStair();

			if (footStair.x >= endPlatformLeft.getRectangle().x - tileWidth
					&& footStair.x <= endPlatformRight.getRectangle().x + tileWidth)
				r = minStair;
		}

		return r;
	}

	/**
	 * Retorna el resultado de buscar una escalera cercana que suba o baje (podria
	 * retornar null)
	 * 
	 * @param toUp true si se busca una escalera que suba, false si busco una
	 *             escalera que baje
	 * @return El resultado de la escalera buscada, si no hay una escalera con las
	 *         caracteristicas pedidas, retorna null
	 */
	private Stair getNearStair(GameCharacter gameCharacter, EndPlatform endPlatformLeft, EndPlatform endPlatformRight,
			boolean toUp)
	{
		Stair stair = null;
		Stair toRight = this.nearStair(gameCharacter, endPlatformLeft, endPlatformRight, toUp, true);
		Stair toLeft = this.nearStair(gameCharacter, endPlatformLeft, endPlatformRight, toUp, false);

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

				if (footStairRight.x - gameCharacter.x < gameCharacter.x - footStairLeft.x)
					stair = toRight;
				else
					stair = toLeft;

			}
		}

		return stair;
	}

	/**
	 * Crea y retorna un objeto de tipo EndPlatform indicando el tipo de final de
	 * plataforma buscado
	 * 
	 * @param toRight true si se busca el final de plataforma hacia la derecha,
	 *                false si se lo busca por izquierda
	 * @return Objeto de tipo EndPlatform que indica el tipo de final de plataforma
	 */
	private EndPlatform endPlatform(GameCharacter gameCharacter, boolean toRight)
	{
		int inc;
		int acum = 0;
		int type;
		float tileWidth = GameRules.getInstance().getLevelTileWidthUnits();

		float x;
		Pyramid pyramid = gameCharacter.getPyramid();
		x = gameCharacter.x;
		if (toRight)
		{
			inc = 1;
			x += gameCharacter.width;
		} else

		{
			inc = -1;

		}

		while (pyramid.getCell(x, gameCharacter.y, acum, 0) == null
				&& pyramid.getCell(x, gameCharacter.y, acum, 1) == null
				&& pyramid.getCell(x, gameCharacter.y, acum, -1) != null && this.isColDesplaInMap(gameCharacter, acum))
		{
			acum += inc;

		}
		EndPlatform epf2 = null;
		LevelObject nearestGiratory = this.getNearestGiratory(gameCharacter, toRight);
		if (nearestGiratory != null && Math.abs(nearestGiratory.x - gameCharacter.x) < Math.abs(acum) * tileWidth)
		{
			Rectangle r = null;
			if (toRight)
				r = new Rectangle(nearestGiratory.x, nearestGiratory.y, GameRules.getInstance().getCharacterWidth(),
						GameRules.getInstance().getCharacterFeetHeight());
			else
				r = new Rectangle(
						nearestGiratory.x + nearestGiratory.width - GameRules.getInstance().getCharacterWidth(),
						nearestGiratory.y, GameRules.getInstance().getCharacterWidth(),
						GameRules.getInstance().getCharacterFeetHeight());
			epf2 = new EndPlatform(EndPlatform.END_BLOCK, r);

		} else

		{
			type = this.typeEndPlatform(gameCharacter, x, acum);

			if (toRight)
				x = x - x % tileWidth + acum * tileWidth - 0.1f * GameRules.getInstance().getCharacterWidth();
			else
				x = x - x % tileWidth + (acum + 1) * tileWidth - 0.9f * GameRules.getInstance().getCharacterWidth();
			epf2 = new EndPlatform(type, new Rectangle(x, gameCharacter.y, GameRules.getInstance().getCharacterWidth(),
					GameRules.getInstance().getCharacterFeetHeight()));
		}

		return epf2;

	}

	/**
	 * LLamado internamente por endPlatform. Retorna la puerta giratoria mas cercana
	 * al caracter que este en la misma plataforma. Si no existe retorna null
	 * 
	 * @param gameCharacter Caracter a partir del cual de busca la puerta giratoria
	 * @param toRight       true si busco la giratoria hacia la derecha, false si la
	 *                      busco hacia la izquierda
	 * @return La puerta giratoria mas cercana al caracter que este en la misma
	 *         plataforma. Si no existe retorna null
	 */
	private LevelObject getNearestGiratory(GameCharacter gameCharacter, boolean toRight)
	{
		LevelObject r = null;
		LevelObject giratory = null;

		ArrayList<LevelObject> giratoriesCandidates = new ArrayList<LevelObject>();
		Iterator<LevelObject> it = gameCharacter.getPyramid().getGiratories().iterator();
		while (it.hasNext())
		{
			giratory = it.next();
			boolean condicionDirection = (gameCharacter.x <= giratory.x) == toRight;
			if (giratory.y == gameCharacter.y && condicionDirection)
				giratoriesCandidates.add(giratory);
		}

		it = giratoriesCandidates.iterator();
		if (it.hasNext())
		{
			r = it.next();
			while (it.hasNext())
			{
				giratory = it.next();
				if (Math.abs(gameCharacter.x - giratory.x) < Math.abs(gameCharacter.x - r.x))
					r = giratory;
			}
		}
		return r;
	}

	/**
	 * Metodo llamado internadmente por this.endPlatform. Evita que durante los
	 * calculos se busque fuera de la piramide
	 * 
	 * @param col cantidad de desplazamiento
	 * @return true si se esta dentro del mapa, false en caso contrario.
	 */
	private boolean isColDesplaInMap(GameCharacter gameCharacter, int col)
	{

		return gameCharacter.getColPosition() + col > 0
				&& gameCharacter.getColPosition() + col < gameCharacter.getPyramid().getMapWidthInTiles() - 1;
	}

	/**
	 * Indica el tipo de fin de plataforma que existe de acuerdo a la posicionX y al
	 * desplazamiento en x pasado por parametro
	 * 
	 * @param gameCharacter Caracter que se esta evaluando
	 * @param positionX     coordenada x desde donde se inica la busqueda
	 * @param iDeltaX       Desplazamiento en x
	 * @return Valor entero que representa el tipo de final de plataforma. Puede
	 *         tomar los valores:<br>
	 *         EndPlatform.END_CLIFF;<br>
	 *         EndPlatform.END_STEP;<br>
	 *         EndPlatform.END_BLOCK
	 */
	private int typeEndPlatform(GameCharacter gameCharacter, float positionX, int iDeltaX)
	{
		int type = -1;
		Pyramid pyramid = gameCharacter.getPyramid();
		if (pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 0) == null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 1) == null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, -1) == null)
			type = EndPlatformOLD.END_CLIFF;
		else if ((pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 1) != null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 2) == null
				&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 3) == null)
				|| (pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 0) != null
						&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 1) == null
						&& pyramid.getCell(positionX, gameCharacter.y, iDeltaX, 2) == null))
			type = EndPlatformOLD.END_STEP;
		else
			type = EndPlatformOLD.END_BLOCK;
		return type;
	}

}
