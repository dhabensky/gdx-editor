package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class ZoomTool extends InputListener {

	private SceneView sceneView;

	public void setSceneView(SceneView sceneView) {
		this.sceneView = sceneView;
	}

	public SceneView getSceneView() {
		return sceneView;
	}

	@Override
	public boolean scrolled(InputEvent event, float x, float y, int amount) {
		if (sceneView == null) {
			return false;
		}
		sceneView.zoom(amount);
		return true;
	}

}
