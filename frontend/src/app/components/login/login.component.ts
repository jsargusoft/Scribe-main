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
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog} from '@angular/material/dialog';
import { log } from 'node:console';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink,
    ReactiveFormsModule,
    CommonModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  loginForm!: FormGroup;
  passwordFieldType: string = 'password';
  incorrect: boolean = false
  errorMessage: String = '';

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
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

    this.userService.loginUser(this.loginForm.value).subscribe({
      next: (response: any) => {

        // console.log('Login successful', response);
        console.log("Welcome "+ this.userService.getUserName());
        
        this.userService.setUserAuthentication(true);

        localStorage.setItem('jwt', response.token);
        this.router.navigate(['/dashboard']); 
      },
      error: (error) => {
        console.error('Login error', error);
        this.incorrect = true;
        this.errorMessage = error.error.message || 'Login failed'; 
      },
    });
  }

  formSubmit() {
    if (this.loginForm.invalid) {
      this._snackBar.open('Please login properly', '', {
        duration: 3000,
        verticalPosition: 'top',
      });
      return;
    }

}
}