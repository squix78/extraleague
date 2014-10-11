package ch.squix.extraleague.rest.blobs;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;



public class BlobUrlResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(BlobUrlResource.class.getName());
	
	@Get(value = "json")
	public BlobUrlDto execute() throws UnsupportedEncodingException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String url = blobstoreService.createUploadUrl("/upload");
		// for test env
		//url = url.replaceAll(":8080", ":35729");
		BlobUrlDto dto = new BlobUrlDto();
		dto.setUrl(url);
		return dto;
	}
	




}
