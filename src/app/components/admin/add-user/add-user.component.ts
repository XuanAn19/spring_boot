import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent  implements OnInit {
  users: any[] = [];
  selectedUser: any = null;
  newUser: any = {};
  

  constructor(private admin: AdminService) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.admin.getAllUsers().subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  // createUser(): void {
  //   this.admin.createNormalUser(this.newUser).subscribe(
  //     (response) => {
  //       console.log('User created successfully:', response);
  //       this.fetchUsers(); // Reload users after creating a new user
  //       this.resetForm(); // Reset the form fields
  //     },
  //     (error) => {
  //       console.error('Error creating user:', error);
  //     }
  //   );
  // }

  // resetForm(): void {
  //   this.newUser = { firstName: '', lastName: '', email: '', password: '', role: 'NORMAL' }; // Reset form values
  // }

  // // Update user
  // updateUser(): void {
  //   if (this.selectedUser) {
  //     this.admin.updateUser(this.selectedUser.email, this.selectedUser).subscribe(
  //       (response) => {
  //         this.fetchUsers(); // Refresh the list after updating
  //         alert('User updated successfully!');
  //       },
  //       (error) => {
  //         console.error('Error updating user:', error);
  //       }
  //     );
  //   }
  // }

  // // Delete user
  deleteUser(userId: number): void {
    this.admin.deleteNormalUser(userId).subscribe(
      (response) => {
        this.fetchUsers(); // Refresh the list after deletion
        alert('User deleted successfully!');
      },
      (error) => {
        console.error('Error deleting user:', error);
        this.fetchUsers();
      }
    );
  }

  // // Select user for editing
  // selectUser(user: any): void {
  //   this.selectedUser = { ...user }; // Clone to prevent direct modification
  // }

  // // Reset selection
  // resetSelection(): void {
  //   this.selectedUser = null;
  // }
}
