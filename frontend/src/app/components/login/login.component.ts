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
import { noWhitespaceValidator } from '../../validators/no-whitespace-validator';
import { customEmailValidator } from '../../validators/email-validator';
import { UserService } from '../../services/user/user.service';
import { MessageService } from "primeng/api";
import { PrimeNGConfig } from 'primeng/api';
import { ToastModule } from 'primeng/toast'; 
import {RippleModule} from 'primeng/ripple';
import { LoginResponseModule } from '../../model/login-response.model';
import { LoginRequest } from '../../model/login-request.model';
// import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    ReactiveFormsModule,
    CommonModule,
    ToastModule,
    RippleModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  providers: [MessageService]
})
export class LoginComponent {
  loginForm!: FormGroup;
  passwordFieldType: string = 'password';
  incorrect: boolean = false
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig
    // private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.createForm();
    this.subscribeToFormChanges();
    this.loginForm = this.formBuilder.group({
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[a-z0-9]+@[a-z]+\.[a-z]{2,3}$/),
        ],
      ],
      password: ['', Validators.required],
    });
    this.primengConfig.ripple = true;
  }

  // added validation for form
  createForm(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, customEmailValidator()]],
      password: ['', [Validators.required, noWhitespaceValidator('Password', null, null)]],
    });
  }

  subscribeToFormChanges(): void {
    this.loginForm.valueChanges.subscribe(() => {
      this.incorrect = false;
      this.errorMessage = '';
    });
  }

  // toggle password visibility
  togglePasswordVisibility(): void {
    this.passwordFieldType =
      this.passwordFieldType === 'password' ? 'text' : 'password';
  }


  login(): void {

    if (this.loginForm.valid) {
      const loginRequest: LoginRequest = {
          email: this.loginForm.value.email.trim(),
          password: this.loginForm.value.password.trim()
      };

      this.userService.loginUser(loginRequest).subscribe({
          next: (response: LoginResponseModule) => {
              if (response.isLogged) {
                  this.router.navigate(['/dashboard']);
                  console.log("Login Successful")
              } else {
                  this.errorMessage = 'Login failed. Please try again.';
                  this.incorrect = true;
              }
          },
          error: (error) => {
              this.errorMessage = error.error.message || 'Login failed. Please try again.';
              this.incorrect = true;
          }
      });
  } else {
      this.incorrect = true;
      this.errorMessage = 'Please fill in the required fields.';
  }
  }
  resendVerification(): void {
  
  }
}