package modelo;

import mummys.Mummy;
import util.Config;

public class MummyFactory
{
   
    
    
    public Mummy getMummy(float x,float y , int mummyType,Pyramid pyramid)
    {
	Mummy respuesta = null;
	float speedWalk=0;
	float speedWalkStairs=0;
	float decisionFactor=0;
	float minTimeToDecide=0;
	float maxTimeToDecide=0;
	
	
	
	
	switch (mummyType) 
	{

	case Mummy.WHITE_MUMMY:
	    speedWalk=Config.getInstance().getMummyWhiteSpeedWalk();
	    speedWalkStairs=Config.getInstance().getMummyWhiteSpeedWalkStairs();
	    decisionFactor=Config.getInstance().getMummyWhiteDecisionFactor();
	    minTimeToDecide=Config.getInstance().getMummyWhiteMinTimeToDecide();
	    maxTimeToDecide=Config.getInstance().getMummyWhiteMaxTimeToDecide();
	    break;
	    
	case Mummy.BLUE_MUMMY:
	    speedWalk=Config.getInstance().getMummyBlueSpeedWalk();
	    speedWalkStairs=Config.getInstance().getMummyBlueSpeedWalkStairs();
	    decisionFactor=Config.getInstance().getMummyBlueDecisionFactor();
	    minTimeToDecide=Config.getInstance().getMummyBlueMinTimeToDecide();
	    maxTimeToDecide=Config.getInstance().getMummyBlueMaxTimeToDecide();
	    break;
	case Mummy.YELLOW_MUMMY:
	    speedWalk=Config.getInstance().getMummyYellowSpeedWalk();
	    speedWalkStairs=Config.getInstance().getMummyYellowSpeedWalkStairs();
	    decisionFactor=Config.getInstance().getMummyYellowDecisionFactor();
	    minTimeToDecide=Config.getInstance().getMummyYellowMinTimeToDecide();
	    maxTimeToDecide=Config.getInstance().getMummyYellowMaxTimeToDecide();
	    break;
	case Mummy.ORANGE_MUMMY:
	    speedWalk=Config.getInstance().getMummyOrangeSpeedWalk();
	    speedWalkStairs=Config.getInstance().getMummyOrangeSpeedWalkStairs();
	    decisionFactor=Config.getInstance().getMummyOrangeDecisionFactor();
	    minTimeToDecide=Config.getInstance().getMummyOrangeMinTimeToDecide();
	    maxTimeToDecide=Config.getInstance().getMummyOrangeMaxTimeToDecide();
	    break;
	    
	case Mummy.RED_MUMMY: 
	    speedWalk=Config.getInstance().getMummyRedSpeedWalk();
	    speedWalkStairs=Config.getInstance().getMummyRedSpeedWalkStairs();
	    decisionFactor=Config.getInstance().getMummyRedDecisionFactor();
	    minTimeToDecide=Config.getInstance().getMummyRedMinTimeToDecide();
	    maxTimeToDecide=Config.getInstance().getMummyRedMaxTimeToDecide();
	    break;

	}
	respuesta= null;//new Mummy(mummyType,x,y,speedWalk,speedWalkStairs,decisionFactor,minTimeToDecide,maxTimeToDecide,pyramid);
	return respuesta;
    }
}
