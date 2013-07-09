package com.ikai.photosharing.client.widgets;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.reveregroup.gwt.imagepreloader.FitImage;

public class PaintView extends HorizontalPanel {
	private AddTextDialogView textDialogView = new AddTextDialogView();
	private CanvasView canvasView = new CanvasView(300, 300);
	private ToolView toolView = new ToolView("20","20");
	private PaintController paintController = new PaintController();
	int XDialog,YDialog,XText,YText;
	private int width;
	
	enum action {PENCIl,LINE ,ECLIPSE,RECT,TEXT,ARROW}
	action currentAcion ;
	
//	private DialogBox main = new DialogBox();
	
	
	public static String URLServerDefault = GWT.getModuleBaseURL() +"getImageFromUrl";	
	private String imageUrl = "";
	
	public PaintView(int width,int height,String imageUrl,String URLServerDefault,ClickHandler clickHandler) {
		
		if(URLServerDefault!=null && !URLServerDefault.isEmpty()){
			this.URLServerDefault = URLServerDefault;
		}
		if(imageUrl!=null && !imageUrl.isEmpty()){
			this.imageUrl = imageUrl;
		}

		this.add(toolView);
		this.add(canvasView);
		
		
		bindOfEclipse();
		bindOfLine();
		bindOfLoadImage();
		bindOfPencil();
		bindOfRect();
		binOfSave(clickHandler);
		bindOfBack();
		bindOfNext();
		bindOfText();
		bindOfArrow();
		loadImage();
		bind();
	}
	
	public PaintView(int width, int height, ClickHandler saveHandler) {
		this.add(toolView);
		this.add(canvasView);
		this.width = width;
		bindOfEclipse();
		bindOfLine();
		bindOfLoadImage();
		bindOfPencil();
		bindOfRect();
		binOfSave(saveHandler);
		bindOfBack();
		bindOfNext();
		bindOfText();
		bindOfArrow();
		bind();
	}
	
	public void loadUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		loadImage();
	}
	
	public void drawArrow(int x1,int y1,int  x2, int y2){
		 double deltaX = x2 - x1;
		 double deltaY = y1 - y2;
		 double dirRadians = Math.atan2(deltaY, deltaX);
		 double dirDegrees = dirRadians*180.0/Math.PI;
		 double dirOpposite = dirDegrees + 180.0;
		 while (dirOpposite > 360) {
		 dirOpposite = dirOpposite - 360;
		 }
		 while (dirOpposite < 0) {
		 dirOpposite = dirOpposite + 360;
		 }
		 double deltaX1 = 10*Math.cos((dirOpposite+40)*Math.PI/180.0);
		 double deltaY1 = 10*Math.sin((dirOpposite+40)*Math.PI/180.0);
		 double deltaX2 = 10*Math.cos((dirOpposite-40)*Math.PI/180.0);
		 double deltaY2 = 10*Math.sin((dirOpposite-40)*Math.PI/180.0);

		 int xx1 = (int) (x2 + deltaX1);
		 int yy1 = (int) (y2 - deltaY1);

		 int xx2 = (int) (x2 + deltaX2);
		 int yy2 = (int) (y2 - deltaY2);
		 
		 canvasView.getContext().moveTo(x2, y2);
		 canvasView.getContext().lineTo(xx1, yy1);
		 canvasView.getContext().stroke();
		 
		 canvasView.getContext().moveTo(x2, y2);
		 canvasView.getContext().lineTo(xx2, yy2);
		 canvasView.getContext().stroke();
		 
	 }

	private void bind(){
		
		toolView.getLoadImageButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				canvasView.getContext().clearRect(0, 0, canvasView.getCanvas().getCoordinateSpaceWidth(), canvasView.getCanvas().getCoordinateSpaceHeight());
				paintController.setCurrentImage(null);
				
			}
		});
		
		
		textDialogView.getOk().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.TEXT;
				textDialogView.hide();
				if(!textDialogView.getTextBox().getText().isEmpty()){
					canvasView.getContext().setFont(textDialogView.getFontSize()+" Arial");
					canvasView.getContext().setFillStyle(ToolView.color);
					
					canvasView.getContext().fillText(textDialogView.getTextBox().getText().trim(), XDialog-XText , YDialog-YText+5+5, 100);
					saveAuto();
				}
			}
		});
	}


	public void showImg(Image image) {
		if(image!=null){
			canvasView.drawImage(image);
		}
	}
	
	
	private void bindOfArrow(){
		toolView.getArrowButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.ARROW;
				removeAllHandle();
				
				paintController.getArrowHandlers().add(canvasView.getCanvas().addMouseDownHandler(new MouseDownHandler() {
					@Override
					public void onMouseDown(MouseDownEvent event) {
						if(currentAcion == action.ARROW){
							canvasView.setIsTab(true);
							canvasView.setX1(event.getX());
							canvasView.setY1(event.getY());
						}
					}
				}));
				
				paintController.getArrowHandlers().add(canvasView.getCanvas().addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(MouseMoveEvent event1) {
						if(currentAcion == action.ARROW && canvasView.getIsTab()){
							canvasView.setX2(event1.getX());
							canvasView.setY2(event1.getY());
							
							canvasView.getContext().clearRect(0, 0,  canvasView.getCanvas().getCoordinateSpaceWidth(), canvasView.getCanvas().getCoordinateSpaceHeight());
							showImg(paintController.getCurrentImage());
							
							canvasView.getContext().beginPath();
							canvasView.getContext().moveTo(canvasView.getX1(), canvasView.getY1());
							canvasView.getContext().lineTo(canvasView.getX2(), canvasView.getY2());
							canvasView.getContext().setStrokeStyle("red");
							
							//
							drawArrow(canvasView.getX1(), canvasView.getY1(), canvasView.getX2(), canvasView.getY2());
							//
							canvasView.getContext().stroke();
							canvasView.getContext().closePath();
						}
					}
				}));
				
				paintController.getLineHandlers().add(canvasView.getCanvas().addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event2) {
						canvasView.setIsTab(false);
						saveAuto();
					}
				}));	
			}
		});
	}
	
	private void bindOfText(){
		toolView.getTextButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.TEXT;
				removeAllHandle();
				paintController.getTextHandlers().add(canvasView.getCanvas().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if(currentAcion == action.TEXT ){
							XDialog = event.getClientX();
							YDialog = event.getClientY();
							textDialogView.setPopupPosition(XDialog-5, YDialog-5);
							textDialogView.show();
							textDialogView.getTextBox().setFocus(true);
							textDialogView.getTextBox().setText("");
						}
						
						XText = canvasView.getCanvas().getAbsoluteLeft();
						YText = canvasView.getCanvas().getAbsoluteTop();
					}
				}));
			}
		});
	}
	
	private void bindOfPencil(){
		
		toolView.getPencilButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.PENCIl;
				removeAllHandle();
				
				paintController.getPencilHandlers().add(canvasView.getCanvas().addMouseDownHandler(new MouseDownHandler() {
					@Override
					public void onMouseDown(MouseDownEvent event) {
						if(currentAcion == action.PENCIl){
							canvasView.setIsTab(true);
							canvasView.setX1(event.getX());
							canvasView.setY1(event.getY());
							canvasView.getContext().moveTo(canvasView.getX1(), canvasView.getY1());
							canvasView.getContext().beginPath();
						}
					}
				}));
				
				paintController.getPencilHandlers().add(canvasView.getCanvas().addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(final MouseMoveEvent event1) {
						if(currentAcion == action.PENCIl && canvasView.getIsTab()){
							canvasView.setX2(event1.getX());
							canvasView.setY2(event1.getY());
							canvasView.getContext().lineTo(canvasView.getX2(), canvasView.getY2());
							canvasView.getContext().stroke();
						}
					}
				}));
				
				paintController.getPencilHandlers().add(canvasView.getCanvas().addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event2) {
						canvasView.setIsTab(false);
						saveAuto();
					}
				}));
			}
		});
	}
	
	private void binOfSave(ClickHandler handler){
		
		toolView.getSaveButton().addClickHandler(handler);
//		toolView.getSaveButton().addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				save();
//			}
//		});
	}


	private void bindOfLoadImage(){
		toolView.getLoadImageButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
//				canvasView.getContext().clearRect(0, 0, canvasView.getCanvas().getCoordinateSpaceWidth(), canvasView.getCanvas().getCoordinateSpaceHeight());
//				loadImage(imageUrl);				
			}
		});
	}
	

	private void bindOfLine(){
		
		toolView.getLineButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.LINE;
				removeAllHandle();
				
				paintController.getLineHandlers().add(canvasView.getCanvas().addMouseDownHandler(new MouseDownHandler() {
					@Override
					public void onMouseDown(MouseDownEvent event) {
						if(currentAcion == action.LINE){
							canvasView.setIsTab(true);
							canvasView.setX1(event.getX());
							canvasView.setY1(event.getY());
						}
					}
				}));
				
				paintController.getLineHandlers().add(canvasView.getCanvas().addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(MouseMoveEvent event1) {
						if(currentAcion == action.LINE && canvasView.getIsTab()){
							canvasView.setX2(event1.getX());
							canvasView.setY2(event1.getY());
							
							canvasView.getContext().clearRect(0, 0,  canvasView.getCanvas().getCoordinateSpaceWidth(), canvasView.getCanvas().getCoordinateSpaceHeight());
							showImg(paintController.getCurrentImage());
							
							canvasView.getContext().beginPath();
							canvasView.getContext().moveTo(canvasView.getX1(), canvasView.getY1());
							canvasView.getContext().lineTo(canvasView.getX2(), canvasView.getY2());
							canvasView.getContext().stroke();
							canvasView.getContext().closePath();
						}
					}
				}));
				
				paintController.getLineHandlers().add(canvasView.getCanvas().addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event2) {
						canvasView.setIsTab(false);
						saveAuto();
					}
				}));	
			}
		});
		
	}
	
	private void bindOfRect(){
		toolView.getRectButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.RECT;
				removeAllHandle();
				
				paintController.getRectHandlers().add(canvasView.getCanvas().addMouseDownHandler(new MouseDownHandler() {
					@Override
					public void onMouseDown(MouseDownEvent event) {
						if(currentAcion == action.RECT){
						canvasView.setIsTab(true);
						canvasView.setX1(event.getX());
						canvasView.setY1(event.getY());
						canvasView.getContext().moveTo(canvasView.getX1(), canvasView.getY1());
						canvasView.getContext().beginPath();
						}
						
					}
				}));
				
				paintController.getRectHandlers().add(canvasView.getCanvas().addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(final MouseMoveEvent event1) {
						if(currentAcion == action.RECT && canvasView.getIsTab()){
							canvasView.setX2(event1.getX());
							canvasView.setY2(event1.getY());
							
							canvasView.getContext().closePath();
							canvasView.getContext().clearRect(0, 0,  canvasView.getCanvas().getCoordinateSpaceWidth(), canvasView.getCanvas().getCoordinateSpaceHeight());
							showImg(paintController.getCurrentImage());
							
							canvasView.getContext().strokeRect(canvasView.getX1(), canvasView.getY1(), canvasView.getX2()-canvasView.getX1(),canvasView.getY2() -canvasView.getY1());
						}
					}
				}));
				
				paintController.getRectHandlers().add(canvasView.getCanvas().addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event2) {
						canvasView.setIsTab(false);
						saveAuto();
					}
				}));				
			}
		});
	}
	
	private void bindOfEclipse(){
		toolView.getRoundButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				currentAcion = action.ECLIPSE;
				removeAllHandle();
				
				paintController.getRoundHandlers().add(canvasView.getCanvas().addMouseDownHandler(new MouseDownHandler() {
					@Override
					public void onMouseDown(MouseDownEvent event) {
						if(currentAcion == action.ECLIPSE){
							canvasView.setIsTab(true);
							canvasView.setX1(event.getX());
							canvasView.setY1(event.getY());
						}
						
					}
				}));
				
				paintController.getRoundHandlers().add(canvasView.getCanvas().addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(final MouseMoveEvent event1) {
						if(currentAcion == action.ECLIPSE && canvasView.getIsTab()){
							canvasView.setX2(event1.getX());
							canvasView.setY2(event1.getY());
							
							canvasView.getContext().clearRect(0, 0,  canvasView.getCanvas().getCoordinateSpaceWidth(), canvasView.getCanvas().getCoordinateSpaceHeight());
							showImg(paintController.getCurrentImage());
							canvasView.getContext().beginPath();
							
							canvasView.getContext().moveTo(canvasView.getX1(), canvasView.getY1());
							
							if(canvasView.getX2()-canvasView.getX1()>0){
								canvasView.getContext().arc(canvasView.getX1(), canvasView.getY1(), (canvasView.getX2()-canvasView.getX1())*Math.PI/2, 0, Math.PI*2, false); 
								canvasView.getContext().stroke();
							}else{
								//TODO
							}
							canvasView.getContext().closePath();
							

						}
					}
				}));
				
				paintController.getRoundHandlers().add(canvasView.getCanvas().addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event2) {
						canvasView.setIsTab(false);
						saveAuto();
					}
				}));
								
			}
		});
	}
	
	private void bindOfNext(){
		
		toolView.getNextButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(paintController.getIndexCurrentImage() < paintController.getImagesSave().size()){
					paintController.setCurrentImage(paintController.getImagesSave().get(paintController.getIndexCurrentImage()));
					showImg(paintController.getCurrentImage());
					paintController.setIndexCurrentImage(paintController.getIndexCurrentImage()+1);
			  }
			}
		});

		
	}
	
	private void bindOfBack(){
		toolView.getBackButon().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(paintController.getIndexCurrentImage() >= 1){
					paintController.setIndexCurrentImage(paintController.getIndexCurrentImage()-1);
					paintController.setCurrentImage(paintController.getImagesSave().get(paintController.getIndexCurrentImage()));
				}else{
					if(paintController.getImageRoot()!=null)
					paintController.setCurrentImage(paintController.getImageRoot());
				}
				
				showImg(paintController.getCurrentImage());

			}
		});

	}
	
	private void removeAllHandle(){
		markCurrentTool();
		paintController.removeAllHandles();
	}
	
	private void markCurrentTool(){
		if(currentAcion == action.TEXT){
			DOM.setStyleAttribute(toolView.getTextButton().getElement(), "backgroundColor","gray");
		}else{
			DOM.setStyleAttribute(toolView.getTextButton().getElement(), "backgroundColor","");
		}
		
		if(currentAcion == action.ARROW){
			DOM.setStyleAttribute(toolView.getArrowButton().getElement(), "backgroundColor","gray");
		}else{
			DOM.setStyleAttribute(toolView.getArrowButton().getElement(), "backgroundColor","");
		}
		
		if(currentAcion == action.ECLIPSE){
			DOM.setStyleAttribute(toolView.getRoundButton().getElement(), "backgroundColor","gray");
		}else{
			DOM.setStyleAttribute(toolView.getRoundButton().getElement(), "backgroundColor","");
		}
		
		if(currentAcion == action.LINE){
			DOM.setStyleAttribute(toolView.getLineButton().getElement(), "backgroundColor","gray");
		}else{
			DOM.setStyleAttribute(toolView.getLineButton().getElement(), "backgroundColor","");
		}
		
		if(currentAcion == action.PENCIl){
			DOM.setStyleAttribute(toolView.getPencilButton().getElement(), "backgroundColor","gray");
		}else{
			DOM.setStyleAttribute(toolView.getPencilButton().getElement(), "backgroundColor","");
		}
		
		if(currentAcion == action.RECT){
			DOM.setStyleAttribute(toolView.getRectButton().getElement(), "backgroundColor","gray");
		}else{
			DOM.setStyleAttribute(toolView.getRectButton().getElement(), "backgroundColor","");
		}
		
	}

	private void saveAuto(){
		String imageData = canvasView.getCanvas().toDataUrl("image/png");
		Image image = new Image(imageData);
		paintController.getImagesSave().add(image);
		paintController.setCurrentImage(image);
		paintController.setIndexCurrentImage(paintController.getImagesSave().size());
//		imageTargetContainer.clear();
//		imageTargetContainer.add(paintController.getCurrentImage());
		
	}
	
	public String save(){
		String imageData = canvasView.getCanvas().toDataUrl("image/png");
		Image image = new Image(imageData);
		paintController.getImagesSave().clear();
		paintController.setImageRoot(image);
		
		canvasView.getImageSave().clear();
		canvasView.getImageSave().add(image);
		
		paintController.setIndexCurrentImage(1);
		return imageData;
	}
	
	public void loadImage(){
		try {
			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, URLServerDefault+"?url="+imageUrl);
			requestBuilder.sendRequest("", new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if(response.getText().isEmpty() || response.getText().length()<50){
						showImg(null);
						return;
					}
					String imageData = response.getText();
					loadImage(imageData);
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("request fail");
				}
			});
		} catch (Exception e) {
			Window.alert("Loaded image fail, please try again");
		}
	}
	
	public void loadImage(final String dataUri){
		final FitImage image = new FitImage(dataUri);
		this.add(image);
		image.setMaxSize(440,300);
//		adjustImageSize(image,canvasView.getOffsetWidth(), canvasView.getOffsetHeight() );
		image.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				PaintView.this.remove(image);
				int w =  image.getOffsetWidth();
				int h = image.getOffsetHeight();
				double ratio = (double) (w) / h;
				System.out.println("w: " +w + ", h: " + h + ", r: " +ratio + ", real width: " +canvasView.getOffsetWidth());
				image.setUrl(dataUri);
//				image.setPixelSize(canvasView.getOffsetWidth(), (int) (canvasView.getOffsetWidth()*ratio));
				paintController.setImageRoot(image);
				paintController.setCurrentImage(image);
				showImg(paintController.getCurrentImage());
			}
		});
	}
	
	public void adjustImageSize(Image image, int widthPx, int heightPx) {
		if (image.getWidth() > 0) {
			double ratio = (double) (widthPx - 10) / image.getWidth();
			if (ratio * image.getHeight() > (heightPx - 10)) {
				ratio = (double) (heightPx - 10) / image.getHeight();
			}
			image.setPixelSize(
					(int) Math.floor(image.getWidth() * ratio),
					(int) Math.floor(image.getHeight() * ratio));
		}
	}
}
