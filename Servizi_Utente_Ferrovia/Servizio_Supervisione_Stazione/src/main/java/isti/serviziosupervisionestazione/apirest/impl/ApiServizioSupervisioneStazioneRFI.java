package isti.serviziosupervisionestazione.apirest.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.utils.Pair;


//@Consumes(MediaType.APPLICATION_XML)
//@Produces(MediaType.APPLICATION_JSON)
@Path("/rfi")
public class ApiServizioSupervisioneStazioneRFI {

	// @Inject
	// TokenPersistence em;

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ApiServizioSupervisioneStazioneRFI.class);

	private static Map<String, BigDecimal> map = new HashMap<>();

	private static Map<String, Pair<Integer, Date>> timediff = new HashMap<>();

	private static Map<String, Integer> ricevuti = new HashMap<>();

	@Path("/test")
	@GET
	public String test() {

		map = new HashMap<>();
		ricevuti = new HashMap<>();
		timediff = new HashMap<>();
		log.info("Test OK\n\r");
		return "<html><body>OK</body></html>";
	}

	@Path("/FrontEnd/{key:.*}")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String viaggiatreno(@PathParam("key") String key, 
			@QueryParam("PlaceId") String PlaceId, 
			@QueryParam("TrainNumber") String TrainNumber, 
			@QueryParam("TrainId") String TrainId, 
			@QueryParam("Time") String Time ,
			@QueryParam("Limit") String Limit,
			@Context HttpServletRequest request/*, @Context HttpServletResponse response*/) {
		String path = "https://stazionevirtuale.rfi.it/IECSV/IeC/SV/FrontEnd/";
		if(PlaceId!=null)
			key+="?PlaceId="+PlaceId;
		if(Time!=null)
			key+="?Time="+Time;
		if(Limit!=null)
			key+="?Limit="+Limit;
		if(TrainNumber!=null)
			key+="?TrainNumber="+TrainNumber;
		if(TrainId!=null)
			key+="?TrainId="+TrainId;
		String url  = path + key;
//Content-Type: application/xml;
		String contenttype = request.getContentType();
		log.info(url+"\n\r");
		try {

			Client client = ClientBuilder.newClient();
			if(contenttype==null)
				contenttype = "application/xml";
			Response response = client.target(url).request().header("Content-Type", contenttype).get();
			String responseAsString = response.readEntity(String.class);

			return responseAsString;
			/*response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", url);
			response.sendRedirect(url);*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e);
		}
		String err =  "<html><body>ERROR</body></html>";
		return err;
		
	}



}
