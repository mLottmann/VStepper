package com.mlottmann.vstepper.stepEvent;

import com.mlottmann.vstepper.Step;

public class AbortEvent extends StepEvent {
	public AbortEvent(Step step) {
		super(step);
	}
}
