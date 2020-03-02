package com.mlottmann.stepper;

import com.vaadin.flow.component.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class Step {

	private Component header;
	private Component content;

	public Step(Component header, Component content) {
		this.header = header;
		this.content = content;
	}

	public void enter() {
		if (content instanceof EnterStepObserver) {
			((EnterStepObserver) content).enter();
		}
		if (header instanceof EnterStepObserver) {
			((EnterStepObserver) header).enter();
		}
	}

	public void abort() {
		if (content instanceof AbortStepObserver) {
			((AbortStepObserver) content).abort();
		}
		if (header instanceof AbortStepObserver) {
			((AbortStepObserver) header).abort();
		}
	}

	public void complete() {
		if (content instanceof CompleteStepObserver) {
			((CompleteStepObserver) content).complete();
		}
		if (header instanceof CompleteStepObserver) {
			((CompleteStepObserver) header).complete();
		}
	}

	public boolean isValid() {
		if (content instanceof ValidatableStep) {
			return ((ValidatableStep) content).isValid();
		}
		return true;
	}

	public void addValidationListener(Consumer<Boolean> validationListener) {
		if (content instanceof ValidatableStep) {
			((ValidatableStep) content).addValidationListener(validationListener);
		}
	}
}
