package be.ac.umons.sgl.mom.Helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class StringHelper {
    /**
     * Add '-' or begins a new line when necessary. The returned String has a width of <code>width</code> with the font <code>font</code>.
     * @param font The font associated with the text.
     * @param text The text
     * @param width The maximum width.
     * @return A String with a width of <code>width</code> with the font <code>font</code>.
     */
    public static String adaptTextToWidth(BitmapFont font, String text, int width) {
        StringBuilder res = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "A");
        double charWidth = layout.width;
        for (String s : text.split(" ")) {
            layout.setText(font, s);
            if (layout.width > width) {
                while (s.length() > 0) {
                    int charsNbr = (int)(width / charWidth) - 1 - tmp.length(); // -1 for -
                    if (charsNbr > s.length())
                        charsNbr = s.length();
                    res.append(s, 0, charsNbr);
                    res.append("-\n");
                    tmp = new StringBuilder();
                    s = s.substring(charsNbr);
                }
                continue;
            }

            tmp.append(s);
            tmp.append(" ");
            layout.setText(font, tmp.toString());
            if (layout.width > width) {
                res.append('\n');
                res.append(s);
                res.append(" ");
                tmp = new StringBuilder();
                tmp.append(s);
                tmp.append(" ");
            } else {
                res.append(s);
                res.append(" ");
            }
        }
        return res.toString();
    }

    /**
     * @param color The string representing the color (in RGBA)
     * @return The color corresponding to the given string (RGBA)
     */
    public static Color getColorFromString(String color) {
        int[] c = decodeHexStringToInt(color);
        if (color.length() == 6)
            return new Color(c[0] / 255f, c[1] / 255f, c[2] / 255f, 1);
        else if (color.length() == 8)
            return new Color(c[0] / 255f, c[1] / 255f, c[2] / 255f, c[3] / 255f);
        return null;
    }

    /**
     * Convert a string into an array of int. Each int represent two chars from the given string.
     * @param hex The hex value (without 0x)
     * @return An array of int representing the given string.
     */
    protected static int[] decodeHexStringToInt(String hex) {
        int[] t = new int[hex.length() / 2];
        for (int i = 0; i < hex.length() / 2; i++) {
            t[i] = Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return t;
    }
}
