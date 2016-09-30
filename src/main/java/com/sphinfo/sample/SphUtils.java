package com.sphinfo.sample;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SphUtils {

	/**
	 * 
	* <pre>
	* 1. 메소드명 : makeHtml
	* 2. 작성일 : 2016. 9. 25. 오후 11:44:08
	* 3. 작성자 : Administrator
	* 4. 설명 : cpature html을 생성한다
	* </pre>
	* @param body
	* @param htmlFileNm
	* @throws Exception
	 */
	public static void makeHtml(String body, String htmlFileNm) throws Exception{
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
	
	public static void makePdfHtml(String path, String catureImage, String pdfSize, String pdfOrientation) throws Exception{
		FileWriter fw = null;
		try{
			StringBuffer html = new StringBuffer();
			html.append("<html>\n");
			html.append("<head><meta charset=\"EUC-KR\"/></head>\n");
			html.append("<body>\n");
			html.append("<img src='").append(catureImage).append("' ");
			String pageSize = getSize(pdfSize) ;
			if(pdfOrientation.equals("Portrait")){
				html.append("height='");
			}else {
				html.append("width='");
			}
			html.append(pageSize).append("' />");
			// html.append("<div style='width:100%;height:100%;background-color:#FEF112;'/>\n");
			html.append("\n</body>\n");
			html.append("</html>");
			
			fw = new FileWriter(path);
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
	public static  void runCapture(String width,String height,String htmlFileNm,String imageFileNm) throws Exception{
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
	
	public static  void runPdf(String pageSize, String orientation, String htmlFileNm,String imageFileNm) throws Exception{
		Runtime run = Runtime.getRuntime();
		Process p = null;
		BufferedReader br = null;
		try{
			String pPageSize = "--page-size " + pageSize;
			String pOrientation = "--orientation " + orientation;
			String cmdarray = "wkhtmltopdf" + " " + pPageSize + " " + pOrientation
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
	public static String getSize(String pageSize){
		String size ="1024";
		if(pageSize.equals("A4")){
			size = "1024";
		}else {
			size = "2048";
		}
		return size;
		
	}
}
