package be.ac.umons.sgl.mom.Helpers;

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
}
