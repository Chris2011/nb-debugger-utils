/*
 * Copyright (C) 2015 markiewb
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.markiewb.netbeans.plugins.debuggerutils;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import org.netbeans.api.debugger.Breakpoint;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.api.debugger.DebuggerManagerListener;
import org.netbeans.api.debugger.Session;
import org.netbeans.api.debugger.Watch;
import org.netbeans.spi.editor.SideBarFactory;

/**
 *
 * @author markiewb
 */
public class FloatableDebugBarSideBarFactory implements SideBarFactory, DebuggerManagerListener {
    private final JPanel jPanel = new JPanel(new BorderLayout());
    private final FloatableDebugBar floatableDebugBar = new FloatableDebugBar();
    private final JButton t = new JButton();

    @Override
    public JComponent createSideBar(JTextComponent jtc) {
        t.setText("clicker");

        jPanel.add(new JLabel(" "), BorderLayout.WEST);
        jPanel.add(t, BorderLayout.CENTER);

        return jPanel;
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
    public void sessionAdded(Session sn) {
//        jPanel.add(floatableDebugBar.getFloatableDebugBar(), BorderLayout.CENTER);
        jPanel.removeAll();
        jPanel.add(new JLabel("Geeeeeht"));
        jPanel.repaint();
        jPanel.updateUI();
    }

    @Override
    public void sessionRemoved(Session sn) {
        jPanel.removeAll();
    }

    @Override
    public void engineAdded(DebuggerEngine de) {

    }

    @Override
    public void engineRemoved(DebuggerEngine de) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {

    }
}
