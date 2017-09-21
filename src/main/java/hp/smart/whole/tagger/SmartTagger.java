package hp.smart.whole.tagger;

import com.alibaba.fastjson.JSON;
import com.yeezhao.commons.util.Pair;
import hp.smart.whole.structure.Tree;
import hp.smart.whole.util.parser.SmartFileUtils;
import hp.smart.whole.util.parser.interfaces.SmartTxtLineParser;

import java.io.*;
import java.util.*;

/**
 * @Author: SMA
 * @Date: 2017-09-18 11:55
 * @Explain:
 */
public class SmartTagger implements Serializable {
    private static Tree<TagObject> tagKeywordTree = new Tree<TagObject>();
    private static Tree<TagObject> tagFilterTree = new Tree<TagObject>();

    private static SmartTagger _singleton = null;

    private static final TreeSet<String> keywordsSet = new SmartTreeSet();
    private static final TreeSet<String> filterSet = new SmartTreeSet();

    private static final Map<String, List<SmartTreeSet>> KEYWORD_MAP = new HashMap<String, List<SmartTreeSet>>();
    private static final Map<String, List<SmartTreeSet>> FILTER_MAP = new HashMap<String, List<SmartTreeSet>>();


    private SmartTagger() {
        init("tag/tencent_mabiao_0826_new.txt");
    }

    public static SmartTagger getInstance() {
        if (_singleton == null) {
            synchronized (SmartTagger.class) {
                if (_singleton == null) {
                    _singleton = new SmartTagger();
                }
            }
        }
        return _singleton;
    }

    private void init(String resourceName) {
        try {
            SmartFileUtils.loadTxtFile(resourceName, new SmartTxtLineParser() {
                @Override
                public void parser(String s) {
                    s = s.toLowerCase();
                    String[] arr = s.split("\t");
                    String tag = arr[0];

                    String[] kws = arr[1].split("\\|");

                    for (String kw : kws) {
                        String[] vars1 = kw.split("&");
                        // 所有的关键词
                        keywordsSet.addAll(Arrays.asList(vars1));
                        // 临时KeyTree
                        TreeSet<String> kwTree = new SmartTreeSet(Arrays.asList(vars1));
                        String lasfKw = kwTree.last();
                        tagKeywordTree.insert(new TagObject(lasfKw, null, tag), kwTree);
                        if (KEYWORD_MAP.containsKey(kwTree.first())) {
                            List<SmartTreeSet> objects = new ArrayList<>(KEYWORD_MAP.get(kwTree.first()));
                            objects.add(new SmartTreeSet(kwTree));
                            KEYWORD_MAP.put(kwTree.first(), objects);
                        } else {
                            KEYWORD_MAP.put(kwTree.first(), Arrays.asList(new SmartTreeSet(kwTree)));
                        }

                    }

                    String[] filters = null;
                    if (arr.length > 2) {
                        filters = arr[2].split("\\|");
                    }
                    if (filters != null && filters.length > 0) {
                        for (String ft : filters) {
                            String[] vars2 = ft.split("&");
                            filterSet.addAll(Arrays.asList(vars2));
                            // 临时tree
                            TreeSet<String> filterTree = new SmartTreeSet(Arrays.asList(vars2));
                            tagFilterTree.insert(new TagObject(null, filterTree.last(), tag), filterTree);
                            if (FILTER_MAP.containsKey(filterTree.first())) {
                                List<SmartTreeSet> objects = new ArrayList<>(FILTER_MAP.get(filterTree.first()));
                                objects.add(new SmartTreeSet(filterTree));
                                FILTER_MAP.put(filterTree.first(), objects);
                            } else {
                                FILTER_MAP.put(filterTree.first(), Arrays.asList(new SmartTreeSet(filterTree)));
                            }
                        }
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TreeSet<String> hasKeyword(String text, boolean ignore) {
        TreeSet<String> kws = new SmartTreeSet();
        if (ignore) {
            for (String kw : keywordsSet) {
                if (text.toLowerCase().contains(kw.toLowerCase())) {
                    kws.add(kw);
                }
            }
        } else {
            for (String kw : keywordsSet) {
                if (text.contains(kw)) {
                    kws.add(kw);
                }
            }
        }
        return kws;
    }

    private static TreeSet<String> hasFilter(String text, boolean ignore) {
        TreeSet<String> filters = new SmartTreeSet();
        if (ignore) {
            for (String kw : filterSet) {
                if (text.toLowerCase().contains(kw.toLowerCase())) {
                    filters.add(kw);
                }
            }
        } else {
            for (String kw : filterSet) {
                if (text.contains(kw)) {
                    filters.add(kw);
                }
            }
        }
        return filters;
    }

    public static Pair<HashSet<String>, HashSet<String>> tag(String text) {
        HashSet<String> resKw = new HashSet<String>();
        HashSet<String> resTag = new HashSet<String>();

        TreeSet<String> kwsSet = hasKeyword(text, true);
        TreeSet<String> ftsSet = hasFilter(text, true);

        if (kwsSet != null && !kwsSet.isEmpty()) {
            for (String kw : kwsSet) {
                List<SmartTreeSet> vars = KEYWORD_MAP.get(kw);
                if (vars != null && !vars.isEmpty()) {
                    for (SmartTreeSet var : vars) {
                        TagObject object = tagKeywordTree.find(var);
                        if (object != null) {
                            resKw.addAll(var);
                            resTag.add(object.getTag());
                        }
                    }
                }
            }
        }

        if (ftsSet != null && !ftsSet.isEmpty()) {
            for (String ft : ftsSet) {
                List<SmartTreeSet> vars = FILTER_MAP.get(ft);
                if (vars != null && !vars.isEmpty()) {
                    for (SmartTreeSet var : vars) {
                        TagObject object = tagFilterTree.find(var);
                        if (object != null) {
                            resTag.remove(object.getTag());
                        }
                    }
                }
            }
        }
        return new Pair<>(resTag, resKw);
    }

    private static boolean contian(List<String> kws, Set<String> allKws) {
        boolean flag = true;
        for (String s : kws) {
            if (!allKws.contains(s)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static void main(String[] args) throws IOException {
        SmartTagger dimensionTag = SmartTagger.getInstance();
        String path = "D:\\selfworkspace\\maven\\elasticsearch-runner\\src\\main\\resources\\part-00000";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String line = "";
        int count = 0;
        long s = System.currentTimeMillis();
        while ((line = reader.readLine()) != null) {
            if (count++ > 5000) {
                break;
            }
            long start = System.currentTimeMillis();
            Map<String, Object> map = (Map<String, Object>) JSON.parse(line);
            String content = (String) map.get("content");
            Pair pair = tag(content);
//            System.out.println("[Time] " + (System.currentTimeMillis() - start) + " ms"
//                    + "  标签 : " + pair.first
//                    + "  关键词 : " + pair.second
//            );
        }
        long t = (System.currentTimeMillis() - s);
        System.out.println("[Time] totle : " + t + " ms,avg : " + t * 1.0f / count);
        reader.close();
    }
}
