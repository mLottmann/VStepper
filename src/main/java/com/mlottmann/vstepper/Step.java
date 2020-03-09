package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container class containing the header and content component of a step.
 */
public class Step {

	private Map<Class<? extends StepEventListener>, List<? extends StepEventListener>> listeners;
	@Getter
	protected Component header;
	@Getter
	protected Component content;

	public Step() {
		this.listeners = new HashMap<>();
	}

	public Step(Component header, Component content) {
		this();
		setHeader(header);
		setContent(content);
	}

	public void setHeader(Component header) {
		removeListener(this.header);
		this.header = header;
		addListener(this.header);
	}

	public void setContent(Component content) {
		removeListener(this.content);
		this.content = content;
		addListener(this.content);
		listenTo(this.content);
	}

	private void removeListener(Component component) {
		if (component instanceof StepEventListener) {
			StepEventListener listener = (StepEventListener) component;
			listeners.values().forEach(stepEventListeners -> {
				stepEventListeners.remove(listener);
			});
		}
	}

	private void addListener(Component component) {
		addListener(component, EnterStepListener.class);
		addListener(component, AbortStepListener.class);
		addListener(component, CompleteStepListener.class);
	}

	private <T extends StepEventListener> void addListener(Component component, Class<T> listerType) {
		if (listerType.isAssignableFrom(component.getClass())) {
			addListener(listerType, listerType.cast(component));
		}
	}

	private void listenTo(Component component) {
		if (this.content instanceof ValidationContent) {
			((ValidationContent) content)
					.addValidationListener(isValid -> updateValidationListeners(isValid));
		}
	}


	public Registration addEnterListener(EnterStepListener listener) {
		return addListener(EnterStepListener.class, listener);
	}

	public Registration addAbortListener(AbortStepListener listener) {
		return addListener(AbortStepListener.class, listener);
	}

	public Registration addCompleteListener(CompleteStepListener listener) {
		return addListener(CompleteStepListener.class, listener);
	}

	public Registration addValidationListener(ValidationStepListener listener) {
		return addListener(ValidationStepListener.class, listener);
	}

	private <E extends StepEventListener> Registration addListener(Class<E> listenerType, E listener) throws IllegalArgumentException {
		if (listener == null) {
			throw new IllegalArgumentException("Listener cant be null");
		}
		List<E> list = (List) this.listeners.computeIfAbsent(listenerType, (key) -> {
			return new ArrayList();
		});
		list.add(listener);
		return () -> list.remove(listener);
	}

	public void enter() {
		EnterEvent event = new EnterEvent(this);
		getListeners(EnterStepListener.class).forEach(enterStepListener ->
				enterStepListener.enter(event));
	}

	public void abort() {
		AbortEvent event = new AbortEvent(this);
		getListeners(AbortStepListener.class).forEach(abortStepListener ->
				abortStepListener.abort(event));
	}

	public void complete() {
		CompleteEvent event = new CompleteEvent(this);
		getListeners(CompleteStepListener.class).forEach(completeStepListener ->
				completeStepListener.complete(event));
	}

	protected void updateValidationListeners(boolean isValid) {
		ValidationChangedEvent event = new ValidationChangedEvent(this, isValid);
		listeners.get(ValidationStepListener.class).forEach(stepEventListener -> {
			((ValidationStepListener) stepEventListener).validationChanged(event);
		});
	}

	private <E extends StepEventListener> List<E> getListeners(Class<E> listenerType) {
		List<E> registeredListeners = (List) this.listeners.computeIfAbsent(listenerType, (key) -> {
			return new ArrayList();
		});
		return registeredListeners;
	}

	public boolean isValid() {
		if (content instanceof ValidationContent) {
			return ((ValidationContent) content).isValid();
		}
		return true;
	}

}
