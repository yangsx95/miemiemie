package com.miemiemie.starter.core.util;

import com.miemiemie.starter.core.exception.BizException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author yangshunxiang
 * @since 2024/8/16
 */
@SuppressWarnings("unused")
public final class AssertUtil {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BizException(message);
        }
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new BizException(message);
        }
    }

    public static void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BizException(message);
        }
    }

    public static void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new BizException(message);
        }
    }

    public static void hasLength(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasLength(text)) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(message);
        }
    }

    public static void hasText(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BizException(message);
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BizException(message);
        }
    }

    public static void notEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            int var3 = array.length;

            for (Object element : array) {
                if (element == null) {
                    throw new BizException(message);
                }
            }
        }

    }

    public static void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            int var3 = array.length;

            for (Object element : array) {
                if (element == null) {
                    throw new BizException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(message);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {

            for (Object element : collection) {
                if (element == null) {
                    throw new BizException(message);
                }
            }
        }

    }

    public static void noNullElements(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection != null) {

            for (Object element : collection) {
                if (element == null) {
                    throw new BizException(nullSafeGet(messageSupplier));
                }
            }
        }

    }

    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BizException(message);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BizException(nullSafeGet(messageSupplier));
        }
    }

    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }

    }

    public static void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<String> messageSupplier) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }

    }

    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }

    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        notNull(superType, "Supertype to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }

    }

    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, "Supertype to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }

    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = obj != null ? obj.getClass().getName() : "null";
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + "Object of class [" + className + "] must be an instance of " + type;
        }

        throw new BizException(result);
    }

    private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + subType + " is not assignable to " + superType;
        }

        throw new BizException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
    }

    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return messageSupplier != null ? messageSupplier.get() : null;
    }

}
