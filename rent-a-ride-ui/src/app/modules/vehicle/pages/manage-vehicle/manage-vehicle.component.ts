import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VehicleRequest } from '../../../../services/models/vehicle-request';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { VehicleService } from '../../../../services/services/vehicle.service';
// @ts-ignore
import { supabase } from '../../../../myServices/supabase-client.ts';

@Component({
  selector: 'app-manage-vehicle',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './manage-vehicle.component.html',
  styleUrls: ['./manage-vehicle.component.scss']
})
export class ManageVehicleComponent implements OnInit {

  errorMsg: Array<string> = [];
  selectedPicture: string = "";  // To hold the selected image preview
  selectedVehicleImage: File | null = null;  // The actual selected file
  vehicleRequest: VehicleRequest = {
    id: 0,
    carModel: "",
    description: "",
    licensePlate: "",
    ownerName: "",
    phone: "",
    price: "",
    shareable: false,
    cover: ""
  };

  constructor(
    private vehicleService: VehicleService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const vehicleId = this.activatedRoute.snapshot.paramMap.get('vehicleId');
    if (vehicleId) {
      this.vehicleService.findVehicleById({
        'vehicle-id': +vehicleId
      }).subscribe({
        next: vehicle => {
          this.vehicleRequest = {
            id: vehicle.id,
            carModel: vehicle.carModel || '',
            description: vehicle.description || '',
            licensePlate: vehicle.licensePlate || '',
            ownerName: vehicle.ownerName || '',
            phone: vehicle.phone || '',
            price: vehicle.price || '',
            shareable: vehicle.shareable || false,
            cover: vehicle.cover || ''
          };
          if (vehicle.cover) {
            this.selectedPicture = vehicle.cover;  // Initialize with the existing vehicle cover
          }
        },
        error: err => {
          console.error('Error fetching vehicle:', err);
          this.errorMsg = ['Error fetching vehicle data.'];
        }
      });
    }
  }

  // Handle file selection for vehicle cover image
  onFileSelected(event: any): void {
    this.selectedVehicleImage = event.target.files[0];
    if (this.selectedVehicleImage) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;  // Set image preview
      };
      reader.readAsDataURL(this.selectedVehicleImage);
    }
  }

  // Save vehicle details
  async saveVehicle(): Promise<void> {
    let imageUrl: string | null = null;

    // Upload image if selected
    if (this.selectedVehicleImage) {
      const filePath = `vehicles/${Date.now()}-${this.selectedVehicleImage.name}`;
      try {
        const { data, error } = await supabase.storage.from('vehicle-images').upload(filePath, this.selectedVehicleImage);
        if (error) {
          console.error('Supabase upload error:', error.message);
          this.errorMsg = ['Error uploading vehicle image to Supabase.'];
          return;
        }

        // Get the public URL of the uploaded image
        const { data: publicUrlData } = supabase.storage.from('vehicle-images').getPublicUrl(filePath);
        imageUrl = publicUrlData.publicUrl;
        console.log('Uploaded Image URL:', imageUrl);
        this.vehicleRequest.cover = imageUrl;  // Update cover URL
      } catch (err) {
        console.error('Error during file upload:', err);
        this.errorMsg = ['Error uploading image.'];
        return;
      }
    }

    // Send the vehicle request data
    this.vehicleService.saveVehicle({
      body: this.vehicleRequest
    }).subscribe({
      next: response => {
        this.vehicleRequest = {
          id: 0,
          cover: '',
          carModel: '',
          description: '',
          licensePlate: '',
          ownerName: '',
          phone: '',
          price: '',
          shareable: false
        }
        console.log('Vehicle saved successfully:', response);
        this.router.navigate(["vehicles", 'my-vehicles']);
      },
      error: err => {
        console.error('Error saving vehicle:', err);
        this.errorMsg = ['Error saving vehicle data.'];
      }
    });
  }
}
