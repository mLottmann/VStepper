package com.mlottmann;

import com.mlottmann.vstepper.Step;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class FullStep extends Step {

	private Label state;
	private Label inputValue;
	private TextField input;
	private boolean isValid;

	public FullStep(String title) {
		header = createHeader(title);
		content = createContent();
		isValid = isValid();
	}

	private VerticalLayout createHeader(String title) {
		Label headerTitle = new Label(title);
		state = new Label("Inactive");
		inputValue = new Label("-");
		return new VerticalLayout(headerTitle, state, inputValue);
	}

	private VerticalLayout createContent() {
		input = new TextField("Input");
		input.addValueChangeListener(valueChanged -> inputChanged());
		input.setValueChangeMode(ValueChangeMode.EAGER);
		return new VerticalLayout(input);
	}

	private void inputChanged() {
		if (isValid != isValid()) {
			isValid = isValid();
			updateValidationListeners(isValid);
		}
	}

	@Override
	public void enter() {
		super.enter();
		state.setText("Active");
		input.setValue("");
		inputValue.setText("-");
	}

	@Override
	public void abort() {
		super.abort();
		state.setText("Inactive");
	}

	@Override
	public void complete() {
		super.complete();
		state.setText("Completed");
		inputValue.setText(input.getValue());
	}

	@Override
	public boolean isValid() {
		return input.getValue() != null && !input.getValue().isEmpty();
	}
}
