
package android.test;

import android.os.Bundle;

import junit.framework.TestSuite;

public class BespokeInstrumentationTestRunner extends InstrumentationTestRunner {

    public BespokeInstrumentationTestRunner() {
        super();
    }

    @Override
    public TestSuite getAllTests() {
        return super.getAllTests();
    }

    @Override
    public TestSuite getTestSuite() {
        return super.getTestSuite();
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected AndroidTestRunner getAndroidTestRunner() {
        return super.getAndroidTestRunner();
    }

    @Override
    public ClassLoader getLoader() {
        // is this ever called?
        return new MyClassLoader();
    }

    private class MyClassLoader extends ClassLoader {

        public MyClassLoader() {
            super();
        }

        @SuppressWarnings("unused")
        public MyClassLoader(ClassLoader parentLoader) {
            super(parentLoader);
        }
    }
}
