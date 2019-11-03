package com.dhabensky.editor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class TextureComponent implements Component {

	private TextureRegion region;
	private final Vector2 size = new Vector2(1f, 1f);
	private final Vector2 anchor = new Vector2(0.5f, 0.5f);


	public TextureRegion getRegion() {
		return region;
	}

	public void setRegion(TextureRegion region) {
		this.region = region;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size.set(size);
	}

	public float getWidth() {
		return size.x;
	}

	public void setWidth(float w) {
		size.x = w;
	}

	public float getHeight() {
		return size.y;
	}

	public void setHeight(float h) {
		size.y = h;
	}

	public Vector2 getAnchor() {
		return anchor;
	}

	public void setAnchor(Vector2 anchor) {
		this.anchor.set(anchor);
	}

	public float getAnchorX() {
		return anchor.x;
	}

	public void setAnchorX(float anchorX) {
		anchor.x = anchorX;
	}

	public float getAnchorY() {
		return anchor.y;
	}

	public void setAnchorY(float anchorY) {
		anchor.y = anchorY;
	}

	public float getMinX() {
		return size.x * (0 - anchor.x);
	}

	public float getMaxX() {
		return size.x * (1 - anchor.x);
	}

	public float getMinY() {
		return size.y * (0 - anchor.y);
	}

	public float getMaxY() {
		return size.y * (1 - anchor.y);
	}

}
