package com.crossway.view;

import javax.swing.*;
import java.awt.*;

public class DialogHelper {

    private static final Color PRIMARY_COLOR = new Color(142, 175, 241);
    private static final Color BG_COLOR = new Color(224, 210, 239);
    private static final Font MESSAGE_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Color BUTTON_BG = new Color(164, 239, 193);
    private static final Color BUTTON_TEXT = new Color(0, 96, 38);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 13);

    public static void showStyledMessage(Component parent, String message, String title, int messageType) {
        JLabel label = new JLabel("<html><body style='width: 300px; padding: 10px;'>" + message.replaceAll("\n", "<br>") + "</body></html>");
        label.setFont(MESSAGE_FONT);

        applyCustomButton();

        UIManager.put("OptionPane.background", PRIMARY_COLOR);
        UIManager.put("Panel.background", PRIMARY_COLOR);

        JOptionPane.showMessageDialog(parent, label, title, messageType);
    }

    public static int showStyledConfirmDialog(Component parent, String message, String title, Object[] options) {
        JLabel label = new JLabel("<html><body style='padding: 10px;'>" + message.replaceAll("\n", "<br>") + "</body></html>");
        label.setFont(MESSAGE_FONT);

        applyCustomButton();

        UIManager.put("OptionPane.background", BG_COLOR);
        UIManager.put("Panel.background", BG_COLOR);

        return JOptionPane.showOptionDialog(
                parent,
                label,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    private static void applyCustomButton() {
        UIManager.put("Button.background", BUTTON_BG);
        UIManager.put("Button.foreground", BUTTON_TEXT);
        UIManager.put("Button.font", BUTTON_FONT);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
        UIManager.put("Button.select", BUTTON_BG.darker());
    }
}