package com.mlottmann;

import com.mlottmann.vstepper.StepContent;
import com.mlottmann.vstepper.stepEvent.StepEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.function.Consumer;

public class CompleteContent extends StepContent {

	private Consumer<String> dataDisplay;
	private TextField input;

	public CompleteContent(Consumer<String> dataDisplay) {
		this.dataDisplay = dataDisplay;
		input = new TextField("Input");
		input.addValueChangeListener(valueChanged -> stepChanged());
		input.setValueChangeMode(ValueChangeMode.EAGER);
		add(input);
	}

	@Override
	public void enter(StepEvent event) {
		input.setValue("");
	}

	@Override
	public void abort(StepEvent event) {
		dataDisplay.accept("-");
	}

	@Override
	public void complete(StepEvent event) {
		dataDisplay.accept(input.getValue());
	}

	@Override
	public boolean isValid() {
		return !input.getValue().isEmpty();
	}
}
