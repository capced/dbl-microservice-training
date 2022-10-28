import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '@modules/example/home/home.component';
import { ListComponent } from '@modules/example/list/list.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
