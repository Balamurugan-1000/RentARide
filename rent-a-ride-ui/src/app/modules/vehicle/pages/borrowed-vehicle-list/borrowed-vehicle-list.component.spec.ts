import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrowedVehicleListComponent } from './borrowed-vehicle-list.component';

describe('BorrowedVehicleListComponent', () => {
  let component: BorrowedVehicleListComponent;
  let fixture: ComponentFixture<BorrowedVehicleListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BorrowedVehicleListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BorrowedVehicleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
