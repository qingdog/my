<xform:text property="fdCode" showStatus="readOnly" style="width:95%;color: inherit" />



<style>
#tid tr td{
	border: 1px solid #e0e0e0;
	padding: 8px;
}
#tid tr:not(:nth-of-type(1)){
	background-color: #ffffff;
}
</style>
<script>
	function callback(jsonArr) {
		var form = document.getElementsByName("kmReviewMainForm")[0];
		form.action = "${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do";
		form.target = "";
		
		var jsonStr = JSON.stringify(jsonArr);
		var fd_39e4944432fe88 = $('input[name="extendDataFormInfo.value\\(fd_39e4944432fe88\\)"]');
		fd_39e4944432fe88.val(jsonStr);
		if (fd_39e4944432fe88.val() == "")
			return;
		
		var tableobj = document.getElementById("tid");
		$('#tid tr').remove();
		var jsonObject = $.parseJSON(fd_39e4944432fe88.val());
		for (var i = 0; i < jsonObject.length; i++) {
			var trobj = document.createElement("tr");
			for ( var key in jsonObject[i]) {
				var tdobj = document.createElement("td");
				tdobj.innerHTML = jsonObject[i][key];
				trobj.appendChild(tdobj);
			}
			tableobj.appendChild(trobj);
		}
	}
</script>

		<td width="35%">
                                        <%-- 资产类别--%>
                                        <div id="_xform_fdBaseTypeId" _xform_type="dialog">
                                            <xform:dialog propertyId="fdBaseTypeId" propertyName="fdBaseTypeName" showStatus="edit" style="width:95%;" htmlElementProperties='onchange="modifyFdAddMaxmonth(this)"' useNewStyle="true" required="true" subject="${lfn:message('yjy-zc:yjyZcIn.fdBaseType')}">
                                                Dialog_Tree(false, 'fdBaseTypeId', 'fdBaseTypeName', ',', 'yjyZcBaseTypeService&parentId=!{value}&fdId=${yjyZcInForm.fdBaseTypeId}&multiStage=true', '${lfn:message('yjy-zc:treeModel.alert.templateAlert')}', null, null, null, null, false, null);
                                            </xform:dialog>
                                            <script>
                                            $(document).ready(function(){
	                                            var id = document.getElementsByName("fdBaseTypeId")[0].value;
	                                            var name = document.getElementsByName("fdBaseTypeName")[0].value;
	                                            initNewDialog("fdBaseTypeId","fdBaseTypeName",";","yjyZcBaseTypeService&newSearch=true",false,id,name,afterSelectData);
	                                            
	                                            var gbId = document.getElementsByName("fdGbId")[0].value;
	                                            var gbName = document.getElementsByName("fdGbName")[0].value;
	                                            initNewDialog("fdGbId","fdGbName",";","yjyZcBaseGbService&newSearch=true",false,gbId,gbName,afterSelectData2);
	                                        });
	                                        function afterSelectData(rtnData){
	                                        	if(rtnData.length){
	                                        		if(rtnData.length == 2){
	                                        			var fdBaseTypeId = rtnData[0];
	                                        			if(fdBaseTypeId != ''){
	                                        				modifyFdAddMaxmonth($("input[name='fdBaseTypeName']")[0]);
	                                        			}
	                                        		} 
	                                        	}
	                                        }
	                                        function afterSelectData2(rtnData){
	                                        	if(rtnData.length){
	                                        		if(rtnData.length == 2){
	                                        			var fdGbId = rtnData[0];
	                                        			if(fdGbId != ''){
	                                        				modifyFdGbCode(fdGbId);
	                                        			}
	                                        		} 
	                                        	}
	                                        }
                                        	</script>
                                        </div>
                                    </td>



<ui:button text="${lfn:message('szc-hr:szcHrTalentInduct')}" order="3" onclick="Com_OpenWindow(
	'${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId=${fdInductId }&fdModelId=${param.fdId }', '_self');" />

<div  class="lui_cclh_title_handle_button" onclick="Com_OpenWindow('kmAgreementCard.do?method=isPurchConfirm&fdId=${kmAgreementLedgerForm.fdId}', '_self');">
						</div>
<script>
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
    var option = window.listOption;
    window.addDoc = function() {
        //Com_OpenWindow(option.contextPath + option.basePath + "?method=add");
        //Com_OpenWindow("${LUI_ContextPath}/szc/hr/szc_hr_view/szcHrView.do?method=add&fdModleId=${param.fdId}", '_self');
   		var url= "${LUI_ContextPath}/szc/hr/szc_hr_view/szcHrView.do?method=add&fdModelId=${param.fdId}";
   		dialog.iframe(url, '${ lfn:message("szc-hr:szcHrTalentImportView") }', function(ids) {
   			if(ids) {
   			} else if(ids == "") {
				return false;
   			}
   		}, {width:1000, height:600});
    }
})

<form name="exportData" action="" method="post">
    <input type="hidden" name="List_Selected" value="">
</form>
<script>
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
	window.exportExcel=function(){
        var values = [];
        $("input[name='List_Selected']:checked").each(function(i, element){
            values.push($(this).val());
        });
        if(values.length==0){
            dialog.alert(option.lang.noSelect);
            return;
        }/* else if(values.length>300){
            return;
        } */
        document.exportData.List_Selected.value = values;
        $('form[name="exportData"]').attr('action', "${LUI_ContextPath}/gas/overtime/gas_overtime_info/gasOvertimeInfo.do?method=exportExcel");
        $("form[name='exportData']").submit();
	};
})
</script>

<div id="_xform_fdGenProId" _xform_type="dialog">
 <xform:dialog propertyId="fdGenProId" propertyName="fdGenProName" showStatus="edit" style="width:95%;">
  dialogSelect(false,'sgc_topic_rm_member_selectb','fdGenProId','fdGenProName');
 </xform:dialog>
</div>
var formOption = {
    formName: 'sgcTopicRmMemberForm',
    modelName: 'com.landray.kmss.sgc.topic.model.SgcTopicRmMember',
    templateName: '',
    subjectField: 'fdName',
    mode: ''

    ,
    dialogs: {
        sgc_topic_initiate_selectInitate: {
            modelName: 'com.landray.kmss.sgc.topic.model.SgcTopicInitiate',
            sourceUrl: '/sgc/topic/sgc_topic_initiate/sgcTopicInitiateData.do?method=selectInitate'
        },
        sgc_topic_rm_member_selectb: {
            modelName: 'com.landray.kmss.sgc.topic.model.SgcTopicRmBMem',
            sourceUrl: '/sgc/topic/sgc_topic_rm_member/sgcTopicRmMember.do?method=select&fdDeptType=b'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {},
    detailNotNullProp: {}
};
<entry key="select" value="/sgc/topic/sgc_topic_rm_member/sgcTopicRmMember_select.jsp"/>

<c:if test="${param.method=='add'}">
<script type="text/javascript">
window.onload = function(){
  $form("docSubject").val("默认标题");
  $form("docSubject").editLevel(0);
}
</script>
</c:if>
<c:if test="${kmCarmngMaintenanceInfoForm.method_GET=='edit'}">
 <input type=button value="<bean:message key="button.update"/>"
 onclick="Com_Submit(document.kmCarmngMaintenanceInfoForm, 'update');">
</c:if>
<xform:editShow>
</xform:editShow>

Com_Parameter.event["submit"].push(function(){
 //操作类型为通过类型 ，才写回编号 
 if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){

//检测内容，若条件完好，则return true。不能提交return false;
}
})

window.dialogSimpleCategory=function(modelName,idField,nameField,mulSelect,callback){
	dialog.simpleCategory(modelName,idField,nameField,mulSelect,callback,null,null,null);
};

//seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/dialog_common'], function($, dialog, topic, dialogCommon) {
seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
})

<c:if test="${bcpPurchMainForm.docStatus=='30'}">
<%--传阅机制(传阅记录)--%>
<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
	<c:param name="formName" value="bcpPurchMainForm" />
	<c:param name="order" value="${bcpPurchMainForm.docStatus=='30'||bcpPurchMainForm.docStatus=='00'||bcpPurchMainForm.docStatus=='31' ? 25 : 5}" />
</c:import>
</c:if>

var len = $("#TABLE_DocList_fdEqList_Form tr[class='docListTr']").length;


<script>
// 项目定制001，如果等级超过规定的比例则提示相关指标已经超标，无法提交。
seajs.use(['lui/jquery','lui/dialog','lui/topic',"lui/util/env"], function($,dialog ,topic,env) {
	Com_Parameter.event["submit"].push(function(){
		// 操作类型为通过类型
		/* if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
		// 检测内容，若条件完好，则return true。不能提交return false;
			
		} */
		var url = Com_Parameter.ContextPath+"pro/exam/pro_exam_sum_person/proExamSumPerson.do?method=getProExamDist";
		var del_load = dialog.loading();
		var isExceed = true;
		$.ajax({
			url: url,
			type: 'POST',
			data: $.param({"fdId": "${param.fdId}"}, true),
			dataType: 'json',
			// 默认true异步loading
			async: false,
			error: function(data){
				del_load.hide();
				dialog.failure("${lfn:message('errors.unknown')}");
			},
			success: function(data) {
				del_load.hide();
				if (data) {
					var fdSRate = data.fdSRate;
					var fdARate = data.fdARate;			
					var s = 0;
					var a = 0;

					var len = $("#TABLE_DocList_fdSumPlist_Form tr[class='docListTr']").length;
					for (var i=0;i<len;i++){
						var fdSuggestLevel = $("select[name='fdSumPlist_Form["+i+"].fdSuggestLevel']").val();
						if (fdSuggestLevel == "S") {
							++s;
						} else if (fdSuggestLevel == "A") {
							++a;
						} else if (fdSuggestLevel == "B") {
							++b;
						}
					}
					if (fdSRate != "" && s/len*100 > fdSRate) {
						dialog.alert("绩效等级S已超过：" + fdSRate + "%");
						isExceed = false;
					} 
					if (isExceed && fdARate != "" && a/len*100 > fdARate) {
						dialog.alert("绩效等级A已超过：" + fdARate + "%");
						isExceed = false;
					}
				}
			}
		});
		return isExceed;
	})
})

</script>

<c:if test="${param.method=='add'}">
<script>
seajs.use(['lui/jquery','lui/dialog','lui/topic',"lui/util/env"], function($,dialog ,topic,env) {
	window.createInventoryBatch2 = function(type){
		var values = [];
		$("input[name='List_Selected']:checked").each(function(){
				values.push($(this).val());
			});
		if(values.length==0){
			dialog.alert('<bean:message key="page.noSelect"/>');
			return;
		}
		var data = new KMSSData();
		var fdTaskId = '${JsParam.fdTaskId}';
		values = values.join(";");
		
		var url = Com_Parameter.ContextPath+"yjy/zc/yjy_zc_task/yjyZcTCardList.do?method=inventoryComplete&fdId="+fdTaskId+"&fdTaskId="+values;
		var del_load = dialog.loading();
		$.ajax({
			url: url,
			type: 'POST',
			data: $.param({"label": 2}, true),
			dataType: 'json',
			// 默认异步loading
			async: true,
			error: function(data){
				del_load.hide();
				dialog.failure("${lfn:message('errors.unknown')}");
			},
			success: function(data) {
				del_load.hide();
				if (data.success == "true") {
					dialog.success("${lfn:message('return.optSuccess')}");
					window.top.location.reload();
					//topic.publish("list.refresh");
					/* var i = 1;
					parent.show(parent.$("#label_tab td :first")) */
						
				} else {
					dialog.failure("${lfn:message('return.optFailure')}");
					if (data.msg) {
						console.error(data.msg);
					}
				}
			}
		});
	};
})


Com_Parameter.event["submit"].push(function(){
	var fdItemNo = $("input[name='fdItemNo']").val();
	//if(fdItemNo){
		var flag = null;
		Com_GetCurDnsHost()+'${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_card/yjyZcCard.do?method=view&fdId=test'
		
		var del_load = dialog.loading();
		$.ajax({
			url: Com_Parameter.ContextPath + "bcp/item/bcp_item_info/bcpItemInfo.do?method=checkSameItemNo",
			type: 'POST',
			data: $.param({"fdType": fdType}, true),
			dataType: 'json',
			// 同步返回值
			async: false,
			error: function(data){
				if(window.del_load!=null){
					window.del_load.hide(); 
				}
				seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
					dialog.failure("${lfn:message('errors.unknown')}");
				});
			},
			success: function(data){
				flag = data.flag;
			}
		});
		if(flag) {
			return true;
		} else{
			seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
				dialog.failure("${lfn:message('errors.unknown')}");
			});
			return false;
		}
	//}
})
</script>
</c:if>

打印
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('szc-hr:py.JiBenXinXi') }" expand="true">  --%>

<div style="padding: 15px 0px 10px 0px;border-left: 3px solid #46b1fc;height: 6px;line-height: 0;margin: 15px 0;"> 
	<div class="title" style="font-weight: 900;font-size: 16px;color: #000;text-align:left;margin-left: 8px;">
		<bean:message bundle="szc-hr" key="py.JiBenXinXi" />
	</div>
</div>
                    <table class="tb_normal" width="100%">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
    <template:replace name="head">
        <style type="text/css">
            
            			.lui_paragraph_title{
            				font-size: 15px;
            				color: #15a4fa;
            		    	padding: 15px 0px 5px 0px;
            			}
            			.lui_paragraph_title span{
            				display: inline-block;
            				margin: -2px 5px 0px 0px;
            			}
            			.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
            			    border: 0px;
            			    color: #868686
            			}
            		
        </style>
        <script type="text/javascript">
            var formInitData = {

                'fdNativeProviceId': '${lfn:escapeJs(szcHrTalentForm.fdNativeProviceId)}',
                'fdBirthProvinceId': '${lfn:escapeJs(szcHrTalentForm.fdBirthProvinceId)}',
                'fdBirthDate': '${lfn:escapeJs(szcHrTalentForm.fdBirthDate)}',
                'fdSelfElement': '${lfn:escapeJs(szcHrTalentForm.fdSelfElement)}'
            };
            var messageInfo = {

                'phoneCheck_checkIsMobile': '${lfn:escapeJs(lfn:message("szc-hr:py.GuoNeiShouJiHao"))}',
                'emailCheck_checkIsMail': '${lfn:escapeJs(lfn:message("szc-hr:py.YouXiangDiZhiQian"))}',
                'uniqueCheck_checkTempId': '${lfn:escapeJs(lfn:message("szc-hr:py.CiErWeiMaYi"))}',
                'uniqueCheck_checkCardNo': '${lfn:escapeJs(lfn:message("szc-hr:py.YiYouXiangTongShen"))}',
                'fdProjectList': '${lfn:escapeJs(lfn:message("szc-hr:table.szcHrPrjList"))}',
                'fdWorkList': '${lfn:escapeJs(lfn:message("szc-hr:table.szcHrWorkList"))}',
                'fdFamilyList': '${lfn:escapeJs(lfn:message("szc-hr:table.szcHrFamilyList"))}',
                'fdStudyList': '${lfn:escapeJs(lfn:message("szc-hr:table.szcHrStudyList"))}'
            };
        </script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${szcHrTalentForm.fdName} - " />
        <c:out value="${ lfn:message('szc-hr:table.szcHrTalent') }" />
    </template:replace>
    <template:replace name="toolbar">
        <script>
            function printorder() {
                window.print();
            }

            function deleteDoc(delUrl) {
                seajs.use(['lui/dialog'], function(dialog) {
                    dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                        if(isOk) {
                            Com_OpenWindow(delUrl, '_self');
                        }
                    });
                });
            }

            function openWindowViaDynamicForm(popurl, params, target) {
                var form = document.createElement('form');
                if(form) {
                    try {
                        target = !target ? '_blank' : target;
                        form.style = "display:none;";
                        form.method = 'post';
                        form.action = popurl;
                        form.target = target;
                        if(params) {
                            for(var key in params) {
                                var
                                v = params[key];
                                var vt = typeof
                                v;
                                var hdn = document.createElement('input');
                                hdn.type = 'hidden';
                                hdn.name = key;
                                if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                                    hdn.value =
                                    v +'';
                                } else {
                                    if($.isArray(
                                        v)) {
                                        hdn.value =
                                        v.join(';');
                                    } else {
                                        hdn.value = toString(
                                            v);
                                    }
                                }
                                form.appendChild(hdn);
                            }
                        }
                        document.body.appendChild(form);
                        form.submit();
                    } finally {
                        document.body.removeChild(form);
                    }
                }
            }

            function doCustomOpt(fdId, optCode) {
                if(!fdId || !optCode) {
                    return;
                }

                if(viewOption.customOpts && viewOption.customOpts[optCode]) {
                    var param = {
                        "List_Selected_Count": 1
                    };
                    var argsObject = viewOption.customOpts[optCode];
                    if(argsObject.popup == 'true') {
                        var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                        for(var arg in argsObject) {
                            param[arg] = argsObject[arg];
                        }
                        openWindowViaDynamicForm(popurl, param, '_self');
                        return;
                    }
                    var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
                    Com_OpenWindow(optAction, '_self');
                }
            }
            window.doCustomOpt = doCustomOpt;
            var viewOption = {
                contextPath: '${LUI_ContextPath}',
                basePath: '/szc/hr/szc_hr_talent/szcHrTalent.do',
                customOpts: {

                    ____fork__: 0
                }
            };

            var flag = 0;

            function ZoomFontSize(size) {
                //当字体缩小到一定程度时，缩小字体按钮变灰不可点击
                if(flag >= 0 || flag == -2) {
                    flag = flag + size;
                }
                if(flag < 0) {
                    $("#zoomOut").prop("disabled", true);
                    $("#zoomOut").addClass("status_disabled");
                } else {
                    $("#zoomOut").prop("disabled", false);
                    $("#zoomOut").removeClass("status_disabled");
                }
                var root = document.getElementsByClassName("tempTB")[0];
                var
                i = 0;
                for(
                    i = 0;
                    i < root.childNodes.length;
                    i ++) {
                    SetZoomFontSize(root.childNodes[
                        i], size);
                }
                var tag_fontsize;
                if(root.currentStyle) {
                    tag_fontsize = root.currentStyle.fontSize;
                } else {
                    tag_fontsize = getComputedStyle(root, null).fontSize;
                }
                root.style.fontSize = parseInt(tag_fontsize) + size + 'px';
            }

            function SetZoomFontSize(dom, size) {
                if(dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
                    for(var
                        i = 0;
                        i < dom.childNodes.length;
                        i ++) {
                        SetZoomFontSize(dom.childNodes[
                            i], size);
                    }
                    var tag_fontsize;
                    if(dom.currentStyle) {
                        tag_fontsize = dom.currentStyle.fontSize;
                    } else {
                        tag_fontsize = getComputedStyle(dom, null).fontSize;
                    }
                    dom.style.fontSize = parseInt(tag_fontsize) + size + 'px';
                }
            }

            function ClearDomWidth(dom) {
                if(dom != null && dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
                    //修改打印布局为 百分比布局 #曹映辉 2014.8.7
                    /****
            			var w = dom.getAttribute("width");
            			if (w != '100%')
            				dom.removeAttribute("width");
            			w = dom.style.width;
            			if (w != '100%')
            				dom.style.removeAttribute("width");
            			****/
                    if(dom.style.whiteSpace == 'nowrap') {
                        dom.style.whiteSpace = 'normal';
                    }
                    if(dom.style.display == 'inline') {
                        dom.style.wordBreak = 'break-all';
                        dom.style.wordWrap = 'break-word';
                    }
                    ClearDomsWidth(dom);
                }
            }

            function ClearDomsWidth(root) {
                for(var
                    i = 0;
                    i < root.childNodes.length;
                    i ++) {
                    ClearDomWidth(root.childNodes[
                        i]);
                }
            }

            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
        </script>
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

            <ui:button text="${lfn:message('button.print')}" onclick="printorder()"></ui:button>
            <ui:button id="zoomIn" text="${ lfn:message('szc-hr:button.zoom.in') }" onclick="ZoomFontSize(2);">
            </ui:button>
            <ui:button id="zoomOut" text="${ lfn:message('szc-hr:button.zoom.out') }" onclick="ZoomFontSize(-2);">
            </ui:button>
            <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="content">
    	
    	<c:import url="szcHrTalent_view_print.jsp"></c:import>
    
    </template:replace>

</template:include>


<!-- 项目定制001 -->
				 	<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==false}">
				 		<kmss:authShow extendOrgIds="${kmImeetingMainForm.docCreatorId}">
							<li data-dojo-type="mui/tabbar/TabBarButton" id="reportQRCode" class="muiBtnSubmit" data-dojo-props="colSize:3">
								<div id="report" onclick="report()">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.reportQRCode"/>
								</div>
								<div id='qrcodexDiv' style='width: 128px;margin: auto;'/>
							</li>
						</kmss:authShow>
					</c:if>
<script>
	require(["dojo/dom",
			'dojo/topic',
	         'dojo/ready',
	         'dijit/registry',
	         'dojox/mobile/TransitionEvent',
	         'dojo/request',
	         'dojo/query',
	         'dojo/dom-construct',
	         'dojo/dom-style',
	         'dojo/dom-geometry',
	         'mui/util',
	         'mui/dialog/BarTip',
	         "dojo/_base/lang",
	         "dojo/request",
	         'mui/dialog/Tip',
			 'mui/dialog/Dialog',
			 'dojo/_base/array',
			 'mui/qrcode/QRcode',//项目定制001
	         ],function(dom,topic,ready,registry,TransitionEvent,request,query,domConstruct,
			 domStyle,domGeometry,util,BarTip,lang,req,Tip,Dialog,array,qrcode){
		
		
		window.openLink = function(url){
			window.open(url,'_self'); 
		};
		 /* 其他更多操作 */
		 var otherListBtn = dom.byId("otherListBtn");
		 if(otherListBtn){
			 var OtherList = dom.byId("kmImeetingMain_other");
			 dom.byId("otherListBtn").onclick= function (){
					var DialogObj = new Dialog.claz({
						element:OtherList,
						scrollable:false,
						parseable:false,
						position:"bottom",
						canClose:false
					});
					DialogObj.show();
				    var dialogCancel =  query(".muiDialogElementContainer_bottom .otherDialogCancel");
					dialogCancel[0].onclick=function(){
						DialogObj.hide();
					};
					//项目定制001
					dom.byId("reportQRCode").onclick= function (){
						DialogObj.hide();
					}
			  } 
		 }

//项目定制001
		window.report = function(){
			var qrcodexDiv = dom.byId("qrcodexDiv");
			if($("#qrcodexDiv img").length==0){
				var obj = qrcode.make({
					url: Com_GetCurDnsHost()+"/sg/report/sg_report_info/sgReportInfo.do?method=add&fdModelId=${kmImeetingMainForm.fdId }"
				});
				domConstruct.place(obj.domNode, qrcodexDiv);
			}
			var DialogObj = new Dialog.claz({
				title: '<bean:message bundle="km-imeeting" key="kmImeetingMain.reportQRCode"/>',
				element: qrcodexDiv,
				destroyAfterClose: false,
				closeOnClickDomNode: true,
				parseable: true,
				canClose: true,
				showClass: 'muiAttendDialogShow',
				transform: 'bottom',
				position: 'bottom',
				buttons: [{
					title : "${lfn:message('km-imeeting:button.cancel')}",
					fn : function(dialog) {
						dialog.hide();
					}
				}],
				scrollable: false
			});
		}

<center>
	<div id="printTable" class="page" style="border: none;">
		<c:forEach var="card" items="${cards }" varStatus="status">
			<div class="container" style="height: 149px;">
				<div class="left" style="margin-top: 15px;">
					<c:import url="/yjy/asset/yjy_asset_card/yjyAssetCard_printQrCode.jsp" charEncoding="UTF-8">
						<c:param name="fdId" value="${card.fdId }"></c:param>
						<c:param name="index" value="${status.index }"></c:param>
					</c:import>
				</div>
				<div class="right" style="margin-top: 15px;">
					<p><bean:message bundle="yjy-asset" key="yjyAssetCard.fdName"/> : ${card.fdName }</p> 
					<p><bean:message bundle="yjy-asset" key="yjyAssetCard.fdCode"/> : ${card.fdCode }</p>
					<p><bean:message bundle="yjy-asset" key="yjyAssetCard.fdStandard"/> : ${card.fdStandard }</p>
					<p><bean:message bundle="yjy-asset" key="yjyAssetCard.fdFirstUsedDate"/> : ${card.fdFirstUsedDate }</p>
				</div>
				<div class="clear"></div>
			</div>
		</c:forEach>
		<div style="display:block;clear:both;"></div>
	</div>
</center>

<center>
<div>
	<div class="detailQrCode">

	</div>
</div>
</center>

<td rowspan="4" width="50%">
			<div id="_xform_fdCode" _xform_type="text">
				<script src="/kms/common/resource/js/lib/jquery.qrcode.js"></script>
				<div class="output" style="text-align: center;"></div>
				<script>
					$(function() {
						var message = "${qrCodeMessage}";
						$('.output').qrcode({
							render : "canvas", //也可以替换为table
							foreground : "#000",
							background : "#f2f2f2",
							text : toUtf8(message),
							width : 120,
							height : 120
						});
					})
					function toUtf8(str) {
				    var out, i, len, c;
				    out = "";
				    len = str.length;
				    for (i = 0; i < len; i++) {
				        c = str.charCodeAt(i);
				        if ((c >= 0x0001) && (c <= 0x007F)) {
				            out += str.charAt(i);
				        } else if (c > 0x07FF) {
				            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
				            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
				            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
				        } else {
				            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
				            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
				        }
				    }
				    return out;
				}
				</script>
			</div>
		</td>


	<li>
                        	<a onclick="openPage('${LUI_ContextPath }/dm/docmgt/dm_docmgt_main/index_fileImport.jsp#cri.q=fdType:import');">
                        		${lfn:message('dm-doclog:nav.fileImport')}
                        	</a>
                        </li>

<template:include file="/sys/profile/resource/template/list.jsp">

<template:include ref="config.edit" >


<div style="margin: auto;width: 50%;">
        			<div style="padding: 50px;">
		        		<input name="code" type="text" placeholder="${lfn:message('pco-code:pleaceCheck')}"  
		        			style="width: 70%;height: 30px;border-radius: 5px;border: 1px solid #d1d1d1;padding: 5px;"/>
						<button type="button" style="width: 10%;height: 30px;margin-left: 30px;" onclick="checkCode();">${lfn:message('pco-code:check')}</button>
					</div>
					<div id="tab" style="display: none;"></div>
        	</div>
function checkCode() {
		var code = $("input[name='code']").val();
		var url = Com_GetCurDnsHost()+"${KMSS_Parameter_ContextPath}pco/code/pco_code_qr/pcoCodeQr.do?method=checkCode";
		
		$("#tab").load(url, {"code" : code}, function(responseTxt, statusTXT, xmlHttpRequest){
			if(statusTXT != "success") {
				seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
					dialog.failure(xmlHttpRequest.status + " " + xmlHttpRequest.statusText);
				});
			} else {
				$("#tab").slideDown('fast');
			}
		});
	}

JSON.parse(jsonstr); //可以将json字符串转换成json对象

JSON.stringify(jsonobj);//可以将json对象转换成json对符串

$.parseJSON( jsonstr ); //jQuery.parseJSON(jsonstr),可以将json字符串转换成json对象

<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript" src='${LUI_ContextPath}/resource/js/domain.js?s_cache=${ LUI_Cache }'></script>
<script type="text/javascript" src='${LUI_ContextPath}/resource/js/form.js?s_cache=${ LUI_Cache }'></script>
<script type="text/javascript" src='${LUI_ContextPath}/sys/ui/js/LUI.js?s_cache=${ LUI_Cache }'></script>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js?s_cache=${ LUI_Cache }"></script>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/sea.js?s_cache=${ LUI_Cache }"></script>

<span style="display: flex;">
	<c:if test="${not empty fdSignList.fdQrCodeMessage}">
		<div id="testId" style="margin: auto"></div>

//匿名 <request path="pco_code_qr/pcoCodeQrCheck.do*"  defaultValidator="true"> </request>

// 自我居中
<div style="display: flex;height: inherit;">
	<div style="width: 50%;text-align: center;align-self: center; ">
		<img src="https://img0.baidu.com/it/u=3717801612,1039119859&fm=253&fmt=auto&app=138&f=PNG?w=450&h=299" height="250px;" />
	</div>
	<div style="width: 50%;text-align: center;align-self: center;">
		<img src="https://img0.baidu.com/it/u=3717801612,1039119859&fm=253&fmt=auto&app=138&f=PNG?w=450&h=299" height="200px;" />
	</div>
</div>
<div style="display: flex;">
	<div id="testId" style="margin: auto;"></div>
	<span style="margin: auto;"></span>
</div>

// 表格居中
<div style="width: 1000px; height: 330px; border: solid; border-width: thin; border-color: rgb(0, 0, 255);">
	<div style="width: 100%;display: table;height: inherit;">
		<div style="width: 50%;text-align: center;display: table-cell;vertical-align: middle;">
			<img src="http://up.deskcity.org/pic/bd/24/24/bd2424fdb329e5c2b71626de7f7a75ed.jpg" height="250px;" />
		</div>
		<span style="width: 50%;text-align: center;display: table-cell;vertical-align: middle;">
			<img src="http://up.deskcity.org/pic/bd/24/24/bd2424fdb329e5c2b71626de7f7a75ed.jpg" height="200px;" />
		</span>
	</div>
</div>

<div style="width: 1000px;height: 300px;border: solid; border-width: thin; border-color: rgb(0, 255, 0);">
	<div style="width: 50%;text-align: center;display: table-cell;vertical-align: middle;height: inherit;">
		<img src="1.jpg" height="250px;" />
	</div>
	<div style="width: inherit;text-align: center;display: table-cell;vertical-align: middle;height: inherit;">
		<img src="1.jpg" height="200px;" />
	</div>
</div>

// 浮动居中xxxx
<div style="width: 1200px;height: 500px;">
	<div style="width: 50%; text-align: center; float: left;">
		<img src="https://img0.baidu.com/it/u=3717801612,1039119859&fm=253&fmt=auto&app=138&f=PNG?w=450&h=299" style="vertical-align: middle;" />
	</div>
		
	<div style="width: 50%; text-align: center; float: left;">
		<img src="https://img0.baidu.com/it/u=3717801612,1039119859&fm=253&fmt=auto&app=138&f=PNG?w=450&h=299" style="vertical-align: middle;" />
	</div>	
</div>

// 绝对定位相对绝对图片
<div style="width: 1000px; height: 444px; border: solid; border-width: thin; border-color: rgb(0, 0, 255);">
	<div style="position: relative;  height: 100%;width: 100%;">
		<div style="position: absolute;width: 50%;text-align: center;top:50%;">
			<img src="http://up.deskcity.org/pic/bd/24/24/bd2424fdb329e5c2b71626de7f7a75ed.jpg" height="250px;" style="transform:translateY(-50%);" /><!-- 图片像素不确定时 -->
		</div>
			 
		<div style="position: absolute;left: 50%;width: 50%;text-align: center;top:50%;">
			<div style="position:relative;top: -100px;"><!-- 取图片高度负一半像素 -->
				<img src="http://up.deskcity.org/pic/bd/24/24/bd2424fdb329e5c2b71626de7f7a75ed.jpg" height="200px;" />
			</div>
		</div>
		
		<div style="position: absolute;left: 75%;top:50%;margin-top: 50px;">
			<img src="http://up.deskcity.org/pic/bd/24/24/bd2424fdb329e5c2b71626de7f7a75ed.jpg" height="200px;" style="transform:translate(-50%,-50%);"/>
		</div>
		
	</div>
</div>

<iframe name="file_frame" style="display:none;"></iframe>
<script>
$(function(){
	attachmentObject_attFuwu.on("uploadSuccess", function() {
		var fileList = attachmentObject_attFuwu.fileList;
		var fdId = fileList[fileList.length-1].fdId;
		fdId = fileList[fileList.length-1].fileKey;
		
		var form = document.getElementsByName("bcpPurchMainForm")[0];
		var url = "${KMSS_Parameter_ContextPath}bcp/purch/bcp_purch_main/bcpPurchMain.do?method=loadAttFuwuExcel&fdId="+fdId;
		document.forms['bcpPurchMainForm'].action = url;
		form.target = "file_frame";
		document.forms['bcpPurchMainForm'].submit();
	});
	
})
seajs.use(['sys/ui/js/dialog'], function(dialog) {
	window.attFuwuCallback = function(jsonArr) {
		var form = document.getElementsByName("bcpPurchMainForm")[0];
		form.action = "${KMSS_Parameter_ContextPath}bcp/purch/bcp_purch_main/bcpPurchMain.do";
		form.target = "";
		
		for(var i=0;i<jsonArr.length;i++){
	    	var rowData = {
			        'fdSList_Form[!{index}].fdRequireNo':jsonArr[i].key0?jsonArr[i].key0:'',
			        'fdSList_Form[!{index}].fdLastPurNo':jsonArr[i].key1?jsonArr[i].key1:'',
			        'fdSList_Form[!{index}].fdApplyTypeId':jsonArr[i].fdApplyTypeId?jsonArr[i].fdApplyTypeId:'',
			    	'fdSList_Form[!{index}].fdApplyTypeName':jsonArr[i].key2?jsonArr[i].key2:'',
			        'fdSList_Form[!{index}].fdPurchasetypeId':jsonArr[i].fdPurchasetypeId?jsonArr[i].fdPurchasetypeId:'',
			        'fdSList_Form[!{index}].fdPurchasetypeName':jsonArr[i].key3?jsonArr[i].key3:'',
			        'fdSList_Form[!{index}].fdEqui':jsonArr[i].key4?jsonArr[i].key4:'',
			        'fdSList_Form[!{index}].fdServiceNo':jsonArr[i].key5?jsonArr[i].key5:'',
			        'fdSList_Form[!{index}].fdLastcurrencyId':jsonArr[i].fdLastcurrencyId?jsonArr[i].fdLastcurrencyId:'',
			        'fdSList_Form[!{index}].fdLastcurrencyName':jsonArr[i].key6?jsonArr[i].key6:'',
			        'fdSList_Form[!{index}].fdLastPrice':jsonArr[i].key7?jsonArr[i].key7:'',
			        		
			        'fdSList_Form[!{index}].fdLastQty':jsonArr[i].key8?jsonArr[i].key8:'',
			        'fdSList_Form[!{index}].fdLastserviceStartDate':jsonArr[i].key9?jsonArr[i].key9:'',
			        'fdSList_Form[!{index}].fdLastserviceEndDate':jsonArr[i].key10?jsonArr[i].key10:'',
			        'fdSList_Form[!{index}].fdPurNo':jsonArr[i].key11?jsonArr[i].key11:'',
			        'fdSList_Form[!{index}].fdServiceStartDate':jsonArr[i].key12?jsonArr[i].key12:'',
			        'fdSList_Form[!{index}].fdServiceEndDate':jsonArr[i].key13?jsonArr[i].key13:'',
			        'fdSList_Form[!{index}].fdRenewNotify':jsonArr[i].key14?jsonArr[i].key14:'',
			        'fdSList_Form[!{index}].fdApplybudNo':jsonArr[i].key15?jsonArr[i].key15:'',
			        'fdSList_Form[!{index}].fdApplyNo1':jsonArr[i].key16?jsonArr[i].key16:'',
			        'fdSList_Form[!{index}].fdApplyNo2':jsonArr[i].key17?jsonArr[i].key17:'',
			        'fdSList_Form[!{index}].fdAssetsIns':jsonArr[i].key18?jsonArr[i].key18:'',
			        'fdSList_Form[!{index}].fdApportUnit':jsonArr[i].key19?jsonArr[i].key19:'',
			        'fdSList_Form[!{index}].fdVendorSelinfoFlag':jsonArr[i].key20?jsonArr[i].key20:'',
			        'fdSList_Form[!{index}].fdVendorSelect':jsonArr[i].key21?jsonArr[i].key21:'',
			        'fdSList_Form[!{index}].fdVendorId':jsonArr[i].fdVendorId?jsonArr[i].fdVendorId:'',
			        'fdSList_Form[!{index}].fdVendorName':jsonArr[i].key22?jsonArr[i].key22:'',
			        'fdSList_Form[!{index}].fdReplydatee':jsonArr[i].key23?jsonArr[i].key23:'',
			        'fdSList_Form[!{index}].fdPurchasedaye':jsonArr[i].key24?jsonArr[i].key24:'',
			        'fdSList_Form[!{index}].fdLabel':jsonArr[i].key25?jsonArr[i].key25:'',
			        'fdSList_Form[!{index}].fdDetailrequire':jsonArr[i].key26?jsonArr[i].key26:'',
			        'fdSList_Form[!{index}].fdRemarks':jsonArr[i].key27?jsonArr[i].key27:'',
			        		
					'fdSList_Form[!{index}].attServiceKey':null,
			        'fdSList_Form[!{index}].fdTechgroupId':jsonArr[i].fdTechgroupId?jsonArr[i].fdTechgroupId:'',
			        'fdSList_Form[!{index}].fdTechgroupName':jsonArr[i].key28?jsonArr[i].key28:'',
			        'fdSList_Form[!{index}].fdSuppinfo':jsonArr[i].key29?jsonArr[i].key29:'',
	        };
	    	DocList_AddRow("TABLE_DocList_fdSList_Form", null, rowData);
		}
		dialog.success("导入成功！");
	}
})

$("#TABLE_DL_fd_3a5404a3c77026 tr[trdraggable='true']").each(function(){
						DocList_DeleteRow_ClearLast(this);
					})
					$("#TABLE_DL_fd_3a5404a3c77026").find("input[name='DocList_SelectAll']").each(function(){
						this.checked = false;
					});
for(var i=0;i<jsonArr.length;i++) {
		var rowData = {
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405b34c4510)': jsonArr[i].fd_3a5405b34c4510,
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405b67529de)': jsonArr[i].fd_3a5405b67529de,
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405bb4181e6)': jsonArr[i].fd_3a5405bb4181e6,
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3ac2d88579a312)': jsonArr[i].fd_3ac2d88579a312,
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405bd990484)': jsonArr[i].fd_3a5405bd990484,
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405c18bee0c)': jsonArr[i].fd_3a5405c18bee0c,
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405c41bf3ba)': getDateString(jsonArr[i].fd_3a5405c41bf3ba),
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405ca5b6570.id)': jsonArr[i].fd_3a5405ca5b6570.id?jsonArr[i].fd_3a5405ca5b6570.id:'',
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405ca5b6570.name)': jsonArr[i].fd_3a5405ca5b6570.name?jsonArr[i].fd_3a5405ca5b6570.name:'',
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405ccaf8bb4.id)': jsonArr[i].fd_3a5405ccaf8bb4.id?jsonArr[i].fd_3a5405ccaf8bb4.id:'',
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405ccaf8bb4.name)': jsonArr[i].fd_3a5405ccaf8bb4.name?jsonArr[i].fd_3a5405ccaf8bb4.name:'',
				'extendDataFormInfo.value(fd_3a5405a9d53510.!{index}.fd_3a5405cea86304)': jsonArr[i].fd_3a5405cea86304,
		};
		DocList_AddRow("TABLE_DL_fd_3a5405a9d53510", null, rowData);
	}
	if(jsonArr){
		DocList_DeleteRow($("#TABLE_DL_fd_3a5405a9d53510 tr")[1]);
	}
function getDateString(date) {
	// sf.date == null
	if(date){
		var year = date.year + 1900;
		var month = padding(date.month + 1, 2);
		var day = padding(date.date, 2);
		return year + "-" + month + "-" + day;
	}
	return '';
}
function padding(num, length) {
	if((num + "").length >= length) {
		return num;
	}
 return padding("0" + num, length)
}

//项目定制001
seajs.use(['lui/dialog'], function(dialog){
	window.dialog=dialog;
});
//监听是否已经有调整行为，如有则重新
$(document).on('table-add',"table[id$='TABLE_DocList_fdListReview_Form']",function(e, row) {
    $("input[name='fdIsModifyPurch']").val("yes");
});


var len = "yyyy-MM-dd".length;
var fdServiceStartDate = data.fdServiceStartDate;
setDate(obj.name.replace("fdRequireNo", "fdServiceEndDate"), data.fdServiceEndDate, len);

function setDate(name, value, len) {
	if(value != null && value.length >= len){
		value = value.substring(0, len);
		$("input[name='" + name + "']").val(value);
	}
}

function getContractPersonNo(obj){
	var arr = obj.rtnData.data;
	if(arr[0]){
		var fdContractPersonId = arr[0].id;
		$.ajax({
			url: Com_Parameter.ContextPath + "bcp/purch/bcp_purch_main/bcpPurchMain.do?method=getContractPersonNo",

<script type="text/javascript">
<script type="text/javascript">
AttachXFormValueChangeEventById("fd_39c5a81d13d790", function(val, dom){
	var value = $("[name='extendDataFormInfo.value\\(fd_39c5a81d13d790\\)']").val();
	if(value){
	var arr = $.parseJSON(value);
	var docIds = "";
	var fdModelNames = "";
	for(var i = 0;i<arr.length; i++){
		docIds += arr[i].docId + ";";
		fdModelNames += arr[i].fdModelName + ";";
	}
	isPurchase(docIds, fdModelNames);
	}
	
});

seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
	window.isPurchase = function(docIds, fdModelNames) {
		if(docIds && fdModelNames) {
			$.ajax({
				url: Com_Parameter.ContextPath + "km/agreement/km_agreement_apply/kmAgreementApply.do?method=isPurchase",
				type: 'POST',
				data: $.param(
						{"docIds": docIds, 
						 "fdModelNames": fdModelNames
						},true),
				dataType: 'json',
				async: false,
				error: function(data){
					if(window.del_load!=null) {
						window.del_load.hide(); 
					}
					//dialog.result(data.responseJSON);
					dialog.failure("${lfn:message('errors.unknown')}");
				},
				success: function(data) {
					if(data.isPurchase == false) {
						dialog.failure("合同审批表单中除人才房租赁、框架协议外必须关联之前已有的“采购申请 ”");
					}
				}
			});
		}
	}

	Com_Parameter.event["submit"].push(function(){
		dialog.alert("合同审批表单中除人才房租赁、框架协议外必须关联之前已有的“采购申请 ”");
	})
})
</script>

<div style="display: inline;">
	<input type="button" style="background: transparent;border-radius: 4px;border: 1px solid #c2c2c2;padding: 6px;cursor: pointer" value="新建" 
		onclick="window.open(Com_GetCurDnsHost()+'${LUI_ContextPath }/km/relative/km_relative_main/kmRelativeMain.do?method=add')" >
</div>


window.onload=function(){
	var len = $("#TABLE_DocList_fdAddList_Form tr[class='docListTr']").length;
	for(var i=0;i<len;i++){
		$("input[name='fdAddList_Form["+i+"].fdAddAmount']").change(function () {
			$("input[name='fdIsModifyPurch']").val("yes");
		})
		$("input[name='fdAddList_Form["+i+"].fdAddMaxmonth']").change(function () {
			$("input[name='fdIsModifyPurch']").val("yes");
		})
	} 
}

			dialog.alert("在服務明細表，供應商評選方式=自行評選，附件為必填項;", function() {
				$("html,body").stop(true);$("html,body").animate({scrollTop: $("#sListTableTr").offset().top - 42}, 500);
				$("#sListTableDiv").scrollLeft(4500);
			});

	$("input[name='extendDataFormInfo.value\\(fd_3b05ac4e40427c\\)']").change(function(){
		if('是' == $(this).val()){
			$("#fd_383e71a8f0d63c #TABLE_DL_fd_39c74eb1c6e174").parent().parent().prev().show();
			$("#fd_383e71a8f0d63c #TABLE_DL_fd_39c74eb1c6e174").parents("tr").eq(0).show();
		}else if('否' == $(this).val()){
			$("#fd_383e71a8f0d63c #TABLE_DL_fd_39c74eb1c6e174").parent().parent().prev().hide();
			// 往上查找父tr的第一个
			$("#fd_383e71a8f0d63c #TABLE_DL_fd_39c74eb1c6e174").parentsUntil("tr").parent().hide();
		}
	})

LUI.ready(function(){
		domain.autoResize();
  	});

window.onload=function(){
	var b_width = Math.max(document.body.scrollWidth,document.body.clientWidth);
    var b_height = Math.max(document.body.scrollHeight,document.body.clientHeight);
    var c_iframe = window.parent.document.getElementById("menu_frame");
    c_iframe.height = b_height;
};


(function autoHeight(){
	var b_width = Math.max(document.body.scrollWidth,document.body.clientWidth);
	var b_height = Math.max(document.body.scrollHeight,document.body.clientHeight);
	var c_iframe = window.parent.document.getElementById("menu_frame");
	//c_iframe.height = b_height;
})()

					<script>
					LUI.ready(function() {
						window.onload = function(){
							console.log(true);
							console.log($(".card_menu").eq(0));
							$(".card_menu").eq(0).click();
						}
					})
					</script>

var hasContract = $("input[name='hasContract']")[0].checked;
	if(hasContract == true){
		$(".contract").show();
	}

var dialogSelect = function(mul, key, idField, nameField, targetWin, func) {}
<div id="_xform_fdFsscPurchId" _xform_type="dialog">
<xform:dialog propertyId="fdFsscPurchId" propertyName="fdFsscPurchName" showStatus="edit" style="width:95%;">
dialogSelect(false,'fssc_fee_main_selectPurch','fdFsscPurchId','fdFsscPurchName', null, function(obj){console.log(obj)});
</xform:dialog>
</div>


<% 
   request.setAttribute("opinionImport", true);
%>
<template:include ref="config.list">
	<%@ include file="/yjy/opinion/yjy_opinion_main/index_import_content.jsp" %>
</template:include>
<c:if test="${empty opinionImport}"></c:if>

function windowOpenInterval(url) {
	const winURL = window.open(url);  	// 设置要打开的对象
	const loop = setInterval(() => {		// 使用定时器查询当前状态
	    if (winURL && winURL .closed) {		// 进行判断条件   closed属性就是返回当前窗口的状态
	      clearInterval(loop);       		// 清除定时器
	      window.location.reload();
	    }
	  }, 500);
}


					var url = new URL(window.location.href);
					url.searchParams.set('fdApplyId', obj[0].fdApplyId);
					url.searchParams.set('fdIsImport', true);
					//url.searchParams.append('x', 42);
					
					window.location.href = url.href;

			var fsscBaseSuppliers = data.datas;
			
			var jsonArray = new Array();
			if(fsscBaseSuppliers.length > 0) {
				
				var json = {};
				var fsscBaseSupplier = fsscBaseSuppliers[0];
				for(var i = 0; i < fsscBaseSupplier.length; i++){
					var key = fsscBaseSupplier[i].col;
					var value = fsscBaseSupplier[i].value;
					json[key] = value;
					
				}
				jsonArray.push(json);
				
				console.log(jsonArray);
			}

<div onclick="Com_OpenWindow(
	'${LUI_ContextPath }/km/agreement/km_agreement_apply/kmAgreementApply.do?method=view&fdId=${kmAgreementChangeForm.fdApplyId }', '_self');" >
	<xform:text property="docSubject" showStatus="edit" style="<%=parse.getStyle()%>" required="false" 
		htmlElementProperties='readonly="readonly" style="color: cornflowerblue; cursor: pointer;"'/>
</div>


<script type="text/javascript">
//此处添加js代码
$(function(){
	var fdId = $("input[name='extendDataFormInfo.value\\(fd_3b3cbe6a21cbc4\\)']").val();
	if(fdId){
		$("[id='_xform_extendDataFormInfo.value\\(fd_3b3cbcd23ef646\\)']").eq(0).click(function(){
		//$("input[name='extendDataFormInfo.value\\(fd_3b3cbcd23ef646\\)']").click(function(){
			window.open(Com_GetCurDnsHost()+'${LUI_ContextPath }/km/agreement/km_agreement_apply/kmAgreementApply.do?method=view&fdId=' + fdId);
		})

		$("input[name='extendDataFormInfo.value\\(fd_3b3cbcd23ef646\\)']").addClass("apply");
		$("[id='_xform_extendDataFormInfo.value\\(fd_3b3cbcd23ef646\\)']").eq(0).addClass("apply")
	
	}
})
</script>

<style>
/* 此处添加css代码  */
.apply:hover {
	cursor: pointer;
	color: cornflowerblue;
}

</style>

<template:include ref="config.list">
	
	<template:replace name="title">
        <c:out value="${ lfn:message('yjy-opinion:module.yjy.opinion') }-${ lfn:message('yjy-opinion:table.yjyOpinionMain') }" />
    	</template:replace>

</template:include>

        <list:data-column col="docSubject" escape="false" title="${lfn:message('km-agreement:ledger.report.name')}" >
    	<a href="javascript:Com_OpenWindow('${LUI_ContextPath }/km/agreement/km_agreement_apply/kmAgreementApply.do?method=view&fdId=${kmAgreementLedger.fdApply.fdId}', '_black');" style="color:#51b6ec;text-decoration:underline;" title="${altSubject}">
            		<c:out value="${kmAgreementLedger.docSubject}" />
	</a>
	<a href="../km_agreement_apply/kmAgreementApply.do?method=view&fdId=${kmAgreementLedger.fdApply.fdId}" target="_blank"
	    		style="color:cornflowerblue; text-decoration:underline;" title="${altSubject}">
            		<c:out value="${kmAgreementLedger.docSubject}" />
	</a>
        </list:data-column>

	<kmss:authShow extendOrgIds="${yjyZcTaskForm.docCreatorId};${yjyZcTaskForm.fdAssignPersonId}">
               		<c:if test="${ yjyZcTaskForm.fdStatus =='1' && yjyZcTaskForm.docStatus=='30' }">
               			<ui:button text="资产异常" onclick="excInfo('${yjyZcTaskForm.fdId}')" order="2" />
               		</c:if>
               	</kmss:authShow>

<c:if test="${not empty param.label }">
	<script>
		var i = "${param.label}";
		var timer = null;
		function checkLabel(){
			var label = $("#Label_Tabel").find("nobr");
			console.log(label.length);
			if(label.length){
				$("#Label_Tabel").find("nobr").eq(5).find("input[type='button']").click();
			} else{
				timer = setTimeout(checkLabel, 100);
			}
		}
		window.onload = function() {
			checkLabel();
		}
	</script>
</c:if>


var data = new KMSSData();
    var url = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTask.do?method=loadTaskDetail&fdTypeId="+fdTypeId+"&fdDutyDeptId="+fdDutyDeptId+"&fdTaskTime="+fdTaskTime;

    data.SendToUrl(url, function(data){
    	var data = eval("(" + data.responseText + ")");
    	for(var i=0;i<data.length;i++){
    		var array = new Array();
    		array.push(null);
    		array.push(null);
    		if(data[i].fdCode){
    			array.push('<input type="hidden" name="fdCardList_Form[!{index}].fdCardCode" value="'+data[i].fdCode+'" />'+data[i].fdCode);
    		}else{
    			array.push('<input type="hidden" name="fdCardList_Form[!{index}].fdCardCode" value="'+''+'" />'+'');
    		}
    		
    		if(data[i].fdCategoryId){
    			array.push('<input type="hidden" name="fdCardList_Form[!{index}].fdCardCategoryId" value="'+data[i].fdCategoryId+'" />'+data[i].fdCategoryName);
    		}else{
    			array.push('<input type="hidden" name="fdCardList_Form[!{index}].fdCardCategoryId" value="'+''+'" />'+'');
    		}
	    	DocList_AddRow("TABLE_DocList_fdCardList_Form",array);
    	}
    	if(data.length==0){
    		seajs.use([ 'lui/jquery', 'lui/dialog', 'lui/topic','lui/dialog_common' ],function($, dialog, topic, dialogCommon) {
				dialog.alert("没有查询到符合条件的资产！");
			})
    	}


<table class="tb_normal" width="100%">
	<tbody>
	<tr><td class="td_normal_title">
		<input type="button" style="background: transparent;border-radius: 4px;border: 1px solid #c2c2c2;padding: 6px;cursor: pointer;"
		onmouseover="this.style.background='rgb(222,222,222)';" onmouseout="this.style.background='transparent';" value="${ lfn:message('yjy-zc:yjyZcTask.onclikSelect') }" onclick="loadTaskDetail();" />
		<span style="color: gray;font-size: 10px;">${ lfn:message('yjy-zc:yjyZcTask.explainSelect') }</span>
	</td></tr>
	</tbody>
</table>


<table id="label_tab" width="100%" style="height:380px !important;" >
	<tr>
		<td style="border-bottom: 2px solid #2f84fb">
		
		<input type="button" style="border: 1px solid #2f84fb;border-bottom: 0px;padding: 6px 10px;cursor: pointer;color: white;background: #2f84fb;margin: 0px;" value="待盘点" onclick="show(this);"
			/>
		<input type="button" style="border: 1px solid #c2c2c2;border-bottom: 0px;padding: 6px 10px;cursor: pointer;" value="已盘点" onclick="show(this);"
			/>
		<input type="button" style="border: 1px solid #c2c2c2;border-bottom: 0px;padding: 6px 10px;cursor: pointer;" value="盘盈" onclick="show(this);"
			/>
		<input type="button" style="border: 1px solid #c2c2c2;border-bottom: 0px;padding: 6px 10px;cursor: pointer;" value="盘亏" onclick="show(this);"
			/>
		<input type="button" style="border: 1px solid #c2c2c2;border-bottom: 0px;padding: 6px 10px;cursor: pointer;" value="待定" onclick="show(this);"
			/>
		
		</td>
	</tr>
	
	<tr><td height="100%" style="border-left-style: solid!important;border-left-width: 1px!important;border-color: #2f84fb;">
		<div style="margin-top: 2%;margin-left: 2%;">
			<label><input type="radio" name="inventory" value="all" onclick="showIframe(this);">全部资产</label>
			<label><input type="radio" name="inventory" value="my" checked="checked" onclick="showIframe(this);">待我盘点的资产</label>		
		</div>
	    <iframe id="todoInventory" width="100%" height="90%" frameborder="0" marginheight="0" 
	    	src="${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?my=true&fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=1"+
	    		"&fdAssignUser=${JsParam.fdAssignUser}&personnelIds=${JsParam.personnelIds}&fdStatus=${param.fdStatus}&fdKeeperId=${param.fdKeeperId}">
	    </iframe>
	</td></tr>
</table>


<style>
   #label_tab input:hover {
		background: #d0d0d0;
   }
</style>
<script>
function showIframe(obj){
	var todoInventory = document.getElementById('todoInventory');
	//var myInventory = document.getElementById('myInventory');
	//var radioValue = $('input[name="inventory"]:checked').val();
	var radioValue = $(obj).val();
	if (radioValue == 'all') {
		var src = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=1";
		todoInventory.src = src;
	} else if(radioValue == 'my') {
		var src = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?my=true&fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=1"+
					"&fdAssignUser=${JsParam.fdAssignUser}&personnelIds=${JsParam.personnelIds}&fdStatus=${param.fdStatus}&fdKeeperId=${param.fdKeeperId}";
		todoInventory.src = src;
	}
}
function show(obj) {
	var index = $(obj).index();
	var todoInventory = document.getElementById('todoInventory');
	var src = "";
	
	var label = $("#label_tab").find("label");
	if (index == 0) {
		label.show();
		$('input[name="inventory"]:checked').click();
	} else {
		label.hide();
		
		if (index == 1) {
			src = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=4";
		} else if(index == 2) {
			src = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=2&"+
					"personnelIds=${JsParam.personnelIds}&fdStatus=${param.fdStatus}";
		} else if(index == 3) {
			src = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=3";
		} else if(index == 4) {
			src = "${KMSS_Parameter_ContextPath}yjy/zc/yjy_zc_task/yjyZcTaskDetail_list.jsp?fdTaskId=${yjyZcTaskForm.fdId }&inventoryType=5";
		}
		todoInventory.src = src;
	}
	
	var len = $("#label_tab").find("input[type=button]").length;
	for (var i = 0; i < len; i++) {
		$("#label_tab").find("input[type=button]").eq(i).css("border", "1px solid #c2c2c2").css("border-bottom","0px").css("color","black").css("background","");//移除内联样式背景用于悬停变色
	}
	$("#label_tab").find("input[type=button]").eq(index).css("border", "1px solid #2f84fb").css("border-bottom","0px").css("color","white").css("background","#2f84fb");
}

$(function(){
	var i = "${param.label}";
	if (i != '') {
		show($("#label_tab > tbody > tr:nth-child(1) > td > input[type=button]:nth-child("+i+")")[0]);
	}
})
</script>


			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message key="kmAgreementCard.fdForeignTradeImportId" bundle="km-agreement"/>
					<a href="${LUI_ContextPath}/sys/profile/index.jsp#app/ekp/yjy/process" target="_blank">（${ lfn:message('list.manager') }）</a>
				</td>
				<td colspan="3">
					<%-- <xform:text property="value(fdForeignTradeImportId)" showStatus="edit" style="width:95%" htmlElementProperties='placeholder=""' /> --%>
					<xform:dialog propertyId="value(fdForeignTradeImportId)" propertyName="value(fdForeignTradeImportName)" showStatus="edit" style="width:95%;">
						 openDialogCategory('com.landray.kmss.yjy.process.model.YjyProcessTemplate', this);
					</xform:dialog>
				</td>
			</tr>
<script>
function openDialogCategory(modelName, obj) {
	var cfg={
			'modelName':modelName,
			'mulSelect':false,
			'authType':'01',
			'action': function(params){
				if(params){
					const id = params.id.replace(";","");
					$(obj).find("input")[0].value = params.id.replace(";","");
					$(obj).find("input")[1].value = params.name.replace(";","");
					
					//document.forms[0]['fdTemplateName'].value= params.name.replace(";","");
					//document.forms[0]['fdTemplateId'].value= params.id.replace(";","");
					//window.open("${LUI_ContextPath}/gas/overtime/gas_overtime_typtmpl/gasOvertimeTyptmpl.do?method=edit&fdId=${param.fdId}&templateId="+id,"_self");
				}else{
					if (params == undefined){
						$(obj).find("input")[0].value = "";
						$(obj).find("input")[1].value = "";
					}
				}
			},
	};
	seajs.use("lui/dialog.js",function(dialog){
		dialog.category(cfg);
	});
}
</script>