package com.ikai.photosharing.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.ikai.photosharing.client.widgets.*;

public class Main implements EntryPoint {

	 PaintView paintView ;
	 DialogBox dialogBox = new DialogBox();

	@Override
	public void onModuleLoad() {
//		ClickHandler clickHandler = new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.alert("save");
//			}
//		};
//		
//	    paintView = new PaintView(500, 500,clickHandler);
//	    dialogBox.add(paintView);
//	    Button button = new Button("Image editting");
//	    button.addClickHandler(new ClickHandler() {
//			
//			@Override
//			public void onClick(ClickEvent event) {
//				paintView.loadUrl("https://si0.twimg.com/profile_images/3096110144/d097a719dba080cc99ca9dbfba85dfa4.jpeg");
//				dialogBox.show();
//			}
//		});
//	    
//	    RootPanel.get().add(button);
		
	}

}
