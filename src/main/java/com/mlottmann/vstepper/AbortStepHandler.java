package com.mlottmann.vstepper;

public interface AbortStepHandler {
	/**
	 * Called on the implementing step when moving on the previous step.
	 */
	void abort();
}
