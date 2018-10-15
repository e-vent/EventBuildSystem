package io.github.e_vent.buildsystem.textprocessor;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessorLink implements ITextProcessor {
	public static final ProcessorLink SINGLETON = new ProcessorLink();

	private static final String TARGET_PRE = "$$$BUILDSCRIPT$LINK$";
	private static final String REGEX_MID = "([a-zA-Z0-9/]+)";
	private static final String TARGET_POST = ".html$$$";
	private static final Pattern REGEX = Pattern.compile(
			Pattern.quote(TARGET_PRE) + REGEX_MID + Pattern.quote(TARGET_POST)
	);

	@Override
	public final String process(final String input, final String name) {
		// workaround for lack of Matcher.replaceAll(Function<MatchResult, String>) before Java 9
		final Matcher matcher = REGEX.matcher(input);
		matcher.reset();
		final StringBuffer result = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(result, doReplace(matcher, name));
		}
		matcher.appendTail(result);
		return result.toString();
	}

	private final String doReplace(final MatchResult match, final String name) {
		final String linkTarget = match.group(1);
		final String s = name.equals(linkTarget) ? "#" : linkTarget + ".html#";
		//System.err.println("DEBUG: " + s);
		return s;
	}

	private ProcessorLink() {}
}
