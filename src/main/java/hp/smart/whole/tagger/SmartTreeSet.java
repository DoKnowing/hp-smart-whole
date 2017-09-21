package hp.smart.whole.tagger;

import hp.smart.whole.util.comparator.SmartComparator;

import java.util.Collection;
import java.util.TreeSet;

/**
 * @Author: SMA
 * @Date: 2017-09-18 14:41
 * @Explain:
 */
public class SmartTreeSet extends TreeSet<String> {
    public SmartTreeSet() {
        super(new SmartComparator());
    }

    public SmartTreeSet(Collection c) {
        super(new SmartComparator());
        addAll(c);
    }
}
