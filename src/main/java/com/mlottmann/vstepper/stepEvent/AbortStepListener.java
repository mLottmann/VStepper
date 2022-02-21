package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for listening to abort step events.
 */
public interface AbortStepListener extends StepEventListener {
    void onAbort(AbortEvent event);
}
