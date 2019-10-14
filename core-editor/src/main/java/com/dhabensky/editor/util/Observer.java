package com.dhabensky.editor.util;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public interface Observer<T> {

	void onUpdate(T value);

}
