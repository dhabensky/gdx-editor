package com.dhabensky.editor.util;

import com.badlogic.gdx.utils.SnapshotArray;

/**
 * @author dhabensky <d.khabensky@a1-systems.com>
 */
public class BaseObservable<T> implements Observable<T> {

	private static Object NO_VALUE = new Object();

	private SnapshotArray<Observer<T>> observers = new SnapshotArray<>();
	private Object                     value     = NO_VALUE;

	@Override
	public void observe(Observer<T> observer) {
		observers.add(observer);
		if (value != NO_VALUE) {
			observer.onUpdate((T) value);
		}
	}

	@Override
	public void removeObserver(Observer<T> observer) {
		observers.removeValue(observer, true);
	}

	@Override
	public void setValue(T value) {
		this.value = value;
		Object[] obs = observers.begin();
		try {
			for (int i = 0, n = observers.size; i < n; ++i) {
				Observer<T> observer = (Observer<T>) obs[i];
				observer.onUpdate(value);
			}
		}
		finally {
			observers.end();
		}
	}

}
