package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.AbortEvent;
import com.mlottmann.vstepper.stepEvent.AbortStepListener;
import com.mlottmann.vstepper.stepEvent.CompleteEvent;
import com.mlottmann.vstepper.stepEvent.CompleteStepListener;
import com.mlottmann.vstepper.stepEvent.EnterEvent;
import com.mlottmann.vstepper.stepEvent.EnterStepListener;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for step headers implementing all step listeners and assigning the
 * "active" or "completed" CSS class based on the step events.
 */
public class StepHeader extends Div implements EnterStepListener, AbortStepListener, CompleteStepListener, NavigationHeader {

  private final List<Runnable> navigationListeners;

  public StepHeader() {
    super();
    navigationListeners = new ArrayList<>();
    addClickListener(divClickEvent -> updateNavigationListeners());
  }

  public StepHeader(Component... components) {
    this();
    add(components);
  }

  private void updateNavigationListeners() {
    navigationListeners.forEach(Runnable::run);
  }

  @Override
  public void onAbort(AbortEvent event) {
    removeClassName("completed");
    removeClassName("active");
  }

  @Override
  public void onComplete(CompleteEvent event) {
    removeClassName("active");
    addClassName("completed");
  }

  @Override
  public void onEnter(EnterEvent event) {
    removeClassName("completed");
    addClassName("active");
  }

  @Override
  public void addNavigationListener(Runnable navigationListener) {
    navigationListeners.add(navigationListener);
  }
}
