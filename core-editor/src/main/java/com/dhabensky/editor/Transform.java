package com.dhabensky.editor;

import com.badlogic.gdx.math.Vector2;

/**
 * Position and rotation.
 *
 * Created on 17.08.2018.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Transform {

	private Vector2 position = new Vector2();
	private float rotation;
	private Vector2 scale = new Vector2(1f, 1f);

	public Transform() {

	}

	public Transform(Vector2 position) {
		if (position != null) {
			this.position.set(position);
		}
	}

	public Transform(Vector2 position, float rotation) {
		if (position != null) {
			this.position.set(position);
		}
		this.rotation = rotation;
	}

	public Transform(Vector2 position, float rotation, Vector2 scale) {
		if (position != null) {
			this.position.set(position);
		}
		this.rotation = rotation;
		if (scale != null) {
			this.scale.set(scale);
		}
	}


	public void setPosition(Vector2 position) {
		this.position.set(position);
	}

	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setX(float x) {
		position.x = x;
	}

	public float getX() {
		return position.x;
	}

	public void setY(float y) {
		position.y = y;
	}

	public float getY() {
		return position.y;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRotation() {
		return rotation;
	}

	public void setScale(Vector2 scale) {
		this.scale.set(scale);
	}

	public void setScale(float scaleX, float scaleY) {
		scale.set(scaleX, scaleY);
	}

	public void setScale(float scale) {
		this.scale.set(scale, scale);
	}

	public Vector2 getScale() {
		return this.scale;
	}

	public void setScaleX(float scaleX) {
		scale.x = scaleX;
	}

	public float getScaleX() {
		return scale.x;
	}

	public void setScaleY(float scaleY) {
		scale.y = scaleY;
	}

	public float getScaleY() {
		return scale.y;
	}


	public void set(Transform transform) {
		this.position.set(transform.position);
		this.rotation = transform.rotation;
		this.scale.set(transform.scale);
	}

	public void set(Vector2 position, float rotation, Vector2 scale) {
		this.position.set(position);
		this.rotation = rotation;
		this.scale.set(scale);
	}

	public void set(float x, float y, float rotation, float scaleX, float scaleY) {
		this.position.set(x, y);
		this.rotation = rotation;
		this.scale.set(scaleX, scaleY);
	}

}
