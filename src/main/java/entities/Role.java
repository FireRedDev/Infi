package entities;

/**
 * Each User in the JRotKreuz has a Role like KIND, GRUPPENLEITER, .. Is used
 * for checking Permissions
 *
 * 
 */
public enum Role {

    /**
     * Lowest Permission
     */
    KIND,
    /**
     * Level 2
     */
    GRUPPENLEITER,
    /**
     * Level 3
     */
    ORTSTELLENLEITER,
    /**
     * Level 4
     */
    BEZIRKSLEITER,
    /**
     * Highest Permission
     */
    LANDESLEITER,

}
