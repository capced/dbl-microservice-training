import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { MatDialogRef } from '@angular/material/dialog';
import { Product } from '@shared/services/product/product';
import { ProductService } from '@shared/services/product/product.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ShoppingCartComponent implements OnInit {
  productsInCart: Product[] = [];

  constructor(
    private logger: NGXLogger,
    private productService: ProductService,
    public dialogRef: MatDialogRef<ShoppingCartComponent>
  ) {}

  ngOnInit(): void {
    this.productService.getProductsInCart().subscribe((productsInCart) => {
      this.productsInCart = productsInCart;
    });
  }

  continueToShipping() {
    this.logger.info('redirect to shipping component');
    this.dialogRef.close();
  }

  increaseProduct(product: Product) {
    this.productService.addProductToCart(product);
  }
  removeOneProduct(product: Product) {
    this.productService.removeOneProductFromCart(product);
  }
}
