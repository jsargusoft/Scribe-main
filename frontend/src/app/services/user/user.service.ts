import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}
  private isLogin = new BehaviorSubject<boolean>(false);
  isUserAuthenticate$ = this.isLogin.asObservable();

  baseUrl = 'http://localhost:8080';

  public addUser(user: any) {
    return this.http.post(`${this.baseUrl}/auth/register`, user);
  }

  public loginUser(user: any) {
    return this.http.post(`${this.baseUrl}/api/auth/login`, user);
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
}