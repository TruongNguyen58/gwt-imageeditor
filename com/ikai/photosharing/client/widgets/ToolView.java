package com.ikai.photosharing.client.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ToolView extends VerticalPanel{
	
	private  Button pencilButton = new Button("Pencil");
	private  Button lineButton  = new Button("Line");
	private  Button rectButton  = new Button("Rect");
	private  Button roundButton  = new Button("Round");
	private  Button arrowButton = new Button("Arrow");
	private  Button textButton = new Button("Text");
	
	private  Button loadImageButton  = new Button("LoadImage");
	private  Button saveButton  = new Button("Save");
	private Button nextButton = new Button("Next");
	private Button backButon = new Button("Back");
	
	private VerticalPanel colorPanel = new VerticalPanel();
	
	private String width = "";
	private String height = "";
	
	public static String color = "black";
	String colorValue = "black";
	
	public ToolView(String width,String height) {
		this.height = height;
		this.width = width;
		DOM.setStyleAttribute(btnCurrentColor.getElement(), "backgroundColor",ToolView.this.color);
		
		draw("images/pencil.png", pencilButton);
		draw("images/line.png", lineButton);
		draw("images/rect.png", rectButton);
		draw("images/round.png", roundButton);
		draw("images/arrow.png", arrowButton);
		draw("images/text.gif", textButton);
		
		draw("images/save.png", saveButton);
		draw("images/erase.png", loadImageButton);
		draw("images/next.png", nextButton);
		draw("images/back.png", backButon);
		
		FlexTable flexTable = new FlexTable();
		
		flexTable.setWidget(0, 0,pencilButton);
		flexTable.setWidget(0, 1,lineButton);
		flexTable.setWidget(1, 0,rectButton);
		flexTable.setWidget(1, 1,roundButton);
		flexTable.setWidget(2, 0,arrowButton);
		flexTable.setWidget(2, 1,textButton);
			
		flexTable.setWidget(3, 0,saveButton);
		flexTable.setWidget(3, 1,loadImageButton);
//		flexTable.setWidget(4, 0,nextButton);
//		flexTable.setWidget(4, 1,backButon);
			
		this.add(flexTable);
		
		drawLineBold();
		
		
	}
	
	Button btnCurrentColor = new Button();
	
	private void drawLineBold(){
		
		VerticalPanel panel = new VerticalPanel();
		
		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, genColor(0));
		flexTable.setWidget(0, 1, genColor(1));
		flexTable.setWidget(0, 2, genColor(2));
		flexTable.setWidget(0, 3, genColor(3));
		flexTable.setWidget(1, 0, genColor(4));
		flexTable.setWidget(1, 1, genColor(5));
		flexTable.setWidget(1, 2, genColor(6));
		flexTable.setWidget(1, 3, genColor(7));
		
		panel.add(flexTable);
		btnCurrentColor.setSize("68"+"px", "30"+"px");
		panel.add(btnCurrentColor);
		btnCurrentColor.getElement().getStyle().setMarginLeft(1, Unit.PX);
		btnCurrentColor.setTitle("Current color");
		panel.getElement().getStyle().setMarginLeft(0, Unit.PX);
		
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.getElement().getStyle().setMarginLeft(1, Unit.PX);
		horizontalPanel.getElement().getStyle().setMarginTop(5, Unit.PX);
		
		horizontalPanel.add(backButon);
		horizontalPanel.add(nextButton);
		panel.add(horizontalPanel);
		
		this.add(panel);
	}
	
	
	private Button genColor(int color){
		final Button b = new Button();
		 
		switch (color) {
		case 0:
			colorValue = "blue";
			break;
			
		case 1:
			colorValue = "red";
			break;
			
		case 2:
			colorValue = "white";
			break;
			
		case 3:
			colorValue = "gray";
			break;
			
		case 4:
			colorValue = "black";
			break;
			
		case 5:
			colorValue = "yellow";
			break;
			
		case 6:
			colorValue = "green";
			break;
			
		case 7:
			colorValue = "pink";
			break;

		default:
			break;
		}
		DOM.setStyleAttribute(b.getElement(), "backgroundColor",colorValue);
//		b.getElement().setAttribute("style", "background-color : " + colorValue);
		b.getElement().setTitle(colorValue);
		b.setSize("15"+"px", "15"+"px");
		b.addClickHandler(new ClickHandler() {
			String color = colorValue;
			@Override
			public void onClick(ClickEvent event) {
				ToolView.this.color = color;
				DOM.setStyleAttribute(btnCurrentColor.getElement(), "backgroundColor",ToolView.this.color);
			}
		});
		
		return b;
	}
	
	private void draw(String imageLink,Button button){
		button.setHTML("<image src='"+ imageLink+"' height='"+height+"' width='"+width+"'/>");
		String s= imageLink.replace("images/","");
		s = s.replace(".", "/");
		s = s.split("/")[0];
		button.getElement().setTitle(s);
	}

	public Button getPencilButton() {
		return pencilButton;
	}

	public void setPencilButton(Button pencilButton) {
		this.pencilButton = pencilButton;
	}

	public Button getTextButton() {
		return textButton;
	}

	public void setTextButton(Button textButton) {
		this.textButton = textButton;
	}

	public Button getLoadImageButton() {
		return loadImageButton;
	}

	public void setLoadImageButton(Button loadImageButton) {
		this.loadImageButton = loadImageButton;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	public Button getLineButton() {
		return lineButton;
	}

	public void setLineButton(Button lineButton) {
		this.lineButton = lineButton;
	}

	public Button getRectButton() {
		return rectButton;
	}

	public void setRectButton(Button rectButton) {
		this.rectButton = rectButton;
	}

	public Button getRoundButton() {
		return roundButton;
	}

	public void setRoundButton(Button roundButton) {
		this.roundButton = roundButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public void setNextButton(Button nextButton) {
		this.nextButton = nextButton;
	}

	public Button getBackButon() {
		return backButon;
	}

	public void setBackButon(Button backButon) {
		this.backButon = backButon;
	}
	
	public Button getArrowButton() {
		return arrowButton;
	}
	
	
	
}
