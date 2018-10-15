package io.github.e_vent.buildsystem.main;

import io.github.e_vent.buildsystem.State;
import io.github.e_vent.buildsystem.util.TextDoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

public final class ScriptMain {
	public static void main(final String[] args) {
		run();
	}

	public static void run() {
		final String templateData;
		final String titlesData;
		{ // read files
			final Path templateFile = Paths.get("template.html");
			if (Files.notExists(templateFile)) {
				System.err.println("No template.html");
				return;
			}
			final Path titlesFile = Paths.get("titles.txt");
			if (Files.notExists(titlesFile)) {
				System.err.println("No titles.txt");
				return;
			}
			try {
				templateData = new String(Files.readAllBytes(templateFile), "UTF-8");
			} catch (final IOException e) {
				System.err.println("Problem reading titles.txt");
				e.printStackTrace();
				return;
			}
			try {
				titlesData = new String(Files.readAllBytes(titlesFile), "UTF-8");
			} catch (final IOException e) {
				System.err.println("Problem reading titles.txt");
				e.printStackTrace();
				return;
			}
		}
		final State state = State.createState(templateData, titlesData);
		final Path cwd = Paths.get(".");
		try {
			Files.list(cwd).filter(PATH_FILTER).forEach(p -> work(p, state));
		} catch (final IOException ie) {
			System.err.println("Failed to walk file tree");
			ie.printStackTrace();
		} catch (final RuntimeException re) {
			System.err.println("Error while working on a file");
			re.printStackTrace();
		}
	}

	private static Predicate<Path> PATH_FILTER = p -> {
		final String name = p.getFileName().toString();
		return name.endsWith(".html") && !name.contains("_") && !name.contains("template");
	};

	private static final void work(final Path p, final State state) {
		final String name;
		{
			final String fileName = p.getFileName().toString();
			name = fileName.substring(0, fileName.length() - 4);
		}
		final String inputContents;
		try {
			inputContents = new String(Files.readAllBytes(p), "UTF-8");
		} catch (final IOException e) {
			throw new RuntimeException("Failed to read input file", e);
		}
		final TextDoc inputDoc = TextDoc.loadFromString(inputContents, name);
		final TextDoc outputDoc = state.buildDoc(inputDoc);
		final String outputContents = outputDoc.saveToString();
		try {
			Files.write(p, outputContents.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Failed to write output file", e);
		}
	};

	private ScriptMain() {}
}
