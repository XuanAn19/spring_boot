import { Component } from '@angular/core';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-add-doctor',
  templateUrl: './add-doctor.component.html',
  styleUrls: ['./add-doctor.component.css']
})
export class AddDoctorComponent {

  doctor: any = {}; // Define your user data model here

  specializations : any[] = [];

  ngOnInit(): void {
    this.fetchSpecializations();
  }

  constructor(private adminService : AdminService){

  }

  fetchSpecializations() {
    this.adminService.getAllSpecializations()
      .subscribe(
        response => {
          this.specializations = response;
          console.log('Chuyên ngành:', this.specializations);
          // Handle success, e.g., update UI
        },
        error => {
          console.error('Lỗi khi thêm chuyên môn:', error);
          // Handle error, e.g., show error message
        }
      );
  }

  addDoctor(){
    this.adminService.saveDoctor(this.doctor)
    .subscribe(
      response => {
        console.log('Bác sĩ đã thêm:', response);
        alert("Đã thêm bác sĩ thành công")
        // Handle success, e.g., show success message
      },
      error => {
        console.error('Không thể thêm bác sĩ:', error);
        alert("Lỗi khi thêm bác sĩ ");
        // Handle error, e.g., show error message
      }
    );
  }
}