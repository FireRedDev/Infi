console.log("firebase service-worker");
importScripts('https://www.gstatic.com/firebasejs/5.5.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.5.0/firebase-messaging.js');

firebase.initializeApp({
  'messagingSenderId': '694294067792'
});

const messaging = firebase.messaging();
