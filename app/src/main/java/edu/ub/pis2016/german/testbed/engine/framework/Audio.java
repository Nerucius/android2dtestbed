package edu.ub.pis2016.german.testbed.engine.framework;

import edu.ub.pis2016.german.testbed.engine.framework.audio.Music;
import edu.ub.pis2016.german.testbed.engine.framework.audio.Sound;

/** Interface for the Audio resources manager. Platform-independent skeleton. */
public interface Audio {
	public Music newMusic(String filename);

	public Sound newSound(String filename);
}