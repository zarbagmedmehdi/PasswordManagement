/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Bean.DBTable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DaoEngigne {

    private static final List<String> types = new ArrayList<>();

    static {
        types.add("String");
        types.add("int");
        types.add("Integer");
        types.add("float");
        types.add("Float");
        types.add("double");
        types.add("Double");
        types.add("long");
        types.add("Long");
        types.add("boolean");
        types.add("Boolean");
        types.add("Date");
    }
    public static  <T> List<T> selectQuery(Class<T> type,String query) throws SQLException {
        List<T> list = new ArrayList<T>();
        try (Connection conn = DbHelper.GetDatabaseConnection()
        ) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rst = stmt.executeQuery(query)) {
                    while (rst.next()) {
                        T t = type.newInstance();
                        loadResultSetIntoObject(rst, t);// Point 4
                        list.add(t);
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
            }


        return list;
        } }
    public static void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            field.setAccessible(true);
            DBTable column = field.getAnnotation(DBTable.class);
            Object value = rst.getObject(column.columnName());
            Class<?> type = field.getType();
            if (isPrimitive(type)) {//check primitive type(Point 5)
                Class<?> boxed = boxPrimitiveClass(type);//box if primitive(Point 6)
                value = boxed.cast(value);
            }
            field.set(object, value);
        }
    }
    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }
    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

    public static String getAllObejctsByForeignKey(Object obj,String foreinKey,int id,String extra) throws Exception {
        String requette = "select * FROM "
                + transformeToSqlName(obj.getClass()) +
                " WHERE "+foreinKey+"='" + id + "'"
                  +extra;
        return requette;
    }


    public static String constructDaoSaveRequette(Object obj) throws Exception {
        String requette = "INSERT INTO " + transformeToSqlName(obj.getClass()) + "(";
        List<Method> mothods = getGetterList(obj.getClass());
        System.out.println(mothods);
        List<String> params = getParameterName(mothods);
        requette += assembleRequestItem(params, "");
        requette += ") VALUES(";
        requette += assembleRequestItem(lunchGetters(obj), "'");
        requette += ")";
        return requette;
    }

    public static String constructDaoUpdateRequette(Object obj, Object id) throws Exception {
        String requette = "UPDATE  " + transformeToSqlName(obj.getClass()) + " SET ";
        List<Method> mothods = getGetterList(obj.getClass());
        List<String> params = getParameterName(mothods);
        requette += assembleRequestItemForUpdate(params, lunchGetters(obj));
        requette += " WHERE id='" + id + "'";

        return requette;
    }

    public static String constructDaoDeleteRequette(Object obj) throws Exception {
        String requette = "DElETE FROM " + transformeToSqlName(obj.getClass()) + " WHERE id='" + lunchGetter(obj, getId(obj.getClass())) + "'";
        return requette;
    }

    private static String assembleRequestItemForUpdate(List<String> params, List<String> execMethods) {
        String requette = " ";
        for (int i = 0; i < params.size(); i++) {
            requette += "" + params.get(i) + "='" + execMethods.get(i) + "' ";
            if (i != params.size() - 1) {
                requette += ", ";
            }
        }
        return requette;
    }

    private static String assembleRequestItem(List<String> params, String separator) {
        String requette = "";
        for (int i = 0; i < params.size(); i++) {
            requette += separator + "" + params.get(i) + "" + separator;
            if (i != params.size() - 1) {
                requette += ", ";
            }
        }
        return requette;
    }

    public static String transformeToSqlName(Class myclass) {
        return (myclass.getSimpleName().charAt(0) + "").toLowerCase() + myclass.getSimpleName().substring(1);
    }

    public static boolean isGenericType(String type) {
        boolean result = false;
        for (int i = 0; i < types.size(); i++) {
            String myType = types.get(i);
            result = result || type.equals(myType);
        }
        return result;
    }
    //************************************************


    public static void generateDB() throws SQLException {
        try {
            
            generateTables("Bean");
        } catch (Exception ex) {
            Logger.getLogger(DaoEngigne.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void generateTables(String beanPath) throws Exception {

        File file = new File("src/" + beanPath);
        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {
            String myFile = files[i];
            myFile = myFile.replace(".java", "");
            System.out.println(beanPath + "." + myFile);
            Class myClass = Class.forName(beanPath + "." + myFile);

            System.out.println(" haaa file ==> " + myFile);
            System.out.println(constructDaoCreateTableRequette(myClass));
        //    ConnectDB.exec(constructDaoCreateTableRequette(myClass));
        }

    }

    public static String constructDaoCreateTableRequette(Class myClass) throws Exception {
        String requette = "CREATE TABLE IF NOT EXISTS  " + transformeToSqlName(myClass) + "(";
        List<Method> mothods = getGetterList(myClass);
        List<String> params = getParameterName(mothods);
        List<String> typeParams = getTypeParams(mothods);
        System.out.println(" params ==> " + params);
        System.out.println(" type params ==> " + typeParams);

        for (int i = 0; i < typeParams.size(); i++) {
            String myType = typeParams.get(i);
            String param = params.get(i);
            requette += "" + param + " " + myType + " NOT NULL,";
        }
        requette += "PRIMARY KEY (id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;";

        return requette;
    }

    //**********************************************
    public static Object lunchGetter(Object obj, int getterNumber) throws Exception {
        List<Method> methods = getGetterList(obj.getClass());
        if (getterNumber < methods.size()) {
            return lunchGetter(obj, methods.get(getterNumber));
        }

        return null;
    }

    public static Method lunchGetterByName(Object obj, String name) throws Exception {
        Class[] params = new Class[1];
        params[0] = Class.forName("java.lang.String");
        return obj.getClass().getMethod(name, params);
    }

    public static Object lunchGetterByParamName(Object obj, String paramName) throws Exception {
        List<Method> getters = getGetterList(obj.getClass());
        for (int i = 0; i < getters.size(); i++) {
            Method method = getters.get(i);
            if (method.getName().toLowerCase().equals(("get" + paramName).toLowerCase())) {

                return lunchGetter(obj, method);
            }

        }
        return null;
    }

    public static Object lunchGetter(Object objet, Method methodGetter) throws Exception {
        String type = methodGetter.getReturnType().getSimpleName();

        if (isGenericType(type)) {
            return methodGetter.invoke(objet, null);
        } else if (!type.equals("List") && !type.equals("ArrayList")) {
            Object resultatExec = methodGetter.invoke(objet, null);
            return getId(resultatExec.getClass()).invoke(resultatExec, null);
        }
        return null;
    }

    public static Object lunchGetterWithParams(Object objet, Method methodGetter, String param) throws Exception {
        Object[] params = new Object[1];
        params[0] = param;
        return methodGetter.invoke(objet, params);
    }

    public static void lunchSetter(Object objet, Method methodSetter, Object value) throws Exception {
        Object[] objects = new Object[1];
        objects[0] = value;
        methodSetter.invoke(objet, objects);
    }

    private static List<Method> getGetterList(Class myClass) throws Exception {
        Method[] methods = myClass.getMethods();
        List<Method> getters = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (!method.getName().equals("getClass") && !method.getReturnType().getName().equals("java.util.List") && method.getName().startsWith("get")) {
                getters.add(method);
            }

        }

        return getters;
    }

    public static String constructGetterName(String type) {
        String retourn = "";
        if (type.equals("String")) {
            retourn = "String";
        } else if (type.equals("Integer") || type.equals("int")) {
            retourn = "Int";
        } else if (type.equals("Long") || type.equals("long")) {
            retourn = "Long";
        } else if (type.equals("Double") || type.equals("double")) {
            retourn = "Double";
        } else if (type.equals("Folat") || type.equals("float")) {
            retourn = "Folat";
        } else if (type.equals("Boolean") || type.equals("boolean")) {
            retourn = "Boolean";
        } else if (type.equals("Date")) {
            retourn = "Date";
        }
        return "get" + retourn;
    }

    public static Object makeInstance(Class myClass, List<Method> setters, List<Object> values) {
        Object myObject = null;
        try {
            myObject = myClass.newInstance();
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                lunchSetter(myClass, setters.get(i), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myObject;
    }

    public static List<Method> getSetterList(Class myClass) throws Exception {
        Method[] methods = myClass.getMethods();
        List<Method> getters = new ArrayList<>();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("set") && !method.getParameterTypes()[0].getName().equals("java.util.List")) {
                getters.add(method);
            }
        }

        return getters;
    }

    public static Method getId(Class myClass) throws Exception {
        List<Method> methods = getGetterList(myClass);
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("getId")) {
                return method;
            }
        }
        return null;
    }

    public static Method setId(Object obj) throws Exception {
        List<Method> methods = getSetterList(obj.getClass());
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            if (method.getName().equals("setId")) {
                return method;
            }
        }
        return null;
    }

    private static List<String> lunchGetters(Object obj) throws Exception {
        List<Method> methods = getGetterList(obj.getClass());
        List<String> res = new ArrayList<>();
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
			Object object =lunchGetter(obj, method);
			if(object!=null) {
                String type = method.getReturnType().getSimpleName();

                if(type.equals("Date")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
                    java.sql.Date dateDB = new java.sql.Date(((Date)object).getTime());
                    res.add(dateDB.toString());

                }
else
            res.add(object.toString());
            }
		else
			res.add("");
			
        }
        return res;
    }

    public static String getParameterName(Method methodGetter) {
        return (methodGetter.getName().substring(3).charAt(0) + "").toLowerCase() + methodGetter.getName().substring(4);
    }

    public static List<String> getParameterName(List<Method> methodGetters) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Method method = methodGetters.get(i);
            results.add(getParameterName(method));
        }
        return results;
    }

    public static List<String> getTypeParams(List<Method> methodGetters) throws Exception {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Method method = methodGetters.get(i);
            String typeRetour = method.getReturnType().getSimpleName();
            if (!typeJavaToSql(typeRetour).equals("")) {
                results.add(typeJavaToSql(typeRetour));
            } else if (!typeRetour.equals("List") && !typeRetour.equals("ArrayList")) {
                System.out.println("methode ==> " + method.getName());
                System.out.println(" typeee ==> " + method.getReturnType().getName());
                Class retourClass = Class.forName(method.getReturnType().getName());
                results.add(typeJavaToSql(getId(retourClass).getReturnType().getSimpleName()));
            }
        }
        return results;
    }

    private static String typeJavaToSql(String typeRetour) throws Exception {
        if (typeRetour.equals("String")) {
            return ("VARCHAR(255)");
        } else if (typeRetour.equals("int") || typeRetour.equals("Integer")) {
            return ("INT(5)");

        } else if (typeRetour.equals("long") || typeRetour.equals("Long")) {
            return ("INT(10)");

        } else if (typeRetour.equals("Double") || typeRetour.equals("double") || typeRetour.equals("Float") || typeRetour.equals("float")) {
            return ("Double");
        } else if (typeRetour.equals("Date")) {
            return ("Date");
        }
        return "";
    }

    public static List<Class> getParameterType(List<Method> methodGetters) {
        List<Class> results = new ArrayList<>();
        for (int i = 0; i < methodGetters.size(); i++) {
            Class type = methodGetters.get(i).getParameterTypes()[0];
            results.add(type);
        }
        return results;
    }

}
