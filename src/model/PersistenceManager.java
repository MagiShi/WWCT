package src.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class PersistenceManager {
    private static final Logger LOGGER = Logger.getLogger("PersistenceManager");

    private List<Location> model;

    public PersistenceManager(Collection<Location> m) {
        model.addAll(m.stream().collect(Collectors.toList()));
    }

    public void saveToText(File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println(model.size());
            for (Location l : model) {
                l.saveToText(pw);
            }
            pw.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception working with Text Save File", e);
        }

    }

    public void loadFromText(File file) {
        String ct = null;
        model.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ct = br.readLine();
            int count = Integer.parseInt(ct);
            for (int i = 0; i < count; ++i) {
                ct = br.readLine();
                Location loc = Location.makeFromFileString(ct);
                model.add(loc);
            }
            br.close();
        } catch(IOException ex) {
            LOGGER.log(Level.SEVERE, "Exception working with Text Load File", ex);
        } catch(NumberFormatException fe) {
            LOGGER.log(Level.SEVERE, "File Format problem with count of elements: " + ct, fe);
        } catch (FileFormatException e) {
            LOGGER.log(Level.SEVERE, "Format problem with individual line: " + e.getOriginalLine(), e);
        }
    }
//
//    public void saveToBinary(File file) {
//        try (ObjectOutput oos = new ObjectOutputStream(new FileOutputStream(file))){
//            oos.writeObject(model);
//            oos.close();
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Failed to make an output stream for Binary", e);
//        }
//    }
//
//    public void loadFromBinary(File file) {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            Object obj = ois.readObject();
//            if (obj instanceof ArrayList<?>) {
//                model = (ArrayList<Location>) ois.readObject();
//            }
//            ois.close();
//        } catch (IOException ex) {
//            LOGGER.log(Level.SEVERE, "Failed to make an input stream for Binary", ex);
//        } catch (ClassNotFoundException ex) {
//            LOGGER.log(Level.SEVERE, "Failed to find appropriate class in Binary", ex);
//        }
//    }

    public void saveToJson(File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            String str = gson.toJson(model);
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception working with Json Save File", e);
        }
    }

//    public void loadFromJsonfile(File file) {
//        String ct;
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//
//            Type collectionType = new TypeToken<List<Location>>(){}.getType();
//
//            Gson gson = new Gson();
//
//            ct = br.readLine();
//
//            model = gson.fromJson(ct, collectionType);
//
//            br.close();
//        } catch (IOException ex) {
//            LOGGER.log(Level.SEVERE, "Exception working with Json load file", ex);
//        }
//    }
}
