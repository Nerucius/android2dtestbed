package edu.ub.pis2016.german.testbed;

import edu.ub.pis2016.german.testbed.engine.android.AndroidGame;
import edu.ub.pis2016.german.testbed.engine.framework.Screen;


public class Canvas2DGame extends AndroidGame {
	@Override
	public Screen getStartScreen() {
		return new CanvasSpriteScreen(this);
	}
}
