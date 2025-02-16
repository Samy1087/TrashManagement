package ui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UtilsFonction {

    public static <T> void displayDataInTable(List<T> dataList, JTable jTable) {
        displayDataInTable(dataList, jTable, new ArrayList<>());
    }

    public static <T> void displayDataInTable(List<T> dataList, JTable jTable, List<String> columnsToOmit) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        Class<?> itemClass = dataList.get(0).getClass();
        Field[] allFields = itemClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : allFields) {
            if (!Modifier.isStatic(field.getModifiers()) && !columnsToOmit.contains(field.getName())) {
                fields.add(field);
            }
        }

        String[] columnNames = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i] = camelCaseToText(fields.get(i).getName());
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (T item : dataList) {
            Object[] rowData = new Object[fields.size()];
            for (int i = 0; i < fields.size(); i++) {
                try {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    Object fieldValue = field.get(item);

                    if (fieldValue instanceof Date) {
                        rowData[i] = dateFormat.format((Date) fieldValue);
                    } else {
                        rowData[i] = fieldValue;
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }
        jTable.setModel(model);
    }

    public static String camelCaseToText(String camelCaseString) {
        if (camelCaseString == null || camelCaseString.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(camelCaseString.charAt(0)));

        for (int i = 1; i < camelCaseString.length(); i++) {
            char currentChar = camelCaseString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append(' ');
                result.append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    public static String encrypt(String str) {
        int code;
        StringBuilder result = new StringBuilder();
        final String SEPARATOR = ";";
        for (int i = 0; i < str.length(); i++) {
            code = Math.round((float) Math.random() * 8 + 1);
            result.append(code).append(Integer.toHexString(((int) str.charAt(i)) ^ code)).append(SEPARATOR);
        }
        return result.substring(0, result.length() - SEPARATOR.length());
    }

    public static String decrypt(String str) {
        final String SEPARATOR = ";";
        String[] tokens = str.split(SEPARATOR);
        StringBuilder result = new StringBuilder();
        for (String token : tokens) {
            String hex = token.substring(1);
            result.append((char) (Integer.parseInt(hex, 16) ^ (Integer.parseInt(String.valueOf(token.charAt(0))))));
        }
        return result.toString();
    }

    static void displayDataInTable(DefaultTableModel model, JTable tableau, List<String> of) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
