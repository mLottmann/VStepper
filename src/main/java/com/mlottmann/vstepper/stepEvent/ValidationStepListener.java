package com.mlottmann.vstepper.stepEvent;

public interface ValidationStepListener extends StepEventListener {
	void validationChanged(ValidationChangedEvent event);
}
