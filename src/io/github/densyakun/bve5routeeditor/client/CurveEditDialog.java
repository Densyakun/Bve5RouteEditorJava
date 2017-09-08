package io.github.densyakun.bve5routeeditor.client;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.HashMap;

import javax.swing.JDialog;

import io.github.densyakun.bve5routeeditor.common.linear.Linear;

@SuppressWarnings("serial")
public class CurveEditDialog extends JDialog implements ActionListener, TextListener {

	double distance = 0.0;
	TextArea distanceText;
	Button okButton;
	Button closeButton;

	public CurveEditDialog(Dialog owner) {
		super(owner, "曲線編集・追加ツール");
		setResizable(false);

		setLayout(new FlowLayout());

		add(distanceText = new TextArea("", 1, 10, TextArea.SCROLLBARS_NONE));
		distanceText.addTextListener(this);
		c();

		add(okButton = new Button("OK"));
		okButton.addActionListener(this);

		add(closeButton = new Button("閉じる"));
		closeButton.addActionListener(this);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			a(distanceText.getText());
			Linear linear = Client.map.getOwn_Linear();
			HashMap<Double, Double> a = linear.getCurvePoints();
			a.put(distance, 1000.0);
			linear.setCurvePoints(a);
			Client.mapreload();
		} else if (e.getSource() == closeButton) {
			setVisible(false);
		}
	}

	@Override
	public void textValueChanged(TextEvent e) {
		String c = distanceText.getText();
		if (b(c)) {
			a(c);
		}
	}

	private void a(String str) {
		try {
			distance = Double.valueOf(str.replaceAll(System.getProperty("line.separator"), ""));
			c();
		} catch (NumberFormatException x) {
		}
	}

	private boolean b(String str) {
		return str.indexOf(System.getProperty("line.separator")) != -1;
	}

	private void c() {
		distanceText.setText("" + distance);
		distanceText.selectAll();
	}
}
