package io.github.e_vent.buildsystem.textprocessor;

import java.util.regex.Pattern;

public class ProcessorMyname implements ITextProcessor {
	public static final ProcessorMyname SINGLETON = new ProcessorMyname();

	private static final String TARGET = "$$$BUILDSCRIPT$MY_NAME$$$";
	private static final Pattern REGEX = Pattern.compile(Pattern.quote(TARGET));

	@Override
	public String process(final String input, final String name) {
		return REGEX.matcher(input).replaceAll(name);
	}

	private ProcessorMyname() {}
}
