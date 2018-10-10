console.log("firebase service-worker");
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');


firebase.initializeApp({
  'messagingSenderId': '765257743324'
});

const messaging = firebase.messaging();
