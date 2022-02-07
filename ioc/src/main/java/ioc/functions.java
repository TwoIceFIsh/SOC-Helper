package ioc;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class functions {

	
	//Excel 파일을 읽어들인다

	public void readExcel_igloo(String PATH) {
		System.out.println("Excel 분석");
		try {
			FileInputStream fis = new FileInputStream(PATH);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
			int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
			for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
				HSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
						HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
						String value = "";
						switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고 해당 타입에 맞게 가져온다.
						case HSSFCell.CELL_TYPE_NUMERIC:
							value = cell.getNumericCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = cell.getBooleanCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = cell.getErrorCellValue() + "";
							break;
						}
						System.out.println(value);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	void readExcel_custom(int ROW, int COLUMN, String PATH) {
		try {
			FileInputStream fis = new FileInputStream(PATH);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0); // 해당 엑셀파일의 시트(Sheet) 수
			int rows = sheet.getPhysicalNumberOfRows(); // 해당 시트의 행의 개수
			for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
				HSSFRow row = sheet.getRow(rowIndex); // 각 행을 읽어온다
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
						HSSFCell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
						String value = "";
						switch (cell.getCellType()) { // 각 셀에 담겨있는 데이터의 타입을 체크하고 해당 타입에 맞게 가져온다.
						case HSSFCell.CELL_TYPE_NUMERIC:
							value = cell.getNumericCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = cell.getBooleanCellValue() + "";
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = cell.getErrorCellValue() + "";
							break;
						}
						System.out.println(value);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
