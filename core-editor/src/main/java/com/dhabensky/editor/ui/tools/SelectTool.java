package com.dhabensky.editor.ui.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.ui.editor.SceneView;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class SelectTool extends ActorGestureListener {

	private SceneView sceneView;

	public void setSceneView(SceneView sceneView) {
		this.sceneView = sceneView;
	}

	public SceneView getSceneView() {
		return sceneView;
	}

	@Override
	public void tap(InputEvent event, float x, float y, int count, int button) {
		Entity e = sceneView.hitEntity(x, y);
		sceneView.getSceneModel().selectedEntity.setValue(e);
	}

}
