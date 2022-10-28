import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MarkdownModule } from 'ngx-markdown';
import { SecurityContext } from '@angular/core';
import * as shared from './';

@NgModule({
  declarations: [...shared.components, ...shared.directives],
  imports: [
    BrowserModule,
    MarkdownModule.forRoot({
      sanitize: SecurityContext.NONE,
    }),
  ],
  exports: [...shared.components, ...shared.directives],
})
export class SharedModule {}
