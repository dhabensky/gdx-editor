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
import com.dhabensky.editor.EditorModel;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Transform;
import com.dhabensky.editor.ui.components.BackgroundComponent;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class SceneView extends Widget {

	private OrthographicCamera camera;
	private Matrix4            savedMatrix = new Matrix4();
	private boolean            cameraDirty = true;
	private EditorModel        model;

	private BackgroundComponent background = new BackgroundComponent(this, Color.BLACK);
	private Texture             texture;
	private NinePatch           arrow;


	public SceneView(Skin skin) {
		camera = new OrthographicCamera();
		camera.up.set(0, 1, 0);
		camera.direction.set(0, 0, -1);
		camera.position.set(0, 1, 0);
		camera.zoom = 0.01f;

		texture = new Texture("badlogic.jpg");

		arrow = skin.getPatch("arrow-red");
	}


	public void setSceneModel(EditorModel model) {
		this.model = model;
	}

	public EditorModel getSceneModel() {
		return model;
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

		if (model == null || model.getScene() == null) {
			return;
		}

		savedMatrix.set(batch.getProjectionMatrix());

		if (cameraDirty) {
			updateCamera();
			cameraDirty = false;
		}

		batch.setProjectionMatrix(camera.combined);
		batch.setColor(Color.WHITE);

		for (Entity e : model.getScene().getObjects()) {
			Transform t = e.getTransform();
			batch.draw(texture, t.getX(), t.getY(), 1, 1);
		}

		Entity selected = model.selectedEntity.getValue();
		if (selected != null) {
			Transform t = selected.getTransform();
			arrow.draw(batch, t.getX(), (t.getY()), 12, 5);
		}

		batch.setProjectionMatrix(savedMatrix);
	}

	private void updateCamera() {
		camera.viewportWidth = getWidth();
		camera.viewportHeight = getHeight();
		camera.update();

		// hack camera.projection to make camera render on view rectangle instead of whole screen

		float scaleX = getWidth() / Gdx.graphics.getWidth();
		float scaleY = getHeight() / Gdx.graphics.getHeight();

		float offsetLeft = getX();
		float offsetRight = Gdx.graphics.getWidth() - getRight();
		float cameraDx = (offsetLeft - offsetRight) / 2 * camera.zoom;

		float offsetTop = Gdx.graphics.getHeight() - getTop();
		float offsetBottom = getY();
		float cameraDy = (offsetBottom - offsetTop) / 2 * camera.zoom;

		camera.projection.scale(scaleX, scaleY, 1f);
		camera.projection.translate(cameraDx, cameraDy, 0);

		camera.combined.set(camera.projection);
		Matrix4.mul(camera.combined.val, camera.view.val);

		camera.invProjectionView.set(camera.combined);
		Matrix4.inv(camera.invProjectionView.val);
		camera.frustum.update(camera.invProjectionView);
	}

}
