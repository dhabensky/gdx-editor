package com.dhabensky.editor;

import javax.annotation.Nullable;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class EditorController {

	private Scene scene;

	private Entity selected;


	public EditorController() {

	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}

	void selectEntity(@Nullable Entity entity) {
		selected = entity;
	}

	@Nullable Entity getSelectedEntity() {
		return selected;
	}

}
