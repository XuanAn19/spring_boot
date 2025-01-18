import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AppointmentService } from 'src/app/service/appointment.service';
import { Appointment } from '../../models/appointment.model';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-doctor-appointments',
  templateUrl: './doctor-appointments.component.html',
  styleUrls: ['./doctor-appointments.component.css']
})
export class DoctorAppointmentsComponent {

  appointments: Appointment[] = [];
  doctorId = 0;
  displayedColumns: string[] = ['userName', 'userEmail', 'appointmentDate', 'appointmentTime', 'bookingDate', 'reason', 'status'];
  dataSource = new MatTableDataSource<Appointment>();

  constructor(private appointmentService: AppointmentService) { }

 @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
   // To set user_id
   let userStr: any = localStorage.getItem('user');
   if (userStr) {
     let user = JSON.parse(userStr);
     this.doctorId = user.userId;
   } else {
     console.error('Dữ liệu người dùng không tìm thấy trong localStorage.');
   }
   this.getAppointments();
  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  getAppointments(): void {
    this.appointmentService.getAppointmentsByDoctorId(this.doctorId).subscribe(
      (data: Appointment[]) => {
        this.appointments = data;
        this.dataSource.data = this.appointments;
      },
      (error) => {
        console.error(error);
      }
    );
  }
  
  

  updateStatus(appointmentId:any, status: any): void {
    this.appointmentService.updateStatus(appointmentId, status).subscribe(
      (data) => {
        console.log('Cập nhật tình trạng cuộc hẹn:', data);
        
        window.location.reload();
        alert("Trạng thái đã được cập nhật.")
        // Perform any additional actions upon success
      },
      (error) => {
        console.error('Lỗi cập nhật cuộc hẹn:', error);
        alert("Lỗi cập nhật cuộc hẹn");
        // Handle error scenarios
      }
    );
  }


  
}
