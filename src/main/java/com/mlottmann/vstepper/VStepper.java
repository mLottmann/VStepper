package com.mlottmann.vstepper;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias Lottmann
 * <p>
 * Vaadin addon for displaying a series of components one at a time.
 */
@Tag("v-stepper")
@JsModule("./v-stepper.js")
public class VStepper extends LitTemplate implements HasSize, HasStyle {

    private final List<Step> steps;
    private Step currentStep;
    private ValidationMode validationMode = ValidationMode.ON_CHANGE;

    @Id
    @Getter
    private Div header;
    @Id
    private Div content;
    @Id
    @Getter
    private Div footer;

    @Getter
    private Button cancel;
    @Getter
    private Button back;
    @Getter
    private Button next;
    @Getter
    private Button finish;

    public VStepper() {
        this.steps = new ArrayList<>();
        initFooter();
    }

    /**
     * @param components the components to display in the different stepper steps.
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
        finish.setWidth("90px");
        finish.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        next.addClickListener(click -> showNextStep());
        back.addClickListener(click -> showPreviousStep());
        finish.addClickListener(click -> currentStep.complete());

        back.setVisible(false);
        finish.setVisible(false);
        setCancelVisible(false);
    }

    private void showNextStep() {
        if (currentStep.isValid()) {
            currentStep.complete();
            Step nextStep = getNextStep(currentStep);
            changeStep(nextStep);
        }
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
            updateButtonVisibility();
            updateButtonEnabledState();
        }
    }

    private void updateButtonVisibility() {
        next.setVisible(!isLastStep(currentStep));
        back.setVisible(!isFirstStep(currentStep));
        finish.setVisible(isLastStep(currentStep));
    }

    private void updateButtonEnabledState() {
        if (validationMode == ValidationMode.ON_CHANGE) {
            next.setEnabled(currentStep.isValid());
            finish.setEnabled(currentStep.isValid());
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
        StepHeader stepHeader = new DefaultStepHeader(steps.size() + 1, stepTitle);
        addStep(stepHeader, stepContent);
    }

    /**
     * Adds a new step with the given header component and content component to the stepper.
     *
     * @param stepHeader  the header component of this step to display in the header of the stepper.
     * @param stepContent the content to display when the corresponding step is reached.
     */
    public void addStep(Component stepHeader, Component stepContent) {
        Step step = new DefaultStep(stepHeader, stepContent);
        addStep(step);
    }

    /**
     * Adds the given step to the stepper.
     *
     * @param step the step to add to the stepper. Each step consists of a header and a content component.
     */
    public void addStep(Step step) throws IllegalArgumentException {
        checkStep(step);
        step.addStepNavigationListener(event -> setCurrentStep(event.getStep()));
        step.addValidationListener(event -> updateButtonEnabledState());
        header.add(step.getHeader());
        steps.add(step);
        initialize();
    }

    private void checkStep(Step step) throws IllegalArgumentException {
        if (step.getHeader() == null) {
            throw new IllegalArgumentException("Step header can not be null.");
        }
        if (step.getContent() == null) {
            throw new IllegalArgumentException("Step content can not be null.");
        }
    }

    public void setCurrentStep(Step step) {
        if (steps.contains(step)) {
            changeStep(step);
        }
    }

    public void enableHeaderNavigation(boolean enabled) {

    }

    public Registration addCancelListener(ComponentEventListener<ClickEvent<Button>> listener) {
        setCancelVisible(true);
        return cancel.addClickListener(listener);
    }

    /**
     * Sets the visibility of the cancel button in the footer. Also affects the layout of the footer.
     *
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

    public Registration addNextListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return next.addClickListener(listener);
    }

    public Registration addBackListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return back.addClickListener(listener);
    }

    public Registration addFinishListener(ComponentEventListener<ClickEvent<Button>> listener) {
        return finish.addClickListener(listener);
    }

    public void setCancelText(String text) {
        cancel.setText(text);
    }

    public void setNextText(String text) {
        next.setText(text);
    }

    public void setBackText(String text) {
        back.setText(text);
    }

    public void setFinishText(String text) {
        finish.setText(text);
    }

    public void setValidationMode(ValidationMode validationMode) {
        this.validationMode = validationMode;
        next.setEnabled(validationMode == ValidationMode.ON_NEXT || currentStep.isValid());
        finish.setEnabled(validationMode == ValidationMode.ON_NEXT || currentStep.isValid());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        initialize();
    }

    /**
     * Set the first step.
     * Only happens when the component is attached, at least one step is available and there is no current step.
     */
    protected void initialize() {
        if (getParent().isPresent() && !steps.isEmpty() && currentStep == null) {
            currentStep = steps.get(0);
            currentStep.enter();
            next.setEnabled(currentStep.isValid());
            content.add(currentStep.getContent());
        }
    }

}
