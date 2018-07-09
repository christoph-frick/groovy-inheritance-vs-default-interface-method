package sut

import groovy.transform.CompileStatic

@CompileStatic
class SubImpl extends Impl {
    @Override
    void doSomething() {
        super.doSomething()
        println "Impl"
    }
}
