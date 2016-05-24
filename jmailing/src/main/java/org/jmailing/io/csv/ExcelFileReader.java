package org.jmailing.io.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
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
		int nbRow = sh.getLastRowNum() + 1;
		int maxCol = 0;
		for (int i = 0; i < nbRow; i++) {
			XSSFRow row = sh.getRow(i);
			if (row != null) {
				maxCol = Math.max(maxCol, row.getPhysicalNumberOfCells());
			}
		}
		final FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();
		List<Data> dataColl = new ArrayList<>();
		Data data = null;
		String val;
		for (int i = 0; i < nbRow && i < nbItemToRead; i++) {
			if (ignoreFirstLine == false || (ignoreFirstLine == true && i != 0)) {
				XSSFRow row = sh.getRow(i);
				if (row != null) {
					data = provider.get();
					dataColl.add(data);
					int nbOfCol = row.getPhysicalNumberOfCells();
					int iCol = 0;
					for (; iCol < nbOfCol; iCol++) {
						XSSFCell cell = row.getCell(iCol);
						val = getCellValue(cell, evaluator);
						data.add(val);
					}
					for (; iCol < maxCol; iCol++) {
						data.add("");
					}
					// Check if data is empty.
					boolean empty = true;
					for (iCol = 0; iCol < maxCol && empty; iCol++) {
						empty = StringUtils.isBlank(data.get(iCol));
					}
					if (empty)
						dataColl.remove(data);
				}
			}
		}
		wb.close();
		return dataColl;
	}

	private String getCellValue(XSSFCell cell, FormulaEvaluator evaluator) {
		String val = "";
		if (cell != null) {
			int cellType = cell.getCellType();
			val = getCellValue(cellType, cell, evaluator);
		}
		return val;
	}

	private String getCellValue(int cellType, XSSFCell cell,
			FormulaEvaluator evaluator) {
		String val = "";
		if (cell != null) {
			if (cellType == Cell.CELL_TYPE_STRING) {
				val = cell.getStringCellValue();
			} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
				double numericCellValue = cell.getNumericCellValue();
				// test if a date!
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					val = sdf.format(HSSFDateUtil.getJavaDate(numericCellValue));
				} else {
					Double d = new Double(numericCellValue);
					long l = d.longValue();
					Double compD = (double) l;
					if (compD.equals(d)) {
						val = Long.toString(l);
					} else {
						val = Double.toString(d);
					}
				}
			} else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
				boolean booleanCellValue = cell.getBooleanCellValue();
				val = String.valueOf(booleanCellValue);
			} else if (cellType == Cell.CELL_TYPE_FORMULA) {
				// Re-run based on the formula type
				try {
					evaluator.evaluateFormulaCell(cell);
					val = getCellValue(cell.getCachedFormulaResultType(), cell,
							evaluator);
				} catch (FormulaParseException e) {
				}
			}
		}
		return val;
	}
}
