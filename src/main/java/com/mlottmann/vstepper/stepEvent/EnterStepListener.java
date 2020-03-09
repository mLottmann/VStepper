package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for handling enter step events.
 */
public interface EnterStepListener extends StepEventListener {
	void enter(EnterEvent event);
}
