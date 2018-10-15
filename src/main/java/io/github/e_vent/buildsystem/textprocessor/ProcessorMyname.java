package io.github.e_vent.buildsystem.textprocessor;

import java.util.regex.Pattern;

public final class ProcessorMyname implements ITextProcessor {
	public static final ProcessorMyname SINGLETON = new ProcessorMyname();

	private static final String REGEX = "\\Q$$$BUILDSCRIPT$MY_NAME$$$\\E";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public String process(final String input, final String name) {
		return PATTERN.matcher(input).replaceAll(name);
	}

	private ProcessorMyname() {}
}
