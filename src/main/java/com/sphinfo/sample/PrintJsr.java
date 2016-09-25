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
		return "capture";
	}
	
	// 실행 결과
	@Override
	protected Object runArithmetic(JSONObject json) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		Date date = new Date();
		
		String htmlNm = String.valueOf(date.getTime());
		String defaultPath = outputPath+getAlgorithmName()+"/";
		String htmlFileNm = defaultPath+ htmlNm + ".html";
		String imageFileNm = defaultPath + htmlNm + ".png";
		String resultCode = "500";
		String imageUrl = "";
		try {
			File caturePath = new File(defaultPath);
			File testPath = new File("../../");
			log.info(testPath.toPath().toString());
			if(!caturePath.exists()){
				caturePath.mkdirs();
			}
			//log.info(json.getString("html"));

			String body = json.getString("html") == null ? "" : json.getString("html");
			String width = json.getString("width") == null ? "" : json.getString("width");
			String height = json.getString("height") == null ? "" : json.getString("height");
			
			makeHtml(body, htmlFileNm);
			runCapture(width, height, htmlFileNm, imageFileNm);
			
			resultCode = "200";
			result.put("code",resultCode);
			result.put("path",getAlgorithmName()+"/"+htmlNm+".png");
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

	private void makeHtml(String body, String htmlFileNm) throws Exception{
		FileWriter fw = null;
		try{
			body = body.replaceAll("%26amp;", "&");
			StringBuffer html = new StringBuffer();
			html.append("<html>\n");
			html.append("<head><meta charset=\"EUC-KR\"/></head>\n");
			html.append("<body>\n");
			html.append(body);
			// html.append("<div style='width:100%;height:100%;background-color:#FEF112;'/>\n");
			html.append("\n</body>\n");
			html.append("</html>");
			
			fw = new FileWriter(htmlFileNm);
			fw.write(html.toString());
			fw.flush();
			fw.close();
		}finally{
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void runCapture(String width,String height,String htmlFileNm,String imageFileNm) throws Exception{
		Runtime run = Runtime.getRuntime();
		Process p = null;
		BufferedReader br = null;
		try{
			String pWidth = "--width " + width;
			String pHeight = "--height " + height;
			String cmdarray = "wkhtmltoimage" + " " + pWidth + " " + pHeight
					+ " " + htmlFileNm + " " + imageFileNm;
			System.out.println(cmdarray);
			p = run.exec(cmdarray);

			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			// 프로세스의 수행이 끝날때까지 대기
			p.waitFor();
			br.close();
			p.destroy();
		}  finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (p != null) {
				p.destroy();
			}
		}
	}

}
