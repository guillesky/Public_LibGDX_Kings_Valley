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

    
    private boolean actionKey;
    private Vector2 nuevoRumbo;
    private boolean shotEnabled = true;
    private boolean nextEnabled = true;
    private boolean nextKey = false;

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
    
    
/*
    public boolean getShot()
    {
	boolean r = actionKey;
	this.actionKey = false;
	return r;
    }

    public void shot()
    {
	actionKey = true;
	this.shotEnabled = false;
    }

    public boolean isShotEnabled()
    {
	return shotEnabled;
    }

    public void enableShotEnabled()
    {
	this.shotEnabled = true;
    }

    public void next()
    {
	nextKey = true;
	this.nextEnabled = false;
    }

    public void enableNexttEnabled()
    {
	this.nextEnabled = true;
    }

    public boolean isNextKey()
    {
	boolean r = nextKey;
	this.nextKey = false;
	return r;

    }

    public boolean isNextEnabled()
    {
	return nextEnabled;
    }
*/
}
