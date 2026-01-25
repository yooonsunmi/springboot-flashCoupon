package com.yoonsunmi.timetracking.global.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ExcelUtil {

    //엑셀 데이터 저장용 class
    @Getter
    public static class ExcelData implements AutoCloseable {

        @Getter
        public static class SheetData {

            private final Map<String, List<String>> map = new LinkedHashMap<>();

            @Setter
            private String name;

            public void addData(String key, String data) {
                if(map.containsKey(key)) {
                    map.get(key).add(data);
                } else {
                    map.put(key, new ArrayList<>(List.of(data != null ? data : "")));
                }
            }

            public void addDataAll(String key, List<String> datas) {
                if(map.containsKey(key)) {
                    map.get(key).addAll(datas);
                } else {
                    map.put(key, datas);
                }
            }

        }

        private final Workbook workbook = new XSSFWorkbook();

        private List<SheetData> sheetDatas = new ArrayList<>();

        @Setter
        private String name;

        public void addData(SheetData data) {
            this.sheetDatas.add(data);
        }

        @Override
        public void close() throws Exception {
            workbook.close();
        }

    }
    
    //저장된 데이터를 column 단위로 엑셀 데이터로 변환
    public static void generateExcelData(ExcelData data) {
        for (ExcelData.SheetData sheetData : data.getSheetDatas()) {

            Sheet sheet = data.getWorkbook().createSheet(sheetData.getName());
            sheet.createRow(0);

            int cIdx = 0;
            for (String key : sheetData.getMap().keySet()) {

                sheet.getRow(0).createCell(cIdx).setCellValue(key);

                int rIdx = 1;
                for (String value : sheetData.getMap().get(key)) {

                    Row row = sheet.getRow(rIdx) != null ? sheet.getRow(rIdx) : sheet.createRow(rIdx);
                    row.createCell(cIdx).setCellValue(value != null ? value : "");

                    rIdx++;

                }

                cIdx++;

            }

        }
    }

}
