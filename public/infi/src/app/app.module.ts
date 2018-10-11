import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
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
import { TerminComponent, DefaultIntl} from './termin/termin.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './auth/token.interceptor';
import { ProtocolComponent } from './protocol/protocol.component';
import { DiagramsComponent } from './diagrams/diagrams.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import { RestService } from './rest.service';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ToastrModule } from 'ngx-toastr';
import { DateTimePickerComponent } from './calendar/date-time-picker.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OWL_DATE_TIME_LOCALE, OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_FORMATS} from 'ng-pick-datetime';
import { CarouselModule } from "angular2-carousel";
import { OwlDateTimeIntl } from 'ng-pick-datetime/date-time/date-time-picker-intl.service';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { AddUserComponent } from './add-user/add-user.component';
import { InformationComponent } from './information/information.component';
import { PasswordComponent } from './password/password.component';
import { ShowprotocolComponent } from './showprotocol/showprotocol.component';
import { ProtocolPipe } from './protocol.pipe';
import {MatTableModule} from '@angular/material/table';

import { CategoryPipe } from './showprotocol/category.pipe';
import { OrderrByPipe } from './showprotocol/orderByPipe';


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
    CalendarComponent,
    HomeComponent,
    ManageUserComponent,
    AddUserComponent,
    InformationComponent,
    PasswordComponent,
    ShowprotocolComponent,
    ProtocolPipe,
    CategoryPipe,    // Note these two line
    OrderrByPipe    // Note these two line
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
    ToastrModule.forRoot(), 
    OwlDateTimeModule, 
    OwlNativeDateTimeModule,
    NgxChartsModule,
    CarouselModule,
	  ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [UserService, RestService, AuthguardGuard, AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    {provide: OWL_DATE_TIME_LOCALE, useValue: 'de'},
    {provide: OwlDateTimeIntl, useValue: DefaultIntl},
],
  bootstrap: [AppComponent]
})
export class AppModule {}

export class MyModule {}

export class MyAppModule {}
