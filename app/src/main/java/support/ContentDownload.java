package support;


import android.os.Environment;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
public class ContentDownload {


    private String fileName;
    private String UrlAddress;

    public ContentDownload() {
    }


    public void setURL(String Address) {
        this.UrlAddress = Address;
    }
    public void DownloadFiles(String urlAddress, String fileName){
     //stahuje json objekty na uloziste
        try {
            URL u = new URL(urlAddress);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/Download/" + fileName));

            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//pomocna metoda pro kontrolni cteni stazenych souboru
    public void ReadFile (String path) {
        String fileName = path;
        File file = new File(fileName);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                   System.out.println(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}





