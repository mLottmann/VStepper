package com.mlottmann;

import com.mlottmann.vstepper.VStepper;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends Div {

	public View() {
		VStepper stepper = completeTest();
		stepper.setWidth("500px");
		stepper.setHeight("350px");
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

	private VStepper createShowTest() {
		VStepper showTest = new VStepper();
		showTest.addStep("Personal Info", new Label("Step 1"));
		showTest.addStep("Address", createAddressForm());
		showTest.addStep("Confirmation", new Label("Step 3"));
		return showTest;
	}

	private VerticalLayout createAddressForm() {
		VerticalLayout addressForm = new VerticalLayout();
		addressForm.add(createRow("Street Name:", createInput("Please enter street name")));
		addressForm.add(createRow("Postal code:", createInput("Please enter postal code")));
		addressForm.add(createRow("Country:", createInput("Please enter country")));
		return addressForm;
	}

	private TextField createInput(String placeholder) {
		TextField input = new TextField();
		input.setPlaceholder(placeholder);
		input.setWidth("250px");
		return input;
	}

	private HorizontalLayout createRow(String caption, TextField input) {
		Label title = new Label(caption);
		HorizontalLayout row = new HorizontalLayout(title, input);
		row.setFlexGrow(1, title);
		row.setAlignItems(FlexComponent.Alignment.CENTER);
		row.setWidth("100%");
		return row;
	}
}
