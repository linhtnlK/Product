
package com.linhtnl.product.parts;

import javax.inject.Inject;


import java.util.ArrayList;

import javax.annotation.PostConstruct;
import  org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import com.linhtnl.dialogs.UserDetailsAdd;
import com.linhtnl.dialogs.UserDetailsUpdate;
import com.linhtnl.models.User;

public class Users {

	private ArrayList<User> list;
	private TableViewer tableViewer;
	@Inject
	private IEventBroker eventBroker;
	private int index = -1;

	@Inject
	public Users() {
		list = new ArrayList<User>();
		// username, password, fName, lName, dob, gender, avatar
		User u = new User("LamLinh", "123", "lam", "linh", "2000/09/13", "FEMALE",
				"C:\\Users\\linh.to\\Downloads\\icons8-grinning-face-with-big-eyes-48.png");
		list.add(u);
		u = new User("NgoQuyen", "123", "Ngo", "Quyen", "2002/03/15", "MALE", "C:\\Users\\linh.to\\Desktop\\sss.jpg");
		list.add(u);
	}

	private void loadDataTable() {
		tableViewer.getTable().removeAll();
		TableItem item;
		int i = 1;
		for (User u : list) {
			item = new TableItem(tableViewer.getTable(), SWT.NONE);
			item.setText(new String[] { (i++) + "", u.getUsername(), u.getDateOfBirth(), u.getGender() });
		}
	}

	private void createColumns() {
		// ID
		TableViewerColumn colFirstName = new TableViewerColumn(tableViewer, SWT.NONE);
		colFirstName.getColumn().setWidth(50);
		colFirstName.getColumn().setText("ID");
		colFirstName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}
		});
		// Username
		TableViewerColumn colSecondName = new TableViewerColumn(tableViewer, SWT.NONE);
		colSecondName.getColumn().setWidth(100);
		colSecondName.getColumn().setText("Username");
		colSecondName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				User u = (User) element;
				return u.getUsername();
			}
		});

		// DOB
		TableViewerColumn colThirdName = new TableViewerColumn(tableViewer, SWT.NONE);
		colThirdName.getColumn().setWidth(150);
		colThirdName.getColumn().setText("Date of Birth");
		colThirdName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				User u = (User) element;
				return u.getDateOfBirth();
			}
		});

		// Gender
		TableViewerColumn colFourthName = new TableViewerColumn(tableViewer, SWT.NONE);
		colFourthName.getColumn().setWidth(100);
		colFourthName.getColumn().setText("Gender");
		colFourthName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((User) element).getGender();
			}
		});
	}

	private void createButton(Composite parent) {
		Composite bottom = new Composite(parent	, SWT.BOTTOM);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		bottom.setLayout(gridLayout);

		// Add
		Button add = new Button(bottom, SWT.PUSH);
		add.setText("Add");
		add.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1));
		add.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				UserDetailsAdd userDetails = new UserDetailsAdd(parent.getShell(), list);
				userDetails.open();
				loadDataTable();
				eventBroker.send("Clear", "");
			}
		});

		// Delete
		Button del = new Button(bottom, SWT.PUSH);
		del.setText("Delete");
		del.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (index == -1)
					return;
				index = tableViewer.getTable().getSelectionIndex();
				list.remove(index);
				loadDataTable();
				index = -1;
				eventBroker.send("Clear", "");
			}
		});

		// Update
		Button update = new Button(bottom, SWT.PUSH);
		update.setText("Update");
		update.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {

				if (index == -1)
					return;
				index = tableViewer.getTable().getSelectionIndex();
				UserDetailsUpdate userDetails = new UserDetailsUpdate(parent.getShell(), list, index);
				userDetails.open();
				loadDataTable();
				index = -1;
				eventBroker.send("Clear", "");
			}
		});
		bottom.pack();
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		tableViewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		parent.setLayout(new GridLayout());
		tableViewer.getTable().setLayoutData(new GridData(SWT.FULL_SELECTION,500));
		tableViewer.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				index = tableViewer.getTable().getSelectionIndex();
				eventBroker.send("Avatar",list.get(index).getAvatar());
				eventBroker.send("Welcome", list.get(index).getUsername());
			}
		});
		 
		createColumns();
		loadDataTable();
		// make lines and header visible
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		createButton(parent);
	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	

}