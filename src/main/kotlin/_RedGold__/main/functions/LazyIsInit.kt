package _RedGold__.main.functions

import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

val KProperty0<*>.isInit: Boolean
    get() {
        this.isAccessible = true
        return (this.getDelegate() as? Lazy<*>)?.isInitialized()?: true
    }