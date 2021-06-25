package EE402;

import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")


public class IntTextField extends TextField implements KeyListener {
    
    public IntTextField(int size){
            super(size);
            this.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if(Character.isDigit(c)||c==KeyEvent.VK_DELETE||c==KeyEvent.VK_BACK_SPACE){
                    System.out.println("Numeric key pressed");
            }
            else {
                    System.out.println("Non numeric key pressed");
                    e.consume();
            }        
    }
}
