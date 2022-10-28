import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from './product';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppConfig } from '@config/config.service';
import { fakeProducts } from '@data/fake-products';
import { NGXLogger } from 'ngx-logger';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = AppConfig.settings.apiUrl; // URL to web api

  private productsInCart: Product[] = [];
  private productsInCartBS = new BehaviorSubject<Product[]>(this.productsInCart);

  constructor(private http: HttpClient, private logger: NGXLogger) {}

  getProducts(): Observable<Product[]> {
    if (this.apiUrl === 'mock-api') {
      return new Observable((subscriber) => {
        subscriber.next(fakeProducts);
        subscriber.complete();
      });
    } else {
      const apiCall = this.apiUrl + '/products';
      this.logger.debug('calling GET : ', apiCall);
      /*eslint no-underscore-dangle: ["error", { "allow": ["_embedded"] }]*/
      return this.http.get<Product[]>(apiCall).pipe(map((data: any) => data._embedded.products));
    }
  }

  addProductToCart(product: Product) {
    this.logger.debug('adding product to cart in service : ', product);
    const existingProduct = this.productsInCart.find((p) => p.code === product.code);
    if (existingProduct) {
      existingProduct.quantity++;
    } else {
      const clonedProduct = { ...product };
      clonedProduct.quantity = 1;
      this.productsInCart.push(clonedProduct);
    }
    this.productsInCartBS.next(this.productsInCart);
  }

  removeAllProductFromCart(product: Product) {
    this.productsInCart = this.productsInCart.filter((p) => p.code !== product.code);
    this.productsInCartBS.next(this.productsInCart);
  }
  removeOneProductFromCart(product: Product) {
    const existingProduct = this.productsInCart.find((p) => p.code === product.code);
    existingProduct.quantity--;
    if (existingProduct.quantity === 0) {
      this.removeAllProductFromCart(product);
    } else {
      this.productsInCartBS.next(this.productsInCart);
    }
  }

  getProductsInCart(): Observable<Product[]> {
    return this.productsInCartBS.asObservable();
  }
}
