package com.dhabensky.editor.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class BackgroundComponent {

	private static Pixmap sPixmap;
	static {
		sPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		sPixmap.setColor(Color.WHITE);
		sPixmap.fill();
	}

	private Actor owner;
	private Color color = Color.WHITE;
	private Color batchColorBackup = new Color();

	private Texture texture = new Texture(sPixmap);

	public BackgroundComponent(Actor owner) {
		this.owner = owner;
	}

	public BackgroundComponent(Actor owner, Color color) {
		this.owner = owner;
		setColor(color);
	}

	public void draw(Batch batch, float parentAlpha) {
		if (owner == null || color == null) {
			return;
		}
		batchColorBackup.set(batch.getColor());
		batch.setColor(color);
		batch.draw(texture, owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight());
		batch.setColor(batchColorBackup);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
