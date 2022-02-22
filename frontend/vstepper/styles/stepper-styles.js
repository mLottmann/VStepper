import styles from './stepper-styles.css'

const $_documentContainer = document.createElement('template');
$_documentContainer.innerHTML = `
  <dom-module id="stepper-styles">
    <template><style>${styles}</style></template>
  </dom-module>`;
document.head.appendChild($_documentContainer.content);