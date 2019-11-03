package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.util.Observer;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class InspectorView extends VerticalGroup implements Observer<Entity> {

	private final TransformView transformView = new TransformView(EditorContext.skin);

	{
		addActor(transformView);
	}

	public TransformView getTransformView() {
		return transformView;
	}

	@Override
	public void onUpdate(Entity value) {
		transformView.onUpdate(value);
	}

}
