import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Grayscaler {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        try {
            System.out.print("Enter image file name: ");
            String fileName = stdin.nextLine();

            // int flag = -1;
            // while (flag != 1 && flag != 2 && flag != 3) {
            // System.out.println("What do you want to do? (1: Invert, 2: Brightness, 3:
            // Contrast): ");
            // flag = stdin.nextInt();
            // }

            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                System.out.println("This file is not a valid image.");
                return;
            }

            int width = image.getWidth();
            int height = image.getHeight();

            // int brightnessValue = 0;
            // int contrastValue = 1;

            // if (flag == 2) {
            // System.out.print("Brightness Value? (0-255): ");
            // brightnessValue = stdin.nextInt();
            // } else if (flag == 3) {
            // System.out.print("Contrast Multiplier? (e.g., 1 for no change, 2 for double
            // brightness): ");
            // contrastValue = stdin.nextInt();
            // }

            // Process the image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = image.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);

                    int newPixel = (a << 24) | (gray << 16) | (gray << 8) | gray;

                    image.setRGB(x, y, newPixel);
                }
            }

            String outputName = "graysaclae.png";
            String windowTitle = "Grayscale Image";

            // if (flag == 1) {
            // outputName = "inverted.png";
            // windowTitle = "Inverted Image";
            // } else if (flag == 2) {
            // outputName = "brightness.png";
            // windowTitle = "Brightness Image";
            // } else if (flag == 3) {
            // outputName = "contrast.png";
            // windowTitle = "Contrast Image";
            // }

            ImageIO.write(image, "png", new File(outputName));
            System.out.println("Modified image saved as '" + outputName + "'");

            // Display the image
            JFrame frame = new JFrame(windowTitle);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(image));
            frame.add(label);
            frame.setVisible(true);

        } catch (Exception e) {
            System.out.println("Something went wrong in the pixel dimension: " + e.getMessage());
        }
    }
}
