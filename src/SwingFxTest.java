import java.awt.Color;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SwingFxTest extends Application {

    private DragSource dragSource;
    private DropTarget dropTarget;
    private DropTargetListener dropListener;
    private DragGestureListener dgListener;
    private DragGestureRecognizer dragRecognizer;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        final SwingNode swingNode = new SwingNode();

        createSwingContent(swingNode);
        BorderPane pane = new BorderPane();
        pane.setCenter(swingNode);

        stage.setTitle("Swing in JavaFX");
        stage.setScene(new Scene(pane, 250, 150));
        stage.onCloseRequestProperty().addListener(new InvalidationListener(){
            @Override public void invalidated(Observable observable) {
                System.exit(0);
            }
        });
        stage.show();
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                JPanel panel = new JPanel();
                panel.setBackground(Color.RED);
                panel.add(new JLabel("Drag me!"));

                swingNode.setContent(panel);

                dgListener = new DragGestureListener() {
                    @Override public void dragGestureRecognized(DragGestureEvent dge) {
                        System.out.println("drag recognized...");
                        dge.startDrag(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR), new StringSelection("some string data"));
                    }
                };

                dropListener = new DropTargetAdapter() {
                    @Override public void drop(DropTargetDropEvent dtde) {
                        //this is never called =(
                        System.out.println("drop... " + dtde.getTransferable());
                    }
                };

                dragSource = new DragSource();
                dragRecognizer = dragSource.createDefaultDragGestureRecognizer(panel, DnDConstants.ACTION_COPY_OR_MOVE, dgListener);
                dropTarget = new DropTarget(panel, dropListener);
                System.out.println(dragRecognizer);
                System.out.println(dropTarget);

            }
        });
    }
}
