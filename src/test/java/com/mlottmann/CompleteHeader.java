package com.mlottmann;

import com.mlottmann.vstepper.AbortStepHandler;
import com.mlottmann.vstepper.CompleteStepHandler;
import com.mlottmann.vstepper.EnterStepHandler;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

public class CompleteHeader extends VerticalLayout implements AbortStepHandler, CompleteStepHandler, EnterStepHandler {

	private Label state;
	@Getter
	private Label stepData;

	public CompleteHeader(String name) {
		Label caption = new Label(name);
		state = new Label("Inactive");
		stepData = new Label("-");
		add(caption, state, stepData);
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
