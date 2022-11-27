package de.markiewb.netbeans.plugins.debuggerutils;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.debugger.Breakpoint;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManagerListener;
import org.netbeans.api.debugger.Session;
import org.netbeans.api.debugger.Watch;
import org.openide.util.ImageUtilities;
import org.openide.windows.WindowManager;

public final class FloatableDebugBar implements DebuggerManagerListener, MouseListener, MouseMotionListener {

    private List<? extends Action> actionsForPath;
    private JToolBar toolBar;
    private JFrame floatableToolbarWindow;
    private ShadowPane shadowPane;
    private Frame mainWindow;
    private JLabel lblDraggable;

    @StaticResource
    private static final String dragIcon = "de/markiewb/netbeans/plugins/debuggerutils/dragIcon_20.png";

    // see http://stackoverflow.com/a/11238100
    public FloatableDebugBar() {
        SwingUtilities.invokeLater(() -> {
            toolBar = new JToolBar();
            floatableToolbarWindow = new JFrame();
            shadowPane = new ShadowPane();
//            lblDraggable = new JLabel(new ImageIcon(dragIcon));
            lblDraggable = new JLabel("dragicon");

            floatableToolbarWindow.setResizable(true);
//            floatableToolbarWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            floatableToolbarWindow.setUndecorated(false);
//            floatableToolbarWindow.setBackground(new Color(255, 0, 0, 0));
//            floatableToolbarWindow.setLocation(250, 250);

            lblDraggable.setSize(16, 16);
            lblDraggable.setLayout(new BorderLayout());
//            lblDraggable.setOpaque(false);
//            lblDraggable.setHorizontalAlignment(JLabel.CENTER);
//            lblDraggable.setBorder(new EmptyBorder(10, 10, 10, 10));
            lblDraggable.setBorder(BorderFactory.createLineBorder(Color.black));
            lblDraggable.setIconTextGap(5);
            toolBar.setBorder(new EmptyBorder(10, 10, 10, 10));

//            floatableToolbarWindow.setContentPane(shadowPane);
//            toolBar.add(lblDraggable);
            toolBar.setFloatable(false);
        });
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
        SwingUtilities.invokeLater(() -> {
            actionsForPath = org.openide.util.Utilities.actionsForPath("Toolbars/Debug");
            // see http://wiki.netbeans.org/DevFaqInvokeActionProgrammatically
            List<Action> actionsForToolbar = new ArrayList<>();

            toolBar.add(lblDraggable);
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

//                floatableToolbarWindow.add(lblDraggable);
                floatableToolbarWindow.add(toolBar);
                floatableToolbarWindow.pack();
                floatableToolbarWindow.setVisible(true);

                mainWindow = WindowManager.getDefault().getMainWindow();

                floatableToolbarWindow.setLocation((int) (mainWindow.getWidth() / 2.5), mainWindow.getLocation().y);

//                JOptionPane.showMessageDialog(null, "X: " + mainWindow.getLocation().x + " & Y: " + mainWindow.getLocation().y);
//                shadowPane.setDimension(floatableToolbarWindow.getWidth(), floatableToolbarWindow.getHeight());
            }
        });
    }

    @Override
    public void sessionRemoved(Session sn) {
        toolBar.removeAll();
        floatableToolbarWindow.remove(toolBar);
        floatableToolbarWindow.setVisible(false);
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

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private class ShadowPane extends JPanel {

        private int width = 0;
        private int height = 0;

        public ShadowPane() {
//            super(new GridBagLayout());

            setLayout(new BorderLayout());
            setOpaque(false);
            setBackground(Color.YELLOW);
            setBorder(new EmptyBorder(10, 10, 10, 10));
        }

        public void setDimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            g2d.fillRect(10, 10, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}
