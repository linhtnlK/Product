package com.vogella.tasks.ui.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class ExitHandler {
    @Execute
    private void execute(IWorkbench workbench) {
       workbench.close();
    }
    @Execute
    private void execute() {
        System.out.println(this.getClass().getSimpleName() + " called");

    }
    @CanExecute
    private void CanExecute() {
       

    }
}
