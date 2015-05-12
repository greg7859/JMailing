package org.jmailing.io.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jmailing.injector.provider.DataProvider;
import org.jmailing.model.source.Data;

public class ExcelFileReader implements DataFileReader {

	@Inject
	DataProvider provider;

	@Override
	public List<Data> readAll(String filename, boolean ignoreFirstLine)
			throws IOException {
		return read(filename, Integer.MAX_VALUE, ignoreFirstLine);
	}

	@Override
	public List<Data> read(String filename, int nbItemToRead,
			boolean ignoreFirstLine) throws IOException {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sh = wb.getSheetAt(0);
		int nbRow = sh.getLastRowNum();
		int maxCol = 0;
		for (int i = 0; i < nbRow; i++) {
			XSSFRow row = sh.getRow(i);
			if (row != null) {
				maxCol = Math.max(maxCol, row.getPhysicalNumberOfCells());
			}
		}

		List<Data> dataColl = new ArrayList<>();
		Data data = null;
		String val;
		for (int i = 0; i < nbRow && i<nbItemToRead; i++) {
			if (ignoreFirstLine == false || (ignoreFirstLine == true && i != 0)) {
				XSSFRow row = sh.getRow(i);
				if (row != null) {
					data = provider.get();
					dataColl.add(data);
					int nbOfCol = row.getPhysicalNumberOfCells();
					int iCol = 0;
					for (; iCol < nbOfCol; iCol++) {
						val = "";
						XSSFCell cell = row.getCell(iCol);
						if (cell != null) {
							int cellType = cell.getCellType();
							if (cellType == Cell.CELL_TYPE_STRING) {
								val = cell.getStringCellValue();
							} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
								double numericCellValue = cell
										.getNumericCellValue();
								val = String.valueOf(numericCellValue);
							} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
								boolean booleanCellValue = cell
										.getBooleanCellValue();
								val = String.valueOf(booleanCellValue);
							}
						}
						data.add(val);

					}
					for (; iCol < maxCol; iCol++) {
						data.add("");
					}
				}
			}
		}
		wb.close();
		return dataColl;
	}
}
