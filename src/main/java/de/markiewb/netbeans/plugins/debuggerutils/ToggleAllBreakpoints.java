package de.markiewb.netbeans.plugins.debuggerutils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.debugger.ActiveBreakpoints;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.*;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Debug",
        id = "de.markiewb.netbeans.plugins.debuggerutils.ToggleAllBreakpoints"
)
@ActionRegistration(
        iconBase = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint_stroke.png",
        displayName = "#CTL_ToggleAllBreakpoints"
)
@ActionReferences({
    @ActionReference(path = "Toolbars/Debug", position = 1050),
    @ActionReference(path = "Menu/RunProject", position = 2350)
}
)
@Messages("CTL_ToggleAllBreakpoints=Disable/enable breakpoints")
public final class ToggleAllBreakpoints extends AbstractAction implements ActionListener {

    @StaticResource
    private static final String iconpath_unmuted = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint.png";
    @StaticResource
    private static final String iconpath_muted = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint_stroke.png";

    Icon icon_muted = ImageUtilities.loadImageIcon(iconpath_muted, false);
    Icon icon_unmuted = ImageUtilities.loadImageIcon(iconpath_unmuted, false);

    public ToggleAllBreakpoints() {
        setLabel(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DebuggerEngine _engine = DebuggerManager.getDebuggerManager().getCurrentEngine();

        if (null != _engine) {
            ActiveBreakpoints ab = ActiveBreakpoints.get(_engine);

            if (ab.canDeactivateBreakpoints()) {
                final boolean active = ab.areBreakpointsActive();
                ab.setBreakpointsActive(!active);
                setLabel(!active);
            } else {
                setLabel(false);
            }
        } else {
            setLabel(false);
        }
    }

    public void setLabel(final boolean muted) {
        final AbstractAction action = this;
        if (muted) {
            action.putValue(Action.SMALL_ICON, icon_muted);
            action.putValue(Action.LARGE_ICON_KEY, icon_muted);
            action.putValue(Action.SHORT_DESCRIPTION, "[Disabled] Press to skip all breakpoints");
        } else {
            action.putValue(Action.SMALL_ICON, icon_unmuted);
            action.putValue(Action.LARGE_ICON_KEY, icon_unmuted);
            action.putValue(Action.SHORT_DESCRIPTION, "[Enabled] Press to unskip all breakpoints");
        }
    }
}
