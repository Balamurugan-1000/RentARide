import { CommonModule } from '@angular/common';
import { VehicleResponse } from './../../../../services/models/vehicle-response';
import { Component, Input, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { RatingComponent } from '../rating/rating.component';

@Component({
  selector: 'app-vehicle-card',
  standalone: true,
  imports: [CommonModule, RatingComponent],
  templateUrl: './vehicle-card.component.html',
  styleUrls: ['./vehicle-card.component.scss']
})
export class VehicleCardComponent implements OnInit {
[x: string]: any;

  private _vehicle: VehicleResponse = {};
  private _manage: boolean = false;


  ngOnInit(): void {
    this.isLongDescription = this._vehicle.description ? this._vehicle.description.length > 100 : false;
  }

  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }
  get vehicle(): VehicleResponse {
    return this._vehicle;
  }


  showFullDescription: boolean = false;
  isLongDescription: boolean = false;

  @Input()
  set vehicle(value: VehicleResponse) {
    this._vehicle = value;
  }

  toggleDescription() {
    this.showFullDescription = !this.showFullDescription;
  }

  @Output() private share: EventEmitter<VehicleResponse> = new EventEmitter<VehicleResponse>();
  @Output() private archive: EventEmitter<VehicleResponse> = new EventEmitter<VehicleResponse>();
  @Output() private addToWaitingList: EventEmitter<VehicleResponse> = new EventEmitter<VehicleResponse>();
  @Output() private borrow: EventEmitter<VehicleResponse> = new EventEmitter<VehicleResponse>();
  @Output() private edit: EventEmitter<VehicleResponse> = new EventEmitter<VehicleResponse>();
  @Output() private details: EventEmitter<VehicleResponse> = new EventEmitter<VehicleResponse>();
  onArchive() {
    this.archive.emit(this._vehicle);
  }
  onShare() {
    this.share.emit(this._vehicle);
  }
  onEdit() {
    this.edit.emit(this._vehicle);
  }
  onAddToWaitList() {
    this.addToWaitingList.emit(this._vehicle);
  }
  onBorrow() {
    this.borrow.emit(this._vehicle);
  }
  onShowDetails() {
    this.details.emit(this._vehicle);
  }
}
