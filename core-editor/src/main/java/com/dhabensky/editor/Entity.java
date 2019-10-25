package com.dhabensky.editor;

import java.util.HashMap;
import java.util.Map;
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




	// TODO consider support multiple components of same type
	private Map<Class, Component> components = new HashMap<>();


	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(Class<T> componentClass) {
		return (T) components.get(componentClass);
	}

	public <T extends Component> void addComponent(T component) {
		components.put(component.getClass(), component);
	}

	public <T extends Component> void removeComponent(T component) {
		components.remove(component.getClass());
	}

}
