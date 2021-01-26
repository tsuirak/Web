package pers.zyx.sportplay.bean;

/**
 * 次导航
 */
public class SubMenu {
    private int id;
    private String title;
    private String path;

    public SubMenu(){

    }

    public SubMenu(String title,String path){
        this.title = title;
        this.path = path;
    }


    @Override
    public String toString() {
        return "SubMenu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
