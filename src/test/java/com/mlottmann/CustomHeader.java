package com.mlottmann;

import com.mlottmann.vstepper.stepEvent.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

public class CustomHeader extends Div implements AbortStepListener, CompleteStepListener, EnterStepListener {

	private Label state;

	public CustomHeader(String name) {
		Label caption = new Label(name);
		state = new Label("Inactive");
		addClassName("custom-header");
		add(caption, state);
	}

	@Override
	public void onAbort(AbortEvent event) {
		state.setText("Inactive");
	}

	@Override
	public void onComplete(CompleteEvent event) {
		state.setText("Complete");
	}

	@Override
	public void onEnter(EnterEvent event) {
		state.setText("Active");
	}
}