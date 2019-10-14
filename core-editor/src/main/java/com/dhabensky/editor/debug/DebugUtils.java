package com.dhabensky.editor.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class DebugUtils {

	public static void dumpHierarchy(Actor actor) {
		StringBuilder buffer = new StringBuilder(128);
		buffer.append('\n');
		if (actor instanceof Group) {
			groupToString((Group) actor, buffer, 0);
		}
		else {
			actorToString(actor, buffer);
		}

		Gdx.app.log("dump_hierarchy", buffer.toString());
	}

	public static void dumpStageHierarchy(Actor actor) {
		if (actor.getStage() == null) {
			Gdx.app.log("dump_hierarchy", "actor not attached to stage: " + actor);
		}
		dumpHierarchy(actor.getStage().getRoot());
	}

	public static void logBounds(Actor a, String tag) {
		Gdx.app.log(tag, a.getX() + "," + a.getY() + " " + a.getWidth() + "x" + a.getHeight());
	}


	private static void groupToString (Group g, StringBuilder buffer, int indent) {
		actorToString(g, buffer);
		buffer.append('\n');

		Actor[] actors = g.getChildren().begin();
		for (int i = 0, n = g.getChildren().size; i < n; i++) {
			for (int ii = 0; ii < indent; ii++)
				buffer.append("|  ");
			Actor actor = actors[i];
			if (actor instanceof Group)
				groupToString((Group)actor, buffer, indent + 1);
			else {
				actorToString(actor, buffer);
				buffer.append('\n');
			}
		}
		g.getChildren().end();
	}

	private static void actorToString(Actor a, StringBuilder buffer) {
		String name = a.getName();
		if (name == null) {
			name = a.getClass().getName();
			int dotIndex = name.lastIndexOf('.');
			if (dotIndex != -1) name = name.substring(dotIndex + 1);
		}
//		buffer.append(name).append(" ")
//				.append((int)(a.getX())).append(",").append((int)(a.getY())).append(" - ")
//				.append((int)(a.getX() + a.getWidth())).append(",").append((int)(a.getY() + a.getHeight()));

		String s = String.format("%.0f,%.0f - %.0f,%.0f", a.getX(), a.getY(), a.getX() + a.getWidth(), a.getY() + a.getHeight());
		buffer.append(String.format("%-15s ", name)).append(s);
	}

}
