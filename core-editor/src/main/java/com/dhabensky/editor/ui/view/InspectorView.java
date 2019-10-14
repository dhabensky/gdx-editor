package com.dhabensky.editor.ui.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.dhabensky.editor.ui.components.BackgroundComponent;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class InspectorView extends WidgetGroup {

	private BackgroundComponent background = new BackgroundComponent(this, Color.GOLDENROD);

	@Override
	public void draw(Batch batch, float parentAlpha) {
		validate();
		background.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}

}
