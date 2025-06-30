package modelo.control;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Controls
{
    private class KeyBooleans
    {
	protected boolean pushed = false;
	protected boolean enabled = true;
    }

    
  
    private Vector2 nuevoRumbo;
   
  

    private HashMap<Integer, KeyBooleans> hashMapKeys = new HashMap<Integer, KeyBooleans>();

    public Vector2 getNuevoRumbo()
    {
	return nuevoRumbo;
    }

    public void setNuevoRumbo(Vector2 nuevoRumbo)
    {
	this.nuevoRumbo = nuevoRumbo;
    }

    private void shot(int key)
    {
	KeyBooleans kb = this.hashMapKeys.get(key);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeys.put(key, kb);
	}
	kb.pushed = true;
	kb.enabled = false;
    }

    public boolean getShot(int key)
    {
	KeyBooleans kb = this.hashMapKeys.get(key);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeys.put(key, kb);
	}
	boolean r = kb.pushed;
	kb.pushed = false;
	return r;
    }

    private boolean isShotEnabled(int key)
    {
	KeyBooleans kb = this.hashMapKeys.get(key);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeys.put(key, kb);
	}
	return kb.enabled;
    }

    private void enableShotEnabled(int key)
    {
	KeyBooleans kb = this.hashMapKeys.get(key);
	if (kb == null)
	{
	    kb = new KeyBooleans();
	    this.hashMapKeys.put(key, kb);
	}
	kb.enabled = true;
    }
    
    public void processKey(int key) 
    {
	if (Gdx.input.isKeyPressed(key) && this.isShotEnabled(key))
	    this.shot(key);
	if (!Gdx.input.isKeyPressed(key) && !this.isShotEnabled(key))
	    this.enableShotEnabled(key);
	
    }
    
 
}
