package de.markiewb.netbeans.plugins.debuggerutils;

import java.awt.Frame;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicToolBarUI;
import org.netbeans.api.debugger.Breakpoint;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManagerListener;
import org.netbeans.api.debugger.Session;
import org.netbeans.api.debugger.Watch;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.windows.WindowManager;

public final class FloatableDebugBar implements DebuggerManagerListener {

    private List<? extends Action> actionsForPath;
    private JDialog toolBarWindow;
    private JToolBar toolBar;
    private BasicToolBarUI ui;
    private Frame mainWindow;

    public FloatableDebugBar() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                toolBarWindow = new JDialog();
                toolBar = new JToolBar();
                mainWindow = WindowManager.getDefault().getMainWindow();

                mainWindow.setVisible(true);
                mainWindow.getComponent(0).setVisible(true);

                // see http://stackoverflow.com/a/11238100
                ui = getToolbarUI();

                if (null != ui) {
                    toolBar.setUI(ui);
                }
            }
        });
    }

    private BasicToolBarUI getToolbarUI() {
        // get UI from LAF
        String classOfToolbarUI = UIManager.getString("ToolBarUI");

        try {
            Class<?> clazz = Class.forName(classOfToolbarUI);
            Object newInstance = clazz.newInstance();

            if (newInstance instanceof BasicToolBarUI) {
                return (BasicToolBarUI) newInstance;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }

    @Override
    public Breakpoint[] initBreakpoints() {
        return null;
    }

    @Override
    public void breakpointAdded(Breakpoint brkpnt) {

    }

    @Override
    public void breakpointRemoved(Breakpoint brkpnt) {

    }

    @Override
    public void initWatches() {

    }

    @Override
    public void watchAdded(Watch watch) {

    }

    @Override
    public void watchRemoved(Watch watch) {

    }

    @Override
    public void sessionAdded(final Session sn) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                actionsForPath = org.openide.util.Utilities.actionsForPath("Toolbars/Debug");
                // see http://wiki.netbeans.org/DevFaqInvokeActionProgrammatically
                List<Action> actionsForToolbar = new ArrayList<>();

                if (sn != null) {
                    for (Action action : actionsForPath) {
                        // INFO: http://wiki.netbeans.org/DevFaqAddIconToContextMenu
                        action.putValue(SMALL_ICON, ImageUtilities.loadImageIcon((String) action.getValue("iconBase"), false));
                        action.putValue("iconBase", (String) action.getValue("iconBase"));

                        actionsForToolbar.add(action);
                    }

                    for (Action action : actionsForToolbar) {
                        toolBar.add(action);
                    }

//            mainWindow.add(toolBar);
//                    mainWindow.setVisible(true);
//                    mainWindow.getComponent(0).setVisible(true);
                    // now you can set the toolbar floating
//                    ui.setFloating(true, new Point(0, 0));
                }
            }
        });
    }

    @Override
    public void sessionRemoved(Session sn) {
//        toolBarWindow.setVisible(false);
        toolBar.removeAll();
//        toolBarWindow.removeAll();
        mainWindow.remove(toolBar);
        ui.setFloating(true, new Point(0, 0));
    }

    @Override
    public void engineAdded(DebuggerEngine de) {
    }

    @Override
    public void engineRemoved(DebuggerEngine de) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
