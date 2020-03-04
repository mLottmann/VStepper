package com.mlottmann;

import com.mlottmann.vstepper.AbortStepHandler;
import com.mlottmann.vstepper.CompleteStepHandler;
import com.mlottmann.vstepper.EnterStepHandler;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomHeader extends VerticalLayout implements AbortStepHandler, CompleteStepHandler, EnterStepHandler {

	private Label state;

	public CustomHeader(String name) {
		Label caption = new Label(name);
		state = new Label("Inactive");
		add(caption, state);
	}

	@Override
	public void abort() {
		state.setText("Inactive");
	}

	@Override
	public void complete() {
		state.setText("Complete");
	}

	@Override
	public void enter() {
		state.setText("Active");
	}
}