package de.markiewb.netbeans.plugins.debuggerutils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
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
import org.openide.util.actions.BooleanStateAction;

@ActionID(
        category = "Debug",
        id = "de.markiewb.netbeans.plugins.debuggerutils.SkipAllBreakpoints"
)
@ActionRegistration(
        lazy = false,
        displayName = "#CTL_SkipAllBreakpoints"
)
@ActionReferences({
    @ActionReference(path = "Toolbars/Debug", position = 1050),
    @ActionReference(path = "Menu/RunProject", position = 2350)
}
)
@Messages("CTL_SkipAllBreakpoints=Skip all breakpoints")
/**
 * See
 * http://bits.netbeans.org/dev/javadoc/org-openide-util-ui/org/openide/util/actions/BooleanStateAction.html
 */
public final class SkipAllBreakpoints extends BooleanStateAction implements PropertyChangeListener {

    @StaticResource
    private static final String iconpath_muted = "de/markiewb/netbeans/plugins/debuggerutils/Breakpoint_stroke.png";

    public SkipAllBreakpoints() {
        addPropertyChangeListener(this);

        setBooleanState(false);
        setLabel(false);
    }

    public void handleToggle() {
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PROP_BOOLEAN_STATE)) {
            handleToggle();
        }
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public String getName() {
        return Bundle.CTL_SkipAllBreakpoints();
    }

    public void setLabel(final boolean muted) {
        if (muted) {
            putValue(Action.SHORT_DESCRIPTION, "[Disabled] Check to skip all breakpoints");
        } else {
            putValue(Action.SHORT_DESCRIPTION, "[Enabled] Uncheck to unskip all breakpoints");
        }
    }

    @Override
    protected String iconResource() {
        return iconpath_muted;
    }
}
