package com.mlottmann.stepper;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class StepHeader extends Div implements EnterStepObserver, AbortStepObserver, CompleteStepObserver {

	public StepHeader(int number, String title) {
		this(number);
		Label caption = new Label(title);
		caption.addClassName("step-title");
		add(new Label(title));
	}

	public StepHeader(int number) {
		addClassName("step-header");
		Div numberWrapper = new Div();
		Span stepNumber = new Span();
		stepNumber.setText(String.valueOf(number));
		stepNumber.addClassName("step-number");
		numberWrapper.add(stepNumber);
		numberWrapper.addClassName("number-wrapper");
		add(numberWrapper);
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
