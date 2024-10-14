import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { CommonModule } from '@angular/common';
import { customEmailValidator } from '../../validators/email-validator';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user/user.service';
@Component({
  selector: 'app-resetPassword',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterLink,CommonModule],
  templateUrl: './resetPassword.component.html',
  styleUrl: './resetPassword.component.css'
})
export class ResetPasswordComponent {
  resetStatus:boolean=false;
  forgotPasswordForm!: FormGroup;
  errorMessage:string='';
  userEmail:string='';
  constructor(
    private fb : FormBuilder,
    private userService: UserService,
    private router: Router,
  ) {}

  closeModal() {
    this.forgotPasswordForm.reset();
  }

  ngOnInit(){
    this.createForm();
    
  }

  createForm(){
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, customEmailValidator()]]
    });
  }

  forgotPassword() {
    const trimmedEmail = this.forgotPasswordForm.value.email.trim();
      // this.userService.forgotPassword({email : trimmedEmail}).subscribe({
      //   next: (res: any) => {
      //     this.router.navigate(['/forget-password']);
      //   },
      //   error: (error) => {
      //     this.errorMessage = error.error.message;
      //     this.resetStatus = false;
    //     }
    //   });
    // }
  }

  markAllFieldsAsTouched(): void {
    Object.keys(this.forgotPasswordForm.controls).forEach(field => {
      const control = this.forgotPasswordForm.get(field);
      if (control) {
        control.markAsTouched({ onlySelf: true });
      }
    });
  }

  redirectToLogin(){
    this.router.navigateByUrl('/login');
  }

  subscribeToFormChanges(): void {
    this.forgotPasswordForm.get('email')?.valueChanges.subscribe(() => {
      this.errorMessage = '';
    });
  }

}

