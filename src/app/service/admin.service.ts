import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8181/'; // Spring Boot URL


  constructor(private http: HttpClient, private loginService : LoginService) { }

  getAdmin(userId:any): Observable<any> {
    const url = `${this.baseUrl}admin/getAdmin/${userId}`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<any>(url, {headers});
  }

  addSpecialization(spec: any): Observable<any> {
    const url = `${this.baseUrl}admin/addSpec`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.post<any>(url, spec, {headers});
  }

  getAllSpecializations(): Observable<any> {
    const url = `${this.baseUrl}admin/getAllSpec`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<any>(url, {headers});
  }
  deleteSpecialization(id: number): Observable<any> {
    const url = `${this.baseUrl}admin/deleteSpec/${id}`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.delete<any>(url, { headers });
  }

  saveDoctor(doctorDto: any): Observable<any> {
    const url = `${this.baseUrl}admin/saveDoctor`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.post<any>(url, doctorDto, {headers});
  }

   // Update Admin
   updateAdmin(adminId: any, adminDto: any): Observable<any> {
    const url = `${this.baseUrl}admin/updateAdmin/${adminId}`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.put<any>(url, adminDto, { headers });
  }

  // Delete Admin
  deleteAdmin(adminId: any): Observable<any> {
    const url = `${this.baseUrl}admin/deleteAdmin/${adminId}`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.delete<any>(url, { headers });
  }

  getAllDoctors(): Observable<any> {
    const url = `${this.baseUrl}admin/getAllDoctors`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<any>(url, { headers });
  }

 // Get doctor by ID
 getDoctorById(doctorId: number): Observable<any> {
  const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
  return this.http.get<any>(`${this.baseUrl}admin/doctors/${doctorId}`, { headers });
}
  // Update Doctor
  updateDoctor(doctorId: any, doctorDto: any): Observable<any> {
    const url = `${this.baseUrl}admin/updateDoctor/${doctorId}`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.put<any>(url, doctorDto, { headers });
  }

  // Delete Doctor
  deleteDoctor(doctorId: any): Observable<any> {
    const url = `${this.baseUrl}admin/deleteDoctor/${doctorId}`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.delete<any>(url, { headers });
  }

  // Create Normal User
  createNormalUser(normalUserDto: any): Observable<any> {
    const url = `${this.baseUrl}admin/create`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.post<any>(url, normalUserDto, { headers });
  }

 // Xóa người dùng
 deleteNormalUser(userId: number): Observable<any> {
  const url = `${this.baseUrl}admin/user/delete/${userId}`; // Cập nhật URL API
  const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
  return this.http.delete<any>(url, { headers });
}
// Get all users
getAllUsers(): Observable<any> {
  const url = `${this.baseUrl}admin/users`;
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<any>(url, { headers });
}

  getUserByEmail(email: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.get<any>(`${this.baseUrl}/user/update/${email}`, { headers });
  }

  // Cập nhật thông tin người dùng
  updateUser(email: string, user: any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + this.loginService.getToken());
    return this.http.put<any>(`${this.baseUrl}/user/update/${email}`, user, { headers });
  }
}
