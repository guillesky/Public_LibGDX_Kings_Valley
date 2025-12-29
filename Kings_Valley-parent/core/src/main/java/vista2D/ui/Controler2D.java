package vista2D.ui;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import controler.AbstractControler;
import controler.IView;
import engine.KVEventListener;
import engine.game.Game;
import facade.Facade;

/**
 * Clase Controlador de la UI inicial del juego (patron MVC)
 * 
 * @author Guillermo Lazzurri
 */
public class Controler2D extends AbstractControler implements KVEventListener
{
    private ClickListener clickListener;
    private ChangeListener changeListener;
    private UI2D ui;

    /**
     * Contructor de clase
     * 
     * @param view Objeto que representa la vista del patron.
     */
    public Controler2D(IView view)
    {
	super(view);
	this.ui = (UI2D) view;
	this.clickListener = new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		Actor actor = event.getTarget();

		while (actor != null && actor.getUserObject() == null)
		{
		    actor = actor.getParent();
		}
		int action = (int) actor.getUserObject();
		switch (action)
		{
		case AbstractControler.NEW_GAME:
		{
		    doNewGame();
		    break;

		}
		case AbstractControler.EXIT:
		{
		    doExit();
		    break;

		}

		case AbstractControler.RETRY:
		    doRetry();

		    break;

		case AbstractControler.MAIN_MENU:
		    Controler2D.this.doBackToMainMenu();
		    break;
		case AbstractControler.SHOW_MAP:
		    doShowMap();
		    break;
		case AbstractControler.HIDE_MAP:
		    doHideMap();
		    break;
		}
	    }

	};

	this.changeListener = new ChangeListener()
	{
	    @Override
	    public void changed(ChangeEvent event, Actor actor)
	    {
		while (actor != null && actor.getUserObject() == null)
		{
		    actor = actor.getParent();
		}
		int action = (int) actor.getUserObject();
		Slider sl = (Slider) actor;
		switch (action)
		{
		case AbstractControler.MASTER_VOLUME:
		    changeMasterVolume(sl.getValue());
		    break;
		case AbstractControler.MUSIC_VOLUME:
		    changeMusicVolume(sl.getValue());
		    break;
		case AbstractControler.FX_VOLUME:
		    changeSoundsVolume(sl.getValue());
		    break;

		}
	    }

	};

    }

    /**
     * Llamado para cambiar el volumen de los efectos de sonido
     * 
     * @param value Valor del volumen. Debe estar comprendido entre 0 y 100
     */
    protected void changeSoundsVolume(float value)
    {
	Facade.getInstance().setSoundsVolume(value / 100f);

    }

    /**
     * Llamado para cambiar el volumen de la musica
     * 
     * @param value Valor del volumen. Debe estar comprendido entre 0 y 100
     */
    protected void changeMusicVolume(float value)
    {
	Facade.getInstance().setMusicVolume(value / 100f);

    }

    /**
     * Llamado para cambiar el volumen general
     * 
     * @param value Valor del volumen. Debe estar comprendido entre 0 y 100
     */
    protected void changeMasterVolume(float value)
    {
	Facade.getInstance().setMasterVolume(value / 100f);

    }

    /**
     * Retorna el objeto que responde a los clicks de los botones
     * 
     * @return El objeto que responde a los clicks de los botones
     */
    public ClickListener getInputListener()
    {
	return clickListener;
    }

    /**
     * Llamado al pedir salir del juego
     */
    protected void doExit()
    {

	Gdx.app.exit();
    }

    /**
     * Retorna un objeto changeListener que responde a los cambios de volumenes
     * 
     * @return Objeto changeListener que responde a los cambios de volumenes
     */
    public ChangeListener getChangeListener()
    {
	return changeListener;
    }

    /**
     * Llamado al iniciar un nuevo juego
     */
    private void doNewGame()
    {
	// System.out.println("Extendido: " + this.view.isExtendedVersion() + "
	// Episodio: " + this.view.getEpisode());
	this.ui.createMap(this.view.isExtendedVersion());
	Facade.getInstance().startNewGame(this.view.isExtendedVersion(), this.view.getDificultLevel(),
		this.view.getEpisode());

    }

    /**
     * Llamado al cambiar el idioma
     * 
     * @param languageName nombre del nuevo idioma
     */
    public void changeLanguage(String languageName)
    {
	Facade.getInstance().changeLanguage(languageName);
	this.view.updateLanguage();
    }

    @Override
    public void eventFired(int eventCode, Object param)
    {
	switch (eventCode)
	{
	case KVEventListener.PAUSED_IS_CHANGED:
	{
	    boolean isPaused = (boolean) param;
	    if (isPaused)
	    {
		this.ui.doUiInGame();

	    } else
	    {
		this.ui.doEnterGame();
		doHideMap();

	    }
	    break;
	}

	case KVEventListener.ENTERING_LEVEL:
	{
	    this.ui.doEnteringLevel();
	    break;
	}

	case KVEventListener.GAME_OVER:
	{
	    doMainMenu();
	    break;
	}

	case KVEventListener.FINISH_ALL_LEVELS:
	{
	    doFinishAllLevels();
	    break;
	}

	case KVEventListener.FINISH_EPISODE:
	{
	    int episodeFinished = (int) param;
	    doFinishEpisode(episodeFinished);
	    break;
	}

	}

    }

    /**
     * Llamado para cuando se finaliza un episodio. Se debe actualizar la lista de
     * episodios disponibles en la vista.
     */
    private void doFinishEpisode(int episodeFinished)
    {
	Facade.getInstance().finishEpisode(episodeFinished);
	
    }

    /**
     * Llamado para cuando se finalizaron todos los niveles del juego. Se debe
     * habilitar la opcion de cambio de dificultad
     */
    private void doFinishAllLevels()
    {
	Facade.getInstance().finishAllLevels();
    }

    @Override
    public void updateframe(float deltaTime)
    {

    }

    /**
     * Llamado al ocultar el mapa
     */
    private void doHideMap()
    {
	Facade.getInstance().hideMap();
    }

    /**
     * Llamado al mostrar el mapa
     */
    private void doShowMap()
    {
	Facade.getInstance().showMap();
    }

    /**
     * Llamado al ir o regresar al menu principal desde la UI inicial
     */
    private void doMainMenu()
    {
	Facade.getInstance().mainMenu();

    }

    /**
     * Llamado al volver a la pantalla inicial durante el juego
     */
    protected void doBackToMainMenu()
    {
	Game.getInstance().pressPause();
	Game.getInstance().endGame();
    }

    /**
     * Llamado para reintentar el juego (perdiendo una vida)
     */
    protected void doRetry()
    {
	Facade.getInstance().retry();
    }

   

    /**
     * Retorna el volumen general
     * 
     * @return volumen general
     */
    protected float getMasterVolume()
    {
	return Facade.getInstance().getGameConfig().getMasterVolume();
    }

    /**
     * Retorna el volumen de la musica
     * 
     * @return volumen de la musica
     */
    protected float getMusicVolume()
    {
	return Facade.getInstance().getGameConfig().getMusicVolume();
    }

    /**
     * Retorna el volumen de los efectos de sonido
     * 
     * @return volumen de los efectos de sonido
     */
    protected float getSoundsVolume()
    {
	return Facade.getInstance().getGameConfig().getSoundsVolume();
    }

    /**
     * Retorna los nombres de los idiomas posibles
     * 
     * @return Los nombres de los idiomas posibles
     */
    public Iterator<String> getLanguagesName()
    {

	return Facade.getInstance().getAllLanguages().getLanguagesName();
    }

}
