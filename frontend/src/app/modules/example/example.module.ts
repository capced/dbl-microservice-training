import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { components } from './';
import { SharedModule } from '@shared/shared.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatListModule } from '@angular/material/list';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [components],
  imports: [
    BrowserModule,
    SharedModule,

    MatToolbarModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatDialogModule,
    MatSnackBarModule,
    MatListModule,
    MatIconModule,
    MatInputModule,

    AppRoutingModule,
  ],
  exports: [components],
})
export class ExampleModule {}
