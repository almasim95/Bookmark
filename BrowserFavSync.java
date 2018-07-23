/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browserfavsync;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author uia98662
 */
class YourSingleton  {  

        private static YourSingleton mInstance;
        private ArrayList<Bookmark> list = null;

        public static YourSingleton getInstance() {
            if(mInstance == null)
                mInstance = new YourSingleton();

            return mInstance;
        }

        private YourSingleton() {
          list = new ArrayList<Bookmark>();
        }
        // retrieve array from anywhere
        public ArrayList<Bookmark> getArray() {
         return this.list;
        }
        //Add element to array
        public void addToArray(Bookmark bk) {
         list.add(bk);
        }
}

class Bookmark{
    private String name, path;
    public Bookmark(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "Name: " + name + " \t\t\t Path:" + path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
public class BrowserFavSync {

    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // Get all the files from a directory.
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }

    public static void main(String[] args) {
        //%USERPROFILE%
        ArrayList<String> links = new ArrayList<>();
        ArrayList<String> linksFromChrome = new ArrayList<>();
        String userprofile = System.getenv("USERPROFILE");

//        Bookmarks links internet explorer
        YourSingleton ys = YourSingleton.getInstance();
        
        List<File> al = new ArrayList<File>();
        listf(userprofile + "\\Favorites", al);
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).toString().endsWith(".url")) {
                //String name = al.get(i).toString().split("\\\\")[al.get(i).toString().split("\\\\").length - 1];
                //System.out.println(name);
                try {
                    Scanner input;

                    File file = new File(al.get(i).toString());

                    input = new Scanner(file);

                    while (input.hasNextLine()) {

                        String line = input.nextLine();
                        if (line.startsWith("URL=")) {
                           // System.out.println(line.substring(4).replace("\"", ""));
                            links.add(line.substring(4));
                            ys.addToArray(new Bookmark(al.get(i).toString().split("\\\\")[al.get(i).toString().split("\\\\").length-1].replace(".url", ""),line.substring(4)));
                        }
                    }
                    input.close();

                } catch (FileNotFoundException ex) {
                }
            }

        }
for(int i=0;i<ys.getArray().size();i++){
    System.out.println(ys.getArray().get(i));
}
//      Bookmarks chrome        
//        List<File> chrome = new ArrayList<File>();
//        try {
//            Scanner input;
//
//            File file = new File(userprofile + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Bookmarks");
//
//            input = new Scanner(file);
//
//            while (input.hasNextLine()) {
//
//                String line = input.nextLine();
//                if (line.trim().startsWith("\"url\": ")) {
//                    System.out.println(line.trim().substring("\"url\": ".length()).replace("\"", ""));
//                    linksFromChrome.add(line.substring(4));
//                }
//            }
//            input.close();
//
//        } catch (FileNotFoundException ex) {
//        }
    }

}
