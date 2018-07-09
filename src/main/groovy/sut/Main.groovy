package sut

class Main {
    static void main(String[] args) {
        println "Impl"
        new Impl().doSomething()
        println "SubImpl"
        new SubImpl().doSomething()
    }
}
