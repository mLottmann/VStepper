/*
 * Copyright (c) 2001-2020 HPD Software Ltd.
 */
package com.mlottmann.vstepper.stepEvent;

/**
 * com.mlottmann.vstepper.stepEvent.NavigationStepListener, created on 15.12.20 13:30 <p>
 *
 * @author mLottmann
 */
public interface StepNavigationListener extends StepEventListener {
    void onNavigate(StepNavigationEvent event);
}
