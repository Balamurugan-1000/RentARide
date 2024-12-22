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
  vehicleRequest: VehicleRequest = { carModel: "", description: "", licensePlate: "", ownerName: "", phone: "" };

  constructor(
    private vehicleService: VehicleService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const vehicleId = this.activatedRoute.snapshot.paramMap.get('vehicleId');
    this.vehicleService.findVehicleById({
      'vehicle-id': vehicleId as unknown as number
    }).subscribe({
      next: vehicle => {
        this.vehicleRequest = {
          id: vehicle.id,
          carModel: vehicle.carModel as string,
          description: vehicle.description as string,
          licensePlate: vehicle.licensePlate as string,
          ownerName: vehicle.ownerName as string,
          phone: vehicle.phone as string,
          shareable: vehicle.shareable,
          cover: vehicle.cover,

          price : vehicle.price
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

  // Fallback for vehicle cover image if no image is selected
  vehicleCover = this.selectedVehicleImage || this.vehicleRequest.cover || '/No_IMAGE.jpeg';

  // Handle file selection
  onFileSelected(event: any) {
    this.selectedVehicleImage = event.target.files[0];
    if (this.selectedVehicleImage) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;  // Store the preview image
      };
      reader.readAsDataURL(this.selectedVehicleImage);
    }
  }

  // Save vehicle details
  async saveVehicle() {
    let imageUrl: string | null = null;

    // Check if there is an image to upload
    if (this.selectedVehicleImage) {
      const filePath = `vehicles/${Date.now()}-${this.selectedVehicleImage.name}`;

      try {
        // Upload image to Supabase storage
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

        // Update vehicle request with the image URL
        this.vehicleRequest.cover = imageUrl;

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
          cover: '',
          carModel: '',
          description: '',
          id: 0,
          licensePlate: '',
          ownerName: '',
          phone: '',
          price: '',
          shareable: false
        }
        console.log('Vehicle saved successfully:', response);
        this.router.navigate(["vehicles" , 'my-vehicles']);
      },
      error: err => {
        console.error('Error saving vehicle:', err);
        this.errorMsg = ['Error saving vehicle data.'];
      }
    });
  }
}
