import { FeatureToggleDirective } from './feature.directive';
import { AppConfig } from '@config/config.service';
import { By } from '@angular/platform-browser';
import { MockBuilder, MockRender } from 'ng-mocks';

describe('TestStructuralDirectiveWithoutContext', () => {
  beforeEach(() => MockBuilder(FeatureToggleDirective));

  it('should show h1 #1 and hide h2 #2 and #3', () => {
    AppConfig.settings = {
      apiUrl: 'test',
      env: { name: 'test' },
      loggerConfig: null,
      features: { featureOne: true, featureTwo: false },
    };
    const fixture = MockRender(
      `
    <h2 *featureToggle="'featureOne'">Something activated</h2>
    <h2 *featureToggle>The Default</h2>
    <h2 *featureToggle="'featureTwo'">Something not activated</h2>`,
      {}
    );
    const des = fixture.debugElement.queryAll(By.css('h2'));
    expect(des.length).toBe(1);
    const content = des[0].nativeElement.innerHTML;
    expect(content).toBe('Something activated');
  });

  it('should show all h2 when all operator is used', () => {
    AppConfig.settings = { apiUrl: 'test', env: { name: 'test' }, loggerConfig: null, features: { all: true } };

    const fixture = MockRender(
      `
    <h2 *featureToggle="'featureOne'">Something activated</h2>
    <h2 *featureToggle>The Default</h2>
    <h2 *featureToggle="'featureTwo'">Something not activated</h2>`,
      {}
    );
    const des = fixture.debugElement.queryAll(By.css('h2'));
    expect(des.length).toBe(3);
    let content = des[0].nativeElement.innerHTML;
    expect(content).toBe('Something activated');
    content = des[1].nativeElement.innerHTML;
    expect(content).toBe('The Default');
    content = des[2].nativeElement.innerHTML;
    expect(content).toBe('Something not activated');
  });
});
