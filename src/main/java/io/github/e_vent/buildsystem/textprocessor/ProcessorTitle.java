package io.github.e_vent.buildsystem.textprocessor;

import io.github.e_vent.buildsystem.util.TitleDB;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ProcessorTitle implements ITextProcessor {
	private static final String TARGET = "$$$BUILDSCRIPT$TITLE$$$";
	private static final Pattern REGEX = Pattern.compile(Pattern.quote(TARGET));

	private final TitleDB titles;

	public ProcessorTitle(final TitleDB titles) {
		Objects.requireNonNull(titles);
		this.titles = titles;
	}

	@Override
	public final String process(final String input, final String name) {
		return REGEX.matcher(input).replaceAll(titles.getTitle(name));
	}
}
