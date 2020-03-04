package com.mlottmann.vstepper;

import java.util.function.Consumer;

/**
 * The stepper registers itself as a validation listener with all steps implementing this interface.
 * Implemented by steps that need validation before moving on to the next step.
 */
public interface ValidationHandler {
	void addValidationListener(Consumer<Boolean> validationListener);

	boolean isValid();
}
