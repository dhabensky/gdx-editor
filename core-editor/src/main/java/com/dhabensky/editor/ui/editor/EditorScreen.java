package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dhabensky.editor.ui.BaseScreen;

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
		ScrollPane inspectorScroll = new ScrollPane(inspectorView, skin);

		Label assetsView = new Label("Here will be assets", skin);
		ScrollPane assetsPane = new ScrollPane(assetsView, skin);

		root = new Table();
		stage.setRoot(root);

		root.add(new SceneView()).expand().fill();
		root.add(inspectorScroll).width(192).expandY().fillY();
		root.row();
		root.add(assetsPane).colspan(2).height(100).expandX().fillX();

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
