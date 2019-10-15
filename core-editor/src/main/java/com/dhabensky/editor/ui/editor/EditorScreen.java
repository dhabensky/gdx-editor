package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dhabensky.editor.EditorModel;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Scene;
import com.dhabensky.editor.Transform;
import com.dhabensky.editor.ui.BaseScreen;

import java.util.UUID;

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class EditorScreen extends BaseScreen {

	private Table root;
	private SceneView sceneView;
	private InspectorView inspectorView;

	private EditorModel model;

	@Override
	public void show() {
		super.show();

		createViews();
		createModel();

		sceneView.setScene(model.getScene());
		model.selectedEntity.observe(inspectorView);

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

	private void createViews() {
		Skin skin = EditorContext.skin;

		sceneView     = new SceneView(skin);
		inspectorView = new InspectorView();

		root = new Table();
		stage.setRoot(root);

		root.add(sceneView).expand().fill();
		root.add(new ScrollPane(inspectorView, skin)).width(192).expandY().fillY();
		root.row();
		root.add(new ScrollPane(new Label("Here will be assets", skin), skin)).colspan(2).height(100).expandX().fillX();
	}

	private void createModel() {
		Entity e = new Entity(UUID.randomUUID());
		Transform t = new Transform();
		t.setPosition(20, 50);
		e.setTransform(t);

		Scene scene = new Scene();
		scene.add(e, null);

		model = new EditorModel();
		model.setScene(scene);
		model.selectedEntity.setValue(e);
	}

}
