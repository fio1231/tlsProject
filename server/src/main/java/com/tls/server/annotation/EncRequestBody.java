package com.tls.server.annotation;

import java.lang.annotation.*;

/**
 * ==========================================================
 * PackageName : com.tls.server.annotation
 * FileName    : EncRequestBody
 * Author      : 이진우
 * Date        : 25. 4. 7.
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 25. 4. 7.    |    이진우    |    최초생성
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncRequestBody {
    String value() default "data";
}
