package io.github.e_vent.buildsystem.util;

import io.github.e_vent.buildsystem.Constants;

import java.util.regex.Matcher;

public final class TextDoc {
	private final String head;
	private final String main;
	private final String foot;
	private final String name;

	public TextDoc(final String head, final String main, final String foot, final String name) {
		this.head = head;
		this.main = main;
		this.foot = foot;
		this.name = name;
	}

	public final String getHead() {
		return this.head;
	}

	public final String getMain() {
		return this.main;
	}

	public final String getFoot() {
		return this.foot;
	}

	public final String getName() {
		return name;
	}

	public static final TextDoc loadFromString(final String input, final String name) {
		final Matcher matcher = Constants.REGEX.matcher(input);
		if (!matcher.matches()) {
			throw new IllegalStateException("Doc is missing buildscript separators");
		}
		final String head = matcher.group(0);
		final String main = matcher.group(1);
		final String foot = matcher.group(2);
		return new TextDoc(head, main, foot, name);
	}

	public final String saveToString() {
		return this.head + this.main + this.foot;
	}

	@Override
	public final String toString() {
		return "TextDoc { name: \"" + this.name +
				"\" head: \"" + this.head +
				"\", main: \"" + this.main +
				"\", foot: \"" + this.foot + "\" }";
	}
}
