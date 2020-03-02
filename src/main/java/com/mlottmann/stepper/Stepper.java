package com.mlottmann.stepper;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.ArrayList;
import java.util.List;

@Tag("vaadin-stepper")
@JsModule("./vaadin-stepper.js")
public class Stepper extends PolymerTemplate<TemplateModel> implements HasSize, HasStyle {

	private final List<Step> steps;
	private Step currentStep;

	@Id
	private Div header;
	@Id
	private Div content;
	@Id
	private Div footer;

	private Button cancel;
	private Button back;
	private Button next;
	private Button finish;

	public Stepper() {
		this.steps = new ArrayList<>();
		initFooter();
	}

	public Stepper(Component... components) {
		this();
		for (Component component : components) {
			addStep(component);
		}
	}

	public Stepper(Step... steps) {
		this();
		for (Step step : steps) {
			addStep(step);
		}
	}

	private void initFooter() {
		cancel = new Button("Cancel");
		cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
		back = new Button("Back");
		back.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		next = new Button("Next");
		next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		finish = new Button("Finish");
		finish.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

		next.addClickListener(click -> showNextStep());
		back.addClickListener(click -> showPreviousStep());

		back.setVisible(false);
		finish.setVisible(false);

		setCancelVisible(false);
	}

	private void showNextStep() {
		currentStep.complete();
		Step nextStep = getNextStep(currentStep);
		changeStep(nextStep);
	}

	private void showPreviousStep() {
		currentStep.abort();
		Step previousStep = getPreviousStep(currentStep);
		changeStep(previousStep);
	}

	private void changeStep(Step newStep) {
		content.removeAll();
		currentStep = newStep;
		currentStep.enter();
		content.add(currentStep.getContent());
		updateButtons();
	}

	private void updateButtons() {
		if (currentStep != null) {
			next.setVisible(!isLastStep(currentStep));
			next.setEnabled(currentStep.isValid());
			back.setVisible(!isFirstStep(currentStep));
			finish.setVisible(isLastStep(currentStep));
		}
	}

	private Step getNextStep(Step step) {
		if (isLastStep(step)) {
			return step;
		}
		return steps.get(steps.indexOf(step) + 1);
	}

	private Step getPreviousStep(Step step) {
		if (isFirstStep(step)) {
			return step;
		}
		return steps.get(steps.indexOf(step) - 1);
	}

	private boolean isFirstStep(Step step) {
		return steps.indexOf(step) == 0;
	}

	private boolean isLastStep(Step step) {
		return steps.indexOf(step) == steps.size() - 1;
	}

	public void addStep(Component stepContent) {
		addStep("", stepContent);
	}

	public void addStep(String stepTitle, Component stepContent) {
		StepHeader stepHeader = new StepHeader(steps.size() + 1, stepTitle);
		addStep(stepHeader, stepContent);
	}

	public void addStep(Component stepHeader, Component stepContent) {
		Step step = new Step(stepHeader, stepContent);
		addStep(step);
	}

	public void addStep(Step step) {
		step.addValidationListener(isValid -> next.setEnabled(isValid));
		header.add(step.getHeader());
		steps.add(step);
		if (currentStep == null) {
			currentStep = step;
			currentStep.enter();
			content.add(currentStep.getContent());
		}
	}

	public void setCancelVisible(boolean visible) {
		footer.removeAll();
		cancel.setVisible(visible);
		if (visible) {
			footer.add(cancel, new HorizontalLayout(back, next, finish));
		} else {
			footer.add(back, new Div(), new HorizontalLayout(next, finish));
		}
	}

	public Registration addCancelListener(ComponentEventListener<ClickEvent<Button>> listener) {
		setCancelVisible(true);
		return cancel.addClickListener(listener);
	}

	public Registration addFinishListener(ComponentEventListener<ClickEvent<Button>> listener) {
		return finish.addClickListener(listener);
	}

}
