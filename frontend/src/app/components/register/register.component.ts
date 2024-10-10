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
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.createForm();
    this.subscribeToFormChanges();
  }

  // added validation for form
  createForm(): void {
    this.userData = this.fb.group({
      fname: ['', [Validators.required, noWhitespaceValidator('First name',3,30)]],
      lname: ['', [Validators.required, noWhitespaceValidator('Last name',3,30)]],
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

  /**
   * Creates a new user and saves their data then navigates to login page on success
   */
  onSubmit(): void {
    if(this.userData.valid && this.errorMessage===''){
      const trimmedValues = {
        email: this.userData.value.email.trim().toLowerCase(),
        fname: this.userData.value.fname.trim(),
        lname: this.userData.value.lname.trim(),
        password: this.userData.value.password.trim(),
        username: this.userData.value.username.trim(),
        phone: this.userData.value.phone.trim(),
        role: this.userData.value.role,
      };
    } else{
      this.markAllFieldsAsTouched();
    }
  }
  
  // checking if error in fields and highlight them
  markAllFieldsAsTouched(): void {
    Object.keys(this.userData.controls).forEach(field => {
      const control = this.userData.get(field);
      if (control) {
        control.markAsTouched({ onlySelf: true });
      }
    });
  }

  // execute functionality as soon as change in form details
  subscribeToFormChanges(): void {
    this.userData.get('email')?.valueChanges.subscribe(() => {
      this.errorMessage = '';
    });
  }

}
