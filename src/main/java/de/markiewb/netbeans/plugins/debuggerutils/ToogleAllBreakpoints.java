package de.markiewb.netbeans.plugins.debuggerutils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.ref.WeakReference;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.debugger.ActiveBreakpoints;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.api.debugger.DebuggerManagerAdapter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.*;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Debug",
        id = "de.markiewb.netbeans.plugins.debuggerutils.ToogleAllBreakpoints"
)
@ActionRegistration(
        iconBase = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint_stroke.png",
        displayName = "#CTL_ToogleAllBreakpoints"
)
@ActionReference(path = "Toolbars/Debug", position = 1050)
@Messages("CTL_ToogleAllBreakpoints=Mute breakpoints")
public final class ToogleAllBreakpoints extends AbstractAction implements ActionListener {

    @StaticResource
    private static final String iconpath_muted = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint.png";
    @StaticResource
    private static final String iconpath_unmuted = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint_stroke.png";

    Icon icon_muted = ImageUtilities.loadImageIcon(iconpath_muted, false);
    Icon icon_unmuted = ImageUtilities.loadImageIcon(iconpath_muted, false);
    WeakReference<DebuggerEngine> engine = null;

    public ToogleAllBreakpoints() {
        final AbstractAction action = this;
        DebuggerManager.getDebuggerManager().addDebuggerListener(DebuggerManager.PROP_CURRENT_ENGINE, new DebuggerManagerAdapter() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                DebuggerEngine de = (DebuggerEngine) evt.getNewValue();
                engine = new WeakReference<>(de);
                if (null != de) {
                    ActiveBreakpoints ab = ActiveBreakpoints.get(de);
                    if (ab.canDeactivateBreakpoints()) {
                        if (ab.areBreakpointsActive()) {
                            action.putValue(Action.SMALL_ICON, icon_muted);
                            action.putValue(Action.LARGE_ICON_KEY, icon_muted);
                            action.putValue(Action.SHORT_DESCRIPTION, "Mute all breakpoints");
                        } else {
                            action.putValue(Action.SMALL_ICON, icon_unmuted);
                            action.putValue(Action.LARGE_ICON_KEY, icon_unmuted);
                            action.putValue(Action.SHORT_DESCRIPTION, "Unmute all breakpoints");
                        }
                    }
                    action.setEnabled(ab.canDeactivateBreakpoints());

                }
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final AbstractAction action = this;

        if (null != engine && null != engine.get()) {
            ActiveBreakpoints ab = ActiveBreakpoints.get(engine.get());
            if (ab.canDeactivateBreakpoints()) {
                if (ab.areBreakpointsActive()) {
                    action.putValue(Action.SMALL_ICON, icon_muted);
                    action.putValue(Action.LARGE_ICON_KEY, icon_muted);
                    action.putValue(Action.SHORT_DESCRIPTION, "Mute all breakpoints");
                    ab.setBreakpointsActive(false);
                } else {
                    action.putValue(Action.SMALL_ICON, icon_unmuted);
                    action.putValue(Action.LARGE_ICON_KEY, icon_unmuted);
                    action.putValue(Action.SHORT_DESCRIPTION, "Unmute all breakpoints");
                    ab.setBreakpointsActive(true);
                }

            }
            action.setEnabled(ab.canDeactivateBreakpoints());
        }
    }
}
