import {Component, OnInit} from '@angular/core';
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {BorrowedVehicleResponse} from '../../../../services/models/borrowed-vehicle-response';
import {VehicleService} from '../../../../services/services/vehicle.service';
import {PageResponseBorrowedVehicleResponse} from '../../../../services/models/page-response-borrowed-vehicle-response';

@Component({
  selector: 'app-return-vehicles',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgClass
  ],
  templateUrl: './return-vehicles.component.html',
  styleUrl: './return-vehicles.component.scss'
})
export class ReturnVehiclesComponent implements OnInit{
  public page: number = 0
  selectedVehicle: BorrowedVehicleResponse | undefined= undefined



  constructor(
    private vehicleService : VehicleService
  ) {
  }

  returnedVehicles: PageResponseBorrowedVehicleResponse = {
    content: [],
  }
  level: string = "success";
  message = "";


  ngOnInit(): void {
    this.findAllReturnedVehicles();
  }

  private findAllReturnedVehicles() {
    this.vehicleService.findAllReturnedVehicles({
      page : this.page,
      size : 10
    }).subscribe(
      {
        next : (returnedVehicles) => {
          this.returnedVehicles = returnedVehicles
        }
      }
    )

  }



  goToFirstPage() {
    this.page = 0
    this.findAllReturnedVehicles()
  }
  goToLastPage() {
    if (this.returnedVehicles && this.returnedVehicles.totalPages !== undefined) {
      this.page = this.returnedVehicles.totalPages - 1
    }
    this.findAllReturnedVehicles()

  }

  goToNextPage() {
    if (this.returnedVehicles && this.returnedVehicles.last) {
      return
    }
    this.page++
    this.findAllReturnedVehicles()
  }
  goToPreviousPage() {
    if (this.returnedVehicles && this.returnedVehicles.first) {
      return
    }
    this.page--
    this.findAllReturnedVehicles()
  }
  goToPage(pageIndex: number) {
    this.page = pageIndex
    this.findAllReturnedVehicles()
  }


  approveVehicleReturn(vehicle: BorrowedVehicleResponse) {
    if (vehicle.returnApproved) {
      return;
    }
    if (!vehicle.returned) {
      this.level = "error"
      this.message = "Vehicle is return yet.."
      return
    }
    const resp = confirm("Approve return?")
    if (!resp) {
      return;
    }
    this.vehicleService.approveReturnVehicle({
      "vehicle-id" : vehicle.id as number
    }).subscribe({
      next : () => {
        vehicle.returnApproved = true
        this.level = "success"
        this.message = "Vehicle returned successfully"
        this.findAllReturnedVehicles()
      } , error : (error) => {
        console.log(error)
        this.level = "error"
        this.message = "Vehicle return failed"
      }
    })

  }
}
