package com.mlottmann.vstepper.stepEvent;

public interface ValidateStepListener extends StepEventListener {
	void validationChanged(ValidateStepEvent event);
}
