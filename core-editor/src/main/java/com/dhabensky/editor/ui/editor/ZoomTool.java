package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class ZoomTool extends InputListener {

	private SceneView sceneView;
	private float zoomPower = 1.25f;

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

		if (amount > 0) {
			sceneView.zoom(zoomPower, x, y);
		}
		else if (amount < 0) {
			sceneView.zoom(1f / zoomPower, x, y);
		}

		return true;
	}

}
