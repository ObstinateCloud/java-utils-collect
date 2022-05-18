package utils.bean;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author potatomato
 */
public class ObjectUtil {

    /**
     * 将一个对象中的值传到目标对象响应的属性中
     *
     * @param source 源对象
     * @param target 目标对象
     * @throws Exception
     */
    public static void transValues(Object source, Object target) {
        // 获得实体类
        Class<?> clazz = source.getClass();
        Class<?> clazz1 = target.getClass();
        // 查看有那些字段
        List<Field> fields = new ArrayList<>();
        List<Field> fields2 = new ArrayList<>();
        //获取非静态属性
        getAllField(clazz, fields);
        getAllField(clazz1, fields2);

        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields2) {
            fieldMap.put(field.getName(), field);
        }
        // 遍历
        for (Field field : fields) {
            try {
                // 属性的名字
                String fieldName = field.getName();
                // 属性的类型
                String getMethodName = "get" + decapitalize(fieldName);
                Method getMethod = clazz.getMethod(getMethodName);
                // 判断model的哪个属性是否为空,利用get方法
                Object object = getMethod.invoke(source);
                if (object != null) {
                    if (fieldMap.get(fieldName) != null) {
                        // 不为空就进行赋值
                        // 通过属性名，来获取对应的setXXX的名字
                        String setMethodName = "set" + decapitalize(fieldName);
                        // 这是set方法
                        Method setMethod = clazz1.getMethod(setMethodName, field.getType());
                        // 这里需要进行类型的强制转换吗?
                        setMethod.invoke(target, object);
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            }
        }
    }

    /**
     * 将List里对象复制给目标对象并返回目标对象list
     *
     * @param source 原类型列表
     * @param target 目标类型
     * @return 目标类型列表
     * @throws Exception
     */
    public static <E, T> List<T> copyListProperties(List<E> source, Class<T> target) {
        List<T> tagetList = new ArrayList<>();
        if (source != null) {
            for (E e : source) {
                try {
                    T t = target.newInstance();
                    transValues(e, t);
                    tagetList.add(t);
                } catch (InstantiationException | IllegalAccessException instantiationException) {
                    instantiationException.printStackTrace();
                }
            }
        }
        return tagetList;
    }

    /**
     * 获取类中的所有非静态字段
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    private static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new ArrayList<>();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            // 排除静态字段
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)) {
            return getAllField(superClass, fieldList);
        }
        return fieldList;
    }

    /**
     * getter,setter属性处理
     *
     * @param s
     * @return
     */
    private static String decapitalize(String s) {
        if (s == null || s.length() == 0) {
            // 空处理
            return s;
        }
        if (s.length() > 1 && Character.isUpperCase(s.charAt(1)) && Character.isUpperCase(s.charAt(0))) {
            // 长度大于1，并且前两个字符大写时，返回原字符串
            return s;
        } else if (s.length() > 1 && Character.isUpperCase(s.charAt(1)) && Character.isLowerCase(s.charAt(0))) {
            // 长度大于1，并且第一个字符小写，第二个字符大写时，返回原字符串
            return s;
        } else if (Character.isUpperCase(s.charAt(0))) {
            //如果首字母大写，返回原字符
            return s;
        } else {
            // 其他情况下，把原字符串的首个字符大写处理后返回
            char ac[] = s.toCharArray();
            ac[0] = Character.toUpperCase(ac[0]);
            return new String(ac);
        }
    }
}
