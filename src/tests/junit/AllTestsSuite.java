package tests.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    BDeTests.class,
    TestFonctionnalites.class,
    tests.junit.business.tools.TestGenerationOffres.class,
})
public class AllTestsSuite {
}