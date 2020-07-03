package gui.windows;

import application.Context;
import elements.HumanBeing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShowWindow extends JFrame {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 400;

    private int rows = 0;
    private int columns = 2;

    public ShowWindow(Context context, int id) {
        super();

        HumanBeing human = context.getHuman(id);

        JPanel elements = new JPanel();

        JTextField text1 = new JTextField();
        text1.setEnabled(false);
        text1.setText(String.valueOf(human.getIdUser()));
        addElement(new JLabel("id user:"), text1, elements);
        JTextField text2 = new JTextField();
        text2.setEnabled(false);
        text2.setText(String.valueOf(human.getId()));
        addElement(new JLabel("id:"), text2, elements);
        JTextField text3 = new JTextField();
        text3.setEnabled(false);
        text3.setText(String.valueOf(human.getName()));
        addElement(new JLabel("name:"), text3, elements);
        JTextField text4 = new JTextField();
        text4.setEnabled(false);
        text4.setText(String.valueOf(human.getCoordinates().getX()));
        addElement(new JLabel("coordinate x:"), text4, elements);
        JTextField text5 = new JTextField();
        text5.setEnabled(false);
        text5.setText(String.valueOf(human.getCoordinates().getY()));
        addElement(new JLabel("coordinate y:"), text5, elements);
        JTextField text6 = new JTextField();
        text6.setEnabled(false);
        text6.setText(String.valueOf(human.getCreationDate()));
        addElement(new JLabel("creation date:"), text6, elements);
        JTextField text7 = new JTextField();
        text7.setEnabled(false);
        text7.setText(String.valueOf(human.getRealHero()));
        addElement(new JLabel("real hero:"), text7, elements);
        JTextField text8 = new JTextField();
        text8.setEnabled(false);
        text8.setText(String.valueOf(human.getHasToothpick()));
        addElement(new JLabel("has toothpick:"), text8, elements);
        JTextField text9 = new JTextField();
        text9.setEnabled(false);
        text9.setText(String.valueOf(human.getImpactSpeed()));
        addElement(new JLabel("impact speed:"), text9, elements);
        JTextField text10 = new JTextField();
        text10.setEnabled(false);
        text10.setText(String.valueOf(human.getWeaponType()));
        addElement(new JLabel("weapon type:"), text10, elements);
        JTextField text11 = new JTextField();
        text11.setEnabled(false);
        text11.setText(String.valueOf(human.getMood()));
        addElement(new JLabel("mood:"), text11, elements);
        JTextField text12 = new JTextField();
        text12.setEnabled(false);
        text12.setText(String.valueOf(human.getCar().getName()));
        addElement(new JLabel("car name:"), text12, elements);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(rows);
        gridLayout.setColumns(columns);
        elements.setLayout(gridLayout);
        elements.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(elements);

        setTitle("show");
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                context.workWindow.setEnabled(true);
                e.getWindow().dispose();
            }
        });
    }

    private void addElement(Component leftComponent, Component rightComponent, JPanel panel) {
        panel.add(leftComponent);
        panel.add(rightComponent);
        rows++;
    }
}
