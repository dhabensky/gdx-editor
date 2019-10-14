package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class EditorContext {

	public static Skin skin;

	static {
		FileHandle file = Gdx.files.internal("skins/default/skin/uiskin.json");
		skin = new Skin(file);
	}

}
