package com.mlottmann.vstepper.stepEvent;

import com.mlottmann.vstepper.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StepEvent {

    private final Step step;
}
