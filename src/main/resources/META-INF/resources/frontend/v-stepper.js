import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import '@vaadin/vaadin-lumo-styles/color.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import './styles/stepper-styles.js'
import './styles/step-header-styles.js'

class VStepper extends ThemableMixin(PolymerElement) {
    static get template() {
        return html`
            <style include="stepper-styles step-header-styles lumo-color lumo-spacing"></style>
            <div id="header" class="header" part="header"></div>
            <div id="content" class="content" part="content"></div>
            <div id="footer" class="footer" part="footer"></div>
        `;
    }

    static get is() {
        return 'v-stepper';
    }
}

window.customElements.define('v-stepper', VStepper);