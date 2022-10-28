import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { AppConfig } from '@config/config.service';
import { MockProvider } from 'ng-mocks';
import { LoggerTestingModule } from 'ngx-logger/testing';

import { ProductService } from './product.service';

describe('ProductService', () => {
  let service: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LoggerTestingModule],
      providers: [MockProvider(HttpClient)],
    });
    AppConfig.settings = { apiUrl: 'test' } as any;
    service = TestBed.inject(ProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
