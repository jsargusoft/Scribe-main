import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { customEmailValidator } from '../../validators/email-validator';
import { noWhitespaceValidator } from '../../validators/no-whitespace-validator';
import { UserService } from '../../services/user/user.service';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})

export class RegisterComponent {
  userData!: FormGroup;
  passwordFieldType: string = 'password';
  confirmPasswordFieldType: string = 'password';
  errorMessage:string='';

  constructor(
    private fb: FormBuilder,
    private router : Router,
    private userService : UserService
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.subscribeToFormChanges();
  }

  // added validation for form
  createForm(): void {
    this.userData = this.fb.group({
      firstName: ['', [Validators.required, noWhitespaceValidator('First name',3,30)]],
      lastName: ['', [Validators.required, noWhitespaceValidator('Last name',3,30)]],
      email: ['', [Validators.required, customEmailValidator()]],
      username: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      role: ['', [Validators.required]],
      password: [
        '',
        [Validators.required,noWhitespaceValidator('Password',4,20)],
      ]
    });
  }

  // toggle password visibility
  togglePasswordVisibility(): void {
    this.passwordFieldType =
      this.passwordFieldType === 'password' ? 'text' : 'password';
  }

  onSubmit(): void {

    console.log(this.userData);
    
    this.userService.addUser(this.userData.value).subscribe({
      next: (response : any) => {
        console.log("User registered")
        console.log(response);
        
        this.router.navigate(['/login'])
      },
      error: (error) => {
        console.log('Registration error', error);
        this.errorMessage = error.error.message || 'Registration failed'; 
      },
    })
  }
  
  markAllFieldsAsTouched(): void {
    Object.keys(this.userData.controls).forEach(field => {
      const control = this.userData.get(field);
      if (control) {
        control.markAsTouched({ onlySelf: true });
      }
    });
  }

  subscribeToFormChanges(): void {
    this.userData.get('email')?.valueChanges.subscribe(() => {
      this.errorMessage = '';
    });
  }

}
