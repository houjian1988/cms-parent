package com.cms.web.common.servlet;

import nl.captcha.servlet.StickyCaptchaServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 生成验证码图片
 * Created by houjian on 2015/7/30.
 */
public class AuthCodeServlet extends StickyCaptchaServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AuthCodeServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = req.getSession();
        response.setHeader("Cache-Control", "no-store"); //HTTP1.1
        response.setHeader("Pragma", "no-cache"); //HTTP1.0
        response.setDateHeader("Expires", -1);
        session.setAttribute("authCode", value(response));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    private String value(HttpServletResponse response) {
        String authCodeValue = "";
        try {
            AuthCode authCode = new AuthCode();
            authCode.writeImg(response.getOutputStream());
            authCodeValue = authCode.getCaptchaValue();
            LOG.info("authCode=" + authCodeValue);
        } catch (IOException e) {
            LOG.error("验证码生成错误", e);
        }
        return authCodeValue;
    }
}

class AuthCode {
    // 验证码图片的宽度。
    private static int width = 100;
    // 验证码图片的高度。
    private static int height = 22;
    // 验证码字符个数
    private static int codeCount = 4;
    // 干扰线条数
    private static int line = 1;
    // 字体高度
    private static int fontHeight = 20;
    //字符集
    private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    //字体
    private static int[] fontStyles = new int[]{Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC};
    //文字在图片中的高度位置
    private static int[] heightImage = new int[]{33, 35, 37, 39};
    //文字在图片中的水平位置
    private static int[] widthImage = new int[]{3, 25, 46, 68};
    //设置字体旋转
    //private static double[] rotates = new double[]{0.0, 0.1, 0.2, 0.3, 0.4, 0.05, 0.15, 0.25, 0.35, 0.45};

    private String captchaValue;

    public void writeImg(OutputStream os) throws IOException {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 画边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        //随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < line; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(width);
            int yl = random.nextInt(height);
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(getRandomColor());
            g.drawLine(x, y, xl, yl);
        }

        //用于保存随机产生的验证码，以便用户登录后进行验证
        StringBuffer randomCode = new StringBuffer();

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);

            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(getRandomColor());

            // 创建字体，字体的大小应该根据图片的高度来定。PLAIN普通字体，BOLD（加粗），ITALIC（斜体），Font.BOLD+ Font.ITALIC（粗斜体）
            Font font = new Font("Fixedsys", random.nextInt(fontStyles.length), fontHeight);
            // 设置字体
            g.setFont(font);

            //设置字体旋转
            //double rotate = rotates[random.nextInt(10)];
            //g.rotate(rotate);

            //在图片中画出文字
            int width = widthImage[i];
            int height = heightImage[random.nextInt(4)];
            g.drawString(strRand, width, height);

            //在图片中画出文字阴影
            g.setColor(getRandomColor());
            g.drawString(strRand, width + 1, height + 1);

            //字体旋转归零
            //g.rotate(-rotate);

            // 将产生的四个随机数组合在一起
            randomCode.append(strRand);
        }
        captchaValue = randomCode.toString();

        ImageIO.write(buffImg, "jpeg", os);
    }

    private Color getRandomColor() {
        Random random = new Random();
        int red = 0, green = 0, blue = 0;
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
        return new Color(red, green, blue);
    }

    public String getCaptchaValue() {
        return captchaValue;
    }
}
