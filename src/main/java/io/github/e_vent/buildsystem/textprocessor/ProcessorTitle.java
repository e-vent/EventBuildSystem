package io.github.e_vent.buildsystem.textprocessor;

import io.github.e_vent.buildsystem.util.TitleDB;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ProcessorTitle implements ITextProcessor {
	private static final String REGEX = "\\Q$$$BUILDSCRIPT$TITLE$$$\\E";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	private final TitleDB titles;

	public ProcessorTitle(final TitleDB titles) {
		Objects.requireNonNull(titles);
		this.titles = titles;
	}

	@Override
	public final String process(final String input, final String name) {
		final String title = titles.getTitle(name);
		//System.err.println("New title: " + title);
		return PATTERN.matcher(input).replaceAll(title);
	}
}
