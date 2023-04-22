package com.landray.kmss.dm.doclog.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.IOUtils;
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
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.FileTypeUtil;
import com.landray.kmss.util.FileUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MUtil {

	/**
	 * ��������
	 * @param rowName
	 * @param dataList
	 * @param workbook
	 * @param sheet
	 * @throws Exception
	 */
	public static void exportExcel(String[] rowName, List<String[]> dataList, HSSFWorkbook workbook, HSSFSheet sheet)
			throws Exception {
		try {
			// ������Ԫ����ʽ����
			HSSFCellStyle style = MUtil.getStyle(workbook); 
			// �����е�Ԫ����ʽ
			HSSFCellStyle columnTopStyle = MUtil.getColumnTopStyle(workbook);
			
			for (int i = 0; i < dataList.size(); i++) {
				// �����ж���
				HSSFRow row = sheet.createRow(i);
				// ��ÿһ�����ø߶�
				if(i == 0)
					row.setHeight((short) 500);
				else
					// 20��
					row.setHeight((short) 400);
				
				Object[] obj = dataList.get(i);
				// ���������õ�sheet��Ӧ�ĵ�Ԫ����
				for (int j = 0; j < obj.length; j++) {
					// �����ַ������͵�Ԫ��
//					HSSFCell cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					HSSFCell cell = row.createCell(j);
					if(i == 0) 
						// ���ñ��������ʽ
						cell.setCellStyle(columnTopStyle);
					else
						// ���õ�Ԫ����ʽ
						cell.setCellStyle(style);
					
					if (obj[j] == null)
						// ����Ϊnull����Ϊ��
						cell.setCellValue("");
					else
						// ���õ�Ԫ���ֵ
						cell.setCellValue(obj[j].toString()); 
				}
			}
			// ������
			int columnNum = rowName.length;
			// ���п����ŵ������г��Զ���Ӧ��һ���е�ÿ����ֵ��ռ����󳤶ȣ�
			for (int colNum = 0; colNum < columnNum; colNum++) {
				// Ĭ���п�8���ַ�
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				// ��ѭ������������ȡ������ֵ�����������п�
//				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				// �����п�ʼ������ÿһ��ǰ���е�Ԫ�����ݵ���ַ��������ø����п�
				for (int rowNum = 0; rowNum < 2; rowNum++) {
					HSSFRow currentRow;
					// ��ǰ��δ��ʹ�ù�
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					HSSFCell currentCell = currentRow.getCell(colNum);
					if (currentCell != null) {
						// ��ȡ��Ԫ�������
						String value = MUtil.getValue(currentCell);
						// ȡ��Ԫ�����ݵĲ���ϵͳĬ�ϱ����ʽ���ַ�����
						int length = value.getBytes().length;
						if (length > columnWidth) {
							if(length > 34) {
								// ����ID����34�ַ�Ϊ����
								length = 34;
							}
							columnWidth = length;
							// ����ÿһ��ǰ���е�Ԫ�����ݵ���ַ��������ø����п�
//							if(rowNum > 1)
//								break;
						}
					}
				}
				// ��ÿһ�����ÿ��
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}
			// ��д��
//			if (workbook != null) {
//				try {
//					workbook.write(out);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
//		}finally {
//			IOUtils.closeQuietly(out);
		}
	}
	
	public static List<JSONArray> loadExcel(File file) throws IOException {
		Workbook wb = null;
		BufferedInputStream bis = null;
		List<JSONArray> excelList = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			String fileType = FileTypeUtil.getFileType(file);
//			String contentType = FileMimeTypeUtil.getContentType(file.getName());
			if(fileType != null && "wps".equals(fileType)) {
//				POIFSFileSystem create = POIFSFileSystem.create(file);
				wb = new HSSFWorkbook(bis);
			} else if("docx".equals(fileType)) {
				wb = new XSSFWorkbook(bis);
				//Content Type
			}
			excelList = getExcelList(wb);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bis.close();
		}
		
		
		return excelList;
	}
	
	/**
	 * ��ȡExcel��sheet����JSONArray
	 * @param in
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	public static List<JSONArray> loadExcel(InputStream in) {
		ArrayList<JSONArray> arrayList = null;
//		BufferedInputStream bis = null;
		Workbook wb = null;
//		Exception exception = null;
		try {
//			bis = new BufferedInputStream(in);
			
//			wb = new HSSFWorkbook(bis);
			
			wb = WorkbookFactory.create(in);
			arrayList = getExcelList(wb);

			
			// POI����Excel�ļ���ͨ���ļ����ж�Excel�İ汾
//			if (POIFSFileSystem.hasPOIFSHeader(bis)) { // 2003������
//				wb = new HSSFWorkbook(bis);
//			} else if (POIXMLDocument.hasOOXMLHeader(bis)) { // 2007������
//				wb = new XSSFWorkbook(bis);
//			} else
//				throw new IllegalArgumentException("excel����ʧ�ܣ�");
			
		} catch (Exception e) {
			e.printStackTrace();
//			exception = e;
		} finally {
			if(wb != null) {
				try {
					wb.close();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			IOUtils.close(bis);
		}
		return arrayList;
	}
	
	private static ArrayList<JSONArray> getExcelList(Workbook wb) {
		ArrayList<JSONArray> arrayList = new ArrayList<JSONArray>();
		int numberOfSheets = wb.getNumberOfSheets();
		// ֻ����һ��sheet
//		numberOfSheets = 1;
		for (int i = 0; i < numberOfSheets; i++) {
			JSONArray jsonArray = new JSONArray();
			Sheet sheet = wb.getSheetAt(i); // ��ȡsheet i
			int firstRowIndex = sheet.getFirstRowNum(); // ��һ��������
			int lastRowIndex = sheet.getLastRowNum();
			// ��һ������ ��ʹ������
			// ArrayList<String> list = new ArrayList<String>();
			int firstCellNum = 0;
			int lastCellNum = 0;
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) { // ������
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					// ÿһ�ж���ͷ��ʼ��
					// firstCellNum = row.getFirstCellNum();
					lastCellNum = row.getLastCellNum();
					// һ������
					JSONObject jsonObject = new JSONObject();
					for (int cIndex = firstCellNum; cIndex < lastCellNum; cIndex++) { // ������
						Cell cell = row.getCell(cIndex);
						if (cell != null) {
							jsonObject.put("key" + cIndex, MUtil.getValue(cell));
							// ��lastCellNumΪ���������м䲻����cell����Ϊ��ֵ
						} else {
							jsonObject.put("key" + cIndex, "");
						}
					}
					jsonArray.add(jsonObject);
				}
			}
			arrayList.add(jsonArray);
		}
		return arrayList;
	}

	/**
	 * ��ȡ��Ԫ��ֵ
	 * @param cell
	 * @return
	 */
//	public static String getValue(Cell cell) {
//		String value = "";
//		switch (cell.getCellType()) {
//		case HSSFCell.CELL_TYPE_NUMERIC: // ����
//			if (HSSFDateUtil.isCellDateFormatted(cell)) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
//				break;
//			} else {
//				value = new DecimalFormat("0").format(cell.getNumericCellValue());
//			}
//			break;
//		case HSSFCell.CELL_TYPE_STRING: // �ַ���
//			value = cell.getStringCellValue();
//			break;
//		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
//			value = cell.getBooleanCellValue() + "";
//			break;
//		case HSSFCell.CELL_TYPE_FORMULA: // ��ʽ
//			value = cell.getCellFormula() + "";
//			break;
//		case HSSFCell.CELL_TYPE_BLANK: // ��ֵ
//			value = "";
//			break;
//		case HSSFCell.CELL_TYPE_ERROR: // ����
//			value = "";
//			break;
//		default:
//			value = "";
//			break;
//		}
//		return value;
//	}
	public static String getValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
		case NUMERIC: // ����
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
				break;
			} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			break;
		case STRING: // �ַ���
			value = cell.getStringCellValue();
			break;
		case BOOLEAN: // Boolean
			value = cell.getBooleanCellValue() + "";
			break;
		case FORMULA: // ��ʽ
			value = cell.getCellFormula() + "";
			break;
		case BLANK: // ��ֵ
			value = "";
			break;
		case ERROR: // ����
			value = "";
			break;
		default:
			value = "";
			break;
		}
		return value;
	}

	/**
	 * ��ͷ��Ԫ����ʽ
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
//		font.setBold(true);
		font.setFontName("Arial");
		style.setFont(font);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.index);
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.index);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.index);
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.index);
//		// ���ò����г�������
//		style.setWrapText(false);
//		// ����ˮƽ�������ʽΪ���ж���;
		style.setAlignment(HorizontalAlignment.CENTER);
//		// ���ô�ֱ�������ʽΪ���ж���;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// ���õ�Ԫ�񱳾���ɫ
		// RGB(192,192,192)
//		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

		// �õ�palette��ɫ��
		HSSFPalette palette = workbook.getCustomPalette();
		palette.setColorAtIndex(IndexedColors.LIME.index, (byte) 230, (byte) 230, (byte) 230);
		style.setFillForegroundColor(palette.getColor(IndexedColors.LIME.index).getIndex());
//		// �������ͼ��
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return style;
	}

	/**
	 * ��������Ϣ��Ԫ����ʽ
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// ��������
		HSSFFont font = workbook.createFont();
		// ���������СĬ��10
		font.setFontHeightInPoints((short)10);
//		boolean bold = true;
		// ����Ӵ�Ĭ��400
//		font.setBold(bold);
		// ��������
		font.setFontName("΢���ź�");
		// ������ʽ
		HSSFCellStyle style = workbook.createCellStyle();
		// ����ʽ��Ӧ�����õ�����
		style.setFont(font);
		// ���õױ߿�
		style.setBorderBottom(BorderStyle.THIN);
//		// ���õױ߿���ɫ
		style.setBottomBorderColor(IndexedColors.BLACK.index);
//		// ������߿�
		style.setBorderLeft(BorderStyle.THIN);
//		// ������߿���ɫ
		style.setLeftBorderColor(IndexedColors.BLACK.index);
//		// �����ұ߿�
		style.setBorderRight(BorderStyle.THIN);
//		// �����ұ߿���ɫ
		style.setRightBorderColor(IndexedColors.BLACK.index);
//		// ���ö��߿�
		style.setBorderTop(BorderStyle.THIN);
//		// ���ö��߿���ɫ
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		// �����Զ�����
		style.setWrapText(true);
//		// ����ˮƽ�������ʽΪ���ж���
		style.setAlignment(HorizontalAlignment.LEFT);
//		// ���ô�ֱ�������ʽΪ���ж���;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}
	
	/**
	 * �������ʽ
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getLeftStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Courier New");
		style.setFont(font);
		style.setWrapText(false);	// �����Զ�����;
		style.setAlignment(HorizontalAlignment.LEFT);	// ����ˮƽ�������ʽΪ�������;
		// ���ô�ֱ�������ʽΪ���ж���;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}
}