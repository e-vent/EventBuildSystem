package io.github.e_vent.buildsystem.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class TitleDB {
	private final Map<String, String> titleMap;
	private final String defaultTitle;

	private static final String TITLE_SEPARATOR = "§";
	private static final Pattern TITLE_SEPARATER = Pattern.compile(TITLE_SEPARATOR);
	private static final Pattern LINE_SPLITTER = Pattern.compile("[\\n\\r]+");

	private TitleDB(final Map<String, String> titleMap, final String defaultTitle) {
		if (defaultTitle == null) {
			throw new IllegalArgumentException("Default title missing");
		}
		if (titleMap.containsKey("DEFAULT")) {
			throw new IllegalArgumentException();
		}
		this.titleMap = titleMap;
		this.defaultTitle = defaultTitle;
	}

	public final String getTitle(final String name) {
		final String resultMaybe = titleMap.get(name);
		return resultMaybe != null ? resultMaybe : this.defaultTitle;
	}

	public static final TitleDB loadFromString(final String input) {
		Map<String, String> tmpTitleMap = new HashMap<>();
		String defaultValue = null;
		final String[] split = LINE_SPLITTER.split(input);
		for (final String line : split) {
			if (line.isEmpty()) continue;
			final String[] keyValue = TITLE_SEPARATER.split(line);
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
