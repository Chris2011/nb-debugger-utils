package javax.swing.plaf.basic;

import java.awt.Window;

/**
 *
 * @author markiewb
 */
public class BasicToolBarUIAccessHack{
    public static Window getFloatingWindow(BasicToolBarUI ui){
        return ui.dragWindow;
    }
}
