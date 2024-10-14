import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ErrorComponent } from './components/error/error.component';
import { ForgetPasswordComponent } from './components/forget-password/forget-password.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ResetPasswordComponent } from './components/resetPassword/resetPassword.component';

export const routes: Routes = [

    {path: '', redirectTo:'login',pathMatch:'full'},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterComponent},
    {path: 'forget-password', component: ForgetPasswordComponent},
    {path: 'reset-password', component: ResetPasswordComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: '**', component: ErrorComponent},
];
