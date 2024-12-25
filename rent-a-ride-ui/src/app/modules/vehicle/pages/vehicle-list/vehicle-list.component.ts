import { Router } from '@angular/router';
import { VehicleService } from './../../../../services/services/vehicle.service';
import { Component, OnInit } from '@angular/core';
import { PageResponseVehicleResponse, VehicleResponse } from '../../../../services/models';
import { CommonModule } from '@angular/common';
import { VehicleCardComponent } from "../../components/vehicle-card/vehicle-card.component";

@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  imports: [CommonModule, VehicleCardComponent],
  templateUrl: './vehicle-list.component.html',
  styleUrl: './vehicle-list.component.scss'
})
export class VehicleListComponent implements OnInit {



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
    this.vehicleService.findAllVehicles({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (vehicles) => {
        this.vehicleResponse = vehicles
        console.log(vehicles)
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

  borrowVehicle(vehicle: VehicleResponse) {
    this.vehicleService.borrowVehicle({
      "vehicle-id": vehicle.id as number
    }).subscribe({
      next: (response) => {
        this.level = "success"
        this.message = "Vehicle borrowed successfully"
        this.findAllVehicles()
      },
      error: (error) => {
        console.log(error.error.error)
        this.level = "error"
        this.message = error.error.error
      }
    })
  }
}
