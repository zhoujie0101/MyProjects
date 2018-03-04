package com.jay.json;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by jay on 16/6/24.
 */
public class JSONObject {

    private static final class Null {
        @Override
        public boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return this;
        }

        @Override
        public String toString() {
            return "null";
        }
    }

    private final Map<String, Object> map;
    private static final Object NULL = new Null();

    public JSONObject() {
        this.map = new HashMap<>();
    }

    public JSONObject(JSONObject jo, String[] names) {
        this();
        for(int i = 0; i < names.length; i++) {
            try {
                this.putOnce(names[i], jo.opt(names[i]));
            } catch (Exception e) {

            }
        }
    }

    public JSONObject(Object object) {
        this();
        this.populateMap(object);
    }

    public JSONObject(JSONTokener tokener) {
        this();
        char c;
        String key;
        if(tokener.nextClean() != '{') {
            throw tokener.syntaxError("A JSONObject text must begin with '{'");
        }
        for(;;) {
            c = tokener.nextClean();
            switch (c) {
                case 0:
                    throw tokener.syntaxError("A JSONObject text must be end with '}'");
                case '}':
                    return;
                default:
                    tokener.back();
                    key = tokener.nextValue().toString();
            }
            //key : value, key : value
            c = tokener.nextClean();
            if(c != ':') {
                throw tokener.syntaxError("Expected a ':' after a key");
            }
            this.putOnce(key, tokener.nextValue());
            switch (tokener.nextClean()) {
                case ';':
                case ',':
                    if(tokener.nextClean() == '}') {
                        return;
                    }
                    tokener.back();
                    break;
                case '}':
                    return;
                default:
                    throw tokener.syntaxError("Expected a ',' or '}'");
            }
        }
    }

    public JSONObject(Map<?, ?> anotherMap) {
        this.map = new HashMap<>();
        if(anotherMap != null) {
            for(Map.Entry<?, ?> entry : anotherMap.entrySet()) {
                Object value = entry.getValue();
                if(value != null) {
                    this.map.put(String.valueOf(entry.getKey()), wrap(value));
                }
            }
        }
    }

    public JSONObject(Object object, String[] names) {
        this();
        Class<?> clazz = object.getClass();
        for(int i = 0; i < names.length; i++) {
            try {
                this.putOpt(names[i], clazz.getField(names[i]).get(object));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(String source) {
        this(new JSONTokener(source));
    }

    /**
     * Construct a JSONObject from a ResourceBundle.
     * @param baseName The ResourceBundle base name
     * @param locale The Locale to load the ResourceBundle for
     * @throws  JSONException
     */
    public JSONObject(String baseName, Locale locale) throws JSONException {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale,
                Thread.currentThread().getContextClassLoader());

        Enumeration<String> keys = bundle.getKeys();
        while(keys.hasMoreElements()) {
            Object obj = keys.nextElement();
            if(obj != null) {
                //Go through the path, ensuring that there is a nested JSONObject
                //for each segment except the last. Add the value using the last
                //segment's name into the deepest nested JSONObject.
                String key = (String) obj;
                String[] path = (key).split(".");
                int last = path.length - 1;
                JSONObject target = this;
                for(int i = 0; i < last; i++) {
                    String segment = path[i];
                    JSONObject nextTarget = target.optJSONObject(segment);
                    if(nextTarget == null) {
                        nextTarget = new JSONObject();
                        target.put(segment, nextTarget);
                    }
                    target = nextTarget;
                }
                target.put(path[last], bundle.getString(key));
            }
        }
    }

    public Object get(String key) throws JSONException {
        if(key == null) {
            throw new JSONException("Null key.");
        }

        Object object = this.opt(key);
        if(object == null) {
            throw new JSONException("JSONObject[" + quote(key) + "] not found");
        }

        return object;
    }

    public boolean getBoolean(String key) {
        Object obj = this.get(key);
        if(obj.equals(Boolean.FALSE) || (obj instanceof String &&((String)obj).equalsIgnoreCase("false"))) {
            return false;
        }
        if(obj.equals(Boolean.TRUE) || (obj instanceof String &&((String)obj).equalsIgnoreCase("true"))) {
            return true;
        }

        throw new JSONException("JSONObject[" + quote(key) + "] is not a boolean");
    }

    public BigInteger getBigInteger(String key) throws JSONException{
        Object obj = this.get(key);
        try {
            return new BigInteger(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to BigInteger");
        }
    }

    public BigDecimal getBigDecimal(String key) {
        Object obj = this.get(key);
        try {
            return new BigDecimal(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to BigDecimal");
        }
    }

    public double getDouble(String key) {
        Object obj = this.get(key);
        try {
            return obj instanceof Number ? ((Number)obj).doubleValue() : Double.parseDouble((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to double");
        }
    }

    public int getInt(String key) {
        Object obj = this.get(key);
        try {
            return obj instanceof Number ? ((Number)obj).intValue() : Integer.parseInt((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to int");
        }
    }

    public long getLong(String key) {
        Object obj = this.get(key);
        try {
            return obj instanceof Number ? ((Number)obj).longValue() : Long.parseLong((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to long");
        }
    }

    public JSONArray getJSONArray(String key) {
        Object obj = this.get(key);
        if(obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to JSONArray");
    }

    public JSONObject getJSONObject(String key) {
        Object obj = this.get(key);
        if(obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to JSONObject");
    }

    public String getString(String key) {
        Object obj = this.get(key);
        if(obj instanceof String) {
            return (String) obj;
        }
        throw new JSONException("JSONObject[" + quote(key) + "] could not be converted to String");
    }

    public static String[] getNames(JSONObject jo) {
        int len = jo.length();
        if(len == 0) {
            return null;
        }
        Iterator<String> it = jo.keys();
        String[] names = new String[len];
        int i = 0;
        while(it.hasNext()) {
            names[i++] = it.next();
        }

        return names;
    }

    public static String[] getNames(Object obj) {
        if(obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getFields();
        int len = fields.length;
        String[] names = new String[len];
        for(int i = 0; i < len; i++) {
            names[i] = fields[i].getName();
        }

        return names;
    }

    public boolean has(String key) {
        return this.map.containsKey(key);
    }

    public static String quote(String key) {
        StringWriter sw = new StringWriter();
        synchronized (sw.getBuffer()) {
            return quote(key, sw).toString();
        }
    }

    public static Writer quote(String str, StringWriter sw) {
        if(str == null || str.length() == 0) {
            sw.write("\"\"");
            return sw;
        }

        char b;
        char c = 0;
        String s;
        int len = str.length();
        sw.write('"');
        for(int i = 0; i < len; i++) {
            b = c;
            c = str.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sw.write('\\');
                    sw.write(c);
                    break;
                case '/':
                    if(b == '<') {
                        sw.write('\\');
                    }
                    sw.write(c);
                    break;
                case '\b':
                    sw.write("\\b");
                    break;
                case '\t':
                    sw.write("\t");
                    break;
                case '\n':
                    sw.write("\\n");
                    break;
                case '\f':
                    sw.write("\\f");
                    break;
                case '\r':
                    sw.write("\\r");
                    break;
                default:
                    if(c < ' ' || (c >= '\u0080' && c <= '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
                        sw.write("\\u");
                        s = Integer.toHexString(c);
                        sw.write("0000", 0, 4 - s.length());
                        sw.write(s);
                    } else {
                        sw.write(c);
                    }
            }
        }
        sw.write('"');
        return sw;
    }

    public JSONObject increment(String key) {
        Object obj = this.get(key);
        if(obj == null) {
            this.put(key, 1);
        } else if(obj instanceof BigInteger) {
            this.put(key, ((BigInteger)obj).add(BigInteger.ONE));
        } else if(obj instanceof BigDecimal) {
            this.put(key, ((BigDecimal)obj).add(BigDecimal.ONE));
        } else if(obj instanceof Integer) {
            this.put(key, (Integer)obj + 1);
        } else if(obj instanceof Long) {
            this.put(key, (Long)obj + 1);
        } else if(obj instanceof Double) {
            this.put(key, (Double)obj + 1);
        } else if(obj instanceof Float) {
            this.put(key, (Float)obj + 1);
        } else {
            throw new JSONException("Unable to increment [" + quote(key) + "]");
        }

        return this;
    }

    public boolean isNull(String key) {
        return JSONObject.NULL.equals(this.opt(key));
    }

    public Iterator<String> keys() {
        return this.keySet().iterator();
    }

    private Set<String> keySet() {
        return this.map.keySet();
    }

    public int length() {
        return this.map.size();
    }

    private JSONObject optJSONObject(String key) {
        Object obj = this.opt(key);

        return obj instanceof JSONObject ? (JSONObject) obj : null;
    }

    private JSONObject putOpt(String key, Object value) {
        if(key != null && value != null) {
            this.put(key, value);
        }
        return this;
    }

    private Object wrap(Object object) {
        try {
            if(object == null) {
                return NULL;
            }
            if(object instanceof JSONObject || object instanceof JSONArray
                    || NULL.equals(object) || object instanceof JSONString
                    || object instanceof Byte || object instanceof Character
                    || object instanceof Short || object instanceof Integer
                    || object instanceof Long || object instanceof Boolean
                    || object instanceof Float || object instanceof Double
                    || object instanceof String || object instanceof BigInteger
                    || object instanceof BigDecimal) {
                return object;
            }
            if(object instanceof Collection) {
                Collection<?> coll = (Collection<?>) object;
                return new JSONArray(coll);
            }
            if(object.getClass().isArray()) {
                return new JSONArray(object);
            }
            if(object instanceof Map) {
                Map<?, ?> aMap = (Map<?, ?>) object;
                return new JSONObject(aMap);
            }
            Package pkg = object.getClass().getPackage();
            String pkgName = pkg != null ? pkg.getName() : "";
            if(pkgName.startsWith("java.") || pkgName.startsWith("javax.")
                    //system class
                    || object.getClass().getClassLoader() == null) {
                return object.toString();
            }

            return new JSONObject(object);
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject putOnce(String key, Object value) throws JSONException {
        if(key != null && value != null) {
            if(this.opt(key) != null) {
                throw new JSONException("Duplicate key \"" + key + "\"");
            }
            this.put(key, value);
        }

        return this;
    }

    private void populateMap(Object object) {
        Class<?> klass = object.getClass();
        boolean includeSuperClass = klass.getClassLoader() != null;
        Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
        for(int i = 0; i < methods.length; i++) {
            try {
                Method method = methods[i];
                if(Modifier.isPublic(method.getModifiers())) {
                    String name = method.getName();
                    String key = "";
                    if(name.startsWith("get")) {
                        if("getClass".equals(name) || "getDeclaringClass".equals(name)) {
                            key = "";
                        } else {
                            key = name.substring(3);
                        }
                    } else if(name.startsWith("is")) {
                        key = name.substring(2);
                    }
                    if(key.length() > 0 && Character.isUpperCase(key.charAt(0))
                            && method.getParameterTypes().length == 0) {
                        if(key.length() == 1) {
                            key = key.toLowerCase();
                        } else if(!Character.isUpperCase(key.charAt(1))) {
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                        }

                        Object result = method.invoke(object, (Object[]) null);
                        if(result != null) {
                            this.map.put(key, wrap(result));
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void testValidity(Object obj) throws JSONException{
        if(obj != null) {
            if(obj instanceof Double) {
                if(((Double)obj).isInfinite() || ((Double)obj).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers");
                }
            } else if(obj instanceof Float) {
                if(((Float)obj).isInfinite() || ((Float)obj).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers");
                }
            }
        }
    }

    private void put(String key, Object value) {
        this.map.put(key, value);
    }

    private Object opt(String key) {
        return key == null ? null : this.map.get(key);
    }

    public static Object stringToValue(String str) {
        if(str == null || str.equals("")) {
            return str;
        }
        if(str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if(str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if(str.equalsIgnoreCase("null")) {
            return JSONObject.NULL;
        }

        char initial = str.charAt(0);
        if((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                if(str.indexOf('.') > -1 || str.indexOf('e') > -1 || str.indexOf('E') > -1 || "-0".equals(str)) {
                    Double d = Double.parseDouble(str);
                    if(!d.isInfinite() || d.isNaN()) {
                        return d;
                    }
                } else {
                    Long myLong = new Long(str);
                    if(str.equals(myLong.toString())) {
                        if(myLong.longValue() == myLong.intValue()) {
                            return Integer.valueOf(myLong.intValue());
                        }
                        return myLong;
                    }
                }
            } catch (Exception e) {
            }
        }

        return str;
    }
}
