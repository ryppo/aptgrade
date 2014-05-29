package org.y3.aptgrade.view.gfx;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 *
 * @author christian.rybotycky
 */
public class ImageTransferHandler  extends TransferHandler {

        private static final DataFlavor FILE_FLAVOR = DataFlavor.javaFileListFlavor;

        private final ImageDragPanel imageDragPanel;

        public ImageTransferHandler(ImageDragPanel _imageDragPanel) {
            imageDragPanel = _imageDragPanel;
        }

        @Override
        public boolean importData(JComponent c, Transferable t) {
            if (canImport(c, t.getTransferDataFlavors())) {
                if (transferFlavor(t.getTransferDataFlavors(), FILE_FLAVOR)) {
                    try {
                        List<File> fileList = (List<File>) t.getTransferData(FILE_FLAVOR);
                        if (fileList != null && fileList.toArray() instanceof File[]) {
                            File[] files = (File[]) fileList.toArray();
                            imageDragPanel.addFiles(files);
                        }
                        return true;
                    } catch (            IOException | UnsupportedFlavorException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        @Override
        protected void exportDone(JComponent c, Transferable data, int action) {
            c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        private boolean transferFlavor(DataFlavor[] flavors, DataFlavor flavor) {
            boolean found = false;
            for (int i = 0; i < flavors.length && !found; i++) {
                found = flavors[i].equals(flavor);
            }
            return found;
        }

        @Override
        public boolean canImport(JComponent c, DataFlavor[] flavors) {
            for (DataFlavor flavor : flavors) {
                if (FILE_FLAVOR.equals(flavor)) {
                    return true;
                }
            }
            return false;
        }
    }