import { MyVehiclesComponent } from './pages/my-vehicles/my-vehicles.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { VehicleRoutingModule } from './vehicle-routing.module';
import { MainComponent } from './pages/main/main.component';
import { VehicleListComponent } from './pages/vehicle-list/vehicle-list.component';
import { ManageVehicleComponent } from './pages/manage-vehicle/manage-vehicle.component';
import {BorrowedVehicleListComponent} from './pages/borrowed-vehicle-list/borrowed-vehicle-list.component';
import {ReturnVehiclesComponent} from './pages/return-vehicles/return-vehicles.component';
import {authGuard} from '../../guards/auth.guard';

const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    canActivate : [authGuard],
    children: [
      {
        path: "",
        canActivate : [authGuard],
        component : VehicleListComponent
      }, {
        path: "my-vehicles",
        canActivate : [authGuard],

        component: MyVehiclesComponent
      },
      {
        path: "manage",
        canActivate : [authGuard],

        component: ManageVehicleComponent
      },
      {
        path: "manage/:vehicleId",
        canActivate : [authGuard],

        component: ManageVehicleComponent
      },
      {
        path: 'my-borrowed-vehicles',
        canActivate : [authGuard],

        component: BorrowedVehicleListComponent
      },{
        path : 'my-returned-vehicles',
        canActivate : [authGuard],

        component: ReturnVehiclesComponent

      }
    ]
  }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    VehicleRoutingModule
  ],
  exports: [RouterModule]
})
export class VehicleModule { }
