package com.mlottmann;

import com.mlottmann.vstepper.StepContent;
import com.mlottmann.vstepper.stepEvent.AbortEvent;
import com.mlottmann.vstepper.stepEvent.CompleteEvent;
import com.mlottmann.vstepper.stepEvent.EnterEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class FullContent extends StepContent {

	private TextField input;

	public FullContent() {
		this.getElement().getStyle().set("display", "flex");
		this.getElement().getStyle().set("flex-direction", "column");
		this.input = new TextField("Input");
		this.input.addValueChangeListener(valueChanged -> stepChanged());
		this.input.setValueChangeMode(ValueChangeMode.EAGER);
		add(this.input);
	}

	@Override
	public boolean isValid() {
		return input.getValue() != null && !input.getValue().isEmpty();
	}

	@Override
	public void onAbort(AbortEvent event) {
		add(new Label("Discard: " + input.getValue()));
	}

	@Override
	public void onComplete(CompleteEvent event) {
		add(new Label("Save: " + input.getValue()));
	}

	@Override
	public void onEnter(EnterEvent event) {
		input.setValue("");
	}
}
