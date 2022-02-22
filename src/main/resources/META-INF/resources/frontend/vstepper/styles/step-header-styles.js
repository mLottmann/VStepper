import styles from './step-header-styles.css'

const $_documentContainer = document.createElement('template');
$_documentContainer.innerHTML = `
  <dom-module id="step-header-styles">
    <template><style>${styles}</style></template>
  </dom-module>`;
document.head.appendChild($_documentContainer.content);