package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for listening to complete step events.
 */
public interface CompleteStepListener extends StepEventListener {
    void onComplete(CompleteEvent event);
}
