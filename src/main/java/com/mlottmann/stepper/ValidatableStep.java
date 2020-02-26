package com.mlottmann.stepper;

import java.util.function.Consumer;

public interface ValidatableStep {
	void addValidationListener(Consumer<Boolean> validationListener);

	boolean isValid();
}
