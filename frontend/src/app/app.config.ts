import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
// import { provideToastr } from 'ngx-toastr';
// import { ToastrModule } from 'ngx-toastr';
// import { MessageService } from "primeng/api";
import { ToastModule } from 'primeng/toast'; 
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideRouter(routes), 
    provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi(), withFetch()), // Combine both methods here
    provideAnimations(),
    // provideToastr(),
    ToastModule,
    BrowserAnimationsModule,
    BrowserModule
  ]
};
