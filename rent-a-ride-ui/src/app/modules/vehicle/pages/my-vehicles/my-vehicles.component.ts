// import { FindAllVehiclesByOwner } from './../../../../services/fn/vehicle/find-all-vehicles-by-owner';
import {Router, RouterLink} from '@angular/router';
import { VehicleService } from '../../../../services/services/vehicle.service';
import { Component, OnInit } from '@angular/core';
import { PageResponseVehicleResponse, VehicleResponse } from '../../../../services/models';
import { CommonModule } from '@angular/common';
import { VehicleCardComponent } from "../../components/vehicle-card/vehicle-card.component";
@Component({
  selector: 'app-my-vehicles',
  standalone: true,
  imports: [CommonModule, VehicleCardComponent, RouterLink],
  templateUrl: './my-vehicles.component.html',
  styleUrl: './my-vehicles.component.scss'
})
// export class MyVehiclesComponent {
// }
export class MyVehiclesComponent implements OnInit {

  vehicleResponse: PageResponseVehicleResponse = {}
  public page = 0
  public size = 4
  public message: string = ""
  level = ""
  constructor(
    private vehicleService: VehicleService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.findAllVehicles()


  }
  findAllVehicles() {
    this.vehicleService.findAllVehiclesByOwner({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (vehicles) => {
        this.vehicleResponse = vehicles
      }
    })
  }
  goToFirstPage() {
    this.page = 0
    this.findAllVehicles()
  }
  goToLastPage() {
    if (this.vehicleResponse && this.vehicleResponse.totalPages !== undefined) {
      this.page = this.vehicleResponse.totalPages - 1
    }
    this.findAllVehicles()

  }

  goToNextPage() {
    if (this.vehicleResponse && this.vehicleResponse.last) {
      return
    }
    this.page++
    this.findAllVehicles()
  }
  goToPreviousPage() {
    if (this.vehicleResponse && this.vehicleResponse.first) {
      return
    }
    this.page--
    this.findAllVehicles()
  }
  goToPage(pageIndex: number) {
    this.page = pageIndex
    this.findAllVehicles()
  }
  shareVehicle(vehicle: VehicleResponse) {
  }
  archiveVehicle(vehicle: VehicleResponse) {
  }
  editVehicle(vehicle: VehicleResponse) {
this.router.navigate(['vehicles', 'manage' , vehicle.id])

  }

}
