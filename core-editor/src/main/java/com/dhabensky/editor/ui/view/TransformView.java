package com.dhabensky.editor.ui.view;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.dhabensky.editor.ui.EditorContext;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class TransformView extends Table {

	{
		Skin skin = EditorContext.skin;

		row();
		add(new Label("position", skin)).colspan(2).pad(4);
		row();
		add(new Label("x", skin));
		add(new TextField("0.0", skin)).width(50);
		row();
		add(new Label("y", skin));
		add(new TextField("0.0", skin)).width(50);
	}

}
