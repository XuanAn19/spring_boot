import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-add-specialization',
  templateUrl: './add-specialization.component.html',
  styleUrls: ['./add-specialization.component.css']
})
export class AddSpecializationComponent implements OnInit{


  specializationName: string;
  specializations: any[] = []; // Danh sách chuyên môn

  
  constructor(private adminService: AdminService, private snack : MatSnackBar) {
    this.specializationName = '';
  }
  ngOnInit(): void {
    this.getAllSpec(); // Lấy danh sách chuyên môn khi component được tải
  }

  getAllSpec(): void {
    this.adminService.getAllSpecializations()
      .subscribe(
        data => {
          this.specializations = data; // Gán dữ liệu vào danh sách
          console.log('Danh sách chuyên môn:', data);
        },
        error => {
          console.error('Lỗi khi tải danh sách chuyên môn:', error);
          this.snack.open("Không thể tải danh sách chuyên môn.", 'Đóng', { verticalPosition: 'top' });
        }
      );
  }

  addSpec() {
    const specialization = { specializationName: this.specializationName };
    this.adminService.addSpecialization(specialization)
      .subscribe(
        response => {
          const snackBarRef = this.snack.open("Thêm chuyên môn thành công..", 'ok', {
            verticalPosition: 'top'
          });
        
          snackBarRef.onAction().subscribe(() => {
            window.location.reload();
          });
          console.log('Thêm chuyên môn mới:', response);
          // Handle success, e.g., show success message
        },
        error => {
          console.error('Lỗi khi thêm chuyên môn:', error);
          const snackBarRef = this.snack.open("Lỗi khi thêm..", 'ok', {
            verticalPosition: 'top'
          });
        
          snackBarRef.onAction().subscribe(() => {
            window.location.reload();
          });
          // Handle error, e.g., show error message
        }
      );
  }
  deleteSpec(id: number): void {
    this.adminService.deleteSpecialization(id)
      .subscribe(
        () => {
          this.snack.open("Xóa chuyên môn thành công.", 'OK', { verticalPosition: 'top' });
          this.getAllSpec(); // Cập nhật danh sách
        },
        error => {
          this.getAllSpec(); // Cập nhật danh sách
        }
      );
  }
}