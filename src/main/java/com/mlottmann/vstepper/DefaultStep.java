package com.mlottmann.vstepper;

import com.vaadin.flow.component.Component;

class DefaultStep extends Step {

    DefaultStep(Component header, Component content) {
        super(header, content);
    }

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
        Component content = getContent();
        if (content instanceof ValidationContent) {
            return ((ValidationContent) content).isValid();
        }
        return true;
    }


}
