import {Component, OnInit} from '@angular/core';
import {BorrowedVehicleResponse} from '../../../../services/models/borrowed-vehicle-response';
import {PageResponseBorrowedVehicleResponse} from '../../../../services/models/page-response-borrowed-vehicle-response';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {VehicleService} from '../../../../services/services/vehicle.service';
import {PageResponseVehicleResponse} from '../../../../services/models/page-response-vehicle-response';
import {FeedbackRequest} from '../../../../services/models/feedback-request';
import {FormsModule} from '@angular/forms';
import {RatingComponent} from '../../components/rating/rating.component';
import {RouterLink} from '@angular/router';
import {FeedbackService} from '../../../../services/services/feedback.service';

@Component({
  selector: 'app-borrowed-vehicle-list',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NgClass,
    FormsModule,
    RatingComponent,
    RouterLink
  ],
  templateUrl: './borrowed-vehicle-list.component.html',
  styleUrl: './borrowed-vehicle-list.component.scss'
})
export class BorrowedVehicleListComponent  implements OnInit{
  public page: number = 0
  selectedVehicle: BorrowedVehicleResponse | undefined= undefined
  feedbackRequest : FeedbackRequest = {comment: "", vehicleId: 0 , note : 0}



  constructor(
    private vehicleService : VehicleService ,
    private feedbackService : FeedbackService
    ) {
  }

  borrowedVehicles: PageResponseBorrowedVehicleResponse = {
    content: [],
  }

  returnBorrowedVehicle(vehicle: BorrowedVehicleResponse) {
    this.selectedVehicle = vehicle
    this.feedbackRequest.vehicleId = this.selectedVehicle.id as number
     }

  ngOnInit(): void {
      this.findAllBorrowedVehicles();
  }

  private findAllBorrowedVehicles() {
    this.vehicleService.findAllBorrowedVehicles({
      page : this.page,
      size : 10
    }).subscribe(
      {
        next : (borrowedVehicles) => {
          this.borrowedVehicles = borrowedVehicles
        }
      }
    )

  }



  goToFirstPage() {
    this.page = 0
    this.findAllBorrowedVehicles()
  }
  goToLastPage() {
    if (this.borrowedVehicles && this.borrowedVehicles.totalPages !== undefined) {
      this.page = this.borrowedVehicles.totalPages - 1
    }
    this.findAllBorrowedVehicles()

  }

  goToNextPage() {
    if (this.borrowedVehicles && this.borrowedVehicles.last) {
      return
    }
    this.page++
    this.findAllBorrowedVehicles()
  }
  goToPreviousPage() {
    if (this.borrowedVehicles && this.borrowedVehicles.first) {
      return
    }
    this.page--
    this.findAllBorrowedVehicles()
  }
  goToPage(pageIndex: number) {
    this.page = pageIndex
    this.findAllBorrowedVehicles()
  }

  returnVehicle(withFeedback: boolean) {
this.vehicleService.returnVehicle({
  "vehicle-id" : this.selectedVehicle?.id as number,
}).subscribe({
  next : () => {
    if (withFeedback){
      this.giveFeedback();
    }
    this.selectedVehicle = undefined
    this.findAllBorrowedVehicles()
  }
})
  }

  private giveFeedback() {
    this.feedbackService.saveFeedback({
      body : this.feedbackRequest
    }).subscribe({
      next : () => {}
    })
  }
}
