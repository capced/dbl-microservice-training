import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ShoppingCartComponent } from '@modules/example/shopping-cart/shopping-cart.component';
import { ProductService } from '@shared/services/product/product.service';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  nbArticles = 0;
  constructor(public dialog: MatDialog, private productService: ProductService, private logger: NGXLogger) {}

  ngOnInit(): void {
    this.productService.getProductsInCart().subscribe((productsInCart) => {
      this.logger.debug('updating articles count : ', productsInCart.length);
      this.nbArticles = productsInCart.length;
    });
  }

  openShoppingCart(): void {
    const dialogRef = this.dialog.open(ShoppingCartComponent);
  }
}
