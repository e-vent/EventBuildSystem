package io.github.e_vent.buildsystem.textprocessor;

import io.github.e_vent.buildsystem.util.TextDoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class TextProcessorSystem {
	private final List<ITextProcessor> processors;

	public TextProcessorSystem(final List<ITextProcessor> processors) {
		Objects.requireNonNull(processors);
		final List<ITextProcessor> tmpProcessors = Collections.unmodifiableList(new ArrayList<>(processors));
		tmpProcessors.forEach(Objects::requireNonNull);
		this.processors = processors;
	}

	private final String processText(final String text, final String name) {
		String tmp = text;
		Objects.requireNonNull(tmp);
		for (final ITextProcessor processor : processors) {
			tmp = processor.process(text, name);
			Objects.requireNonNull(tmp);
		}
		return tmp;
	}

	public final TextDoc processDoc(final TextDoc doc) {
		Objects.requireNonNull(doc);
		final String name = doc.getName();

		final String newHead = processText(doc.getHead(), name);
		final String newMain = processText(doc.getMain(), name);
		final String newFoot = processText(doc.getFoot(), name);

		return new TextDoc(newHead, newMain, newFoot, name);
	}
}
