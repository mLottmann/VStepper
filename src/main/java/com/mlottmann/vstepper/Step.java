package com.mlottmann.vstepper;

import com.vaadin.flow.component.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * Container class containing the header and content component of a step.
 */
@Getter
@Setter
class Step implements EnterStepHandler, AbortStepHandler, CompleteStepHandler, ValdatableStep {

	protected Component header;
	protected Component content;

	public Step(Component header, Component content) {
		this.header = header;
		this.content = content;
	}

	public void enter() {
		if (content instanceof EnterStepHandler) {
			((EnterStepHandler) content).enter();
		}
		if (header instanceof EnterStepHandler) {
			((EnterStepHandler) header).enter();
		}
	}

	public void abort() {
		if (content instanceof AbortStepHandler) {
			((AbortStepHandler) content).abort();
		}
		if (header instanceof AbortStepHandler) {
			((AbortStepHandler) header).abort();
		}
	}

	public void complete() {
		if (content instanceof CompleteStepHandler) {
			((CompleteStepHandler) content).complete();
		}
		if (header instanceof CompleteStepHandler) {
			((CompleteStepHandler) header).complete();
		}
	}

	public boolean isValid() {
		if (content instanceof ValdatableStep) {
			return ((ValdatableStep) content).isValid();
		}
		return true;
	}

	public void addValidationListener(Consumer<Boolean> validationListener) {
		if (content instanceof ValdatableStep) {
			((ValdatableStep) content).addValidationListener(validationListener);
		}
	}

}
