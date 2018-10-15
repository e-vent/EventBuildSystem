package io.github.e_vent.buildsystem.textprocessor;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProcessorLink implements ITextProcessor {
	public static final ProcessorLink SINGLETON = new ProcessorLink();

	private static final String REGEX_PRE = "\\Q$$$BUILDSCRIPT$LINK$\\E";
	private static final String REGEX_MID = "([a-zA-Z0-9/]+)";
	private static final String REGEX_POST = "\\Q.html$$$\\E";
	private static final Pattern PATTERN = Pattern.compile(
			REGEX_PRE + REGEX_MID + REGEX_POST
	);

	@Override
	public final String process(final String input, final String name) {
		// workaround for lack of Matcher.replaceAll(Function<MatchResult, String>) before Java 9
		final Matcher matcher = PATTERN.matcher(input);
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
		return name.equals(linkTarget) ? "#" : linkTarget + ".html#";
	}

	private ProcessorLink() {}
}
