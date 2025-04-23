import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageToAscii {
    private static final String ASCII_CHARS = "`^\",:;Il!i~+_-?][}{1)(|\\/0*#MW&8%B@$";

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        try {
            // System.out.println("AVISO: Imagens com 1000+ pixeis de lado não ficam tão
            // bem.");
            System.out.print("Enter image file name: ");
            String fileName = stdin.nextLine();

            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                System.out.println("This file is not an image.");
                return;
            }

            StringBuilder ascii = new StringBuilder();
            int width = image.getWidth();
            int height = image.getHeight();

            // Convert to grayscale && 25% more contrast
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int p = image.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    if (a < 128) {
                        // Se o "alpha" do pixel for menor que 128 (em 255)
                        // o pixel é tratado como trasnparente
                        ascii.append(" ");
                        ascii.append(" ");
                        continue;
                    }

                    int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);

                    int charIndex = (gray * (ASCII_CHARS.length() - 1)) / 255;
                    for (int i = 0; i < 2; i++) {
                        ascii.append(ASCII_CHARS.charAt(charIndex));
                    }
                }
                ascii.append('\n');
            }

            String asciiArt = ascii.toString();
            System.out.println(asciiArt);

            try (PrintWriter out = new PrintWriter("ascii_art.txt")) {
                out.println(asciiArt);
                System.out.println("ASCII art saved to 'ascii_art.txt'");
            }

        } catch (Exception e) {
            System.out.println("Sorry! Something went wrong: " + e.getMessage());
        }
    }
}
