package utils;

public class FeedsData {
    private String label;
    private String url;
    private String type;
    
    private String download;

    public FeedsData(String label, String url, String type, String download) {
        this.label = label;
        this.url = url;
        this.type = type;
        this.download = download;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
    // 
    public String getDownload() {
        return download;
    }

    public void print() {
        System.out.println("Feed: " + label);
        System.out.println("URL: " + url);
        System.out.println("Type: " + type);
        //
        System.out.println("Download: " + download);
    }
        
}