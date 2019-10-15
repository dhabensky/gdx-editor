package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	private Vector3            tmpVec3     = new Vector3();
	private EditorModel        model;

	private BackgroundComponent background = new BackgroundComponent(this, Color.BLACK);
	private Texture             texture;
	private NinePatch           arrow;


	public SceneView(Skin skin) {
		camera = new OrthographicCamera();
		camera.up.set(0, 1, 0);
		camera.direction.set(0, 0, -1);
		camera.position.set(0, 1, 0);

		float pixelsPerUnit = 100;
		camera.zoom = 1f / pixelsPerUnit;

		texture = new Texture("badlogic.jpg");

		arrow = skin.getPatch("arrow-red");

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Vector2 v = new Vector2(x, y);
				localToWorld(v);
				Gdx.app.log("SceneView", String.format("local: %.1f, %.1f | world: %.1f, %.1f", x, y, v.x, v.y));
				worldToLocal(v);
				Gdx.app.log("SceneView", String.format("local: %.1f, %.1f", v.x, v.y));
			}
		});
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

		if (cameraDirty) {
			updateCamera();
			cameraDirty = false;
		}

		savedMatrix.set(batch.getProjectionMatrix());
		if (clipBegin()) {
			drawScene(batch);
			batch.flush();  // flush before we pop scissors
			clipEnd();
		}
		batch.setProjectionMatrix(savedMatrix);
	}

	private void drawScene(Batch batch) {
		batch.setProjectionMatrix(camera.combined);
		batch.setColor(Color.WHITE);

		for (Entity e : model.getScene().getObjects()) {
			Transform t = e.getTransform();
			batch.draw(texture, t.getX(), t.getY(), 1, 1);
		}

		Entity selected = model.selectedEntity.getValue();
		if (selected != null) {
			Transform t = selected.getTransform();
			arrow.draw(batch, t.getX(), (t.getY()), 200 * camera.zoom, 500 * camera.zoom);
		}
	}

	private void localToWorld(Vector2 local) {
		localToScreenCoordinates(local);
		tmpVec3.set(local.x, local.y, 0);
		camera.unproject(tmpVec3);
		local.set(tmpVec3.x, tmpVec3.y);
	}

	private void worldToLocal(Vector2 world) {
		tmpVec3.set(world.x, world.y, 0);
		camera.project(tmpVec3);
		world.set(tmpVec3.x, tmpVec3.y);
		stageToLocalCoordinates(world);
		world.y += 1;  // who knows why, but... seems its needed
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
