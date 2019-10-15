package com.dhabensky.editor.util;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public interface Observable<T> {

	void observe(Observer<T> observer);

	void removeObserver(Observer<T> observer);

	void setValue(T value);

	T getValue();

}
