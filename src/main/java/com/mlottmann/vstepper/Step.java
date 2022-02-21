package com.mlottmann.vstepper;

import com.mlottmann.vstepper.stepEvent.AbortEvent;
import com.mlottmann.vstepper.stepEvent.AbortStepListener;
import com.mlottmann.vstepper.stepEvent.CompleteEvent;
import com.mlottmann.vstepper.stepEvent.CompleteStepListener;
import com.mlottmann.vstepper.stepEvent.EnterEvent;
import com.mlottmann.vstepper.stepEvent.EnterStepListener;
import com.mlottmann.vstepper.stepEvent.StepEventListener;
import com.mlottmann.vstepper.stepEvent.StepNavigationEvent;
import com.mlottmann.vstepper.stepEvent.StepNavigationListener;
import com.mlottmann.vstepper.stepEvent.ValidationChangedEvent;
import com.mlottmann.vstepper.stepEvent.ValidationStepListener;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container class containing the header and content component of a step also handles firing step events.
 */
public abstract class Step {

    private final Map<Class<? extends StepEventListener>, List<? extends StepEventListener>> listeners;
    @Getter
    private Component header;
    @Getter
    private Component content;

    protected Step() {
        this.listeners = new HashMap<>();
    }

    protected Step(Component header, Component content) {
        this();
        setHeader(header);
        setContent(content);
    }

    /**
     * Sets the header component of this step and registers it as a listener if necessary.
     *
     * @param header the header component of this step.
     */
    public void setHeader(Component header) {
        removeListener(this.header);
        this.header = header;
        addListener(this.header);
        if (this.header instanceof NavigationHeader) {
            ((NavigationHeader) this.header)
                    .addNavigationListener(this::updateStepNavigationListeners);
        }
    }

    /**
     * Sets the content component of this step and registers it as a listener if necessary.
     *
     * @param content the content component of this step.
     */
    public void setContent(Component content) {
        removeListener(this.content);
        this.content = content;
        addListener(this.content);
        if (this.content instanceof ValidationContent) {
            ((ValidationContent) this.content)
                    .addValidationListener(isValid -> updateValidationListeners());
        }
    }

    private void removeListener(Component component) {
        if (component instanceof StepEventListener) {
            StepEventListener listener = (StepEventListener) component;
            listeners.values().forEach(stepEventListeners -> stepEventListeners.remove(listener));
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

    public Registration addEnterListener(EnterStepListener listener) {
        return addListener(EnterStepListener.class, listener);
    }

    private <E extends StepEventListener> Registration addListener(Class<E> listenerType, E listener) throws IllegalArgumentException {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cant be null");
        }
        List<E> list = (List) this.listeners.computeIfAbsent(listenerType, (key) -> new ArrayList<>());
        list.add(listener);
        return () -> list.remove(listener);
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

    public Registration addStepNavigationListener(StepNavigationListener listener) {
        return addListener(StepNavigationListener.class, listener);
    }

    /**
     * Called when the step is entered.
     */
    public void enter() {
        onEnter();
        EnterEvent event = new EnterEvent(this);
        getListeners(EnterStepListener.class).forEach(enterStepListener ->
                enterStepListener.onEnter(event));
    }

    protected abstract void onEnter();

    private <E extends StepEventListener> List<E> getListeners(Class<E> listenerType) {
        List<E> registeredListeners = (List) this.listeners.computeIfAbsent(listenerType, (key) -> {
            return new ArrayList<>();
        });
        return registeredListeners;
    }

    /**
     * Called when the step is exited by hitting the back button.
     */
    public void abort() {
        onAbort();
        AbortEvent event = new AbortEvent(this);
        getListeners(AbortStepListener.class).forEach(abortStepListener ->
                abortStepListener.onAbort(event));
    }

    protected abstract void onAbort();

    /**
     * Called when the step is exited by hitting the next button.
     */
    public void complete() {
        onComplete();
        CompleteEvent event = new CompleteEvent(this);
        getListeners(CompleteStepListener.class).forEach(completeStepListener ->
                completeStepListener.onComplete(event));
    }

    protected abstract void onComplete();

    protected void updateValidationListeners() {
        ValidationChangedEvent event = new ValidationChangedEvent(this, isValid());
        getListeners(ValidationStepListener.class).forEach(stepEventListener -> {
            stepEventListener.onValidationChange(event);
        });
    }

    protected void updateStepNavigationListeners() {
        StepNavigationEvent event = new StepNavigationEvent(this);
        getListeners(StepNavigationListener.class).forEach(stepNavigationListener ->
                stepNavigationListener.onNavigate(event));
    }

    public abstract boolean isValid();

}
