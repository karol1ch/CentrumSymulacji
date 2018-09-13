package sample.editor;

import com.mxgraph.swing.util.mxGraphActions;
import sample.editor.EditorActions.HistoryAction;

import javax.swing.*;

public class EditorToolBar extends JToolBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015443128436394471L;

	/**
	 * 
	 * @param frame
	 * @param orientation
	 */
	private boolean ignoreZoomChange = false;

	/**
	 * 
	 */
	public EditorToolBar(final BasicGraphEditor editor, int orientation)
	{
		super(orientation);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(3, 3, 3, 3), getBorder()));
		setFloatable(false);



		addSeparator();

		add(editor.bind("Delete", mxGraphActions.getDeleteAction(),
				"/sample/images/delete.gif"));

		addSeparator();

		add(editor.bind("Undo", new HistoryAction(true),
				"/sample/images/undo.gif"));
		add(editor.bind("Redo", new HistoryAction(false),
				"/sample/images/redo.gif"));

	}
}
