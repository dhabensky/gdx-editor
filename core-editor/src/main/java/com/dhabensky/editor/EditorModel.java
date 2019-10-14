package com.dhabensky.editor;

import com.dhabensky.editor.util.BaseObservable;
import com.dhabensky.editor.util.Observable;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class EditorModel {

	private Scene scene;

	public final Observable<Entity> selectedEntity = new BaseObservable<>();

	public EditorModel() {
		selectedEntity.setValue(null);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}

}
