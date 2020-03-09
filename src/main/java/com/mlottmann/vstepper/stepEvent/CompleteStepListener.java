package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for handling complete step events.
 */
public interface CompleteStepListener extends StepEventListener {
	void complete(CompleteEvent event);
}
