package components;

import javafx.animation.Timeline;

public class GameValue {
	public static boolean IsWin, IsPass, IsLose;
	public static boolean IsBag, IsBook, IsCloth;
	public static boolean IsStart;
	
	//timer and range of overtime
    public static int TIMER = 0;
    public static int OVERTIME = 120;
    //timeline
    public static Timeline TIMELINE = null;
    
  //game outcome/state
    public static byte WIN = 1;
    public static byte UNSURE = 0;
    public static byte LOSE = -1;
    public static byte STATE = UNSURE;
    
	void start() {
		IsStart = false;
	}

}
