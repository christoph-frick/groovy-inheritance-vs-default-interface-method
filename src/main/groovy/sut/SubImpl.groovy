package sut

import groovy.transform.CompileStatic

@CompileStatic
class SubImpl extends Impl implements Intr {
    @Override
    void doSomething() {
        super.doSomething()
        println "Impl"
    }
}
