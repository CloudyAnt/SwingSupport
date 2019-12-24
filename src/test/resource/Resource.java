package test.resource;

import javax.swing.*;
import java.net.URL;

public class Resource {

    private static Resource resource;
    private Resource(){}
    public static Resource instance() {
        if(resource == null) {
            resource = new Resource();
        }
        return resource;
    }

    public URL urlOf(String resourceName) {
        return Resource.class.getResource("img/" + resourceName);
    }

    public ImageIcon getIcon(String iconName) {
        return new ImageIcon(urlOf(iconName));
    }
}
