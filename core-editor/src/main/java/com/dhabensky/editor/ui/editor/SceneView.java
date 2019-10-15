package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Scene;
import com.dhabensky.editor.Transform;
import com.dhabensky.editor.ui.components.BackgroundComponent;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class SceneView extends Widget {

	private OrthographicCamera camera;
	private Matrix4            savedMatrix = new Matrix4();
	private boolean            cameraDirty = true;
	private Scene              scene;

	private BackgroundComponent background = new BackgroundComponent(this, Color.BLACK);
	private Texture             texture;
	private NinePatch           arrow;


	public SceneView(Skin skin) {
		camera = new OrthographicCamera();
		texture = new Texture("badlogic.jpg");

		arrow = skin.getPatch("arrow-red");
//		arrow.setOrigin(2f / 16f, 4.5f / 8f);
//		arrow.setScale(16f, 16f);
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}


	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		cameraDirty = true;
	}

	@Override
	protected void positionChanged() {
		super.positionChanged();
		cameraDirty = true;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		background.draw(batch, parentAlpha);

		if (scene == null) {
			return;
		}

		savedMatrix.set(batch.getProjectionMatrix());

		if (cameraDirty) {
			updateCamera();
			cameraDirty = false;
		}

		batch.setProjectionMatrix(camera.combined);
		batch.setColor(Color.WHITE);

		for (Entity e : scene.getObjects()) {
			Transform t = e.getTransform();
			batch.draw(texture, t.getX(), t.getY());
			arrow.draw(batch, t.getX(), t.getY(), 100, 100);
		}

		batch.setProjectionMatrix(savedMatrix);
	}

	private void updateCamera() {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(-getX(), -getY());
		camera.update();
	}

}
