package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class HoverScroll implements EventListener {

	private Actor actor;

	public HoverScroll(Actor actor) {
		this.actor = actor;
	}

	@Override
	public boolean handle(Event event) {
		if (event instanceof InputEvent) {
			InputEvent e = (InputEvent) event;
			if (e.getType() == InputEvent.Type.enter) {
				event.getStage().setScrollFocus(actor);
			}
			else if (e.getType() == InputEvent.Type.exit) {

				// for some reason touchDown triggers a sequence of events:
				//   enter [touched actor] from [null]
				//   exit  [touched actor] to   [touched actor]
				//
				// we should filter this case
				if (e.getTarget() != e.getRelatedActor()) {
					event.getStage().setScrollFocus(null);
				}
			}
		}
		return false;
	}

	private String toStrActor(Actor a) {
		if (a == null) {
			return "(null)";
		}
		return a.getClass().getSimpleName() + "@" + Integer.toHexString(a.hashCode());
	}

}
