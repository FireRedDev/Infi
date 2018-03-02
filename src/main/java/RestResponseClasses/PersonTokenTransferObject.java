package RestResponseClasses;

/**
 * C.G
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
     *
     * @param userID
     * @param token
     */
    public PersonTokenTransferObject(String userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    /**
     *
     * @return
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
