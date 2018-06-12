package service;

import entities.JRKEntitaet;
import entities.Person;
import entities.Termin;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import repository.EntityManagerSingleton;

/**
 * C.G
 */
public class NotificationRunnable implements Runnable {

    private static final EntityManager em = EntityManagerSingleton.getInstance().getEm();
    /**
     * The Time to live of GCM notifications
     */
    private static final int TTL = 255;
    private static final String PROJECT_ID = "infi-faefd";
    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    private static String TITLE = "FCM Notification";
    private static String BODY = "Notification from FCM";

    /**
     *
     */
    public static final String MESSAGE_KEY = "message";

    @Override
    public void run() {
        //TODO Post machen wo man subscription bekommt und mehr in datenbank speichern und hier auslesen und in schleife durchgehen
//        Security.addProvider(new BouncyCastleProvider());
//        PushService pushService = new PushService();
//       Notification notification = new Notification(subscription, payload);
//    pushService.sendAsync(notification);
        List<Person> personen = em.createNamedQuery("Benutzer.listAll", Person.class).getResultList();
        LocalDate todaysDate = LocalDate.now();
        for (Person currentPerson : personen) {
            List<Termin> termine = new LinkedList();

            //Check for Duplicates, prepare Termin list
            termine = addList(termine, currentPerson.getJrkentitaet().getTermine());
            //Recursivly get Termine hierarchic upwards
            termine = this.termineLayerDown(currentPerson.getJrkentitaet(), termine);
            //Recursivly get Termine hierarchic downwards
            termine = this.termineLayerUp(currentPerson.getJrkentitaet(), termine);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Termin termin : termine) {
                //    "2018-05-02 18:00:00
                if (LocalDate.parse(termin.getS_date(), formatter).equals(todaysDate)) {
                    try {
                        this.TITLE = "Bevorstehender Termin";
                        //Format String
                        this.BODY = termin.toString();
                        sendCommonMessage();
                    } catch (IOException ex) {
                        Logger.getLogger(NotificationRunnable.class.getName()).log(Level.SEVERE, null, ex);
                        ex.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * Retrieve a valid access token that can be use to authorize requests to
     * the FCM REST API.
     *
     * @return Access token.
     * @throws IOException
     */
    // [START retrieve_access_token]
    private String getAccessToken() throws IOException, ClassNotFoundException {
        InputStream is=getClass().getClassLoader().getResourceAsStream("service-account.json");
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(is)
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }
    // [END retrieve_access_token]

    /**
     * Create HttpURLConnection that can be used for both retrieving and
     * publishing.
     *
     * @return Base HttpURLConnection.
     * @throws IOException
     */
    private HttpURLConnection getConnection() throws IOException {
        // [START use_access_token]
        URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NotificationRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
        return httpURLConnection;
        // [END use_access_token]
    }

    /**
     * Send request to FCM message using HTTP.
     *
     * @param fcmMessage Body of the HTTP request.
     * @throws IOException
     */
    private void sendMessage(JsonObject fcmMessage) throws IOException {
        HttpURLConnection connection = getConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(fcmMessage.toString());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            String response = inputstreamToString(connection.getInputStream());
            System.out.println("Message sent to Firebase for delivery, response:");
            System.out.println(response);
        } else {
            System.out.println("Unable to send message to Firebase:");
            String response = inputstreamToString(connection.getErrorStream());
            System.out.println(response);
        }
    }

    /**
     * Send a message that uses the common FCM fields to send a notification
     * message to all platforms. Also platform specific overrides are used to
     * customize how the message is received on Android and iOS.
     *
     * @throws IOException
     */
    private void sendOverrideMessage() throws IOException {
        JsonObject overrideMessage = buildOverrideMessage();
        System.out.println("FCM request body for override message:");
        prettyPrint(overrideMessage);
        sendMessage(overrideMessage);
    }

    /**
     * Build the body of an FCM request. This body defines the common
     * notification object as well as platform specific customizations using the
     * android and apns objects.
     *
     * @return JSON representation of the FCM request body.
     */
    private static JsonObject buildOverrideMessage() {
        JsonObject jNotificationMessage = buildNotificationMessage();

        JsonObject messagePayload = jNotificationMessage.get(MESSAGE_KEY).getAsJsonObject();
        messagePayload.add("android", buildAndroidOverridePayload());

        JsonObject apnsPayload = new JsonObject();
        apnsPayload.add("headers", buildApnsHeadersOverridePayload());
        apnsPayload.add("payload", buildApsOverridePayload());

        messagePayload.add("apns", apnsPayload);

        jNotificationMessage.add(MESSAGE_KEY, messagePayload);

        return jNotificationMessage;
    }

    /**
     * Build the android payload that will customize how a message is received
     * on Android.
     *
     * @return android payload of an FCM request.
     */
    private static JsonObject buildAndroidOverridePayload() {
        JsonObject androidNotification = new JsonObject();
        androidNotification.addProperty("click_action", "android.intent.action.MAIN");

        JsonObject androidNotificationPayload = new JsonObject();
        androidNotificationPayload.add("notification", androidNotification);

        return androidNotificationPayload;
    }

    /**
     * Build the apns payload that will customize how a message is received on
     * iOS.
     *
     * @return apns payload of an FCM request.
     */
    private static JsonObject buildApnsHeadersOverridePayload() {
        JsonObject apnsHeaders = new JsonObject();
        apnsHeaders.addProperty("apns-priority", "10");

        return apnsHeaders;
    }

    /**
     * Build aps payload that will add a badge field to the message being sent
     * to iOS devices.
     *
     * @return JSON object with aps payload defined.
     */
    private static JsonObject buildApsOverridePayload() {
        JsonObject badgePayload = new JsonObject();
        badgePayload.addProperty("badge", 1);

        JsonObject apsPayload = new JsonObject();
        apsPayload.add("aps", badgePayload);

        return apsPayload;
    }

    /**
     * Send notification message to FCM for delivery to registered devices.
     *
     * @throws IOException
     */
    public void sendCommonMessage() throws IOException {
        JsonObject notificationMessage = buildNotificationMessage();
        System.out.println("FCM request body for message using common notification object:");
        prettyPrint(notificationMessage);
        sendMessage(notificationMessage);
    }

    /**
     * Construct the body of a notification message request.
     *
     * @return JSON of notification message.
     */
    private static JsonObject buildNotificationMessage() {
        JsonObject jNotification = new JsonObject();
        jNotification.addProperty("title", TITLE);
        jNotification.addProperty("body", BODY);

        JsonObject jMessage = new JsonObject();
        jMessage.add("notification", jNotification);
        jMessage.addProperty("topic", "news");

        JsonObject jFcm = new JsonObject();
        jFcm.add(MESSAGE_KEY, jMessage);

        return jFcm;
    }

    /**
     * Read contents of InputStream into String.
     *
     * @param inputStream InputStream to read.
     * @return String containing contents of InputStream.
     * @throws IOException
     */
    private static String inputstreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }

    /**
     * Pretty print a JsonObject.
     *
     * @param jsonObject JsonObject to pretty print.
     */
    private static void prettyPrint(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jsonObject) + "\n");
    }

    private List<Termin> addList(List<Termin> termine, List<Termin> tt) {
        if (!termine.equals(tt)) {
            for (Termin te : tt) {
                termine.add(te);
            }
        }
        return termine;
    }

    private List<Termin> termineLayerDown(JRKEntitaet jrk, List<Termin> termine) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerDown", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            for (JRKEntitaet entity : jrkentitaet) {
                termine = addList(termine, entity.getTermine());
                List<Termin> term = termineLayerDown(entity, termine);
                termine = addList(termine, term);
            }
        }
        return termine;
    }

    private List<Termin> termineLayerUp(JRKEntitaet jrk, List<Termin> termine) {
        List<JRKEntitaet> jrkentitaet = em.createNamedQuery("JRKEntitaet.layerUp", JRKEntitaet.class).setParameter("jrkentitaet", jrk).getResultList();
        //Does List exist and is it filled
        if (jrkentitaet != null && !jrkentitaet.isEmpty()) {
            //Go through all entitys
            for (JRKEntitaet entity : jrkentitaet) {
                //Prepare Termin List, check for duplicates
                termine = addList(termine, entity.getTermine());
                List<Termin> term = termineLayerUp(entity, termine);
                termine = addList(termine, term);
            }
        }
        return termine;
    }
}
