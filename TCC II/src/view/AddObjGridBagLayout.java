package view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import javax.swing.*;

public class AddObjGridBagLayout {

	public static GridBagConstraints restricoes = new GridBagConstraints();

	public static void addObjetoGridBagLayout(JPanel pnl, Component obj, int x, int y, int gridwidth) {
		restricoes.gridx = x;
		restricoes.gridy = y;
		restricoes.gridwidth = gridwidth;
		pnl.add(obj, restricoes);
	}

}