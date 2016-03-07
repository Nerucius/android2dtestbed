package edu.ub.pis2016.german.testbed.engine.framework;

public abstract class Screen implements Disposable{
	protected final Game game;

	public Screen(Game game) {
		this.game = game;
	}

	public abstract void update(float deltaTime);

	public abstract void present(float deltaTime);

	public abstract void pause();

	public abstract void resume();

	public abstract void dispose();
}