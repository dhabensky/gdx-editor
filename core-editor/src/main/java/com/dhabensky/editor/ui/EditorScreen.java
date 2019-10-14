package com.dhabensky.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

		Widget sceneView = new SceneView();
		Actor inspectorView = new InspectorView();

		root = new Table();
		root.setFillParent(true);
		stage.setRoot(root);
		root.add(sceneView).expand().fill();
		root.add(inspectorView).width(192).expandY().fillY();
		root.row();
		root.add(new Widget()).colspan(2).height(100).expandX().fillX();
		root.debug();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		root.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

}
