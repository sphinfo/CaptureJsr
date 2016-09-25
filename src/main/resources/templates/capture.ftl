<#include "head.ftl">
<h1>DataEdit </h1>
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
		<#assign html><h1>test</h1></#assign>
		<tr>
			<td>Width </td>
			<td>
				<input type="text" name="width" value="1024"/>
			</td>
		</tr>
		<tr>
			<td>Height </td>
			<td>
				<input type="text" name="height" value="600"/>
			</td>
		</tr>
		<tr>
			<td>Html</td>
			<td>
				<textarea type="text" style="width:255px;height:50px" name="html" >${html}</textarea>
			</td>
		</tr>
		
        <tr>
        	<td></td>
        	<td>
        		<input type="submit" value="Edit Data"/>
        	</td>
        </tr>
	</table>
</form>
</p>
<br>
<br>
<hr>
<#include "right.ftl">

