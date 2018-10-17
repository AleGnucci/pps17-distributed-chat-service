import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ChatComponent } from './chat/chat.component';
import { RegisterComponent } from './register/register.component';

import { RegisterFormComponent } from './register/register-form/register-form.component';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { RoomEntryComponent } from './room-entry/room-entry.component';

import { EventBusService } from './event-bus.service';
import { ChatService } from './chat.service';
import { AddRoomComponent } from './add-room/add-room.component';

import { ServerInterceptor } from './server-interceptor';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ChatComponent,
    RegisterComponent,
    RegisterFormComponent,
    LoginFormComponent,
    RoomEntryComponent,
    AddRoomComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [EventBusService, ChatService,
    { provide: HTTP_INTERCEPTORS, useClass: ServerInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }