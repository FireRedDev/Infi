import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { HeaderComponent } from './header/header.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { FooterComponent } from './footer/footer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { UserService } from './user.service';
import { AuthguardGuard } from './authguard.guard';
import { AuthService } from './auth/auth.service';
import { SidebarModule } from 'ng-sidebar';
import { HttpClientModule } from '@angular/common/http';
import { TerminComponent } from './termin/termin.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './auth/token.interceptor';
import { ProtocolComponent } from './protocol/protocol.component';
import { DiagramsComponent } from './diagrams/diagrams.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { RestService } from './rest.service';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ToastrModule } from 'ngx-toastr';
import { DateTimePickerComponent } from './calendar/date-time-picker.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OWL_DATE_TIME_LOCALE, OwlDateTimeModule, OwlNativeDateTimeModule, OwlDateTimeIntl } from 'ng-pick-datetime';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { AddUserComponent } from './add-user/add-user.component';
import { InformationComponent } from './information/information.component';
import { PasswordComponent } from './password/password.component';
import { ShowprotocolComponent } from './showprotocol/showprotocol.component';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import localeDe from '@angular/common/locales/de-AT';
import { registerLocaleData } from '@angular/common';
import { CalendarDetailComponent } from './calendar-detail/calendar-detail.component';
import { MessagingService } from './messaging.service';
import { AngularFireDatabaseModule, AngularFireDatabase } from 'angularfire2/database';
import { AngularFireAuth } from 'angularfire2/auth';
import { AngularFireModule, FirebaseApp } from 'angularfire2';
import { environment } from '../environments/environment';
import { FilterPipe } from './filter-pipe.pipe';
import { OrderByPipe } from './order-by.pipe';
import { PlanningComponent } from './planning/planning.component';
import { QuillModule } from 'ngx-quill'

registerLocaleData(localeDe);

const appRoutes: Routes = [
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
    CalendarDetailComponent,
    FilterPipe,
    OrderByPipe,
    PlanningComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes),
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    }),
    SidebarModule.forRoot(),
    NgbModule.forRoot(),
    ToastrModule.forRoot(),
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    NgxChartsModule,
    AngularFireDatabaseModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production }),
    QuillModule
  ],
  providers: [UserService, RestService, AuthguardGuard, AuthService, MessagingService, AngularFireDatabase, AngularFireAuth,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: OWL_DATE_TIME_LOCALE, useValue: 'de' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }