package com.dhabensky.editor.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class DebugInputProcessor extends InputAdapter {

	private Stage stage;
	public DebugInputProcessor(Stage stage) {
		this.stage = stage;
	}

	@Override
	public boolean keyTyped(char character) {
		if (character == 'd') {
			DebugUtils.dumpStageHierarchy(stage.getRoot());
			return true;
		}
		return super.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("touch_down", "screenX: " + screenX + ", screenY: " + screenY);
		return super.touchDown(screenX, screenY, pointer, button);
	}

}
