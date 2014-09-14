package ch.squix.extraleague.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

/**
 * Creates thumbnails by calling another service
 * 
 * @author deichhor
 * 
 */
public class PlayerImageServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(PlayerImageServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String urlString = req.getParameter("url");
		
		resp.setContentType("image/png");
		resp.setHeader("Cache-Control", "public, max-age=3600");
		BufferedInputStream bis = null;
		ByteArrayOutputStream bos = null;
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			bis = new BufferedInputStream(connection.getInputStream());
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[0xFFFF];
			for (int len; (len = bis.read(buffer)) != -1;) {
				bos.write(buffer, 0, len);
			}
			byte[] picture = bos.toByteArray();
			// only set the cache header after successfull header reading

			ImagesService imagesService = ImagesServiceFactory.getImagesService();

			Image oldImage = ImagesServiceFactory.makeImage(picture);
			Transform resize = ImagesServiceFactory.makeResize(48, 58, 0.5, 0);
			Image newImage = imagesService.applyTransform(resize, oldImage, OutputEncoding.PNG);
			picture = newImage.getImageData();

			resp.getOutputStream().write(picture);
		} catch (Exception e) {
			System.err.println(e);
			resp.setHeader("Cache-Control", "no-cache,must-revalidate");
			resp.sendRedirect(resp.encodeRedirectURL("/images/person2.png"));
		}

	}

}
