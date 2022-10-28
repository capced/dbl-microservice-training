import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NGXLogger } from 'ngx-logger';
import { Product } from '@shared/services/product/product';
import { ProductService } from '@shared/services/product/product.service';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
})
export class ProductCardComponent implements OnInit {
  @Input() product: Product;

  constructor(private logger: NGXLogger, private productService: ProductService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {}
  addProductToCart() {
    this.logger.debug('adding product to cart from component : ', this.product);
    this.productService.addProductToCart(this.product);
    this.snackBar.open(`Succesfully added ${this.product.name} to shopping cart`, 'close', { duration: 3000 });
  }
}
