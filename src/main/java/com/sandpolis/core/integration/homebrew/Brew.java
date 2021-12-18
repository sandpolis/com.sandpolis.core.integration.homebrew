package com.sandpolis.core.integration.homebrew;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.sandpolis.core.foundation.S7SFile;
import com.sandpolis.core.foundation.S7SProcess;
import com.sandpolis.core.foundation.S7SSystem;

public record Brew(Path executable) {

	public static boolean isAvailable() {
		switch (S7SSystem.OS_TYPE) {
		case MACOS:
			return S7SFile.which("brew").isPresent();
		default:
			return false;
		}
	}

	public static Brew load() {
		if (!isAvailable()) {
			throw new IllegalStateException();
		}

		return new Brew(S7SFile.which("brew").get().path());
	}

	public S7SProcess install(String... packages) {
		return S7SProcess.exec("brew", "install", Arrays.stream(packages).collect(Collectors.joining(" ")));
	}

}
