import { Directive, Input, TemplateRef, ViewContainerRef, OnInit } from '@angular/core';
import { AppConfig } from '@config/config.service';

@Directive({
  selector: '[featureToggle]', // eslint-disable-line @angular-eslint/directive-selector
})
export class FeatureToggleDirective implements OnInit {
  @Input() featureToggle: string;

  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef) {}

  ngOnInit() {
    if (this.isEnabled()) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }

  isEnabled() {
    const { features } = AppConfig.settings;
    if (features.all) {
      return true;
    }

    return features[this.featureToggle];
  }
}
