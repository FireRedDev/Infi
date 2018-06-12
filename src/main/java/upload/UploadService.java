package upload;

import java.io.*;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.grizzly.http.util.HttpStatus;

/**
 * Service for the Image-Upload
 *
 * @author isi
 */
public class UploadService extends HttpHandler {

    File root;

    /**
     *
     */
    public UploadService() {
    }

    /**
     *
     * @param root
     */
    public UploadService(File root) {
        this.root = root;
    }

    /**
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    public void service(Request request, Response response) throws Exception {
        InputStream is = request.getInputStream();
        String filename = request.getParameter("filename");

        //Save image on server
        FileOutputStream fo = new FileOutputStream(root.getAbsolutePath() + File.separator + "upload_image" + File.separator + filename);
        int read = -1;
        while ((read = is.read()) != -1) {
            fo.write(read);
        }
        fo.close();

        //send ok back
        response.setStatus(HttpStatus.OK_200);
        PrintWriter pw = new PrintWriter(response.getWriter());
        pw.print("{\"status\":\"ok\"}");
    }

}
