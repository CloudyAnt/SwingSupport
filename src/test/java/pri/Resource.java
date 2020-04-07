package pri;

import javax.swing.*;
import java.io.File;
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
        return Resource.class.getResource(resourceName);
    }

    public ImageIcon getIcon(String iconName) {
        return new ImageIcon(urlOf(iconName));
    }
}
