package com.mlottmann.vstepper;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;

/**
 * Default step header used whenever no header component is specified.
 */
public class DefaultStepHeader extends StepHeader {

  public DefaultStepHeader(int number, String title) {
    this(String.valueOf(number), title);
  }

  public DefaultStepHeader(String number, String title) {
    Span stepNumber = new Span(number);
    stepNumber.addClassName("step-number");
    Div numberWrapper = new Div(stepNumber);
    numberWrapper.addClassName("number-wrapper");
    Label caption = new Label(title);
    caption.addClassName("step-title");
    addClassName("step-header");
    add(numberWrapper, caption);
  }

}
