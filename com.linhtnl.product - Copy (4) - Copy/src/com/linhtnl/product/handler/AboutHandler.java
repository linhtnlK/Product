
package com.linhtnl.product.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class AboutHandler {
    @Execute
    public void execute(Shell shell) {
        String mess = "Sample Eclipse RCP application \n" + "Author: Lam Linh \n" + "Version: 1.0.0";
        MessageDialog.openInformation(shell, "About", mess);
    }

}
