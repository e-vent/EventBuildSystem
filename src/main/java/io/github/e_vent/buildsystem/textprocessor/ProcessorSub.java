package io.github.e_vent.buildsystem.textprocessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProcessorSub implements ITextProcessor {
	public static final ProcessorSub SINGLETON = new ProcessorSub();

	private static final String START_DIVIDER = "<!--$$$BUILDSCRIPT-SUB-START$$$-->";
	private static final String MIDDLE_DIVIDER = "<!--$$$BUILDSCRIPT-SUB-MIDDLE$$$";
	private static final String END_DIVIDER = "$$$BUILDSCRIPT-SUB-END-->";
	private static final Pattern REGEX = Pattern.compile(
			Pattern.quote(START_DIVIDER) + ".*" + Pattern.quote(MIDDLE_DIVIDER) + "(.+)" + Pattern.quote(END_DIVIDER),
			Pattern.DOTALL | Pattern.MULTILINE
	);

	@Override
	public final String process(final String input, final String name) {
		return REGEX.matcher(input).replaceAll("$1");
	}

	private ProcessorSub() {}
}
