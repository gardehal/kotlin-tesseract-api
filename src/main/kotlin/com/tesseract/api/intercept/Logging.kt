package com.tesseract.api.intercept

import java.util.logging.Logger
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

// https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin

// Return logger for Java class, if companion object fix the name
fun <T: Any> logger(forClass: Class<T>): Logger
{
    return Logger.getLogger(unwrapCompanionClass(forClass).name)
}

// unwrap companion class to enclosing class given a Java Class
fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*>
{
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}

// unwrap companion class to enclosing class given a Kotlin Class
fun <T: Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*>
{
    return unwrapCompanionClass(ofClass.java).kotlin
}

// Return logger for Kotlin class
fun <T: Any> logger(forClass: KClass<T>): Logger
{
    return logger(forClass.java)
}

// return logger from extended class (or the enclosing class)
fun <T: Any> T.logger(): Logger
{
    return logger(this.javaClass)
}

// return a lazy logger property delegate for enclosing class
fun <R : Any> R.lazyLogger(): Lazy<Logger>
{
    return lazy { logger(this.javaClass) }
}

// return a logger property delegate for enclosing class
fun <R : Any> R.injectLogger(): Lazy<Logger>
{
    return lazyOf(logger(this.javaClass))
}

// marker interface and related extension (remove extension for Any.logger() in favour of this)
interface Loggable {}
fun Loggable.logger(): Logger = logger(this.javaClass)

// abstract base class to provide logging, intended for companion objects more than classes but works for either
abstract class WithLogging: Loggable
{
    val LOG = logger()
}

// https://karimvarela.com/2012/06/12/android-how-to-include-line-numbers-class-names-and-method-names-in-your-log-statements/

fun <R : Logger> R.exception(e: Exception, printStackTrace: Boolean = false, stackTraceLevelsUp: Int = 3): Unit
{
    val caller = Thread.currentThread().stackTrace[stackTraceLevelsUp]
    this.severe("Exception in method ${caller.methodName} at line ${caller.lineNumber}: ${e.message}")

    if(printStackTrace)
        e.printStackTrace()
}