package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class CameraHelper {

	private Actor actor;

	private OrthographicCamera camera      = new OrthographicCamera();
	private boolean            cameraDirty = true;
	private Vector3            tmpVec3     = new Vector3();


	public CameraHelper(Actor forActor) {
		this.actor = forActor;
	}


	public void invalidateCamera() {
		cameraDirty = true;
	}

	public void updateCamera() {
		if (cameraDirty) {
			updateCameraActual();
			cameraDirty = false;
		}
	}

	public OrthographicCamera getCamera() {
		return camera;
	}


	public void zoomTo(float zoom, float x, float y) {
		float dx = (x - actor.getWidth() / 2);
		float dy = (y - actor.getHeight() / 2);

		camera.position.x += (camera.zoom - zoom) * dx;
		camera.position.y += (camera.zoom - zoom) * dy;
		camera.zoom = zoom;
		cameraDirty = true;
	}

	public void pan(float dx, float dy) {

		dx *= camera.zoom;
		dy *= camera.zoom;

		camera.position.x -= dx;
		camera.position.y -= dy;
		cameraDirty = true;
	}


	public void localToWorld(Vector2 local) {
		actor.localToScreenCoordinates(local);
		tmpVec3.set(local.x, local.y, 0);
		camera.unproject(tmpVec3);
		local.set(tmpVec3.x, tmpVec3.y);
	}

	public void worldToLocal(Vector2 world) {
		worldToStage(world);
		actor.stageToLocalCoordinates(world);
	}

	public void worldToParentLocal(Vector2 world) {
		worldToStage(world);
		actor.getParent().stageToLocalCoordinates(world);
	}

	private void worldToStage(Vector2 world) {
		tmpVec3.set(world.x, world.y, 0);
		camera.project(tmpVec3);
		world.set(tmpVec3.x, tmpVec3.y);

		// y' = worldToLocal(localToWorld(y))
		// seems y' == y - 1, so
		world.y += 1;
	}

	private void updateCameraActual() {
		camera.viewportWidth = actor.getWidth();
		camera.viewportHeight = actor.getHeight();
		camera.update();

		// hack camera.projection to make camera render on view rectangle instead of whole screen

		float scaleX = actor.getWidth() / Gdx.graphics.getWidth();
		float scaleY = actor.getHeight() / Gdx.graphics.getHeight();

		float offsetLeft = actor.getX();
		float offsetRight = Gdx.graphics.getWidth() - actor.getRight();
		float cameraDx = (offsetLeft - offsetRight) / 2 * camera.zoom;

		float offsetTop = Gdx.graphics.getHeight() - actor.getTop();
		float offsetBottom = actor.getY();
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
