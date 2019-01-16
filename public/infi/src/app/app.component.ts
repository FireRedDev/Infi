import { Component } from '@angular/core';
import { MessagingService } from './messaging.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    /**
     * Message Service
     * @param msgService 
     */
    constructor(private msgService: MessagingService) { }
    /** Message */
    message;

    /**
     * initalize Push Notifications
     */
    ngOnInit() {
        this.msgService.getPermission();
        this.msgService.receiveMessage();
        this.message = this.msgService.currentMessage;
    }
}
