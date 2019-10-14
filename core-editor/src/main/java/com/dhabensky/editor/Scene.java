package com.dhabensky.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Holds {@link Entity}s and provides access to them via tags.
 *
 * Created on 02.08.2018.
 * @author dhabensky <dhabensky@yandex.ru>
 */
public class Scene {

	private Map<String, List<Entity>> tagMap = new HashMap<>();
	private List<Entity> objects = new LinkedList<>();


	public @Nullable List<Entity> get(String tag) {
		return tagMap.get(tag);
	}

	public @Nullable Entity getFirst(String tag) {
		List<Entity> list = get(tag);
		if (list == null || list.isEmpty())
			return null;
		return list.get(0);
	}

	public void add(@Nonnull Entity entity, @Nullable String tag) {
		objects.add(entity);
		if (tag != null) {
			List<Entity> objectsForTag = tagMap.get(tag);
			if (objectsForTag == null) {
				objectsForTag = new ArrayList<>();
				tagMap.put(tag, objectsForTag);
			}
			objectsForTag.add(entity);
		}
	}

	public List<Entity> getObjects() {
		return objects;
	}

}
