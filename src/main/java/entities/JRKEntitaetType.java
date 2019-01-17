package entities;

/**
 * Type of the Organisation, like a Gruppe and/or its organisational level
 *
 * @author Christopher G
 */
public enum JRKEntitaetType {

    /**
     * Highest Organisational Level
     */
    Bezirkstelle,

    /**
     * Organisational Level
     */
    Landstelle,

    /**
     * A group where children work and learn with adults
     */
    Gruppe,

    /**
     * Lowest Organisational Level
     */
    Ortstelle
}
