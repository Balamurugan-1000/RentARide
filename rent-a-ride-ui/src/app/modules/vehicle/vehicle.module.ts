import { MyVehiclesComponent } from './pages/my-vehicles/my-vehicles.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { VehicleRoutingModule } from './vehicle-routing.module';
import { MainComponent } from './pages/main/main.component';
import { VehicleListComponent } from './pages/vehicle-list/vehicle-list.component';
import { ManageVehicleComponent } from './pages/manage-vehicle/manage-vehicle.component';

const routes: Routes = [
  {
    path: "",
    component: MainComponent,
    children: [
      {
        path: "",
        component: VehicleListComponent
      }, {
        path: "my-vehicles",
        component: MyVehiclesComponent
      },
      {
        path: "manage",
        component: ManageVehicleComponent
      },
      {
        path: "manage/:vehicleId",
        component: ManageVehicleComponent
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
