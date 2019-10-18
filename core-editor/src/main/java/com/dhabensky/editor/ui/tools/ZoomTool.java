package com.dhabensky.editor.ui.tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.dhabensky.editor.ui.editor.SceneView;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class ZoomTool extends InputListener {

	private SceneView sceneView;
	private float     zoomPower = 1.25f;
	private float     minZoom   = 1e-3f;
	private float     maxZoom   = 1e+4f;

	public void setSceneView(SceneView sceneView) {
		this.sceneView = sceneView;
	}

	public SceneView getSceneView() {
		return sceneView;
	}

	@Override
	public boolean scrolled(InputEvent event, float x, float y, int amount) {
		if (event.isHandled()) {
			return true;
		}
		if (sceneView == null) {
			return false;
		}
		if (amount == 0) {
			return false;
		}

		float newZoom;
		if (amount > 0) {
			newZoom = sceneView.getZoom() * zoomPower;
		}
		else if (amount < 0) {
			newZoom = sceneView.getZoom() / zoomPower;
		}
		else {
			return false;
		}

		newZoom = MathUtils.clamp(newZoom, minZoom, maxZoom);

		sceneView.zoomTo(newZoom, x, y);

		return true;
	}

}
