package edu.ub.pis2016.german.testbed;

import edu.ub.pis2016.german.testbed.engine.framework.Screen;
import edu.ub.pis2016.german.testbed.engine.opengl.GLGame;


public class OpenGLGame extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new OpenGLTestScreen(this);
	}
}
