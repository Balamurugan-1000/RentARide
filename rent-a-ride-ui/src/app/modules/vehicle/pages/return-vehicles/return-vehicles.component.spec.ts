import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnVehiclesComponent } from './return-vehicles.component';

describe('ReturnVehiclesComponent', () => {
  let component: ReturnVehiclesComponent;
  let fixture: ComponentFixture<ReturnVehiclesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReturnVehiclesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReturnVehiclesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
