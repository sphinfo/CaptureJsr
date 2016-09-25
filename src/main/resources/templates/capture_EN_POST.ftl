<#include "head.ftl">
<h1>rectangleArea 资源</h1>
<br>
<b>资源描述:</b> 面积量算结果资源，用于描述一个面积量算的结果。
<br>
<#if resource.content.code!= -1>
 <p>
	<table width="150px" id="userTable">
		<tr valign="left">
			<td style="width:50px"> <h5>area:</h5></td> <td style="width:50px"> ${resource.content.code}</td>
		</tr>
		<tr valign="left">
			<td style="width:50px"> <h5 >unit:</h5></td> <td style="width:50px">${resource.content.msg}</td>
		</tr>
	</table>
 </p>
<#else>
 <br><br>
ORCLE에 공간데이터 및 속성데이터 등록 입니다.
</#if>
<p>
<form method="post" >
	<table style="border:1px solid #000000;width:800;" >
		<#assign geometry>{"center":{"y":148916.22066066338,"x":215730.8990956851},"id":0,"style":null,"prjCoordSys":null,"parts":[5],"points":[{"y":18.376877927612455,"x":63.400605838119425},{"y":18.376877927612455,"x":431398.3975855321},{"y":297814.06444339914,"x":431398.3975855321},{"y":297814.06444339914,"x":63.400605838119425},{"y":18.376877927612455,"x":63.400605838119425}],"type":"REGION"}</#assign>
		<tr>
			<td>Geometry</td>
			<td>
				<textarea type="text" style="width:255px;height:50px" name="geometry" >${geometry}</textarea>
			</td>
		</tr>
		<tr>
			<td>Query Type</td>
			<td>
				 <select name="unit">
						<option value="ADDNEW" selected="selected">ADDNEW</option>
						<option value="MODIFY" selected="selected">MODIFY</option>
						<option value="DELETE" selected="selected">DELETE</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Dataset Name</td>
			<td>
				<input type="text" value="TestPolygon"/>
			</td>
		</tr>
		<tr>
			<td>Where</td>
			<td>
				<input type="text" name="attributeFilter" value="1=1"/>
			</td>
		</tr>
		<tr>
			<td>Dataset Name</td>
			<td>
				<input type="text" name="dataset" value="TestPolygon"/>
			</td>
		</tr>
		<tr>
			<#assign values>{"TITLE":"TEST"}</#assign>
			<td>Geometry</td>
			<td>
				<textarea type="text" style="width:255px;height:50px" name="values" >${values}</textarea>
			</td>
		</tr>
        <tr>
        	<td></td>
        	<td>
        		<input type="submit" value="rect2DareaMeasure"/>
        	</td>
        s</tr>
	</table>
</form>
</p>
<br>
<br>
<hr>
<#include "tail.ftl">

