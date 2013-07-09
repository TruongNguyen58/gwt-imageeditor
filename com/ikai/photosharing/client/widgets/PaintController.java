package com.ikai.photosharing.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;

public class PaintController {
	private List<HandlerRegistration> pencilHandlers = new ArrayList<HandlerRegistration>();
	private List<HandlerRegistration> lineHandlers = new ArrayList<HandlerRegistration>();
	private List<HandlerRegistration> rectHandlers = new ArrayList<HandlerRegistration>();
	private List<HandlerRegistration> roundHandlers = new ArrayList<HandlerRegistration>();
	private List<HandlerRegistration> textHandlers = new ArrayList<HandlerRegistration>();
	private List<HandlerRegistration> arrowHandlers = new ArrayList<HandlerRegistration>();
	
	private int indexCurrentImage = 1;
	private Image currentImage =null;
	private Image imageRoot = null;
	private List<Image> imagesSave = new ArrayList<Image>(); 

	public PaintController() {
		// TODO Auto-generated constructor stub
	}
	
	public void removeAllHandles() {
		pencilHandlers.clear();
		lineHandlers.clear();
		rectHandlers.clear();
		roundHandlers.clear();
		textHandlers.clear();
		arrowHandlers.clear();
	}

	public List<HandlerRegistration> getPencilHandlers() {
		return pencilHandlers;
	}

	public void setPencilHandlers(List<HandlerRegistration> pencilHandlers) {
		this.pencilHandlers = pencilHandlers;
	}

	public List<HandlerRegistration> getLineHandlers() {
		return lineHandlers;
	}

	public void setLineHandlers(List<HandlerRegistration> lineHandlers) {
		this.lineHandlers = lineHandlers;
	}

	public List<HandlerRegistration> getRectHandlers() {
		return rectHandlers;
	}

	public void setRectHandlers(List<HandlerRegistration> rectHandlers) {
		this.rectHandlers = rectHandlers;
	}


	public List<HandlerRegistration> getTextHandlers() {
		return textHandlers;
	}

	public void setTextHandlers(List<HandlerRegistration> textHandlers) {
		this.textHandlers = textHandlers;
	}

	public List<HandlerRegistration> getRoundHandlers() {
		return roundHandlers;
	}

	public void setRoundHandlers(List<HandlerRegistration> roundHandlers) {
		this.roundHandlers = roundHandlers;
	}

	public int getIndexCurrentImage() {
		return indexCurrentImage;
	}

	public void setIndexCurrentImage(int indexCurrentImage) {
		this.indexCurrentImage = indexCurrentImage;
	}

	public Image getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}

	public Image getImageRoot() {
		return imageRoot;
	}

	public void setImageRoot(Image imageRoot) {
		this.imageRoot = imageRoot;
	}


public List<Image> getImagesSave() {
	return imagesSave;
}

public void setImagesSave(List<Image> imagesSave) {
	this.imagesSave = imagesSave;
}

public List<HandlerRegistration> getArrowHandlers() {
	return arrowHandlers;
}
	
	
}
