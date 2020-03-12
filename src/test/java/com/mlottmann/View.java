package com.mlottmann;

import com.mlottmann.vstepper.Step;
import com.mlottmann.vstepper.VaadinStepper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends VerticalLayout {

	public View() {
		VaadinStepper simpleUse = createSimpleUseTest();
		simpleUse.setWidth("500px");
		simpleUse.setHeight("350px");
		add(new Label("Simple Use:"), simpleUse);

		VaadinStepper headerCaptions = createHeaderCaptionsTest();
		headerCaptions.setWidth("500px");
		headerCaptions.setHeight("350px");
		add(new Label("Header Captions:"), headerCaptions);

		VaadinStepper headerComponents = createHeaderComponentsTest();
		headerComponents.setWidth("500px");
		headerComponents.setHeight("350px");
		add(new Label("Header Components:"), headerComponents);

		VaadinStepper customHeader = createCustomHeadersTest();
		customHeader.setWidth("500px");
		customHeader.setHeight("350px");
		add(new Label("Custom Header:"), headerComponents);

		VaadinStepper fullContent = createFullContentTest();
		fullContent.setWidth("500px");
		fullContent.setHeight("350px");
		add(new Label("Complex Content:"), fullContent);

		VaadinStepper fullStep = createFullStepTest();
		fullStep.setWidth("500px");
		fullStep.setHeight("350px");
		add(new Label("Custom Step:"), fullStep);

		VaadinStepper show = createShowTest();
		show.setWidth("500px");
		show.setHeight("350px");
		add(new Label("Pretty Example:"), show);

		setSpacing(true);
		setSizeFull();
		setAlignItems(Alignment.CENTER);
	}

	private VaadinStepper createSimpleUseTest() {
		VaadinStepper simpleUse = new VaadinStepper();
		simpleUse.addStep(new Label("Step 1"));
		simpleUse.addStep(new Label("Step 2"));
		simpleUse.addStep(new Label("Step 3"));
		return simpleUse;
	}

	private VaadinStepper createHeaderCaptionsTest() {
		VaadinStepper customHeaderCaptions = new VaadinStepper();
		customHeaderCaptions.addStep("Step 1", new Label("Step 1"));
		customHeaderCaptions.addStep("Step 2", new Label("Step 2"));
		customHeaderCaptions.addStep("Step 3", new Label("Step 3"));
		customHeaderCaptions.setCancelVisible(true);
		return customHeaderCaptions;
	}

	private VaadinStepper createHeaderComponentsTest() {
		VaadinStepper headerComponents = new VaadinStepper();
		headerComponents.addStep(new Label("Header 1"), new Label("Step 1"));
		TextField header2 = new TextField();
		header2.setValue("Header 2");
		headerComponents.addStep(header2, new Label("Step 2"));
		headerComponents.addStep(new Label("Header 3"), new Label("Step 3"));
		return headerComponents;
	}

	private VaadinStepper createCustomHeadersTest() {
		VaadinStepper customHeaders = new VaadinStepper();
		customHeaders.addStep(new CustomHeader("Step 1"), new Label("Step 1"));
		customHeaders.addStep(new CustomHeader("Step 2"), new Label("Step 2"));
		customHeaders.addStep(new CustomHeader("Step 3"), new Label("Step 3"));
		return customHeaders;
	}

	private VaadinStepper createFullContentTest() {
		VaadinStepper fullContentTest = new VaadinStepper();
		fullContentTest.addStep("Step 1", new FullContent());
		fullContentTest.addStep("Step 2", new FullContent());
		fullContentTest.addStep("Step 3", new FullContent());
		return fullContentTest;
	}

	private VaadinStepper createCustomStepsTest() {
		VaadinStepper customSteps = new VaadinStepper();
		customSteps.addStep(createStep(new Label("Step 1"), new Label("Step 1")));
		customSteps.addStep(createStep(new Label("Step 2"), new Label("Step 2")));
		customSteps.addStep(createStep(new Label("Step 3"), new Label("Step 3")));
		return customSteps;
	}

	private Step createStep(Component header, Component content) {
		return new Step(header, content) {
			@Override
			protected void onEnter() {
			}

			@Override
			protected void onAbort() {
			}

			@Override
			protected void onComplete() {
			}

			@Override
			public boolean isValid() {
				return true;
			}
		};
	}

	private VaadinStepper createFullStepTest() {
		VaadinStepper customStepTest = new VaadinStepper();
		customStepTest.addStep(new FullStep("Step 1"));
		customStepTest.addStep(new FullStep("Step 2"));
		customStepTest.addStep(new FullStep("Step 3"));
		return customStepTest;
	}

	private VaadinStepper createShowTest() {
		VaadinStepper showTest = new VaadinStepper();
		showTest.addStep("Personal Info", createPersonalInfoForm());
		showTest.addStep("Address", createAddressForm());
		showTest.addStep("Confirmation", createConfirmation());
		return showTest;
	}

	private VerticalLayout createPersonalInfoForm() {
		VerticalLayout personalInfo = new VerticalLayout();
		personalInfo.add(createRow("First Name:", createInput("Please enter your first name")));
		personalInfo.add(createRow("Last Name:", createInput("Please enter your last name")));
		personalInfo.add(createRow("Date of Birth:", new DatePicker()));
		return personalInfo;
	}

	private VerticalLayout createAddressForm() {
		VerticalLayout addressForm = new VerticalLayout();
		addressForm.add(createRow("Street Name:", createInput("Please enter street name")));
		addressForm.add(createRow("Postal code:", createInput("Please enter postal code")));
		addressForm.add(createRow("Country:", createInput("Please enter country")));
		return addressForm;
	}

	private VerticalLayout createConfirmation() {
		VerticalLayout confirmation = new VerticalLayout();
		confirmation.add("Please confirm that the entered information is correct.");
		return confirmation;
	}

	private TextField createInput(String placeholder) {
		TextField input = new TextField();
		input.setPlaceholder(placeholder);
		input.setWidth("250px");
		return input;
	}

	private HorizontalLayout createRow(String caption, Component input) {
		Label title = new Label(caption);
		HorizontalLayout row = new HorizontalLayout(title, input);
		row.setFlexGrow(1, title);
		row.setAlignItems(FlexComponent.Alignment.CENTER);
		row.setWidth("100%");
		return row;
	}
}
