import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { ToastModule } from 'primeng/toast'; // Import ToastModule
import { MessageService } from "primeng/api";
import { LoginResponseModule } from '../../model/login-response.model';
import { User } from '../../model/user.model';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, ToastModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  providers: [MessageService]
})

export class DashboardComponent implements OnInit {
  name!: string;
  user!: User;
  loginResponse!: LoginResponseModule;

  constructor(
    private userService: UserService,
    // private messageService:MessageService,
    private router: Router
  ) { }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe({
      next: (response:any) => {
      this.user = response;
      console.log(this.user);
      
      this.name = this.user.firstName + " " + this.user.lastName;
    },
      error:(error) => {
        console.error('Error fetching user details', error);
      }});
  }

  logout() {
    this.userService.logoutUser().subscribe({
      next: (response: any) => {
        this.loginResponse = response;
        console.log("logged out");
        this.router.navigate(['/login']);

      },
      error: (error) => {
        console.error('Error logging out', error);
      },
    });
  }
}