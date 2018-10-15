package io.github.e_vent.buildsystem.textprocessor;

@FunctionalInterface
public abstract interface ITextProcessor {
	public abstract String process(final String input, final String name);
}
