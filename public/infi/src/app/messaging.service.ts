import { Injectable } from '@angular/core';
import * as firebase from 'firebase';
import { BehaviorSubject } from 'rxjs';
import { AngularFireDatabase } from '@angular/fire/database';
import { AngularFireAuth } from '@angular/fire/auth';
import { RestService } from './rest.service';

/**
 * Service for push-Messages
 */
@Injectable()
export class MessagingService {

  messaging = firebase.messaging()
  currentMessage = new BehaviorSubject(null)

  constructor(private rest: RestService, private db: AngularFireDatabase, private afAuth: AngularFireAuth) {
    this.rest = rest;
  }

  updateToken(token) {

    this.afAuth.authState.subscribe(user => {
      if (!user) return;

      const data = { [user.uid]: token }
      this.db.object('fcmTokens/').update(data)
    })
  }

  getPermission() {
    this.messaging.requestPermission()
      .then(() => {
        console.log('Notification permission granted.');
        return this.messaging.getToken()
      })
      .then(token => {
        console.log(token)
        localStorage.setItem('pushToken', token)
        this.updateToken(token)
      })
      .catch((err) => {
        console.log('Unable to get permission to notify.', err);
      });
  }

  receiveMessage() {
    console.log("Recieved:")
    this.messaging.onMessage((payload) => {
      console.log("Message received. ", payload);
      this.rest.showSuccessMessage(payload.data.notification.title, payload.data.notification.body);
      this.currentMessage.next(payload)
    });

  }
}
