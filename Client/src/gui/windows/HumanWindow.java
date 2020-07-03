package gui.windows;

import application.Context;
import arguments.checkers.*;
import arguments.valid.*;
import gui.adapted_components.CheckedField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HumanWindow extends JFrame {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 515;

    public Context context;
    public HumanWindowType type;

    public CheckedField id;
    public CheckedField name;
    public CheckedField coordinateX;
    public CheckedField coordinateY;
    public JCheckBox realHero;
    public JCheckBox hasToothpick;
    public CheckedField impactSpeed;
    public ButtonGroup weaponTypeGroup;
    public JRadioButton shotgun;
    public JRadioButton rifle;
    public JRadioButton knife;
    public JRadioButton machineGun;
    public JRadioButton bat;
    public ButtonGroup moodGroup;
    public JRadioButton sorrow;
    public JRadioButton longing;
    public JRadioButton gloom;
    public JRadioButton rage;
    public JRadioButton frenzy;
    public CheckedField carName;
    public JButton confirmButton;

    private int rows;
    private int columns;

    public HumanWindow(Context context, HumanWindowType type) {
        super();

        this.context = context;
        this.type = type;

        id = new CheckedField(new ValidInteger(new IntegerLargerValue(0)));
        name = new CheckedField(new ValidString(new StringSize(1)));
        coordinateX = new CheckedField(new ValidFloat(new FloatLargerValue(0F), new FloatLessValue(context.workWindow.visualPanel.HEIGHT * 1.0F)));
        coordinateY = new CheckedField(new ValidFloat(new FloatLargerValue(0F), new FloatLessValue(context.workWindow.visualPanel.WIDTH * 1.0F)));
        realHero = new JCheckBox();
        hasToothpick = new JCheckBox();
        impactSpeed = new CheckedField(new ValidFloat(new FloatLargerValue(0F), new FloatLessValue(101.0F)));
        weaponTypeGroup = new ButtonGroup();
        shotgun = new JRadioButton("SHOTGUN", true);
        shotgun.setActionCommand(shotgun.getText());
        rifle = new JRadioButton("RIFLE", false);
        rifle.setActionCommand(rifle.getText());
        knife = new JRadioButton("KNIFE", false);
        knife.setActionCommand(knife.getText());
        machineGun = new JRadioButton("MACHINE_GUN", false);
        machineGun.setActionCommand(machineGun.getText());
        bat = new JRadioButton("BAT", false);
        bat.setActionCommand(bat.getText());
        weaponTypeGroup.add(shotgun);
        weaponTypeGroup.add(rifle);
        weaponTypeGroup.add(knife);
        weaponTypeGroup.add(machineGun);
        weaponTypeGroup.add(bat);
        moodGroup = new ButtonGroup();
        sorrow = new JRadioButton("SORROW", true);
        sorrow.setActionCommand(sorrow.getText());
        longing = new JRadioButton("LONGING", false);
        longing.setActionCommand(longing.getText());
        gloom = new JRadioButton("GLOOM", false);
        gloom.setActionCommand(gloom.getText());
        rage = new JRadioButton("RAGE", false);
        rage.setActionCommand(rage.getText());
        frenzy = new JRadioButton("FRENZY", false);
        frenzy.setActionCommand(frenzy.getText());
        moodGroup.add(sorrow);
        moodGroup.add(longing);
        moodGroup.add(gloom);
        moodGroup.add(rage);
        moodGroup.add(frenzy);
        carName = new CheckedField(new ValidString(new StringSize(1)));
        confirmButton = new JButton("confirm");

        columns = 2;
        rows = 0;
        JPanel elements = new JPanel();
        if(type == HumanWindowType.UPDATE) {
            addElement(new JLabel("id:"), id, elements);
        }
        if(type == HumanWindowType.SHOW) {

        }
        addElement(new JLabel("name:"), name, elements);
        addElement(new JLabel("coordinate X:"), coordinateX, elements);
        addElement(new JLabel("coordinate Y:"), coordinateY, elements);
        addElement(new JLabel("real hero:"), realHero, elements);
        addElement(new JLabel("has toothpick:"), hasToothpick, elements);
        addElement(new JLabel("impact speed:"), impactSpeed, elements);
        addElement(new JLabel("weapon type:"), shotgun, elements);
        addElement(new JPanel(), rifle, elements);
        addElement(new JPanel(), knife, elements);
        addElement(new JPanel(), machineGun, elements);
        addElement(new JPanel(), bat, elements);
        addElement(new JLabel("mood:"), sorrow, elements);
        addElement(new JPanel(), longing, elements);
        addElement(new JPanel(), gloom, elements);
        addElement(new JPanel(), rage, elements);
        addElement(new JPanel(), frenzy, elements);
        addElement(new JLabel("car name:"), carName, elements);

        GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(rows);
        gridLayout.setColumns(columns);
        elements.setLayout(gridLayout);
        elements.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        setLayout(new BorderLayout());
        add(elements, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);

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
