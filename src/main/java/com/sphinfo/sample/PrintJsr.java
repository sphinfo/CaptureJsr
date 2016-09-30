package com.sphinfo.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.Path;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.concurrent.atomicreference.client.GetRequest;
import com.supermap.services.components.commontypes.MapImage;
import com.supermap.services.rest.PreInputValidater;
import com.supermap.services.rest.resources.JaxAlgorithResultSetResource;
import com.supermap.services.rest.resources.SimpleAlgorithmResultResourceBase;
import com.supermap.services.rest.util.MappingUtil;

@Path("/print")
public class PrintJsr extends JaxAlgorithResultSetResource<JSONObject> {
	private static Logger log = Logger.getLogger("PrintJsr");

	private static String outputPath = "D:/supermap/supermap_iserver_8.0.0a_win64_zip_eng/webapps/iserver/output/resources/";
	@Override
	protected String getAlgorithmName() {
		// TODO Auto-generated method stub
		return "print";
	}
	
	// 실행 결과
	@Override
	protected Object runArithmetic(JSONObject json) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		Date date = new Date();
		
		String htmlNm = String.valueOf(date.getTime());
		String defaultPath = "";
		
		String htmlFileNm = "";
		String htmlCaptureFileNm = "";
		
		String imageFileNm ="";
		String imageCaptureNm="";
		
		String resultCode = "500";
		
		try {
			File defalutPath = new File("./..");
			String outputPath  = defalutPath.getCanonicalPath()+"/webapps/iserver/output/resources/";
			defaultPath = outputPath+getAlgorithmName()+"/";
			
			htmlFileNm = defaultPath+ htmlNm + ".html";
			imageFileNm = defaultPath + htmlNm + ".pdf";
			
			htmlCaptureFileNm = outputPath+ htmlNm + ".html";
			imageCaptureNm = outputPath + htmlNm + ".png";
			
			File printPath = new File(defaultPath);
			
			if(!printPath.exists()){
				printPath.mkdirs();
			}


			String body = json.getString("html") == null ? "" : json.getString("html");
			String width = json.getString("width") == null ? "" : json.getString("width");
			String height = json.getString("height") == null ? "" : json.getString("height");
			String pageSize = json.getString("pageSize") == null ? "" : json.getString("pageSize");
			String orientation = json.getString("orientation") == null ? "" : json.getString("orientation");
			
			SphUtils.makeHtml(body, htmlCaptureFileNm);
			SphUtils.runCapture(width, height, htmlCaptureFileNm, imageCaptureNm);
			
			SphUtils.makePdfHtml(htmlFileNm,imageCaptureNm, pageSize, orientation);
			SphUtils.runPdf(pageSize, orientation, htmlFileNm, imageFileNm);
			
			resultCode = "200";
			result.put("code",resultCode);
			result.put("path","output/resources/"+getAlgorithmName()+"/"+htmlNm+".pdf");
			Iterator<String> iterator =getCustomVariables().keySet().iterator();
			while(iterator.hasNext()){
				String key  = iterator.next();
				log.info("GCV ==> "+ getCustomVariables().get(key));
			}
			return result.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			resultCode = "500";
			result.put("code",resultCode);
			return result.toString();
		} finally {
//			File htmlFile = new File(htmlFileNm);
//			if(htmlFile.exists()){
//				htmlFile.delete();
//			}
		}
	}

}
