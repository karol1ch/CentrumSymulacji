package sample.editor;

import com.mxgraph.swing.util.mxGraphActions;
import sample.editor.EditorActions.HistoryAction;

import javax.swing.*;

public class SchemaEditorToolBar extends JToolBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3979320704834605323L;

	/**
	 * 
	 * @param frame
	 * @param orientation
	 */
	private boolean ignoreZoomChange = false;

	/**
	 * 
	 */
	public SchemaEditorToolBar(final BasicGraphEditor editor, int orientation)
	{


		add(editor.bind("Delete", mxGraphActions.getDeleteAction(),
				"/com/mxgraph/examples/swing/images/delete.gif"));

		addSeparator();

		add(editor.bind("Undo", new HistoryAction(true),
				"/com/mxgraph/examples/swing/images/undo.gif"));
		add(editor.bind("Redo", new HistoryAction(false),
				"/com/mxgraph/examples/swing/images/redo.gif"));


	}
}
