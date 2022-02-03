package rrpss.store;


import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Write/read database files
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class Store {

    /**
     * Read database file
     * @param filename name of the database file
     * @return List of objects
     */
    public static List readSerializedObject(String filename) {
        List list = null;
        try {
            FileInputStream fstream = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fstream);
            list = (ArrayList) in.readObject();
            in.close();
        } catch (IOException ex) {
            if (!(ex instanceof EOFException || ex instanceof FileNotFoundException)) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        if (list == null) return new ArrayList();
        return list;
    }

    /**
     * Write to the database file
     * @param filename name of the file that requires editing
     * @param list list to be added to the file
     */
    public static void writeSerializedObject(String filename, List list) {
        try {
            FileOutputStream fileStream = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileStream);
            out.writeObject(list);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}