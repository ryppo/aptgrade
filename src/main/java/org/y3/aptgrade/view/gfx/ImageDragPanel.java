package org.y3.aptgrade.view.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.y3.aptgrade.view.i18n.Messages;

/**
 *
 * @author christian.rybotycky
 */
public class ImageDragPanel extends JPanel {

    private Image image;
    private Image imageToPaint;
    private double maxWidthAndHeight = 800;
    private JButton jb_removePicture; 
    private double prefWidth = maxWidthAndHeight;
    private double prefHeight = maxWidthAndHeight;

    public ImageDragPanel(double _maxWidthAndHeight) {
        maxWidthAndHeight = _maxWidthAndHeight;
        setTransferHandler(new ImageTransferHandler(this));
        setBackground(getBackground().brighter());
        jb_removePicture = new JButton(new Messages().getString(Messages.REMOVE_PICTURE));
        jb_removePicture.addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageDragPanel.this.setImage(null);
            }
        });    
        setLayout(new FlowLayout());
        add(jb_removePicture);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) prefWidth, (int) prefHeight);
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setImage(Image _image) {
        image = _image;
        if (image == null || image.getWidth(this) == 0 || image.getHeight(this) == 0) {
            imageToPaint = null;
        } else {
            double scaleFactor = 1;
            int imageHeight = image.getHeight(this);
            int imageWidth = image.getWidth(this);
            if (imageHeight > imageWidth && imageHeight > maxWidthAndHeight) {
                scaleFactor = maxWidthAndHeight / imageHeight;
            } else if (imageWidth > maxWidthAndHeight) {
                scaleFactor = maxWidthAndHeight / imageWidth;
            }
            int paintWidth = (int) (imageWidth * scaleFactor);
            prefWidth = paintWidth;
            prefHeight = (imageHeight * scaleFactor);
            imageToPaint = image.getScaledInstance(paintWidth, (int) prefHeight, Image.SCALE_SMOOTH);
        }
        paint(getGraphics());
    }

    void addFiles(File[] files) {
        for (File file : files) {
            try {
                setImage(ImageIO.read(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (image != null) {
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        if (imageToPaint != null) {
            setSize(new Dimension(imageToPaint.getWidth(this), imageToPaint.getHeight(this)));
        }
        g.setColor(Color.BLUE);
        int width = getWidth();
        int height = getHeight();
        drawImage((Graphics2D) g, width, height);
    }

    private void drawImage(Graphics2D g2, int width, int height) {
        if (imageToPaint != null) {
            g2.drawImage(imageToPaint, 0, 0, width, height, null);
        } else {
            g2.drawRect(10, 10, width - 20, height - 20);
        }
    }
    
    public void setEditable(boolean editable) {
        setEnabled(editable);
        jb_removePicture.setEnabled(editable);
    }

}
