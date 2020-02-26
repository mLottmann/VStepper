package com.mlottmann;

import com.mlottmann.stepper.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route("")
public class View extends Div {

	public View() {
		Stepper stepper = new Stepper();
		stepper.setWidthFull();
		stepper.setHeight("400px");
		stepper.addStep(new TextField("Content 1"));
		stepper.addStep("Steppable Test", new SteppableTest());
		stepper.addStep("Step 3", new Label("Content 3"));
		stepper.addStep("Step content Test", new StepContentTest());
		stepper.addStep("Step 5", new ComboBox<>("Content 5"));
		add(stepper);
	}

	private Div createStepOneContent() {
		Div content = new Div();
		content.addClassName("step-header");
		content.add(new TextField("Field1"));
		content.add(new TextField("Field2"));
		content.add(new TextField("Field3"));
		return content;
	}

	private class SteppableTest extends VerticalLayout implements EnterStepObserver, AbortStepObserver, CompleteStepObserver, ValidatableStep {

		private final List<Consumer<Boolean>> validationListeners;
		private boolean validState;
		private TextField input;

		public SteppableTest() {
			this.validationListeners = new ArrayList<>();
			input = new TextField("Content 3");
			input.addValueChangeListener(valueChanged -> stepChanged());
			input.setValueChangeMode(ValueChangeMode.EAGER);
			add(input, new TextField("Content 3"), new TextField("Content 3"), new TextField("Content 3"), new TextField("Content 3"), new TextField("Content 3"));
		}

		@Override
		public void enter() {

		}

		@Override
		public void abort() {
			input.setValue("");
		}

		@Override
		public void complete() {

		}

		@Override
		public boolean isValid() {
			return !input.getValue().isEmpty();
		}

		@Override
		public void addValidationListener(Consumer<Boolean> validationListener) {
			this.validationListeners.add(validationListener);
		}

		private void updateValidationListeners() {
			validationListeners.forEach(listener -> listener.accept(validState));
		}

		private void stepChanged() {
			if (validState != isValid()) {
				validState = isValid();
				updateValidationListeners();
			}
		}
	}

	private class StepContentTest extends StepContent {

		private TextField input;

		public StepContentTest() {
			input = new TextField("Content 4");
			input.addValueChangeListener(valueChanged -> stepChanged());
			input.setValueChangeMode(ValueChangeMode.EAGER);
			add(input);
		}

		@Override
		public void enter() {
			input.setValue("Welcome to content 4");
		}

		@Override
		public void abort() {

		}

		@Override
		public void complete() {

		}

		@Override
		public boolean isValid() {
			return !input.getValue().isEmpty();
		}
	}
}
