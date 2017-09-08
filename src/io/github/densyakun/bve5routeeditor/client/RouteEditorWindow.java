package io.github.densyakun.bve5routeeditor.client;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class RouteEditorWindow extends JDialog implements ActionListener {

	Button curveEditButton;
	Button curveDeleteButton;

	public RouteEditorWindow() {
		super((Dialog) null, "RouteEditor");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//setDefaultCloseOperation(HIDE_ON_CLOSE); TODO
		setResizable(false);

		setLayout(new FlowLayout());

		add(curveEditButton = new Button("曲線編集・追加ツール"));
		curveEditButton.addActionListener(this);

		add(curveDeleteButton = new Button("曲線削除ツール"));
		curveDeleteButton.addActionListener(this);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == curveEditButton) {
			new CurveEditDialog(this).setVisible(true);
		} else if (e.getSource() == curveDeleteButton) {
			new CurveDeleteDialog(this).setVisible(true);
		}
	}

}
