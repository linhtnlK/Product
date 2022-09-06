package com.linhtnl.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.linhtnl.models.User;

public class UserDetailsUpdate extends Dialog {

    private List<User> list;

    private int index;

    private String filePath;

    private String mes;

    private Label validation;

    private Text firstName;

    private Text username;

    private Text pass;

    private Text lastName;

    private Text avatar;

    private DateTime calendarDropDown;

    public UserDetailsUpdate(Shell shell, List<User> list, int index) {
        super(shell);
        this.list = list;
        this.index = index;
    }

    private void fillData() {
        User u = list.get(index);
        username.setText(u.getUsername());
        firstName.setText(u.getFirstName());
        lastName.setText(u.getLastName());
        pass.setText(u.getPass());
        avatar.setText(u.getAvatar());
        filePath = u.getAvatar();
        String[] time = u.getDateOfBirth().split("/");
        calendarDropDown.setDate(Integer.parseInt(time[0]), Integer.parseInt(time[1]) - 1, Integer.parseInt(time[2]));
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout gl = new GridLayout();
        gl.marginTop = 30;
        gl.marginLeft = 10;
        gl.marginRight = 10;
        gl.horizontalSpacing = 30;
        gl.numColumns = 3;
        container.setLayout(gl);

        GridData gdInput = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

        // ID
        Label idlbl = new Label(container, SWT.NONE);
        idlbl.setText("ID");

        Text id = new Text(container, SWT.BORDER);
        id.setLayoutData(gdInput);
        id.setEnabled(false);
        id.setText(index + 1 + "");

        // Username
        Label usernamelbl = new Label(container, SWT.NONE);
        usernamelbl.setText("Username");

        username = new Text(container, SWT.BORDER);
        username.setLayoutData(gdInput);
        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                mes = "";
                validation.setText("");
            }
        });

        // Password
        Label passlbl = new Label(container, SWT.NONE);
        passlbl.setText("Password");

        pass = new Text(container, SWT.BORDER | SWT.PASSWORD);
        pass.setLayoutData(gdInput);
        pass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                mes = "";
                validation.setText("");
            }
        });

        // FirstName
        Label firstNamelbl = new Label(container, SWT.NONE);
        firstNamelbl.setText("Fist Name");

        firstName = new Text(container, SWT.BORDER);
        firstName.setLayoutData(gdInput);

        // Last Name
        Label lastNamelbl = new Label(container, SWT.NONE);
        lastNamelbl.setText("Last Name");

        lastName = new Text(container, SWT.BORDER);
        lastName.setLayoutData(gdInput);

        // Date of Birth
        Label doblbl = new Label(container, SWT.NONE);
        doblbl.setText("Date of Birth");

        calendarDropDown = new DateTime(container, SWT.DROP_DOWN | SWT.CALENDAR_WEEKNUMBERS);
        calendarDropDown.setLayoutData(gdInput);

        // Gender
        gdInput.horizontalSpan = 2;
        Label genderlbl = new Label(container, SWT.NONE);
        genderlbl.setText("Gender");

        Button malebtn = new Button(container, SWT.RADIO);
        malebtn.setSelection(true);
        malebtn.setText("Male");

        Button femalebtn = new Button(container, SWT.RADIO);
        femalebtn.setText("Female");

        // Avatar
        Label avatarlbl = new Label(container, SWT.NONE);
        avatarlbl.setText("Avatar");

        avatar = new Text(container, SWT.BORDER);
        GridData d = new GridData();
        d.widthHint = 80;
        avatar.setLayoutData(d);
        Button file = new Button(container, SWT.PUSH);
        file.setText("Choose file");
        file.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                FileDialog dialog = new FileDialog(container.getShell(), SWT.OPEN);
                String[] extensions = { "*.png", "*.jpeg", "*.jpg" };
                dialog.setFilterExtensions(extensions);
                dialog.open();
                filePath = dialog.getFilterPath() + "\\" + dialog.getFileName();
                avatar.setText(dialog.getFileName());
            }
        });
        avatar.setSize(50, 30);
        // Validation
        gdInput = new GridData(300, 50);
        gdInput.horizontalSpan = 3;

        validation = new Label(container, SWT.NONE);
        validation.setForeground(new Color(container.getDisplay(), 255, 0, 0));
        validation.setFont(new Font(container.getDisplay(), "Cambria", 10, SWT.ITALIC));
        validation.setLayoutData(gdInput);
        // Add + Cancel btn
        Composite bottom = new Composite(parent, SWT.BOTTOM);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        bottom.setLayout(gridLayout);

        Button updateBtn = new Button(bottom, SWT.PUSH);
        updateBtn.setText("Update");
        updateBtn.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                String user = username.getText();
                String password = pass.getText();
                int flag = 0;
                mes = "";
                if (user.trim().length() == 0) {
                    mes += "- Username must not be blank\n";
                    flag = 1;
                }
                if (password.trim().length() == 0) {
                    mes += "- Passwork must not be blank\n";
                    flag = 1;
                }

                if (flag == 0) {
                    String fName = firstName.getText();
                    String lName = lastName.getText();
                    String dob = calendarDropDown.getYear() + "/" + calendarDropDown.getMonth() + "/"
                            + calendarDropDown.getDay();
                    String gender = "MALE";
                    if (femalebtn.getSelection()) {
                        gender = "FEMALE";
                    }
                    // username, password, fName, lName, dob, gender, avatar
                    list.get(index).setUsername(user);
                    list.get(index).setPass(password);
                    list.get(index).setFirstName(fName);
                    list.get(index).setLastName(lName);
                    list.get(index).setDateOfBirth(dob);
                    list.get(index).setGender(gender);
                    list.get(index).setAvatar(filePath);
                    container.getShell().dispose();
                } else {
                    validation.setText(mes);
                }

            }
        });

        Button cancelbtn = new Button(bottom, SWT.PUSH);
        cancelbtn.setText("Cancel");
        cancelbtn.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                container.getShell().dispose();
            }
        });

        // Fill Data
        fillData();

        container.getShell().pack();
        return container;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("User Details");
    }

    @Override
    protected Control createButtonBar(Composite parent) {
        // TODO Auto-generated method stub
        return null;
    }

}
