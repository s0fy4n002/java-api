package programmer.yans.spring.core.dto;

public class SearchData {
    private String searchKeyword;
    private String otherSearchKeyword;

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getOtherSearchKeyword() {
        return otherSearchKeyword;
    }

    public void setOtherSearchKeyword(String otherSearchKeyword) {
        this.otherSearchKeyword = otherSearchKeyword;
    }
}
