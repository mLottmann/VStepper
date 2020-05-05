package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.AbortEvent;
import com.mlottmann.vstepper.stepEvent.CompleteEvent;
import com.mlottmann.vstepper.stepEvent.EnterEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import lombok.Getter;
import lombok.Setter;

public class BinderContent<T> extends StepContent {

	private Binder<T> binder;
	@Getter
	@Setter
	private T value;

	public BinderContent(Binder<T> binder, Component... components) {
		this.binder = binder;
		this.binder.addValueChangeListener(valueChangeEvent -> stepChanged());
		add(components);
	}

	@Override
	public boolean isValid() {
		return binder.isValid();
	}

	@Override
	public void onAbort(AbortEvent event) {
		binder.readBean(value);
	}

	@Override
	public void onComplete(CompleteEvent event) {
		try {
			binder.writeBean(value);
		} catch (ValidationException var2) {
			throw new IllegalStateException(var2);
		}
	}

	@Override
	public void onEnter(EnterEvent event) {
		if (value == null) {
			throw new NullPointerException("Set the value before entering the view.");
		}
		binder.readBean(value);
	}
}
