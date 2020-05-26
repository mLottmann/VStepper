package com.mlottmann;

import com.mlottmann.vstepper.*;
import com.mlottmann.vstepper.stepEvent.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Route("demo")
@CssImport(value = "./styles/stepper-test-styles.css", themeFor = "v-stepper")
@CssImport(value = "./styles/textfield-test-styles.css", themeFor = "vaadin-text-field")
public class View extends DemoView {

	@Override
	protected void initView() {
		fullDemo();
		simpleStepperDemo();
		headerCaptionsDemo();
		buttonListenersDemo();
		customHeadersDemo();
		customContentDemo();
		customStepDemo();
		setParameter(null, "full-demo");
	}

	// begin-source-example
	// source-example-heading: Full Demo
	private void fullDemo() {
		VStepper stepper = new VStepper();
		stepper.getNext().setIcon(VaadinIcon.COG.create());
		stepper.setHeight("400px");
		Person person = new Person();
		Address address = new Address();
		stepper.addStep("Personal Info", createPersonalInfoContent(person));
		stepper.addStep("Address", createAddressContent(address));
		stepper.addStep("Confirmation", new ConfirmationContent(person, address));
		addCard("Full Demo", "Full Demo", stepper);
	}

	private StepContent createPersonalInfoContent(Person person) {
		Binder<Person> binder = new Binder<>();
		Div firstName = createFirstNameRow(binder);
		Div lastName = createLastNameRow(binder);
		Div birthDate = createBirthDateRow(binder);

		BinderContent<Person> content = new BinderContent<>(binder,
				firstName, lastName, birthDate);
		content.setValue(person);
		content.addClassName("custom-content");
		content.setWidth("100%");
		content.setHeight("200px");
		return content;
	}

	private Div createFirstNameRow(Binder<Person> binder) {
		TextField firstName = createInput("Please enter your first name");
		binder.forField(firstName)
				.withValidator(new Validator<String>() {
					@Override
					public ValidationResult apply(String s, ValueContext valueContext) {
						return s.isEmpty() ? ValidationResult.error("First name can not be empty") : ValidationResult.ok();
					}
				})
				.bind(Person::getFirstName, Person::setFirstName);
		return createRow("First Name:", firstName);
	}

	private Div createLastNameRow(Binder<Person> binder) {
		TextField lastName = createInput("Please enter your last name");
		binder.forField(lastName)
				.withValidator(new Validator<String>() {
					@Override
					public ValidationResult apply(String s, ValueContext valueContext) {
						return s.isEmpty() ? ValidationResult.error("Last name can not be empty") : ValidationResult.ok();
					}
				})
				.bind(Person::getLastName, Person::setLastName);
		return createRow("Last Name:", lastName);
	}

	private Div createBirthDateRow(Binder<Person> binder) {
		DatePicker birthDate = new DatePicker();
		birthDate.setPlaceholder("Please enter you date of birth");
		birthDate.setWidth("270px");
		binder.forField(birthDate)
				.withValidator(new Validator<LocalDate>() {
					@Override
					public ValidationResult apply(LocalDate date, ValueContext valueContext) {
						return date == null ? ValidationResult.error("Birth date can not be empty") : ValidationResult.ok();
					}
				})
				.bind(Person::getBirthDate, Person::setBirthDate);
		return createRow("Date of Birth:", birthDate);
	}

	private StepContent createAddressContent(Address address) {
		Binder<Address> binder = new Binder<>();
		Div streetName = createStreetNameRow(binder);
		Div postalCode = createPostalCodeRow(binder);
		Div country = createCountryRow(binder);

		BinderContent<Address> content = new BinderContent(binder,
				streetName, postalCode, country);
		content.setValue(address);
		content.addClassName("custom-content");
		content.setWidth("100%");
		content.setHeight("200px");
		return content;
	}

	private Div createStreetNameRow(Binder<Address> binder) {
		TextField streetName = createInput("Please enter street name");
		binder.forField(streetName)
				.withValidator(new Validator<String>() {
					@Override
					public ValidationResult apply(String s, ValueContext valueContext) {
						return s.isEmpty() ? ValidationResult.error("Street name can not be empty") : ValidationResult.ok();
					}
				})
				.bind(Address::getStreetName, Address::setStreetName);
		return createRow("Street Name:", streetName);
	}

	private Div createPostalCodeRow(Binder<Address> binder) {
		TextField postalCode = createInput("Please enter your postal code");
		binder.forField(postalCode)
				.withValidator(new Validator<String>() {
					@Override
					public ValidationResult apply(String s, ValueContext valueContext) {
						return s.isEmpty() ? ValidationResult.error("Postal code can not be empty") : ValidationResult.ok();
					}
				})
				.bind(Address::getPostalCode, Address::setPostalCode);
		return createRow("Postal Code:", postalCode);
	}

	private Div createCountryRow(Binder<Address> binder) {
		ComboBox<String> countries = new ComboBox();
		countries.setItemLabelGenerator(s -> new Locale("", s).getDisplayCountry(Locale.US));
		countries.setItems(Locale.getISOCountries());
		countries.setPlaceholder("Please select a country");
		countries.setWidth("270px");
		binder.forField(countries)
				.withValidator(new Validator<String>() {
					@Override
					public ValidationResult apply(String s, ValueContext valueContext) {
						return s == null ? ValidationResult.error("Country can not be empty") : ValidationResult.ok();
					}
				})
				.bind(Address::getCountry, Address::setCountry);
		return createRow("Country:", countries);
	}

	private TextField createInput(String placeholder) {
		TextField input = new TextField();
		input.setValueChangeMode(ValueChangeMode.EAGER);
		input.setPlaceholder(placeholder);
		input.setWidth("270px");
		return input;
	}

	private Div createRow(String caption, Component input) {
		Label title = new Label(caption);
		Div row = new Div(title, input);
		row.addClassName("row");
		return row;
	}

	private class ConfirmationContent extends StepContent {

		private Person person;
		private Address address;
		private Binder<Person> personBinder;
		private Binder<Address> addressBinder;
		private Checkbox confirmation;

		public ConfirmationContent(Person person, Address address) {
			this.person = person;
			this.address = address;
			this.personBinder = new Binder<>();
			this.addressBinder = new Binder<>();
			addContent();
			addClassName("confirmation-content");
		}

		private void addContent() {
			TextField firstName = createDisplayField("First Name");
			personBinder.forField(firstName).bind(Person::getFirstName, null);

			TextField lastName = createDisplayField("Last Name");
			personBinder.forField(lastName).bind(Person::getLastName, null);

			TextField birthDate = createDisplayField("Birth Date");
			personBinder.forField(birthDate).bind(this::getBirthDate, null);

			TextField streetName = createDisplayField("Street Name");
			addressBinder.forField(streetName).bind(Address::getStreetName, null);

			TextField postalCode = createDisplayField("Postal Code");
			addressBinder.forField(postalCode).bind(Address::getPostalCode, null);

			TextField country = createDisplayField("Country");
			addressBinder.forField(country).bind(this::getCountry, null);

			confirmation = new Checkbox("Please confirm that the entered information is correct.");
			confirmation.addValueChangeListener(valueChanged -> stepChanged());
			add(firstName, streetName, lastName, birthDate, streetName, postalCode, country, confirmation);
		}

		private String getBirthDate(Person person) {
			return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(person.getBirthDate());
		}

		private String getCountry(Address address) {
			return new Locale("", address.getCountry()).getDisplayCountry(Locale.US);
		}

		private TextField createDisplayField(String caption) {
			TextField display = new TextField(caption);
			display.addClassName("display");
			display.setWidth("200px");
			display.setEnabled(false);
			return display;
		}

		@Override
		public boolean isValid() {
			return confirmation.getValue();
		}

		@Override
		public void onAbort(AbortEvent event) {

		}

		@Override
		public void onComplete(CompleteEvent event) {
			Notification.show("Saved Person and Address", 1000, Notification.Position.MIDDLE);
		}

		@Override
		public void onEnter(EnterEvent event) {
			personBinder.readBean(person);
			addressBinder.readBean(address);
		}
	}

	@Getter
	@Setter
	private class Person {
		private String firstName;
		private String lastName;
		private LocalDate birthDate;
	}

	@Getter
	@Setter
	private class Address {
		private String StreetName;
		private String PostalCode;
		private String Country;
	}
	// end-source-example

	private void simpleStepperDemo() {
		// begin-source-example
		// source-example-heading: Simple Stepper
		VStepper stepper = new VStepper();
		stepper.setHeight("250px");
		stepper.addStep(new Label("Step 1 Content"));
		stepper.addStep(new TextField("Step 2 Content"));
		stepper.addStep(new Checkbox("Step 3 Content"));
		stepper.addFinishListener(buttonClickEvent ->
				Notification.show("Finished", 1000, Notification.Position.MIDDLE));
		// end-source-example
		addCard("Basic Usage", "Simple Stepper", stepper);
	}

	private void headerCaptionsDemo() {
		// begin-source-example
		// source-example-heading: Header Captions
		VStepper stepper = new VStepper();
		stepper.setHeight("250px");
		stepper.addStep("Step 1", new Label("Step 1 Content"));
		stepper.addStep("Step 2", new TextField("Step 2 Content"));
		stepper.addStep("Step 3", new Checkbox("Step 3 Content"));
		stepper.addFinishListener(buttonClickEvent ->
				Notification.show("Finished", 1000, Notification.Position.MIDDLE));
		// end-source-example
		addCard("Basic Usage", "Header Captions", stepper);
	}

	private void buttonListenersDemo() {
		// begin-source-example
		// source-example-heading: Button Listeners
		VStepper stepper = new VStepper();
		stepper.setHeight("250px");
		stepper.addStep(new Label("Step 1 Content"));
		stepper.addStep(new TextField("Step 2 Content"));
		stepper.addStep(new Checkbox("Step 3 Content"));
		stepper.setCancelVisible(true);
		stepper.addCancelListener(buttonClickEvent ->
				Notification.show("Cancel", 1000, Notification.Position.MIDDLE));
		stepper.addBackListener(buttonClickEvent ->
				Notification.show("Back", 1000, Notification.Position.MIDDLE));
		stepper.addNextListener(buttonClickEvent ->
				Notification.show("Next", 1000, Notification.Position.MIDDLE));
		stepper.addFinishListener(buttonClickEvent ->
				Notification.show("Finished", 1000, Notification.Position.MIDDLE));
		// end-source-example
		addCard("Basic Usage", "Button Listeners", stepper);
	}

	// begin-source-example
	// source-example-heading: Custom Headers
	private void customHeadersDemo() {
		VStepper stepper = new VStepper();
		stepper.addStep(new CustomHeader("Step 1"), new Label("Step 1"));
		stepper.addStep(new CustomHeader("Step 2"), new Label("Step 2"));
		stepper.addStep(new CustomHeader("Step 3"), new Label("Step 3"));
		stepper.addFinishListener(buttonClickEvent ->
				Notification.show("Finished", 1000, Notification.Position.MIDDLE));
		addCard("Advanced Usage", "Custom Headers", stepper);
	}

	private class CustomHeader extends StepHeader
			implements AbortStepListener, CompleteStepListener, EnterStepListener {

		private Label state;

		public CustomHeader(String name) {
			Label caption = new Label(name);
			state = new Label("Inactive");
			addClassName("custom-header");
			add(caption, state);
		}

		@Override
		public void onAbort(AbortEvent event) {
			super.onAbort(event); //Needed to attach/detach CSS classes
			state.setText("Inactive");
		}

		@Override
		public void onComplete(CompleteEvent event) {
			super.onComplete(event); //Needed to attach/detach CSS classes
			state.setText("Complete");
		}

		@Override
		public void onEnter(EnterEvent event) {
			super.onEnter(event); //Needed to attach/detach CSS classes
			state.setText("Active");
		}
	}
	// end-source-example

	// begin-source-example
	// source-example-heading: Custom Content
	private void customContentDemo() {
		VStepper stepper = new VStepper();
		stepper.addStep("Step 1", new CustomContent());
		stepper.addStep("Step 2", new CustomContent());
		stepper.addStep("Step 3", new CustomContent());
		stepper.addFinishListener(buttonClickEvent ->
				Notification.show("Finished", 1000, Notification.Position.MIDDLE));
		stepper.setValidationMode(ValidationMode.ON_NEXT);
		addCard("Advanced Usage", "Custom Content", stepper);
	}

	private class CustomContent extends StepContent {

		private TextField input;

		public CustomContent() {
			this.input = new TextField("Input");
			this.input.setPlaceholder("Enter something to move on.");
			this.input.addValueChangeListener(valueChanged -> stepChanged());
			this.input.setValueChangeMode(ValueChangeMode.EAGER);
			this.input.setWidth("250px");
			addClassName("custom-content");
			add(this.input);
		}

		@Override
		public boolean isValid() {
			return input.getValue() != null && !input.getValue().isEmpty();
		}

		@Override
		public void onAbort(AbortEvent event) {
			Notification.show("Discarded: " + input.getValue(),
					1000, Notification.Position.MIDDLE);
		}

		@Override
		public void onComplete(CompleteEvent event) {
			Notification.show("Saved: " + input.getValue(),
					1000, Notification.Position.MIDDLE);
		}

		@Override
		public void onEnter(EnterEvent event) {
			input.setValue("");
		}
	}
	// end-source-example

	// begin-source-example
	// source-example-heading: Custom Steps
	private void customStepDemo() {
		VStepper stepper = new VStepper();
		stepper.addStep(new CustomStep("Step 1"));
		stepper.addStep(new CustomStep("Step 2"));
		stepper.addStep(new CustomStep("Step 3"));
		stepper.addFinishListener(buttonClickEvent ->
				Notification.show("Finished", 1000, Notification.Position.MIDDLE));
		addCard("Advanced Usage", "Custom Steps", stepper);
	}

	public class CustomStep extends Step {

		private Label inputValue;
		private TextField input;
		private boolean isValid;

		public CustomStep(String title) {
			setHeader(createHeader(title));
			setContent(createContent());
			isValid = isValid();
		}

		private Div createHeader(String title) {
			Label headerTitle = new Label(title);
			inputValue = new Label("-");
			StepHeader header = new StepHeader(headerTitle, inputValue);
			header.addClassName("custom-header");
			return header;
		}

		private Div createContent() {
			input = new TextField("Input");
			input.setPlaceholder("Enter something to move on.");
			input.addValueChangeListener(valueChanged -> inputChanged());
			input.setValueChangeMode(ValueChangeMode.EAGER);
			input.setWidth("250px");
			Div content = new Div(input);
			content.addClassName("custom-content");
			return content;
		}

		private void inputChanged() {
			if (isValid != isValid()) {
				isValid = isValid();
				updateValidationListeners();
			}
		}

		@Override
		public void onEnter() {
			inputValue.setText("-");
		}

		@Override
		public void onAbort() {
			input.setValue("");
			inputValue.setText("-");
		}

		@Override
		public void onComplete() {
			inputValue.setText(input.getValue());
		}

		@Override
		public boolean isValid() {
			return input.getValue() != null && !input.getValue().isEmpty();
		}
	}
	// end-source-example
}
