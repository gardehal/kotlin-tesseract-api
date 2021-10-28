package com.tesseract.api.intercept

import java.util.logging.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

class Logging
{
    // https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin

    fun <R : Any> R.logger(): Lazy<Logger>
    {
        return lazy { Logger.getLogger(unwrapCompanionClass(this.javaClass).name) }
    }

    // unwrap companion class to enclosing class given a Java Class
    private fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*>
    {
        return ofClass.enclosingClass?.takeIf {
            ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
        } ?: ofClass
    }

    // unwrap companion class to enclosing class given a Kotlin Class
    fun <T: Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
        return unwrapCompanionClass(ofClass.java).kotlin
    }
}