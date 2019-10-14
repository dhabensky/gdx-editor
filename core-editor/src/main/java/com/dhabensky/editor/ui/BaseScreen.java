package com.dhabensky.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class BaseScreen implements Screen {

	protected OrthographicCamera camera;
	protected Stage stage;

	protected boolean wasCreated() {
		return stage != null;
	}

	protected void create() {
		camera = new OrthographicCamera();
		stage = new Stage();
		stage.getViewport().setCamera(camera);
	}

	@Override
	public void show() {
		if (!wasCreated()) {
			create();
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
