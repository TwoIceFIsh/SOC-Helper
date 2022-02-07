package ioc;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class functions {

	
	//Excel ������ �о���δ�

	public void readExcel_igloo(String PATH) {
		System.out.println("Excel �м�");
		try {
			FileInputStream fis = new FileInputStream(PATH);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ(Sheet) ��
			int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
			for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
				HSSFRow row = sheet.getRow(rowIndex); // �� ���� �о�´�
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
						HSSFCell cell = row.getCell(columnIndex); // ���� ����ִ� ���� �д´�.
						String value = "";
						switch (cell.getCellType()) { // �� ���� ����ִ� �������� Ÿ���� üũ�ϰ� �ش� Ÿ�Կ� �°� �����´�.
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
			HSSFSheet sheet = workbook.getSheetAt(0); // �ش� ���������� ��Ʈ(Sheet) ��
			int rows = sheet.getPhysicalNumberOfRows(); // �ش� ��Ʈ�� ���� ����
			for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
				HSSFRow row = sheet.getRow(rowIndex); // �� ���� �о�´�
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
						HSSFCell cell = row.getCell(columnIndex); // ���� ����ִ� ���� �д´�.
						String value = "";
						switch (cell.getCellType()) { // �� ���� ����ִ� �������� Ÿ���� üũ�ϰ� �ش� Ÿ�Կ� �°� �����´�.
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
