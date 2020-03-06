package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for handling abort step events.
 */
public interface AbortStepListener extends StepEventListener {
	void abort(StepEvent event);
}
