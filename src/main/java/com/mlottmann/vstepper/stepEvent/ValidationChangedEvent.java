package com.mlottmann.vstepper.stepEvent;

import com.mlottmann.vstepper.Step;
import lombok.Getter;

@Getter
public class ValidationChangedEvent extends StepEvent {

	private boolean isValid;

	public ValidationChangedEvent(Step step, boolean isValid) {
		super(step);
		this.isValid = isValid;
	}
}
