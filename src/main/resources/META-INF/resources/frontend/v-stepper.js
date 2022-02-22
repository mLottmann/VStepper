import {html, LitElement} from 'lit-element';
import '@vaadin/vaadin-lumo-styles/color.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import './styles/stepper-styles.js'
import './styles/step-header-styles.js'

class VStepper extends LitElement {
    static get template() {
        return html`
            <style include="stepper-styles step-header-styles lumo-color lumo-spacing"></style>
            <div id="header" class="header" part="header">
                <slot name="header"></slot>
            </div>
            <div id="content" class="content" part="content">
                <slot name="content"></slot>
            </div>
            <div id="footer" class="footer" part="footer">
                <slot name="footer"></slot>
            </div>
        `;
    }

    static get is() {
        return 'v-stepper';
    }
}

window.customElements.define('v-stepper', VStepper);
