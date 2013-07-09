package com.ikai.photosharing.client.widgets;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CanvasView extends VerticalPanel {
	
	private HorizontalPanel imageSave = new HorizontalPanel();
	private Canvas canvas;
	private Context2d context;
	private int x1, y1, x2, y2;
	private boolean isTab = false;
	
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public CanvasView(int width,int height) {
		canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		canvas.getElement().setAttribute("style", "background-color:gray");
		context = canvas.getContext2d();
		
		this.add(getCanvas());
		this.add(imageSave);
		
		
	}
	
	public void drawImage(Image image){
		canvas.setCoordinateSpaceWidth(image.getWidth());
		canvas.setCoordinateSpaceHeight(image.getHeight());
		
		ImageElement image1 = ImageElement.as(image.getElement());
		context.drawImage(image1, 0, 0, image.getWidth(), image.getHeight());
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Context2d getContext() {
		context.setStrokeStyle(ToolView.color);
		return context;
	}

	public void setContext(Context2d context) {
		this.context = context;
	}

	public boolean getIsTab(){
		return isTab;
	}
	
	public void setIsTab(boolean b) {
		isTab = b;
	}

	public HorizontalPanel getImageSave() {
		return imageSave;
	}

	public void setImageSave(HorizontalPanel imageSave) {
		this.imageSave = imageSave;
	}
	
}
