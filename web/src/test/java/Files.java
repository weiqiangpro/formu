import java.io.File;

/**
 * Created by weiqiang
 */
public class Files {
    public static void main(String[] args) {
        File file = new File("./");
        File[] files = file.listFiles();
        int i = 1;
        String fileName;
        String suffix;
        String name;
        new File("../newFiles").mkdir();
        for (File f : files) {
            fileName = f.getName();
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (suffix.equals("class")||suffix.equals("jar"))
                continue;
            if (i < 10)
                name = "0" + i;
            else
                name = "" + i;
            f.renameTo(new File("../newFiles/" + name + "." + suffix));
            i++;
        }
    }
}
