/* 
 * Copyright 2016 markiewb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
@Messages("CTL_SkipAllBreakpoints=Skip all breakpoin&ts")
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

    @Override
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
