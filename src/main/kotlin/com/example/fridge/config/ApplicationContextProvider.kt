package com.example.fridge.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationContextProvider : ApplicationContextAware {

    companion object {
        private var context: ApplicationContext? = null
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    fun applicationContext(): ApplicationContext = context!!
}