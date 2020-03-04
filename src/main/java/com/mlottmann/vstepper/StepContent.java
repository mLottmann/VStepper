package com.mlottmann.vstepper;

import com.vaadin.flow.component.html.Div;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Abstract helper class for step contents implementing all step handlers.
 */
public abstract class StepContent extends Div implements EnterStepHandler, AbortStepHandler, CompleteStepHandler, ValidationHandler {

	private final List<Consumer<Boolean>> validationListeners;
	private boolean validState;

	public StepContent() {
		this.validationListeners = new ArrayList<>();
	}

	public abstract void enter();

	public abstract void abort();

	public abstract void complete();

	public abstract boolean isValid();

	public void addValidationListener(Consumer<Boolean> validationListener) {
		this.validationListeners.add(validationListener);
	}

	private void updateValidationListeners() {
		validationListeners.forEach(listener -> listener.accept(validState));
	}

	protected void stepChanged() {
		if (validState != isValid()) {
			validState = isValid();
			updateValidationListeners();
		}
	}

}
