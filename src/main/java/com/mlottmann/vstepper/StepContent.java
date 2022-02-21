package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.AbortStepListener;
import com.mlottmann.vstepper.stepEvent.CompleteStepListener;
import com.mlottmann.vstepper.stepEvent.EnterStepListener;
import com.vaadin.flow.component.html.Div;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Abstract helper class for step contents implementing all step listeners and the validation content interface
 */
public abstract class StepContent extends Div implements EnterStepListener, AbortStepListener, CompleteStepListener, ValidationContent {

    private final List<Consumer<Boolean>> validationListeners;
    private boolean validState;

    public StepContent() {
        this.validationListeners = new ArrayList<>();
    }

    public void addValidationListener(Consumer<Boolean> validationListener) {
        this.validationListeners.add(validationListener);
    }

    protected void stepChanged() {
        if (validState != isValid()) {
            validState = isValid();
            updateValidationListeners();
        }
    }

    private void updateValidationListeners() {
        validationListeners.forEach(listener -> listener.accept(validState));
    }

}
