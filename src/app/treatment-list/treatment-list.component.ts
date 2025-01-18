import { Component, OnInit } from '@angular/core';
import { Appointment, Appointments } from '../components/models/appointment.model';
import { Treatment } from '../components/models/treatment.model';
import { MatTableDataSource } from '@angular/material/table';
import { AppointmentService } from '../service/appointment.service';
import { TreatmentService } from '../treatment.service';

@Component({
  selector: 'app-treatment-list',
  templateUrl: './treatment-list.component.html',
  styleUrls: ['./treatment-list.component.css']
})
export class TreatmentListComponent implements OnInit {
    appointments: Appointment[] = [];
    appointment: Appointments[] = [];
    doctorId = 0;
    treatmentHistory: Treatment[] = [];
    displayedColumns: string[] = [
      'userName',
      'userEmail',
      'appointmentDate',
      'appointmentTime',
      'bookingDate',
      'reason',
      'status',
      'action',
    ];
  
    dataSource = new MatTableDataSource<Appointment>();
  
    selectedAppointment: Appointment | null = null;
    treatment: Treatment = {
      treatmentId: 0,
      diagnosis: '',
      treatmentMethod: '',
      notes: '',
      result: '',
      filePath: '',
      treatmentDate: new Date(),
      appointmentId: 0,
      userId: 0,
    };
  
    // Biến cờ để hiển thị/ẩn form và lịch sử
    showTreatmentForm: boolean = false;
    showTreatmentHistory: boolean = false;
    isEditMode: boolean = false;
  
    constructor(
      private appointmentService: AppointmentService,
      private treatmentService: TreatmentService
    ) {}
  
    ngOnInit(): void {
      const userStr = localStorage.getItem('user');
      if (userStr) {
        const user = JSON.parse(userStr);
        this.doctorId = user.userId;
      } else {
        console.error('Không tìm thấy dữ liệu người dùng trong localStorage.');
      }
      this.getAppointments();
    }
  
    getAppointments(): void {
      this.appointmentService.getAppointmentsByDoctorId(this.doctorId).subscribe(
        (data: Appointments[]) => {
          this.appointments = data;
          this.dataSource.data = this.appointments;
          console.log(data);
        },
        (error) => {
          console.error('Lỗi khi lấy danh sách lịch hẹn:', error);
        }
      );
    }
  
    // Mở form thêm/cập nhật điều trị
    openTreatmentForm(appointment: Appointments): void {
      this.treatmentService.getUserIdByEmail(appointment.user.user.email).subscribe(
        (userId: number) => {
          this.treatment = {
            treatmentId: 0,
            diagnosis: '',
            treatmentMethod: '',
            notes: '',
            result: '',
            filePath: '',
            treatmentDate: new Date(),
            appointmentId: appointment.appointmentId,
            userId: userId,
          };
          this.showTreatmentForm = true;
          this.isEditMode = false;
          this.showTreatmentHistory = false; // Ẩn lịch sử nếu đang hiển thị
        },
        (error) => {
          console.error('Lỗi khi lấy User ID:', error);
          alert('Lỗi khi lấy User ID.');
        }
      );
    }
  
    // Mở lịch sử điều trị
    viewTreatmentHistory(userId: number): void {
      this.treatmentService.getTreatmentsByUserId(userId).subscribe(
        (treatments) => {
          if (treatments.length > 0) {
            this.treatmentHistory = treatments;
            this.showTreatmentHistory = true;
            this.showTreatmentForm = false; // Ẩn form nếu đang hiển thị
          } else {
            alert('Không có lịch sử điều trị nào cho người dùng này.');
          }
        },
        (error) => {
          console.error('Lỗi khi lấy lịch sử điều trị:', error);
        }
      );
    }
  
    // Lưu thông tin điều trị
    saveTreatment(): void {
      if (!this.treatment.treatmentDate) {
        this.treatment.treatmentDate = new Date();
      }
      console.log(this.treatment.treatmentDate);
      if (this.isEditMode) {
        this.treatmentService.updateTreatment(this.treatment.treatmentId, this.treatment).subscribe(
          (data) => {
            alert('Cập nhật điều trị thành công!');
            this.resetForm();
            this.getAppointments(); // Refresh danh sách
          },
          (error) => {
            console.error('Lỗi khi cập nhật điều trị:', error);
            alert('Lỗi khi cập nhật điều trị.');
          }
        );
      } else {
        this.treatmentService.addTreatment(this.treatment).subscribe(
          (data) => {
            console.log('Dữ liệu thêm điều trị:', data);
            alert('Thêm điều trị thành công!');
            this.resetForm();
            this.getAppointments(); // Refresh danh sách
          },
          (error) => {
            console.error('Lỗi khi thêm điều trị:', error);
            alert('Lỗi khi thêm điều trị.');
          }
        );
      }
    }
  
    // Xóa điều trị
    deleteTreatment(treatmentId: number): void {
      if (confirm('Bạn có chắc chắn muốn xóa điều trị này?')) {
        this.treatmentService.deleteTreatment(treatmentId).subscribe(
          (data) => {
            alert('Xóa điều trị thành công!');
            this.getAppointments(); // Refresh danh sách
          },
          (error) => {
            console.error('Lỗi khi xóa điều trị:', error);
            alert('Lỗi khi xóa điều trị.');
          }
        );
      }
    }
  
    // Đóng form và reset dữ liệu
    resetForm(): void {
      this.showTreatmentForm = false;
      this.showTreatmentHistory = false;
      this.isEditMode = false;
      this.treatment = {
        treatmentId: 0,
        diagnosis: '',
        treatmentMethod: '',
        notes: '',
        result: '',
        filePath: '',
        treatmentDate: new Date(),
        appointmentId: 0,
        userId: 0,
      };
    }
  }
