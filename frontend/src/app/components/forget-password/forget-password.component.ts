import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { noWhitespaceValidator } from '../../validators/no-whitespace-validator';

@Component({
  selector: 'app-forget-password',
  standalone: true,
  imports: [FormsModule, RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.css'
})
export class ForgetPasswordComponent {


  forgotPasswordForm!: FormGroup;
  token: string | null = null;
  passwordVisible: boolean = false;
  confirmPasswordVisible: boolean = false;
  errorMessage:string='';
  
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token');
    this.createForm();
  }

  createForm(): void {
    this.forgotPasswordForm = this.fb.group({
      token: [this.token],
      password: ['', [Validators.required,noWhitespaceValidator('Password',4,20)],],
      confirmpassword: ['', Validators.required],
    });
  }

  /**
   * Checks whether the passwords match or not.
   * @returns boolean
   */
  doPasswordsMatch(): boolean {
      const password = this.forgotPasswordForm.get('password')?.value.trim();
      const confirmpassword = this.forgotPasswordForm.get('confirmpassword')?.value.trim();
      return password === confirmpassword;
    
  }
  togglePasswordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }

  toggleConfirmPasswordVisibility(): void {
    this.confirmPasswordVisible = !this.confirmPasswordVisible;
  }

  changePassword() {
    console.log("Password changed");
    
  }

}
