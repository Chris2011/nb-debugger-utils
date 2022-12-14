package de.markiewb.netbeans.plugins.debuggerutils;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import org.netbeans.api.annotations.common.StaticResource;

public final class FloatableDebugBar implements MouseListener, MouseMotionListener {

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

    }

    public JToolBar getFloatableDebugBar() throws HeadlessException {
//        SwingUtilities.invokeLater(() -> {
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
//        });

        return toolBar;
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {

    }

//    @Override
//    public void sessionAdded(final Session sn) {
//        SwingUtilities.invokeLater(() -> {
//            actionsForPath = org.openide.util.Utilities.actionsForPath("Toolbars/Debug");
//            // see http://wiki.netbeans.org/DevFaqInvokeActionProgrammatically
//            List<Action> actionsForToolbar = new ArrayList<>();
//
//            toolBar.add(lblDraggable);
//            if (sn != null) {
//                for (Action action : actionsForPath) {
//                    // INFO: http://wiki.netbeans.org/DevFaqAddIconToContextMenu
//                    action.putValue(SMALL_ICON, ImageUtilities.loadImageIcon((String) action.getValue("iconBase"), false));
//                    action.putValue("iconBase", (String) action.getValue("iconBase"));
//
//                    actionsForToolbar.add(action);
//                }
//
//                for (Action action : actionsForToolbar) {
//                    toolBar.add(action);
//                }
//
////                floatableToolbarWindow.add(lblDraggable);
//                floatableToolbarWindow.add(toolBar);
//                floatableToolbarWindow.pack();
//                floatableToolbarWindow.setVisible(true);
//
//                mainWindow = WindowManager.getDefault().getMainWindow();
//
//                floatableToolbarWindow.setLocation((int) (mainWindow.getWidth() / 2.5), mainWindow.getLocation().y);
//
////                JOptionPane.showMessageDialog(null, "X: " + mainWindow.getLocation().x + " & Y: " + mainWindow.getLocation().y);
////                shadowPane.setDimension(floatableToolbarWindow.getWidth(), floatableToolbarWindow.getHeight());
//            }
//        });
//    }
//
//    @Override
//    public void sessionRemoved(Session sn) {
//        toolBar.removeAll();
//        floatableToolbarWindow.remove(toolBar);
//        floatableToolbarWindow.setVisible(false);
//    }
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
