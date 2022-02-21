package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for listening to validation change events.
 */
public interface ValidationStepListener extends StepEventListener {
    void onValidationChange(ValidationChangedEvent event);
}
