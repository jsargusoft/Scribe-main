import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { SERVICE_BASE_URL } from '../../environment-config';
import { LoginResponseModule } from '../../model/login-response.model';
import { LoginRequest } from '../../model/login-request.model';
import { User } from '../../model/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}
  private isLogin = new BehaviorSubject<boolean>(false);
  isUserAuthenticate$ = this.isLogin.asObservable();
  baseUrl: String = SERVICE_BASE_URL
  userName !: String;


  public addUser(user: User) {
    return this.http.post(`${this.baseUrl}/register`, user, { withCredentials: true });
  }

  public loginUser(user: LoginRequest): Observable<LoginResponseModule> {
    return this.http.post<LoginResponseModule>(`${this.baseUrl}/auth/login`, user, { withCredentials: true });
}

public getCurrentUser():Observable<User>{
  return this.http.get<User>(`${this.baseUrl}/user`, { withCredentials: true });
}

public logoutUser(): Observable<LoginResponseModule> {
  return this.http.post<LoginResponseModule>(`${this.baseUrl}/auth/logout`, {},{ withCredentials: true });
}
}