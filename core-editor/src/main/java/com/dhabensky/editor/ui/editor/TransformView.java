package com.dhabensky.editor.ui.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.dhabensky.editor.Entity;
import com.dhabensky.editor.Transform;
import com.dhabensky.editor.util.Observer;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class TransformView extends Table implements Observer<Entity> {

	private final TextField posX;
	private final TextField posY;

	private Transform transform;

	public TransformView(Skin skin) {
		posX = new TextField("0.0", skin);
		posY = new TextField("0.0", skin);

		row();
		add(new Label("position", skin)).colspan(2).pad(4);
		row();
		add(new Label("x", skin));
		add(posX).width(100);
		row();
		add(new Label("y", skin));
		add(posY).width(100);

		TextField.TextFieldFilter filter = new FloatFieldFilter();
		posX.setTextFieldFilter(filter);
		posY.setTextFieldFilter(filter);

		posX.setTextFieldListener(new FloatFieldUpdater(FloatFieldUpdater.FIELD_POS_X));
		posY.setTextFieldListener(new FloatFieldUpdater(FloatFieldUpdater.FIELD_POS_Y));
	}

	@Override
	public void onUpdate(Entity entity) {
		if (entity == null) {
			Gdx.app.log("TransformView", "selected entity: null");
			transform = null;
			return;
		}

		Gdx.app.log("TransformView", "selected entity: " + entity.getUuid());
		transform = entity.getTransform();
		posX.setText(String.valueOf(transform.getX()));
		posY.setText(String.valueOf(transform.getY()));
	}

	private static class FloatFieldFilter implements TextField.TextFieldFilter {
		@Override
		public boolean acceptChar(TextField textField, char c) {
			if (Character.isDigit(c)) {
				return true;
			}
			if (c == '.') {
				return !textField.getText().contains(".");
			}
			if (c == '-') {
				return textField.getCursorPosition() == 0 && !textField.getText().contains("-");
			}
			return false;
		}
	}

	private class FloatFieldUpdater implements TextField.TextFieldListener {

		public static final int FIELD_POS_X = 0;
		public static final int FIELD_POS_Y = 1;

		public FloatFieldUpdater(int modelField) {
			this.modelField = modelField;
		}
		private int modelField;

		@Override
		public void keyTyped(TextField textField, char c) {
			try {
				float value = 0f;
				String text = textField.getText();
				if (!text.isEmpty() && !".".equals(text)) {
					value = Float.parseFloat(textField.getText());
				}
				updateModel(value);
			}
			catch (NumberFormatException e) {
				// how can it be ?!
			}
		}

		private void updateModel(float value) {
			if (transform == null) {
				return;
			}

			if (modelField == FIELD_POS_X) {
				transform.setX(value);
			}
			else if (modelField == FIELD_POS_Y) {
				transform.setY(value);
			}
		}
	}

}
