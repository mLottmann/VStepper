package com.mlottmann.vstepper.stepEvent;

/**
 * Interface for listening to enter step events.
 */
public interface EnterStepListener extends StepEventListener {
	void onEnter(EnterEvent event);
}
