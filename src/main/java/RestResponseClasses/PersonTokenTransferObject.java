package RestResponseClasses;

/**
 * Has the Property userID and token, used for Rest
 */
public class PersonTokenTransferObject {

    /**
     *
     */
    public String userID;

    /**
     *
     */
    public String token;

    /**
     * Constructor
     * @param userID
     * @param token
     */
    public PersonTokenTransferObject(String userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    /**
     * Getter
     * @return
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Setter
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Getter
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
