package io.github.e_vent.buildsystem;

import io.github.e_vent.buildsystem.textprocessor.ITextProcessor;
import io.github.e_vent.buildsystem.textprocessor.ProcessorLink;
import io.github.e_vent.buildsystem.textprocessor.ProcessorSub;
import io.github.e_vent.buildsystem.textprocessor.ProcessorTitle;
import io.github.e_vent.buildsystem.textprocessor.TextProcessorSystem;
import io.github.e_vent.buildsystem.util.TextDoc;
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

	public final String getHeader() {
		return this.header;
	}

	public final String getFooter() {
		return this.footer;
	}

	public static final State createState(final String templateInput, final String titlesInput) {
		final TitleDB titles = TitleDB.loadFromString(titlesInput);
		final String header, footer;
		{
			final TextDoc templateRaw = TextDoc.loadFromString(templateInput, "<template>");
			final TextDoc templateProcessed = TEMPLATE_PROCESSOR.processDoc(templateRaw);
			header = templateProcessed.getHead();
			footer = templateProcessed.getFoot();
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
	}

	private final TextDoc retemplateDoc(final TextDoc input) {
		return new TextDoc(this.header, input.getMain(), this.footer, input.getName());
	}

	public final TextDoc buildDoc(final TextDoc input) {
		return this.docProcessor.processDoc(retemplateDoc(input));
	}
}
