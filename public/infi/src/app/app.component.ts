import { AngularFireModule } from 'angularFire2';

import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { MessagingService } from './messaging.service';


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    message;

    constructor(private msgService: MessagingService) { }

    ngOnInit() {
        this.msgService.getPermission()
        this.msgService.receiveMessage()
        this.message = this.msgService.currentMessage
    }
}
