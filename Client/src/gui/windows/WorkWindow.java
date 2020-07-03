package gui.windows;

import application.Context;
import arguments.checkers.IntegerLargerValue;
import arguments.valid.ValidInteger;
import elements.HumanBeing;
import gui.adapted_components.LanguageMenu;
import gui.adapted_components.VisualPanel;
import gui.adapted_components.listeners.AddListener;
import gui.adapted_components.listeners.RemoveParamListener;
import gui.adapted_components.listeners.UpdateListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

public class WorkWindow extends JFrame {

    public static final int WIDTH = 960;
    public static final int HEIGHT = 600;

    public Context context;

    public JMenuBar menuBar;
    public LanguageMenu languageMenu;

    public JLabel userName;

    public JTable table;
    public DefaultTableModel modelTable;
    public JButton filterButton;
    public JTextField filterField;

    public VisualPanel visualPanel;

    public JTextArea console;

    private int quantityEastButton;
    private JPanel generalEastPanel;
    public JButton addButton;
    public JButton clearButton;
    public JButton helpButton;
    public JButton historyButton;
    public JButton infoButton;
    public JButton printDescendingButton;
    public JButton printFieldDescendingCarButton;
    public JButton printUniqueCar;
    public JButton removeByIdButton;
    public JButton removeGreaterButton;
    public JButton removeHeadButton;
    public JButton updateByIdButton;

    public WorkWindow(Context context) {
        super();

        this.context = context;

        menuBar = new JMenuBar();
        languageMenu = new LanguageMenu(context);
        menuBar.add(languageMenu);

        JPanel generalCenterPanel = new JPanel();
        generalCenterPanel.setLayout(new BorderLayout());
        userName = new JLabel();
        userName.setFont(new Font(Font.DIALOG, Font.ITALIC, 18));
        userName.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        generalCenterPanel.add(userName, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        String[] columnNames = {
                "user_id",
                "id",
                "name",
                "coordinate_x",
                "coordinate_y",
                "creation_date",
                "real_hero",
                "has_toothpick",
                "impact_speed",
                "weapon_type",
                "mood",
                "car_name",
        };
        modelTable = new DefaultTableModel(0, columnNames.length) {
            public Class getColumnClass(int column) {
                Class returnValue;
                try {
                    if ((column >= 0) && (column < getColumnCount())) {
                        returnValue = getValueAt(0, column).getClass();
                    } else {
                        returnValue = Object.class;
                    }
                } catch (Exception e) {
                    returnValue = Object.class;
                };
                return returnValue;
            }
        };
        modelTable.setColumnIdentifiers(columnNames);
        table = new JTable(modelTable);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelTable);
        table.setRowSorter(sorter);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        tablePanel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

        filterField = new JTextField();
        filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = filterField.getText();
                try {
                    if (text.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        try {
                            sorter.setRowFilter(RowFilter.regexFilter(text));
                        } catch (PatternSyntaxException pse) {
                            JOptionPane.showMessageDialog(new JFrame(), "Неправильное регулярное выражение!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BorderLayout());
        filterPanel.add(filterField, BorderLayout.CENTER);
        filterPanel.add(filterButton, BorderLayout.EAST);
        tablePanel.add(filterPanel, BorderLayout.SOUTH);

        visualPanel = new VisualPanel(context);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Таблица", tablePanel);
        tabbedPane.addTab("Визуализация", visualPanel);
        generalCenterPanel.add(tabbedPane, BorderLayout.CENTER);

        console = new JTextArea();
        console.setRows(10);
        DefaultCaret caret = (DefaultCaret)console.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        console.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        console.setEnabled(false);
        JPanel consolePanel = new JPanel();
        consolePanel.setLayout(new BorderLayout());
        JScrollPane scrollConsole = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        consolePanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        consolePanel.add(scrollConsole);
        generalCenterPanel.add(consolePanel, BorderLayout.SOUTH);

        quantityEastButton = 0;
        generalEastPanel = new JPanel();
        GridLayout eastGrid = new GridLayout();
        generalEastPanel.setLayout(eastGrid);
        addButton  = new JButton("add");
        clearButton = new JButton("clear");
        helpButton = new JButton("help");
        historyButton = new JButton("history");
        infoButton = new JButton("info");
        printDescendingButton = new JButton("print descending");
        printFieldDescendingCarButton = new JButton("print field descending car");
        printUniqueCar = new JButton("print unique car");
        removeByIdButton = new JButton("remove by id");
        removeGreaterButton = new JButton("remove greater");
        removeHeadButton = new JButton("remove head");
        updateByIdButton = new JButton("update by id");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HumanWindow humanWindow = new HumanWindow(context, HumanWindowType.ADD);
                humanWindow.setTitle(addButton.getText());
                humanWindow.confirmButton.addActionListener(new AddListener("add", humanWindow));
                humanWindow.setTitle(addButton.getText());
                humanWindow.setVisible(true);
                setEnabled(false);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("clear");
            }
        });
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("help");
            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("history");
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("info");
            }
        });
        printDescendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("print_descending");
            }
        });
        printFieldDescendingCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("print_field_descending_car");
            }
        });
        printUniqueCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("print_unique_car");
            }
        });
        removeByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ParameterWindow parameterWindow = new ParameterWindow(context, "id:", new ValidInteger(new IntegerLargerValue(1)));
                parameterWindow.setWidth(250);
                parameterWindow.confirmButton.addActionListener(new RemoveParamListener("remove_by_id", parameterWindow));
                parameterWindow.setTitle(removeByIdButton.getText());
                parameterWindow.setVisible(true);
                setEnabled(false);
            }
        });
        removeGreaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HumanWindow humanWindow = new HumanWindow(context, HumanWindowType.ADD);
                humanWindow.setTitle(addButton.getText());
                humanWindow.confirmButton.addActionListener(new AddListener("remove_greater", humanWindow));
                humanWindow.setVisible(true);
                humanWindow.setTitle(removeGreaterButton.getText());
                setEnabled(false);
            }
        });

        removeHeadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                context.sendRequest("remove_head");
            }
        });
        updateByIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HumanWindow humanWindow = new HumanWindow(context, HumanWindowType.UPDATE);
                humanWindow.setTitle(addButton.getText());
                humanWindow.confirmButton.addActionListener(new UpdateListener("update", humanWindow));
                humanWindow.setTitle(updateByIdButton.getText());
                humanWindow.setVisible(true);
                setEnabled(false);
            }
        });


        addEastButton(addButton);
        addEastButton(clearButton);
        addEastButton(helpButton);
        addEastButton(historyButton);
        addEastButton(infoButton);
        addEastButton(printDescendingButton);
        addEastButton(printFieldDescendingCarButton);
        addEastButton(printUniqueCar);
        addEastButton(removeByIdButton);
        addEastButton(removeGreaterButton);
        addEastButton(removeHeadButton);
        addEastButton(updateByIdButton);
        eastGrid.setColumns(1);
        eastGrid.setRows(quantityEastButton);
        generalEastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        setLayout(new BorderLayout());
        add(menuBar, BorderLayout.NORTH);
        add(generalCenterPanel, BorderLayout.CENTER);
        add(generalEastPanel, BorderLayout.EAST);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Окно взаимодействия с БД.");

    }

    private void addEastButton(JButton button) {
        quantityEastButton++;
        generalEastPanel.add(button);
    }

    public void setUserSettings(String userName, Color color) {
        this.userName.setText(userName);
        this.userName.setForeground(color);
    }

    public void updateTable(HumanBeing[] arrayHumanBeing) {
        modelTable.setRowCount(0);
        int j = 0;
        for(int i = 0; i < arrayHumanBeing.length; ++i) {
            Vector row = new Vector();
            row.add(arrayHumanBeing[i].getIdUser());
            row.add(arrayHumanBeing[i].getId());
            row.add(arrayHumanBeing[i].getName());
            row.add(arrayHumanBeing[i].getCoordinates().getX());
            row.add(arrayHumanBeing[i].getCoordinates().getY());
            row.add(arrayHumanBeing[i].getCreationDate());
            row.add(arrayHumanBeing[i].getRealHero());
            row.add(arrayHumanBeing[i].getHasToothpick());
            row.add(arrayHumanBeing[i].getImpactSpeed());
            row.add(arrayHumanBeing[i].getWeaponType());
            row.add(arrayHumanBeing[i].getMood());
            row.add(arrayHumanBeing[i].getCar().getName());
            modelTable.addRow(row);
        }
    }

    public void updateConsole(String line) {
        console.append(line + "\n");
    }

    public void applyLocale(String baseName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);
        for (String str : context.components.keySet()) {
            Component component = context.components.get(str);
            if(component instanceof JButton) {
                ((JButton) component).setText(resourceBundle.getString(str));
            }
            if(component instanceof JLabel) {
                ((JLabel) component).setText(resourceBundle.getString(str));
            }
            if(component instanceof JFrame) {
                ((JFrame) component).setTitle(resourceBundle.getString(str));
            }
            if(component instanceof LanguageMenu) {
                ((LanguageMenu) component).setText(resourceBundle.getString(str));
                ((LanguageMenu) component).updateUI();
            }
        }
    }

}
