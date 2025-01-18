import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginService } from './service/login.service';
import { Observable } from 'rxjs/internal/Observable';
import { Treatment } from './components/models/treatment.model';

@Injectable({
  providedIn: 'root'

})
export class TreatmentService {
 private apiUrl = 'http://localhost:8181/treatment'; // URL của backend

  constructor(private http: HttpClient, private loginService : LoginService) {}

  // Thêm điều trị
  addTreatment(treatment: Treatment): Observable<Treatment> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.post<Treatment>(`${this.apiUrl}/add`, treatment, {headers});
  }


  // Lấy danh sách điều trị theo ID bệnh nhân
  getTreatmentsByUserId(userId: number): Observable<Treatment[]> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<Treatment[]>(`${this.apiUrl}/getByUserId/${userId}`, {headers});
  }

  updateTreatment(treatmentId: number, treatment: Treatment): Observable<Treatment> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.put<Treatment>(`${this.apiUrl}/update/${treatmentId}`, treatment, {headers});
  }

  deleteTreatment(treatmentId: number): Observable<void> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.delete<void>(`${this.apiUrl}/delete/${treatmentId}`, {headers});
  }


  getUserIdByEmail(email: string) {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<number>(`${this.apiUrl}/getUserIdByEmail?email=${email}`, {headers});
  }

  // Lấy thông tin điều trị theo ID cuộc hẹn
  getTreatmentByAppointmentId(appointmentId: number): Observable<Treatment> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<Treatment>(`${this.apiUrl}/getByAppointmentId/${appointmentId}`, {headers});
  }
}

