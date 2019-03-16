import org.junit.Test;
import ru.otus.java.hw06.cache.CacheEngine;
import ru.otus.java.hw06.cache.MyElement;
import ru.otus.java.hw06.cache.SoftCacheEngineImpl;

public class SoftCacheEngineTest {
    @Test
    public void sampleTest() throws InterruptedException {
        int size = 5;

        CacheEngine<Integer, String> cache = new SoftCacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }
}
