package me.aaronakhtar.captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Captcha {

    private static final String     captcha_home = "./captcha";
    private static final String     captcha_file = "assets/captcha.png";
    private static final int        captcha_text_base_height = 20;
    private static final int        captcha_text_base_width = 12;
    private static final int        captcha_text_base_random_w = 70;
    private static final int        captcha_text_base_random_h = 16;
    private static final int        captcha_text_base_random_f = 12;
    private static final int        captcha_text_base_font_size = 18;

    private static final String abc = "abcdefghlmniopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    //          generate a captcha image
    public static void generate(String captcha_identifier) throws IOException {
        File captcha_file = new File(captcha_home + "/" + captcha_identifier + ".png");
        if (captcha_file.exists()){
            return; // already existing captcha
        }else{
            if (!captcha_file.createNewFile()){
                // failed for some reason
                return;
            }
        }
        final BufferedImage image = ImageIO.read(new FileInputStream(Captcha.captcha_file));
        // generating captcha code
        StringBuilder captcha = new StringBuilder();
        final Random random = new Random();
        for (int x = 0; x < 5; x++){
            int c = 0;
            captcha.append(abc.charAt(((c = random.nextInt(abc.length())))) + (((c % 2) == 0) ? "  " : " "));
        }
        // drawing the code on the image
        Graphics graphics = image.getGraphics();
        try(AutoCloseable autoCloseable = () -> graphics.dispose()){
            graphics.setFont(graphics.getFont().deriveFont(Float.parseFloat(random.nextInt(captcha_text_base_random_f) + captcha_text_base_font_size + "")));
            graphics.drawString(captcha.toString(), (random.nextInt(captcha_text_base_random_w) + captcha_text_base_width + 2), (random.nextInt(captcha_text_base_random_h) + captcha_text_base_height + 6));
        }catch (Exception e){
            e.printStackTrace();
        }
        ImageIO.write(image, "png", captcha_file);
    }

}
