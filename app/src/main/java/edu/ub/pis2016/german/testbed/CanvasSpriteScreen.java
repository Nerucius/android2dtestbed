package edu.ub.pis2016.german.testbed;

import android.graphics.Color;
import android.util.Log;

import edu.ub.pis2016.german.testbed.engine.android.AndroidSprite;
import edu.ub.pis2016.german.testbed.engine.framework.Game;
import edu.ub.pis2016.german.testbed.engine.framework.Graphics;
import edu.ub.pis2016.german.testbed.engine.framework.Input;
import edu.ub.pis2016.german.testbed.engine.framework.Screen;
import edu.ub.pis2016.german.testbed.engine.framework.graphics.Pixmap;
import edu.ub.pis2016.german.testbed.engine.framework.graphics.Sprite;
import edu.ub.pis2016.german.testbed.engine.graphics.OrthoCamera;
import edu.ub.pis2016.german.testbed.engine.graphics.SpriteGrid;
import edu.ub.pis2016.german.testbed.engine.math.MathUtils;
import edu.ub.pis2016.german.testbed.engine.math.Vector2;
import edu.ub.pis2016.german.testbed.engine.math.WindowedMean;

public class CanvasSpriteScreen extends Screen {

	OrthoCamera camera;
	Pixmap pixmap;
	Sprite strikeBase;
	Sprite[] sprites;
	SpriteGrid grid;
	Graphics g;

	public CanvasSpriteScreen(Game game) {
		super(game);

		this.g = game.getGraphics();

		camera = new OrthoCamera(1920, 1080);
		camera.setPosition(1920 / 2, 1080 / 2);
		camera.setZoom(1);
		camera.rotateTo(0f);

		pixmap = g.newPixmap("strike_base.png");
		pixmap.mirrorY();

		strikeBase = new AndroidSprite(pixmap);
		strikeBase.scaleTo(16,16);
		strikeBase.setOrigin(0.5f, 0.5f);

//		int nSprites = 25;
//		sprites = new Sprite[nSprites];
//		for (int i = 0; i < nSprites; i++) {
//			sprites[i] = new AndroidSprite(pixmap);
//			sprites[i].setOrigin(0.5f, 0.5f);
//			sprites[i].translateTo(MathUtils.random(0f, 1920f), MathUtils.random(0, 1080));
//		}

		//grid = new SpriteGrid(grass, 32, 32, 8, 8);

	}

	public Vector2 touch = new Vector2();
	public Vector2 orig = new Vector2();
	public Vector2 drag = new Vector2();
	public Vector2 delta = new Vector2();

	float ease, sum;

	@Override
	public void update(float deltaTime) {
		sum += deltaTime * 60;
		ease = (MathUtils.cosDeg(sum) + 1) * 0.5f;

		//strikeBase.translateTo(200, 200);
		strikeBase.rotateTo(45);

		float angle = strikeBase.getAngle();
		Vector2 dir = new Vector2(10, 0);
		dir.rotate(angle);
		strikeBase.translate(dir.x, dir.y);

//		for (Sprite s : sprites)
//			s.rotateTo(360f * ease);
		//camera.setZoom(1 + ease);

		for (Input.TouchEvent e : game.getInput().getTouchEvents()) {
			camera.unproject(touch.set(e.x, e.y));
			switch (e.type) {
				case Input.TouchEvent.TOUCH_DOWN:
					orig.set(touch);
					break;
				case Input.TouchEvent.TOUCH_DRAGGED:
					drag.set(touch);
					delta.set(orig).sub(drag);
					camera.translate(delta.scl(0.5f));
					break;
			}
		}


		camera.update();
	}

	float acc = 0;
	int fpsSecondDelay = 2;
	WindowedMean fpsMean = new WindowedMean(20);

	@Override
	public void present(float deltaTime) {
		g.clear(Color.CYAN);

		g.setTransformation(camera.combined);

//		for (Sprite s : sprites)
//			s.draw(g);

		strikeBase.draw(g);

		g.drawRect(200 - 5, 200 - 5, 10, 10, Color.BLUE);

		// Draw axes
		g.drawRect(-50, -50, 100, 100, Color.BLUE);
		g.drawRect(-100, -5, 200, 10, Color.RED);
		g.drawRect(0, -5, 200, 10, Color.RED);
		g.drawRect(-5, -100, 10, 200, Color.GREEN);
		g.drawRect(-5, 0, 10, 200, Color.GREEN);

//		g.drawRect(0, -5, 2000, 5, Color.RED);
//		g.drawRect(-5, 0, 5, 2000, Color.GREEN);
//		g.drawRect(orig.x - 5, orig.y - 5, 10, 10, Color.GREEN);
//		g.drawRect(drag.x - 5, drag.y - 5, 10, 10, Color.RED);

		fpsMean.addValue(1f / deltaTime);
		acc += deltaTime;
		if (acc > fpsSecondDelay) {
			acc -= fpsSecondDelay;
			Log.i("FPS:", fpsMean.getMean() + "");
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		for (Sprite s : sprites)
			s.dispose();
	}
}
