import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { ProductService } from '@shared/services/product/product.service';
import { MockProvider } from 'ng-mocks';
import { LoggerTestingModule } from 'ngx-logger/testing';
import { of } from 'rxjs';

import { ShoppingCartComponent } from './shopping-cart.component';

describe('ShoppingCartComponent', () => {
  let component: ShoppingCartComponent;
  let fixture: ComponentFixture<ShoppingCartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ShoppingCartComponent],

      imports: [LoggerTestingModule],
      providers: [
        MockProvider(ProductService, {
          getProductsInCart: () => of([]),
        }),
        MockProvider(MatDialogRef),
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShoppingCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
