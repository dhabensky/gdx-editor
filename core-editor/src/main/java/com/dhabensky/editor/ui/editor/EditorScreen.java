package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dhabensky.editor.EditorModel;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Scene;
import com.dhabensky.editor.TextureComponent;
import com.dhabensky.editor.ui.BaseScreen;
import com.dhabensky.editor.ui.tools.PanTool;
import com.dhabensky.editor.ui.tools.SelectTool;
import com.dhabensky.editor.ui.tools.ZoomTool;

import java.util.ArrayList;
import java.util.List;
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

		sceneView.setSceneModel(model);
		model.selectedEntity.observe(inspectorView);

//		InputMultiplexer input = new InputMultiplexer();
//		input.addProcessor(new DebugInputProcessor(stage));
//		input.addProcessor(stage);
//		Gdx.input.setInputProcessor(input);

		Gdx.input.setInputProcessor(stage);


		ZoomTool zoomTool = new ZoomTool();
		zoomTool.setSceneView(sceneView);

		PanTool panTool = new PanTool();
		panTool.setSceneView(sceneView);

		SelectTool selectTool = new SelectTool();
		selectTool.setSceneView(sceneView);

		sceneView.addListener(zoomTool);
		sceneView.addListener(panTool);
		sceneView.addListener(selectTool);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		root.setSize(stage.getWidth(), stage.getHeight());
	}

	private void createViews() {
		Skin skin = EditorContext.skin;

		sceneView     = new SceneView(skin);
		sceneView.addListener(new HoverScroll(sceneView));

		inspectorView = new InspectorView();

		root = new Table();
		stage.setRoot(root);

		root.add(sceneView).expand().fill();
		root.add(wrapInScroll(inspectorView, skin)).width(192).expandY().fillY();
		root.row();
		root.add(wrapInScroll(new Label("Here will be assets", skin), skin)).colspan(2).height(100).expandX().fillX();
	}

	private ScrollPane wrapInScroll(Actor actor, Skin skin) {
		ScrollPane scroll = new ScrollPane(actor, skin);
		scroll.addListener(new HoverScroll(scroll));
		return scroll;
	}

	private void createModel() {

		float[] positions = new float[] {
				0,0,
				1,2,
				2,1,
				4,0,
				1,-2,
				-1,-2,
				-2,1
		};
		TextureRegion region = new TextureRegion(new Texture("badlogic.jpg"));

		List<Entity> entities = new ArrayList<>();
		for (int i = 0; i < positions.length; ) {
			Entity e = new Entity(UUID.randomUUID());
			e.getTransform().setPosition(positions[i++], positions[i++]);

			TextureComponent texture = new TextureComponent();
			texture.setRegion(region);
			e.addComponent(texture);

			entities.add(e);
		}

		Scene scene = new Scene();
		for (Entity e : entities) {
			scene.add(e, null);
		}

		model = new EditorModel();
		model.setScene(scene);
		model.selectedEntity.setValue(entities.get(0));
	}

}
