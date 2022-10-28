import { INGXLoggerConfig } from 'ngx-logger';

export interface IAppConfig {
  apiUrl: string;
  env: {
    name: string;
  };
  loggerConfig: INGXLoggerConfig;
  features: any;
}
