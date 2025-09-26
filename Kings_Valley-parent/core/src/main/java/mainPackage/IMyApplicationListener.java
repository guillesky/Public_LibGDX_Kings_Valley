package mainPackage;

import com.badlogic.gdx.ApplicationListener;

import modelo.IGraphic;

/**
 * @author Guillermo Lazzurri
 * Interfaz que se extiende de IGraphic (propia del modelo del King Valley, no exite dependencia ni acoplamiento con LibGDX) y ApplicationListener (Propia de LibGDX)
 */
public interface IMyApplicationListener extends IGraphic, ApplicationListener
{



}
