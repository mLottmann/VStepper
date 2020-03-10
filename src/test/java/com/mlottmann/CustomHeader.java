package com.mlottmann;

import com.mlottmann.vstepper.stepEvent.*;
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