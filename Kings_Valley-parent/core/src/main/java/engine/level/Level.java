package engine.level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import engine.DrawableElement;
import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.abstractGameCharacter.GameCharacter;
import engine.gameCharacters.mummys.Mummy;
import engine.gameCharacters.player.Player;
import engine.level.dagger.Dagger;
import engine.level.door.Door;
import util.GameRules;
import util.Constants;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa un nivel, con informacion la piramide, las
 *         momias, el player, la puerta en la cual aparecio el player y un identificador numerico
 */
public class Level
{

	private Pyramid pyramid;
	private ArrayList<Mummy> mummys = new ArrayList<Mummy>();
	private Player player = null;
	private int id;
	private Door doorIn;

	/**
	 * Constructor de clase
	 * 
	 * @param id      Idenificador numerico del nivel dentro del juego
	 * @param pyramid Piramide correspondiente
	 * @param mummys  Coleccion de momias
	 * @param door    Puerta por la que el player ingreso al nivel
	 * @param player  Corresponde al player
	 */
	public Level(int id, Pyramid pyramid, ArrayList<Mummy> mummys, Door door, Player player)
	{

		this.pyramid = pyramid;
		this.mummys = mummys;
		this.doorIn = door;
		this.id = id;
		this.player = player;

	}

	/**
	 * Actualiza todas las momias
	 * 
	 * @param deltaTime Tiempo transucurrido desde la ultima llamada
	 */
	private void updateMummys(float deltaTime)
	{
		Iterator<Mummy> it = this.mummys.iterator();
		while (it.hasNext())
			it.next().update(deltaTime);

	}

	/**
	 * @return la coleccion de momias
	 */
	public ArrayList<Mummy> getMummys()
	{
		return mummys;
	}

	/**
	 * Actualiza todos los mecanismos (puertas, giratorias, y trampas)
	 * 
	 * @param deltaTime Tiempo transucurrido desde la ultima llamada
	 */
	private void updateMechanism(float deltaTime)
	{
		ArrayList<TrapMechanism> trapMechanisms = this.pyramid.getTrapMechanisms();
		Collection<GiratoryMechanism> giratoryMechanisms = this.pyramid.getGiratoryMechanisms();

		for (TrapMechanism trapMechanism : trapMechanisms)
		{
			trapMechanism.update(deltaTime);
			if (this.checkCharacterSmash(trapMechanism, this.player))
				Game.getInstance().dying();
			for (Mummy mummy : mummys)
			{
				if (this.checkCharacterSmash(trapMechanism, mummy))
					mummy.die(true);

			}

		}

		Iterator<TrapMechanism> it = trapMechanisms.iterator();
		while (it.hasNext())
		{
			Mechanism mechanism = it.next();
			if (!mechanism.isActive())
			{
				it.remove();
				this.pyramid.removeGraphicElement(new DrawableElement(Constants.DRAWABLE_TRAP, mechanism));
				Game.getInstance().eventFired(KVEventListener.TRAP_END_DOWN, mechanism);

			}

		}

		for (GiratoryMechanism gMechanism : giratoryMechanisms)
		{
			if (gMechanism.isActive())
				gMechanism.update(deltaTime);
		}

		for (Door door : this.pyramid.getDoors())
		{

			door.update(deltaTime);
		}

	}

	/**
	 * Verifica si un GameCharacter (el player o una momia) es aplastado por una
	 * trampa
	 * 
	 * @param trapMechanism muro trampa a verificar
	 * @param gameCharacter GameCharacter a veriicar
	 * @return
	 */
	private boolean checkCharacterSmash(TrapMechanism trapMechanism, GameCharacter gameCharacter)
	{
		boolean condicionX = (gameCharacter.x + gameCharacter.width > trapMechanism.getX()
				&& gameCharacter.x < trapMechanism.getX() + GameRules.getInstance().getLevelTileWidthUnits());
		boolean condicionY = gameCharacter.y + gameCharacter.height > trapMechanism.getY()
				&& gameCharacter.y < trapMechanism.getY();
		return gameCharacter.getState() != GameCharacter.ST_JUMPING && condicionX && condicionY;

	}

	/**
	 * @return el Player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @return la piramide asociada
	 */
	public Pyramid getPyramid()
	{
		return pyramid;
	}

	/**
	 * Actualiza todas las espadas. Solo afectara a las espadas no clavas en el
	 * suelo
	 * 
	 * @param deltaTime Tiempo transucurrido desde la ultima llamada
	 */
	private void updateFlyingDagger(float deltaTime)
	{

		Iterator<Dagger> it = this.pyramid.getStuckedDaggers().iterator();
		while (it.hasNext())
		{
			Dagger dagger = it.next();
			dagger.updateDagger(deltaTime, pyramid, mummys);
		}

	}

	/**
	 * Llamado cuando el player recolecto todas las gemas. Lo delega en
	 * this.pyramid.prepareToExit();
	 */
	public void prepareToExit()
	{
		this.pyramid.prepareToExit();

	}

	/**
	 * @return true si se recolectaron todas las jemas del nivel, false en caso
	 *         contrario
	 */
	public boolean isReadyToExit()
	{
		return this.pyramid.getJewels().isEmpty();
	}

	/**
	 * Verifica si se activaron las palancas de las puertas
	 */
	public void checkLevers()
	{
		Iterator<Door> it = this.pyramid.getDoors().iterator();
		while (it.hasNext())
		{
			Door door = it.next();
			door.checkPushLever(player);
		}

	}

	/**
	 * @return numero identificatorio del nivel
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Verifica si el Player entro en una puerta, de ser asi, retorna la misma.
	 * 
	 * @return La puerta porla cual entro el player. Si el player no entro en
	 *         ninguna puerta, retorna null
	 */
	public Door checkPassages()
	{

		Iterator<Door> it = this.pyramid.getDoors().iterator();
		Door respuesta = null;
		Door door;
		do
		{
			door = it.next();
		} while (it.hasNext() && !door.checkEnterPassage(player));

		if (door.checkEnterPassage(player))
		{
			respuesta = door;
		}

		return respuesta;

	}

	/**
	 * @return La puerta por la cual el Player ingreso al nivel.
	 */
	public Door getDoorIn()
	{
		return doorIn;
	}

	/**
	 * @return true si una momia mato al Player, false en caso contrario.
	 */
	public boolean checkPlayerDieByMummy()
	{

		Iterator<Mummy> it = this.mummys.iterator();

		Mummy mummy = null;
		if (it.hasNext())
			do
			{
				mummy = it.next();
			} while (it.hasNext() && !(this.playerCoincideMummy(mummy) && this.player.isColision(mummy)));

		return (this.playerCoincideMummy(mummy) && this.player.isColision(mummy));
	}

	/**
	 * Llamado internamente por checkPlayerDieByMummy. Verifica si hay colision
	 * entre el Player y una momia, y su ambos estan en escalera o ambos fuera de
	 * escalera.
	 * 
	 * @param mummy Momia a verificar
	 * @return true si la momia coincide con el Player, false en caso contrario.
	 */
	private boolean playerCoincideMummy(Mummy mummy)
	{
		boolean bothInStair = (this.player.isInStair() && mummy.isInStair());
		boolean bothNotInStair = (!this.player.isInStair() && !mummy.isInStair());

		return (mummy.isDanger() && (bothInStair || bothNotInStair));

	}

	/**
	 * Libera en cascada los recursos cargados.
	 */
	public void dispose()
	{
		this.pyramid.dispose();
	}

	/**
	 * Actualiza los elementos del nivel. Llama a:<br>
	 * this.updateMechanism(deltaTime); <br>
	 * this.updateMummys(deltaTime); <br>
	 * this.updateFlyingDagger(deltaTime);
	 * 
	 * @param deltaTime Tiempo transcurrido desde la ultima actualizacion.
	 */
	public void update(float deltaTime)
	{
		this.updateMechanism(deltaTime);
		this.updateMummys(deltaTime);
		this.updateFlyingDagger(deltaTime);

	}

}
