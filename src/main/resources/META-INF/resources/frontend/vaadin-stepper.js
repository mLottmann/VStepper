import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-lumo-styles/color.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import './styles/stepper-styles.js'

class VaadinStepper extends PolymerElement {
    static get template() {
        return html`
            <style include="stepper-styles lumo-color lumo-spacing"></style>
            <div id="header" class="header"></div>
            <div id="content" class="content"></div>
            <div id="footer" class="footer"></div>
        `;
    }
}

window.customElements.define('vaadin-stepper', VaadinStepper);