package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;

/**
 * Default step header used whenever no header component is specified.
 */
public class StepHeader extends Div implements EnterStepListener, AbortStepListener, CompleteStepListener {

	public StepHeader() {

	}

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

	@Override
	public void abort(AbortEvent event) {
		removeClassName("active");
	}

	@Override
	public void complete(CompleteEvent event) {
		removeClassName("active");
		addClassName("completed");
	}

	@Override
	public void enter(EnterEvent event) {
		removeClassName("completed");
		addClassName("active");
	}
}
