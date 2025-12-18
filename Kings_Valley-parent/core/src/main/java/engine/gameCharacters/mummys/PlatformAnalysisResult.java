package engine.gameCharacters.mummys;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.level.LevelObject;
import engine.level.Pyramid;
import engine.level.Stair;
import util.GameRules;

/**
 * Representa el resultado del analisis de una plataforma desde la perspectiva
 * de un caracter
 * 
 * @author Guillermo Lazzurri
 */
public class PlatformAnalysisResult
{
    /**
     * Indica el final izquierdo de la plataforma
     */
    private EndPlatform endPlatformLeft;
    /**
     * Indica el final derecho de la plataforma
     */
    private EndPlatform endPlatformRight;
    /**
     * Indica las escaleras que suben en la plataforma (podria ser un ArrayList
     * vacio)
     */
    private ArrayList<Stair> upStairsInPlatform;
    /**
     * Indica las escaleras que bajan en la plataforma (podria ser un ArrayList
     * vacio)
     */
    private ArrayList<Stair> downStairsInPlatform;
    /**
     * Indica La escalera que sube mas cercana al caracter. Podria ser null.
     */
    private Stair nearestUpStair;
    /**
     * Indica La escalera que baja mas cercana al caracter. Podria ser null.
     */
    private Stair nearestDownStair;
    /**
     * Indica el id del gameCharacter a partir del cual se genero el analisis
     */
    private int gameCharacterId;

    /**
     * Constructor de clase a partir del analisis de la plataforma desde la
     * perspectiva del GameCharacter pasado por parametro
     * 
     * @param gameCharacter GameCharacter desde el cual debe realizarse el analisis
     *                      de la plataforma
     */
    public PlatformAnalysisResult(GameCharacter gameCharacter)
    {
	this.gameCharacterId = gameCharacter.getId();
	this.endPlatformLeft = this.endPlatform(gameCharacter, false);
	this.endPlatformRight = this.endPlatform(gameCharacter, true);
	this.upStairsInPlatform = this.getStairs(gameCharacter, true);
	this.downStairsInPlatform = this.getStairs(gameCharacter, false);
	this.nearestUpStair = this.getNearStair(gameCharacter, true);
	this.nearestDownStair = this.getNearStair(gameCharacter, false);

    }

    /**
     * Retorna un ArrayList de escaleras que suben o bajan (segun el parametro toUp)
     * que estan en la plataforma en la que esta parado el gameCharacter
     * 
     * @param gameCharacter    Se buscan las escalera sobre las que esta parado este
     *                         gameCharacter
     * @param endPlatFormLeft  Final Izquierdo de la plataforma
     * @param endPlatFormRight Final derecho de la plataforma
     * @param toUp             true si se busca una escalera que suba, false si
     *                         busco una escalera que baje
     * @return ArrayList de escaleras que suben o bajan (segun el parametro toUp)
     *         que estan en la plataforma en la que esta parado el gameCharacter
     */
    private ArrayList<Stair> getStairs(GameCharacter gameCharacter, boolean toUp)
    {
	Pyramid pyramid = gameCharacter.getPyramid();
	ArrayList<Stair> candidatesStairs = new ArrayList<Stair>();
	LevelObject beginOfStair;

	Iterator<Stair> it = pyramid.getAllStairs().iterator();
	while (it.hasNext())
	{
	    Stair stair = it.next();
	    if (toUp)
		beginOfStair = stair.getDownStair();
	    else
		beginOfStair = stair.getUpStair();
	    if (beginOfStair.y == gameCharacter.getLastFloorCoordinate()
		    && this.endPlatformLeft.getRectangle().x < beginOfStair.x
		    && endPlatformRight.getRectangle().x + endPlatformRight.getRectangle().width > beginOfStair.x)
		candidatesStairs.add(stair);
	}
	return candidatesStairs;
    }

    /**
     * Retorna el resultado de buscar una escalera cercana que suba o baje (podria
     * retornar null si el ArrayList candidatesStairs es vacio)
     * 
     * @param gameCharacter    El caracter a partir del cual se busca la escalera
     *                         mas cercana
     * @param endPlatformLeft  Final izquierdo de la plataforma
     * @param endPlatformRight Final derecho de la plataforma
     * @param candidatesStairs ArrayList con las escaleras de que suben o bajan de
     *                         la plataforma
     * @param toUp             true si se busca una escalera que suba, false si
     *                         busco una escalera que baje
     * @return El resultado de la escalera buscada, si no hay una escalera con las
     *         caracteristicas pedidas, retorna null
     */
    private Stair getNearStair(GameCharacter gameCharacter, boolean toUp)
    {
	ArrayList<Stair> candidatesStairs;
	if (toUp)
	    candidatesStairs = this.upStairsInPlatform;
	else
	    candidatesStairs = this.downStairsInPlatform;

	Stair r = null;
	if (!candidatesStairs.isEmpty())
	{
	    float posX = gameCharacter.x + gameCharacter.width * 0.5f;
	    Iterator<Stair> it = candidatesStairs.iterator();
	    Rectangle beginOfStair;
	    Stair stair = it.next();
	    r = stair;
	    if (toUp)
		beginOfStair = stair.getDownStair();
	    else
		beginOfStair = stair.getUpStair();

	    float aux = posX - (beginOfStair.x + beginOfStair.width * 0.5f);
	    if (aux < 0)
		aux *= -1;
	    float min = aux;
	    while (it.hasNext())
	    {
		stair = it.next();
		if (toUp)
		    beginOfStair = stair.getDownStair();
		else
		    beginOfStair = stair.getUpStair();

		aux = posX - (beginOfStair.x + beginOfStair.width * 0.5f);
		if (aux < 0)
		    aux *= -1;

		if (aux < min)
		{
		    min = aux;

		    r = stair;
		}
	    }

	}

	return r;

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

	while (pyramid.getCell(x, gameCharacter.getLastFloorCoordinate(), acum, 0) == null
		&& pyramid.getCell(x, gameCharacter.getLastFloorCoordinate(), acum, 1) == null
		&& pyramid.getCell(x, gameCharacter.getLastFloorCoordinate(), acum, -1) != null
		&& this.isColDesplaInMap(gameCharacter, acum))
	{
	    acum += inc;

	}
	EndPlatform epf2 = null;
/*
	if (!this.isColDesplaInMap(gameCharacter, acum))
	{
	    if (toRight)
		x = x - x % tileWidth + acum * tileWidth - 0.5f * GameRules.getInstance().getCharacterWidth();
	    else
		x = x - x % tileWidth + (acum + 1) * tileWidth - 0.5f * GameRules.getInstance().getCharacterWidth();

	    epf2 = new EndPlatform(EndPlatform.END_BLOCK, new Rectangle(x, gameCharacter.getLastFloorCoordinate(),
		    GameRules.getInstance().getCharacterWidth(), GameRules.getInstance().getCharacterFeetHeight()));
	} else*/
	{

	    LevelObject nearestGiratory = this.getNearestGiratory(gameCharacter, toRight);
	//    if (nearestGiratory != null)System.out.println("HAY UNA GIRATORIA");
	    if (nearestGiratory != null && Math.abs(nearestGiratory.x - gameCharacter.x) < Math.abs(acum) * tileWidth)
	    {
		Rectangle r = null;
		if (toRight)
		    r = new Rectangle(nearestGiratory.x - gameCharacter.width * .2f, nearestGiratory.y,
			    GameRules.getInstance().getCharacterWidth(),
			    GameRules.getInstance().getCharacterFeetHeight());
		else
		    r = new Rectangle(
			    nearestGiratory.x + nearestGiratory.width * 1.2f
				    - GameRules.getInstance().getCharacterWidth(),
			    nearestGiratory.y, GameRules.getInstance().getCharacterWidth(),
			    GameRules.getInstance().getCharacterFeetHeight());
		epf2 = new EndPlatform(EndPlatform.END_BLOCK, r);

	    } else

	    {
		type = this.typeEndPlatform(gameCharacter, x, acum);
		if (type == EndPlatform.END_CLIFF)
		{
		    if (toRight)
			x = x - x % tileWidth + acum * tileWidth - 0.1f * GameRules.getInstance().getCharacterWidth();
		    else
			x = x - x % tileWidth + (acum + 1) * tileWidth
				- 0.9f * GameRules.getInstance().getCharacterWidth();

		    epf2 = new EndPlatform(type,
			    new Rectangle(x, gameCharacter.getLastFloorCoordinate(),
				    GameRules.getInstance().getCharacterWidth(),
				    GameRules.getInstance().getCharacterFeetHeight()));

		} else
		{
		    if (toRight)
			x = x - x % tileWidth + acum * tileWidth - 0.5f * GameRules.getInstance().getCharacterWidth();
		    else
			x = x - x % tileWidth + (acum + 1) * tileWidth
				- 0.5f * GameRules.getInstance().getCharacterWidth();

		    epf2 = new EndPlatform(type,
			    new Rectangle(x, gameCharacter.getLastFloorCoordinate(),
				    GameRules.getInstance().getCharacterWidth(),
				    GameRules.getInstance().getCharacterFeetHeight()));
		}
	    }
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
System.out.println("BUsco por "+toRight);
	ArrayList<LevelObject> giratoriesCandidates = new ArrayList<LevelObject>();
	Iterator<LevelObject> it = gameCharacter.getPyramid().getGiratories().iterator();
	while (it.hasNext())
	{
	    giratory = it.next();
	    boolean condicionDirection = (gameCharacter.x <= giratory.x) == toRight;
	    if (giratory.y == gameCharacter.getLastFloorCoordinate() && condicionDirection)
		giratoriesCandidates.add(giratory);
	    
	    System.out.println(giratory+"\n"+gameCharacter+"\nlastfloor "+gameCharacter.getLastFloorCoordinate()+"\nsize of candidates: "+giratoriesCandidates.size()+condicionDirection);

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
	float tileWidth = GameRules.getInstance().getLevelTileWidthUnits();

	float aux = gameCharacter.x + gameCharacter.width + col * tileWidth;
	/*
	 * return gameCharacter.getColPosition() + col > 0 &&
	 * gameCharacter.getColPosition() + col <
	 * gameCharacter.getPyramid().getMapWidthInTiles() - 1;
	 */
	return gameCharacter.getColPosition() + col > 0
		&& aux < gameCharacter.getPyramid().getMapWidthInUnits() - tileWidth;

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
	if (pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 0) == null
		&& pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 1) == null
		&& pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, -1) == null)
	    type = EndPlatform.END_CLIFF;
	else if ((pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 1) != null
		&& pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 2) == null
		&& pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 3) == null)
		|| (pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 0) != null
			&& pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 1) == null
			&& pyramid.getCell(positionX, gameCharacter.getLastFloorCoordinate(), iDeltaX, 2) == null))
	    type = EndPlatform.END_STEP;
	else
	    type = EndPlatform.END_BLOCK;
	return type;
    }

    /**
     * Retorna el final izquierdo de la plataforma
     * 
     * @return El final izquierdo de la plataforma
     */
    public EndPlatform getEndPlatformLeft()
    {
	return endPlatformLeft;
    }

    /**
     * Retorna el final derecho de la plataforma
     * 
     * @return El final derecho de la plataforma
     */
    public EndPlatform getEndPlatformRight()
    {
	return endPlatformRight;
    }

    /**
     * Retorna la escalera que sube mas cercana. Si no hay escaleras en la
     * plataforma retorna null.
     * 
     * @return La escalera que sube mas cercana. Si no hay escaleras en la
     *         plataforma retorna null.
     */
    public Stair getNearestUpStair()
    {
	return nearestUpStair;
    }

    /**
     * Retorna la escalera que baja mas cercana. Si no hay escaleras en la
     * plataforma retorna null.
     * 
     * @return La escalera que baja mas cercana. Si no hay escaleras en la
     *         plataforma retorna null.
     */

    public Stair getNearestDownStair()
    {
	return nearestDownStair;
    }

    /**
     * Retorna las escaleras que suben en la plataforma
     * 
     * @return las escaleras que suben en la plataforma
     */
    public ArrayList<Stair> getUpStairsInPlatform()
    {
	return upStairsInPlatform;
    }

    /**
     * Retorna las escaleras que bajan en la plataforma
     * 
     * @return las escaleras que bajan en la plataforma
     */

    public ArrayList<Stair> getDownStairsInPlatform()
    {
	return downStairsInPlatform;
    }

    @Override
    public String toString()
    {
	return "PlatFormAnalisys [endEndPlatformLeft=" + endPlatformLeft + ", endEndPlatformRight=" + endPlatformRight
		+ ", nearestUpStair=" + nearestUpStair + ", nearestDownStair=" + nearestDownStair + "]";
    }

    /**
     * Indica si el caracter esta rentro de los limites de la plataforma
     * 
     * @param character Caracter a analizar
     * @return true si el player esta dentro de los limites de la plataforma, false
     *         en caso contrario
     */
    public boolean isCharacterReachable(GameCharacter character)
    {
	return this.endPlatformLeft.getRectangle().x <= character.x
		&& character.x <= this.endPlatformRight.getRectangle().x + this.endPlatformRight.getRectangle().width;
    }

    
    /**
     * Retorna el id del GameCharacter a partir del cual se realizo el analisis
     * 
     * @return El id del GameCharacter a partir del cual se realizo el analisis
     */
    public int getGameCharacterId()
    {
        return gameCharacterId;
    }

}
