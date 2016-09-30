#Extent JSR

============================
##capture/print  추가 
 1. http://wkhtmltopdf.org/downloads.html 이미지 캡쳐 설치
 2. 레이어: useCanvas= false
 3. com.sph.Capture에서 html 생성 후 wkhtmltoimage을 사용하여 html을 이미지로 변경 
 4. Demo : http://61.32.6.18:18080/TestSuperMap/example.html 
## client 호출
### CAPTURE : 레이어를 canvas 사용시
 1. html2canvas.js 사용
 ```javascript
var host = "http://127.0.0.1:8090/iserver"; // 이미지를 다운 받을 iserver url
var size = map.getCurrentSize();
var mapViewPort = $("#map div:first-child");
var mapElem = mapViewPort.children()[0]; // the id of your map div here
html2canvas(mapElem, {
    useCORS: true,
    onrendered: function(canvas) {
    mapImg = canvas.toDataURL('image/png');
        var jsonCanvasParameters = SuperMap.Util.toJSON({
            	"width" :size.w,
         				"height" :size.h,
					        "html" :"<img src='"+mapImg+"'/>"
	    	  });
        var capturUrl = host+"/services/spatialanalyst-sample/restjsr/"+type+".jsonp";
        var restService = new SuperMap.ServiceBase(capturUrl+"?returnContent=true");
        restService.isInTheSameDomain = false;
        var option = {
         	method : "POST",
		        scope:this,
		        data :jsonParameters,
		        success :function(json){
             var result = SuperMap.Util.transformResult(json);
             var link = document.createElement('a');
             link.download = 'Download.png';
	 				       link.target= "_blank";
             document.body.appendChild(link);
             link.click();
             link.remove();
          }
       }
       restService.request(option);
    }
});
