/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upload;

import java.io.*;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.http.util.HttpStatus;

/**
 *
 * @author isi
 */
public class UploadService extends HttpHandler {

    File root;

    public UploadService(File root) {
        this.root = root;
    }

    @Override
    public void service(Request request, Response response) throws Exception {

        System.out.println("upload");

        InputStream is = request.getInputStream();
        String filename = request.getParameter("filename");

        FileOutputStream fo = new FileOutputStream(root.getAbsolutePath() + File.separator + "upload_image" + File.separator + filename);
        int read = -1;
        while ((read = is.read()) != -1) {
            fo.write(read);
        }
        fo.close();
        
        response.setStatus(HttpStatus.OK_200);
        PrintWriter pw = new PrintWriter(response.getWriter());
        pw.print("{\"status\":\"ok\"}");
    }

}

