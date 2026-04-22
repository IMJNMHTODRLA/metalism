package _RedGold__.main.functions

object FastBoolean {
    inline fun Boolean.trueRun(block: () -> Unit): Boolean {
        if (this) block()
        return this
    }

    inline fun Boolean.falseRun(block: () -> Unit): Boolean {
        if (!this) block()
        return this
    }
}

inline fun <T> Boolean.ifRun(block: () -> T): ConditionChain<T> {
    return if (this) {
        ConditionChain(block(), true)
    } else {
        ConditionChain(null, false)
    }
}

class ConditionChain<T>(val value: T?, val isProcessed: Boolean) {
    inline fun elseIfRun(condition: Boolean, block: () -> T): ConditionChain<T> {
        if (!isProcessed && condition) {
            return ConditionChain(block(), true)
        }
        return this
    }

    inline fun elseRun(block: () -> T): T {
        return if (!isProcessed) {
            block()
        } else {
            value as T
        }
    }
}