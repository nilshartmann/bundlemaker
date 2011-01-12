package org.bundlemaker.core.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StringCache {

	//
	private Map<Integer, String> _indexToString;
	private Map<String, Integer> _stringToIndex;
	private int _currentIndex;

	/**
	 * <p>
	 * Creates a new instance of type {@link StringCache}.
	 * </p>
	 */
	public StringCache() {

		//
		_indexToString = new HashMap<Integer, String>();
		_stringToIndex = new HashMap<String, Integer>();
		_currentIndex = 0;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param string
	 * @return
	 */
	public synchronized int storeString(String string) {

		//
		if (_stringToIndex.containsKey(string)) {
			return _stringToIndex.get(string);
		}

		_currentIndex++;

		_stringToIndex.put(string, _currentIndex);
		_indexToString.put(_currentIndex, string);

		return _currentIndex;
	}

	public synchronized String getString(int index) {
		return _indexToString.get(index);
	}
}
