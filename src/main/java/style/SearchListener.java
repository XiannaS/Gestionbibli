package style;

@FunctionalInterface
public interface SearchListener {
    void onSearch(String searchText, String genre, String year, boolean isAvailable, boolean isUnavailable);
}