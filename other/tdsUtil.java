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
	 * 导出数据
	 * @param rowName
	 * @param dataList
	 * @param workbook
	 * @param sheet
	 * @throws Exception
	 */
	public static void exportExcel(String[] rowName, List<String[]> dataList, HSSFWorkbook workbook, HSSFSheet sheet)
			throws Exception {
		try {
			// 创建单元格样式对象
			HSSFCellStyle style = MUtil.getStyle(workbook); 
			// 标题行单元格样式
			HSSFCellStyle columnTopStyle = MUtil.getColumnTopStyle(workbook);
			
			for (int i = 0; i < dataList.size(); i++) {
				// 创建行对象
				HSSFRow row = sheet.createRow(i);
				// 给每一行设置高度
				if(i == 0)
					row.setHeight((short) 500);
				else
					// 20磅
					row.setHeight((short) 400);
				
				Object[] obj = dataList.get(i);
				// 将数据设置到sheet对应的单元格中
				for (int j = 0; j < obj.length; j++) {
					// 创建字符串类型单元格
//					HSSFCell cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					HSSFCell cell = row.createCell(j);
					if(i == 0) 
						// 设置表格首行样式
						cell.setCellStyle(columnTopStyle);
					else
						// 设置单元格样式
						cell.setCellStyle(style);
					
					if (obj[j] == null)
						// 对象为null则置为空
						cell.setCellValue("");
					else
						// 设置单元格的值
						cell.setCellValue(obj[j].toString()); 
				}
			}
			// 总列数
			int columnNum = rowName.length;
			// 让列宽随着导出的列长自动适应（一列中的每行数值所占的最大长度）
			for (int colNum = 0; colNum < columnNum; colNum++) {
				// 默认列宽8个字符
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				// 不循环首行列名，取含有数值的首行设置列宽
//				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				// 从首行开始，根据每一列前两行单元格数据的最长字符长度设置该列列宽
				for (int rowNum = 0; rowNum < 2; rowNum++) {
					HSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					HSSFCell currentCell = currentRow.getCell(colNum);
					if (currentCell != null) {
						// 获取单元格的数据
						String value = MUtil.getValue(currentCell);
						// 取单元格数据的操作系统默认编码格式的字符长度
						int length = value.getBytes().length;
						if (length > columnWidth) {
							if(length > 34) {
								// 设置ID长度34字符为最长宽度
								length = 34;
							}
							columnWidth = length;
							// 根据每一列前两行单元格数据的最长字符长度设置该列列宽
//							if(rowNum > 1)
//								break;
						}
					}
				}
				// 给每一列设置宽度
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}
			// 不写入
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
	 * 读取Excel的sheet存入JSONArray
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

			
			// POI操作Excel文件，通过文件流判断Excel的版本
//			if (POIFSFileSystem.hasPOIFSHeader(bis)) { // 2003及以下
//				wb = new HSSFWorkbook(bis);
//			} else if (POIXMLDocument.hasOOXMLHeader(bis)) { // 2007及以上
//				wb = new XSSFWorkbook(bis);
//			} else
//				throw new IllegalArgumentException("excel解析失败！");
			
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
		// 只读第一个sheet
//		numberOfSheets = 1;
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
							jsonObject.put("key" + cIndex, MUtil.getValue(cell));
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
		return arrayList;
	}

	/**
	 * 获取单元格值
	 * @param cell
	 * @return
	 */
//	public static String getValue(Cell cell) {
//		String value = "";
//		switch (cell.getCellType()) {
//		case HSSFCell.CELL_TYPE_NUMERIC: // 数字
//			if (HSSFDateUtil.isCellDateFormatted(cell)) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
//				break;
//			} else {
//				value = new DecimalFormat("0").format(cell.getNumericCellValue());
//			}
//			break;
//		case HSSFCell.CELL_TYPE_STRING: // 字符串
//			value = cell.getStringCellValue();
//			break;
//		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
//			value = cell.getBooleanCellValue() + "";
//			break;
//		case HSSFCell.CELL_TYPE_FORMULA: // 公式
//			value = cell.getCellFormula() + "";
//			break;
//		case HSSFCell.CELL_TYPE_BLANK: // 空值
//			value = "";
//			break;
//		case HSSFCell.CELL_TYPE_ERROR: // 故障
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
		case NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
				break;
			} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			break;
		case STRING: // 字符串
			value = cell.getStringCellValue();
			break;
		case BOOLEAN: // Boolean
			value = cell.getBooleanCellValue() + "";
			break;
		case FORMULA: // 公式
			value = cell.getCellFormula() + "";
			break;
		case BLANK: // 空值
			value = "";
			break;
		case ERROR: // 故障
			value = "";
			break;
		default:
			value = "";
			break;
		}
		return value;
	}

	/**
	 * 列头单元格样式
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
//		// 设置不换行超出隐藏
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
//		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置单元格背景颜色
		// RGB(192,192,192)
//		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

		// 拿到palette颜色板
		HSSFPalette palette = workbook.getCustomPalette();
		palette.setColorAtIndex(IndexedColors.LIME.index, (byte) 230, (byte) 230, (byte) 230);
		style.setFillForegroundColor(palette.getColor(IndexedColors.LIME.index).getIndex());
//		// 设置填充图案
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return style;
	}

	/**
	 * 列数据信息单元格样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 创建字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小默认10
		font.setFontHeightInPoints((short)10);
//		boolean bold = true;
		// 字体加粗默认400
//		font.setBold(bold);
		// 设置字体
		font.setFontName("微软雅黑");
		// 创建样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 在样式用应用设置的字体
		style.setFont(font);
		// 设置底边框
		style.setBorderBottom(BorderStyle.THIN);
//		// 设置底边框颜色
		style.setBottomBorderColor(IndexedColors.BLACK.index);
//		// 设置左边框
		style.setBorderLeft(BorderStyle.THIN);
//		// 设置左边框颜色
		style.setLeftBorderColor(IndexedColors.BLACK.index);
//		// 设置右边框
		style.setBorderRight(BorderStyle.THIN);
//		// 设置右边框颜色
		style.setRightBorderColor(IndexedColors.BLACK.index);
//		// 设置顶边框
		style.setBorderTop(BorderStyle.THIN);
//		// 设置顶边框颜色
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		// 设置自动换行
		style.setWrapText(true);
//		// 设置水平对齐的样式为居中对齐
		style.setAlignment(HorizontalAlignment.LEFT);
//		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}
	
	/**
	 * 左对齐样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getLeftStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Courier New");
		style.setFont(font);
		style.setWrapText(false);	// 设置自动换行;
		style.setAlignment(HorizontalAlignment.LEFT);	// 设置水平对齐的样式为靠左对齐;
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}
}