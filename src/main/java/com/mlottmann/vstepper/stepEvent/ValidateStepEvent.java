package com.mlottmann.vstepper.stepEvent;

import com.mlottmann.vstepper.Step;
import lombok.Getter;

@Getter
public class ValidateStepEvent extends StepEvent {

	private boolean isValid;

	public ValidateStepEvent(Step step, boolean isValid) {
		super(step);
		this.isValid = isValid;
	}
}
