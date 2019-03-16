package ru.otus.java.hw06.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class SoftCacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public SoftCacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(MyElement<K, V> element) {
        // if elements reached max elements - make room for new element - remove one element
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        // softify element and put into the elements map
        elements.put(key, new SoftReference<>(element));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    public MyElement<K, V> get(K key) {
        MyElement<K, V> element = null;
        SoftReference softReference = elements.get(key);
        // if key exists in the elements map
        if (softReference != null) {
            element = (MyElement<K, V>) softReference.get();
            // and if reference points to the object that is still alive
            // we can consider element as item from cache
            if (element != null) {
                hit++;
                element.setAccessed();
            // GC removed object from memory, we have useless entry
            // removing this entry from the map
            } else {
                elements.remove(key);
                miss++;
            }
        // no item was found in elements map for the given key
        } else {
            miss++;
        }

        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> element = null;

                SoftReference softReference = elements.get(key);
                // check if we still have reference to the map entry
                if (softReference != null) {
                    element = (MyElement<K, V>) softReference.get();
                    if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                        elements.remove(key);
                        this.cancel();
                    }
                }


            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
