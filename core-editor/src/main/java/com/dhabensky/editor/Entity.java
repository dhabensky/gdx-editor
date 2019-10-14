package com.dhabensky.editor;

import java.util.UUID;

import javax.annotation.Nonnull;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class Entity {

	private UUID uuid;
	private Transform transform = new Transform();

	public Entity(UUID uuid) {
		this.uuid = uuid;
	}


	public UUID getUuid() {
		return uuid;
	}


	public @Nonnull
	Transform getTransform() {
		return transform;
	}

	public void setTransform(@Nonnull Transform transform) {
		this.transform.set(transform);
	}

}
