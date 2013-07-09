package com.ikai.photosharing.client.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class AddTextDialogView extends DialogBox {
	
	private HorizontalPanel horizontalPanel = new HorizontalPanel();
	private TextBox textBox = new TextBox();
	private Button ok = new Button("Ok");
	private ListBox size = new ListBox();
	
	private String fontSize = "10px";
	
	public AddTextDialogView() {
		horizontalPanel.add(textBox);
		horizontalPanel.add(size);
		horizontalPanel.add(ok);
		
		this.setWidget(horizontalPanel);
		
		textBox.getElement().setAttribute("style", "background: transparent; z-index: 1;");
		size.getElement().setAttribute("style", "background: transparent; z-index: 1;");
		
		setDataListBoxSize();
		
		bind();
	}
	
	private void setDataListBoxSize(){
		for (int i = 10; i <= 30; i+=2) {
			size.addItem(i+"px", i+"px");
		}
	}
	
	private void bind(){
		size.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				int index = size.getSelectedIndex();
				fontSize = size.getValue(index);
				textBox.setFocus(true);
			}
		});
	}

	public HorizontalPanel getHorizontalPanel() {
		return horizontalPanel;
	}

	public void setHorizontalPanel(HorizontalPanel horizontalPanel) {
		this.horizontalPanel = horizontalPanel;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}

	public Button getOk() {
		return ok;
	}

	public void setOk(Button ok) {
		this.ok = ok;
	}

	public ListBox getSize() {
		return size;
	}

	public void setSize(ListBox size) {
		this.size = size;
	}
	
	public String getFontSize() {
		return fontSize;
	}
	
}
