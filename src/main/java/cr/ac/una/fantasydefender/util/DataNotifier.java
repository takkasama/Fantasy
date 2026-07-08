package cr.ac.una.fantasydefender.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 *
 * @author takkasama
 */
public class DataNotifier {

    public interface Listener {
        void onDataChanged(String event);
    }

    private static final List<Listener> listeners = new CopyOnWriteArrayList<>();

    public static void subscribe(Listener listener) {
        listeners.add(listener);
    }

    public static void unSuscribe(Listener listener){
        listeners.remove(listener);
    }
    public static void notifyChange(String event) {
        for (Listener listener : listeners) {
            listener.onDataChanged(event);
        }
    }
}
