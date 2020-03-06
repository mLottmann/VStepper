package com.mlottmann;

import com.mlottmann.vstepper.stepEvent.AbortStepListener;
import com.mlottmann.vstepper.stepEvent.CompleteStepListener;
import com.mlottmann.vstepper.stepEvent.EnterStepListener;
import com.mlottmann.vstepper.stepEvent.StepEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomHeader extends VerticalLayout implements AbortStepListener, CompleteStepListener, EnterStepListener {

	private Label state;

	public CustomHeader(String name) {
		Label caption = new Label(name);
		state = new Label("Inactive");
		add(caption, state);
	}

	@Override
	public void abort(StepEvent event) {
		state.setText("Inactive");
	}

	@Override
	public void complete(StepEvent event) {
		state.setText("Complete");
	}

	@Override
	public void enter(StepEvent event) {
		state.setText("Active");
	}
}