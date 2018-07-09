# Example code to show calling indirect default interface methods failing with Groovy

The `master` branch show the error case:

```
$ git checkout master
$ ./gradlew clean run
> Task :compileJava UP-TO-DATE
> Task :compileGroovy UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE

> Task :run FAILED
Impl
Intr
SubImpl
Exception in thread "main" groovy.lang.MissingMethodException: No signature of method: sut.SubImpl.doSomething() is applicable for argument types: () values: []
Possible solutions: doSomething(), toString(), toString()
	at org.codehaus.groovy.runtime.ScriptBytecodeAdapter.unwrap(ScriptBytecodeAdapter.java:72)
	at org.codehaus.groovy.runtime.ScriptBytecodeAdapter.invokeMethodOnSuperN(ScriptBytecodeAdapter.java:148)
	at org.codehaus.groovy.runtime.ScriptBytecodeAdapter.invokeMethodOnSuper0(ScriptBytecodeAdapter.java:166)
	at sut.SubImpl.doSomething(SubImpl.groovy:6)
	at sut.Intr$doSomething.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:120)
	at sut.Main.main(Main.groovy:8)

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':run'.
> Process 'command '/usr/lib/jvm/zulu-8-amd64/bin/java'' finished with non-zero exit value 1

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 0s
3 actionable tasks: 1 executed, 2 up-to-date
```

Using `@CompileStatic` (see branch `compilestatic`) for the following error:

```
$ git checkout compilestatic
$ ./gradlew clean run

> Task :compileJava UP-TO-DATE
> Task :compileGroovy UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE

> Task :run FAILED
Impl
Intr
SubImpl
Exception in thread "main" java.lang.VerifyError: Bad invokespecial instruction: interface method reference is in an indirect superinterface.
Exception Details:
  Location:
    sut/SubImpl.doSomething()V @1: invokespecial
  Reason:
    Error exists in the bytecode
  Bytecode:
    0x0000000: 2ab7 0014 0157 2a12 16b8 001c 0157 b1  

	at java.lang.Class.getDeclaredConstructors0(Native Method)
	at java.lang.Class.privateGetDeclaredConstructors(Class.java:2671)
	at java.lang.Class.getDeclaredConstructors(Class.java:2020)
	at org.codehaus.groovy.reflection.CachedClass$2$1.run(CachedClass.java:88)
	at java.security.AccessController.doPrivileged(Native Method)
	at org.codehaus.groovy.reflection.CachedClass$2.initValue(CachedClass.java:86)
	at org.codehaus.groovy.reflection.CachedClass$2.initValue(CachedClass.java:81)
	at org.codehaus.groovy.util.LazyReference.getLocked(LazyReference.java:50)
	at org.codehaus.groovy.util.LazyReference.get(LazyReference.java:37)
	at org.codehaus.groovy.reflection.CachedClass.getConstructors(CachedClass.java:311)
	at groovy.lang.MetaClassImpl.<init>(MetaClassImpl.java:218)
	at groovy.lang.MetaClassImpl.<init>(MetaClassImpl.java:228)
	at groovy.lang.MetaClassRegistry$MetaClassCreationHandle.createNormalMetaClass(MetaClassRegistry.java:171)
	at groovy.lang.MetaClassRegistry$MetaClassCreationHandle.createWithCustomLookup(MetaClassRegistry.java:161)
	at groovy.lang.MetaClassRegistry$MetaClassCreationHandle.create(MetaClassRegistry.java:144)
	at org.codehaus.groovy.reflection.ClassInfo.getMetaClassUnderLock(ClassInfo.java:288)
	at org.codehaus.groovy.reflection.ClassInfo.getMetaClass(ClassInfo.java:331)
	at org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl.getMetaClass(MetaClassRegistryImpl.java:270)
	at org.codehaus.groovy.runtime.InvokerHelper.getMetaClass(InvokerHelper.java:976)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.createCallConstructorSite(CallSiteArray.java:86)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallConstructor(CallSiteArray.java:59)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:238)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:242)
	at sut.Main.main(Main.groovy:8)

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':run'.
> Process 'command '/usr/lib/jvm/zulu-8-amd64/bin/java'' finished with non-zero exit value 1

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 0s
3 actionable tasks: 1 executed, 2 up-to-date
```

To make it work (see branch `workaround`) make the `SubImpl` also implement the interface and make it `@CompileStatic`.

```
$ git checkout workaround
$ ./gradlew clean run
> Task :compileJava UP-TO-DATE
> Task :compileGroovy UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE

> Task :run
Impl
Intr
SubImpl
Intr
Impl

BUILD SUCCESSFUL in 0s
3 actionable tasks: 1 executed, 2 up-to-date
```
