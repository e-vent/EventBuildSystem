package io.github.e_vent.buildsystem;

import io.github.e_vent.buildsystem.textprocessor.ITextProcessor;
import io.github.e_vent.buildsystem.textprocessor.ProcessorSub;
import io.github.e_vent.buildsystem.textprocessor.TextProcessorSystem;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public final class Constants {
	public static final String DIVIDER_HEAD_MAIN = "<!--buildscriptdivider$header-content-->";
	public static final String DIVIDER_MAIN_FOOT = "<!--buildscriptdivider$content-footer-->";

	public static final Pattern REGEX = Pattern.compile(
			"(.+)" + Pattern.quote(DIVIDER_HEAD_MAIN) + "(.+)" + Pattern.quote(DIVIDER_MAIN_FOOT) + "(.+)"
	);

	public static final String TITLE_SEPARATOR = "§";
	public static final Pattern TITLE_SEPARATER = Pattern.compile(TITLE_SEPARATOR);
	public static final Pattern LINE_SPLITTER = Pattern.compile("[\\n\\r]+");

	public static final TextProcessorSystem TEMPLATE_PROCESSOR;

	static {
		{
			List<ITextProcessor> processors = new LinkedList<>();
			processors.add(ProcessorSub.SINGLETON);
			TEMPLATE_PROCESSOR = new TextProcessorSystem(processors);
		}
	}

	private Constants() {}
}
