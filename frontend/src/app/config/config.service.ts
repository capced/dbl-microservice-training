import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@env/environment';
import { IAppConfig } from './config.model';
import { NGXLogger } from 'ngx-logger';
@Injectable()
export class AppConfig {
  static settings: IAppConfig;
  constructor(private http: HttpClient, private logger: NGXLogger) {}
  load() {
    const jsonFile = `assets/config/config.${environment.name}.json`;
    return new Promise<void>((resolve, reject) => {
      this.http
        .get(jsonFile)
        .toPromise()
        .then((config: IAppConfig) => {
          AppConfig.settings = config as IAppConfig;
          this.logger.updateConfig(config.loggerConfig);
          this.logger.debug('Configuration loaded : ', { config });
          resolve();
        })
        .catch((response: any) => {
          reject(`Could not load file '${jsonFile}': ${JSON.stringify(response)}`);
        });
    });
  }
}
