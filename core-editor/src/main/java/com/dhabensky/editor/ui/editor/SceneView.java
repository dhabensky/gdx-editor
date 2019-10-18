package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dhabensky.editor.EditorModel;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Transform;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class SceneView extends Widget {

	private EditorModel model;
	private CameraHelper helper;

	private Matrix4 savedMatrix = new Matrix4();
	private Vector2 tmpVec2     = new Vector2();

	private TextureRegion gridTexture;
	private Texture       texture;
	private Sprite        arrow;


	public SceneView(Skin skin) {
		helper = new CameraHelper(this);
		OrthographicCamera camera = helper.getCamera();
		camera.up.set(0, 1, 0);
		camera.direction.set(0, 0, -1);
		camera.position.set(0, 0, 0);

		float pixelsPerUnit = 100;
		camera.zoom = 1f / pixelsPerUnit;

		texture = new Texture("badlogic.jpg");
		gridTexture = skin.getRegion("white");

		arrow = new Sprite(new Texture("axis-arrows.png"));
		arrow.setOrigin(99f / 512f * arrow.getWidth(), 98f / 512f * arrow.getHeight());
		arrow.setScale(200f / arrow.getWidth());

		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tmpVec2.set(x, y);
				helper.localToWorld(tmpVec2);
				Gdx.app.log("SceneView", String.format("local: %.1f, %.1f | world: %.1f, %.1f", x, y, tmpVec2.x, tmpVec2.y));
			}
		});
	}


	public void setSceneModel(EditorModel model) {
		this.model = model;
	}

	public EditorModel getSceneModel() {
		return model;
	}

	public void zoomTo(float zoom, float x, float y) {
		helper.zoomTo(zoom, x, y);
		System.out.println("zoom: " + helper.getCamera().zoom);
	}

	public float getZoom() {
		return helper.getCamera().zoom;
	}

	public void pan(float dx, float dy) {
		helper.pan(dx, dy);
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		helper.invalidateCamera();
	}

	@Override
	protected void positionChanged() {
		super.positionChanged();
		helper.invalidateCamera();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (clipBegin()) {
			helper.updateCamera();

			if (model == null || model.getScene() == null) {
				return;
			}

			drawBackground(batch);
			drawWorld(batch);
			drawUi(batch);

			batch.flush();  // flush before we pop scissors
			clipEnd();
		}
	}


	private void drawWorld(Batch batch) {
		savedMatrix.set(batch.getProjectionMatrix());

		batch.setProjectionMatrix(helper.getCamera().combined);
		batch.setColor(Color.WHITE);

		for (Entity e : model.getScene().getObjects()) {
			Transform t = e.getTransform();
			batch.draw(texture, t.getX(), t.getY(), 1, 1);
		}

		batch.setProjectionMatrix(savedMatrix);
	}

	private void drawUi(Batch batch) {
		Entity selected = model.selectedEntity.getValue();
		if (selected != null) {
			tmpVec2.set(selected.getTransform().getPosition());
			helper.worldToParentLocal(tmpVec2);  // ui is drawn in parents coords
			arrow.setOriginBasedPosition(tmpVec2.x, tmpVec2.y);
			arrow.draw(batch);
		}
	}

	private void drawBackground(Batch batch) {
		tmpVec2.set(0, 0);
		helper.localToWorld(tmpVec2);
		float wMinX = tmpVec2.x;
		float wMinY = tmpVec2.y;
		tmpVec2.set(getWidth(), getHeight());
		helper.localToWorld(tmpVec2);
		float wMaxX = tmpVec2.x;
		float wMaxY = tmpVec2.y;

		float wx = (float) Math.ceil(wMinX);
		float wXlimit = (float) Math.ceil(wMaxX);
		while (wx < wXlimit) {

			tmpVec2.set(wx, 0);
			helper.worldToParentLocal(tmpVec2);

			batch.setColor(Color.LIGHT_GRAY);
			batch.draw(gridTexture, tmpVec2.x, getY(), 1f, getHeight());

			wx += 1f;
		}

		float wy = (float) Math.ceil(wMinY);
		float wYlimit = (float) Math.ceil(wMaxY);
		while (wy < wYlimit) {

			tmpVec2.set(0, wy);
			helper.worldToParentLocal(tmpVec2);

			batch.setColor(Color.LIGHT_GRAY);
			batch.draw(gridTexture, getX(), tmpVec2.y, getWidth(), 1);

			wy += 1f;
		}
	}


}
