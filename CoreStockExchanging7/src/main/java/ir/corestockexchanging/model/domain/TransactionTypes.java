package ir.corestockexchanging.model.domain;

import ir.corestockexchanging.model.domain.dealer.Dealer;
import ir.corestockexchanging.model.domain.dealer.GTC;
import ir.corestockexchanging.model.domain.dealer.IOC;
import ir.corestockexchanging.model.domain.dealer.MPO;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ali on 02/25/2016.
 */
public class TransactionTypes {
    private static Map<String, Dealer> transactionTypes_ = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static Dealer get(String type) {
        return transactionTypes_.get(type);
    }

    static {
        transactionTypes_.put("gtc", new GTC());
        transactionTypes_.put("mpo", new MPO());
        transactionTypes_.put("ioc", new IOC());
    }
//    private static WatchKey watckKey_;
//    private static WatchService watcher_;

//    public static void addNewTypes() throws InterruptedException, IOException, ClassNotFoundException,
//            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        watckKey_ = watcher_.poll(); //
//        System.out.println("in add new types");//debug
//        if (watckKey_ != null) {
//            System.out.println("Inside if!!!");//debug
//            List<WatchEvent<?>> events = watckKey_.pollEvents();
//            for (WatchEvent we : events) {
//                Path newPath = ((WatchEvent<Path>) we).context();
//                URL url = newPath.toUri().toURL();
//                URL[] urls = new URL[]{url};
//
//                Class cls;
//                ClassLoader classLoader = new URLClassLoader(urls);
//
//                String className = newPath.getFileName().toString();
//                cls = classLoader.loadClass(newPath.getFileName().toString());
//
//                Class<Dealer> clas = cls;
//                Constructor<?> constructor = clas.getConstructor();
//                Object object = constructor.newInstance();
//                transactionTypes_.put(className, (Dealer)object);
//            }
//            watch();
//        }
//    }
//
//    public static void watch() throws InterruptedException, IOException {
//        Path path = Paths.get("");
//        watcher_ = path.getFileSystem().newWatchService();
//        path.register(watcher_, StandardWatchEventKinds.ENTRY_CREATE);
//    }
}
