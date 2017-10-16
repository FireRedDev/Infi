package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author INFI-Projektgruppe
 */
public class Repository {
    
    private static Repository instance = null;
    
    private Map<String,String> todos;

    private Repository() {
        todos = new HashMap<>();
    }
  
    public static Repository getInstance() {
            if (instance==null) {
               instance = new Repository();
        }
        return instance;
    }

    // Einfügen eines Reminders
    public String insert(String key, String value) {
        this.todos.put(key, value);
        return value;
    }

    // Suchen eines Reminders
    public String find(String key) {
        return todos.get(key);
    }

    // Suchen aller Reminder
    public List<String> findAll() {
        return new ArrayList<String>(todos.values());
       
    }

    // Schreiben der Collection auf File
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String update(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
