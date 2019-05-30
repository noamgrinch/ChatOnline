package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import CentralLogger.SendLogThread;

public class Util {
	
    public static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\" + path));
        } catch (Exception e) {
        	new SendLogThread(Level.SEVERE,e).start();
        }

        return null;
    }

}
