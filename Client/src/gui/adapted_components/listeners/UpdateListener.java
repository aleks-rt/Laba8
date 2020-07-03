package gui.adapted_components.listeners;

import communication.Argument;
import elements.*;
import gui.windows.HumanWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateListener implements ActionListener {

    private String name;
    private HumanWindow humanWindow;

    public UpdateListener(String name, HumanWindow humanWindow) {
        this.name = name;
        this.humanWindow = humanWindow;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            HumanBeing human = new HumanBeing((String) humanWindow.name.getValue(), new Coordinates((Float) humanWindow.coordinateX.getValue(),
                    (Float) humanWindow.coordinateY.getValue()), (Boolean) humanWindow.realHero.isSelected(), (Boolean) humanWindow.hasToothpick.isSelected(),
                    (Float) humanWindow.impactSpeed.getValue(), WeaponType.valueOf(humanWindow.weaponTypeGroup.getSelection().getActionCommand()),
                    Mood.valueOf(humanWindow.moodGroup.getSelection().getActionCommand()), new Car((String) humanWindow.carName.getValue()));
            humanWindow.context.sendRequest(name, new Argument((Integer) humanWindow.id.getValue()), new Argument(human));
            humanWindow.setVisible(false);
            humanWindow.context.workWindow.setEnabled(true);
            humanWindow.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(), "Некорректно введены данные!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
