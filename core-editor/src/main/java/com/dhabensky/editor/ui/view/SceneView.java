package com.dhabensky.editor.ui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
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


	public SceneView() {
		createCamera();
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

		savedMatrix.set(batch.getProjectionMatrix());

		if (cameraDirty) {
			updateCamera();
			cameraDirty = false;
		}

		batch.setProjectionMatrix(camera.combined);
		batch.setColor(Color.WHITE);
		batch.draw(texture, 20, 20);

		batch.setProjectionMatrix(savedMatrix);
	}

	private void updateCamera() {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(-getX(), -getY());
		camera.update();
	}

}
