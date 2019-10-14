package com.dhabensky.editor.launcher;

import com.badlogic.gdx.Game;
import com.dhabensky.editor.ui.editor.EditorScreen;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class EditorApp extends Game {

	@Override
	public void create() {
		setScreen(new EditorScreen());
	}

}
