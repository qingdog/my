package com.landray.kmss.km.agreement;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.alimei.sso.api.utils.MD5Util;
import com.google.zxing.common.BitMatrix;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.agreement.model.Attachment;
import com.landray.kmss.km.agreement.model.KmAgreementApply;
import com.landray.kmss.km.agreement.model.KmAgreementChange;
import com.landray.kmss.km.agreement.model.KmAgreementContbody;
import com.landray.kmss.km.agreement.model.KmAgreementLedger;
import com.landray.kmss.km.imissive.model.KmImissiveConfig;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.handover.KmReviewCateTempProvider;
import com.landray.kmss.km.review.model.KmReviewConfigNotify;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewSnService;
import com.landray.kmss.km.supervise.forms.KmSuperviseMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.yjy.zc.model.YjyZcBaseType;

import java.io.BufferedInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
//import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionMapping;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class MyUtil {
	
	public void loadTaskDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!request.getMethod().equals("POST"))
			throw new UnexpectedRequestException();
    	String fdTypeId = request.getParameter("fdTypeId");
    	String fdDutyDeptId = request.getParameter("fdDutyDeptId");
    	String fdTaskTime = request.getParameter("fdTaskTime");
    	
    	HQLInfo hqlInfo = new HQLInfo();
    	//盘点任务批量选择资产时去除未开始盘点和待盘点的资产卡片
    	String whereBlock = "(fdStatus is null or fdStatus!='e') and fdId not in(select fdYzCard.fdId from YjyZcTCardList where (fdStatus=0 or fdStatus=1) and fdYzCard.fdId is not null)";
    	
    	if (StringUtil.isNotNull(fdTypeId)) {
//    		HQLInfo hql = new HQLInfo();
//    		hql.setWhereBlock("yjyZcBaseType.hbmParent.fdId=:fdTypeId");
//    		hql.setParameter("fdTypeId", fdTypeId);
//    		List<?> findList = getYjyZcBaseTypeService().findList(hql);
//    		ArrayList<String> arrayList = new ArrayList<>();
//    		for (Object obj: findList) {
//    			YjyZcBaseType yjyZcBaseType = (YjyZcBaseType)obj;
//    			String fdId = yjyZcBaseType.getFdId();
//    			arrayList.add(fdId);
//    		}
    		ArrayList<String> arrayList = new ArrayList<>();
    		arrayList.add(fdTypeId);
    		List<String> baseTypeAllChildren = this.getBaseTypeAllChildren(arrayList, 10);
    		
    		baseTypeAllChildren.add(fdTypeId);
    		whereBlock += " and fdCategory.fdHierarchyId like :fdTypeId";
    		hqlInfo.setParameter("fdTypeId", "%" + fdTypeId + "%");
    	}
		hqlInfo.setWhereBlock(whereBlock);

		List<YjyZcCard> YjyZcCardList = getYjyZcCardService(request).findList(hqlInfo);
		JSONArray loadCard = loadCard(YjyZcCardList);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(loadCard.toString());
	}
	
	/**
     * 	获取全部资产类别子类的fdId集合
     * @param list 资产类别fdId集合（父类别）
     * @param i 资产类别深度最大值（递减直到0）
     * @return
     * @throws Exception
     */
    public List<String> getBaseTypeAllChildren(List<String> list, int i) throws Exception {
    	HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("yjyZcBaseType.hbmParent.fdId in(:list)");
		hql.setParameter("list", list);
		List<?> findList = getServiceImp().findList(hql);
		
		ArrayList<String> arrayList = new ArrayList<>();
		for (Object obj: findList) {
			YjyZcBaseType yjyZcBaseType = (YjyZcBaseType)obj;
			String fdId = yjyZcBaseType.getFdId();
			arrayList.add(fdId);
		}
		
		if (!findList.isEmpty()) {
			if (i == 0) {
				arrayList.addAll(list);
				return arrayList;
			}
			List<String> yjyZcBaseType = getBaseTypeAllChildren(arrayList, --i);
			arrayList.addAll(yjyZcBaseType);
		}
    	return arrayList;
    }
	
	public void a1() {
//		ArrayList<Object> arrayList2 = new ArrayList<>();
//		
//		for (int i = 0; i < findList.size(); i++) {
//			KmAgreementStage kmAgreementStage = (KmAgreementStage)findList.get(i);
//			
//			HashMap<String, String> hashMap = new HashMap<>();
//			
//			String fdPeriod = kmAgreementStage.getFdPeriod();
//			if("第一期".equals(fdPeriod)) {
//				fdPeriod = "1";
//			}
//			hashMap.put("fd_3afc39518da4a8", fdPeriod);
//			
//			String fdMilepost = kmAgreementStage.getFdMilepost();
//			hashMap.put("fd_3afc3976e510c2", fdMilepost);
//			
//			Date fdEndTime = kmAgreementStage.getFdEndTime();
//			if (fdEndTime != null) {
//				String convertDateToString = DateUtil.convertDateToString(fdEndTime, DateUtil.PATTERN_DATE);
//				hashMap.put("fd_3b42eeeb197384", convertDateToString);
//			}
//			
//			arrayList2.add(hashMap);
//		}
//		formData.put("fd_39c74eb1c6e174", arrayList2);
//
//		if(fdFsscPurch != null) {
//			ArrayList<Object> arrayList = new ArrayList<>();
//			JSONObject jsonObject = new JSONObject();
//	//		[{"docId":"181eadc308ca40687bd852c4e07ae682","subject":"0711","isCreator":"true","checked":true,"openRight":"true","fdModelName":"com.landray.kmss.km.review.model.KmReviewMain"}]
//			jsonObject.put("docId", fdFsscPurch.getFdId());
//			jsonObject.put("subject", fdFsscPurch.getDocSubject());
//			jsonObject.put("isCreator", "true");
//			jsonObject.put("openRight", "true");
//			String name = fdFsscPurch.getClass().getSuperclass().getName();
//			jsonObject.put("fdModelName", name);
//			
//			arrayList.add(jsonObject);
//			
//			IBaseModel findByPrimaryKey = getKmReviewMainService(request).findByPrimaryKey("1845159bf78e945ef6f24e047bba365d");
//			IExtendForm form = null;
//			form = getKmReviewMainService(request).convertModelToForm((IExtendForm) form, findByPrimaryKey, new RequestContext(request));
//			Object object = ((KmReviewMainForm)form).getExtendDataFormInfo().getFormData().get("fd_3b108e49513b94");
//			
//			
//			formData.put("fd_3b108e49513b94", arrayList.toString());
//	//		kmReviewMainForm.getExtendDataFormInfo().setValue("fd_3b108e49513b94", arrayList.toString());
//		}
	}
	
	public void downloadWordTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	KmssMessages messages = new KmssMessages();
    	try {
    		response.setCharacterEncoding("UTF-8");
        	response.setContentType("application/octet-stream");
        	String filename = "Import Template.xls";
//			response.setHeader("Content-Disposition", "attachment;fileName=" + DmDocmgtUtil.encodeFileName(request, filename));
//			String realPath = this.getServletContext().getRealPath("/dm/doclog/common/Import Template.xls");
//			File file = new File(realPath);
//			if(file.exists()) {
//				OutputStream os = response.getOutputStream();
//				FileInputStream in = new FileInputStream(file);
//				int BUFFER_SIZE = 1024;
//				byte[] buf = new byte[BUFFER_SIZE];
//				int len;
//				while ((len = in.read(buf)) != -1) {
//					os.write(buf, 0, len);
//				}
//				in.close();
//			}
    	} catch (Exception e) {
    		messages.addError(e);
    		e.printStackTrace();
    	}
    }
	
	public ActionForward fsscPaymentView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		String fsscPaymentId = request.getParameter("fsscPaymentId");
		if(StringUtil.isNotNull(fsscPaymentId)) {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "kmAgreementApply.docNumber in"
					+ "(select fdContractCode from com.landray.kmss.fssc.ledger.model.FsscLedgerContract where fdId =:id)";
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("id", fsscPaymentId);
			
//			List<?> findList = getServiceImp(request).findList(hqlInfo);
//			if(!findList.isEmpty()) {
//				KmAgreementApply kmAgreementApply = (KmAgreementApply)findList.get(0);
//				String fdId = kmAgreementApply.getFdId();
//				String url = "/km/agreement/km_agreement_apply/kmAgreementApply.do?method=view&fdId=" + fdId;
//				response.sendRedirect(request.getContextPath() + url);
//				return null;
//			} else {
//				throw new RuntimeException("没有找到对应编号的合同！");
//			}
		}
//		return getActionForward("failure", mapping, form, request, response);
		return null;
	}
	
//	hqlInfo.setJoinBlock("left join kmAgreementApply.fdFsscPurch fsscPurch");
//			hqlInfo.setFromBlock("com.landray.kmss.fssc.fee.model.FsscFeeMain fsscFeeMain, "
//					+ "com.landray.kmss.km.agreement.model.KmAgreementApply kmAgreementApply");
//			whereBlock += " and fsscFeeMain.fdId not in kmAgreementApply.fdFsscPurch.fdId";
//			hqlInfo.setWhereBlock(whereBlock);
//	
//	// 期数
//	ArrayList<?> object = (ArrayList<?>)hashMap.get("fd_3afc39518da4a8_record");
//	if(CollectionUtils.isNotEmpty(object)) {
//		HashMap<String, Object> map = (HashMap)object.get(0);
//		Object object2 = map.get("data");
//		if(object2 != null)
//			kmAgreementStage.setFdPeriod(String.valueOf(object2.toString()));
//	}
	
	
//	List<?> findAttListByModel = getSysAttMainService().findAttListByModel(KmAgreementApply.class.getName(),kmAgreementApply.getFdId());
//		Map<?, ?> attForm = getAttForm(findAttListByModel, "fd_3b3315be267c2c");
//		kmAgreementChangeForm.getAttachmentForms().putAll(attForm);
	
	public Map<?, ?> getAttForm(List<?> atts, String settingKey) throws Exception {
		IAttachment att = new Attachment();
		Map<?, ?> attForms = att.getAttachmentForms();
		for (Iterator<?> iter = atts.iterator(); iter.hasNext();) {
			SysAttMain sysAttMain = (SysAttMain) iter.next();
			if(!"fd_39c82ef2ebc6f6".equals(sysAttMain.getFdKey()))
				continue;
			
			SysAttMain copyAtt = (SysAttMain) sysAttMain.clone();
			copyAtt.setFdId(IDGenerator.generateID());
			copyAtt.setFdCreatorId(UserUtil.getUser().getFdId());
			copyAtt.setDocCreateTime(new Date());
			copyAtt.setFdModelId(null);
			copyAtt.setFdFilePath(null);
			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
			sysAttMainService.add(copyAtt);
			
			AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
			attForm.setFdModelId("");
			attForm.setFdModelName(KmAgreementChange.class.getName());
			attForm.setFdKey(settingKey);
			if (!attForm.getAttachments().contains(sysAttMain)) {
				attForm.getAttachments().add(sysAttMain);
			}
			String attids = attForm.getAttachmentIds();
			if (StringUtil.isNull(attids)) {
				attForm.setAttachmentIds(sysAttMain.getFdId());
			} else {
				attForm.setAttachmentIds(sysAttMain.getFdId() + ";" + attids);
			}
		}
		attForms = att.getAttachmentForms();
		Map<Object, Object>newAttForms = new HashMap<Object, Object>();
		newAttForms.put(settingKey, attForms.get(settingKey));

		return newAttForms;
	}
	
//	List<SysAttMain> newAtts = sysAttMainService.findByModelKey(KmRelativeChange.class.getName(),change.getFdId(),"attQualityFile");
//	if (!ArrayUtil.isEmpty(newAtts)) {
//		for (SysAttMain newMain : newAtts) {
//			SysAttMain updatedAttMain = sysAttMainService.clone((SysAttMain) newMain);
//			updatedAttMain.setFdModelId(main.getFdId());
//			updatedAttMain.setFdModelName("com.landray.kmss.km.relative.model.KmRelativeMain");
//			sysAttMainService.add(updatedAttMain);
//		}
//	}
	
	
	// service中
//	ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
//				.getBean("sysAttMainService");
//		
//			sysAttMainService.getSysAttMainDao().update(sysAttMain);
//	
//	List<SysAttMain> oldAtts = getSysAttMainService().findByModelKey(KmAgreementApply.class.getName(), kmAgreementApply.getFdId(), "mainOnline");
//				if (!ArrayUtil.isEmpty(oldAtts)) {
//					SysAttMain newAtt = sysAttMainService.clone(oldAtts.get(0));
//					newAtt.setFdModelId(fsscPaymentMainForm.getFdId());
//					newAtt.setFdModelName(FsscPaymentMain.class.getName());
//					newAtt.setFdKey("attPayment");
//					getKmAgreementApplyService(request).updateAttModel(null, newAtt);
//					
//					AutoHashMap attachmentForms = fsscPaymentMainForm.getAttachmentForms();
//					AttachmentDetailsForm attachmentDetailsForm = (AttachmentDetailsForm) attachmentForms.get("attPayment");
//					attachmentDetailsForm.setAttachmentIds(newAtt.getFdId());
//				}
	
	public void deleteFile(SysAttFile attFile, String suffix) throws Exception {
		String suffixFile = "";
		if (StringUtil.isNotNull(suffix)) {
			suffixFile = "_" + suffix;
		}

		ISysFileLocationProxyService proxy = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
		if (proxy.doesFileExist(attFile.getFdFilePath() + suffixFile)) {
//			this.logger.info("[nozuo-deleteFile]:" + (attFile != null ? attFile.toString() : "为空"));
			proxy.deleteFile(attFile.getFdFilePath() + suffixFile);
		}

	}
	
	public void update() {
//		String fdFilePath = dmDoclogInfoList.getFdFilePath();
//	    if(StringUtil.isNotNull(fdFilePath)) {
//	    	File file = new File(fdFilePath);
//	    	if(file.exists()) {
//	    		FileInputStream fileInputStream = new FileInputStream(file);
//	        	// 增加明细文件
//	        	getSysAttMainCoreInnerService().addAttachment(dmDoclogInfoList, dmDoclogInfoList.FD_ATT_KEY,
//						fileInputStream, file.getName(), "byte", Double.valueOf(fileInputStream.available()), fdFilePath);
//	    	}
//	    }
//	    
//	    AutoHashMap attachmentForms = logInfo.getAttachmentForms();
//		AttachmentDetailsForm attachmentDetailsForm = (AttachmentDetailsForm) attachmentForms.get("attInfo");
//		String fileKey = attachmentDetailsForm.getAttachmentIds();
//		// 项目定制001，删除文件
//		if(StringUtil.isNotNull(fileKey)) {
//			SysAttFile sysAttFile = getSysAttMainCoreInnerService().getFile(fileKey);
//			try {
//				this.deleteFile(sysAttFile, "");
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			getSysAttMainCoreInnerService().deleteAtt(fileKey);
//		}
		
		// copy对象
//		DmDocmgtMain dmDocmgtMain = findList.get(findList.size() - 1);
//		String fdId = dmDocmgtMain.getFdId();
//		String fdRemarks = dmDocmgtMain.getFdRemarks();
//		String fdRemarks01 = dmDocmgtMain.getFdRemarks();
//		
//		BeanUtils.copyProperties(dmDocmgtMain, dmDoclogInfoList);
//		dmDocmgtMain.setFdId(fdId);
//		dmDocmgtMain.setFdRemarks(fdRemarks);
//		dmDocmgtMain.setFdRemarks01(fdRemarks01);
//		getDmDocmgtMainService().update(dmDocmgtMain);
		
		
	    
	}
	
//	<xform:dialog propertyId="fdEqList_Form[!{index}].fdApplyTypeId" propertyName="fdEqList_Form[!{index}].fdApplyTypeName" showStatus="edit" style="width:95%;" >
//	<!-- dialogSimpleCategory('com.landray.kmss.bcp.device.model.BcpDeviceType','fdEqList_Form[!{index}].fdApplyTypeId','fdEqList_Form[!{index}].fdApplyTypeName',false, getEqListByfdApplyType); -->
//		Dialog_TreeList(false,'fdEqList_Form[!{index}].fdApplyTypeId','fdEqList_Form[!{index}].fdApplyTypeName', null, 'bcpDeviceTypeService&parentId=!{value}', 
//			'${lfn:message('bcp-purch:bcpPurchEqList.fdSuppinfo')}', 'bcpDeviceDirecService&categoryId=!{value}',getEqListByfdApplyType, 'bcpDeviceDirecService&categoryId=!{value}&key=!{keyword}&type=search');
//	</xform:dialog>
	
//	@Override
//	public List getDataList(RequestContext requestInfo) throws Exception {
//		String fdParentId = requestInfo.getParameter("parentId");
//		//String filedId = requestInfo.getParameter("filedId");
//		//String filedName = requestInfo.getParameter("filedName");
//		List<BcpDeviceType> subList = null;
//		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
//		HQLInfo hqlInfo=new HQLInfo();
//		String whereBlock=" 1=1 ";
//		if (StringUtil.isNotNull(fdParentId)) {
//			whereBlock="bcpDeviceType.hbmParent.fdId = :fdParentId";
//			hqlInfo.setParameter("fdParentId", fdParentId);
//		}else {
//			whereBlock="bcpDeviceType.hbmParent.fdId is null";
//		}
//		hqlInfo.setWhereBlock(whereBlock);
//		subList = this.findList(hqlInfo);
//		if (!ArrayUtil.isEmpty(subList)) {
//			for (int i = 0; i < subList.size(); i++) {
//				Map<String, String> map = new HashMap<String, String>();
//				BcpDeviceType kdp = subList.get(i);
//				map.put("id", kdp.getFdId());
//				map.put("name", kdp.getFdName());
//				map.put("value", kdp.getFdId());
//				map.put("text", kdp.getFdName());
//				rtnList.add(map);
//			}
//		}
//		return rtnList;
//	}
//	@Override
//	public List getDataList(RequestContext requestInfo) throws Exception {
//		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
//		String categoryId = requestInfo.getParameter("categoryId");
//		HQLInfo hqlInfo = new HQLInfo();
//		// 根据3级类别 設備/服務類別#3 查询
//		hqlInfo.setWhereBlock("fdApplyLevel3.fdId=:fdId");
//		hqlInfo.setParameter("fdId", categoryId);
//		List<BcpDeviceDirec> findList = this.findList(hqlInfo);
//		for (int i = 0; i < findList.size(); i++) {
//			Map<String, String> map = new HashMap<String, String>();
//			BcpDeviceDirec kdp = findList.get(i);
//			BcpDeviceType fdApplyLevel3 = kdp.getFdApplyLevel3();
//			map.put("id", kdp.getFdId());
//			map.put("name", kdp.getDocSubject());
////			map.put("value", kdp.getFdId());
////			map.put("text", kdp.getDocSubject());
//			if(fdApplyLevel3 != null) {
//				map.put("fdApplyLevel3Id", fdApplyLevel3.getFdId());
//				map.put("fdApplyLevel3Name", fdApplyLevel3.getFdName());
//			} else {
//				map.put("fdApplyLevel3Id", "");
//				map.put("fdApplyLevel3Name", "");
//			}
//			
//			rtnList.add(map);
//		}
//		return rtnList;
//	}
	
	
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	
	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if(sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService)SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		
		return sysNotifyMainCoreService;
	}
	
	// 年报已办
//	public String add(IBaseModel modelObj) throws Exception {
//		String add = super.add(modelObj);
//		ZtEnterpriseMain docMain = ztEnterpriseAnnualReport.getDocMain();
//		if(docMain != null) {
//			// 年报已办
//			sysNotifyMainCoreService.getTodoProvider().removePerson(docMain, "annualReport", UserUtil.getUser());
//		}
//		return add;
//	}
	
//	private ISysNotifyMainCoreService sysNotifyMainCoreService;
//	
//	public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
//		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
//	}
	
	//<bean 
      //  id="ztEnterpriseAnnualReportTarget" 
        //class="com.landray.kmss.zt.enterprise.service.spring.ZtEnterpriseAnnualReportServiceImp" 
        //parent="KmssExtendDataTarget">
        //<property name="baseDao">
//            <ref bean="ztEnterpriseAnnualReportDao"/>
//        </property>
//        <property name="sysNotifyMainCoreService">
//			<ref bean="sysNotifyMainCoreService" />
//		</property>
 //   </bean>
//	<bean 
//        id="ztEnterpriseAnnualReportDao" 
//        class="com.landray.kmss.zt.enterprise.dao.hibernate.ZtEnterpriseAnnualReportDaoImp" 
//        parent="KmssAuthBaseDao">
//        <property name="modelName">
//            <value>com.landray.kmss.zt.enterprise.model.ZtEnterpriseAnnualReport</value>
//        </property>
//    </bean>
//    <bean 
//        id="ztEnterpriseAnnualReportTarget" 
//        class="com.landray.kmss.zt.enterprise.service.spring.ZtEnterpriseAnnualReportServiceImp" 
//        parent="KmssExtendDataTarget">
//        <property name="baseDao">
//            <ref bean="ztEnterpriseAnnualReportDao"/>
//        </property>
//        <property name="sysNotifyMainCoreService">
//			<ref bean="sysNotifyMainCoreService" />
//		</property>
//    </bean>
//    <bean 
//        id="ztEnterpriseAnnualReportService" 
//        parent="KmssBaseService">
//        <property name="target">
//            <ref bean="ztEnterpriseAnnualReportTarget"/>
//        </property>
//    </bean>
	
	private void sendNotifyToPerson(BaseModel main, List<SysOrgElement> sysOrgElements) throws Exception {
		List<SysOrgElement> target = new ArrayList<SysOrgElement>();
		target.addAll(sysOrgElements);

		NotifyContext notifyContext = null;
// 		ztEnterpriseMain.annualReport.notify.subject=请于%fdStartSubmitTime%-%fdEndSubmitTime%完成%docSubject%%fdParticularYear%年%fdSubmitType%的报送工作，请您关注！
		notifyContext = sysNotifyMainCoreService.getContext("zt-enterprise:ztEnterpriseMain.annualReport.notify");
		notifyContext.setKey("annualReport");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext.setNotifyType("todo");// 通知方式
		notifyContext.setNotifyTarget(target);// 通知人员
//		notifyContext.setDocCreator(main.getDocMain().getDocCreator());
//		String url = "/zt/enterprise/zt_enterprise_main/ztEnterpriseAnnualReport.do?method=add&docMainId="+main.getDocMain().getFdId();
//		notifyContext.setLink(url);
		
//		notifyContext.setSubject("年报");
		HashMap<String, String> hashMap = new HashMap<String, String>();
//		hashMap.put("docSubject", main.getDocMain().getDocSubject());
		
//		hashMap.put("fdStartSubmitTime", DateUtil.convertDateToString(main.getFdEndSubmitTime(), DateUtil.PATTERN_DATETIME));
//		hashMap.put("fdEndSubmitTime",  DateUtil.convertDateToString(main.getFdEndSubmitTime(), DateUtil.PATTERN_DATETIME));
//		hashMap.put("fdParticularYear", main.getFdParticularYear());
//		hashMap.put("fdSubmitType", main.getFdSubmitType());
//		
//		sysNotifyMainCoreService.send(main.getDocMain(), notifyContext, hashMap);
	}
	
	
	public ActionForward checkCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		
		try {

			form.reset(mapping, request);
			IExtendForm rtnForm = null;
			String code = request.getParameter("code");
//				IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null, true);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdCode=:code");
				hqlInfo.setParameter("code", code);
				List<?> findList = getServiceImp().findList(hqlInfo);
				if(findList.isEmpty()) {
//					return getActionForward("empty", mapping, form, request, response);
				}
				IBaseModel model = (IBaseModel)findList.get(0);
				if (model != null) {
					rtnForm = getServiceImp().convertModelToForm(
							(IExtendForm) form, model, new RequestContext(request));
					UserOperHelper.logFind(model);
					
//					PcoCodeQr pcoCodeQr = (PcoCodeQr)model;
//					String qrCodeMessage = this.getQrCodeMessage(pcoCodeQr);
//					String replace = qrCodeMessage.replace("\n", "\\n");
//					request.setAttribute("qrCodeMessage", replace);
					
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("msg", qrCodeMessage);
//					request.setAttribute("qrCodeMessage", jsonObject);
				}
			if (rtnForm == null)
				throw new NoRecordException();
//			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
			.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return null;//getActionForward("failure", mapping, form, request, response);
		} else {
			return null;//getActionForward("code", mapping, form, request, response);
		}
		
	}
	
	public void loadExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmSuperviseMainForm mainForm = (KmSuperviseMainForm) form;
		FormFile file = mainForm.getFile();
		
		List<JSONArray> jsonArrays = null;
		try {
			jsonArrays = ExcelUtil.loadExcel(file.getInputStream());
			
			JSONArray jsonArray = jsonArrays.get(0);
			if(!jsonArray.isEmpty()) {
//				HashMap<String,List<SzcHrFamilyList>> fdFamilyListMap = new HashMap<String, List<SzcHrFamilyList>>();
//				JSONArray jsonArray0 = jsonArrays.get(1);
//				jsonArray0.remove(0);
//				for(Object object:jsonArray0) {
//					JSONObject jsonObject = (JSONObject) object;
//					Object fdCardNo = jsonObject.get("key0");
//					if(fdCardNo != null && fdCardNo != "") {
//						// 家庭信息
//						SzcHrFamilyList szcHrFamilyList = new SzcHrFamilyList();
//						Object fdRelated = jsonObject.get("key1");
//						if(fdRelated != null && fdRelated != "") {
//							szcHrFamilyList.setFdRelated(fdRelated.toString());
//						}
//						if(fdFamilyListMap.get(fdCardNo) != null) {
//							List<SzcHrFamilyList> list = fdFamilyListMap.get(fdCardNo);
//							list.add(szcHrFamilyList);
//						} else {
//							ArrayList<SzcHrFamilyList> arrayList = new ArrayList<SzcHrFamilyList>();
//							arrayList.add(szcHrFamilyList);
//							fdFamilyListMap.put(fdCardNo.toString(), arrayList);
//						}
//					}
//				}
				
				jsonArray.remove(0);
				for(Object object:jsonArray) {
//					SzcHrTalent szcHrTalent = new SzcHrTalent();
//					
//					JSONObject jsonObject = (JSONObject) object;
//					Object fdCardNo = jsonObject.get("key0");
//					if(fdCardNo != null && fdCardNo != "") {
//						szcHrTalent.setFdCardNo(fdCardNo.toString());
//						
//						List<SzcHrFamilyList> fdFamilyList = fdFamilyListMap.get(fdCardNo);
//						if(!fdFamilyList.isEmpty())
//							szcHrTalent.setFdFamilyList(fdFamilyList);
//						
//					}
//					Object fdName = jsonObject.get("key1");
//					if(fdName != null)
//						szcHrTalent.setFdName(fdName.toString());
//					Object fdSexObj = jsonObject.get("key2");
//					if(fdSexObj != null && fdSexObj != "") {
//						String columnValueByLabel = EnumerationTypeUtil.getColumnValueByLabel("szc_hr_sex_type", fdSexObj.toString());
//						szcHrTalent.setFdSex(columnValueByLabel);
//					}
//					getServiceImp().add(szcHrTalent);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			response.getWriter().print("<script>parent.callback('" + "" + "');</script>");
			return;
		}
		//String columnValueByLabel = EnumerationTypeUtil.getColumnValueByLabel("lsad_contract_Reminder_status", ResourceUtil.getString("lsad-comtract:enums.Reminder_status.1"));
        //lsadContractTodo.setFdReminderStatus(columnValueByLabel);
		String msg = ResourceUtil.getString("szc-hr:detail.transport.result.success");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("<script>parent.callback('" + msg + "');</script>");
	}
	
	public String getFdKeyCode(KmReviewMain yjyZcCard) throws Exception {
    	String code = yjyZcCard.getFdId();
    	if(StringUtil.isNull(code)) {
//    		YjyZcBaseType fdCategory = yjyZcCard.getFdCategory();
    		SysOrgPerson fdCategory = yjyZcCard.getDocCreator();
    		if (fdCategory != null) {
    			String prefix = "";
    			SysOrgPerson fdParent = (SysOrgPerson) fdCategory.getFdParent();
    			if(fdParent != null) {
    				String fdCode = fdParent.getFdNo();
    				if(StringUtil.isNotNull(fdCode))
    					prefix += fdCode;
    			}
    			String fdCode = fdCategory.getFdNo();
    			if (StringUtil.isNotNull(fdCode)) {
    				prefix += fdCode;
    			}
    			Object fdFlowNo = getFdFlowNo(fdCategory.getFdId(), 0);
    			if (fdFlowNo == null)
    				fdFlowNo = 1;
    			String format = String.format("%0" + 6 + "d", fdFlowNo);
    			String fdKeyCode = prefix + format;
    			//去重
    			Object maxKeyCode = getKeyCode(fdCategory.getFdId(), fdKeyCode, prefix, 0);
    			if(maxKeyCode != null)
    				fdKeyCode = maxKeyCode.toString();
    			return fdKeyCode;
    		}
    	}
    	return "";
    }
	
	//乐观锁递归查询流水号
    public Object getFdFlowNo(String fdBaseTypeId, int index) throws HibernateException, SQLException, Exception {
		Object flowNo = 0;
		String query = "select fd_flow_no from yjy_zc_base_type where fd_id=?";
		getServiceImp().getBaseDao().getHibernateSession().connection().commit();
		List<Object> list = getServiceImp().getBaseDao().getHibernateSession().createSQLQuery(query)
			.setString(0, fdBaseTypeId).list();
		if (list.size() > 0) {
			flowNo = list.get(0);
			if (flowNo != null) {
				String update = "update yjy_zc_base_type set fd_flow_no=fd_flow_no+1 where fd_id=? and fd_flow_no="
						+ flowNo;
				int executeUpdate = getServiceImp().getBaseDao().getHibernateSession().createSQLQuery(update)
						.setParameter(0, fdBaseTypeId).executeUpdate();
				if (executeUpdate == 0 && index < 10)
					flowNo = getFdFlowNo(fdBaseTypeId, ++index);
			}
		}
		return flowNo;
	}
    
    // 最大keycode
    public Object getKeyCode(String fdCategoryId, String fdKeyCode, String prefix, int index) throws Exception {
		// TODO Auto-generated method stub
		if(fdKeyCode.length()>0) {
	    	String query = "select fd_code from yjy_zc_card where fd_category_id=? and fd_code = '"+fdKeyCode+"' ";
	    	getServiceImp().getBaseDao().getHibernateSession().connection().commit();
			List<String> list = getServiceImp().getBaseDao().getHibernateSession().createSQLQuery(query).setString(0, fdCategoryId).list();
			if(list.size() > 0) {
				String string = list.get(0);
				if(StringUtil.isNotNull(string)) {
					Object fdFlowNo = getFdFlowNo(fdCategoryId, 0);
					if (fdFlowNo == null)
	    				fdFlowNo = 1;
	    			String format = String.format("%0" + 6 + "d", fdFlowNo);
	    			fdKeyCode = prefix + format;
	    			
	    			if (index < 100) {
	    				Object maxKeyCode = getKeyCode(fdCategoryId, fdKeyCode, prefix, ++index);
	    				if(maxKeyCode != null)
	    					fdKeyCode = maxKeyCode.toString();
					}
				}
			}
		}
		return fdKeyCode;
	}
	
	
	/**
	 * 获取澳巴令牌
	 * @param ngOperateConfig
	 * @return
	 * @throws JSONException 
	 */
	public String getAoBaToken(SysCommonSensitiveConfig ngOperateConfig) throws JSONException {
		String fdAoBaApi = ngOperateConfig.getValue("fdAoBaApi");
		String fdAoBaUsername = ngOperateConfig.getValue("fdAoBaUsername");
		String fdAoBaPassword = ngOperateConfig.getValue("fdAoBaPassword");
		String fdAoBaLoginMark = ngOperateConfig.getValue("fdAoBaLoginMark");
		if(StringUtil.isNotNull(fdAoBaApi) && StringUtil.isNotNull(fdAoBaUsername) && 
				StringUtil.isNotNull(fdAoBaPassword) && StringUtil.isNotNull(fdAoBaLoginMark)) {
			fdAoBaPassword = com.alibaba.alimei.sso.api.utils.MD5Util.getMD5Str(fdAoBaPassword);
			
			PostMethod postMethod = new PostMethod(fdAoBaApi + "/tcm/auth/token");
			// 设置为默认的重试策略
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			postMethod.addParameter("token", "");
			// 同一个客户端IP地址只同时存在一个有效令牌
			postMethod.addParameter("loginMark", fdAoBaLoginMark);
			JSONObject dataJson = new JSONObject();
			dataJson.put("username", fdAoBaUsername);
			dataJson.put("password", fdAoBaPassword);
			postMethod.addParameter("data", dataJson.toString());
			
			HttpClient httpClient = new HttpClient();
			try {
				// HttpClient设置本地代理ip地址103.97.200.71
				httpClient.getHostConfiguration().setProxy("127.0.0.1", 10809);
				int executeMethod = httpClient.executeMethod(postMethod);
				if(executeMethod == 200) {
					String responseBodyAsString = postMethod.getResponseBodyAsString();
					String stringJson = new String(responseBodyAsString.getBytes("iso-8859-1"), "UTF-8");
					net.sf.json.JSONObject resultJson = net.sf.json.JSONObject.fromObject(stringJson);
					Object code = resultJson.get("code");
					if(code != null) {
						if("200".equals(code.toString())) {
							Object dataObject = resultJson.get("data");
							if(dataObject != null) {
								net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(dataObject);
								Object token = data.get("token");
								if(token != null)
									return token.toString();
							}
						} else {
							Object info = resultJson.get("info");
							if(info != null) {
								throw new RuntimeException(info.toString());
							}
						}
					}
				}
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				postMethod.releaseConnection();
			}
		}
		return null;
	}
	/**
	 * 分页获取现金数据列表接口
	 * @param ngOperateConfig
	 * @param params
	 * @return
	 * @throws JSONException 
	 */
	public Integer getAoBaTotalPassengers(SysCommonSensitiveConfig ngOperateConfig, String[] params) throws JSONException {
		String fdAoBaApi = ngOperateConfig.getValue("fdAoBaApi");
		String fdAoBaUsername = ngOperateConfig.getValue("fdAoBaUsername");
		String fdAoBaPassword = ngOperateConfig.getValue("fdAoBaPassword");
		if(StringUtil.isNotNull(fdAoBaApi) && StringUtil.isNotNull(fdAoBaUsername) && StringUtil.isNotNull(fdAoBaPassword) && 
				StringUtil.isNotNull(params[0]) && StringUtil.isNotNull(params[1]) && params[2] != null && params[3] != null && 
				params[4] != null && params[5] != null && StringUtil.isNotNull(params[6]) && StringUtil.isNotNull(params[7]) && 
				StringUtil.isNotNull(params[8]) && StringUtil.isNotNull(params[9])) {
			fdAoBaPassword = com.alibaba.alimei.sso.api.utils.MD5Util.getMD5Str(fdAoBaPassword);
			JSONObject dataJsonObject = new JSONObject();
			JSONObject queryJson = new JSONObject();
			queryJson.put("opDate", params[2]);
			queryJson.put("routeName", params[3]);
			queryJson.put("plateNo", params[4]);
			queryJson.put("carNo", params[5]);
			// queryJson的参数类型为json字符串
			dataJsonObject.put("queryJson", queryJson.toString());
			JSONObject pagination = new JSONObject();
			pagination.put("rows", params[6]);
			pagination.put("page", params[7]);
			pagination.put("sidx", params[8]);
			pagination.put("sord", params[9]);
			// pagination的参数类型为json
			dataJsonObject.put("pagination", pagination);
			GetMethod getMethod = null;
			try {
				String dataEncoder = URLEncoder.encode(dataJsonObject.toString() ,"UTF-8");
				String url = fdAoBaApi + "/tcm/cash/pagelist?token=" + params[0] + "&loginMark=" + params[1] + "&data=" + dataEncoder;
				getMethod = new GetMethod(url);
				// 设置为默认的重试策略
				getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
				
				HttpClient httpClient = new HttpClient();
				// HttpClient设置本地代理ip地址103.97.200.71
				httpClient.getHostConfiguration().setProxy("127.0.0.1", 10809);
				int executeMethod = httpClient.executeMethod(getMethod);
				if(executeMethod == 200) {
					String responseBodyAsString = getMethod.getResponseBodyAsString();
					String stringJson = new String(responseBodyAsString.getBytes("iso-8859-1"), "UTF-8");
					net.sf.json.JSONObject resultJson = net.sf.json.JSONObject.fromObject(stringJson);
					Object code = resultJson.get("code");
					if(code != null) {
						if("200".equals(code.toString())) {
							Object dataObject = resultJson.get("data");
							if(dataObject != null) {
								net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(dataObject);
								Object rowsObject = data.get("rows");
								if(rowsObject != null) {
									// 总现金 = 澳门币（单位元）+ 港币（单位元）+ 人民币（单位元）
									int totalCash = 0;
									JSONArray rows = JSONArray.fromObject(rowsObject);
									for(Object obj:rows) {
										if(obj instanceof JSONObject) {
											JSONObject jsonObject = (JSONObject)obj;
											
											Object mop_amount = jsonObject.get("mop_amount");
											Object hk_amount = jsonObject.get("hk_amount");
											Object rmb_amount = jsonObject.get("rmb_amount");
											if(mop_amount != null) {
												if(org.apache.commons.lang.math.NumberUtils.isNumber(mop_amount.toString())) {
													Integer valueOf = Integer.valueOf(mop_amount.toString());
													totalCash += valueOf;
												}
											}
											if(hk_amount != null) {
												if(org.apache.commons.lang.math.NumberUtils.isNumber(hk_amount.toString())) {
													Integer valueOf = Integer.valueOf(hk_amount.toString());
													totalCash += valueOf;
												}
											}
											if(rmb_amount != null) {
												if(org.apache.commons.lang.math.NumberUtils.isNumber(rmb_amount.toString())) {
													Integer valueOf = Integer.valueOf(rmb_amount.toString());
													totalCash += valueOf;
												}
											}
										}
									}
									Object recordsObject = data.get("records");
									if(recordsObject != null) {
										if(org.apache.commons.lang.math.NumberUtils.isNumber(recordsObject.toString())) {
											Integer records = Integer.valueOf(recordsObject.toString());
											// 乘车人次总数四舍五入不保留小数
											int intValue = new BigDecimal(totalCash).divide(new BigDecimal(6), 0, BigDecimal.ROUND_HALF_UP).intValue();
											// 乘车人次总数 = 明细数的count值 + 现金总金额 / 6
											int totalPassengers = records + intValue;
											return totalPassengers;
										}
									}
								}
							}
						} else {
							Object info = resultJson.get("info");
							if(info != null) {
								throw new RuntimeException(info.toString());
							}
						}
							
					}
				}
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(getMethod != null)
					getMethod.releaseConnection();
			}
		}
		return null;
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-view", true, getClass());
//        KmssMessages messages = new KmssMessages();
//        try {
//            loadActionForm(mapping, form, request, response);
//        } catch (Exception e) {
//            messages.addError(e);
//        }
//        if (messages.hasError()) {
//            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
//            return mapping.findForward("failure");
//        }
//        String fdId = request.getParameter("fdId");
//        BcpPurchMainForm bcpPurchMainForm = (BcpPurchMainForm) form;
//        BcpPurchMain bcpPurchMain = (BcpPurchMain) getServiceImp(request).findByPrimaryKey(fdId);
//        Boolean enable = ((ISysPrintMainCoreService) getBean("sysPrintMainCoreService")).isEnablePrintTemplate(bcpPurchMain.getDocTemplate(), null, request);
//        ((ISysPrintMainCoreService) getBean("sysPrintMainCoreService")).initPrintData(bcpPurchMain, bcpPurchMainForm, request);
//        String printPage = request.getParameter("printPage");
//        if (StringUtil.isNotNull(printPage)) {
//            return mapping.findForward(printPage);
//        }
//        if (enable) {
//            return mapping.findForward("sysprint");
//        } else {
//            KmssMessages noSelect = new KmssMessages();
//            noSelect.addMsg(new KmssMessage("bcp-purch:printMechanism.page.noSelect"));
//            KmssReturnPage.getInstance(request).addMessages(noSelect).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
//            return getActionForward("failure", mapping, form, request, response);
//        }
        
//        String fdIds = request.getParameter("fdId");
//		String[] fdId = fdIds != null ? fdIds.split(",") : null;
//		if (fdId != null) {
//			List<BcpPurchMainForm> bcpPurchMainFormList = new ArrayList<BcpPurchMainForm>();
//
//			for (int index = 0; index < fdId.length; ++index) {
//				String id = fdId[index];
//				BcpPurchMain bcpPurchMain = (BcpPurchMain) this.getServiceImp(request).findByPrimaryKey(id, BcpPurchMain.class, true);
//				BcpPurchMainForm bcpPurchMainForm = null;
//				if (bcpPurchMain != null) {
//					bcpPurchMainForm = (BcpPurchMainForm) this.getServiceImp(request)
//							.convertModelToForm(bcpPurchMainForm, bcpPurchMain, new RequestContext(request));
//					bcpPurchMainFormList.add(bcpPurchMainForm);
//				}
//			}
//			request.setAttribute("isPrintBatch", "false");
//			request.setAttribute("bcpPurchMainFormList", bcpPurchMainFormList);
//		}
//		return mapping.findForward("sysprint");
        TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			//loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return null;//getActionForward("failure", mapping, form, request, response);
		} else {
			return null;//getActionForward("sysprint", mapping, form, request, response);
		}
    }
	
	private ISysOrgElementService sysOrgElementService;

    public ISysOrgElementService getSysOrgElementService(HttpServletRequest request) {
        if (sysOrgElementService == null) {
//        	sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }
	
	public static void main(String[] args) {
		
//		String encode = new String(java.util.Base64.getEncoder().encode(xml.getBytes("UTF-8")), "UTF-8");
//		byte[] buffer = java.util.Base64.getDecoder().decode(fdBase64Binary);


		// 相差毫秒数
		Date date1 = new Date();
		Date date2 = new Date();
		
		long differTime = date2.getTime() - date1.getTime();
		// 1天毫秒数
		long dayMs = 1000 * 24 * 60 * 60;
		// 按24小时计算天数
		long day = differTime / dayMs;
		
		// 1小时毫秒数
        long hourMs = 1000 * 60 * 60;
        long hour = differTime % dayMs / hourMs;
        
		BigDecimal divide = new BigDecimal(hour).divide(new BigDecimal(24), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal bigDecimal = divide.add(new BigDecimal(day));
			
		
		// 在数据量只有10的情况下，hashMap的插入耗时是arrayList尾插的两倍，linkedList的五分之一，查询匹配是arrayList或linkedList耗时的一半
    	// 在数据量只有100的情况下，查询匹配10次仍选用hashMap
    	// 在数据量只有1000的情况下，hashMap的插入耗时是arrayList尾插的4倍，查询匹配10次仍选用hashMap
    	// 在数据量高达一万的情况下，hashMap的插入耗时是arrayList尾插的10倍，查询匹配耗时是arrayList尾插的10分之一.查询匹配100次仍选用hashMap
    	// 在数据量高达一万的情况下，hashMap的插入耗时是hashSet的2倍，查找速度相当（少量数据时set和map没有区别）8基本类型或字符串哈希
    	int number = 10;
    	ArrayList<Integer> arrayList = new ArrayList<Integer>();
    	Integer[] integers = new Integer[number];
    	HashMap<Integer,Integer> hashMap = new HashMap<Integer, Integer>();
		
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
    	
		long stime;
        long etime;
		
        // 开始时间
        long stime1 = System.currentTimeMillis();
        for(int i=0;i<number;i++) {
        	arrayList.add(i);
		}
        // 结束时间
        long etime1 = System.currentTimeMillis();
        // 计算执行时间
        System.out.println("ArrayList用时：\t" + (etime1 - stime1)+" ms");
        
        stime = System.nanoTime();
        for(int i=0;i<number;i++) {
			integers[i] = i;
		}
        etime = System.nanoTime();
        System.out.println("固定长度数组：\t" + (etime - stime)+" ns");
        
        
        stime = System.nanoTime();
        for(int i=0;i<number;i++) {
        	arrayList.add(i);
		}
        etime = System.nanoTime();
        System.out.println("ArrayList插入：\t" + (etime - stime)+" ns");
        
        
        Vector<Integer> vector = new  Vector<Integer>();
        
        stime = System.nanoTime();
        for(int i=0;i<number;i++) {
        	vector.add(i);
		}
        etime = System.nanoTime();
        System.out.println("vector插入：\t" + (etime - stime)+" ns");
        
        stime = System.nanoTime();
        for(int i=0;i<number;i++) {
			linkedList.add(i);
		}
        etime = System.nanoTime();
        System.out.println("linkedList数组：\t" + (etime - stime)+" ns");
        
        stime = System.nanoTime();
        for(int i=0;i<number;i++) {
			hashMap.put(i, i);
		}
        etime = System.nanoTime();
        System.out.println("hashMap插入：\t" + (etime - stime)+" ns");
        
        HashSet<Integer> hashSet = new HashSet<>();
        stime = System.nanoTime();
        for(int i=0;i<number;i++) {
        	hashSet.add(i);
		}
        etime = System.nanoTime();
        System.out.println("hashSet插入：\t" + (etime - stime)+" ns");
        
        
        System.out.println();
        
        stime = System.nanoTime();
        if(hashMap.containsKey(number-1)) {
		}
        etime = System.nanoTime();
        System.out.println("hashMap查询：\t" + (etime - stime)+" ns");
        
        stime = System.nanoTime();
        if(hashMap.containsValue(number-1)) {
		}
        etime = System.nanoTime();
        System.out.println("hash value查询：\t" + (etime - stime)+" ns");
        
        stime = System.nanoTime();
        if(hashSet.contains(number-1)) {
        }
        etime = System.nanoTime();
        System.out.println("hashSet查询：\t" + (etime - stime)+" ns");
        
        
        
        stime = System.nanoTime();
        for(int i=0;i<integers.length;i++) {
			if(integers[i] == number-1) {
				break;
			}
		}
        etime = System.nanoTime();
        System.out.println("固定数组查询：\t" + (etime - stime)+" ns");
        
        stime = System.nanoTime();
        boolean contains = arrayList.contains(number-1);
		if(contains) {
		}
        etime = System.nanoTime();
        System.out.println("ArrayList查询：\t" + (etime - stime)+" ns");
        
        stime = System.nanoTime();
        boolean contains2 = linkedList.contains(number-1);
		if(contains2) {
		}
        etime = System.nanoTime();
        System.out.println("linkedList查询：\t" + (etime - stime)+" ns");
        
        
        stime = System.nanoTime();
        boolean contains3 = vector.contains(number-1);
        if(contains3) {
        	
        }
        etime = System.nanoTime();
        System.out.println("vector查询：\t" + (etime - stime)+" ns");
        
        Hashtable<Integer, ?> hashtable = new Hashtable<>();
		
//		for(BcpPurchSList bcpPurchSList:fdSList) {
//			String fdRequireNo = bcpPurchSList.getFdRequireNo();
//			int length = fdRequireNo.length();
//			if(fdRequireNo != null && length == requireNoLen) {
//				String substring = fdRequireNo.substring(length - 2, length);
//				boolean isNumber = org.apache.commons.lang.math.NumberUtils.isNumber(substring);
//				if(isNumber) {
//					Integer valueOf = Integer.valueOf(substring);
//					if(valueOf > number) {
//						number = valueOf;
//					}
//				}
//			}
//		}
//		
//		for(BcpPurchSList bcpPurchSList:fdSList) {
//			String fdRequireNo = bcpPurchSList.getFdRequireNo();
//			if(StringUtil.isNull(fdRequireNo)) {
//				String format = String.format("%02d", ++number);
//				bcpPurchSList.setFdRequireNo(prefix + format);
//			}
//		}
		
		
		HQLInfo hqlInfo = new HQLInfo();
//		hqlInfo.setWhereBlock("sysOrgElement.fdId in(:ids)");
//    	hqlInfo.setParameter("ids", Arrays.asList(ids.split(";")));
		
		
    	// FreeMark模板
    	HashMap<String, String> footerMap = new HashMap<String, String>();
//		if(!attFooter.isEmpty()) {
//			String pathname3 = kmssConfigString + "/" + attFooter.get(0);
//			footerFile = new File(pathname3);
//			footerMap.put("dis", dmDocmgtMain.getFdSquad());
//		}
//		FreeMarkUtils.createDocx(dataMap, docxFile, xmlFile, response.getOutputStream(), footerMap, footerFile);

		// 公式定义器解析器
//		FormulaParser formulaParserDetail = FormulaParser.getInstance(main);
//		Object sValue = formulaParserDetail.parseValueScript("$" + fdParamColumnName + "$");
//		String tmpStr = "";
//		if (sValue != null) {
//			if (sValue instanceof Date) {
//				// zhuanhuan
//				Date tmpVal = (Date) sValue;
//				tmpStr = DateUtil.convertDateToString(tmpVal, "yyyy-MM-dd");
//			} else if (sValue instanceof String) {
//				// zhuanghuan
//				if("fdComments".equals(fdParamColumnName)) {
//					sValue = EnumerationTypeUtil.getColumnEnumsLabel("dm_docmgt_comments", sValue.toString());
//				}
//				if("null".equals(sValue)){
//					sValue = "";
//				}
//				tmpStr = String.valueOf(sValue);
//			}
//		}
	}
	
	/**
	 * 生成QR方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    public void generateQrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	String tempId = request.getParameter("tempId");
        	String tempName = request.getParameter("tempName");
        	String scheme = request.getScheme();
        	String serverName = request.getServerName();
        	int serverPort = request.getServerPort();
        	String contextPath = request.getContextPath();
			KmReviewConfigNotify config = new KmReviewConfigNotify();
//			String qrcodeUrl = config.getValue("qrcode.url");
			String qrcodeUrl = config.getJSPUrl();
			if(StringUtil.isNull(qrcodeUrl)){
				qrcodeUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			}
			if(StringUtil.isNull(qrcodeUrl)){
				qrcodeUrl = scheme + "://" + serverName + ":" + serverPort + contextPath;
			}
			String url =  qrcodeUrl + "/szc/hr/szc_hr_talent/szcHrTalentApply.do?method=add&tempId="+tempId;
			response.setContentType(FileMimeTypeUtil.getContentType(tempName+".png"));
			response.setHeader("Content-Disposition",
					"attachment;filename*=UTF-8''" + java.net.URLEncoder.encode(tempName,"UTF-8"));
        	this.zxingQrCode(url, response.getOutputStream());
        } catch (Exception e) {
        }
    }
    public void zxingQrCode(String url, OutputStream os) throws Exception {
        int width = 280; 
        int height = 280; 
        com.google.zxing.qrcode.QRCodeWriter qrCodeWriter = new com.google.zxing.qrcode.QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(url, com.google.zxing.BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = this.toBufferedImage(bitMatrix);
        ImageIO.write(bufferedImage, "png", os);
    }
    public BufferedImage toBufferedImage(BitMatrix matrix) {
    	final int BLACK = 0xFF000000;
        final int WHITE = 0x00FFFFFF;
        
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    
    private IKmReviewMainService kmReviewMainService;
    public IBaseService getServiceImp() {
        if (kmReviewMainService == null) {
        	kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
        }
        return kmReviewMainService;
    }
    
    public void export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
    		throws Exception {
        String fdId = request.getParameter("fdId");
//        if (StringUtil.isNotNull(fdId)) {
//        	ProExamSumPerson proExamSumPerson = (ProExamSumPerson)getServiceImp(request).findByPrimaryKey(fdId);
//        	List<ProExamSumPlist> fdSumPlist = proExamSumPerson.getFdSumPlist();
//        	
//        	// 集合倒序
//        	Collections.sort(fdSumPlist, new Comparator<ProExamSumPlist>() {
//        		@Override
//        		public int compare(ProExamSumPlist o1, ProExamSumPlist o2) {
//        			// TODO Auto-generated method stub
//        			Double fdSelfTotalScore = o1.getFdSelfTotalScore();
//        			if (fdSelfTotalScore == null)
//        				fdSelfTotalScore = 0d;
//        			Double fdSelfTotalScore2 = o2.getFdSelfTotalScore();
//        			if (fdSelfTotalScore2 == null)
//        				fdSelfTotalScore2 = 0d;
//        			return (int) (fdSelfTotalScore2 - fdSelfTotalScore);
//        		}
//        	});
//        	
//        	// 按部门分成多个集合
//        	HashMap<String, List<ProExamSumPlist>> hashMap = new HashMap<>();
//        	for (int i = 0; i < fdSumPlist.size(); i++) {
//        		ProExamSumPlist proExamSumPlist = fdSumPlist.get(i);
//        		SysOrgElement fdDept = proExamSumPlist.getFdDept();
//        		if (fdDept != null) {
//        			String id = fdDept.getFdId();
//        			List<ProExamSumPlist> list = hashMap.get(fdDept.getFdId());
//            		if (list == null) {
//            			list = new ArrayList<ProExamSumPlist>();
//            			hashMap.put(id, list);
//            		}
//            		list.add(proExamSumPlist);
//        		} else {
//        			List<ProExamSumPlist> list = hashMap.get("null");
//            		if (list == null) {
//            			list = new ArrayList<ProExamSumPlist>();
//            			hashMap.put("null", list);
//            		}
//            		list.add(proExamSumPlist);
//        		}
//            }
//        	
//        	String[] columnArr = new String[] {"职级", "部门/企业", "姓名", "组织绩效(30%)", "个人业绩(15%)", "上级(50%)", 
//        			"下级(5%)", "综合总分", "个人总分,", "组织绩效排序", "个人绩效排序", "绩效等级"};
//        	
//        	ArrayList<Object[][]> arrayList = new ArrayList<>();
//        	ArrayList<String> array = new ArrayList<>();
//        	
//        	Set<String> keySet = hashMap.keySet();
//        	for (String key: keySet) {
//        		List<ProExamSumPlist> list = hashMap.get(key);
//        		
//        		Object[][] dataArray = new Object[list.size() + 1][columnArr.length];
//    			dataArray[0] = columnArr;
//    			
//    			String tableName = null;
//        		for (int i = 0; i < list.size(); i++) {
//        			Object[] dataArr = new Object[columnArr.length]; //数据列表
//        			ProExamSumPlist proExamSumPlist = list.get(i);
//        			
//        			int index = -1;
//    				dataArr[++index] = proExamSumOlist.getDocDept() == null ? "" : proExamSumOlist.getDocDept().getFdName();
//    				tableName = dataArr[index].toString();
//    				dataArr[++index] = proExamSumOlist.getFdDeptDuty() == null ? null : proExamSumOlist.getFdDeptDuty().getFdName();
//					dataArr[++index] = proExamSumOlist.getFdTotalScore();
//					dataArr[++index] = proExamSumOlist.getFdSuggestLevel();
//					dataArr[++index] = proExamSumOlist.getFdExamId();
//    				
//    				dataArray[i + 1] = dataArr;
//        		}
//        		array.add(tableName);
//        		arrayList.add(dataArray);
//        	}
//        	
//        	response.setCharacterEncoding("UTF-8");
//        	response.setContentType("application/octet-stream");
//            String filename = "个人汇总表";//.getBytes("GBK"), "ISO-8859-1");
//            String docSubject = proExamSumPerson.getDocSubject();
//    		if (StringUtil.isNotNull(docSubject)) {
//    			filename += "-" + docSubject;
//    		}
//            String userAgent = request.getHeader("User-Agent");
//            if (userAgent.matches(".*MSIE.*|.*rv.*|.*Trident.*|.*Edge.*")) {
//    			filename = URLEncoder.encode(filename, "UTF-8");
//    		} else if (userAgent.contains("Mozilla")) {
//    			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
//    		} else {
//    			filename = URLEncoder.encode(filename, "UTF-8");
//    		}
//            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
//            
//        	com.landray.kmss.pro.exam.util.ExcelUtil.export(arrayList, array, response.getOutputStream());
//        }
    }
		
}

class ExcelUtil {
	
	/**
	 * 	导出单个sheet的excel
	 * @param dataList 数据表格
	 * @param tableName 表格名
	 * @param outputStream 输出
	 * @throws Exception
	 */
	public static void export(Object[][] dataArray, String tableName, OutputStream outputStream) throws Exception {
		XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
		if (tableName == null) {
			tableName = "Sheet1";
		}
		XSSFSheet sheet = xSSFWorkbook.createSheet(tableName);
		ExcelUtil.dataWrite(dataArray, xSSFWorkbook, sheet);
		xSSFWorkbook.write(outputStream);
	}
	
	/**
	 * 	导出多个sheet的excel
	 * @param dataList
	 * @param tableNames
	 * @param outputStream
	 * @throws Exception 
	 */
	public static void export(List<Object[][]> dataList, List<String> tableNames, OutputStream outputStream) throws Exception {
		// 创建工作簿对象
		XSSFWorkbook xSSFWorkbook = new XSSFWorkbook();
		for (int i = 0; i < dataList.size(); i++) {
			String tableName = tableNames.get(i);
			if (tableName == null || tableName.length() == 0) {
				int j = i + 1;
				tableName = "Sheet" + j;
			}
			// 创建工作表
			XSSFSheet sheet = xSSFWorkbook.createSheet(tableName);
			Object[][] dataArray = dataList.get(i);
			ExcelUtil.dataWrite(dataArray, xSSFWorkbook, sheet);
		}
		xSSFWorkbook.write(outputStream);
	}
	
	/**
	 * 	首行标题多行数据写入sheet
	 * @param dataArray 数据列表
	 * @param workbook 工作簿
	 * @param sheet 其中一个表格
	 * @throws Exception
	 */
	public static void dataWrite(Object[][] dataArray, XSSFWorkbook xSSFWorkbook, XSSFSheet xSSFSheet) throws Exception {
		/* try { */
		// 标题行单元格样式
		XSSFCellStyle titleXSSFCellStyle = ExcelUtil.getTitleXSSFCellStyle(xSSFWorkbook);
		// 数据单元格样式对象
		XSSFCellStyle dataXSSCellStyle = ExcelUtil.getDataXSSCellStyle(xSSFWorkbook);
		
		// 靠左顶部
		XSSFCellStyle leftTopXSSCellStyle = ExcelUtil.getLeftTopXSSCellStyle(xSSFWorkbook);
		
		// 定制红色样式
		XSSFCellStyle redStyle = ExcelUtil.getDataXSSCellStyle(xSSFWorkbook);
		XSSFFont font = redStyle.getFont();
		font.setColor(HSSFColor.RED.index);
		
		// 首行标题列数
		int columnTotal;
		// 总列数
		if (dataArray.length == 0) {
			columnTotal = 0;
		} else {
			columnTotal = dataArray[0].length;
		}
		
		HashMap<Integer, Integer> hashMap = new HashMap<>();
		for (int colNum = 0; colNum < columnTotal; colNum++) {
			// 初始列宽9个字号为10的汉字
			hashMap.put(colNum, 18);
		}
		
		XSSFCellStyle textXSSCellStyle = ExcelUtil.getDataXSSCellStyle(xSSFWorkbook);
		// 单元格数据格式为文本
		XSSFDataFormat createDataFormat = xSSFWorkbook.createDataFormat();
		short textFormat = createDataFormat.getFormat("TEXT");
		textXSSCellStyle.setDataFormat(textFormat);
//		short generalFormat = createDataFormat.getFormat("General");
//		textXSSCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		for (int i = 0; i < dataArray.length; i++) {
			// 创建行对象
			XSSFRow xSSFRow = xSSFSheet.createRow(i);
			// 给每一行设置高度
			if (i == 0)
				xSSFRow.setHeight((short) (20 * 29));
			else
				xSSFRow.setHeight((short) (20 * 26));
			
//			Object[] objects = dataList.get(i);
			Object[] dataArr = dataArray[i];
			// 将数据设置到sheet对应的单元格中
			for (int j = 0; j < dataArr.length; j++) {
				// 创建字符串类型单元格
				XSSFCell xSSFCell = xSSFRow.createCell(j);
				
				Object obj = dataArr[j];
				if (obj == null) {
					// 数据对象为null则置为空字符串
					xSSFCell.setCellValue("");
					if (i == 0) {
						xSSFCell.setCellStyle(titleXSSFCellStyle);
					} else {
						xSSFCell.setCellStyle(dataXSSCellStyle);
					}
				} else {
					// 取单元格数据操作系统默认编码格式的字符长度
					String value = obj.toString();
					int length = value.getBytes().length;
					
					if (i == 0) {
						// 表格首行单元格设置样式
						xSSFCell.setCellStyle(titleXSSFCellStyle);
						
						if (obj instanceof String) {
							xSSFCell.setCellValue(value);
						} else if (obj instanceof Number) {
							Number number = (Number) obj;
							xSSFCell.setCellValue(number.doubleValue());
						} else {
							xSSFCell.setCellValue(value);
						}
					} else {
						// 设置单元格的值
						if (obj instanceof String) {
							xSSFCell.setCellValue(value);
							// 表格数据单元格设置样式
							xSSFCell.setCellStyle(textXSSCellStyle);
						} else if (obj instanceof Number) {
							Number number = (Number) obj;
							xSSFCell.setCellValue(number.doubleValue());
							
							xSSFCell.setCellStyle(dataXSSCellStyle);
						} else {
							xSSFCell.setCellValue(value);
							
							xSSFCell.setCellStyle(dataXSSCellStyle);
						}
						
						// 设置ID长度32字符为最长宽度，超出换行靠上
						if (length > 32) {
							length = 33;
							xSSFCell.setCellStyle(leftTopXSSCellStyle);
						}
						
						// 定制第6列到10列单元格内容为×则更改样式
						if (j > 5 && j < 11 && "×".equals(value)) {
							xSSFCell.setCellStyle(redStyle);
						}
					}
					
					// 动态设置列宽
					Integer integer = hashMap.get(i);
					if (length > integer) {
						hashMap.put(j, length);
					}
				}
				
			}
		}
		
		for (int colNum = 0; colNum < columnTotal; colNum++) {
			// 给每一列设置宽度。显示比例缩放为100%时一行最多显示35个字符，十六个汉字
			xSSFSheet.setColumnWidth(colNum, (int)((hashMap.get(colNum) + 0.73) * 256));
		}
		
		// 让列宽随着导出的列长自动适应（一列中的每行数值所占的最大长度）
		columnTotal = 0;
		for (int colNum = 0; colNum < columnTotal; colNum++) {
			// 取默认列宽8个字符
//				int columnWidth = sheet.getColumnWidth(colNum) / 256;
			// 不循环首行列名，取含有数值的首行设置列宽
			// 初始列宽18 //+ 2
			 int columnWidth = 18;
//				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			// 从首行开始，根据每一列前两行单元格数据的最长字符长度设置该列列宽
			for (int rowNum = 0; rowNum < 2; rowNum++) {
				XSSFRow currentRow;
				// 当前行未被使用过
				if (xSSFSheet.getRow(rowNum) == null) {
					currentRow = xSSFSheet.createRow(rowNum);
				} else {
					currentRow = xSSFSheet.getRow(rowNum);
				}
				XSSFCell currentCell = currentRow.getCell(colNum);
				if (currentCell != null) {
					// 获取单元格的数据
					String value = ExcelUtil.getValue(currentCell);
					// 取单元格数据操作系统默认编码格式的字符长度
					int length = value.getBytes().length;
					// GBK编码的9个汉字的长度
					if (length > columnWidth) {
						if (length > 34) {
							// 设置ID长度34字符为最长宽度
							length = 34;
//								currentCell.setCellStyle(leftStyle);
						}
						columnWidth = length;
//							if(rowNum > 1)
//								break;
					}
				}
			}
//				sheet.autoSizeColumn(colNum, false);
//			     * @param colindex- 要设置的列（从0开始）
//			     * @param width - 以字符宽度的1 / 256为单位的宽度
//				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			// 边距一个汉字
//				int margin = 2;
//				sheet.setColumnWidth(colNum, (int)((columnWidth + 0.73 - 1) * 256));
		}
			// 不写入
//			if (xSSFWorkbook != null) {
//				try {
//					xSSFWorkbook.write(out);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			
		/*} catch (Exception e) {
			e.printStackTrace();*/
			
//		} finally {
//			IOUtils.closeQuietly(out);
		/*}*/
	}
	
	/**
	 * 读取Excel的sheet存入JSONArray
	 * @param in 文件输入流
	 * @return
	 */
	public static List<JSONArray> loadExcel(InputStream in) {
		ArrayList<JSONArray> arrayList = new ArrayList<JSONArray>();
		BufferedInputStream bis = null;
		try {
			org.apache.poi.ss.usermodel.Workbook wb = null;
			bis = new BufferedInputStream(in);
			// POI操作Excel文件，通过文件流判断Excel的版本
			if (POIFSFileSystem.hasPOIFSHeader(bis)) { // 2003及以下
				wb = new HSSFWorkbook(bis);
			} else if (POIXMLDocument.hasOOXMLHeader(bis)) { // 2007及以上
				wb = new XSSFWorkbook(bis);
			} else
				throw new IllegalArgumentException("excel解析失败！");
			int numberOfSheets = wb.getNumberOfSheets();
			// 只读第一个sheet
//			numberOfSheets = 1;
			for (int i = 0; i < numberOfSheets; i++) {
				JSONArray jsonArray = new JSONArray();
				Sheet sheet = wb.getSheetAt(i); // 读取sheet i
				int firstRowIndex = sheet.getFirstRowNum(); // 第一行是列名
				int lastRowIndex = sheet.getLastRowNum();
				// 第一行列名 不使用列名
				// ArrayList<String> list = new ArrayList<String>();
				int firstCellNum = 0;
				int lastCellNum = 0;
				for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) { // 遍历行
					Row row = sheet.getRow(rIndex);
					if (row != null) {
						// 每一行都从头开始读
						// firstCellNum = row.getFirstCellNum();
						lastCellNum = row.getLastCellNum();
						// 一行数据
						JSONObject jsonObject = new JSONObject();
						for (int cIndex = firstCellNum; cIndex < lastCellNum; cIndex++) { // 遍历列
							Cell cell = row.getCell(cIndex);
							if (cell != null) {
								jsonObject.put("key" + cIndex, ExcelUtil.getValue(cell));
								// 以lastCellNum为总列数若中间不存在cell则视为空值
							} else {
								jsonObject.put("key" + cIndex, "");
							}
						}
						jsonArray.add(jsonObject);
					}
				}
				arrayList.add(jsonArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bis);
		}
		return arrayList;
	}

	/**
	 * 获取单元格值
	 * @param cell
	 * @return
	 */
	public static String getValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
				break;
			} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_STRING: // 字符串
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			value = cell.getBooleanCellValue() + "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			value = cell.getCellFormula() + "";
			break;
		case HSSFCell.CELL_TYPE_BLANK: // 空值
			value = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 故障
			value = "";
			break;
		default:
			value = "";
			break;
		}
		return value;
	}

	/**
	 * 标题单元格样式
	 * @param workbook
	 * @return
	 */
	public static XSSFCellStyle getTitleXSSFCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle createCellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("黑体");
		createCellStyle.setFont(font);
		
		createCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		createCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		createCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		createCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		createCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		createCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		createCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		createCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		// 设置自动换行
		createCellStyle.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		createCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		createCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置单元格背景颜色
		// RGB(192,192,192)
//		createCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		createCellStyle.setFillForegroundColor(new XSSFColor(new Color(230, 230, 230)));
		// 设置填充图案
		createCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		return createCellStyle;
	}

	/**
	 * 数据单元格样式
	 * @param workbook
	 * @return
	 */
	public static XSSFCellStyle getDataXSSCellStyle(XSSFWorkbook workbook) {
		// 创建样式
		XSSFCellStyle createCellStyle = workbook.createCellStyle();
		// 创建字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小默认10
		font.setFontHeightInPoints((short)10);
		// 字体加粗默认400
		font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// 设置字体
		font.setFontName("楷体");
		// 在样式用应用设置的字体
		createCellStyle.setFont(font);
		
		// 设置底边框
		createCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色
		createCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框
		createCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色
		createCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框
		createCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色
		createCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框
		createCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色
		createCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		// 设置自动换行
		createCellStyle.setWrapText(true);
		// 设置水平对齐的样式为左对齐
		createCellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		// 设置垂直对齐的样式为垂直顶部
		createCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		
		// 拿到palette颜色板
//		HSSFPalette palette = workbook.getCustomPalette();
//		palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 230, (byte) 230, (byte) 230);
//		style.setFillForegroundColor(palette.getColor(HSSFColor.LIME.index).getIndex());
		return createCellStyle;
	}
	
	public static XSSFCellStyle getLeftTopXSSCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle dataXSSCellStyle = getDataXSSCellStyle(workbook);
		dataXSSCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		return dataXSSCellStyle;
	}
}

class ZipUtil {
	/**
     * 	递归的从里向外删除文件和目录
     * @param dir
     */
    public static void removedir(File dir) {
    	// 删除整个目录
    	if(dir.exists()){
            File[] files = dir.listFiles();
            for(File file:files) {
            /*    if(file.isDirectory()){//文件是目录继续遍历里面的目录，直到找到文件目录里面的文件
                    removedir(file);
                }else{
                    System.out.println(file.getAbsolutePath()+file.delete());//删除目录里面的文件
                }*/
                if(file.isFile()) {//是否是文件，是文件的话，直接删除
                	file.delete();//删除目录里面的文件
//                	System.out.println("删除文件：" + file.getAbsolutePath());
                } else {
                    removedir(file);//不是文件，是目录，递归的遍历，直到是文件
                }
            }
            dir.delete();//删除目录从里向外删除
//            System.out.println("删除目录：" + dir.getAbsolutePath());
        } else {
            throw new RuntimeException("删除的目录文件不存在");
        }
    }
    
    private static final int  BUFFER_SIZE = 2 * 1024;
	/**
	 * 压缩成ZIP 方法1
	 * @param srcDir 压缩文件夹路径 
	 * @param out    压缩文件输出流
	 * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
	 * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException{
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			ZipUtil.compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 压缩成ZIP 方法2
	 * @param srcFiles 需要压缩的文件列表
	 * @param out 	        压缩文件输出流
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1){
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) +" ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 递归压缩方法
	 * @param sourceFile 源文件
	 * @param zos		 zip输出流
	 * @param name		 压缩后的名称
	 * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
	 * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception{
		byte[] buf = new byte[BUFFER_SIZE];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if(KeepDirStructure){
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
			}else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),KeepDirStructure);
					}
					
				}
			}
		}
	}
    
}

class DocxUtil {
	/**
     * 	生成docx
     * @param dataMap 替换数据
     * @param docxFile docx模板
     * @param xmlFile 替换xml文件
     * @param outputStream 输出流
     * @param footerMap 页脚替换数据
     * @param footerFile 页脚xml文件
     */
	public void createDocx(Map<String, Object> dataMap, File docxFile, File xmlFile, OutputStream outputStream, Map<String, String> footerMap, File footerFile) {
		ZipOutputStream zipout = null;
		ZipFile zipFile = null;
		try {
			/*
			 * //图片配置文件模板 ByteArrayInputStream documentXmlRelsInput =
			 * FreeMarkUtils.getFreemarkerContentInputStream(dataMap, documentXmlRels);
			 */
			// 内容模板
			ByteArrayInputStream documentInput = DocxUtil.getFreemarkerContentInputStream(dataMap, xmlFile);
			
			//<w:t>Rev</w:t>
			//<#list squad as s></#list>
			ByteArrayInputStream freemarkerContentInputStream = null;
			if(!footerMap.isEmpty())
				freemarkerContentInputStream = DocxUtil.getFreemarkerContentInputStream(footerMap, footerFile);
//			HashMap<String, Object> hashMap = new HashMap<String, Object>();
//			hashMap.put("dis", "001");
//			ByteArrayInputStream freemarkerContentInputStream = getFreemarkerContentInputStream(hashMap, new File("D:\\patch\\document\\AGP-TDS系统文档管理功能\\1\\DISTRIBUTION SHEET模板 - 副本\\word\\footer1.xml"));
			
//			File file = new File("D:\\patch\\document\\AGP-TDS系统文档管理功能\\1\\DISTRIBUTION SHEET模板 - 副本\\word\\media\\image1.jpeg");
//			FileInputStream fileInputStream = new FileInputStream(file);
			
			// 最初设计的模板
			// File docxFile = new
			// File(WordUtils.class.getClassLoader().getResource(template).getPath());
			// File docxFile = new
			// File("D:\\patch\\document\\DISTRIBUTIONSHEET模板.zip");//换成自己的zip路径
			if (!docxFile.exists()) {
				docxFile.createNewFile();
			}
			zipFile = new ZipFile(docxFile);
			Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
			zipout = new ZipOutputStream(outputStream);
			// 开始覆盖文档------------------
			int len = -1;
			byte[] buffer = new byte[1024];
			while (zipEntrys.hasMoreElements()) {
				ZipEntry next = zipEntrys.nextElement();
				InputStream is = zipFile.getInputStream(next);
				// 需要图片
//				if (next.toString().indexOf("media") < 0) {
				zipout.putNextEntry(new ZipEntry(next.getName()));
				if ("word/document.xml".equals(next.getName()) && documentInput != null) {// 如果是word/document.xml由我们输入
					while ((len = documentInput.read(buffer)) != -1) {
						zipout.write(buffer, 0, len);
					}
					documentInput.close();
				} else if ("word/footer1.xml".equals(next.getName()) && freemarkerContentInputStream != null) {// 页脚
					while ((len = freemarkerContentInputStream.read(buffer)) != -1) {
						zipout.write(buffer, 0, len);
					}
					freemarkerContentInputStream.close();
					// 图片配置路径word/_rels/header1.xml.rels
//					} else if ("word/media/image1.jpeg".equals(next.getName() && fileInputStream != null)) {// 图片
//						while ((len = fileInputStream.read(buffer)) != -1) {
//							zipout.write(buffer, 0, len);
//						}
//						fileInputStream.close();
				} else {
					// 没有替换原样写入
					while ((len = is.read(buffer)) != -1) {
						zipout.write(buffer, 0, len);
					}
					is.close();
				}
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zipout != null) {
				try {
					zipout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			try {
//				zipFile.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	/**
     * 获取模板字符串输入流
     * @param dataMap   参数
     * @param templateName  模板名称
     * @return
     */
    public static ByteArrayInputStream getFreemarkerContentInputStream(Map<?, ?> dataMap, File xmlFile) {
        ByteArrayInputStream in = null;
        try {
            //获取模板
        	Configuration configuration = getConfiguration(xmlFile.getParentFile());// document.xml所在目录
        	//configuration.setClassicCompatible(true);
            Template template = configuration.getTemplate(xmlFile.getName());// document.xml文件名字
//            Template template = configuration.getTemplate("document.xml");
            StringWriter swriter = new StringWriter();
            //生成文件
            template.process(dataMap, swriter);//替换模板中变量${xxx}

            in = new ByteArrayInputStream(swriter.toString().getBytes("utf-8"));//这里一定要设置utf-8编码 否则导出的word中中文会是乱码
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return in;
    }

    public static Configuration getConfiguration(File xmlDir) {
        //创建配置实例
//        Configuration configuration = new Configuration(Configuration.getVersionNumber());
        Configuration configuration = new Configuration();
        //设置编码
        configuration.setDefaultEncoding("utf-8");
        // 使用类路径+resources路径
//        configuration.setClassForTemplateLoading(FreeMarkUtils.class, "/dm_doclog_word_tmp/");//换成自己对应的目录
        // 基于web project
//        configuration.setServletContextForTemplateLoading(servletContext, "/dm/doclog/dm_doclog_word_tmp");
        // 文件系统
        try {
			configuration.setDirectoryForTemplateLoading(xmlDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return configuration;
    }
}


/**
 * @author 
 * @description:
 * @date 
 */
class JsonUtil {

	private int recursionTimes = 4;
	private String dateFormat = "yyyy-MM-dd HH:mm";
	private List<HashMap<Integer, Object>> recursionObject = new ArrayList<>();
//	private static final HashSet<String> EMPTY_EXCLUDNAME;
//	static {
//		EMPTY_EXCLUDNAME = new HashSet<String>();
//	}
	private HashSet<String> excludeFieldNames = new HashSet<String>();
	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(JsonUtil.class);

	public JsonUtil() {
		// EnhancerByCGLIB
		this.excludeFieldNames.add("hibernateLazyInitializer");
		this.excludeFieldNames.add("interceptFieldCallback");
		// Property '" + key + "' of " + bean.getClass() + " has no read method. SKIPPED
		this.excludeFieldNames.add("latelyNames");
		this.excludeFieldNames.add("leader");
		this.excludeFieldNames.add("myLeader");
		// com.landray.kmss.sys.right.interfaces.ExtendAuthModel
		String[] extendAuthModelFieldNames = new String[] { "attachmentForms", "authAllEditors", "authAllReaders",
				"authArea", "authAttCopys", "authAttDownloads", "authAttNocopy", "authAttNodownload", "authAttNoprint",
				"authAttPrints", "authChangeAtt", "authChangeEditorFlag", "authChangeReaderFlag", "authEditorFlag",
				"authEditors", "authOtherEditors", "authOtherReaders", "authRBPFlag", "authReaderFlag", "authReaders",
				"customPropMap", "dynamicMap", "mechanismMap", "sysDictModel", "toFormPropertyMap" };
		this.excludeFieldNames.addAll(Arrays.asList(extendAuthModelFieldNames));
		// com.landray.kmss.sys.organization.model.SysOrgElement
//		this.excludeFieldNames.add("fdChildren");
//		this.excludeFieldNames.add("fdParent");
		String[] sysOrgElementFieldNames = new String[] { "addressTypeList", "allLeader", "allMyLeader",
				"authElementAdmins", "fdGroups", "fdInitPassword", "fdParentOrg", "fdPassword", "fdPersons", "fdPosts",
				"hbmChildren", "hbmGroups", "hbmParent", "hbmParentOrg", "hbmPersons", "hbmPosts", "hbmSuperLeader",
				"hbmSuperLeaderChildren", "hbmThisLeader", "hbmThisLeaderChildren", "docCreator", "docAlteror" };
		this.excludeFieldNames.addAll(Arrays.asList(sysOrgElementFieldNames));
		// com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel
		String[] sysSimpleCategoryAuthTmpModelFieldNames = new String[] { "authNotReaderFlag", "authTmpAttCopys",
				"authTmpAttDownloads", "authTmpAttNocopy", "authTmpAttNocopy", "authTmpAttNodownload",
				"authTmpAttNoprint", "authTmpAttPrints", "authTmpEditors", "authTmpReaders" };
		this.excludeFieldNames.addAll(Arrays.asList(sysSimpleCategoryAuthTmpModelFieldNames));
		// list - main
//		this.excludeFieldNames.add("docMain");
//		this.excludeFieldNames.add("docTemplate");
		this.excludeFieldNames.add("extendDataModelInfo");
		this.excludeFieldNames.add("extendDataXML");
		// fdParent-fdChildren
//		this.excludeFieldNames.add("fdChildren");
	}
	
	public JSONObject parse(Object bean) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "handler", "hibernateLazyInitializer", "interceptFieldCallback" });
//		jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//		jsonConfig.setIgnoreTransientFields(false);
		return JSONObject.fromObject(bean, jsonConfig);
	}

	public JSONObject beanToJson(Object bean) {
		// class
		this.excludeFieldNames.add("class");
		this.excludeFieldNames.add("formClass");

//		JSONObject jsonObject = new JSONObject();
//		PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(bean);
//		for (PropertyDescriptor propertyDescriptor: propertyDescriptors) {
//			this.oneBreadthIsRecursion = false;
//			this.setJsonObject(jsonObject, bean, propertyDescriptor, this.recursionTimes);
//			// 当前对象递归结束
//			if(this.oneBreadthIsRecursion) {
//				this.oneDepthAllRecursionObject.clear();
//			}
//		}
//		return jsonObject;

		for (int i = 0; i <= this.recursionTimes; i++) {
			HashMap<Integer, Object> hashMap = new HashMap<Integer, Object>();
			this.recursionObject.add(hashMap);
		}
		// 初始对象始终保留不再递归出该对象的重复数据加入json中
		HashMap<Integer, Object> hashMap = new HashMap<Integer, Object>();
		hashMap.put(bean.hashCode(), bean);
		this.recursionObject.add(hashMap);

		return parseBean(bean, this.recursionTimes);
	}

	/**
	 * 解析JavaBean
	 * @param bean JavaBean对象
	 * @param recursionTimes 递归次数
	 * @return
	 */
	private JSONObject parseBean(Object bean, int recursionTimes) {
//		this.oneBreadthIsRecursion = true;
		JSONObject jsonObject = new JSONObject();

		java.beans.PropertyDescriptor[] propertyDescriptors = 
				org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			this.setJsonObject(jsonObject, propertyDescriptor, bean, recursionTimes);
		}
		return jsonObject;
	}

	private void setJsonObject(JSONObject jsonObject, PropertyDescriptor propertyDescriptor, Object bean,
			int recursionTimes) {
		String name = propertyDescriptor.getName();
		// 通过哈希函数，能够快速地对数据元素进行定位，hash算法尽量减少冲突使链表长度尽可能短，理想状态下时间复杂度为O(1)
		if (!this.excludeFieldNames.contains(name)) {
			Method readMethod = propertyDescriptor.getReadMethod();
			if (readMethod != null) {
				Object property = null;
				try {
					property = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, name);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Object value = this.getValue(property, recursionTimes);
				// key为null时，net.sf.json.JSONObject无法存入，修改为"null"。value为null时，修改为空仍然存放到jsonObject
				jsonObject.put(name == null ? "null" : name.toString(), value == null ? "" : value);
			} else {
				this.excludeFieldNames.add(name);
				log.warn("Property '" + name + "' of " + bean.getClass() + " has no read method. SKIPPED");
			}
		}
	}

	/**
	 * 获取字段的值
	 * @param object 字段对象
	 * @param recursionTimes 递归次数
	 * @return
	 */
	private Object getValue(Object object, int recursionTimes) {
		// JSONObject中放置Map的时候，会自动将Map看成是JSONObject来处理。JSON allows only string to be a key
//		if(recursionTimes <= 0)
//			return object.getClass();
//		--recursionTimes;
		Object value;
		if (object == null) {
			value = null;
		} else if (object instanceof String) {
			value = object.toString();
		} else if (object instanceof Number) {
			Number number = (Number) object;
			value = number.doubleValue();
		} else if (object instanceof Boolean) {
			value = object;
		} else if (object instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
			String format = sdf.format(object);
			value = format;
		} else if (Collection.class.isAssignableFrom(object.getClass())) {
			JSONArray jsonArray = new JSONArray();
			
			Collection<?> collection = (Collection<?>) object;
			Iterator<?> iterator = collection.iterator();
			while (iterator.hasNext()) {
				Object next = iterator.next();
				// 限制过多对象集合
//            	Class<?> superclass = next.getClass().getSuperclass();
//            	Integer times = this.recursionClassTimes.get(superclass.getName());
//        		if(times != null) 
//        			--recursionTimes;
				Object val = this.getValue(next, recursionTimes);
				jsonArray.add(val);
			}
			value = jsonArray;
		} else if (Map.class.isAssignableFrom(object.getClass())) {
			JSONObject jsonObject = new JSONObject();
			Map<?, ?> map = ((Map<?, ?>) object);
			try {
				Iterator<?> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					Object key = iterator.next();
					Object obj = map.get(key);
					Object val = this.getValue(obj, recursionTimes);
					
					jsonObject.put(key == null ? "null" : key.toString(), val == null ? "" : val);
				}
			} catch (java.lang.UnsupportedOperationException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			value = jsonObject;
		} else if (object.getClass().isArray()) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < Array.getLength(object); i++) {
				Object obj = Array.get(object, i);
				Object val = this.getValue(obj, recursionTimes);
				jsonArray.add(val);
			}
			value = jsonArray;
		} else {
			if (recursionTimes <= 0) return object.getClass();

			// 清除该递归次数中的所有对象
			for (int i = recursionTimes; i > 0; i--) {
				HashMap<Integer, Object> hashMap = this.recursionObject.get(i);
				if (hashMap.isEmpty()) {
					break;
				} else {
					hashMap.clear();
				}
			}

			// 两个对象是包含的关系
			int hashCode = object.hashCode();
			for (int i = recursionTimes + 1; i <= this.recursionTimes + 1; i++) {
				HashMap<Integer, Object> hashMap = this.recursionObject.get(i);
				// 先比较哈希码
				Object oldObject = hashMap.get(hashCode);
				if (oldObject != null) {
					/**
					 * 此方法只比较public字段。
					 * 不比较transient字段，因为它们不能被序列化。
					 * 此外，此方法不比较static字段，因为它们不是对象实例的一部分。
					 * 如果某个字段是一个数组/Map/Collection，则比较内容，而不是对象的引用。
					 * @param excludeFields  不比较的字段
					 */
					if (org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(oldObject, object)) {
					//if (this.isEquals(oldObject, object)) {
						// 定制BaseModel对象
						if (com.landray.kmss.common.model.BaseModel.class.isAssignableFrom(object.getClass())) {
							String fdId = ((com.landray.kmss.common.model.BaseModel) object).getFdId();
							JSONObject json = new JSONObject();
							json.put("fdId", fdId);
							return json;
						}
						// 相同对象返回null
						return null;
					} else {
						log.warn(object.getClass() + "：" + object.toString() + " 字段不相等！");
					}
				}
			}
			HashMap<Integer, Object> hashMap = this.recursionObject.get(recursionTimes);
			hashMap.put(hashCode, object);

			// 再次解析并且递归次数减一
			JSONObject parseBean = this.parseBean(object, --recursionTimes);
			// JSONObject objectValue = getMethodValue(object, --recursionTimes);
			value = parseBean;
		}
		return value;
	}
	
	public void setRecursionTimes(int recursionTimes) {
		this.recursionTimes = recursionTimes;
	}

	public void addExcludeFieldNames(List<String> excludeFieldNames) {
		this.excludeFieldNames.addAll(excludeFieldNames);
	}

	public boolean removeExcludeFieldNames(String fieldName) {
		return excludeFieldNames.remove(fieldName);
	}
	
	public void clearExcludeField() {
		excludeFieldNames.clear();
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	class old {
		/**
		 * 比较两个对象属性值是否相等
		 * 
		 * @param oldObject
		 * @param newObject
		 * @param ignoreProperties
		 * @param <T>
		 * @return 是否相等
		 */
		public <T> boolean isEquals(Object oldObject, Object newObject, String... ignoreProperties) {
			boolean isEquals = true;
			if (oldObject != null && newObject != null) {
				// 如果没有重写equals方法将会判断两个对象内存地址是否一致即oldObject==newObject
				if (oldObject.equals(newObject)) {
					return true;
				}

				Map<String, Object> oldObjectPropertyMap = this.getObjectPropertyMap(oldObject);
				Map<String, Object> newObjectPropertyMap = this.getObjectPropertyMap(newObject);

				for (String key : oldObjectPropertyMap.keySet()) {
					Object oldObjectPropertyValue = oldObjectPropertyMap.get(key);
					Object newObjectPropertyValue = newObjectPropertyMap.get(key);
					if (newObjectPropertyValue != null && oldObjectPropertyValue != null) {
						if (!(oldObjectPropertyValue.equals(newObjectPropertyValue))) {
							if (Collection.class.isAssignableFrom(oldObjectPropertyValue.getClass())) {

							} else if (com.landray.kmss.common.model.BaseModel.class
									.isAssignableFrom(oldObjectPropertyValue.getClass())) {

							} else {
								isEquals = false;
								break;
							}
						}
						// 两个对象都指向null
					} else if (oldObjectPropertyValue != newObjectPropertyValue) {
						isEquals = false;
						break;
					}
				}
			} else if (oldObject != newObject) {
				isEquals = false;
			}
			return isEquals;
		}

		/**
		 * 获取对象属性名字和数值
		 * 
		 * @param <T>
		 * @param object
		 * @param ignoreProperties
		 * @return 属性名作为map的key，属性值作为map的value
		 */
		public <T> Map<String, Object> getObjectPropertyMap(Object object, String... ignoreProperties) {
			Class<?> objectClass = object.getClass();
			PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(objectClass);

			HashMap<String, Object> propertyMap = new HashMap<>(propertyDescriptors.length);

			Collection<?> ignorePropertyCollection = null;
			if (ignoreProperties != null) {
				ignorePropertyCollection = Arrays.asList(ignoreProperties);
			} else {
//				ignorePropertyCollection = this.excludeFieldNames;
			}
			if (ignorePropertyCollection.isEmpty()) {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						Object invoke = null;
						try {
							invoke = readMethod.invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						propertyMap.put(name, invoke);
					}
				}
			} else {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null && ignorePropertyCollection.contains(name)) {
						Object invoke = null;
						try {
							invoke = readMethod.invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						propertyMap.put(name, invoke);
					}
				}
			}
			return propertyMap;
		}

		@SuppressWarnings("unused")
		private JSONObject parseForDeclaredField(Object bean, int recursionTimes) {
			JSONObject jsonObject = new JSONObject();
			// 通过getDeclaredFields()?法获取对象类中的所有属性（含私有）
			java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();

			for (java.lang.reflect.Field field : fields) {
				// 设置允许通过反射访问私有变量
				// if (Modifier.isPrivate(field.getModifiers()))
				field.setAccessible(true);
				// 获取字段属性名称
				String name = field.getName();
				Object object = null;
				try {
					object = field.get(bean);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (object != null && name != null && name != "") {
					Object value = getValue(object, recursionTimes);
					jsonObject.put(name, value);
				} else {
					// 避免没值而导致前端undefined
					jsonObject.put(name, "");
				}
			}
			return jsonObject;
		}

		@SuppressWarnings("unused")
		private JSONObject getMethodValue(Object object, int recursionTimes) {
			JSONObject jsonObject = new JSONObject();

			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				String name = method.getName();
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (name.contains("getFd") && parameterTypes.length == 0) {
					Object invoke = null;
					try {
						invoke = method.invoke(object);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (invoke != null && !(invoke instanceof Method)) {
						int indexOf = name.indexOf("getF");
						if (indexOf != -1) {
							String substring = name.substring(indexOf + "getF".length(), name.length());
							if (substring != null && substring != "") {
								Object value = getValue(invoke, recursionTimes);
								jsonObject.put('f' + substring, value);
							}
						}
					}
				}
			}
			return jsonObject;
		}

		@SuppressWarnings("unused")
		private JSONObject getCGLIBValue(Object bean1) {
			JSONObject jsonObj = new JSONObject();
			Field[] fields = bean1.getClass().getDeclaredFields();
			for (java.lang.reflect.Field field : fields) {
				field.setAccessible(true);
				Object object1 = null;
				try {
					object1 = field.get(bean1);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				String name1 = field.getName();
				if (object1 != null && name1 != null && name1 != "") {
					if (object1 instanceof Method) {
						// 对象的组织架构对象调用get方法取值
						if (name1.contains("getFd")) {
							Method method = (Method) object1;
							Class<?>[] parameterTypes = method.getParameterTypes();
							// 没有参数调用
							if (parameterTypes.length == 0) {
								Object invoke = null;
								try {
									invoke = method.invoke(bean1);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
								if (invoke != null && !(invoke instanceof Method)) {
									int indexOf = name1.indexOf("CGLIB$getF");
									int lastIndexOf = name1.lastIndexOf("$");
									if (indexOf != -1 && lastIndexOf != -1 && lastIndexOf > indexOf) {
										String substring = name1.substring(indexOf + "CGLIB$getF".length(), lastIndexOf);
										if (substring != null && substring != "") {
											jsonObj.put('f' + substring, invoke.toString());
										}
									}
								}
							}
						}
					} else {

					}
				} else {
					jsonObj.put(name1, "");
				}
			}
			return jsonObj;
		}
	}
}

class test {
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		// TODO Auto-generated method stub
		LbpmProcess self = execution.getProcessInstance();
		if (self == null) {
			return;
		}
		String modelName = self.getFdModelName();
		String modelId = self.getFdModelId();
		KmAgreementApply kmAgreementApply = (KmAgreementApply) execution.getMainModel();
		
		
		Date docCreateTime = lsadContractTodo.getDocCreateTime();
//            	
//            	Instant instant = docCreateTime.toInstant();
//            	ZoneId zone = ZoneId.systemDefault();
//            	LocalDateTime docCreateTimeLocal = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
//            	long daysBetween = ChronoUnit.DAYS.between(docCreateTimeLocal.toLocalDate(), LocalDateTime.now().toLocalDate());
            	
//            	Integer fdDaysDue = lsadContractTodo.getFdDaysDue();
//            	if (fdDaysDue <= daysBetween) {
                    //
                    
//            	}
	}
	
	public String add(IBaseModel modelObj) throws Exception {
		// TODO Auto-generated method stub
    	LsadContractTodo lsadContractTodo = (LsadContractTodo)modelObj;
    	Integer fdDaysDue = lsadContractTodo.getFdDaysDue();
    	Date fdStartDate = lsadContractTodo.getFdStartDate();
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(fdStartDate);
    	calendar.add(Calendar.DATE, fdDaysDue);
    	
    	lsadContractTodo.setFdDaysDueDate(calendar.getTime());// 项目定制001
    	
		return super.add(lsadContractTodo);
	}
	
	public void report(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
	        String List_Selected = request.getParameter("List_Selected");
			if(StringUtil.isNotNull(List_Selected)) {
				String[] columnArr = new String[] {"合同编号", "合同名称", "签订日期", "合同金额", "币种", "备案时间", "申购部门",
						"采购方式", "经费类型", "申购经办人/电话", "中标单位", "中标单位联系人/电话", "中标日期", "社会代理机构", "是否免税", "外贸合同号", "外贸合同签订日期", "外贸代理公司", "外贸合同乙方",
						"制造商", "货物名称", "品牌", "规格型号", "数量", "单位", "单价", "产地", "保修期", "使用场所", "监管其至", "目的口岸", "预计到货时间", "提交免税申请时间", "免税证号", "免税证签发日期", 
						"通知发货日期", "到货日期", "开箱日期", "验收日期", "资产编号", "付款方式", "第一次付款日期", "第一次付款比例", "第二次付款日期", "第二次付款比例", "第三次付款日期", "第三次付款比例", "备注"};
				
				@SuppressWarnings("unchecked")
				List<KmAgreementApply> kmAgreementApplyList = getServiceImp(request).findByPrimaryKeys(List_Selected.split(","));
				
				Object[][] dataArray = new Object[kmAgreementApplyList.size() + 1][columnArr.length];
				dataArray[0] = columnArr;
				for (int i = 0; i < kmAgreementApplyList.size(); i++) {
					KmAgreementApply kmAgreementApply = kmAgreementApplyList.get(i);
					Object[] dataArr = new Object[columnArr.length]; //数据列表
					
					int j = 0;
					dataArr[j++] = kmAgreementApply.getDocNumber();
					dataArr[j++] = kmAgreementApply.getDocSubject();
					
					Date docCreateTime = kmAgreementApply.getDocCreateTime();
					dataArr[j++] = docCreateTime == null ? "" : DateUtil.convertDateToString(docCreateTime, DateUtil.PATTERN_DATETIME);
					
					dataArr[j++] = kmAgreementApply.getFdAccount() == null ? "" : kmAgreementApply.getFdAccount();
					
					Map<String, Object> modelData = kmAgreementApply.getExtendDataModelInfo().getModelData();
					List<?> li = (List<?>)(modelData.get("fd_39c5a8a6dbc988_record"));
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(li)) {
						HashMap<?,?> map = (HashMap<?, ?>)li.get(0);
						dataArr[j++] = map.get("data") == null ? "" : map.get("data").toString();
					} else {
						dataArr[j++] = "";
					}
					
					dataArr[j++] = ""; // 备案时间
					
					SysOrgElement fdCreatordept = kmAgreementApply.getFdCreatordept();
					dataArr[j++] = fdCreatordept == null ? "" : fdCreatordept.getFdName();
					
					List<?> li2 = (List<?>)(modelData.get("fd_39c80b3b1cbba2_record"));
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(li2)) {
						HashMap<?,?> map = (HashMap<?, ?>)li2.get(0);
						dataArr[j++] = map.get("data") == null ? "" : map.get("data").toString();
					} else {
						dataArr[j++] = "";
					}
					
					Object obj = modelData.get("fd_39c5a9ca68f262");// 经费名称
					dataArr[j++] = obj == null ? "" : obj.toString();
					
					SysOrgPerson fdManager = kmAgreementApply.getFdManager();
					dataArr[j++] = fdManager == null ? "" : fdManager.getFdName() + (fdManager.getFdWorkPhone() == null ? "" : "/"+ fdManager.getFdWorkPhone());
					
					List<?> li3 = (List<?>) modelData.get("fd_39cb748232b21e");
					
					String str3 = "";
					String str4 = "";
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(li3)) {
						String[] strArr = li3.stream()
						        .flatMap(map -> Stream.of(
						            ((HashMap<?, ?>) map).get("fd_39cb74b8f3b7a8_text"),
						            ((HashMap<?, ?>) map).get("fd_39cb74be35a2da"),
						            ((HashMap<?, ?>) map).get("fd_39cb74bf8d30b0")
						        ))
						        .filter(Objects::nonNull)
						        .map(Object::toString)
						        .toArray(String[]::new);

						String[] result = IntStream.range(0, strArr.length / 3)
						        .mapToObj(k -> {
						            String s3 = strArr[k * 3] + ",";
						            String s4 = (k * 3 + 1 < strArr.length) ? strArr[k * 3 + 1] + "/" : "";
						            s4 += (k * 3 + 2 < strArr.length) ? strArr[k * 3 + 2] + "," : "";
						            return s3 + s4;
						        })
						        .toArray(String[]::new);
						str3 = Arrays.stream(result).map(s -> s.split(",")[0]).collect(Collectors.joining(";"));
						str4 = Arrays.stream(result).map(s -> s.split(",")[1]).collect(Collectors.joining(";"));
					}
					
					dataArr[j++] = str3;
					dataArr[j++] = str4;

					FsscFeeMain fdFsscPurch = kmAgreementApply.getFdFsscPurch();
					dataArr[j++] = fdFsscPurch == null ? "" : DateUtil.convertDateToString(fdFsscPurch.getDocCreateTime(), DateUtil.PATTERN_DATETIME);

					String str6 = "";
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock("docStatus!='00' and fdModelId =:applyId");
					hqlInfo.setParameter("applyId", kmAgreementApply.getFdId());
					List<?> findList = getKmReviewMainService(request).findList(hqlInfo);
					if (!findList.isEmpty()) {
						KmReviewMain kmReviewMain = (KmReviewMain)findList.get(0);
						SysOrgElement fdDepartment = kmReviewMain.getFdDepartment();
						// 招标确认流程的 代理机构
						str6 = fdDepartment == null ? "" : fdDepartment.getFdName();
					}
					dataArr[j++] = str6;
					
					// 是否免税，合同审批流程增加的字段
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";// 数量
					
					List<?> li4 = (List<?>)(modelData.get("fd_39c73baf0c1b76_record"));
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(li4)) {
						HashMap<?,?> map = (HashMap<?, ?>)li4.get(0);
						// 单位
						dataArr[j++] = map.get("data") == null ? "" : map.get("data").toString();
					} else {
						dataArr[j++] = "";
					}
					
					
					String str5  = "";
					Object obj5 = modelData.get("fd_39c5a8573627f6");
					if (Objects.nonNull(obj5)) {
						if ("确认单价".equals(obj5.toString())) {
							Object o5 = modelData.get("fd_39c5aa519dd730");
							if (Objects.nonNull(obj5)) {
								str5 = o5.toString();
							}
						}
					}
					dataArr[j++] = str5;
					
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					dataArr[j++] = "";
					
					// 资产编号
					dataArr[j++] = modelData.get("fd_39c5a9d025eb0c") == null ? "" : modelData.get("fd_39c5a9d025eb0c");

					// 付款方式
					dataArr[j++] = "";
					// 第一次付款
					dataArr[j++] = "";
					
					
					
					dataArray[i + 1] = dataArr;
				}
				
				response.setCharacterEncoding("UTF-8");
	        	response.setContentType("application/octet-stream");
	            String filename = "合同报表";//.getBytes("GBK"), "ISO-8859-1");
	            String userAgent = request.getHeader("User-Agent");
	            if (userAgent.matches(".*MSIE.*|.*rv.*|.*Trident.*|.*Edge.*")) {
	    			filename = URLEncoder.encode(filename, "UTF-8");
	    		} else if (userAgent.contains("Mozilla")) {
	    			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
	    		} else {
	    			filename = URLEncoder.encode(filename, "UTF-8");
	    		}
	            response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
	            
	            String tableName = String.valueOf(kmAgreementApplyList.size());
	            com.landray.kmss.km.agreement.util.ExcelUtil.export(dataArray, tableName, response.getOutputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected IKmReviewMainService kmReviewMainService;
	protected IKmReviewMainService getKmReviewMainService(HttpServletRequest request) {
		if (kmReviewMainService == null)
			kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
		return kmReviewMainService;
	}
	
	public ActionForward dataReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && ordertype.equalsIgnoreCase("down")) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve)
				orderby += " desc";
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			
			List<KmAgreementApply> list = page.getList();
			
			List<KmAgreementApply> collect = list.stream().peek(main -> {
				try {
					KmAgreementCoin kmAgreementCoin = new KmAgreementCoin();
					
					List<?> li = (List<?>)(main.getExtendDataModelInfo().getModelData().get("fd_39c5a8a6dbc988_record"));
					if (org.apache.commons.collections.CollectionUtils.isNotEmpty(li)) {
						HashMap<?,?> map = (HashMap<?, ?>)li.get(0);
						
						kmAgreementCoin.setFdName(map.get("data") == null ? "" : map.get("data").toString());
					}
					
					main.setFdCoin(kmAgreementCoin);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).collect(Collectors.toList());
			page.setList(collect);
			
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("dataReport", mapping, form, request, response);
		}
	}
}

