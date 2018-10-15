package io.github.e_vent.buildsystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextDoc {
	private static final String DIVIDER_HEAD_MAIN = "<!--buildscriptdivider$header-content-->";
	private static final String DIVIDER_MAIN_FOOT = "<!--buildscriptdivider$content-footer-->";
	private static final Pattern REGEX = Pattern.compile(
			"(.+)" + Pattern.quote(DIVIDER_HEAD_MAIN) + "(.+)" + Pattern.quote(DIVIDER_MAIN_FOOT) + "(.+)",
			Pattern.DOTALL | Pattern.MULTILINE
	);

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
		final Matcher matcher = REGEX.matcher(input);
		if (!matcher.matches()) {
			throw new IllegalStateException("Doc is missing buildscript separators");
		}
		final String head = matcher.group(1);
		final String main = matcher.group(2);
		final String foot = matcher.group(3);
		return new TextDoc(head, main, foot, name);
	}

	public final String saveToString() {
		return this.head + DIVIDER_HEAD_MAIN + this.main + DIVIDER_MAIN_FOOT + this.foot;
	}

	@Override
	public final String toString() {
		return "TextDoc { name: \"" + this.name +
				"\" head: \"" + this.head +
				"\", main: \"" + this.main +
				"\", foot: \"" + this.foot + "\" }";
	}
}
