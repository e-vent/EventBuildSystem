package io.github.e_vent.buildsystem.textprocessor;

import java.util.regex.Pattern;

public final class ProcessorSub implements ITextProcessor {
	public static final ProcessorSub SINGLETON = new ProcessorSub();

	private static final String START_DIVIDER_REGEX = "\\Q<!--$$$BUILDSCRIPT-SUB-START$$$-->\\E";
	private static final String MIDDLE_DIVIDER_REGEX = "\\Q<!--$$$BUILDSCRIPT-SUB-MIDDLE$$$\\E";
	private static final String END_DIVIDER_REGEX = "\\Q$$$BUILDSCRIPT-SUB-END-->\\E";
	private static final Pattern PATTERN = Pattern.compile(
			START_DIVIDER_REGEX + ".*?" + MIDDLE_DIVIDER_REGEX + "(.+?)" + END_DIVIDER_REGEX,
			Pattern.DOTALL | Pattern.MULTILINE
	);

	@Override
	public final String process(final String input, final String name) {
		return PATTERN.matcher(input).replaceAll("$1");
	}

	private ProcessorSub() {}
}
