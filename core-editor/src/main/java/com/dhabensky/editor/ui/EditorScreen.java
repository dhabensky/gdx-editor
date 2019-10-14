package com.dhabensky.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.dhabensky.editor.ui.view.InspectorView;
import com.dhabensky.editor.ui.view.SceneView;

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class EditorScreen extends BaseScreen {

	private Table root;

	@Override
	public void show() {
		super.show();

		Skin skin = EditorContext.skin;

		InspectorView inspectorView = new InspectorView();

		root = new Table();
		stage.setRoot(root);

		root.add(new SceneView()).expand().fill();
		root.add(inspectorView).width(192).expandY().fillY();
		root.row();
		root.add(new Widget()).colspan(2).height(100).expandX().fillX();

//		InputMultiplexer input = new InputMultiplexer();
//		input.addProcessor(new DebugInputProcessor(stage));
//		input.addProcessor(stage);
//		Gdx.input.setInputProcessor(input);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		root.setSize(stage.getWidth(), stage.getHeight());
	}

}
