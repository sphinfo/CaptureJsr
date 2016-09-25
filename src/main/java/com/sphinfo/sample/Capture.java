package com.sphinfo.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.representation.Variant;

import com.supermap.services.rest.HttpException;
import com.supermap.services.rest.PostResult;
import com.supermap.services.rest.PreInputValidater;
import com.supermap.services.rest.decoders.Decoder;
import com.supermap.services.rest.resources.AlgorithmResultSetResource;
import com.supermap.services.rest.resources.SimpleAlgorithmResultResourceBase;
import com.supermap.services.rest.util.MappingUtil;

public class Capture extends AlgorithmResultSetResource {
	private String mapName;
	private MappingUtil mappingUtil;
	private static Logger log = Logger.getLogger("Capture");

	public Capture(Context context, Request request, Response response) {
		super(context, request, response);
		response.getAttributes().put("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.getAttributes().put("Access-Control-Max-Age", "3600");
		response.getAttributes().put("Access-Control-Allow-Headers",
				"x-requested-with");
		response.getAttributes().put("Access-Control-Allow-Origin", "*");

		mappingUtil = new MappingUtil(this);
		this.mapName = this.mappingUtil.getMapName(this.getRequest());
		this.mapName = this.mapName.trim();
		// List<String> supportList = getSupportedOperations();
		// supportList.add("POST");
		// for (int i = 0; i < supportList.size(); i++) {
		// log.info("Support : " + supportList.get(i));
		// }
		// setSupportedOperations(supportList);
		// addSupportedOperations(supportList);
		String method = getMethod().getName();
		log.info("Method : " + method);
		if (allowPost()) {
			log.info("Use Method Post!");
		}
	}

	@Override
	protected boolean doEnsureInputSafe() {
		try {
			log.info("doEnsureInputSafe");
			new PreInputValidater().check(createArithParamClassMappings(),
					getURLParameter());
		} catch (PreInputValidater.PreInputValidateFailedException localPreInputValidateFailedException) {
			return false;
		}
		return true;
	}

	// 자원이 존재하는지 여부를 확인합니다
	public boolean isResourceExist() {
		log.info("MapName : " + this.mapName);
		boolean flag = false;
		flag = this.mappingUtil.isMapExist(this.mapName);
		return flag;
	}

	@Override
	protected String getArithName() {
		// TODO Auto-generated method stub
		return "capture";
	}

	@Override
	public Object runArithmetic(Map arg0) {
		// TODO Auto-generated method stub
		log.info("runArithmetic");
		CaptureResult result = new CaptureResult();
		result.code=200;
		
		return result;
	}

	@Override
	public Object getRequestEntityObject() {
		Object localObject = null;
		String reqText=null;
		Variant localVariant = getRequestEntityVariant();
		Decoder localDecoder = getAdaptedDecoder(localVariant.getMediaType());
		if (localDecoder == null) {
			HttpException localHttpException = new HttpException("Media Type not Supported");

			localHttpException
					.setErrorStatus(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
			throw localHttpException;
		}
		try {
			reqText = getRequest().getEntityAsText();
		} catch (Exception localException) {
			throw new HttpException(Status.CLIENT_ERROR_BAD_REQUEST,
					localException.getMessage(), localException);
		}
		if ((reqText == null) || (reqText.equals(""))) {
			return localObject;
		}
		try{
//			Map<String, Object> attrMap =  getRequest().getAttributes();
//			reqText = "{\""+reqText+"\"}";
//			reqText = reqText.replaceAll("=", "\":\"").replaceAll("&", "\",\"");
//			log.info("Param::>"+reqText);

//			String[] paramList = reqText.split("&");
//			Map<String, Object> map = new HashMap<String, Object>();
//			for(int i = 0; i<paramList.length;i++){
//				String[] paramstr = paramList[i].split("=");
//				if(paramstr.length==1){
//					map.put(paramstr[0], "");
//				} else if(paramstr.length==2){
//					map.put(paramstr[0], paramstr[1]);
//					log.info("Key : "+ paramstr[0] + ", value : "+paramstr[1]);
//				}
//			}
			//localObject = attrMap;
			localObject = localDecoder.toMap(reqText, createArithParamClassMappings());
			log.info(localObject.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return localObject;
	}


	// 구조적 파라미터 의 타입을 제공한다.。
	@Override
	public java.util.Map<String, Class> createArithParamClassMappings() {
		log.info("createArithParamClassMappings Start");
		log.info(getRequest().getEntityAsText());
		java.util.Map<String, Class> paramClassMapping = new HashMap<String, Class>();
		try{
			paramClassMapping.put("width", String.class);
			paramClassMapping.put("height", String.class);
			paramClassMapping.put("html", String.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		log.info("createArithParamClassMappings END");
		return paramClassMapping;
	}

	//
	protected void checkUrlParamValid(java.util.Map arg0) {
		// TODO Auto-generated method stub
		log.info("checkUrlParamValid");
	}

}
