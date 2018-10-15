package io.github.e_vent.buildsystem.core;

import io.github.e_vent.buildsystem.textprocessor.ITextProcessor;

import java.util.ArrayList;
import java.util.List;

/*packaged*/ final class TextProcessorSystem {
	private final List<ITextProcessor> processors;

	/*packaged*/ TextProcessorSystem(final List<ITextProcessor> processors) {
		this.processors = new ArrayList<>(processors);
	}

	private final String processText(String text, final String name) {
		for (final ITextProcessor processor : processors) {
			text = processor.process(text, name);
		}
		return text;
	}

	/*packaged*/ final TextDoc processDoc(final TextDoc doc) {
		final String name = doc.name;

		final String newHead = processText(doc.head, name);
		final String newMain = processText(doc.main, name);
		final String newFoot = processText(doc.foot, name);

		return new TextDoc(newHead, newMain, newFoot, name);
	}
}
