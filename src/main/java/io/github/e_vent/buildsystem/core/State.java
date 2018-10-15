package io.github.e_vent.buildsystem.core;

import io.github.e_vent.buildsystem.textprocessor.ITextProcessor;
import io.github.e_vent.buildsystem.textprocessor.ProcessorLink;
import io.github.e_vent.buildsystem.textprocessor.ProcessorMyname;
import io.github.e_vent.buildsystem.textprocessor.ProcessorSub;
import io.github.e_vent.buildsystem.textprocessor.ProcessorTitle;
import io.github.e_vent.buildsystem.util.TitleDB;

import java.util.LinkedList;
import java.util.List;

public final class State {
	private static final TextProcessorSystem TEMPLATE_PROCESSOR;

	static {
		{
			List<ITextProcessor> processors = new LinkedList<>();
			processors.add(ProcessorSub.SINGLETON);
			TEMPLATE_PROCESSOR = new TextProcessorSystem(processors);
		}
	}

	private final String header;
	private final String footer;
	private final TextProcessorSystem docProcessor;

	private State(final String header, final String footer, final TextProcessorSystem docProcessor) {
		this.header = header;
		this.footer = footer;
		this.docProcessor = docProcessor;
	}

	public static final State createState(final String templateInput, final String titlesInput) {
		final TitleDB titles = TitleDB.loadFromString(titlesInput);
		final String header, footer;
		{
			final TextDoc templateRaw = TextDoc.loadFromString(templateInput, "<template>");
			final TextDoc templateProcessed = TEMPLATE_PROCESSOR.processDoc(templateRaw);
			header = templateProcessed.head;
			footer = templateProcessed.foot;
		}
		final TextProcessorSystem docProcessor;
		{
			List<ITextProcessor> processors = new LinkedList<>();
			addDefaultProcessors(processors, titles);
			docProcessor = new TextProcessorSystem(processors);
		}
		return new State(header, footer, docProcessor);
	}

	private static final void addDefaultProcessors(final List<ITextProcessor> buffer, final TitleDB titles) {
		buffer.add(new ProcessorTitle(titles));
		buffer.add(ProcessorLink.SINGLETON);
		buffer.add(ProcessorMyname.SINGLETON);
	}

	private final TextDoc retemplateDoc(final TextDoc input) {
		return new TextDoc(this.header, input.main, this.footer, input.name);
	}

	public final TextDoc buildDoc(final TextDoc input) {
		return this.docProcessor.processDoc(retemplateDoc(input));
	}
}
