/*
 * "THE BEER-WARE LICENSE" (Revision 42):
 * =============================================================================
 * <eirikb@eirkb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.gcomic.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import no.eirikb.gcomic.gwt.client.ImageService;
import no.eirikb.gcomic.parse.Page;
import no.eirikb.gcomic.parse.PageParser;

/**
 *
 * @author eirikb
 */
public class ImageServiceImpl extends RemoteServiceServlet implements ImageService {

    public String test(String text) {
        return text + " - HAHA neida";
    }

    public List<int[]> getImage(String imageURL) {
        try {
            URL url = new URL(imageURL);
            URLConnection con = url.openConnection();
            BufferedImage orig = ImageIO.read(con.getInputStream());
            BufferedImage image = new BufferedImage(orig.getWidth() + 10, orig.getHeight() + 10, orig.getType());
            image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
            image.getGraphics().drawImage(orig, 5, 5, null);
            Page page = new Page("test", image);
            page = PageParser.parse(page);
            for (int[] ints : page.getFrames()) {
                for (int i = 0; i < ints.length; i++) {
                    ints[i] = ints[i] - 5;
                }
            }
            return page.getFrames();
        } catch (IOException ex) {
            Logger.getLogger(ImageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
