import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { SERVICE_BASE_URL } from '../../environment-config';
import { LoginResponseModule } from '../../model/login-response/login-response.module';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}
  private isLogin = new BehaviorSubject<boolean>(false);
  isUserAuthenticate$ = this.isLogin.asObservable();
  baseUrl: String = SERVICE_BASE_URL
  userName !: String;


  public addUser(user: any) {
    return this.http.post(`${this.baseUrl}/api/register`, user);
  }

  public loginUser(user: any) {
    return this.http.post<LoginResponseModule>(`${this.baseUrl}/api/auth/login`, user)
    .pipe(
      tap(response => {

        if (response && response.name) {
          this.userName = response.name; 
          this.setUserAuthentication(response.isLogged); 
        }
      })
    );
  }

  public logoutUser() {
    const token = localStorage.getItem('jwt');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Include JWT token in 'Authorization' header
      'Content-Type': 'application/json', // Set content type to JSON
    });
    return this.http.post(`${this.baseUrl}/api/auth/logout`, {headers});
  }

  public setUserAuthentication(boolean: boolean) {
    this.isLogin.next(boolean);
  }

  public getUserName(): String {
    return this.userName; 
  }

  // public forgotPassword(userData: any) : Observable<any>{
    
  // }
}