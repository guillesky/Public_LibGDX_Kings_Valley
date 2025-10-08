package mainPackage;

import com.badlogic.gdx.ApplicationListener;

import engine.IGraphic;

/**
 * Interfaz que se extiende de IGraphic (propia del modelo del King Valley, no
 * existe dependencia ni acoplamiento con LibGDX) y ApplicationListener (Propia
 * de LibGDX)
 * 
 * @author Guillermo Lazzurri
 */
public interface IMyApplicationListener extends IGraphic, ApplicationListener
{

}
