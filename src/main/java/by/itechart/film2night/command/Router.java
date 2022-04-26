package by.itechart.film2night.command;

public class Router {

    private String pagePath;

    public Router() {
    }

    public Router(String pagePath) {
        this.pagePath = pagePath;

    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }


}
