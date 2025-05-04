package util;

public abstract class GlobalEventListener
{
    public static final int ENTER_LEVEL = 0;
    
    public static final int PICKUP_JEWEL = 1;
    public static final int PICKUP_DAGGER = 2;
    public static final int PICKUP_PICKER = 3;
    public static final int PICKUP_ALL_JEWEL = 4;
    
    public static final int THROW_DAGGER = 5;
    public static final int ENTER_GIRATORY = 6;
    public static final int ACTIVATE_TRAP = 7;
        
    
    public static final int PLAYER_JUMP = 10;
    public static final int PLAYER_LANDING = 11;
    public static final int PLAYER_BEGIN_FALL = 12;
    public static final int PLAYER_DIE = 13;
    
    public static final int MUMMY_APPEAR = 20;
    public static final int MUMMY_DIE = 21;
    
    public static final int GO_NEXT_LEVEL = 30;
    public static final int ADD_ONE_LIVE = 40;
    
    public static void eventFired(int eventCode, Object param)
    {

    }
}
