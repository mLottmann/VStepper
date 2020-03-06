package com.mlottmann;

import com.mlottmann.vstepper.stepEvent.AbortStepListener;
import com.mlottmann.vstepper.stepEvent.CompleteStepListener;
import com.mlottmann.vstepper.stepEvent.EnterStepListener;
import com.mlottmann.vstepper.stepEvent.StepEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

public class CompleteHeader extends VerticalLayout implements AbortStepListener, CompleteStepListener, EnterStepListener {

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
