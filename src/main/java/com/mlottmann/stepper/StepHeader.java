package com.mlottmann.stepper;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepHeader extends Div implements EnterStepObserver, AbortStepObserver, CompleteStepObserver {

	public StepHeader(int number, String title) {
		Span stepNumber = new Span(String.valueOf(number));
		stepNumber.addClassName("step-number");
		Div numberWrapper = new Div(stepNumber);
		numberWrapper.addClassName("number-wrapper");
		Label caption = new Label(title);
		caption.addClassName("step-title");
		addClassName("step-header");
		add(numberWrapper, caption);
	}

	public StepHeader(int number) {
		this(number, "");
	}

	@Override
	public void abort() {
		removeClassName("active");
	}

	@Override
	public void complete() {
		removeClassName("active");
		addClassName("completed");
	}

	@Override
	public void enter() {
		removeClassName("completed");
		addClassName("active");
	}
}
