import { Component } from '@angular/core';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent {

  admin :any;
  userId:any;
  constructor(private adminService : AdminService){}


  ngOnInit(): void {
    let userStr: any = localStorage.getItem('user');
    if (userStr) {
      let user = JSON.parse(userStr);
      this.userId = user.userId;
    } else {
      console.error('Không thể tìm thấy dữ liệu người dùng.');
    }
    this.getAdminDetails();
  }
  getAdminDetails(): void {
    this.adminService.getAdmin(this.userId).subscribe(
      (data) => {
        this.admin = data;
      },
      (error) => {
        console.error('Lỗi:', error);
        // Handle error as per your application's requirements
      }
    );
  }
}