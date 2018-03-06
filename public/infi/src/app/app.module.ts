import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'angular-calendar';
import { HeaderComponent } from './header/header.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { FooterComponent } from './footer/footer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { UserService } from './user.service';
import {AuthguardGuard} from './authguard.guard';
import {AuthService} from './auth/auth.service';
import { SidebarModule } from 'ng-sidebar';
import {HttpClientModule} from '@angular/common/http';
import { TerminComponent, DefaultIntl } from './termin/termin.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './auth/token.interceptor';
//import { DateTimePickerModule } from 'ng-pick-datetime';
import { ProtocolComponent } from './protocol/protocol.component';
import { DiagramsComponent } from './diagrams/diagrams.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import { RestService } from './rest.service';
import { HomeComponent } from './home/home.component';

import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { DateTimePickerComponent } from './dashboard/date-time-picker.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
//import { OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';

import { OwlDateTimeIntl } from 'ng-pick-datetime';

const appRoutes:Routes = [
  {
    path: '',
    component: LoginFormComponent
  },
  {
    path: 'dashboard',
    canActivate: [AuthguardGuard],
    component: DashboardComponent
  }
]
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginFormComponent,
    FooterComponent,
    DashboardComponent,
    TerminComponent,
    ProtocolComponent,
    DiagramsComponent,
    DateTimePickerComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes),
    CalendarModule.forRoot(),
    SidebarModule.forRoot(),
    NgbModule.forRoot(),
    OwlDateTimeModule, 
    OwlNativeDateTimeModule,
    NgxChartsModule
  ],
  providers: [UserService, RestService, AuthguardGuard, AuthService, 
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    {provide: OwlDateTimeIntl, useValue: DefaultIntl}
],
  bootstrap: [AppComponent]
})
export class AppModule {}

export class MyModule {}

export class MyAppModule {}