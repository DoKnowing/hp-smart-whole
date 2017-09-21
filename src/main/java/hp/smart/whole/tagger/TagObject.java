package hp.smart.whole.tagger;

/**
 * @Author: SMA
 * @Date: 2017-09-18 11:56
 * @Explain:
 */
public class TagObject {
    private String keyword;
    private String filter;
    private String tag;

    public TagObject(String keyword, String filter, String tag) {
        this.keyword = keyword;
        this.filter = filter;
        this.tag = tag;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
