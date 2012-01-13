/*
 * This file is part of SpoutAPI (http://www.spout.org/).
 *
 * SpoutAPI is licensed under the SpoutDev license version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev license version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://getspout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.api.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenericRadioButton extends GenericButton implements RadioButton {

	/** Current version for serialisation and packet handling.*/
	private static final long serialVersionUID = 0L;
	private boolean selected = false;
	private int group = 0;

	public GenericRadioButton() {
	}

	public GenericRadioButton(String text) {
		super(text);
	}

	public GenericRadioButton(int width, int height) {
		super(width, height);
	}

	public GenericRadioButton(int width, int height, String text) {
		super(width, height, text);
	}

	public GenericRadioButton(int X, int Y, int width, int height) {
		super(X, Y, width, height);
	}

	public GenericRadioButton(int X, int Y, int width, int height, String text) {
		super(X, Y, width, height, text);
	}

	@Override
	public int getVersion() {
		return super.getVersion() + (int) serialVersionUID;
	}

	@Override
	public int getNumBytes() {
		return super.getNumBytes() + 2;
	}

	@Override
	public void readData(DataInputStream input) throws IOException {
		super.readData(input);
		selected = input.readBoolean();
		group = input.readInt();
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		super.writeData(output);
		output.writeBoolean(isSelected());
		output.writeInt(getGroup());
	}

	@Override
	public WidgetType getType() {
		return WidgetType.RadioButton;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public RadioButton setSelected(boolean selected) {
		if (selected) {
			for (RadioButton b : getRadiosInGroup()) {
				b.setSelected(false);
			}
		}
		this.selected = selected;
		return this;
	}

	@Override
	public int getGroup() {
		return group;
	}

	@Override
	public RadioButton setGroup(int group) {
		this.group = group;
		return this;
	}

	public List<RadioButton> getRadiosInGroup() {
		List<RadioButton> ret = new ArrayList<RadioButton>();
		for (Widget w : getScreen().getChildren(true)) {
			if (w instanceof RadioButton) {
				if (((RadioButton) w).getGroup() == group) {
					ret.add((RadioButton) w);
				}
			}
		}
		return ret;
	}
}