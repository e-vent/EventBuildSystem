package io.github.e_vent.buildsystem.util;

import io.github.e_vent.buildsystem.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TitleDB {
	private final Map<String, String> titleMap;
	private final String defaultTitle;

	private TitleDB(final Map<String, String> titleMap, final String defaultTitle) {
		Objects.requireNonNull(titleMap);
		Objects.requireNonNull(defaultTitle);
		final Map<String, String> tmpTitleMap = Collections.unmodifiableMap(new HashMap<>(titleMap));
		if (tmpTitleMap.containsKey("DEFAULT")) {
			throw new IllegalArgumentException();
		}
		tmpTitleMap.keySet().forEach(Objects::requireNonNull);
		tmpTitleMap.values().forEach(Objects::requireNonNull);
		this.titleMap = tmpTitleMap;
		this.defaultTitle = defaultTitle;
	}

	public final String getDefaultTitle() {
		return defaultTitle;
	}

	public final String getTitle(final String name) {
		final String resultMaybe = titleMap.get(name);
		return resultMaybe != null ? resultMaybe : this.defaultTitle;
	}

	public static final TitleDB loadFromString(final String input) {
		Map<String, String> tmpTitleMap = new HashMap<>();
		String defaultValue = null;
		final String[] split = Constants.LINE_SPLITTER.split(input);
		for (final String line : split) {
			if (line.isEmpty()) continue;
			final String[] keyValue = Constants.TITLE_SEPARATER.split(line);
			if (keyValue.length != 2) {
				throw new IllegalArgumentException("Expected § to appear once, separating key and value. Got: " + line);
			}
			final String key = keyValue[0];
			final String value = keyValue[1];
			if ("DEFAULT".equals(key)) {
				defaultValue = value;
			} else {
				tmpTitleMap.put(key, value);
			}
		}
		return new TitleDB(tmpTitleMap, defaultValue);
	}
}
