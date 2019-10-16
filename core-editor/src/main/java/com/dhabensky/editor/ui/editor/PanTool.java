package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class PanTool extends DragListener {

	private SceneView sceneView;
	private float     lastX;
	private float     lastY;

	public void setSceneView(SceneView sceneView) {
		this.sceneView = sceneView;
	}

	public SceneView getSceneView() {
		return sceneView;
	}

	@Override
	public void dragStart(InputEvent event, float x, float y, int pointer) {
		lastX = x;
		lastY = y;
	}

	@Override
	public void drag(InputEvent event, float x, float y, int pointer) {
		if (sceneView == null) {
			return;
		}
		if (event.getTarget() != sceneView) {
			return;
		}

		float dx = x - lastX;
		float dy = y - lastY;
		lastX = x;
		lastY = y;

		sceneView.pan(dx, dy);
	}

}
