package gui;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class RoundedPanel extends JPanel {
//
//    private final int radius;
//    private final Color backgroundColor;
//    private final Color borderColor;
//
//    public RoundedPanel(int radius, Color backgroundColor, Color borderColor) {
//        this.radius = radius;
//        this.backgroundColor = backgroundColor;
//        this.borderColor = borderColor;
//        setOpaque(false); // Important!
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Fill rounded background
//        g2.setColor(backgroundColor);
//        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius * 2, radius * 2);
//
//        // Draw rounded border only if borderColor is not fully transparent or null
//        if (borderColor != null && borderColor.getAlpha() > 0) {
//            g2.setColor(borderColor);
//            g2.setStroke(new BasicStroke(1.5f));
//            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius * 2, radius * 2);
//        }
//
//        g2.dispose();
//    }
//}

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int cornerRadius;
    private Color backgroundColor;
    private Color borderColor;

    public RoundedPanel(int radius, Color bgColor, Color borderColor) {
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        this.borderColor = borderColor;
        setOpaque(false); // This is crucial for custom painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();

        // Anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        // Border
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }

        g2.dispose();
    }
}
