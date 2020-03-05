package com.mlottmann.vstepper;

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

/**
 * @author Matthias Lottmann
 * <p>
 * Vaadin addon for displaying a series of components one at a time.
 */
@Tag("v-stepper")
@JsModule("./v-stepper.js")
public class VStepper extends PolymerTemplate<TemplateModel> implements HasSize, HasStyle {

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

	public VStepper() {
		this.steps = new ArrayList<>();
		initFooter();
	}

	/**
	 * @param components the components to display in the the different stepper steps.
	 */
	public VStepper(Component... components) {
		this();
		for (Component component : components) {
			addStep(component);
		}
	}

	/**
	 * @param steps the steps to display in this stepper. A step consists of a header and a content component.
	 */
	public VStepper(Step... steps) {
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

	/**
	 * Adds a new step with the given content component and a default header component to the stepper.
	 *
	 * @param stepContent the content to display when the corresponding step is reached.
	 */
	public void addStep(Component stepContent) {
		addStep("", stepContent);
	}

	/**
	 * Adds a new step with the given content component and a default header component with the given title to
	 * the stepper.
	 *
	 * @param stepTitle   the title to display in the default header component.
	 * @param stepContent the content to display when the corresponding step is reached.
	 */
	public void addStep(String stepTitle, Component stepContent) {
		StepHeader stepHeader = new StepHeader(steps.size() + 1, stepTitle);
		addStep(stepHeader, stepContent);
	}

	/**
	 * Adds a new step with the given header component and content component to the stepper.
	 *
	 * @param stepHeader  the header component of this step to display in the header of the stepper.
	 * @param stepContent the content to display when the corresponding step is reached.
	 */
	public void addStep(Component stepHeader, Component stepContent) {
		Step step = new Step(stepHeader, stepContent);
		addStep(step);
	}

	/**
	 * And the given step to the stepper.
	 *
	 * @param step the step to add to the stepper. Each step consists of a header and a content component.
	 */
	private void addStep(Step step) {
		step.addValidationListener(isValid -> next.setEnabled(isValid));
		header.add(step.getHeader());
		steps.add(step);
		if (currentStep == null) {
			showFirstStep(step);
		}
	}

	private void showFirstStep(Step step) {
		currentStep = step;
		currentStep.enter();
		next.setEnabled(currentStep.isValid());
		content.add(currentStep.getContent());
	}

	/**
	 * Sets the visibility of the cancel button in the footer. Also affects the layout of the footer.
	 *
	 * @param visible
	 */
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
