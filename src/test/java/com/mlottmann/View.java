package com.mlottmann;

import com.mlottmann.vstepper.VStepper;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends Div {

	public View() {
		VStepper stepper = completeTest();
		stepper.setWidth("500px");
		stepper.setHeight("300px");
		add(stepper);
	}

	private VStepper createSimpleUseTest() {
		VStepper simpleUse = new VStepper();
		simpleUse.addStep(new Label("Step 1"));
		simpleUse.addStep(new Label("Step 2"));
		simpleUse.addStep(new Label("Step 3"));
		return simpleUse;
	}

	private VStepper createSimpleUseTest2() {
		VStepper simpleUse = new VStepper(new Label("Step 1"),
				new Label("Step 2"), new Label("Step 3"));
		return simpleUse;
	}

	private VStepper createHeaderCaptionsTest() {
		VStepper customHeaderCaptions = new VStepper();
		customHeaderCaptions.addStep("Step 1", new Label("Step 1"));
		customHeaderCaptions.addStep("Step 2", new Label("Step 2"));
		customHeaderCaptions.addStep("Step 3", new Label("Step 3"));
		return customHeaderCaptions;
	}

	private VStepper createHeaderComponentsTest() {
		VStepper headerCaptions = new VStepper();
		headerCaptions.addStep(new Label("Header 1"), new Label("Step 1"));
		TextField header2 = new TextField();
		header2.setValue("Header 2");
		headerCaptions.addStep(header2, new Label("Step 2"));
		headerCaptions.addStep(new DatePicker(), new Label("Step 3"));
		return headerCaptions;
	}

	private VStepper createCustomHeadersTest() {
		VStepper customHeaderCaptions = new VStepper();
		customHeaderCaptions.addStep(new CustomHeader("Step 1"), new Label("Step 1"));
		customHeaderCaptions.addStep(new CustomHeader("Step 2"), new Label("Step 2"));
		customHeaderCaptions.addStep(new CustomHeader("Step 3"), new Label("Step 3"));
		return customHeaderCaptions;
	}

	private VStepper completeTest() {
		VStepper completeTest = new VStepper();
		CompleteHeader header1 = new CompleteHeader("Step 1");
		completeTest.addStep(header1, new CompleteContent(header1.getStepData()::setText));
		CompleteHeader header2 = new CompleteHeader("Step 2");
		completeTest.addStep(header2, new CompleteContent(header2.getStepData()::setText));
		CompleteHeader header3 = new CompleteHeader("Step 3");
		completeTest.addStep(header3, new CompleteContent(header3.getStepData()::setText));
		return completeTest;
	}
}
