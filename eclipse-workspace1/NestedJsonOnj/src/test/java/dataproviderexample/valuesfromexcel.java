package dataproviderexample;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class valuesfromexcel {

	public static Object[][] gettestdata() throws IOException {
		// public static void main(String str[]) throws IOException{

		String path = "C:/Users/Aditya/Desktop/Data.xlsx";

		FileInputStream fis = new FileInputStream(path);

		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheetAt(0);

		int totalrows = sheet.getLastRowNum();
		Row row = sheet.getRow(1);
		int totalcolumn = row.getLastCellNum();

		Object[][] data = new Object[totalrows][totalcolumn];

		for (int i = 0; i < totalrows; i++) {

			for (int j = 0; j < totalcolumn; j++) {

				try {
					data[i][j] = sheet.getRow(i).getCell(j).getStringCellValue();
			
				}

				catch (NullPointerException ex)

				{
					System.out.println(ex.getMessage());
				}
			}

		}
		workbook.close();
		return data;

	}

}
