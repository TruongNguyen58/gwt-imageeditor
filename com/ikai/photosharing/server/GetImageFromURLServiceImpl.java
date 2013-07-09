package com.ikai.photosharing.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.Base64Utils;

@SuppressWarnings("serial")
public class GetImageFromURLServiceImpl extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url = req.getParameter("url");
		String dataImageBase64 = toDataImage(url);
		System.out.println("server : "+ dataImageBase64);
		resp.getOutputStream().print(dataImageBase64);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
	

	public String toDataImage(String url) {
		String s = "";
		try {
			 byte[] bais = getByteFromUrl(url);
//			 System.out.println("bytes : "+bais.toString() + "/"+ bais.length);
//			 Base64Utils base64Utils = new Base64Utils();
			 s = Base64Utils.toBase64(bais);
//			 System.out.println("base64 : " + s);
		} catch (Exception e) {
		}
		s = s.replace("_", "/");
		s = s.replace("$", "+");
		return "data:image/jpeg;base64,"+s;
	}
	
	private byte[] getByteFromUrl(String urlStr)  {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		 byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
		try {
			URL url = new URL(urlStr);
			InputStream is = null;
			try {
			  is = url.openStream ();
			  int n;
			  while ( (n = is.read(byteChunk)) > 0 ) {
			    bais.write(byteChunk, 0, n);
			  }
			}
			catch (IOException e) {
			  System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
			  e.printStackTrace ();
			  // Perform any other exception handling that's appropriate.
			}
			finally {
			  if (is != null) { is.close(); }
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bais.toByteArray();
	}

}
