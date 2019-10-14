package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Scene;
import com.dhabensky.editor.Transform;
import com.dhabensky.editor.ui.components.BackgroundComponent;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class SceneView extends Widget {

	private BackgroundComponent background = new BackgroundComponent(this, Color.BLACK);

	private OrthographicCamera camera;
	private Texture            texture;
	private Matrix4            savedMatrix = new Matrix4();
	private boolean            cameraDirty = true;

	private Scene scene;


	public SceneView() {
		createCamera();
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}


	private void createCamera() {
		camera = new OrthographicCamera();
		texture = new Texture("badlogic.jpg");
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

//		Gdx.app.log("scene_view", getX() + " " + getY() + " " + getWidth() + " " + getHeight());
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
		}

		batch.setProjectionMatrix(savedMatrix);
	}

	private void updateCamera() {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(-getX(), -getY());
		camera.update();
	}

}
