package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

/**
 * Helper class for step headers implementing all step listeners and assigning the
 * "active" or "completed" CSS class based on the step events.
 */
public class StepHeader extends Div implements EnterStepListener, AbortStepListener, CompleteStepListener {

	public StepHeader(Component... components) {
		super(components);
	}

	@Override
	public void onAbort(AbortEvent event) {
		removeClassName("completed");
		removeClassName("active");
	}

	@Override
	public void onComplete(CompleteEvent event) {
		removeClassName("active");
		addClassName("completed");
	}

	@Override
	public void onEnter(EnterEvent event) {
		removeClassName("completed");
		addClassName("active");
	}
}
